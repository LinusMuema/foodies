package com.moose.foodies.features.feature_search.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.databinding.PagerLayoutBinding
import com.moose.foodies.features.feature_search.adapters.VideoResultsAdapter
import com.moose.foodies.features.feature_search.domain.Video
import com.moose.foodies.models.onError
import com.moose.foodies.models.onSuccess
import com.moose.foodies.util.hideBottomBar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class VideosFragment : Fragment(R.layout.pager_layout) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: PagerLayoutBinding

    override fun onAttach(context: Context) {
        this.requireActivity().hideBottomBar()
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(SearchViewModel::class.java)
        binding = PagerLayoutBinding.bind(view)

        viewModel.videos.observe(viewLifecycleOwner, { result ->
            result.onSuccess { updateUi(it) }
            result.onError { Log.e(this.javaClass.name, "onViewCreated: $it") }
        })
    }

    private fun updateUi(videos: List<Video>) {
        if (videos.isEmpty()) {
            binding.errorLayout.message.text = resources.getString(R.string.not_found, "videos")
            binding.root.transitionToState(R.id.error)
        }
        else {
            Log.d("Foodies", "updateUi: videos === $videos")
            binding.root.transitionToState(R.id.end)
            binding.recyclerView.apply {
                setHasFixedSize(true)
                adapter = VideoResultsAdapter(videos)
            }
        }
    }
}