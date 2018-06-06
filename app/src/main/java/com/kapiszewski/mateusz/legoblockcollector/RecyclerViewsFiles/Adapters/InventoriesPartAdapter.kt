package com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import com.kapiszewski.mateusz.legoblockcollector.InventoriesPartActivity
import com.kapiszewski.mateusz.legoblockcollector.MyDBHandler
import com.kapiszewski.mateusz.legoblockcollector.R
import com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.ViewHolders.InventoriesPartViewHolder
import com.kapiszewski.mateusz.legoblockcollector.models.InventoriesPart
import kotlinx.android.synthetic.main.inventories_part_element.view.*
import java.security.AccessController.getContext


/**
 * Created by mateu on 5/31/2018.
 */
class InventoriesPartAdapter (val items : ArrayList<InventoriesPart>, val context: Context) : RecyclerView.Adapter<InventoriesPartViewHolder>() {


    val REQUEST_CODE = 10000

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoriesPartViewHolder {
        val viewholder = InventoriesPartViewHolder(LayoutInflater.from(context).inflate(R.layout.inventories_part_element, parent, false))
        return viewholder
    }

    private fun showActivity(position: Int) {
        val i = Intent(context, InventoriesPartActivity::class.java)
        i.putExtra("InventoryId", items.get(position).id)
        ContextCompat.startActivity(context, i, null)


    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holderInventoriesPart: InventoriesPartViewHolder, position: Int) {
        val dbHandler = MyDBHandler(context, null, null, 1)
        holderInventoriesPart?.inventoryId?.text = "Inventory ID: " +  items.get(position).id.toString()
        holderInventoriesPart?.itemId?.text = "Item ID: " + items.get(position).inventoryId.toString()
        holderInventoriesPart?.quantityInStore?.text = "Quantity in store: " + items.get(position).quantityInStore.toString()
        holderInventoriesPart?.quantityInSet?.text = "Quantity in set: " + items.get(position).quantityInSet.toString()
        holderInventoriesPart?.view.setBackgroundColor(Color.parseColor("#FAFAFA"))
        val bitmap = dbHandler.findImage(items.get(position).itemId)
        holderInventoriesPart?.image?.setImageBitmap(bitmap)
        holderInventoriesPart?.view.addButton.setOnClickListener({
            dbHandler.updateInventoriesPartQuantityInStore(items.get(position).id , 1)
            holderInventoriesPart?.quantityInStore?.text = "Quantity in store: " + dbHandler.findQuantityInStore(items.get(position).id).toString()

            if (dbHandler.findQuantityInSet(items.get(position).id) == dbHandler.findQuantityInStore(items.get(position).id)){
                holderInventoriesPart?.view.setBackgroundColor(Color.parseColor("#C6C2CA"))
            }
        })

        holderInventoriesPart?.view.minusButton.setOnClickListener({
            dbHandler.updateInventoriesPartQuantityInStore(items.get(position).id , -1)
            holderInventoriesPart?.quantityInStore?.text = "Quantity in store: " + dbHandler.findQuantityInStore(items.get(position).id).toString()
            holderInventoriesPart?.view.setBackgroundColor(Color.parseColor("#FAFAFA"))

        })

    }
}

