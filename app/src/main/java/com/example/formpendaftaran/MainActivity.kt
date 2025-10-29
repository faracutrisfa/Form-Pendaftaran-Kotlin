package com.example.formpendaftaran

import com.example.formpendaftaran.DisplayActivity
import com.example.formpendaftaran.Member
import com.example.formpendaftaran.SupabaseClient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etJabatan: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var rbMale: MaterialRadioButton
    private lateinit var rbFemale: MaterialRadioButton
    private lateinit var btnSave: MaterialButton
    private lateinit var btnViewData: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        etUsername = findViewById(R.id.etUsername)
        etJabatan = findViewById(R.id.etJabatan)
        etEmail = findViewById(R.id.etEmail)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        btnSave = findViewById(R.id.btnSave)
        btnViewData = findViewById(R.id.btnViewData)

        // Set default selection for gender
        rbMale.isChecked = true

        // Save button click listener
        btnSave.setOnClickListener {
            saveMember()
        }

        // View data button click listener
        btnViewData.setOnClickListener {
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveMember() {
        // Get input values
        val username = etUsername.text.toString().trim()
        val jabatan  = etJabatan.text.toString().trim()
        val email    = etEmail.text.toString().trim()
        val jenisKelamin = if (rbMale.isChecked) "Laki-laki" else "Perempuan"

        // Validation
        if (username.isEmpty()) {
            etUsername.error = "Username tidak boleh kosong"
            etUsername.requestFocus()
            return
        }

        if (jabatan.isEmpty()) {
            etJabatan.error = "Jabatan tidak boleh kosong"
            etJabatan.requestFocus()
            return
        }

        if (email.isEmpty()) {
            etEmail.error = "Email tidak boleh kosong"
            etEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Format email tidak valid"
            etEmail.requestFocus()
            return
        }

        // Disable button while saving
        btnSave.isEnabled = false
        btnSave.text = "Menyimpan..."

        // Create member object
        val member = Member(
            username = username,
            jabatan = jabatan,
            jenisKelamin = jenisKelamin,
            email = email,
            avatarRes = R.mipmap.ic_launcher_round
        )

        // Save to Supabase
        lifecycleScope.launch {
            try {
                SupabaseClient.client.from("members").insert(member)

                Toast.makeText(
                    this@MainActivity,
                    "Data berhasil disimpan!",
                    Toast.LENGTH_SHORT
                ).show()

                // Clear form
                clearForm()

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Gagal menyimpan data: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnSave.isEnabled = true
                btnSave.text = "Save"
            }
        }
    }

    private fun clearForm() {
        etUsername.text?.clear()
        etJabatan.text?.clear()
        etEmail.text?.clear()
        rbMale.isChecked = true
        etUsername.requestFocus()
    }
}