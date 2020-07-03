package com.moose.foodies.features.intolerances

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.di.DaggerAppComponent
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.hide
import com.moose.foodies.util.show
import com.moose.foodies.util.showSnackbar
import kotlinx.android.synthetic.main.activity_intolerances.*
import javax.inject.Inject


class IntolerancesActivity @Inject constructor(): AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val intolerancesViewModel: IntolerancesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.factory().create(this).inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intolerances)
        intolerancesloading.start()

        if (intent.getBooleanExtra("signup", false)) showSnackbar(intolerances_layout, "We have sent you a confirmation email :)")

        intolerancesViewModel.results.observe(this, Observer {
            val introAdapter = IntolerancesAdapter(it) { intolerance, selected ->
                intolerancesViewModel.handleItem(intolerance, selected)
            }
            intro_recycler.apply {
                setHasFixedSize(true)
                adapter = introAdapter
            }
        })

        intolerancesViewModel.saveResult.observe(this, Observer {
            intolerancesloading.hide()
            intolerances_submit.show()
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            else showSnackbar(intolerances_layout, "something went wrong! Try again later")
        })

        intolerancesViewModel.state.observe(this, Observer {
            showSnackbar(intolerances_layout, it.state)
        })

        intolerancesViewModel
        intolerancesViewModel.getIntolerances()

        intolerances_submit.setOnClickListener {
            intolerances_submit.hide()
            intolerancesloading.show()
            intolerancesViewModel.update()
        }
    }

}
