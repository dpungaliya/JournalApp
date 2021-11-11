package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import androidsamples.java.journalapp.EntryDetailsViewModel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TimePickerFragment extends DialogFragment {

  private EntryDetailsViewModel entryDetailsViewModel;
  private int startOrEnd;
  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    return null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    entryDetailsViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);
    startOrEnd = (int) getArguments().get("startOrEndTime");
    Log.d("TAGGING", Integer.toString(startOrEnd));
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return new TimePickerDialog(requireContext(), (tp, hm, m)->{
      String timeHours = String.format("%02d", hm);
      String timeMins= String.format("%02d",m);

      if (startOrEnd == 0) {
        entryDetailsViewModel.setStartTimeHours(timeHours);
        entryDetailsViewModel.setStartTimeMins(timeMins);
        entryDetailsViewModel.updateStartTimeLiveData();
        Log.d("Set in the time picker", entryDetailsViewModel.getStartTimeHours());
      }

      else{
        entryDetailsViewModel.setEndTimeHours(timeHours);
        entryDetailsViewModel.setEndTimeMins(timeMins);
        entryDetailsViewModel.updateEndTimeLiveData();
        Log.d("Set in the time picker", entryDetailsViewModel.getEndTimeHours());
      }
    }, 0, 0, false);
  }
}