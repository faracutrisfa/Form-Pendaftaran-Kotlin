package com.example.formpendaftaran

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.formpendaftaran.Member

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_MEMBER = "extra_member"

        fun start(context: Context, member: Member) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_MEMBER, member)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val member = intent.getParcelableExtra<Member>(EXTRA_MEMBER) ?: return

        findViewById<ImageView>(R.id.imgAvatarDetail).setImageResource(member.avatarRes)
        findViewById<TextView>(R.id.tvUsernameDetail).text = member.username
        findViewById<TextView>(R.id.tvEmailDetail).text = member.email
        findViewById<TextView>(R.id.tvJabatanDetail).text = member.jabatan
        findViewById<TextView>(R.id.tvGenderDetail).text = member.jenisKelamin
    }
}
