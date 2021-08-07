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
    val jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aHN2MjZAZ21haWwuY29tIiwiaXNzIjoid2hzdjI2IiwiaWF0IjoxNjI4MjY1NjY5LCJleHAiOjE2Mjg4NzA0Njl9.sc2_-7iGNSQIWdwH1-D9xiKDZFOpmzeEsUoL4o2BN5pjIclIGjujUwX-uxKihksaIUoorg3uVyTWjSuyw1xaCQ"
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