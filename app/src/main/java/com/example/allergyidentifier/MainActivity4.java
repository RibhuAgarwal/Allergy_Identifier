package com.example.allergyidentifier;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;

public class MainActivity4 extends AppCompatActivity {
    String checkingxyz;
    MaterialButton findme, inputImageBtn, recognizeTextBtn;
    ShapeableImageView imageIv;
    TextView recognizedTextEt;
    TextView editfinder;
    BottomNavigationView nav;


    private static final String TAG="MAIN_TAG";
    private Uri imageUri=null;

    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=101;

    ProgressDialog progressDialog;

    TextRecognizer textRecognizer;


    private String[] cameraPermissions,storagePermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        inputImageBtn=findViewById(R.id.inputImageBtn);
        recognizeTextBtn=findViewById(R.id.recognizeTextBtn);
        imageIv=findViewById(R.id.imageIv);
        recognizedTextEt=findViewById(R.id.recognizeTextEt);
        nav=findViewById(R.id.nav_bar);

        //
     
        findme=findViewById(R.id.find);
        editfinder=findViewById(R.id.findint);
       

        //

        cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(MainActivity4.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);



        findme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                List<String> myList = new ArrayList<String>(Arrays.asList(checkingxyz.split(" ")));

                for (int i =0; i<myList.size();i++){
                    if(myList.get(i).contains("banana")){
                        editfinder.setText("Don't eat");
                        recognizedTextEt.setText("");
                    }


                }
            }
        });



        inputImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputImageDialog();
            }
        });

        recognizeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editfinder.setText("");
                if (imageUri == null){
                    Toast.makeText(MainActivity4.this,"Pick image first",Toast.LENGTH_LONG).show();

                }
                else {
                    recognizeTextfromImage();
                }
            }
        });



    }

    private void recognizeTextfromImage() {
        progressDialog.setMessage("Recognizing text......");
        progressDialog.show();
        try {
            InputImage inputImage = InputImage.fromFilePath(MainActivity4.this, imageUri);

            progressDialog.setMessage("Recognizing text...");

            Task<Text> textTaskResult = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {

                            progressDialog.dismiss();
                            String recognizedText = text.getText();
                            ///
                            checkingxyz=recognizedText;
                            ///
                            recognizedTextEt.setText(recognizedText);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(MainActivity4.this,"Failed "+e.getMessage(),Toast.LENGTH_LONG).show();



                        }
                    });
        } catch (IOException e) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity4.this,"Failed"+e.getMessage(),Toast.LENGTH_LONG).show();

        }

    }

    private void showInputImageDialog() {
        PopupMenu popupMenu = new PopupMenu(this, inputImageBtn);

        popupMenu.getMenu().add(Menu.NONE,1,1,"CAMERA");


        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id==1){

                    if (checkCameraPermissions()){
                        pickImageCamera();
                    }
                    else{
                        requestCameraPermissions();
                    }

                }
                else if (id == 2){
                    if (checkStoragePermission()){
                        pickImageGallery();
                    }
                    else{
                        requestStoragePermission();
                    }

                }



                return true;
            }
        });


    }

    private void pickImageGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        galleryActionResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActionResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode()== Activity.RESULT_OK){

                        Intent data=result.getData();
                        imageUri=data.getData();

                        imageIv.setImageURI(imageUri);


                    }
                    else{

                        Toast.makeText(MainActivity4.this,"Cancelled",Toast.LENGTH_LONG).show();

                    }

                }
            }

    );

    private void pickImageCamera(){

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Sample Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        caameraActivityResultLauncher.launch(intent);

    }

    private ActivityResultLauncher<Intent> caameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK){

                        imageIv.setImageURI(imageUri);
                    }
                    else{
                        Toast.makeText(MainActivity4.this,"Cancelled",Toast.LENGTH_LONG).show();

                    }


                }
            }
    );

    private boolean checkStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(MainActivity4.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(MainActivity4.this, storagePermissions, STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermissions(){

        boolean cameraResult = ContextCompat.checkSelfPermission(MainActivity4.this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(MainActivity4.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return cameraResult && storageResult;

    }

    private  void requestCameraPermissions(){
        ActivityCompat.requestPermissions(MainActivity4.this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storeAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted&&storeAccepted){
                        pickImageCamera();
                    }
                    else{
                        Toast.makeText(MainActivity4.this,"Camera & Storage Permission", Toast.LENGTH_LONG).show();
                    }

                }


            }
            case STORAGE_REQUEST_CODE:{

                if (grantResults.length>0){

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        pickImageGallery();

                    }
                    else{
                        Toast.makeText(MainActivity4.this,"Storage Permission is required", Toast.LENGTH_LONG).show();

                    }

                }

            }

        }
    }


}