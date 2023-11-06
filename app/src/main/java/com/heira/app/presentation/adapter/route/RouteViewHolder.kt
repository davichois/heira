package com.heira.app.presentation.adapter.route

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.heira.app.databinding.ItemRouteBinding
import com.heira.app.domain.model.RouteModel

class RouteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemRouteBinding.bind(view)

    fun render(item: RouteModel, onSelectRoute: (String) -> Unit) {

        binding.tvLikes.text ="Likes: ${item.likes}"

        itemView.setOnClickListener {
            onSelectRoute(item.id.toString())
        }

    }

}