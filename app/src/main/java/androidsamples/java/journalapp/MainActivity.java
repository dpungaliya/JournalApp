package androidsamples.java.journalapp;

import androidsamples.java.journalapp.EntryDetailsFragment;
import androidsamples.java.journalapp.EntryDetailsViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

  private EntryDetailsViewModel entryDetailsViewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    entryDetailsViewModel = new ViewModelProvider(this).get(EntryDetailsViewModel.class);
  }

  @Override
  public void onBackPressed() {
    EntryDetailsFragment.loadEntry=false;
    super.onBackPressed();
  }
}