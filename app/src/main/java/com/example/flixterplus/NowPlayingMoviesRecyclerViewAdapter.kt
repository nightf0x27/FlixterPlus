package com.example.flixterplus
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class NowPlayingMoviesRecyclerViewAdapter (

    private val movies: List<NowPlayingMovie>,
            private val mListener: OnListFragmentInteractionListener?):RecyclerView.Adapter<NowPlayingMoviesRecyclerViewAdapter.MovieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing_movie, parent, false)
        return MovieViewHolder(view)
    }
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NowPlayingMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
        //val mBuyButton: Button = mView.findViewById<Button>(R.id.buy_button) as Button
        val mMovieImage: ImageView = mView.findViewById<ImageView>(R.id.movie_image) as ImageView
        val mMovieDescription: TextView = mView.findViewById<View>(R.id.movie_description) as TextView


        override fun toString(): String {
            return mMovieTitle.toString() + " '" + mMovieDescription.text + "'"

        }
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        val picture = "https://image.tmdb.org/t/p/w500"

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        Glide.with(holder.mView)
            .load(picture + movie.movieImageUrl)
            .centerInside()
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }
    override fun getItemCount(): Int {
        return movies.size
    }

}