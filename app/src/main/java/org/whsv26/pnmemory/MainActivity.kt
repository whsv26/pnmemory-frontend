package org.whsv26.pnmemory

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.whsv26.pnmemory.ui.notification.FcmTokenViewModel
import androidx.activity.viewModels

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val fcmTokenViewModel by viewModels<FcmTokenViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // TODO Drop hardcoded token
    val jwtTokenHardcoded = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aHN2MjZAZ21haWwuY29tIiwiaXNzIjoid2hzdjI2IiwiaWF0IjoxNjI4MjY1NjY5LCJleHAiOjE2Mjg4NzA0Njl9.sc2_-7iGNSQIWdwH1-D9xiKDZFOpmzeEsUoL4o2BN5pjIclIGjujUwX-uxKihksaIUoorg3uVyTWjSuyw1xaCQ"
    val tokenPreferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    tokenPreferences.edit().putString("jwt_token", jwtTokenHardcoded).apply()

    val jwtToken = tokenPreferences.getString("jwt_token", "")!!;

    val refreshRcmTokenButton: Button = findViewById(R.id.button_refresh_rcm_token)
    refreshRcmTokenButton.setOnClickListener {
      FirebaseInstallations.getInstance().getToken(false).addOnSuccessListener { res ->
        Toast.makeText(this, res.token, Toast.LENGTH_LONG).show()

        fcmTokenViewModel // trigger creation on ui thread

        CoroutineScope(IO).launch {
          fcmTokenViewModel.refresh(jwtToken, res.token)
        }
      }
    }
  }
}