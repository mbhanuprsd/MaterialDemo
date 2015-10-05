package com.xcubelabs.bhanuprasadm.materialdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xcubelabs.bhanuprasadm.materialdemo.MyApplication;
import com.xcubelabs.bhanuprasadm.materialdemo.R;
import com.xcubelabs.bhanuprasadm.materialdemo.adapter.BoxOfficeAdapter;
import com.xcubelabs.bhanuprasadm.materialdemo.logging.L;
import com.xcubelabs.bhanuprasadm.materialdemo.network.VolleySingleton;
import com.xcubelabs.bhanuprasadm.materialdemo.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.Keys.EndpointBoxOffice.KEY_TITLE;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.UrlEndPoints.URL_BOX_OFFICE;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.UrlEndPoints.URL_CHAR_AMPERSAND;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.UrlEndPoints.URL_CHAR_QUES;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.UrlEndPoints.URL_PARAM_APIKEY;
import static com.xcubelabs.bhanuprasadm.materialdemo.extras.UrlEndPoints.URL_PARAM_LIMIT;


public class BoxOfficeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movie> movieList = new ArrayList<>();
    private BoxOfficeAdapter boxOfficeAdapter;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView rvBoxOffice;

    private String mParam1;
    private String mParam2;

    public BoxOfficeFragment() {
        // Required empty public constructor
    }

    public static BoxOfficeFragment newInstance(String param1, String param2) {
        BoxOfficeFragment fragment = new BoxOfficeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJSonRequest();
    }

    private void sendJSonRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(50),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movieList = parseJson(response);
                        boxOfficeAdapter.setList(movieList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.m(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private ArrayList<Movie> parseJson(JSONObject response) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        if (response == null || response.length() == 0) {
            return arrayList;
        }

        try {
            if (response.has(KEY_MOVIES)) {
                JSONArray moviesArray = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    long id = movie.getLong(KEY_ID);
                    String title = movie.getString(KEY_TITLE);
                    String releaseDate;
                    JSONObject releaseDateObject = movie.getJSONObject(KEY_RELEASE_DATES);
                    if (releaseDateObject.has(KEY_THEATER)) {
                        releaseDate = releaseDateObject.getString(KEY_THEATER);
                    } else {
                        releaseDate = "NA";
                    }
                    int audienceScore = -1;
                    JSONObject ratingsObject = movie.getJSONObject(KEY_RATINGS);
                    if (ratingsObject.has(KEY_AUDIENCE_SCORE)) {
                        audienceScore = ratingsObject.getInt(KEY_AUDIENCE_SCORE);
                    }
                    String synopsis = movie.getString(KEY_SYNOPSIS);
                    String thumbUrl = "";
                    JSONObject postersObject = movie.getJSONObject(KEY_POSTERS);
                    if (postersObject.has(KEY_THUMBNAIL)) {
                        thumbUrl = postersObject.getString(KEY_THUMBNAIL);
                    }

                    Movie currentMovie = new Movie();
                    currentMovie.setId(id);
                    currentMovie.setTitle(title);
                    try {
                        Date mDate = dateFormat.parse(releaseDate);
                        currentMovie.setReleaseDateTheater(mDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentMovie.setAudienceScore(audienceScore);
                    currentMovie.setSynopsis(synopsis);
                    currentMovie.setUrlThumbnail(thumbUrl);
                    arrayList.add(currentMovie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private String getRequestUrl(int limit) {
        return URL_BOX_OFFICE +
                URL_CHAR_QUES +
                URL_PARAM_APIKEY +
                MyApplication.ROTTEN_TOMATOES_API_KEY +
                URL_CHAR_AMPERSAND +
                URL_PARAM_LIMIT + limit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_box_office, container, false);
        rvBoxOffice = (RecyclerView) mRootView.findViewById(R.id.rvBoxOffice);
        rvBoxOffice.setLayoutManager(new LinearLayoutManager(getActivity()));
        boxOfficeAdapter = new BoxOfficeAdapter(getActivity());
        rvBoxOffice.setAdapter(boxOfficeAdapter);

        return mRootView;
    }


}
