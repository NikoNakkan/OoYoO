package com.softeng.ooyoo.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.helpers.dateMapToString
import com.softeng.ooyoo.helpers.toast

class ReviewAdapter(val context: Context, val ratings: ArrayList<Rating>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_list_item, parent, false)

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.reviewUserName.text = ratings[position].name

        holder.reviewText.text = ratings[position].comment

        val rating = ratings[position].stars.toString() + "/5.0"
        holder.ratingTextView.text = rating

        Glide.with(context).load(R.drawable.logo).centerCrop().circleCrop().into(holder.reviewUserImageView)

    }

    override fun getItemCount() = ratings.size


    class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val reviewUserImageView: ImageView = itemView.findViewById(R.id.review_user_image_view)
        val reviewUserName: TextView = itemView.findViewById(R.id.username_of_reviewer)
        val reviewText: TextView = itemView.findViewById(R.id.review_text)
        val ratingTextView: TextView = itemView.findViewById(R.id.rating_text_view)
    }


}