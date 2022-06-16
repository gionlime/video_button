package com.dalong.smallvideorecording;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Toast;

import com.dalong.recordlib.RecordVideoActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String videoPath;

    public static final  int TAKE_DATA=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File path=new File(Environment.getExternalStorageDirectory(),
                "dalong");
        if (!path.exists()) {
            path.mkdirs();
        }
        videoPath=path.getAbsolutePath()+File.separator+System.currentTimeMillis()+".mp4";
        //申请权限
        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
    }


    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "必须给予camera权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 录制
     * @param view
     */
    public  void  doRecording(View view){
        Intent intent=new Intent(this, RecordVideoActivity.class);
        intent.putExtra(RecordVideoActivity.RECORD_VIDEO_PATH,videoPath);
        startActivityForResult(intent,TAKE_DATA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_DATA:
                if(resultCode==RecordVideoActivity.TAKE_VIDEO_CODE){
                    String videoPath=data.getStringExtra(RecordVideoActivity.TAKE_VIDEO_PATH);
                    Toast.makeText(this, "视频路径："+videoPath, Toast.LENGTH_SHORT).show();
                }else if(resultCode==RecordVideoActivity.TAKE_PHOTO_CODE){
                    String photoPath=data.getStringExtra(RecordVideoActivity.TAKE_PHOTO_PATH);
                    Toast.makeText(this, "图片路径："+photoPath, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
