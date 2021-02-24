package com.github.nexus.actions.agent

import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

/**
 * NexusStagingClient will create a staging repository and upload the given files to the given staging repository
 * Creating a staging repository avoid having split artifacts
 *
 * The process is outlined in this document (from 2014!):
 * https://support.sonatype.com/hc/en-us/articles/213465868-Uploading-to-a-Staging-Repository-via-REST-API
 *
 * @param baseUrl: the url of the nexus instance, defaults to the OSSRH url
 * @param username: your Nexus username. For OSSRH, this is your Sonatype jira username
 * @param password: your Nexus password. For OSSRH, this is your Sonatype jira password
 */
public class NexusStagingClient(
  private val baseUrl: String = "https://oss.sonatype.org/service/local",
  username: String,
  password: String,
) {
  private val httpClient = HttpClient {
    install(Auth) {
      basic {
        sendWithoutRequest = true
        this.username = username
        this.password = password
      }
    }
    install(JsonFeature) {
      serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
      })
    }
  }

  /**
   * Return a list of all staging repositories
   */
  public suspend fun getProfiles(): List<Profile> {
    return httpClient.get<Data<Profile>>("$baseUrl/staging/profiles").data
  }
}