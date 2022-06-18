package com.example.githubsearchapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearchapp.databinding.ItemRepoBinding
import com.example.githubsearchapp.model.Repo

/**
 * Adapter for rendering users list in a RecyclerView.
 */

typealias RepoClickListener =  (Repo) -> Unit

class ReposAdapter(private val clickListener:RepoClickListener) : PagingDataAdapter<Repo, ReposAdapter.Holder>(ReposDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val repo = getItem(position) ?: return
        holder.binding.repo = repo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.binding.root.setOnClickListener{ clickListener.invoke(getItem(position)!!)}
    }

    class Holder(
        val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}


@BindingAdapter("load_image")
fun loadImage(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        // dummy img
        Glide.with(view)
            .load("https://github.githubassets.com/images/modules/open_graph/github-mark.png")
            .into(view)
    } else {
        Glide.with(view)
            .load(url)
            .into(view)
    }
}

// ---

class ReposDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}
