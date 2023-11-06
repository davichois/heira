package com.heira.app.presentation.adapter.route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heira.app.R
import com.heira.app.domain.model.RouteModel

class RouteAdapter(
    private val routeList: List<RouteModel>,
    private val onSelectRoute: (String) -> Unit
): RecyclerView.Adapter<RouteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RouteViewHolder(layoutInflater.inflate(R.layout.item_route, parent, false))
    }

    override fun getItemCount(): Int = routeList.size

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        val item = routeList[position]
        holder.render(item, onSelectRoute)
    }


}