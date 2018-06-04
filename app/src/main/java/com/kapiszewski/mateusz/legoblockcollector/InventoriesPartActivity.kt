package com.kapiszewski.mateusz.legoblockcollector

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kapiszewski.mateusz.legoblockcollector.RecyclerViewsFiles.Adapters.InventoriesPartAdapter
import com.kapiszewski.mateusz.legoblockcollector.models.InventoriesPart
import kotlinx.android.synthetic.main.activity_inventories_part.*

class InventoriesPartActivity : AppCompatActivity() {

    var inventoriesParts: ArrayList<InventoriesPart> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventories_part)
        val inventoryId = Integer.parseInt(this.intent.extras.get("InventoryId").toString())
        val dbHandler = MyDBHandler(this, null, null, 1)
        inventoriesParts = dbHandler.getInventoriesParts(inventoryId)
        inventories_part_list.layoutManager = LinearLayoutManager(this)
        inventories_part_list.adapter = InventoriesPartAdapter(inventoriesParts, this)
    }
}
