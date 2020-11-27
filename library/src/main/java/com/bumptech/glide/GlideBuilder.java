package com.bumptech.glide;

import android.content.Context;

import androidx.annotation.NonNull;

public class GlideBuilder {

    @NonNull
    Glide build(@NonNull Context context) {
        return new Glide(context);
    }
}
