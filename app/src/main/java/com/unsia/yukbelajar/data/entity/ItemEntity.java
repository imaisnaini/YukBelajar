package com.unsia.yukbelajar.data.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "item",
        foreignKeys = @ForeignKey(
                entity = KategoriEntity.class,
                parentColumns = "kategoriId",
                childColumns = "kategoriId",
                onDelete = ForeignKey.CASCADE
        )
)
public class ItemEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int kategoriId;   // FK
    public String nama;
    public String gambar;

    public ItemEntity(int kategoriId, String nama, String gambar) {
        this.kategoriId = kategoriId;
        this.nama = nama;
        this.gambar = gambar;
    }
}


