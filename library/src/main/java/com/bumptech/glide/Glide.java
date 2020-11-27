package com.bumptech.glide;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.util.Preconditions;

public class Glide implements ComponentCallbacks2 {

    private static final String TAG = "Glide";

    private static volatile Glide glide;

    private static volatile boolean isInitializing;
    private final GlideContext glideContext;

    private final RequestManagerRetriever requestManagerRetriever;

    Glide(
            @NonNull Context context,
            @NonNull RequestManagerRetriever requestManagerRetriever
    ) {
        glideContext = new GlideContext(context);
        this.requestManagerRetriever = requestManagerRetriever;
    }

    public static Glide get(@NonNull Context context) {
        if (glide == null) {
            synchronized (Glide.class) {
                if (glide == null) {
                    checkAndInitializeGlide(context);
                }
            }
        }
        return glide;
    }

    private static void checkAndInitializeGlide(Context context) {
        isInitializing = true;
        initializeGlide(context);
        isInitializing = false;
    }

    private static void initializeGlide(@NonNull Context context) {
        initializeGlide(context, new GlideBuilder());
    }

    private static void initializeGlide(@NonNull Context context, @NonNull GlideBuilder builder) {
        Context applicationContext = context.getApplicationContext();
        Glide glide = builder.build(context);
        Glide.glide = glide;
    }

    @NonNull
    public static RequestManager with(@NonNull Context context) {
        return getRetriever(context).get(context);
    }

    @NonNull
    private static RequestManagerRetriever getRetriever(@Nullable Context context) {
        // Context could be null for other reasons (ie the user passes in null), but in practice it will
        // only occur due to errors with the Fragment lifecycle.
        Preconditions.checkNotNull(
                context,
                "You cannot start a load on a not yet attached View or a Fragment where getActivity() "
                +
                "returns null (which usually occurs when getActivity() is called before the Fragment "
                + "is attached or after the Fragment is destroyed).");
        return Glide.get(context).getRequestManagerRetriever();
    }

    /**
     * Internal method.
     */
    @NonNull
    public RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
