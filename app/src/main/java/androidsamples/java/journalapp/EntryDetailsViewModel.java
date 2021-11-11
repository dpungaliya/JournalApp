package androidsamples.java.journalapp;

import java.util.UUID;

import androidsamples.java.journalapp.JournalEntry;
import androidsamples.java.journalapp.JournalRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class EntryDetailsViewModel extends ViewModel {

    private final JournalRepository repository;
    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> dateLiveData= new MutableLiveData<>();
    private final MutableLiveData<String> startTimeLiveData= new MutableLiveData<>();
    private final MutableLiveData<String> endTimeLiveData = new MutableLiveData<>();

    private String startTimeHours;
    private String startTimeMins;
    private String endTimeHours;
    private String endTimeMins;
    private String dateMonth;
    private String dateDate;
    private String dateYear;

    private String titleTxt;
    private String startTimeTxt;
    private String endTimeTxt;
    private String dateTxt;

    public String getTitleTxt() {
        return titleTxt;
    }

    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
    }

    public String getStartTimeTxt() {
        return startTimeTxt;
    }

    public void setStartTimeTxt(String startTimeTxt) {
        this.startTimeTxt = startTimeTxt;
    }

    public String getEndTimeTxt() {
        return endTimeTxt;
    }

    public void setEndTimeTxt(String endTimeTxt) {
        this.endTimeTxt = endTimeTxt;
    }

    public String getDateTxt() {
        return dateTxt;
    }

    public void setDateTxt(String dateTxt) {
        this.dateTxt = dateTxt;
    }

    public EntryDetailsViewModel() {
        this.repository =JournalRepository.getInstance();
    }

    public LiveData<JournalEntry> getEntryLiveData(){
        return Transformations.switchMap(entryIdLiveData, repository::getEntry);
    }

    public LiveData<String> getDateLiveData(){
        return dateLiveData;
    }

    public LiveData<String> getStartTimeLiveData(){
        return startTimeLiveData;
    }

    public LiveData<String> getEndTimeLiveData(){
        return endTimeLiveData;
    }

    public void updateDateLiveData(){
        dateTxt=dateDate+"/"+dateMonth+"/"+dateYear;
        dateLiveData.setValue(dateDate+"/"+dateMonth+"/"+dateYear);
    }

    public void updateStartTimeLiveData(){
        startTimeTxt=startTimeHours+":"+startTimeMins;
        startTimeLiveData.setValue(startTimeHours+":"+startTimeMins);
    }


    public void updateEndTimeLiveData(){
        endTimeTxt = endTimeHours+":"+endTimeMins;
        endTimeLiveData.setValue(endTimeHours+":"+endTimeMins);
    }

    public void loadEntry(UUID entryID){
        entryIdLiveData.setValue(entryID);
    }

    public void saveEntry(JournalEntry entry){
        repository.update(entry);
    }

    public void deleteEntry(JournalEntry entry){
        repository.delete(entry);
    }

    public String getStartTimeHours() {
        return startTimeHours;
    }

    public void setStartTimeHours(String startTimeHours) {
        this.startTimeHours = startTimeHours;
    }

    public String getStartTimeMins() {
        return startTimeMins;
    }

    public void setStartTimeMins(String startTimeMins) {
        this.startTimeMins = startTimeMins;
    }

    public String getEndTimeHours() {
        return endTimeHours;
    }

    public void setEndTimeHours(String endTimeHours) {
        this.endTimeHours = endTimeHours;
    }

    public String getEndTimeMins() {
        return endTimeMins;
    }

    public void setEndTimeMins(String endTimeMins) {
        this.endTimeMins = endTimeMins;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public String getDateDate() {
        return dateDate;
    }

    public void setDateDate(String dateDate) {
        this.dateDate = dateDate;
    }

    public String getDateYear() {
        return dateYear;
    }

    public void setDateYear(String dateYear) {
        this.dateYear = dateYear;
    }
}