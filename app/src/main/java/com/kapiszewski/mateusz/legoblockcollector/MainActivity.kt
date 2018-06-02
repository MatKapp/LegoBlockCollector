package com.kapiszewski.mateusz.legoblockcollector

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.kapiszewski.mateusz.legoblockcollector.models.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var inventories: ArrayList<Inventory> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions()
        addInventory.setOnClickListener {
            showActivity()
        }


    }

    private fun showActivity() {
        val i = Intent(this, InventoryAddActivity::class.java)
        ContextCompat.startActivity(this, i, null)
    }

    override fun onResume() {
        val dbHandler = MyDBHandler(this, null, null, 1)
        inventories = dbHandler.getInventories()
        inventories_list.layoutManager = LinearLayoutManager(this)
        inventories_list.adapter = InventoryAdapter(inventories, this)
        super.onResume()
    }

    private val TAG = "storage permission"
    private val RECORD_REQUEST_CODE = 101

    private fun setupPermissions() {
        var permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, " READ Access denied")
            makeRequest("READ")
        }
        else{
            Log.i(TAG, "READ Access granted")
        }
        permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        if (permission != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "INTERNET Access denied")
            makeRequest("INTERNET")
        }
        else{
            Log.i(TAG, "INTERNET Access granted")
        }

        permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "WRITE Access denied")
            makeRequest("WRITE")
        }
        else{
            Log.i(TAG, "WRITE Access granted")
        }
    }

    private fun makeRequest(type: String) {
        when (type){
            "READ" -> {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        RECORD_REQUEST_CODE)
            }
            "WRITE" ->{
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        RECORD_REQUEST_CODE)
            }
            "INTERNET" ->{
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.INTERNET),
                        RECORD_REQUEST_CODE)
            }
        }

    }



}
