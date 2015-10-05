package com.xcubelabs.bhanuprasadm.materialdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xcubelabs.bhanuprasadm.materialdemo.R;
import com.xcubelabs.bhanuprasadm.materialdemo.network.VolleySingleton;

public class MyFragment extends Fragment {

    TextView tvPosition;

    public static MyFragment getInstance(int position) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my, container, false);
        tvPosition = (TextView) layout.findViewById(R.id.tvPosition);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tvPosition.setText("Selected fragment position : " + bundle.getInt("position", 0));
        }

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, "http://php.net", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvPosition.setText("Response : \n" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvPosition.setText("Error : \n" + error.toString());
            }
        });
        requestQueue.add(request);
        return layout;
    }
}