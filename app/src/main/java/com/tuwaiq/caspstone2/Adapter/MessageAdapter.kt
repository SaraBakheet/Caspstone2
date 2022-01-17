package com.tuwaiq.caspstone2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

import com.tuwaiq.caspstone2.R

class MessageAdapter(val context:Context,val messageList:ArrayList<Message>):
    RecyclerView.Adapter<ViewHolder>(){

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==1){
            //infate receive
            val view: View =LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)

        } else{
            //inflate sent
            val view: View =LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage= messageList[position]

        if (holder.javaClass==SentViewHolder::class.java){
            //do the stuff for sent view holder

            val viewHolder=holder as SentViewHolder
            holder.sentMessage.text=currentMessage.message
        } else{
            //do the stuff for receive view holder
            val viewHolder= holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }


    }
    //everything is ok ? ^_^
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser!!.uid.equals(currentMessage.sendId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size

    }
    class SentViewHolder(itemView: View): ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)

    }
    class ReceiveViewHolder(itemView: View): ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)

    }
}