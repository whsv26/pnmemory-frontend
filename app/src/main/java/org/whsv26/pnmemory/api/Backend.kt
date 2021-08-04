package org.whsv26.pnmemory.api

import android.content.SharedPreferences
import arrow.core.Option
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.core.deserializers.EmptyDeserializer
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import org.whsv26.pnmemory.api.request.LogInRequest
import org.whsv26.pnmemory.api.request.RefreshFcmTokenRequest

object Backend {
  private val headers: MutableMap<String, String> = mutableMapOf()

  init {
    FuelManager.instance.basePath = "http://10.0.2.2:8080/api/"
    FuelManager.instance.baseHeaders = headers
  }

  private fun authorize(preference: SharedPreferences) {
    Option
      .fromNullable(preference.getString("jwt_token", null))
      .map { headers.put("Authorization", "Bearer $it") };
  }

  operator fun invoke(preference: SharedPreferences): Backend {
    return apply { authorize(preference) }
  }

  suspend fun login(request: LogInRequest): ResponseOf<Unit> {
    return "public/token".httpPost().jsonBody(request).awaitResponse(EmptyDeserializer)
  }

  suspend fun refreshFcmToken(request: RefreshFcmTokenRequest) {
    "fcm/refresh".httpPut().jsonBody(request).awaitUnit()
  }
}