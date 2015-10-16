package com.xcubelabs.bhanuprasadm.materialdemo.interfaces;

import android.view.View;

public interface ClickListener {
    void onClick(View v, int position);

    void onLongClick(View v, int position);
}