package com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.support.v4.content.ContextCompat
import com.kapiszewski.mateusz.legoblockcollector.InventoriesPartActivity
import com.kapiszewski.mateusz.legoblockcollector.MyDBHandler
import com.kapiszewski.mateusz.legoblockcollector.R
import com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.ViewHolders.ViewHolder1
import com.kapiszewski.mateusz.legoblockcollector.models.InventoriesPart
import com.kapiszewski.mateusz.legoblockcollector.models.Inventory
import kotlinx.android.synthetic.main.inventory_element.view.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


/**
 * Created by mateu on 5/31/2018.
 */
class InventoryAdapter (val items: ArrayList<Inventory>, val context: Context) : RecyclerView.Adapter<ViewHolder1>() {


    val REQUEST_CODE = 10000

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        val viewholder = ViewHolder1(LayoutInflater.from(context).inflate(R.layout.inventory_element, parent, false))
        return viewholder
    }

    private fun showActivity(position: Int) {
        val i = Intent(context, InventoriesPartActivity::class.java)
        i.putExtra("InventoryId", items.get(position).id)
        ContextCompat.startActivity(context, i, null)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        if (items.get(position).active > 0){
            holder?.view.setBackgroundColor(Color.parseColor("#FAFAFA"))
        }
        else{
            holder?.view.setBackgroundColor(Color.parseColor("#C6C2CA"))
        }
        holder?.inventoryName?.text = "Inventory name: " + items.get(position).name
        holder?.inventoryId?.text = "Inventory id: " + items.get(position).id.toString()
        holder?.view.setOnClickListener {
            showActivity(position)
        }
        holder?.view?.export_button.setOnClickListener {
            val export = exportXML(items.get(position).id)
            export.execute()
        }
    }

    private inner class exportXML(val inventoryId: Int) : AsyncTask<String, Int, String>(){
        override fun doInBackground(vararg params: String?): String {
            val dbHandler = MyDBHandler(context, null, null, 1)
            var inventoriesParts: ArrayList<InventoriesPart> = dbHandler.getInventoriesParts(inventoryId)
            val docBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val doc: Document = docBuilder.newDocument()
            val rootElement: Element = doc.createElement("Inventory")
            val itemElement: Element = doc.createElement("Item")
            rootElement.appendChild(itemElement)

            inventoriesParts.forEach {
                val itemElement: Element = doc.createElement("Item")
                rootElement.appendChild(itemElement)
                val itemType = dbHandler.findTypeCode(it.typeId)
                val itemTypeElement: Element = doc.createElement("ItemType")
                itemTypeElement.appendChild(doc.createTextNode(itemType))
                val itemIdElement: Element = doc.createElement("ItemId")
                itemIdElement.appendChild(doc.createTextNode(it.itemId.toString()))
                val colorElement: Element = doc.createElement("Color")
                colorElement.appendChild(doc.createTextNode(it.colorId.toString()))
                val qtyFilledElement: Element = doc.createElement("QtyFilled")
                qtyFilledElement.appendChild(doc.createTextNode((it.quantityInSet - it.quantityInStore).toString()))

                itemElement.appendChild(itemTypeElement)
                itemElement.appendChild(itemIdElement)
                itemElement.appendChild(colorElement)
                itemElement.appendChild(qtyFilledElement)
            }


            doc.appendChild(rootElement)
            val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2")

            val path = context.filesDir
            val outDir = File(path, "XMLFiles")
            outDir.mkdir()

            val file = File(outDir, inventoryId.toString() + ".xml")
            transformer.transform(DOMSource(doc), StreamResult(file))

            return "Success"
        }
    }
}
