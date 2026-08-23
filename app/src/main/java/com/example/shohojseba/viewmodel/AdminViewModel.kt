package com.example.shohojseba.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shohojseba.data.model.Area
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.data.model.Customer
import com.example.shohojseba.data.model.Provider
import com.example.shohojseba.data.model.Review
import com.example.shohojseba.data.model.Service

import com.example.shohojseba.data.repository.AdminRepository

import kotlinx.coroutines.launch


class AdminViewModel : ViewModel() {

    private val repository =
        AdminRepository()


    // =====================================================
    // CATEGORIES
    // =====================================================

    private val _categories =
        mutableStateOf<List<Category>>(
            emptyList()
        )

    val categories: State<List<Category>> =
        _categories


    // =====================================================
    // AREAS
    // =====================================================

    private val _areas =
        mutableStateOf<List<Area>>(
            emptyList()
        )

    val areas: State<List<Area>> =
        _areas


    // =====================================================
    // CUSTOMERS
    // =====================================================

    private val _customers =
        mutableStateOf<List<Customer>>(
            emptyList()
        )

    val customers: State<List<Customer>> =
        _customers


    // =====================================================
    // PROVIDERS
    // =====================================================

    private val _providers =
        mutableStateOf<List<Provider>>(
            emptyList()
        )

    val providers: State<List<Provider>> =
        _providers


    // =====================================================
    // SERVICES
    // =====================================================

    private val _services =
        mutableStateOf<List<Service>>(
            emptyList()
        )

    val services: State<List<Service>> =
        _services


    // =====================================================
    // REVIEWS
    // =====================================================

    private val _reviews =
        mutableStateOf<List<Review>>(
            emptyList()
        )

    val reviews: State<List<Review>> =
        _reviews


    // =====================================================
    // LOADING
    // =====================================================

    private val _isLoading =
        mutableStateOf(false)

    val isLoading: State<Boolean> =
        _isLoading


    // =====================================================
    // MESSAGE
    // =====================================================

    private val _message =
        mutableStateOf("")

    val message: State<String> =
        _message


    // =====================================================
    // LOAD CATEGORIES
    // =====================================================

    fun loadCategories() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getCategories()

