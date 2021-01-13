package com.leonardo.tableappreservation.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.leonardo.tableappreservation.R;

import java.io.IOException;

public class NewMenu extends AppCompatActivity {
    private EditText menuTitle;
    private EditText editTextDescription;
    private ImageView menuImage;
    private String menuURL;
    private Button saveButton;
    private Uri menuURI;
    private boolean hasImage;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);

        hasImage = false;

        menuTitle = findViewById(R.id.newMenuTitle);
        editTextDescription = findViewById(R.id.newMenuDescription);
        menuImage = findViewById(R.id.newImage);
        saveButton = findViewById(R.id.saveButton);

        storageReference = FirebaseStorage.getInstance().getReference();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasImage) {
                    uploadImageToFirebase(menuURI);
                } else {
                    Toast.makeText(NewMenu.this, "Please select the photo!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                } else {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, 1000);
                }
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.new_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void SaveMenu() {
        String title = menuTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Title or Description is empty!", Toast.LENGTH_SHORT).show();
        }

        CollectionReference menuOrderRef = FirebaseFirestore.getInstance()
                .collection("menuOrders");
        menuOrderRef.add(new Menu(title, description, menuURL));
        Toast.makeText(this, "New menu added!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                menuURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(NewMenu.this.getContentResolver(), menuURI);
                    menuImage.setImageBitmap(bitmap);
                    hasImage = true;
                } catch (IOException e) {
                    Log.e("NewMenu", e.getLocalizedMessage());
                    e.printStackTrace();
                }
//                uploadImageToFirebase(imageUri);

            }
        }
    }

    private void uploadImageToFirebase(final Uri imageUri) {
        final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("menu-image").child(menuTitle + " " + System.currentTimeMillis() + ".jpeg");
//        final StorageReference fileRef = storageReference.child("menuOrders/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(NewMenu.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into(menuImage);
                        menuURL = uri.toString();
                        SaveMenu();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewMenu.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
