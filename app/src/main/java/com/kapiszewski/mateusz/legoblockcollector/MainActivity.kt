package com.kapiszewski.mateusz.legoblockcollector

import android.Manifest
import android.content.ClipData
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
import android.view.View
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory
import com.kapiszewski.mateusz.legoblockcollector.models.*
import com.kapiszewski.mateusz.legoblockcollector.MyDBHandler

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions()
//        downloadData()
        val inventoriesPart = InventoriesPart(2,3,4,2,5,3,4)
        val dbHandler = MyDBHandler(this, null, null, 1)
        dbHandler.addInventoriesPart(inventoriesPart)

    }

    private val TAG = "storage permission"
    private val RECORD_REQUEST_CODE = 101
    var items:MutableList<Item>? = null

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

    @RequiresApi(Build.VERSION_CODES.N)

    fun downloadData(){
        val id = ItemsDownloader()
        id.execute()
    }

    fun refresh(v: View){
        downloadData()
    }



    private inner class ItemsDownloader : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            loadData()
            showData()
        }

        fun loadData() {
            var localItems: MutableList<Item> = mutableListOf()
            items = mutableListOf()
            val filename = "items.xml"
            val path = filesDir
            val inDir = File(path, "XML")
            if (inDir.exists()){
                val file = File(inDir, filename)
                if (file.exists()){
                    val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)

                    xmlDoc.documentElement.normalize()

                    val itemList: NodeList = xmlDoc.getElementsByTagName("ITEM")

                    for(i in 0..itemList.length - 1)
                    {
                        var itemNode: Node = itemList.item(i)

                        if (itemNode.getNodeType() === Node.ELEMENT_NODE) {
                            var items:MutableList<Item>? = null
                            val elem = itemNode as Element
                            val children = elem.childNodes
                            var itemType:String? = null
                            var itemId:String? = null
                            var qty:String? = null
                            var color:String? = null
                            var extra:String? = null
                            var alternate:String? = null
                            var matchId:String? = null
                            var counterPart:String? = null

                            for(j in 0..children.length - 1)
                            {
                                val node = children.item(j)
                                if (node is Element){
                                    when (node.nodeName){
                                        "ITEMTYPE" -> {itemType = node.textContent}
                                        "ITEMID" -> {itemId = node.textContent}
                                        "QTY" -> {qty = node.textContent}
                                        "COLOR" -> {color = node.textContent}
                                        "EXTRA" -> {extra = node.textContent}
                                        "ALTERNATE" -> {alternate = node.textContent}
                                        "MATCHID" -> {matchId = node.textContent}
                                        "COUNTERPART" ->{counterPart = node.textContent}

                                    }
                                }
                            }

                            if (itemId != null && qty != "0" && qty != null){
                                val item = Item(itemType, itemId, qty.toInt(), color?.toInt(), extra, alternate, matchId, counterPart)
                                localItems?.add(item)
                            }
                        }
                    }
                }
            }
            items = localItems

        }


        override fun doInBackground(vararg params: String?): String? {
            try{

                val url = URL("http://fcds.cs.put.poznan.pl/MyWeb/BL/615.xml")
                val connection = url.openConnection()
                connection.connect()
                val lendthOfFile = connection.contentLength
                val isStream = url.openStream()
                val testDirectory = File("$filesDir/XML")
                if (!testDirectory.exists()){
                    testDirectory.mkdir()
                }
                val fos = FileOutputStream("$testDirectory/items.xml")
                val data = ByteArray(1024)
                var count = 0
                var total: Long = 0
                var progress = 0
                count = isStream.read(data)
                while (count != -1){
                    total += count.toLong()
                    val progressTemp = total.toInt() * 100 / lendthOfFile

                    if (progressTemp % 10 == 0 && progress != progressTemp){
                        progress = progressTemp
                    }
                    fos.write(data, 0, count)
                    count = isStream.read(data)
                }
                isStream.close()
                fos.close()

            }catch (e:MalformedURLException){
                return "Malformed URL"
            }catch (e:FileNotFoundException){
                return "File not found Exception"
            }catch (e:IOException){
                return "IO exception"
            }

            return "success"
        }

    }

    private fun showData() {
        Log.i(TAG, items?.size.toString())
    }
}
