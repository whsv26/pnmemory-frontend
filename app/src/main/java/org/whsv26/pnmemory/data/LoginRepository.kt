package org.whsv26.pnmemory.data

import org.whsv26.pnmemory.api.PnmemoryService
import org.whsv26.pnmemory.api.Outcome
import org.whsv26.pnmemory.api.request.LogInRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(private val backend: PnmemoryService) {

  fun logout(): Unit = TODO()

  suspend fun login(username: String, password: String): Outcome<String> {
    return backend.login(LogInRequest(username, password))
  }
}