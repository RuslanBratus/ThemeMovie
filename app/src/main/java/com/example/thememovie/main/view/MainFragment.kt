package com.example.thememovie.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thememovie.R
import com.example.thememovie.Utils
import com.example.thememovie.context.MyApplication
import com.example.thememovie.databinding.FragmentMainBinding
import com.example.thememovie.main.view.recycler.MainRecyclerView
import com.example.thememovie.main.view.recycler.clicklistener.OnMovieClickListener
import com.example.thememovie.main.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(), OnMovieClickListener  {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: MainRecyclerView

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        setUiListeners()
        initRecycler()
        launchGettingMovies()
    }

    private fun launchGettingMovies() {
        lifecycleScope.launch {
            mainViewModel.images.collectLatest { pagedData ->
                mAdapter.submitData(pagedData)
                //@TODO add Loading animation
            }
        }
    }

    private fun setUiListeners() {
        //@TODO implement search movie by name
    }

    private fun initRecycler() {
        mAdapter = MainRecyclerView(this)

        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun movieWasClicked(movieId: Long) {
        val bundleId = Bundle()
        bundleId.putLong(Utils.movieIdKey, movieId)
        findNavController().navigate(R.id.action_mainFragment_to_openedMovieFragment, bundleId)
    }

}