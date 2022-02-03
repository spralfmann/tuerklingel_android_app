package com.lp.download_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void download_click(View v){

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference gsReference = storage.getReferenceFromUrl("gs://tuerklingel-a0ba8.appspot.com/pictures/Testfoto.jpg");

        ImageView mIm = (ImageView) findViewById(R.id.imageView);
        final long ONE_MEGABYTE=10240*10240;

        //https://stackoverflow.com/questions/57152775/how-to-set-an-imageview-with-a-picture-from-firebase-storage
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                mIm.setImageBitmap(bitmap);
            }
        });

    }
}