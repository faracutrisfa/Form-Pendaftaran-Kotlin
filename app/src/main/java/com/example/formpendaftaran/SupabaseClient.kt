package com.example.formpendaftaran

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    private const val SUPABASE_URL = "https://ayoxzrpmsmwfggzxzqrl.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF5b3h6cnBtc213Zmdnenh6cXJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkyOTY2NzYsImV4cCI6MjA3NDg3MjY3Nn0.qEaZh-eyI06Sy_hEdELmEYLQzcOIl7Hh6DlXiOcTfBo"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}