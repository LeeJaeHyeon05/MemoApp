package com.example.memoapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MemoAdapter(private val context: Context, var list: List<MemoEntity>, var onDeleteListener: OnDeleteListener) : RecyclerView.Adapter<MemoAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.memo_item, parent, false)

        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MemoAdapter.ViewHolder, position: Int) {

        val memo = list[position]

        holder.memo.text = memo.memo
        holder.deleteBtn.setOnClickListener {
            val dialogBuilder = android.app.AlertDialog.Builder(context)
            //메세지, 아이콘, 다이얼 로그 외부 터치 캔슬 막기, 취소/계속 버튼
            dialogBuilder.setMessage(R.string.checkText)
                .setCancelable(false)
                .setNegativeButton(R.string.cancelText, DialogInterface.OnClickListener { _, _ ->

                })
                .setPositiveButton(R.string.nextDialogText, DialogInterface.OnClickListener { _, _ ->
                    onDeleteListener.onDeleteListener(memo)

                })

            val alert = dialogBuilder.create()
            alert.setTitle(R.string.checkText)
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val memo = itemView.findViewById<TextView>(R.id.nameTextView)
        val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteBtn)

    }
}