package com.moose.foodies.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.Video
import com.moose.foodies.util.HeightCalculator
import com.moose.foodies.util.setHeight
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.video_result_item.view.*

class VideoResultsAdapter(private val results: List<Video>): RecyclerView.Adapter<VideoResultsAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder = VideoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.video_result_item, parent, false)
    )

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) = holder.bind(results[position])

    class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val scale: Float = itemView.context.resources.displayMetrics.density
        private val pixels = (HeightCalculator.getImageHeight(itemView.context) * scale + 0.5f).toInt()

        fun bind(video: Video) {
            itemView.video_name.text = video.shortTitle
            itemView.youtube_player.setHeight(pixels)
            itemView.youtube_player.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(video.youTubeId, 0f)
                }
            })
        }

    }
}