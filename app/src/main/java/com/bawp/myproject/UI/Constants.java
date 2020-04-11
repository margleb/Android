package com.bawp.myproject.UI;

import java.util.Random;

public class Constants {
    public static final String URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson";
    // лимит данных
    public static final int LIMIT = 30;
    public static int randomInt(int max, int min) {
        return new Random().nextInt(max - min) + min;
    }
}
