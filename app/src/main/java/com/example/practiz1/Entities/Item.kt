package com.example.practiz1.Entities

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiz1.MainActivity
import com.example.practiz1.R
import com.example.practiz1.data.DBHandler
import kotlinx.android.synthetic.main.item_layout.view.*

class Item(var ID:Int, var Name:String, var Price:Float, var Rate:Int) {
    constructor() : this(0, "", 0f, 0) {

    }
    class ItemAdapter(var items: ArrayList<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        class ItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
            var lblItemName: TextView? = null
            var lblItemPrice: TextView? = null
            var rbItemRate: RatingBar? = null
            init {
                lblItemName = itemview.findViewById(R.id.lblItemName)
                lblItemPrice = itemview.findViewById(R.id.lblItemPrice)
                rbItemRate = itemview.findViewById(R.id.rbItemRate)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ItemViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return items.count()
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = items[position]
            holder.rbItemRate?.rating = item.Rate.toFloat()
            holder.lblItemName?.setText(item.Name)
            holder.lblItemPrice?.setText(item.Price.toString())
            holder.itemView.setOnClickListener(View.OnClickListener { View ->
                val dlg = AlertDialog.Builder(View.context)
                dlg.setMessage("Выберите действие с товаром \"${item.Name}\"")
                dlg.setNeutralButton("Ничего") { dialog, id ->
                    dialog.cancel()
                }
                dlg.setPositiveButton("Изменить") { dialog, id ->
                    dialog.cancel()
                }
                dlg.setNegativeButton("Удалить") { dialog, id ->
                    DBHandler.
                    dialog.cancel()
                }
                dlg.show()
            })
        }
    }

}