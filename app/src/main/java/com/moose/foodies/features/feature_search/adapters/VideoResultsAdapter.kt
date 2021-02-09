package com.moose.foodies.features.feature_search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moose.foodies.databinding.VideoResultItemBinding
import com.moose.foodies.features.feature_search.adapters.VideoResultsAdapter.VideoViewHolder
import com.moose.foodies.features.feature_search.domain.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoResultsAdapter(private val videos: List<Video>): Adapter<VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        with(holder.binding){
            videoName.text = video.shortTitle
            youtubePlayer.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(video.youTubeId, 0f)
                }
            })
        }
    }

    override fun getItemCount(): Int = videos.size

    class VideoViewHolder(val binding: VideoResultItemBinding): ViewHolder(binding.root)
}