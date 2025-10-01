package com.example.formpendaftaran

import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val id: Int? = null,
    val username: String,
    val jabatan: String,
    val jenis_kelamin: String,
    val email: String,
    val created_at: String? = null
)