package com.example.chatapplication.ui.theme.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.R
import com.example.chatapplication.ui.theme.Names
import com.example.chatapplication.ui.theme.SharedData
import com.example.chatapplication.ui.theme.TutViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LogInScreenUI(
    modifier: Modifier = Modifier,
    tutViewModel: TutViewModel = viewModel(),
    text: String,
    auth: FirebaseAuth,
    navController: NavHostController = rememberNavController(),
    db: FirebaseFirestore
) {
    val uiState by tutViewModel.uiState.collectAsState()
    val context: Context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.chatlogo2), contentDescription = "Logo")
        NameComposable(
            name = uiState.name,
            pwd = uiState.pwd,
            onTextChange = { tutViewModel.updateName(it) },
            onPwdChange = { tutViewModel.updatePwd(it) }
        )
//        Spacer(modifier = Modifier.height(10.dp))
//        PasswordComposable()
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            auth.signInWithEmailAndPassword(uiState.name, uiState.pwd)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
//                        tutViewModel.updateCurruser(uiState.username)
                        db.collection("users")
                            .whereEqualTo("Email", uiState.name)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    SharedData.data = (document.getString("Username"))
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "No Username exists", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        navController.navigate(route = Names.Main.name)
                    } else {
                        Toast.makeText(context, "Invalid Mail or Password", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }) {
            Text(text = text)
        }
        Spacer(modifier = Modifier.height(25.dp))
        ClickableText(text = buildAnnotatedString { append("Don't have an account yet?  Sign Up") }, onClick = {
            navController.navigate(Names.SignIn.name)
        })
    }
}

@Composable
fun NameComposable(
    modifier: Modifier = Modifier,
    name: String,
    pwd: String,
    onTextChange: (String) -> Unit,
    onPwdChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onTextChange,
        label = { Text(text = "Email") },
        singleLine = true,
        modifier = modifier.fillMaxWidth(0.72f)
    )
    Spacer(modifier = modifier.height(10.dp))
    OutlinedTextField(
        value = pwd,
        onValueChange = onPwdChange,
        label = { Text(text = "Password") },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        modifier = modifier.fillMaxWidth(0.72f)
    )
}