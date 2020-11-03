package com.moose.foodies.features.intolerances

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.models.Intolerance
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_intolerances.*
import javax.inject.Inject


class IntolerancesActivity @Inject constructor(): AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val intolerancesViewModel: IntolerancesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intolerances)
        intolerancesloading.start()

        if (intent.getStringExtra("type") == "signup") showSnackbar(intolerances_layout, "We have sent you a confirmation email :)")

        intolerancesViewModel.response.observe(this, {
            it as ArrayList<Intolerance>
            val introAdapter = IntolerancesAdapter(it) { intolerance, selected ->
                intolerancesViewModel.handleItem(intolerance, selected)
            }
            intro_recycler.apply {
                setHasFixedSize(true)
                adapter = introAdapter
            }
        })

        intolerancesViewModel.saveResult.observe(this, {
            intolerancesloading.hide()
            intolerances_submit.show()
            if (it) {
                pushWithoutHistory<HomeActivity>()
            }
            else showSnackbar(intolerances_layout, "something went wrong! Try again later")
        })

        intolerancesViewModel.exception.observe(this, {
            showSnackbar(intolerances_layout, it)
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
