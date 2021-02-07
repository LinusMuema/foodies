package com.moose.foodies.features.feature_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.moose.foodies.R
import com.moose.foodies.util.hide
import com.moose.foodies.util.hideBottomBar
import com.moose.foodies.util.show
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_404.*
import kotlinx.android.synthetic.main.fragment_videos.*
import javax.inject.Inject


class VideosFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel

    override fun onAttach(context: Context) {
        this.requireActivity().hideBottomBar()
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.videos.observe(viewLifecycleOwner, {
            videos_loading.hide()
            if (it.isEmpty()) {
                not_found.text = this.resources.getString(R.string.not_found, "recipes")
                error_layout.show()
            }
            videos_recycler.apply {
                setHasFixedSize(true)
                adapter = VideoResultsAdapter(it)
            }
        })
    }
}