package androidsamples.java.journalapp;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

public class JournalTypeConverters {

    @TypeConverter
    public UUID toUUID(@NonNull String uuid){
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(@NonNull UUID uuid){
        return uuid.toString();
    }

    @TypeConverter
    public Date toDate(@NonNull String date){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date returnDate = null;

        try {
            returnDate = format.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDate;
    }

    @TypeConverter
    public String fromDate(@NonNull Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return df.format(date);
    }

    @TypeConverter
    public Time toTime(@NonNull String time){


        DateFormat format = new SimpleDateFormat("HH:mm");
        Time returnTime=null;
        try {
            long ms = format.parse(time).getTime();
            returnTime = new Time(ms);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnTime;
    }

    @TypeConverter
    public String fromTime(@NonNull Time time){
        DateFormat df = new SimpleDateFormat("HH:mm");

        return df.format(time);
    }

}