package com.example.contactlistapp

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*
import android.view.View
import android.view.ViewGroup

class RecycleViewAdapter (val contacts : List<Contact>, val onClick : (Contact) -> Unit) :
    RecyclerView.Adapter<RecycleViewAdapter.ContactViewHolder>() {

    class ContactViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val contactName = view.contact_name
        val contactPhoneNumber  = view.contact_phone_number
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val holder = ContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        holder.view.setOnClickListener {
            onClick(contacts[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.contactName.text = contacts[position].name
        holder.contactPhoneNumber.text = contacts[position].phoneNumber
    }

    override fun getItemCount(): Int = contacts.size

}