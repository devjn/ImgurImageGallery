package com.github.devjn.imgurimagegallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.widgets.adapters.ArrayRecyclerAdapter
import java.util.*


/**
 * Created by @author Jahongir on 24-Sep-18
 * devjn@jn-arts.com
 * DialogViewSelection
 */
class DialogViewSelection : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.dialog_view_selection, container, false);
        val list = root.findViewById<RecyclerView>(R.id.list)
        list.apply {
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }

        return root
    }


    class SimpleRecyclerArrayAdapter(pairList: List<IntPair>, private val listener: RecyclerViewItemClickListener?)
        : ArrayRecyclerAdapter<SimpleRecyclerArrayAdapter.IntPair, SimpleRecyclerArrayAdapter.SimpleViewHolder>(pairList) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view_selection, parent, false)
            val holder = SimpleViewHolder(view);
            holder.itemView.setClickable(true)
            holder.itemView.setOnClickListener {
                listener?.onItemClicked(it, holder.adapterPosition)
            }
            return holder
        }

        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            val pair = getItem(position)
            holder.txtView.setText(pair.first)
            holder.imgView.setImageResource(pair.second)
        }


        inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val txtView: TextView = itemView.findViewById(R.id.text)
            val imgView: ImageView = itemView.findViewById(R.id.image)

        }

        class IntPair(val first: Int, val second: Int)

        interface RecyclerViewItemClickListener {
            fun onItemClicked(view: View, position: Int)
        }

        companion object {

            fun newInstance(txtResIds: IntArray, imgResIds: IntArray?, listener: RecyclerViewItemClickListener): SimpleRecyclerArrayAdapter {
                val pairList = ArrayList<IntPair>(txtResIds.size)
                for (i in txtResIds.indices) {
                    pairList.add(IntPair(txtResIds[i], if (imgResIds != null) imgResIds[i] else -1))
                }
                return SimpleRecyclerArrayAdapter(pairList, listener)
            }
        }

    }


}