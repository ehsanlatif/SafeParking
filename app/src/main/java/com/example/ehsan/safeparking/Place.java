package com.example.ehsan.safeparking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehsan on 28-12-2017.
 */

class Place {
    String name;
    String address;
    int photoId;

    Place(String name, String addr, int photoId) {
        this.name = name;
        this.address = addr;
        this.photoId = photoId;
    }
}
