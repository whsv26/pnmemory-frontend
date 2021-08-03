package org.whsv26.pnmemory.api

import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.httpPut
import org.whsv26.pnmemory.api.request.RefreshFcmTokenRequest

object Backend {
  init {
    // val jwtToken = getString(R.string.jwt_token)
    FuelManager.instance.basePath = "http://10.0.2.2:8080/api/"
    TODO("drop hardcode")
    FuelManager.instance.baseHeaders = mapOf("Authorization" to "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aHN2MjZAZ21haWwuY29tIiwiaXNzIjoid2hzdjI2IiwiaWF0IjoxNjI4MDMwNzA1LCJleHAiOjE2Mjg2MzU1MDV9.Xs06GyO77HJ_BC957C2DM7HggMhQjcFGoW60G1vWqBydbQHo_kge_O5wSaaR43q53mpHgIr4x0A2I96iyegtRw")
  }

  suspend fun refreshFcmToken(request: RefreshFcmTokenRequest) {
    "fcm/refresh".httpPut().jsonBody(request).awaitUnit()
  }
}