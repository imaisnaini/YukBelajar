package com.unsia.yukbelajar.data.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.unsia.yukbelajar.data.entity.ItemEntity;

import java.util.List;

@Dao
public interface ItemDao {

    // Insert single item
    @Insert
    void insert(ItemEntity item);

    // Insert multiple items (useful for prepopulate or Firebase sync)
    @Insert
    void insertAll(List<ItemEntity> items);

    // Update item
    @Update
    void update(ItemEntity item);

    // Delete item
    @Delete
    void delete(ItemEntity item);

    // Get all items
    @Query("SELECT * FROM item")
    List<ItemEntity> getAllItems();

    // Get items by category (FK)
    @Query("SELECT * FROM item WHERE kategoriId = :kategoriId")
    List<ItemEntity> getItemsByKategori(int kategoriId);

    // Get item by ID
    @Query("SELECT * FROM item WHERE id = :itemId LIMIT 1")
    ItemEntity getItemById(int itemId);

    // Delete items by category (optional but useful)
    @Query("DELETE FROM item WHERE kategoriId = :kategoriId")
    void deleteByKategori(int kategoriId);
}

