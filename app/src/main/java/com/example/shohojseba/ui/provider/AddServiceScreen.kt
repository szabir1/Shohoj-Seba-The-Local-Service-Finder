package com.example.shohojseba.ui.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shohojseba.data.model.AddServiceRequest
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.viewmodel.ProviderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServiceScreen(

    onServiceAdded: () -> Unit,

    viewModel: ProviderViewModel = viewModel()

) {

    var serviceName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val provider by viewModel.provider
    val categories by viewModel.categories
    val message by viewModel.message

    LaunchedEffect(Unit) {
        viewModel.loadProviderProfile()
        viewModel.loadCategories()
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),

        verticalArrangement = Arrangement.spacedBy(14.dp)

    ) {

        Text(
            text = "Create Service",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Add your service and reach more customers",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = serviceName,
            onValueChange = { serviceName = it },
            label = { Text("Service Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )

        // ================= CATEGORY DROPDOWN =================

        ExposedDropdownMenuBox(

            expanded = expanded,

            onExpandedChange = {

                expanded = !expanded

            }

        ) {

            OutlinedTextField(

                value = selectedCategory?.category_name ?: "",

                onValueChange = {},

                readOnly = true,

                label = { Text("Select Category") },

                leadingIcon = {
                    Icon(Icons.Default.Category, null)
                },

                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),

                shape = RoundedCornerShape(20.dp)

            )

            ExposedDropdownMenu(

                expanded = expanded,

                onDismissRequest = {

                    expanded = false

                }

            ) {

                categories.forEach { category ->

                    DropdownMenuItem(

                        text = {
                            Text(category.category_name)
                        },

                        onClick = {

                            selectedCategory = category

                            expanded = false

                        }

                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(

            onClick = {

                val providerId = provider?.provider_id

                if (
                    providerId != null &&
                    selectedCategory != null
                ) {

                    val service = AddServiceRequest(

                        service_name = serviceName,

                        description = description,

                        price = price.toDoubleOrNull() ?: 0.0,

                        duration = duration,

                        provider_id = providerId,

                        category_id = selectedCategory!!.category_id

                    )

                    viewModel.addService(service)

                    onServiceAdded()

                }

            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape = RoundedCornerShape(30.dp)

        ) {

            Text("＋ Add Service")

        }

        Spacer(modifier = Modifier.height(12.dp))

        if (message.isNotEmpty()) {

            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary
            )

        }

    }

}