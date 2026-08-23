package com.example.shohojseba.data.repository

import com.example.shohojseba.data.model.Area
import com.example.shohojseba.data.supabase.supabase
import io.github.jan.supabase.postgrest.from

class AreaRepository {

    suspend fun getAreas(): Result<List<Area>> {

        return try {

            val areas = supabase
                .from("area")
                .select()
                .decodeList<Area>()
                .sortedBy {
                    it.area_name
                }

            Result.success(areas)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

}