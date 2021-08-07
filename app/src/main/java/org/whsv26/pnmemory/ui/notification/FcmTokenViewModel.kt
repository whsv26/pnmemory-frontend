package org.whsv26.pnmemory.ui.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.whsv26.pnmemory.api.PnmemoryService
import org.whsv26.pnmemory.api.Result
import org.whsv26.pnmemory.api.request.RefreshFcmTokenRequest
import javax.inject.Inject

@HiltViewModel
class FcmTokenViewModel @Inject constructor(private val backend: PnmemoryService) : ViewModel() {

  suspend fun refresh(jwtToken: String, fcmToken: String): Result<Unit> {
    return backend.refreshFcmToken(jwtToken, RefreshFcmTokenRequest(fcmToken))
  }

}