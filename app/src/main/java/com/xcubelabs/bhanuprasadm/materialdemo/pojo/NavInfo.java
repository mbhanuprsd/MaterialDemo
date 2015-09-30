package com.xcubelabs.bhanuprasadm.materialdemo.pojo;

/**
 * Created by bhanuprasadm on 28/9/15.
 */
public class NavInfo {
    int imageId;
    String text;

    public NavInfo(int id, String title){
        this.imageId = id;
        this.text = title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }
}
