package com.bumptech.glide;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;

public class GlideContext extends ContextWrapper {

    public GlideContext(@NonNull  Context context) {
        super(context);
    }
}
