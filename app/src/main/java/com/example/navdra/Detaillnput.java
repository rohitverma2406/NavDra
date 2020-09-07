package com.example.navdra;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Detaillnput extends AppCompatActivity {
    ImageButton imgbtn;
    public CircleImageView imageView;
    public EditText inpname;
    public EditText inpphone;
    public EditText inpstatus;
    public EditText inpbra;
    public EditText inpemail;

    Button btn;
    private  final int  PERMISSION_ALL = 1;
    String name, phone, status, bra;
    private DatabaseReference post;
    private ImagePicker imagePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaillnput);
        imgbtn = findViewById(R.id.imgBtn);
        imageView= findViewById(R.id.image);
        inpname = findViewById(R.id.inpName);
        inpphone = findViewById(R.id.inpPhone);
        inpstatus = findViewById(R.id.inpStatus);
        inpbra = findViewById(R.id.inpBra);
        btn = findViewById(R.id.button);
        final String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(Detaillnput.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Detaillnput.this, PERMISSIONS, PERMISSION_ALL);
                }else {
                    imagePicker.choosePicture(false/*show camera intents*/);

                }


            }
        });
        imagePicker = new ImagePicker(this, /* activity non null*/
                null, /* fragment nullable*/
                new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {
                        UCrop.of(imageUri, getTempUri())
                                .withAspectRatio(1, 1)

                                .start(Detaillnput.this);

                    }
                });










        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = inpname.getText().toString();
                String phone = inpphone.getText().toString();
                String status = inpstatus.getText().toString();
                String bra = inpbra.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || bra.isEmpty()) {
                    Toast.makeText(Detaillnput.this, "fill all details", Toast.LENGTH_SHORT).show();
                } else if (!inpphone.getText().toString().matches("[0-9]{10}")) {
                    inpphone.setError("Enter only 10 numbers");

                } else {
                    storeNewUserData();

                startActivity(new Intent(Detaillnput.this, NewActivity.class));
            }

            }
        });


    }
    private Uri getTempUri(){

        String dri = Environment.getExternalStorageDirectory()+File.separator+"temp";
         File dirFile = new File(dri);
         dirFile.mkdir();
         String file = dri+File.separator+"temp.png";
         File temFile = new File(file);
        try {
            temFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Uri.fromFile(temFile);

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
        switch (requestCode) {
            case PERMISSION_ALL:{
                if (grantResults.length > 0) {
                    boolean flag = true;
                    for (int i = 0; i<permissionsList.length; i++) {
                        if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                            flag = false;


                        }

                    }
                    if (flag){
                        imagePicker.choosePicture(true /*show camera intents*/);

                    }
                    // Show permissionsDenied

                }
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            upload(resultUri);

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
    void upload(Uri uri){

        final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child("temp/"+System.currentTimeMillis()+".png");

        riversRef.putFile(uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("tag", "onFailure: " +exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageView.setImageURI(null);
                        Picasso.get().load(uri).into(imageView);

                    }
                });
            }
        });
    }







    private void storeNewUserData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("inpname", inpname.getText().toString());
        map.put("inpphone", inpphone.getText().toString());
        map.put("inpbra", inpbra.getText().toString());
        map.put("inpstatus", inpstatus.getText().toString());
        post =  FirebaseDatabase.getInstance().getReference().child("users");



        post.push().
                setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("tag", "on compltet");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("tag", "not compltet"+e.toString());
            }
        });

    }

}