            if (result.isSuccess) {

                _categories.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load categories"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // ADD CATEGORY
    // =====================================================

    fun addCategory(
        name: String
    ) {

        if (name.isBlank()) {

            _message.value =
                "Category name cannot be empty"

            return
        }

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.addCategory(
                    name.trim()
                )

            if (result.isSuccess) {

                _message.value =
                    "Category added successfully"

                loadCategories()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to add category"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // UPDATE CATEGORY
    // =====================================================

    fun updateCategory(
        categoryId: Long,
        name: String
    ) {

        if (name.isBlank()) {

            _message.value =
                "Category name cannot be empty"

            return
        }

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.updateCategory(
                    categoryId,
                    name.trim()
                )

            if (result.isSuccess) {

                _message.value =
                    "Category updated successfully"

                loadCategories()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to update category"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // DELETE CATEGORY
    // =====================================================

    fun deleteCategory(
        categoryId: Long
    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.deleteCategory(
                    categoryId
                )

            if (result.isSuccess) {

                _message.value =
                    "Category deleted successfully"

                loadCategories()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to delete category"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // LOAD AREAS
    // =====================================================

    fun loadAreas() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getAreas()

            if (result.isSuccess) {

                _areas.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load areas"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // ADD AREA
    // =====================================================

    fun addArea(
        name: String
    ) {

        if (name.isBlank()) {

            _message.value =
                "Area name cannot be empty"

            return
        }

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.addArea(
                    name.trim()
                )

            if (result.isSuccess) {

                _message.value =
                    "Area added successfully"

                loadAreas()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to add area"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // UPDATE AREA
    // =====================================================

    fun updateArea(
        areaId: Long,
        name: String
    ) {

        if (name.isBlank()) {

            _message.value =
                "Area name cannot be empty"

            return
        }

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.updateArea(
                    areaId,
                    name.trim()
                )

            if (result.isSuccess) {

                _message.value =
                    "Area updated successfully"

                loadAreas()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to update area"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // DELETE AREA
    // =====================================================

    fun deleteArea(
        areaId: Long
    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.deleteArea(
                    areaId
                )

            if (result.isSuccess) {

                _message.value =
                    "Area deleted successfully"

                loadAreas()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to delete area"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // LOAD CUSTOMERS
    // =====================================================

    fun loadCustomers() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getCustomers()

            if (result.isSuccess) {

                _customers.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load customers"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // LOAD PROVIDERS
    // =====================================================

    fun loadProviders() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getProviders()

            if (result.isSuccess) {

                _providers.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load providers"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // SUSPEND PROVIDER
    // =====================================================

    fun suspendProvider(
        providerId: Long
    ) {

        updateProviderStatus(

            providerId =
                providerId,

            status =
                "SUSPENDED",

            successMessage =
                "Provider suspended successfully"

        )
    }


    // =====================================================
    // REACTIVATE PROVIDER
    // =====================================================

    fun reactivateProvider(
        providerId: Long
    ) {

        updateProviderStatus(

            providerId =
                providerId,

            status =
                "ACTIVE",

            successMessage =
                "Provider reactivated successfully"

        )
    }


    // =====================================================
    // REMOVE PROVIDER
    // =====================================================

    fun removeProvider(
        providerId: Long
    ) {

        updateProviderStatus(

            providerId =
                providerId,

            status =
                "REMOVED",

            successMessage =
                "Provider removed successfully"

        )
    }


    // =====================================================
    // PRIVATE PROVIDER STATUS UPDATE
    // =====================================================

    private fun updateProviderStatus(

        providerId: Long,

        status: String,

        successMessage: String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.updateProviderStatus(

                    providerId =
                        providerId,

                    status =
                        status

                )

            if (result.isSuccess) {

                _message.value =
                    successMessage

                loadProviders()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to update provider status"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // VERIFY PROVIDER
    // =====================================================

    fun verifyProvider(
        providerId: Long
    ) {

        updateProviderVerification(

            providerId =
                providerId,

            verified =
                true,

            successMessage =
                "Provider verified successfully"

        )
    }


    // =====================================================
    // REMOVE PROVIDER VERIFICATION
    // =====================================================

    fun removeProviderVerification(
        providerId: Long
    ) {

        updateProviderVerification(

            providerId =
                providerId,

            verified =
                false,

            successMessage =
                "Provider verification removed successfully"

        )
    }


    // =====================================================
    // PRIVATE VERIFICATION UPDATE
    // =====================================================

    private fun updateProviderVerification(

        providerId: Long,

        verified: Boolean,

        successMessage: String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.updateProviderVerification(

                    providerId =
                        providerId,

                    verified =
                        verified

                )

            if (result.isSuccess) {

                _message.value =
                    successMessage

                loadProviders()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to update provider verification"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // LOAD SERVICES
    // =====================================================

    fun loadServices() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getServices()

            if (result.isSuccess) {

                _services.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load services"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // REMOVE SERVICE
    // =====================================================

    fun removeService(
        serviceId: Long
    ) {

        updateServiceStatus(

            serviceId =
                serviceId,

            status =
                "REMOVED",

            successMessage =
                "Service removed successfully"

        )
    }


    // =====================================================
    // RESTORE SERVICE
    // =====================================================

    fun restoreService(
        serviceId: Long
    ) {

        updateServiceStatus(

            serviceId =
                serviceId,

            status =
                "ACTIVE",

            successMessage =
                "Service restored successfully"

        )
    }


    // =====================================================
    // PRIVATE SERVICE STATUS UPDATE
    // =====================================================

    private fun updateServiceStatus(

        serviceId: Long,

        status: String,

        successMessage: String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.updateServiceStatus(

                    serviceId =
                        serviceId,

                    status =
                        status

                )

            if (result.isSuccess) {

                _message.value =
                    successMessage

                loadServices()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to update service status"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // LOAD REVIEWS
    // =====================================================

    fun loadReviews() {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.getReviews()

            if (result.isSuccess) {

                _reviews.value =
                    result.getOrNull()
                        ?: emptyList()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to load reviews"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // DELETE REVIEW
    // =====================================================

    fun deleteReview(
        reviewId: Long
    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            val result =
                repository.deleteReview(
                    reviewId
                )

            if (result.isSuccess) {

                _message.value =
                    "Review deleted successfully"

                loadReviews()

            } else {

                _message.value =
                    result.exceptionOrNull()?.message
                        ?: "Failed to delete review"

            }

            _isLoading.value =
                false
        }
    }


    // =====================================================
    // CLEAR MESSAGE
    // =====================================================

    fun clearMessage() {

        _message.value = ""
    }
}