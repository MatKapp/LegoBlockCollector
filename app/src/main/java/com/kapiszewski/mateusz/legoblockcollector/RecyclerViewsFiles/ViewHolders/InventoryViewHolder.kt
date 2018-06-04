package com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.inventory_element.view.*

/**
 * Created by mateu on 5/31/2018.
 */
class ViewHolder1 (val view: View) : RecyclerView.ViewHolder(view)  {

        val inventoryName: TextView? = view.inventory_name
        val inventoryId: TextView? = view.inventory_id
//        val itemId: TextView? = view.item_id

}
