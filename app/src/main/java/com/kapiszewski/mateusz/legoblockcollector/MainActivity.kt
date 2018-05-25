package com.kapiszewski.mateusz.legoblockcollector

import android.Manifest
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions()
        readXML()
    }

    private val TAG = "storage permission"
    private val RECORD_REQUEST_CODE = 101

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "Access denied")
            makeRequest()
        }
        else{
            Log.i(TAG, "Access granted")
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RECORD_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun readXML(): String? {
        val xlmFile: File = File("/storage/sdcard0/legoBlock/615.xml")
        val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xlmFile)

        xmlDoc.documentElement.normalize()

        println("Root Node:" + xmlDoc.documentElement.nodeName)

        val itemList: NodeList = xmlDoc.getElementsByTagName("ITEM")

        for(i in 0..itemList.length - 1)
        {
            var itemNode: Node = itemList.item(i)

            if (itemNode.getNodeType() === Node.ELEMENT_NODE) {

                val elem = itemNode as Element


                val mMap = mutableMapOf<String, String>()


                for(j in 0..elem.attributes.length - 1)
                {
                    mMap.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
                }
                //println("Current Book : ${itemNode.nodeName} - $mMap")

                println("ITEMTYPE: ${elem.getElementsByTagName("ITEMTYPE").item(0).textContent}")
                println("ITEMID: ${elem.getElementsByTagName("ITEMID").item(0).textContent}")
                println("QTY: ${elem.getElementsByTagName("QTY").item(0).textContent}")
                println("COLOR: ${elem.getElementsByTagName("COLOR").item(0).textContent}")
                println("EXTRA: ${elem.getElementsByTagName("EXTRA").item(0).textContent}")
                println("ALTERNATE: ${elem.getElementsByTagName("ALTERNATE").item(0).textContent}")
            }
        }
        return "success"
    }



    private inner class ItemsDownloader : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }


        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: String?): String? {
            println("I am in do in background")
            val xlmFile: File = File("/storage/sdcard0/legoBlock/615.xml")
            val url = "http://fcds.cs.put.poznan.pl/MyWeb/BL/615.xml"
            val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xlmFile)

            xmlDoc.documentElement.normalize()

            println("Root Node:" + xmlDoc.documentElement.nodeName)

            val itemList: NodeList = xmlDoc.getElementsByTagName("item")

            for(i in 0..itemList.length - 1)
            {
                var itemNode: Node = itemList.item(i)

                if (itemNode.getNodeType() === Node.ELEMENT_NODE) {

                    val elem = itemNode as Element


                    val mMap = mutableMapOf<String, String>()


                    for(j in 0..elem.attributes.length - 1)
                    {
                        mMap.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
                    }
                    println("Current Book : ${itemNode.nodeName} - $mMap")

                    println("ITEMTYPE: ${elem.getElementsByTagName("ITEMTYPE").item(0).textContent}")
                    println("ITEMID: ${elem.getElementsByTagName("ITEMID").item(0).textContent}")
                    println("QTY: ${elem.getElementsByTagName("QTY").item(0).textContent}")
                    println("COLOR: ${elem.getElementsByTagName("COLOR").item(0).textContent}")
                    println("EXTRA: ${elem.getElementsByTagName("EXTRA").item(0).textContent}")
                    println("ALTERNATE: ${elem.getElementsByTagName("ALTERNATE").item(0).textContent}")
                }
            }
            return "success"
        }

    }
}
