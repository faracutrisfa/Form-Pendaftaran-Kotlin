package com.example.formpendaftaran

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class DisplayActivity : AppCompatActivity() {

    private lateinit var rvMembers: RecyclerView
    private lateinit var emptyState: LinearLayout
    private lateinit var tvTotalMembers: TextView
    private lateinit var btnBack: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        rvMembers = findViewById(R.id.rvMembers)
        emptyState = findViewById(R.id.emptyState)
        tvTotalMembers = findViewById(R.id.tvTotalMembers)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        setupRecycler()
    }

    private fun setupRecycler() {
        val data = MemberData.list
        tvTotalMembers.text = data.size.toString()
        emptyState.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE

        rvMembers.layoutManager = LinearLayoutManager(this)
        rvMembers.adapter = MemberAdapter(data) { selected: Member ->
            DetailActivity.start(this, selected)
        }
    }
}
