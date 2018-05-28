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

        val COLUMN_ID = "id"
        val COLUMN_INVENTORYID = "InventoryID"
        val COLUMN_TYPEID = "TypeID"
        val COLUMN_ITEMID = "ItemID"
        val COLUMN_QUANTITYINSET = "QuantityInSet"
        val COLUMN_QUANTITYINSTORE = "QuantityInStore"
        val COLUMN_EXTRA = "Extra"
    }

    fun addInventoriesPart(inventoriesPart: InventoriesPart) {

        val values = ContentValues()
        values.put(COLUMN_INVENTORYID, inventoriesPart.inventoryId)
        values.put(COLUMN_TYPEID, inventoriesPart.typeId)
        values.put(COLUMN_ITEMID, inventoriesPart.itemId)
        values.put(COLUMN_QUANTITYINSET, inventoriesPart.quantityInSet)
        values.put(COLUMN_QUANTITYINSTORE, inventoriesPart.quantityInStore)
        values.put(COLUMN_EXTRA, inventoriesPart.extra)

        val db = this.writableDatabase

        db.insert(TABLE_INVENTORIESPARTS, null, values)
        db.close()
    }
}