package com.example.muestraantonelli.ui.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muestraantonelli.databinding.UserItemBinding
import com.example.muestraantonelli.entity.UsersModel
import com.example.muestraantonelli.ui.users.utils.LongClickListener

class UsersAdapter (private var userList: ArrayList<UsersModel>, var longClickListener: LongClickListener) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    var itemEliminado : ArrayList<UsersModel> = ArrayList()
    var viewHolder : ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        viewHolder = ViewHolder(binding, longClickListener)

        return viewHolder!!
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(!itemEliminado.equals(userList[position])){
            with(holder) {
                with(binding) {
                    tvName.text = userList[position].name
                    tvEmail.text = userList[position].email
                    tvPhone.text = userList[position].phone
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(val binding: UserItemBinding, longClickListener: LongClickListener) : RecyclerView.ViewHolder(binding.root) , View.OnLongClickListener {

        private var longListener : LongClickListener? = null

        init{
            this.longListener = longClickListener
            binding.itemBox.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            longListener?.longClick(v!! , adapterPosition, userList[adapterPosition])
            return true
        }
    }
    fun eliminarItem(index : Int){
        itemEliminado.add(userList[index])
        userList.removeAll(itemEliminado.toSet())
        notifyDataSetChanged()
    }
}