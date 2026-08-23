package com.example.shohojseba.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.data.model.Area
import com.example.shohojseba.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAreaScreen(

    viewModel: AdminViewModel = viewModel()

) {

    val areas by viewModel.areas
    val isLoading by viewModel.isLoading
    val message by viewModel.message

    var showAddDialog by remember {
        mutableStateOf(false)
    }

    var editingArea by remember {
        mutableStateOf<Area?>(null)
    }

    var deletingArea by remember {
        mutableStateOf<Area?>(null)
    }

    var areaName by remember {
        mutableStateOf("")
    }

    var showMessageDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        viewModel.loadAreas()
    }

    LaunchedEffect(message) {

        if (message.isNotBlank()) {

            showMessageDialog = true
        }
    }

    if (showMessageDialog) {

        AlertDialog(

            onDismissRequest = {

                showMessageDialog = false
                viewModel.clearMessage()
            },

            title = {

                Text(
                    if (
                        message.contains(
                            "successfully",
                            ignoreCase = true
                        )
                    ) {
                        "Success"
                    } else {
                        "Notice"
                    }
                )
            },

            text = {
                Text(message)
            },

            confirmButton = {

                Button(

                    onClick = {

                        showMessageDialog = false
                        viewModel.clearMessage()
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF007A7A)
                        )

                ) {

                    Text("OK")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }

    if (showAddDialog) {

        AlertDialog(

            onDismissRequest = {

                showAddDialog = false
                areaName = ""
            },

            title = {
                Text("Add Area")
            },

            text = {

                OutlinedTextField(

                    value = areaName,

                    onValueChange = {
                        areaName = it
                    },

                    label = {
                        Text("Area Name")
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp)
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        viewModel.addArea(
                            areaName
                        )

                        showAddDialog = false
                        areaName = ""
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF007A7A)
                        )
                ) {

                    Text("Add")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showAddDialog = false
                        areaName = ""
                    }

                ) {

                    Text("Cancel")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }

    if (editingArea != null) {

        AlertDialog(

            onDismissRequest = {

                editingArea = null
                areaName = ""
            },

            title = {
                Text("Edit Area")
            },

            text = {

                OutlinedTextField(

                    value = areaName,

                    onValueChange = {
                        areaName = it
                    },

                    label = {
                        Text("Area Name")
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(16.dp)
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        editingArea?.let {

                            viewModel.updateArea(
                                it.area_id,
                                areaName
                            )
                        }

                        editingArea = null
                        areaName = ""
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF007A7A)
                        )

                ) {

                    Text("Save")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        editingArea = null
                        areaName = ""
                    }

                ) {

                    Text("Cancel")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }

    if (deletingArea != null) {

        AlertDialog(

            onDismissRequest = {
                deletingArea = null
            },

            title = {
                Text("Delete Area?")
            },

            text = {

                Text(
                    "Are you sure you want to delete \"${deletingArea?.area_name}\"?"
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        deletingArea
                            ?.area_id
                            ?.let {

                                viewModel.deleteArea(
                                    it
                                )
                            }

                        deletingArea = null
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFFC62828)
                        )

                ) {

                    Text("Delete")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {
                        deletingArea = null
                    }

                ) {

                    Text("Cancel")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }

    Scaffold(

        containerColor =
            Color.Transparent,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        "Manage Areas"
                    )
                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(

                            containerColor =
                                Color.Transparent
                        )
            )
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    areaName = ""
                    showAddDialog = true
                },

                containerColor =
                    Color(0xFF007A7A),

                contentColor =
                    Color.White

            ) {

                Icon(
                    Icons.Default.Add,
                    contentDescription =
                        "Add Area"
                )
            }
        }

    ) { padding ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(

                    Brush.verticalGradient(

                        listOf(
                            Color(0xFFEFFFFB),
                            Color.White
                        )
                    )
                )
                .padding(padding)

        ) {

            when {

                isLoading &&
                        areas.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        CircularProgressIndicator(
                            color =
                                Color(0xFF007A7A)
                        )
                    }
                }

                areas.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Text(
                            "No areas available"
                        )
                    }
                }

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(20.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(
                                14.dp
                            )

                    ) {

                        items(

                            items = areas,

                            key = {
                                it.area_id
                            }

                        ) { area ->

                            Card(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        22.dp
                                    ),

                                elevation =
                                    CardDefaults
                                        .cardElevation(
                                            5.dp
                                        )

                            ) {

                                Row(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),

                                    verticalAlignment =
                                        Alignment.CenterVertically

                                ) {

                                    Icon(

                                        imageVector =
                                            Icons.Default.LocationOn,

                                        contentDescription =
                                            null,

                                        tint =
                                            Color(0xFF007A7A)
                                    )

                                    Spacer(
                                        Modifier.width(10.dp)
                                    )

                                    Text(

                                        text =
                                            area.area_name,

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleMedium,

                                        modifier =
                                            Modifier.weight(1f)
                                    )

                                    IconButton(

                                        onClick = {

                                            editingArea =
                                                area

                                            areaName =
                                                area.area_name
                                        }

                                    ) {

                                        Icon(

                                            Icons.Default.Edit,

                                            contentDescription =
                                                "Edit",

                                            tint =
                                                Color(0xFF007A7A)
                                        )
                                    }

                                    IconButton(

                                        onClick = {

                                            deletingArea =
                                                area
                                        }

                                    ) {

                                        Icon(

                                            Icons.Default.Delete,

                                            contentDescription =
                                                "Delete",

                                            tint =
                                                Color(0xFFC62828)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}