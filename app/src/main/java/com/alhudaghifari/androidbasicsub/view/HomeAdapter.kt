package com.alhudaghifari.androidbasicsub.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.androidbasicsub.ImageUtils
import com.alhudaghifari.androidbasicsub.R
import com.alhudaghifari.androidbasicsub.model.CarItem

class HomeAdapter(internal var context: Context?, internal var data: MutableList<CarItem>) :
    RecyclerView.Adapter<HomeAdapter.ArticleViewHolder>() {

    private var onCarClickListener: OnCarClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = data[position]

        val price = "Price : $${item.avgPrice.toString()}"
        val horsePower = "Horse Power : ${item.avgHorsepower.toString() ?: "-"}"

        holder.tvTitle.text = item.name ?: "-"
        holder.tvPrice.text = price
        holder.tvHorsePower.text = horsePower

        ImageUtils().loadImage(context!!, holder.ivImage, item.imgUrl)

        holder.conlayParentCar.setOnClickListener {
            if (onCarClickListener != null) {
                onCarClickListener!!.onClick(position, item)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addNewData(data: List<CarItem>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setNewData(data: MutableList<CarItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ArticleViewHolder(mViewContainer: View) : RecyclerView.ViewHolder(mViewContainer) {
        internal var conlayParentCar: ConstraintLayout

        internal var ivImage: ImageView
        internal var tvTitle: TextView
        internal var tvPrice: TextView
        internal var tvHorsePower: TextView

        init {
            conlayParentCar = mViewContainer.findViewById(R.id.conlayParentCar)
            ivImage = mViewContainer.findViewById(R.id.ivImage)
            tvTitle = mViewContainer.findViewById(R.id.tvTitle)
            tvPrice = mViewContainer.findViewById(R.id.tvPrice)
            tvHorsePower = mViewContainer.findViewById(R.id.tvHorsePower)
        }
    }

    interface OnCarClickListener {
        fun onClick(position: Int, itemCar: CarItem)
    }

    fun setOnCarClickListener(onCarClickListener: OnCarClickListener) {
        this.onCarClickListener = onCarClickListener
    }
}