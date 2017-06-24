package com.example.iossenac.exdeusostoragefirebase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);

        List<String> urls = new ArrayList<>();
        urls.add("gurgel.jpg");
        urls.add("camaro.jpg");
        urls.add("elba.jpg");

        final ImageView[] imgViews = new ImageView[3];
        imgViews[0] = img1;
        imgViews[1] = img2;
        imgViews[2] = img3;

        final long CEM_KBYTE = 100 * 1024;

        for (String url : urls) {
            StorageReference gsReference = ControlApp.storageRef.child(url);

            gsReference.getBytes(CEM_KBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Log.e("STORAGE", "SUCESSO!!!!: " + bytes.length);
                    imgViews[index].setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    index++;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("STORAGE", "falha!!!! = " + exception.getLocalizedMessage());
                }
            });
        }

        index = 0;

    }

    public void escolheImagem(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10) {
                Uri selectedImageUri = data.getData();

                UploadTask task = ControlApp.storageRef.child("teste.png").putFile(selectedImageUri);
                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.e("STORAGE", "onSucess no envio");
                    }
                });
            }

        }

    }

}