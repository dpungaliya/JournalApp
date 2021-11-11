package androidsamples.java.journalapp;

import java.util.List;

import androidsamples.java.journalapp.JournalEntry;
import androidsamples.java.journalapp.JournalEntryDao;
import androidsamples.java.journalapp.JournalRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EntryListViewModel extends ViewModel {

    private final JournalRepository repository;

    public EntryListViewModel(){
        repository = JournalRepository.getInstance();
    }

    public LiveData<List<JournalEntry>> getAllEntries(){
        return repository.getAllEntries();
    }

    public void insert(JournalEntry journalEntry){
        repository.insert(journalEntry);
    }

}