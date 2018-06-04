package com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.ViewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.inventories_part_element.view.*

/**
 * Created by mateu on 6/2/2018.
 */
class InventoriesPartViewHolder(val view: View) : RecyclerView.ViewHolder(view)  {

    val inventoryId: TextView? = view.inventory_id
    val itemId: TextView? = view.item_id
    val quantityInSet: TextView? = view.quantity_in_set
    val quantityInStore: TextView? = view.quantity_in_store
    val image: ImageView? = view.lego_image_view
}
