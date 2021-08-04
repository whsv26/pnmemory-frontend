package org.whsv26.pnmemory

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import org.whsv26.pnmemory.api.Backend
import org.whsv26.pnmemory.api.request.RefreshFcmTokenRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // TODO Drop hardcoded token
    val jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aHN2MjZAZ21haWwuY29tIiwiaXNzIjoid2hzdjI2IiwiaWF0IjoxNjI4MTAwOTA0LCJleHAiOjE2Mjg3MDU3MDR9.kujKOzdaCt-3P7jznJDXIpGCmrHLkNzbwoQ4x8_HfJDlLm1LgM8uDE1ifK8MOQwPi2WIpv9yvnCgxwuAy_9_9Q"
    val tokenPreferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)

    tokenPreferences.edit().putString("jwt_token", jwtToken).apply()

    val refreshRcmTokenButton: Button = findViewById(R.id.button_refresh_rcm_token)
    refreshRcmTokenButton.setOnClickListener {
      FirebaseInstallations.getInstance().getToken(false).addOnSuccessListener { res ->
        Toast.makeText(this, res.token, Toast.LENGTH_LONG).show()
        CoroutineScope(IO).launch {
          Backend(tokenPreferences).refreshFcmToken(RefreshFcmTokenRequest(res.token))
        }
      }
    }
  }
}