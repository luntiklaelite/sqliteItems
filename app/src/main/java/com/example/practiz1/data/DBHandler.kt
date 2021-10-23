package com.example.practiz1.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.practiz1.Entities.Item
import com.example.practiz1.R

class DBHandler(context: Context) : SQLiteOpenHelper(context, DBHandler.DB_NAME,null,  DBHandler.DB_VERSION) {
    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "my_test_app.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE items (" +
            "item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "item_name TEXT NOT NULL," +
            "item_rate INTEGER NOT NULL DEFAULT 0," +
            "item_price REAL NOT NULL DEFAULT 0);")
        /*addItem(Item(0, "Апельсин", 120f, 5))
        addItem(Item(0, "Яблоко", 12f, 5))
        addItem(Item(0, "Банан", 50f, 2))
        addItem(Item(0, "Мультифрукт", 99999f, 5))*/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getAllItems() : ArrayList<Item> {
        val itemList = ArrayList<Item>()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM items"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val item = Item()
                    item.ID = cursor.getInt(cursor.getColumnIndex("item_id"))
                    item.Name = cursor.getString(cursor.getColumnIndex("item_name"))
                    item.Rate = cursor.getInt(cursor.getColumnIndex("item_rate"))
                    item.Price = cursor.getFloat(cursor.getColumnIndex("item_price"))
                    itemList.add(item)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return itemList
    }

    fun addItem(item: Item) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("item_name", item.Name)
        values.put("item_rate", item.Rate)
        values.put("item_price", item.Price)
        db.insert("items", null, values)
        db.close()
    }
    fun deleteItem(item: Item) {
        val db = this.writableDatabase
        db.delete("items", "item_id = ?", arrayOf(item.ID.toString()))
        db.close()
    }
    fun updateItem(item: Item) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("item_name", item.Name)
        values.put("item_rate", item.Rate)
        values.put("item_price", item.Price)
        db.update("items", values, "item_id = ?", arrayOf(item.ID.toString()))
        db.close()
    }
}