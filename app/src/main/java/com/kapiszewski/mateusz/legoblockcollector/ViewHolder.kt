package com.kapiszewski.mateusz.legoblockcollector

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by mateu on 5/31/2018.
 */
class ViewHolder (val view: View) : RecyclerView.ViewHolder(view)  {

        val inventoryName: TextView? = view.inventory_name
        val inventoryId: TextView? = view.inventory_id
        val itemId: TextView? = view.item_id

}
