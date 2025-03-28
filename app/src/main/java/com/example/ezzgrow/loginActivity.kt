package com.example.ezzgrow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ezzgrow.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.tvForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
        binding.button.setOnClickListener {
            val emailText = binding.editText1.text.toString().trim()
            val passwordText = binding.editText2.text.toString().trim()

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.textView6.setOnClickListener {
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    // Forgot Password Click Listener
    private fun showForgotPasswordDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Reset Password")

    val input = EditText(this)
    input.hint = "Enter your registered email"
    builder.setView(input)

    builder.setPositiveButton("Send") { _, _ ->
        val email = input.text.toString().trim()
        if (email.isNotEmpty()) {
            resetPassword(email)
        } else {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
        }
    }

    builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
    builder.show()
}
    private fun resetPassword(email: String) {
    auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to send reset email", Toast.LENGTH_SHORT).show()
            }
        }
}

}
