package androidsamples.java.journalapp;

import android.content.Context;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

public class JournalRepository {
    private static final String DATABASE_NAME = "journal_entry_table";

    private static JournalRepository sInstance;
    private final JournalEntryDao mJournalEntryDao;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private JournalRepository (Context context){
        JournalRoomDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                JournalRoomDatabase.class,DATABASE_NAME).build();

        mJournalEntryDao = db.journalEntryDao();
    }

    public static void init(Context context){
        if (sInstance == null){
            sInstance = new JournalRepository(context);
        }
    }

    public static JournalRepository getInstance(){
        if (sInstance == null)
            throw new IllegalStateException("JournalRepository must be initialized");

        return sInstance;
    }

    public void insert(JournalEntry journalEntry){
        mExecutor.execute(() -> mJournalEntryDao.insert(journalEntry));
    }

    public void update(JournalEntry journalEntry){
        mExecutor.execute(() -> mJournalEntryDao.update(journalEntry));
    }

    public void delete(JournalEntry journalEntry){
        mExecutor.execute(() -> mJournalEntryDao.delete(journalEntry));
    }

    public LiveData<List<JournalEntry>> getAllEntries(){
        return mJournalEntryDao.getAllEntries();
    }

    public LiveData<JournalEntry> getEntry(UUID id){
        return mJournalEntryDao.getEntry(id);
    }

}