package com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.kapiszewski.mateusz.legoblockcollector.InventoriesPartActivity
import com.kapiszewski.mateusz.legoblockcollector.MyDBHandler
import com.kapiszewski.mateusz.legoblockcollector.R
import com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.ViewHolders.ViewHolder1
import com.kapiszewski.mateusz.legoblockcollector.models.Inventory


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

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        holder?.view.setBackgroundColor(Color.parseColor("#FAFAFA"))
        holder?.inventoryName?.text = items.get(position).name
        holder?.inventoryId?.text = items.get(position).id.toString()
        holder?.view.setOnClickListener {
            showActivity(position)
        }
    }
}
