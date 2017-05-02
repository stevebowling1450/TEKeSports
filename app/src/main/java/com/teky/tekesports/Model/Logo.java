package com.teky.tekesports.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stevebowling on 12/2/16.
 * toornament-android.
 */

public class Logo {

    @SerializedName("icon_large_square")
    private final String icon_large_square;

    @SerializedName("extra_small_square")
    private final String extra_small_square;

    @SerializedName("medium_small_square")
    private final String medium_small_square;

    @SerializedName("medium_large_square")
    private final String medium_large_square;

    public Logo(String icon_large_square, String extra_small_square, String medium_small_square, String medium_large_square) {
        this.icon_large_square = icon_large_square;
        this.extra_small_square = extra_small_square;
        this.medium_small_square = medium_small_square;
        this.medium_large_square = medium_large_square;
    }

    public String getIcon_large_square() {
        return icon_large_square;
    }

    public String getExtra_small_square() {
        return extra_small_square;
    }

    public String getMedium_small_square() {
        return medium_small_square;
    }

    public String getMedium_large_square() {
        return medium_large_square;
    }
}
