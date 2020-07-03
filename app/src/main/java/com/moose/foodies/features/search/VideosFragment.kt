package com.moose.foodies.features.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.moose.foodies.R
import com.moose.foodies.di.DaggerAppComponent
import javax.inject.Inject


class VideosFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var searchViewModel: SearchViewModel

    override fun onAttach(context: Context) {
        DaggerAppComponent.factory().create(requireActivity().applicationContext).inject(this)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.logIt()
        searchViewModel.videos.observe(viewLifecycleOwner, Observer {
            Log.d("videos", "onViewCreated: $it")
        })
    }
}