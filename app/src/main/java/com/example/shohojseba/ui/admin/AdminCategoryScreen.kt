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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCategoryScreen(

    viewModel: AdminViewModel = viewModel()

) {

    val categories by viewModel.categories
    val isLoading by viewModel.isLoading
    val message by viewModel.message

    var showAddDialog by remember {
        mutableStateOf(false)
    }

    var editingCategory by remember {
        mutableStateOf<Category?>(null)
    }

    var deletingCategory by remember {
        mutableStateOf<Category?>(null)
    }

    var categoryName by remember {
        mutableStateOf("")
    }

    var showMessageDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    LaunchedEffect(message) {

        if (message.isNotBlank()) {
            showMessageDialog = true
        }
    }


    // =====================================================
    // SUCCESS / ERROR POPUP
    // =====================================================

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
                            containerColor = Color(0xFF007A7A)
                        )

                ) {

                    Text("OK")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }


    // =====================================================
    // ADD CATEGORY
    // =====================================================

    if (showAddDialog) {

        AlertDialog(

            onDismissRequest = {

                showAddDialog = false
                categoryName = ""
            },

            title = {
                Text("Add Category")
            },

            text = {

                OutlinedTextField(

                    value = categoryName,

                    onValueChange = {
                        categoryName = it
                    },

                    label = {
                        Text("Category Name")
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

                        viewModel.addCategory(
                            categoryName
                        )

                        showAddDialog = false
                        categoryName = ""
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF007A7A)
                        )
                ) {

                    Text("Add")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showAddDialog = false
                        categoryName = ""
                    }

                ) {

                    Text("Cancel")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }


    // =====================================================
    // EDIT CATEGORY
    // =====================================================

    if (editingCategory != null) {

        AlertDialog(

            onDismissRequest = {

                editingCategory = null
                categoryName = ""
            },

            title = {
                Text("Edit Category")
            },

            text = {

                OutlinedTextField(

                    value = categoryName,

                    onValueChange = {
                        categoryName = it
                    },

                    label = {
                        Text("Category Name")
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

                        val category =
                            editingCategory

                        if (category != null) {

                            viewModel.updateCategory(
                                category.category_id,
                                categoryName
                            )
                        }

                        editingCategory = null
                        categoryName = ""
                    },

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF007A7A)
                        )

                ) {

                    Text("Save")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        editingCategory = null
                        categoryName = ""
                    }

                ) {

                    Text("Cancel")
                }
            },

            shape =
                RoundedCornerShape(24.dp)
        )
    }


    // =====================================================
    // DELETE CONFIRMATION
    // =====================================================

    if (deletingCategory != null) {

        AlertDialog(

            onDismissRequest = {
                deletingCategory = null
            },

            title = {
                Text("Delete Category?")
            },

            text = {

                Text(
                    "Are you sure you want to delete \"${deletingCategory?.category_name}\"?"
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        deletingCategory
                            ?.category_id
                            ?.let {

                                viewModel.deleteCategory(
                                    it
                                )
                            }

                        deletingCategory = null
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
                        deletingCategory = null
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

        containerColor = Color.Transparent,

        topBar = {

            TopAppBar(

                title = {
                    Text("Manage Categories")
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            Color.Transparent
                    )
            )
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    categoryName = ""
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
                        "Add Category"
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
                        categories.isEmpty() -> {

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


                categories.isEmpty() -> {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center

                    ) {

                        Text(
                            "No categories available"
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
                            items = categories,
                            key = {
                                it.category_id
                            }
                        ) { category ->

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

                                    Text(

                                        text =
                                            "🛠 ${category.category_name}",

                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleMedium,

                                        modifier =
                                            Modifier.weight(1f)
                                    )


                                    IconButton(

                                        onClick = {

                                            editingCategory =
                                                category

                                            categoryName =
                                                category.category_name
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

                                            deletingCategory =
                                                category
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