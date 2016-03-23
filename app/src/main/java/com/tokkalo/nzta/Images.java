package com.tokkalo.nzta;

import java.util.ArrayList;

/**
 * Created by rameshkolamala on 23/03/16.
 */
public class Images {
    private ArrayList<Integer> imageId;

    public Images() {
        imageId = new ArrayList<Integer>();
        imageId.add(R.drawable.img);
        imageId.add(R.drawable.img1);
        imageId.add(R.drawable.img2);
        imageId.add(R.drawable.video1);
        imageId.add(R.drawable.video2);
        imageId.add(R.drawable.video3);
        imageId.add(R.drawable.video4);
        imageId.add(R.drawable.video5);
    }

    public ArrayList<Integer> getImageItem() {
        return imageId;
    }
}
