package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import androidsamples.java.journalapp.EntryDetailsViewModel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class DatePickerFragment extends DialogFragment {

  EntryDetailsViewModel entryDetailsViewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    entryDetailsViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);
  }

  @NonNull
  public static DatePickerFragment newInstance(Date date) {

    return null;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    Calendar calendar=Calendar.getInstance();
    calendar.setTime(new Date());

    return new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
      String date = String.format( "%02d", d);
      String month = String.format ("%02d", m+1);
      String year = String.format ( "%04d", y);

      entryDetailsViewModel.setDateDate(date);
      entryDetailsViewModel.setDateMonth(month);
      entryDetailsViewModel.setDateYear(year);
      entryDetailsViewModel.updateDateLiveData();
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

  }
}