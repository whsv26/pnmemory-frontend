package org.whsv26.pnmemory.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.whsv26.pnmemory.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

  fun login(username: String, password: String): Either<IOException, LoggedInUser> {
    return try {
      val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
      fakeUser.right()
    } catch (e: Throwable) {
      IOException("Error logging in", e).left()
    }
  }

  fun logout() {
    // TODO: revoke authentication
  }
}