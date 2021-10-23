package com.example.practiz1.Entities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiz1.R
import com.example.practiz1.data.DBHandler
import kotlinx.android.synthetic.main.edit_item_layout.*
import kotlinx.android.synthetic.main.edit_item_layout.view.*

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
            val db = DBHandler(holder.itemView.context)
            holder.itemView.setOnClickListener(View.OnClickListener { View ->
                val dlg = AlertDialog.Builder(View.context)
                dlg.setMessage("Выберите действие с товаром \"${item.Name}\"")
                dlg.setNeutralButton("Ничего") { dialog, id ->
                    dialog.cancel()
                }
                dlg.setPositiveButton("Изменить") { dialog, id ->
                    val view = LayoutInflater.from(holder.itemView.context).inflate(R.layout.edit_item_layout, null)
                    val builder = AlertDialog.Builder(holder.itemView.context)
                    builder.setView(view)
                    view.EditNameItem.setText(item.Name)
                    view.EditPriceItem.setText(item.Price.toString())
                    view.EditRateItem.rating = item.Rate.toFloat()
                    builder.setTitle("Изменить товар")
                    builder.setNeutralButton("Отмена", DialogInterface.OnClickListener{ dialog, id ->
                        dialog.cancel()
                    })
                    builder.setPositiveButton("Изменить", DialogInterface.OnClickListener{ dialog, id ->
                        item.Name = view.EditNameItem.text.toString()
                        item.Price = view.EditPriceItem.text.toString().toFloat()
                        item.Rate = view.EditRateItem.rating.toInt()
                        db.updateItem(item)
                        this.items = db.getAllItems()
                        this.notifyDataSetChanged()
                        dialog.cancel()
                    })
                    builder.show()
                    dialog.cancel()
                }
                dlg.setNegativeButton("Удалить") { dialog, id ->
                    db.deleteItem(item)
                    items = db.getAllItems()
                    this.notifyDataSetChanged()
                    dialog.cancel()
                }
                dlg.show()
            })
        }
    }
}