package com.example.formpendaftaran

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member(
    val username: String,
    val jabatan: String,
    val jenisKelamin: String,
    val email: String,
    val avatarRes: Int
) : Parcelable
