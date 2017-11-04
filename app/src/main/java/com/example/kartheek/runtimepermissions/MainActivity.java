package com.example.kartheek.runtimepermissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void checkPermisiion(View view) {
        if(isExternalStorageEnabled())
        {
            Toast.makeText(this,"Permission already granted",Toast.LENGTH_LONG).show();
            createFolder();
            return;
        }
        requestStoragePermission();

    }
    public boolean isExternalStorageEnabled()
    {
        int result= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        return false;
    }
    public void requestStoragePermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {

            AlertDialog.Builder dialog=new AlertDialog.Builder(this);
            dialog.setCancelable(false);
            dialog.setMessage("You need to allow permissions to create Folder");
            dialog.setTitle("Allow Permissions");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //requestStoragePermission();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
                createFolder();

            }
            else{
                Toast.makeText(this,"Permission has been denied",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void createFolder()
    {
        File f= new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Kartheek");
        if(f.exists())
        {
            Toast.makeText(this,"File already Created",Toast.LENGTH_LONG).show();
        }
        else{
            boolean res=f.mkdir();
            if(res)
            {
                Toast.makeText(this, "File created", Toast.LENGTH_LONG).show();
            }
        }
    }
}
