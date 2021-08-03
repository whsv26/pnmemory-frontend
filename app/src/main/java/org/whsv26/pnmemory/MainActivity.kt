package org.whsv26.pnmemory

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

    val refreshRcmTokenButton: Button = findViewById(R.id.button_refresh_rcm_token)
    refreshRcmTokenButton.setOnClickListener {
      FirebaseInstallations.getInstance().getToken(false).addOnSuccessListener { res ->
        Toast.makeText(this, res.token, Toast.LENGTH_LONG).show()
        CoroutineScope(IO).launch {
          Backend.refreshFcmToken(RefreshFcmTokenRequest(res.token))
        }
      }
    }
  }
}