package androidsamples.java.journalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import androidsamples.java.journalapp.R;
import androidsamples.java.journalapp.JournalEntry;
import androidsamples.java.journalapp.JournalTypeConverters;
import androidsamples.java.journalapp.EntryDetailsViewModel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {
  private Button dateButton ;
  private Button startTimeButton;
  private Button endTimeButton ;
  private Button saveButton;
  private EditText editText;
  private EntryDetailsViewModel entryDetailsViewModel;
  private JournalTypeConverters converters;
  private JournalEntry journalEntry;
  public static boolean loadEntry =false;//TODO implement a better way tod o this ?

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    converters= new JournalTypeConverters();

    entryDetailsViewModel = new ViewModelProvider(getActivity()).get(EntryDetailsViewModel.class);
    UUID uuid = converters.toUUID((String) getArguments().getSerializable("entryId"));
    Log.d("TAG", "Title text from entry details view model post saving: "+entryDetailsViewModel.getTitleTxt());
    //TODO address rotation
    if (loadEntry == false) {
      Log.d("TAG", "Called when title txt is null");
      entryDetailsViewModel.loadEntry(uuid);
      loadEntry=true;
    }

  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);

    dateButton = view.findViewById(R.id.btn_entry_date);
    startTimeButton = view.findViewById(R.id.btn_start_time);
    endTimeButton = view.findViewById(R.id.btn_end_time);
    saveButton = view.findViewById(R.id.btn_save);
    editText = view.findViewById(R.id.edit_title);



    editText.addTextChangedListener(new TextWatcher() {

      @Override
      public void afterTextChanged(Editable s) {}

      @Override
      public void beforeTextChanged(CharSequence s, int start,
                                    int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start,
                                int before, int count) {
        entryDetailsViewModel.setTitleTxt(s.toString());

      }
    });


    dateButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(androidsamples.java.journalapp.fragment.EntryDetailsFragmentDirections.datePickerAction()));
    startTimeButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(androidsamples.java.journalapp.fragment.EntryDetailsFragmentDirections.timePickerAction().setStartOrEndTime(0)));
    endTimeButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(androidsamples.java.journalapp.fragment.EntryDetailsFragmentDirections.timePickerAction().setStartOrEndTime(1)));
    saveButton.setOnClickListener(v -> {

      journalEntry.setTitle(editText.getText().toString());
      journalEntry.setDate(converters.toDate(dateButton.getText().toString()));
      journalEntry.setStartTime(converters.toTime(startTimeButton.getText().toString()));
      journalEntry.setEndTime(converters.toTime(endTimeButton.getText().toString()));

      entryDetailsViewModel.saveEntry(journalEntry);
      getActivity().onBackPressed();
    });

    if (!loadEntry){
      editText.setText(entryDetailsViewModel.getTitleTxt());
      startTimeButton.setText(entryDetailsViewModel.getStartTimeTxt());
      endTimeButton.setText(entryDetailsViewModel.getEndTimeTxt());
      dateButton.setText(entryDetailsViewModel.getDateTxt());
    }

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    entryDetailsViewModel.getEntryLiveData().observe(getActivity(),
            journalEntry -> {
              if (journalEntry != null) {
                this.journalEntry = journalEntry;
                if (loadEntry) {
                  editText.setText(journalEntry.getTitle());//same with this ?
                  startTimeButton.setText(converters.fromTime(journalEntry.getStartTime()));//should it consider getting this from the viewmodel?
                  endTimeButton.setText(converters.fromTime(journalEntry.getEndTime()));
                  dateButton.setText(converters.fromDate(journalEntry.getDate()));

                  entryDetailsViewModel.setTitleTxt(editText.getText().toString());
                  entryDetailsViewModel.setDateTxt(dateButton.getText().toString());
                  entryDetailsViewModel.setStartTimeTxt(startTimeButton.getText().toString());
                  entryDetailsViewModel.setEndTimeTxt(endTimeButton.getText().toString());
                }
              }
            });

    entryDetailsViewModel.getDateLiveData().observe(getActivity(), str->{
      if (str!=null){
        dateButton.setText(str);
      }
    });

    entryDetailsViewModel.getStartTimeLiveData().observe(getActivity(), str->{
      if (str!=null){
        startTimeButton.setText(str);
      }
    });


    entryDetailsViewModel.getEndTimeLiveData().observe(getActivity(), str->{
      if (str!=null){
        endTimeButton.setText(str);
      }
    });
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_entry_detail, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId()==R.id.menu_delete_entry){
      DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          switch (which){
            case DialogInterface.BUTTON_POSITIVE:
              //Yes button clicked
              entryDetailsViewModel.deleteEntry(journalEntry);
              getActivity().onBackPressed();
              break;

            case DialogInterface.BUTTON_NEGATIVE:
              //No button clicked
              break;
          }
        }
      };

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setMessage("Are you sure you want to delete?").setPositiveButton("Yes", dialogClickListener)
              .setNegativeButton("No", dialogClickListener).show();
    }

    else if (item.getItemId()==R.id.menu_share_entry){
      Intent intent = new Intent(Intent.ACTION_SEND);
      String shareBody="Look what I have been upto: "+editText.getText().toString()+" on " + dateButton.getText().toString() +", "+ startTimeButton.getText().toString()+" to "+ endTimeButton.getText().toString();
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_TEXT,shareBody);
      startActivity(intent);
    }
    return super.onOptionsItemSelected(item);
  }
}