package com.kapiszewski.mateusz.legoblockcollector

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import com.kapiszewski.mateusz.legoblockcollector.models.InventoriesPart
import com.kapiszewski.mateusz.legoblockcollector.models.Inventory


/**
 * Created by mateu on 5/31/2018.
 */
class InventoriesPartAdapter (val items : ArrayList<InventoriesPart>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    val REQUEST_CODE = 10000

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewholder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
        return viewholder
    }

    private fun showActivity(position: Int) {
        val i = Intent(context, InventoriesPartActivity::class.java)
        i.putExtra("InventoryId", items.get(position).id)
        ContextCompat.startActivity(context, i, null)


    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.inventoryId?.text = items.get(position).id.toString()
        holder?.itemId?.text = items.get(position).inventoryId.toString()
        holder?.view.setOnClickListener { showActivity(position) }
    }
}

