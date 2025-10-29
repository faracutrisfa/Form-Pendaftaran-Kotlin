package com.example.formpendaftaran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.formpendaftaran.R

class MemberAdapter(
    private val items: List<Member>,
    private val onClick: (Member) -> Unit
) : RecyclerView.Adapter<MemberAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgAvatar)
        val tvPrimary: TextView = v.findViewById(R.id.tvPrimary)
        val tvSecondary: TextView = v.findViewById(R.id.tvSecondary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.img.setImageResource(item.avatarRes)
        holder.tvPrimary.text = item.username
        holder.tvSecondary.text = item.jabatan

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}