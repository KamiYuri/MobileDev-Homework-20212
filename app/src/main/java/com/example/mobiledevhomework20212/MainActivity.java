package com.example.mobiledevhomework20212;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_CODE = 123;

    Map<String, List<String>> fileType = new HashMap<>();

    private File dir;
    private String dirPath;
    private RecyclerView recyclerView;
    private TextView dirPathTextView;

    private ArrayList<Pair<Integer, String>> fileInfo;
    private CustomAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPerms();
        setFileTypeData();

        //Initialize ArrayLists
        fileInfo = new ArrayList<>();

        dirPathTextView = findViewById(R.id.dirPathText);

        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        refreshListFiles();

        // Set adapter for listview
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CustomAdapter(fileInfo);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setDirPath();
    }

    private void setFileTypeData() {
        fileType.put("image", Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));
        fileType.put("audio", Arrays.asList("mp3", "wav", "ogg", "midi"));
        fileType.put("video", Arrays.asList("jsp", "html", "htm", "js", "php"));
        fileType.put("web", Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));
        fileType.put("zip", Arrays.asList("jar", "zip", "rar", "gz"));
        fileType.put("text", Arrays.asList("txt"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getFileType(String ext) {
        for (Map.Entry<String, List<String>> entry : fileType.entrySet()){
            if (Arrays.asList(entry.getValue().toArray()).contains(ext)){
                return entry.getKey();
            }
        }
        return "";
    }

    private void setDirPath() {
        dirPathTextView.setText(dirPath);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshListFiles() {
        try {
            dir = new File(dirPath);
            File[] files = dir.listFiles();

            //reset ArrayLists
            fileInfo.clear();

            assert files != null;
            for (File file : files) {
                String ext = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(file));
                if (ext.isEmpty()) {
                    fileInfo.add(new Pair<>(R.drawable.ic_baseline_folder_open_24, file.getName()));
                }
                else {
                    switch (getFileType(ext)){
                        case "text":
                            fileInfo.add(new Pair<>(R.drawable.ic_baseline_text_snippet_24, file.getName()));
                            break;
                        case "image":
                            fileInfo.add(new Pair<>(R.drawable.ic_baseline_image_24, file.getName()));
                            break;
                        default:
                            fileInfo.add(new Pair<>(R.drawable.ic_baseline_file_24, file.getName()));
                    }
                }
            }
        } catch (Exception e) {
            String error = e + "\n\nMessage: " + e.getMessage();
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    private void requestPerms() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission denied.");
                requestPermissions(EXTERNAL_PERMS, REQUEST_CODE);
            } else {
                Log.v("TAG", "Permission granted.");
            }
        }

        if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission granted.");
            } else {
                Log.v("TAG", "Permission denied.");
            }
        }
    }
}


