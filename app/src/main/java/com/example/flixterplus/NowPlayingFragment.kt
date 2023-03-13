package com.example.flixterplus
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
//import com.codepath.flixterplus.R
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class NowPlayingFragment : Fragment(), OnListFragmentInteractionListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()
        // Create and set up an AsyncHTTPClient() here

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/now_playing",
                params,
                object : JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                       // Log.e("Hello",json.jsonObject.toString())
                        val resultsJSON: JSONArray = json.jsonObject.getJSONArray("results")
                       // val moviesRawJSON: String = resultsJSON.get("overview")
                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<NowPlayingMovie>>() {}.type
                        val models: List<NowPlayingMovie> = gson.fromJson(resultsJSON.toString(),arrayMovieType)




                     //   NowPlayingMoviesRecyclerViewAdapter.setNowPlayingMovie(arrayMovieType)
                      //  resultsJSON.let { NowPlayingMovie.fromJasonArray(it) }.let { movies.addAll(it) }
                              //  JSONObject = json.jsonObject.get("results") as JSONObject
                      //  Log.v("NowPlayingFragment", arrayMovieType.toString())

                        //val movieName = moviesRawJSON.getString("original_name")
                      //gson.fromJson(moviesRawJSON, arrayMovieType)
                        //    resultsJSON.let{NowPlayingMovie.()} .let { movies.addAll() }

                        recyclerView.adapter =
                           NowPlayingMoviesRecyclerViewAdapter(models, this@NowPlayingFragment)

                        // Look for this in Logcat:
                        Log.d("NowPlayingMovieFragment", "response successful")
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("NowPlayingFragment", errorResponse)
                        }
                    }
                }]
    }
    override fun onItemClick(item: NowPlayingMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }
}