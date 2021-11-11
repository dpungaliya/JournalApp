package androidsamples.java.journalapp;

import java.util.List;
import java.util.UUID;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface JournalEntryDao {

    @Insert
    void insert(JournalEntry journalEntry);

    @Update
    void update(JournalEntry journalEntry);

    @Query("SELECT * FROM journal_entry_table")
    LiveData<List<JournalEntry>> getAllEntries();

    @Query("SELECT * FROM journal_entry_table WHERE uid=(:id)")
    LiveData<JournalEntry> getEntry(UUID id);

    @Delete
    void delete(JournalEntry journalEntry);
}