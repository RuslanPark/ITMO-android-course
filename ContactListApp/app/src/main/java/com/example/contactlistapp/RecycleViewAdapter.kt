package com.example.contactlistapp

import android.net.Uri
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistapp.databinding.ItemBinding
import com.example.contactlistapp.databinding.ItemBlueBinding
import java.util.*
import kotlin.coroutines.coroutineContext

class RecycleViewAdapter(
    var contacts: List<Contact>,
    val onClick: (Contact) -> Unit,
    val onClickSendMessage: (Contact) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val savedContactsInstance = contacts

    class ContactViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                contactName.text = contact.name
                contactPhoneNumber.text = contact.phoneNumber
                if (contact.photo == "N/A") {
                    contactPhoto.setImageResource(R.drawable.ic_launcher_background)
                } else {
                    contactPhoto.setImageURI(Uri.parse(contact.photo))
                }
            }
        }
    }
    class ContactBlueViewHolder(val binding: ItemBlueBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                contactName.text = contact.name
                contactPhoneNumber.text = contact.phoneNumber
                if (contact.photo == "N/A") {
                    contactPhoto.setImageResource(R.drawable.ic_launcher_background)
                } else {
                    contactPhoto.setImageURI(Uri.parse(contact.photo))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val holder = when (viewType) {
            3 -> {
                ContactBlueViewHolder(
                    ItemBlueBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                ).apply {
                    binding.root.setOnClickListener {
                        onClick(contacts[adapterPosition])
                    }
                    binding.sendMessage.setOnClickListener {
                        onClickSendMessage(contacts[adapterPosition])
                    }
                }
            }
            else -> {
                ContactViewHolder(
                    ItemBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                ).apply {
                    binding.root.setOnClickListener {
                        onClick(contacts[adapterPosition])
                    }
                    binding.sendMessage.setOnClickListener {
                        onClickSendMessage(contacts[adapterPosition])
                    }
                }
            }
        }

        return holder
    }

    fun filter(charText: String) {
        contacts = savedContactsInstance
        if (charText.isNotEmpty()) {
            val filteredList = mutableListOf<Contact>()
            for (contact in contacts) {
                if (contact.name.toLowerCase(Locale.getDefault()).contains(charText.toLowerCase(Locale.getDefault())) ||
                    contact.phoneNumber.toLowerCase(Locale.getDefault()).contains(charText.toLowerCase(Locale.getDefault()))
                ) {
                    filteredList.add(contact)
                }
            }
            contacts = filteredList
        }
    }

    override fun getItemCount() = contacts.size

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContactViewHolder) {
            holder.bind(contacts[position])
        }
        if (holder is ContactBlueViewHolder) {
            holder.bind(contacts[position])
        }
    }

}