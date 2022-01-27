package com.tuwaiq.caspstone2.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.caspstone2.R
import com.tuwaiq.caspstone2.data.Message2
import com.tuwaiq.caspstone2.utils.Constants.RECEIVE_ID
import com.tuwaiq.caspstone2.utils.Constants.SEND_ID


class Messaging2Adapter: RecyclerView.Adapter<Messaging2Adapter.MessageViewHolder> (){

    var messageList= mutableListOf<Message2>()



    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var tv_message: TextView
        lateinit var tv_bot_message: TextView
        init {
            tv_message=itemView.findViewById(R.id.tv_message)
            tv_bot_message=itemView.findViewById(R.id.tv_bot_message)
            itemView.setOnClickListener{
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.tv_bot_message.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.tv_message.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    fun insertMessage(message: Message2) {
        this.messageList.add(message)
        notifyItemInserted(this.messageList.size)
    }

}
