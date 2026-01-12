package com.unsia.yukbelajar.data.local.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int userId;

    public String nama;
    public String kataSandi;
    public String jenisKelamin;
    public String fotoProfile;

    public UserEntity(String nama, String kataSandi, String jenisKelamin, String fotoProfile) {
        this.nama = nama;
        this.kataSandi = kataSandi;
        this.jenisKelamin = jenisKelamin;
        this.fotoProfile = fotoProfile;
    }
}
