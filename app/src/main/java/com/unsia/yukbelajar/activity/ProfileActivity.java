package com.unsia.yukbelajar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.unsia.yukbelajar.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private LinearLayout menuEdit, menuLogout;
    private TextView txtName;

    private SharedPreferences prefs;
    private static final int REQ_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.imgProfile);
        menuEdit = findViewById(R.id.menuEdit);
        menuLogout = findViewById(R.id.menuLogout);
        txtName = findViewById(R.id.txtName);

        prefs = getSharedPreferences("profile_pref", MODE_PRIVATE);

        loadProfile();

        // FOTO PROFILE
        imgProfile.setOnClickListener(v -> showPhotoOption());

        // ✅ EDIT PROFILE (INI YANG FIX)
        menuEdit.setOnClickListener(v -> showEditNameDialog());

        // LOGOUT
        menuLogout.setOnClickListener(v -> finish());
    }

    // ================= FOTO =================

    private void showPhotoOption() {
        String[] options = {"Lihat Foto", "Ganti Foto", "Hapus Foto"};

        new AlertDialog.Builder(this)
                .setTitle("Foto Profil")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) viewPhoto();
                    if (which == 1) checkPermissionAndPick();
                    if (which == 2) deletePhoto();
                })
                .show();
    }

    private void viewPhoto() {
        String path = prefs.getString("photo", null);
        if (path == null) {
            Toast.makeText(this, "Belum ada foto", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "File foto tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageView imageView = new ImageView(this);
        imageView.setImageURI(Uri.fromFile(file));
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(20, 20, 20, 20);

        new AlertDialog.Builder(this)
                .setTitle("Foto Profil")
                .setView(imageView)
                .setPositiveButton("Tutup", null)
                .show();
    }

    private void deletePhoto() {
        new AlertDialog.Builder(this)
                .setMessage("Yakin ingin menghapus foto profil?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    String path = prefs.getString("photo", null);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) file.delete();
                    }
                    prefs.edit().remove("photo").apply();
                    imgProfile.setImageResource(R.drawable.ic_profile_placeholder);
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    // ================= EDIT NAMA =================

    private void showEditNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Nama");

        final android.widget.EditText editText = new android.widget.EditText(this);
        editText.setHint("Masukkan nama");
        editText.setText(prefs.getString("name", ""));
        editText.setPadding(40, 30, 40, 30);

        builder.setView(editText);

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String newName = editText.getText().toString().trim();

            if (newName.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            prefs.edit().putString("name", newName).apply();
            txtName.setText(newName);

            Toast.makeText(this, "Nama berhasil diperbarui", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    // ================= GALLERY =================

    private void checkPermissionAndPick() {
        String permission = Build.VERSION.SDK_INT >= 33
                ? Manifest.permission.READ_MEDIA_IMAGES
                : Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    REQ_PERMISSION);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImage.launch(intent);
    }

    private final ActivityResultLauncher<Intent> pickImage =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri uri = result.getData().getData();
                            if (uri != null) {
                                saveImageToInternal(uri);
                            }
                        }
                    }
            );

    // ================= SAVE INTERNAL =================

    private void saveImageToInternal(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "profile.jpg");

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.close();

            prefs.edit().putString("photo", file.getAbsolutePath()).apply();
            imgProfile.setImageURI(Uri.fromFile(file));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal menyimpan foto", Toast.LENGTH_SHORT).show();
        }
    }

    // ================= LOAD =================

    private void loadProfile() {
        txtName.setText(prefs.getString("name", "Yuk Belajar"));

        String path = prefs.getString("photo", null);
        if (path != null) {
            imgProfile.setImageURI(Uri.fromFile(new File(path)));
        } else {
            imgProfile.setImageResource(R.drawable.ic_profile_placeholder);
        }
    }

    // ================= PERMISSION =================

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_PERMISSION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }
}
