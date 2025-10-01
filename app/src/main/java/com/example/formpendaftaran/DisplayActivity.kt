package com.example.formpendaftaran

import com.example.formpendaftaran.DisplayActivity
import com.example.formpendaftaran.SupabaseClient

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class DisplayActivity : AppCompatActivity() {

    private lateinit var membersContainer: LinearLayout
    private lateinit var emptyState: LinearLayout
    private lateinit var tvTotalMembers: TextView
    private lateinit var btnBack: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        // Initialize views
        membersContainer = findViewById(R.id.membersContainer)
        emptyState = findViewById(R.id.emptyState)
        tvTotalMembers = findViewById(R.id.tvTotalMembers)
        btnBack = findViewById(R.id.btnBack)

        // Back button listener
        btnBack.setOnClickListener {
            finish()
        }

        // Load data from Supabase
        loadMembers()
    }

    private fun loadMembers() {
        lifecycleScope.launch {
            try {
                val members = SupabaseClient.client
                    .from("members")
                    .select()
                    .decodeList<Member>()

                if (members.isEmpty()) {
                    showEmptyState()
                } else {
                    displayMembers(members)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@DisplayActivity,
                    "Gagal memuat data: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                showEmptyState()
            }
        }
    }

    private fun showEmptyState() {
        membersContainer.visibility = View.GONE
        emptyState.visibility = View.VISIBLE
        tvTotalMembers.text = "0"
    }

    private fun displayMembers(members: List<Member>) {
        membersContainer.removeAllViews()
        membersContainer.visibility = View.VISIBLE
        emptyState.visibility = View.GONE

        tvTotalMembers.text = members.size.toString()

        members.forEachIndexed { index, member ->
            val memberCard = createMemberCard(member, index + 1)
            membersContainer.addView(memberCard)
        }
    }

    private fun createMemberCard(member: Member, number: Int): View {
        // Create card
        val card = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }
            radius = dpToPx(16).toFloat()
            cardElevation = dpToPx(4).toFloat()
            setCardBackgroundColor(Color.WHITE)
        }

        // Create container layout
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(20), dpToPx(20), dpToPx(20), dpToPx(20))
        }

        // Header with number badge
        val headerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(16)
            }
        }

        val badgeCard = MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(40),
                dpToPx(40)
            ).apply {
                rightMargin = dpToPx(12)
            }
            radius = dpToPx(20).toFloat()
            setCardBackgroundColor(Color.parseColor("#6366F1"))
        }

        val badgeText = TextView(this).apply {
            text = number.toString()
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = android.view.Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }
        badgeCard.addView(badgeText)

        val nameLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val nameText = TextView(this).apply {
            text = member.username
            textSize = 18f
            setTextColor(Color.parseColor("#1F2937"))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val emailText = TextView(this).apply {
            text = member.email
            textSize = 14f
            setTextColor(Color.parseColor("#6B7280"))
        }

        nameLayout.addView(nameText)
        nameLayout.addView(emailText)
        headerLayout.addView(badgeCard)
        headerLayout.addView(nameLayout)

        // Divider
        val divider = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(1)
            ).apply {
                topMargin = dpToPx(0)
                bottomMargin = dpToPx(16)
            }
            setBackgroundColor(Color.parseColor("#E5E7EB"))
        }

        // Info rows
        val jabatanRow = createInfoRow("Jabatan", member.jabatan)
        val genderRow = createInfoRow("Jenis Kelamin", member.jenis_kelamin)

        // Add all views to container
        container.addView(headerLayout)
        container.addView(divider)
        container.addView(jabatanRow)
        container.addView(genderRow)

        card.addView(container)
        return card
    }

    private fun createInfoRow(label: String, value: String): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }

            val labelText = TextView(this@DisplayActivity).apply {
                text = label
                textSize = 14f
                setTextColor(Color.parseColor("#6B7280"))
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            val valueText = TextView(this@DisplayActivity).apply {
                text = value
                textSize = 14f
                setTextColor(Color.parseColor("#1F2937"))
                setTypeface(null, android.graphics.Typeface.BOLD)
                gravity = android.view.Gravity.END
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            addView(labelText)
            addView(valueText)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}