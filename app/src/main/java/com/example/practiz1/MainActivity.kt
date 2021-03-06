package com.example.practiz1

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiz1.Entities.Item
import com.example.practiz1.data.DBHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_item_layout.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val db = DBHandler(this)
        var items: ArrayList<Item> = db.getAllItems()

        recyclerView.adapter = Item.ItemAdapter(items)
        buttonAddItem.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.edit_item_layout, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            builder.setTitle("Добавить товар")
            builder.setNeutralButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            builder.setPositiveButton("Добавить", DialogInterface.OnClickListener{ dialog, id ->
                val item: Item = Item()
                item.Name = view.EditNameItem.text.toString()
                item.Price = view.EditPriceItem.text.toString().toFloat()
                item.Rate = view.EditRateItem.rating.toInt()
                if(item.Name.isEmpty()) {
                    Toast.makeText(this, "Введите название товара!", Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }
                db.addItem(item)
                items.add(item)
                (recyclerView.adapter as Item.ItemAdapter).notifyItemInserted(items.indexOf(item))
                dialog.cancel()
            })
            builder.show()
        }
    }

}