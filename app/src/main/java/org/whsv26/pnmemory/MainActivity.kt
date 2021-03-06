package org.whsv26.pnmemory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.whsv26.pnmemory.ui.notification.FcmTokenViewModel
import androidx.activity.viewModels
import org.whsv26.pnmemory.databinding.ActivityMainBinding
import org.whsv26.pnmemory.ui.login.LoginActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val fcmTokenViewModel by viewModels<FcmTokenViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val tokenPreferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    val jwtToken = tokenPreferences.getString("jwt_token", "")!!;

    binding.buttonRefreshRcmToken.setOnClickListener {6
      FirebaseInstallations.getInstance().getToken(false).addOnSuccessListener { res ->
        Toast.makeText(this, res.token, Toast.LENGTH_LONG).show()
        fcmTokenViewModel // trigger creation on ui thread
        CoroutineScope(IO).launch {
          fcmTokenViewModel.refresh(jwtToken, res.token)
        }
      }
    }

    binding.buttonSignIn.setOnClickListener {
      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
    }
  }
}