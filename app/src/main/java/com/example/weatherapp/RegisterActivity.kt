package com.example.weatherapp

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {
    var nome by rememberSaveable { mutableStateOf("") }
    Spacer(modifier = modifier.size(24.dp))
    var email by rememberSaveable { mutableStateOf("") }
    Spacer(modifier = modifier.size(24.dp))
    var password by rememberSaveable { mutableStateOf("") }
    Spacer(modifier = modifier.size(24.dp))
    var confirmacao by rememberSaveable { mutableStateOf("") }
    Spacer(modifier = modifier.size(24.dp))
    val activity = LocalContext.current as? Activity
    Spacer(modifier = modifier.size(24.dp))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
        modifier = modifier.padding(16.dp).fillMaxSize(),
    ) {
        Text(
            text = "Bem-vindo/a!",
            fontSize = 24.sp
        )
        OutlinedTextField(
            value = nome,
            label = { Text(text = "Digite seu nome") },
            modifier = modifier.fillMaxWidth(),
            onValueChange = { nome = it }
        )
        OutlinedTextField(
            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = modifier.fillMaxWidth(),
            onValueChange = { email = it }
        )
        OutlinedTextField(
            value = password,
            label = { Text(text = "Digite sua senha") },
            modifier = modifier.fillMaxWidth(),
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = confirmacao,
            label = { Text(text = "Confirme sua senha") },
            modifier = modifier.fillMaxWidth(),
            onValueChange = { confirmacao = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Row(modifier = modifier) {
            Button(
                onClick = {
                    if (activity != null) {
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        activity,
                                        "Registro OK!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    activity.startActivity(
                                        Intent(activity, LoginActivity::class.java).setFlags(
                                            FLAG_ACTIVITY_SINGLE_TOP
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Registro FALHOU!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                },
                enabled = email.isNotEmpty() && nome.isNotEmpty() && password.isNotEmpty() && confirmacao.isNotEmpty()
                        && (password == confirmacao)
            ) {
                Text("Registrar")
            }
            Spacer(modifier = modifier.size(24.dp))
            Button(
                onClick = { email = ""; password = "" }
            ) {
                Text("Limpar")
            }
        }
    }
}