package com.example.shohojseba.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shohojseba.data.model.Category
import com.example.shohojseba.data.repository.CategoryRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State



class CategoryViewModel : ViewModel() {


    private val repository = CategoryRepository()



    private val _categories =
        mutableStateOf<List<Category>>(emptyList())


    val categories: State<List<Category>>
        get() = _categories




    fun loadCategories() {


        viewModelScope.launch {


            try {


                Log.d(
                    "CATEGORY_TEST",
                    "Loading categories..."
                )


                val result =
                    repository.getCategories()



                Log.d(
                    "CATEGORY_TEST",
                    "VIEWMODEL RECEIVED = $result"
                )



                _categories.value = result



            } catch (e: Exception) {


                Log.e(
                    "CATEGORY_TEST",
                    "VIEWMODEL ERROR = ${e.message}"
                )


            }


        }


    }


}