package com.example.shohojseba.data.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.engine.okhttp.OkHttp
val supabase = createSupabaseClient(

    supabaseUrl = "https://spmffufgmzeyvndqknom.supabase.co",

    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNwbWZmdWZnbXpleXZuZHFrbm9tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODY5MzU5MDQsImV4cCI6MjEwMjUxMTkwNH0.GHN_dkhyXVmQfaFUyUGnrC2SqjRzNAp6NZKZedylU3w"

) {

    install(Auth)
    install(Postgrest)
    httpEngine = OkHttp.create()
}