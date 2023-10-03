package com.prepeez.toclearningstudent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.prepeez.toclearningstudent.adapter.ChatAdapter.bitmap;

public class PictureActivity extends AppCompatActivity {
    private ImageView photoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        photoImageView = findViewById(R.id.photoImageView);
        photoImageView.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth();
        photoImageView.getLayoutParams().height = getWindowManager().getDefaultDisplay().getHeight();
        photoImageView.setAdjustViewBounds(true);
        //photoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(photoImageView);
        photoImageView.setImageBitmap(bitmap);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
