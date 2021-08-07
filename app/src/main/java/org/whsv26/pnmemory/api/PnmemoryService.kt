package org.whsv26.pnmemory.api

import arrow.core.Either
import arrow.core.flatMap
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.core.deserializers.EmptyDeserializer
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import org.whsv26.pnmemory.api.request.LogInRequest
import org.whsv26.pnmemory.api.request.RefreshFcmTokenRequest
import com.github.kittinunf.result.Result as FuelResult

typealias Outcome<T> = Either<Throwable, T>

class PnmemoryService {

  init {
    FuelManager.instance.basePath = "http://10.0.2.2:8080/api/"
  }

  private fun Request.authorize(jwt: String): Request = appendHeader(
    "Authorization",
    "Bearer $jwt"
  )

  private fun <L : Exception, R> FuelResult<R, L>.toEither(): Either<L, R> = Either.conditionally(
    this is FuelResult.Success,
    { component2()!! },
    { component1()!! }
  )

  suspend fun login(request: LogInRequest): Outcome<String> {
    val (_, response, result) = "public/token".httpPost()
      .jsonBody(request)
      .awaitResponseResult(EmptyDeserializer)

    return result.toEither().flatMap { Either.catch {
      response.headers.getValue("Authorization").first()
    } }
  }


  suspend fun refreshFcmToken(jwt: String, request: RefreshFcmTokenRequest): Outcome<Unit> =
    "fcm/refresh".httpPut().authorize(jwt).jsonBody(request).awaitResult(EmptyDeserializer).toEither()

}