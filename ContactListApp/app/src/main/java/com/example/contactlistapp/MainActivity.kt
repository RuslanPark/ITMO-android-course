package com.example.contactlistapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.contactlistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_PERMISSION_READ_CONTACTS = 200
        private const val REQUEST_CODE_PERMISSION_SEND_SMS = 201
    }
    private  lateinit var binding: ActivityMainBinding
    lateinit var contactAdapter: RecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            displayContacts()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_PERMISSION_READ_CONTACTS
            )
        }
    }

    private fun displayContacts() {

        //val viewManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        val viewManager = GridLayoutManager(this, 3)

        viewManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position % 4) {
                    3 -> 3
                    else -> 1
                }
            }
        }

        contactAdapter = RecycleViewAdapter(fetchAllContacts(), {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${it.phoneNumber}"))
            startActivity(intent)
        }, {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    REQUEST_CODE_PERMISSION_SEND_SMS
                )
            }

            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${it.phoneNumber}"))
            val message = "Hello! Long time no see, how are you!?"
            intent.putExtra("sms_body", message)
            startActivity(intent)
        })


        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.apply {
            layoutManager = viewManager
            adapter = contactAdapter
        }

        Toast.makeText(
            this,
            resources.getQuantityString(
                R.plurals.contacts_toast,
                contactAdapter.itemCount,
                contactAdapter.itemCount
            ),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu!!.findItem(R.id.search_view)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    contactAdapter.filter(searchView.query.toString())
                    contactAdapter.notifyDataSetChanged()
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            }
        )

        return super.onCreateOptionsMenu(menu)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displayContacts()
                } else {
                    Toast.makeText(this, "Read contacts permission denied", Toast.LENGTH_SHORT)
                        .show()
                }
                return
            }
            REQUEST_CODE_PERMISSION_SEND_SMS -> {
                if (!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Send SMS permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}