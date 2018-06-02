package com.kapiszewski.mateusz.legoblockcollector

/**
 * Created by mateu on 5/28/2018.
 */
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import com.kapiszewski.mateusz.legoblockcollector.models.*

class MyDBHandler(context: Context, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_INVENTORIESPARTS = ("CREATE TABLE IF NOT EXISTS " +
                TABLE_INVENTORIESPARTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_INVENTORYID + " INTEGER,"
                + COLUMN_TYPEID + " INTEGER,"
                + COLUMN_ITEMID + " INTEGER,"
                + COLUMN_QUANTITYINSET + " INTEGER,"
                + COLUMN_QUANTITYINSTORE + " INTEGER,"
                + COLUMN_EXTRA + " INTEGER" + ")")
        db.execSQL(CREATE_INVENTORIESPARTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                            newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORIESPARTS)
        onCreate(db)
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "lego.db"
        val TABLE_INVENTORIESPARTS = "InventoriesParts"
        val TABLE_INVENTORIES = "Inventories"
        val TABLE_ITEMTYPES = "ItemTypes"
        val TABLE_PARTS = "Parts"

        val COLUMN_ID = "id"
        val COLUMN_INVENTORYID = "InventoryID"
        val COLUMN_TYPEID = "TypeID"
        val COLUMN_ITEMID = "ItemID"
        val COLUMN_QUANTITYINSET = "QuantityInSet"
        val COLUMN_QUANTITYINSTORE = "QuantityInStore"
        val COLUMN_COLORID = "ColorID"
        val COLUMN_EXTRA = "Extra"
        val COLUMN_NAME = "Name"
        val COLUMN_ACTIVE = "Active"
        val COLUMN_LASTACCESSED = "LastAccessed"
        val COLUMN_CODE = "Code"
    }

    fun addInventoriesPart(inventoriesPart: InventoriesPart) {

        val values = ContentValues()
        //values.put(COLUMN_ID, inventoriesPart.id)
        values.put(COLUMN_INVENTORYID, inventoriesPart.inventoryId)
        values.put(COLUMN_TYPEID, inventoriesPart.typeId)
        values.put(COLUMN_ITEMID, inventoriesPart.itemId)
        values.put(COLUMN_QUANTITYINSET, inventoriesPart.quantityInSet)
        values.put(COLUMN_QUANTITYINSTORE, inventoriesPart.quantityInStore)
        values.put(COLUMN_COLORID, inventoriesPart.colorId)
        values.put(COLUMN_EXTRA, inventoriesPart.extra)

        val db = this.writableDatabase

        db.beginTransaction()

        db.insert(TABLE_INVENTORIESPARTS, null, values)

        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }

    fun addInventory(inventory: Inventory) {

        val values = ContentValues()

        values.put(COLUMN_ID, inventory.id)
        values.put(COLUMN_NAME, inventory.name)
        values.put(COLUMN_ACTIVE, inventory.active)
        values.put(COLUMN_LASTACCESSED, inventory.lastAccessed)

        val db = this.writableDatabase
        db.beginTransaction()

        db.insert(TABLE_INVENTORIES, null, values)

        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }

    fun findInventoriesPart(inventoriesPartID: Int): InventoriesPart? {
        val query =
                "SELECT * FROM $TABLE_INVENTORIESPARTS WHERE $COLUMN_ID =  \"$inventoriesPartID\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var inventoriesPart: InventoriesPart? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val inventoryID = Integer.parseInt(cursor.getString(1))
            val typeId = Integer.parseInt(cursor.getString(2))
            val itemId = Integer.parseInt(cursor.getString(3))
            val quantityInSet = Integer.parseInt(cursor.getString(4))
            val quantityInStore = Integer.parseInt(cursor.getString(5))
            val colorId = Integer.parseInt(cursor.getString(6))
            val extra = cursor.getString(7)

            inventoriesPart = InventoriesPart(id, inventoryID, typeId, itemId, quantityInSet, quantityInStore, colorId, extra)
            cursor.close()
        }

        db.close()
        return inventoriesPart
    }

    fun findInventory(inventoryName: String): Inventory? {
        val query =
                "SELECT * FROM $TABLE_INVENTORIES WHERE $COLUMN_NAME =  \"$inventoryName\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var inventory: Inventory? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val active = Integer.parseInt(cursor.getString(2))
            val lastAccessed = Integer.parseInt(cursor.getString(3))

            inventory = Inventory(id, name, active, lastAccessed)
            cursor.close()
        }

        db.close()
        return inventory
    }

    fun findTypeId (typeCode: String?): Int {
        val query =
                "SELECT $COLUMN_ID FROM $TABLE_ITEMTYPES WHERE $COLUMN_CODE =  \"$typeCode\""
        var itemTypeId = 0
        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            itemTypeId = Integer.parseInt(cursor.getString(0))
            cursor.close()
        }

        db.close()
        return itemTypeId
    }

    fun findPartId (partCode: String?): Int {
        val query =
                "SELECT $COLUMN_ID FROM $TABLE_PARTS WHERE $COLUMN_CODE =  \"$partCode\""
        var partId = 0
        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            partId = Integer.parseInt(cursor.getString(0))
            cursor.close()
        }

        db.close()
        return partId
    }

    fun getInventories(): ArrayList<Inventory>{
        val inventories: ArrayList<Inventory> = ArrayList()
        val query = "SELECT * FROM $TABLE_INVENTORIES"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var inventory: Inventory? = null

        if (cursor.moveToFirst()){
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val active = Integer.parseInt(cursor.getString(2))
                val lastAccessed = Integer.parseInt(cursor.getString(3))
                inventory = Inventory(id, name, active, lastAccessed)
                inventories.add(inventory)
            }while (cursor.moveToNext())
            cursor.close()
        }
        db.close()
        return inventories
    }

    fun getInventoriesParts(inventoryId: Int): ArrayList<InventoriesPart> {
            val inventoriesParts: ArrayList<InventoriesPart> = ArrayList()
            val query = "SELECT * FROM $TABLE_INVENTORIESPARTS where $COLUMN_INVENTORYID =  \"$inventoryId\""
            val db = this.writableDatabase
            val cursor = db.rawQuery(query, null)
            var inventoriesPart: InventoriesPart? = null

            if (cursor.moveToFirst()){
                do {
                    val id = Integer.parseInt(cursor.getString(0))
                    val inventoryID = Integer.parseInt(cursor.getString(1))
                    val typeId = Integer.parseInt(cursor.getString(2))
                    val itemId = Integer.parseInt(cursor.getString(3))
                    val quantityInSet = Integer.parseInt(cursor.getString(4))
                    val quantityInStore = Integer.parseInt(cursor.getString(5))
                    val colorId = Integer.parseInt(cursor.getString(6))
                    val extra = cursor.getString(7)

                    inventoriesPart = InventoriesPart(id, inventoryID, typeId, itemId, quantityInSet, quantityInStore, colorId, extra)

                    inventoriesParts.add(inventoriesPart)
                }while (cursor.moveToNext())
                cursor.close()
            }
            db.close()
            return inventoriesParts
        }

    fun deleteInventoriesParts(inventoryId: Int){
        val query = "DELETE FROM $TABLE_INVENTORIESPARTS where $COLUMN_INVENTORYID =  \"$inventoryId\""
        val db = this.writableDatabase
        db.beginTransaction()
        db.execSQL(query)
        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }

    fun inventoryIdExists(inventoryId: Int): Boolean {
        val query = "SELECT * FROM $TABLE_INVENTORIES where $COLUMN_ID =  \"$inventoryId\""
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        return cursor.moveToFirst()
    }
}
