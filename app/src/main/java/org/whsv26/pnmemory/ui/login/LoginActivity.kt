package org.whsv26.pnmemory.ui.login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import arrow.core.Either
import dagger.hilt.android.AndroidEntryPoint
import org.whsv26.pnmemory.databinding.ActivityLoginBinding
import org.whsv26.pnmemory.R

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

  private val loginViewModel by viewModels<LoginViewModel>()
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val username = binding.username
    val password = binding.password
    val login = binding.login
    val loading = binding.loading

    loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
      val loginState = it ?: return@Observer

      // disable login button unless both username / password is valid
      login.isEnabled = loginState.isDataValid

      if (loginState.usernameError != null) {
        username.error = getString(loginState.usernameError)
      }
      if (loginState.passwordError != null) {
        password.error = getString(loginState.passwordError)
      }
    })

    loginViewModel.loginResult.observe(this@LoginActivity, Observer {
      val loginResult = it ?: return@Observer

      loading.visibility = View.GONE
      when (loginResult) {
        is Either.Right -> saveJwtToken(loginResult.value)
        is Either.Left -> showLoginFailed(R.string.login_failed)
      }
      setResult(Activity.RESULT_OK)

      //Complete and destroy login activity once successful
      finish()
    })

    username.afterTextChanged {
      loginViewModel.loginDataChanged(
        username.text.toString(),
        password.text.toString()
      )
    }

    password.apply {
      afterTextChanged {
        loginViewModel.loginDataChanged(
          username.text.toString(),
          password.text.toString()
        )
      }

      setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
          EditorInfo.IME_ACTION_DONE ->
            loginViewModel.login(
              username.text.toString(),
              password.text.toString()
            )
        }
        false
      }

      login.setOnClickListener {
        loading.visibility = View.VISIBLE
        loginViewModel.login(username.text.toString(), password.text.toString())
      }
    }
  }

  private fun showLoginFailed(@StringRes errorString: Int) {
    Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
  }

  private fun saveJwtToken(jwt: String) {
    val tokenPreferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    tokenPreferences.edit().putString("jwt_token", jwt).apply()
  }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
  this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(editable: Editable?) {
      afterTextChanged.invoke(editable.toString())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
  })
}