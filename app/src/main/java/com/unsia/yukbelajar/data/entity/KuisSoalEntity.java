package com.unsia.yukbelajar.data.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "kuis_soal",
        foreignKeys = @ForeignKey(
                entity = KuisEntity.class,
                parentColumns = "id",
                childColumns = "kuisId",
                onDelete = ForeignKey.CASCADE
        )
)
public class KuisSoalEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int kuisId;
    public String gambarSoal;
    public String jawabanBenar;
    public String jawabanSalah;

    public KuisSoalEntity(int kuisId, String gambarSoal, String jawabanBenar, String jawabanSalah) {
        this.kuisId = kuisId;
        this.gambarSoal = gambarSoal;
        this.jawabanBenar = jawabanBenar;
        this.jawabanSalah = jawabanSalah;
    }
}

