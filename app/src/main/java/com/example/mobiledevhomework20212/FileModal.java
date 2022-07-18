package com.example.mobiledevhomework20212;

import android.os.Build;
import android.text.BoringLayout;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileModal {

    private static final Map<String, List<String>> fileType;
    static {
        fileType = new HashMap<>();
        fileType.put("image", Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));
        fileType.put("audio", Arrays.asList("mp3", "wav", "ogg", "midi", "m4a"));
        fileType.put("video", Arrays.asList("mp4", "rmvb", "avi", "flv", "3gp"));
        fileType.put("web", Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));
        fileType.put("zip", Arrays.asList("jar", "zip", "rar", "gz"));
        fileType.put("text", Collections.singletonList("txt"));
    }

    private File file;
    private View view;
    private Boolean selected = false;

    public FileModal(File file){
        this.file = file;
    }

    public File getFile(){
        return this.file;
    }

    public void setFile(File file){
        this.file = file;
    }

    public String getAbsolutePath(){
        return file.getAbsolutePath();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getExtension(){
        Optional<String> optional = Optional.ofNullable(getName())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(getName().lastIndexOf(".") + 1));
        return optional.orElse("");
    }

    public String getName(){
        return file.getName();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getType(){
        for (Map.Entry<String, List<String>> entry : fileType.entrySet()){
            if (Arrays.asList(entry.getValue().toArray()).contains(getExtension())){
                return entry.getKey();
            }
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getIconDrawable(){
        if (getType().isEmpty()) {
            return R.drawable.ic_baseline_folder_open_24;
        }
        else {
            switch (getType()){
                case "text":
                    return R.drawable.ic_baseline_text_snippet_24;
                case "image":
                    return R.drawable.ic_baseline_image_24;
                case "video":
                    return R.drawable.ic_baseline_video_file_24;
                case "audio":
                    return R.drawable.ic_baseline_audio_file_24;
                default:
                    return R.drawable.ic_baseline_file_24;
            }
        }
    }

    public void setView(View itemView) {
        view = itemView;
    }

    public View getView(){
        return view;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
