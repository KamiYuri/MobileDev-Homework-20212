package com.example.mobiledevhomework20212;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int COPY = 1;
    private static final int CUT = 2;
    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_CODE = 123;

    private File dir;
    private String dirPath;
    private RecyclerView recyclerView;
    private TextView dirPathTextView;

    private ArrayList<FileModal> filesList, selectedFilesList;
    private CustomAdapter adapter;
    private int cutOrCopy = 0;
    private Menu optionsMenu;
    private FloatingActionButton pasteFab;
    private ActionBar actionBar;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPerms();

        actionBar = getSupportActionBar();

        pasteFab = findViewById(R.id.fab_paste);
        pasteFab.setOnClickListener(v -> paste());

        //Initialize ArrayLists
        filesList = new ArrayList<>();
        selectedFilesList = new ArrayList<>();

        dirPathTextView = findViewById(R.id.dirPathText);
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        refreshListFiles();

        // Set adapter for listview
        adapter = new CustomAdapter(filesList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        registerForContextMenu(recyclerView);

        setDirPath();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_item_menu, menu);
        optionsMenu = menu;
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addNewFolder:
                addNewFolder();
                break;
            case R.id.addNewText:
                addNewText();
                break;
            case android.R.id.home:
                backToParent();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void backToParent() {
        try {
            dirPath = (new File(dirPath + "/" + "..").getCanonicalPath());
            refreshListFiles();
            refreshAdapter();
            setDirPath();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
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
            filesList.clear();

            actionBar.setDisplayHomeAsUpEnabled(!dir.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath()));


            assert files != null;
            for (File file : files) {
                Log.v("refreshListFiles", new FileModal(file).getExtension());
                filesList.add(new FileModal(file));
            }
        } catch (Exception e) {
            String error = e + "\n\nMessage: " + e.getMessage();
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void resetDir(){
        dirPath  = Environment.getExternalStorageDirectory().getAbsolutePath();
        refreshListFiles();
        refreshAdapter();
        setDirPath();
        resetHighlight();
    }

    private void requestPerms() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.v("requestPerms", "Permission denied.");
                requestPermissions(EXTERNAL_PERMS, REQUEST_CODE);
            } else {
                Log.v("requestPerms", "Permission granted.");
            }
        }

        if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("onRequestPermissionsResult", "Permission granted.");
            } else {
                Log.v("onRequestPermissionsResult", "Permission denied.");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fileOpen(FileModal file){
        try{
            String oldDirPath = dirPath;

            dirPath = (new File(dirPath + "/" + file.getName())).getCanonicalPath();

            Log.v("fileOpen", "dirPath: " + dirPath);

            File f = new File(dirPath);
            if (f.isDirectory()){
                refreshListFiles();
                refreshAdapter();
                setDirPath();
            }else{
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                intent.setDataAndType(FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", f), MimeTypeMap.getSingleton().getMimeTypeFromExtension(new FileModal(f).getExtension()));
                this.startActivity(intent);

                dirPath = oldDirPath;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<FileModal> filesList;

        public CustomAdapter(ArrayList<FileModal> filesList){
            this.filesList = filesList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View listViewItem = inflater.inflate(R.layout.listview_items, parent, false);

            return new ViewHolder(listViewItem);
        }

        @SuppressLint("NonConstantResourceId")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            FileModal file = filesList.get(position);

            holder.itemView.setOnClickListener(v -> fileOpen(file));

            if (file.isSelected()){
                holder.itemView.setBackgroundColor(Color.parseColor("#2FA4FF"));
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE);
            }

            ((ViewHolder)holder).fileIcon.setImageResource(file.getIconDrawable());
            ((ViewHolder)holder).fileName.setText(file.getName());
            ((ViewHolder)holder).optionButton.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.file_item_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()){
                        case R.id.menu_item_select:
                            select(file);
                            return true;
                        case R.id.menu_item_rename:
                            rename(file);
                            return true;
                        case R.id.menu_item_delete:
                            delete(file);
                            return true;
                        default:
                            return false;
                    }

                });
                popupMenu.show();
            });
        }

        @Override
        public int getItemCount() {
            return filesList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public TextView fileName;
            public ImageView fileIcon;
            public ImageButton optionButton;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                itemView.setSelected(true);

                this.fileIcon = itemView.findViewById(R.id.fileIcon);
                this.fileName = itemView.findViewById(R.id.fileName);
                this.optionButton = itemView.findViewById(R.id.optionButton);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addNewFolder() {
        try {
            //NewFolder Dialog Builder
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.modal, null);
            dialogBuilder.setView(dialogView);

            final EditText editTextNewFolderName = dialogView.findViewById(R.id.modalText);

            dialogBuilder.setTitle("New Folder");
            dialogBuilder.setMessage("Enter name of new folder");
            dialogBuilder.setPositiveButton("Done", (dialog, whichButton) -> {
                String newFolderName = editTextNewFolderName.getText().toString();
                File newFolder = new File(dirPath + "/" + newFolderName);
                // create
                if (newFolder.exists()){
                    Toast.makeText(this, "File is exist.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (newFolder.mkdir()){
                    //Refresh ListView
                    refreshListFiles();
                    refreshAdapter();
                }
                else Toast.makeText(this, "Can not create new folder.", Toast.LENGTH_LONG).show();
            });
            dialogBuilder.setNegativeButton("Cancel", (dialog, whichButton) -> {
                //pass
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addNewText() {
        try {
            //NewFolder Dialog Builder
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.modal, null);
            dialogBuilder.setView(dialogView);

            final EditText editTextNewFolderName = dialogView.findViewById(R.id.modalText);

            dialogBuilder.setTitle("New Text File");
            dialogBuilder.setMessage("Enter name of new text file");
            dialogBuilder.setPositiveButton("Done", (dialog, whichButton) -> {
                String newTextFileName = editTextNewFolderName.getText().toString();
                File newTextFile = new File(dirPath + "/" + newTextFileName + ".txt");
                // create
                if (newTextFile.exists()){
                    Toast.makeText(this, "File is exist.", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    if (newTextFile.createNewFile()){
                        //Refresh ListView
                        refreshListFiles();
                        refreshAdapter();
                    }
                    else Toast.makeText(this, "Can not create new file.", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            dialogBuilder.setNegativeButton("Cancel", (dialog, whichButton) -> {
                //pass
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void rename(FileModal file) {
        try{
            //RenameFolder Dialog Builder
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.modal, null);
            dialogBuilder.setView(dialogView);

            final EditText modalText = dialogView.findViewById(R.id.modalText);
            modalText.setText(file.getName());

            dialogBuilder.setTitle("Rename Folder");
            dialogBuilder.setMessage("Enter new name of folder");
            dialogBuilder.setPositiveButton("Done", (dialog, whichButton) -> {
                File fRename = new File(dirPath+ "/" + modalText.getText().toString());
                if (!fRename.equals(file.getFile())){
                    if (fRename.exists()){
                        Toast.makeText(this, "File is exist", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (file.getFile().renameTo(fRename)){
                    //Refresh ListView
                    refreshListFiles();
                    refreshAdapter();
                }
                else Toast.makeText(this, "Can not rename  folder.", Toast.LENGTH_LONG).show();
            });
            dialogBuilder.setNegativeButton("Cancel", (dialog, whichButton) -> {
                //pass
            });
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }catch (Exception e){
            Log.v("TAG", e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void copy(MenuItem item) {
        pasteFab.setVisibility(View.VISIBLE);
        resetDir();
        cutOrCopy = COPY;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cut(MenuItem item) {
        pasteFab.setVisibility(View.VISIBLE);
        resetDir();
        cutOrCopy = CUT;
    }

    private void resetHighlight() {
        for (FileModal file : selectedFilesList){
            View itemView = Objects.requireNonNull(recyclerView.findViewHolderForLayoutPosition(selectedFilesList.indexOf(file))).itemView;
            itemView.setBackgroundColor(Color.WHITE);
        }
    }

    private void select(FileModal file) {
        //if file is already selected, then deselect
        if (selectedFilesList.contains(file)){
            selectedFilesList.remove(file);
            file.setSelected(false);
        } else {
            //select
            selectedFilesList.add(file);
            file.setSelected(true);
        }
        optionsMenu.findItem(R.id.menu_item_copy).setVisible(!selectedFilesList.isEmpty());
        optionsMenu.findItem(R.id.menu_item_move).setVisible(!selectedFilesList.isEmpty());

        refreshAdapter();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void paste(){
        if (!selectedFilesList.isEmpty()) {
            for (FileModal file : selectedFilesList){
                try {
                    if (cutOrCopy == COPY){
                        FileUtils.copyToDirectory(file.getFile(), new File(dirPath));
                    }
                    else if (cutOrCopy == CUT) {
                        FileUtils.moveToDirectory(file.getFile(), new File(dirPath), false);
                    }
                } catch (Exception e){
                    Toast.makeText(this, "Can not paste here.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
        resetSelect();
        refreshListFiles();
        refreshAdapter();
        pasteFab.setVisibility(View.GONE);
    }

    private void resetSelect() {
        selectedFilesList.clear();
        cutOrCopy = 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void delete(FileModal file) {
        try {
            FileUtils.delete(file.getFile());
            //Refresh ListView
            refreshListFiles();
            refreshAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


