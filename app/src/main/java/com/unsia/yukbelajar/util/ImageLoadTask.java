package com.unsia.yukbelajar.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewRef;
    private String imageUrl;

    public ImageLoadTask(ImageView imageView) {
        imageViewRef = new WeakReference<>(imageView);
    }

    @Override
    protected void onPreExecute() {
        ImageView imageView = imageViewRef.get();
        if (imageView != null) {
            imageView.setImageResource(android.R.drawable.progress_indeterminate_horizontal);
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        imageUrl = strings[0];

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = imageViewRef.get();
        if (imageView == null) return;

        // Prevent wrong image on fast scroll / reuse
        if (!imageUrl.equals(imageView.getTag())) return;

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(android.R.drawable.ic_delete);
        }
    }
}
