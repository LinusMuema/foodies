package com.moose.foodies.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class VideosFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("videos", "onViewCreated: ${searchViewModel.text}")
        searchViewModel.videos.observe(viewLifecycleOwner, Observer {
            Log.d("videos", "onViewCreated: $it")
        })
    }
}