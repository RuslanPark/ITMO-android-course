package com.example.contactlistapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*


class RecycleViewAdapter (val contacts : List<Contact>, val onClick : (Contact) -> Unit, val onClickSendMessage : (Contact) -> Unit) :
    RecyclerView.Adapter<RecycleViewAdapter.ContactViewHolder>() {

    class ContactViewHolder(val root : View) : RecyclerView.ViewHolder(root) {
        fun bind(contact : Contact) {
            with(root) {
                contact_name.text = contact.name
                contact_phone_number.text = contact.phoneNumber
                if (contact.photo == "N/A") {
                    contact_photo.setImageResource(R.drawable.ic_launcher_background)
                } else {
                    contact_photo.setImageURI(Uri.parse(contact.photo))
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.ContactViewHolder {
        val holder : ContactViewHolder
        when (viewType) {
            3 -> {
                holder = ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_blue, parent, false))
            }
            else -> {
                holder = ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
            }
        }
        holder.root.setOnClickListener {
            onClick(contacts[holder.adapterPosition])
        }
        holder.root.sendMessage.setOnClickListener {
            onClickSendMessage(contacts[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind(contacts[position])

    override fun getItemCount() = contacts.size

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

}