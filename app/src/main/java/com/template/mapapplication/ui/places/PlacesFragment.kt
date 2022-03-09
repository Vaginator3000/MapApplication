package com.template.mapapplication.ui.places

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.R
import com.template.mapapplication.databinding.FragmentPlacesBinding
import com.template.mapapplication.ui.places.recycler.PlacesRVAdapter
import com.template.models.Result
import com.template.models.VisitedPlaceModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlacesFragment : Fragment(R.layout.fragment_places) {
    private val binding: FragmentPlacesBinding by viewBinding(FragmentPlacesBinding::bind)
    private val placesViewModel by viewModel<PlacesViewModel> ()

    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }
    private var adapter: PlacesRVAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placesViewModel.getVisitedPlaces().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Error -> {
                    showOrHideRecycler(noPlacesYet = true)
                }
                is Result.Loading -> {
                    // do nothing
                }
                is Result.Success -> {
                    if(result.value != null) {
                        setupRView(result.value!!)
                        showOrHideRecycler(noPlacesYet = false)
                    }
                }
            }
        })
    }

    private fun updateRV(places: List<VisitedPlaceModel>) {
        with(binding) {
            (visitedPlacesRecycler.adapter as PlacesRVAdapter).updateList(places)
            visitedPlacesRecycler.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupRView(places: List<VisitedPlaceModel>) {
        with(binding) {
            visitedPlacesRecycler.adapter = PlacesRVAdapter()
            visitedPlacesRecycler.layoutManager = layoutManager
        }
        updateRV(places)

        val simpleCallBack =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val index = viewHolder.absoluteAdapterPosition
                    placesViewModel.removePlaceByIndex(index)
                    adapter?.notifyItemRemoved(index)
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(binding.visitedPlacesRecycler)
    }

    private fun showOrHideRecycler(noPlacesYet: Boolean) {
        with(binding) {
            visitedPlacesRecycler.isVisible = !noPlacesYet
            emptyPlacesListTextView.isVisible = noPlacesYet
        }
    }
}