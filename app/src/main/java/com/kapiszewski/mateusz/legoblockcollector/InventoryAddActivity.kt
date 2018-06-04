package com.kapiszewski.mateusz.legoblockcollector

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kapiszewski.mateusz.legoblockcollector.models.InventoriesPart
import com.kapiszewski.mateusz.legoblockcollector.models.Inventory
import com.kapiszewski.mateusz.legoblockcollector.models.Singleton
import kotlinx.android.synthetic.main.activity_add_inventory.*
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
import android.graphics.Bitmap
import android.net.Uri
import com.squareup.picasso.Picasso


class InventoryAddActivity : AppCompatActivity() {

    val dbHandler = MyDBHandler(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventory)
        addInventoryButton.setOnClickListener {

            if (!dbHandler.inventoryIdExists(Integer.parseInt(idEditText.text.toString()))){
                val inventory = Inventory(Integer.parseInt(idEditText.text.toString()), nameEditText.text.toString(), Integer.parseInt(activeEditText.text.toString()), (System.currentTimeMillis() / 1000 / 60).toInt())
                dbHandler.deleteInventoriesParts(inventory.id)
                downloadData(inventory.id)
                dbHandler.addInventory(inventory)
                this.finish()
            }
        }
    }


    fun downloadData(inventoryId: Int){
        val itemsDownloader = ItemsDownloader(inventoryId)
        itemsDownloader.execute()
    }


    private inner class ItemsDownloader(val inventoryId: Int) : AsyncTask<String, Int, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//            insertInventoriesParts()
        }

        var inventoriesParts:MutableList<InventoriesPart>? = null


        fun loadData() {
            var localInventoriesParts: MutableList<InventoriesPart> = mutableListOf()
            inventoriesParts = mutableListOf()
            val filename = "inventoriesParts.xml"
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
                            var inventoriesPart:MutableList<InventoriesPart>? = null
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
                                val inventoriesPart = InventoriesPart(inventoryId, dbHandler.findTypeId(itemType)
                                                                    , dbHandler.findPartId(itemId), Integer.parseInt(qty)
                                                                    , 0, Integer.parseInt(color), extra)
                                dbHandler.addInventoriesPart(inventoriesPart)
                                addImageToInventoriesPart(inventoriesPart.id)
                                localInventoriesParts?.add(inventoriesPart)
                            }
                        }
                    }
                }
            }
            inventoriesParts = localInventoriesParts

        }


        override fun doInBackground(vararg params: String?): String? {
            try{

                val url = URL(Singleton.URL + inventoryId.toString()  + ".xml")
                val connection = url.openConnection()
                connection.connect()
                val lendthOfFile = connection.contentLength
                val isStream = url.openStream()
                val testDirectory = File("$filesDir/XML")
                if (!testDirectory.exists()){
                    testDirectory.mkdir()
                }
                val fos = FileOutputStream("$testDirectory/inventoriesParts.xml")
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
                loadData()


            }catch (e: MalformedURLException){
                return "Malformed URL"
            }catch (e: FileNotFoundException){
                return "File not found Exception"
            }catch (e: IOException){
                return "IO exception"
            }

            return "success"
        }

    }

    private fun addImageToInventoriesPart(inventoriesPartId: Int) {
        try{
            val dbHandler = MyDBHandler(this, null, null, 1)
            val code = dbHandler.findInventoriesPartCode(inventoriesPartId)
            val bitmap = loadImageUsingPicasso("https://www.lego.com/service/bricks/5/2/" + code.toString())
            dbHandler.addImage(code, bitmap)
        }
        catch (e: Exception){
            println(e.message)
        }

    }

    private fun loadImageUsingPicasso(imageUrl: String): Bitmap {

        var bitmap = Picasso.with(this)
                .load(Uri.parse(imageUrl))
                .get()


        return bitmap
    }

}
