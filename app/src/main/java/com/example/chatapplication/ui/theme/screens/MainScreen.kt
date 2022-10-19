package com.example.chatapplication.ui.theme.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatapplication.R
import com.example.chatapplication.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Handler

@Composable
fun MainScreenUI(
    modifier: Modifier = Modifier,
    tutViewModel: TutViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    db: FirebaseFirestore
) {
    val uiState by tutViewModel.uiState.collectAsState()
    val context: Context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Chat Application",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                backgroundColor = colorResource(id = R.color.purple_200),
                contentColor = Color.White,
                elevation = 12.dp
            )
        }
    ) {
        Column{
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                NameComposable3(
                    search = uiState.search,
                    onSearchChange = { tutViewModel.updateSearch(it) }
                )
                Spacer(modifier = modifier.width(15.dp))
                FloatingActionButton(
                    onClick = {
                        db.collection("users")
                            .whereEqualTo("Username", uiState.search)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    val connecteduser = hashMapOf(
                                        "Username" to uiState.search
                                    )
                                    Toast.makeText(
                                        context,
                                        "Logged In to the ${SharedData.data} - ${uiState.search}",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    db.collection("users").document(SharedData.data!!)
                                        .collection("connections").document(uiState.search)
                                        .set(connecteduser)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,
                                                "Adding User to the list",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()

                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                "Failed To Add User to the list",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "No Username exists", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    },
                    modifier = modifier.padding(top = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = modifier.size(40.dp)
                    )
                }
            }
            db.collection("users").document("sb1703").collection("connections")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val temp: String? = (document.getString("Username"))
                        Datasource.list.add(temp!!)
                        Toast.makeText(context, "$temp is added to AffirmationList : curr user - ${SharedData.data!!}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed To Load Affirmations", Toast.LENGTH_SHORT)
                        .show()
                }
            AffirmationList(affirmationlist = Datasource.list, modifier = modifier)
        }
    }
}

@Composable
fun AffirmationList(
    affirmationlist: List<String>,
    modifier: Modifier
) {
    Column {
//        items(affirmationlist.size) { username ->
//            AffirmationCard(username = username.toString(), modifier = modifier)
//        }
        for (username in affirmationlist) {
            AffirmationCard(username = username, modifier = modifier)
        }
    }
}

@Composable
fun AffirmationCard(username: String, modifier: Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .clickable { }
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(all = 15.dp)) {
            Text(text = username, modifier = modifier.padding(start = 10.dp))
        }
    }
}

@Composable
fun NameComposable3(
    modifier: Modifier = Modifier,
    search: String,
    onSearchChange: (String) -> Unit
) {
    OutlinedTextField(
        label = { Text(text = "Search For Username") },
        value = search,
        onValueChange = onSearchChange,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .padding(start = 18.dp, top = 4.dp)
            .fillMaxWidth(0.7f)
    )
}