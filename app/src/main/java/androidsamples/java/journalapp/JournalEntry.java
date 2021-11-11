package androidsamples.java.journalapp;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "journal_entry_table")
public class JournalEntry {

    @PrimaryKey
    @ColumnInfo (name = "uid")
    @NonNull
    private UUID uid;

    @ColumnInfo (name= "title")
    private String title;

    @ColumnInfo (name = "startTime")
    private Time startTime;

    @ColumnInfo (name = "endTime")
    private Time endTime;

    @ColumnInfo (name = "date")
    private Date date;

    public JournalEntry(@NonNull String title, @NonNull Time startTime, @NonNull Time endTime, @NonNull Date date){
        this.uid = UUID.randomUUID();
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    @NonNull
    public UUID getUid() {
        return uid;
    }

    public void setUid(@NonNull UUID uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }


    public Time getStartTime() {
        return startTime;
    }



    public Time getEndTime() {
        return endTime;
    }


    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}