package com.example.practiz1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiz1.Entities.Item
import com.example.practiz1.data.DBHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val db = DBHandler(this)
        db.addItem(Item(0, "Апельсин", 120f, 5))
        db.addItem(Item(0, "Яблоко", 12f, 5))
        db.addItem(Item(0, "Банан", 50f, 2))
        db.addItem(Item(0, "Мультифрукт", 99999f, 5))
        var items: ArrayList<Item> = db.getAllItems()

        recyclerView.adapter = Item.ItemAdapter(items)

    }
}