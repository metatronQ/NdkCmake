package com.example.ndkcmake;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ndk_source.NativeProvider;
import com.example.ndkcmake.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSON_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<Object> launcher = registerForActivityResult(new ActivityResultContract<Object, Object>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Object input) {
            Intent openFileIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            openFileIntent.setType("*/*");
            openFileIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            return openFileIntent;
        }

        @Override
        public Object parseResult(int resultCode, @Nullable Intent intent) {
            if (intent == null) {
                return null;
            }
            if (intent.getClipData() != null) {
                return intent.getClipData().getItemCount();
            }
            if (intent.getData() != null) {
                return intent.getData();
            }
            return null;
        }
    }, result -> {
        Toast.makeText(MainActivity.this, "" + result.toString(),Toast.LENGTH_SHORT).show();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        String s = NativeProvider.printfC();
        tv.setText(s);

        Button pcm2Mp3 = binding.pcmToMp3Button;
        pcm2Mp3.setOnClickListener(view -> {
            if (verifyStoragePermissions(MainActivity.this) != 0) {
                return;
            }
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                // 测试创建文件
//                File musicCreate = new File(sdCardPath + "/Music/aaa.text");
//                try {
//                    if (musicCreate.exists()) {
//
//                    } else {
//                        musicCreate.createNewFile();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                File pcmPath = new File(sdCardPath + "/Music/test8K.pcm");
                if (pcmPath.exists()) {
                    String s1 = pcmPath.getAbsolutePath();
                    if (NativeProvider.init(pcmPath.getAbsolutePath(),
                            2,
                            8,
                            8000,
                            sdCardPath + "/Music/test.mp3") == 0) {
                        Log.d("2mp3", "onCreate: isDecoding");
                        NativeProvider.encode();
                        NativeProvider.destroy();
                    }
                    Log.d("2mp3", "onCreate: no");
                }
            }
        });

        binding.openFileTreeButton.setOnClickListener(view -> {
            launcher.launch(true);
        });

    }


    /**
     * 文件操作需要动态申请权限
     */
    public int verifyStoragePermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, PERMISSON_STORAGE[0]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, PERMISSON_STORAGE[1]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSON_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return -1;
        }

        // 该权限已被禁用
//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, REQUEST_EXTERNAL_STORAGE);
//            return -2;
//        }
        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        Log.d("2mp3_PermissionResults", "" + grantResult);
                    }
                }
                break;

            default:
                break;
        }
    }
}