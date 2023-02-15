package com.example.thememovie.openedmovie

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.thememovie.R
import com.example.thememovie.Utils
import com.example.thememovie.context.MyApplication
import com.example.thememovie.databinding.FragmentOpenedMovieBinding
import com.example.thememovie.openedmovie.model.FullMovie
import com.example.thememovie.openedmovie.viewmodel.OpenedMovieViewModel
import javax.inject.Inject

class OpenedMovieFragment : Fragment() {
    private var _binding: FragmentOpenedMovieBinding? = null
    private val binding get() = _binding!!
    private var movieId: Long? = null
    @Inject lateinit var viewModel: OpenedMovieViewModel
    private lateinit var movie: FullMovie

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getLong(Utils.movieIdKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_opened_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOpenedMovieBinding.bind(view)
        if (isMovieIdCorrect()) {
            requestMovieInfo()
        }
        else {
            showErrorMessage()
        }

    }

    private fun requestMovieInfo() {
        viewModel.requestSoloMovieInfo(movieId!!)
        viewModel.image.observe(viewLifecycleOwner) { liveMovieInfo ->
            movie = liveMovieInfo
            setUIInformation()
        }
    }

    private fun setUIInformation() {
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/" + movie.posterPath) //@TODO change to String placeholder
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //@TODO handle
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.animationView.isVisible = false
                    showUiInfo()
                    return false
                }

            })
            .into(binding.image)

    }

    private fun showUiInfo() {
        binding.createdAtDate.isVisible = true
        binding.rate.isVisible = true
        binding.title.isVisible = true
        binding.genres.isVisible = true
        binding.overview.isVisible = true
        binding.createdAtText.isVisible = true
        binding.likesIcon.isVisible = true

        binding.createdAtDate.text = movie.releaseDate
        binding.rate.text = movie.rate.toString()
        binding.title.text = movie.originalTitle
        binding.genres.text = movie.genresToString()
        binding.overview.text = movie.overview
        setTextColor()
        if (!movie.adult) {
            binding.adultCircle.isVisible = false
            binding.adultText.isVisible = false
        }
    }

    @Suppress("DEPRECATION")
    private fun setTextColor() {
        if (movie.rate < 3.1) {
            binding.rate.setTextColor(resources.getColor(R.color.red))
        }
        else if (movie.rate < 7.1){
            binding.rate.setTextColor(resources.getColor(R.color.yellow))
        }
        else {
            binding.rate.setTextColor(resources.getColor(R.color.green))
        }
    }

    private fun showErrorMessage() {
        //@TODO handle error with UI
    }

    private fun isMovieIdCorrect(): Boolean {
        return movieId != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}