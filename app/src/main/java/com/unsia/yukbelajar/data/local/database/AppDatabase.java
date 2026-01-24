package com.unsia.yukbelajar.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.unsia.yukbelajar.data.local.dao.ItemDao;
import com.unsia.yukbelajar.data.local.dao.KategoriDao;
import com.unsia.yukbelajar.data.local.dao.QuizDao;
import com.unsia.yukbelajar.data.local.dao.UserDao;
import com.unsia.yukbelajar.data.local.entity.ItemEntity;
import com.unsia.yukbelajar.data.local.entity.KategoriEntity;
import com.unsia.yukbelajar.data.local.entity.QuizEntity;
import com.unsia.yukbelajar.data.local.entity.QuizQuestionEntity;
import com.unsia.yukbelajar.data.local.entity.QuizResultEntity;
import com.unsia.yukbelajar.data.local.entity.UserEntity;

@Database(
        entities = {
                UserEntity.class,
                KategoriEntity.class,
                ItemEntity.class,
                QuizEntity.class,
                QuizQuestionEntity.class,
                QuizResultEntity.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract KategoriDao kategoriDao();
    public abstract ItemDao itemDao();
    public abstract QuizDao quizDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    Log.d("ROOM_DB", "Creating database...");
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "yuk_belajar_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
