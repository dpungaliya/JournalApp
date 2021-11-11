package androidsamples.java.journalapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidsamples.java.journalapp.R;
import androidsamples.java.journalapp.JournalEntry;
import androidsamples.java.journalapp.JournalTypeConverters;
import androidsamples.java.journalapp.EntryListViewModel;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {

  private EntryListViewModel entryListViewModel;

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    entryListViewModel=new ViewModelProvider(this).get(EntryListViewModel.class);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_add_entry);
    floatingActionButton.setOnClickListener(v -> {
      JournalEntry entry = new JournalEntry("",new Time(new Date().getTime()),new Time(new Date().getTime()),new Date());
      entryListViewModel.insert(entry);
      Navigation.findNavController(v).navigate(androidsamples.java.journalapp.fragment.EntryListFragmentDirections.addEntryAction().setEntryId(entry.getUid().toString()));
    });

    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    EntryListAdapter adapter = new EntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);

    entryListViewModel.getAllEntries().observe(getActivity(), adapter::setEntries);

    return view;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_entry_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId()==R.id.menu_info){
      Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(androidsamples.java.journalapp.fragment.EntryListFragmentDirections.infoAction());
    }
    return super.onOptionsItemSelected(item);
  }

  private class EntryViewHolder extends RecyclerView.ViewHolder{
    private final TextView txtTitle;
    private final TextView txtDate;
    private final TextView txtStartTime;
    private final TextView txtEndTime;
    private final JournalTypeConverters converters;
    private JournalEntry entry;

    public EntryViewHolder(@NonNull View itemView) {
      super(itemView);

      converters=new JournalTypeConverters();
      txtTitle=itemView.findViewById(R.id.txt_item_title);
      txtDate=itemView.findViewById(R.id.txt_item_date);
      txtStartTime=itemView.findViewById(R.id.txt_item_start_time);
      txtEndTime=itemView.findViewById(R.id.txt_item_end_time);
      itemView.setOnClickListener(view -> Navigation.findNavController(view).navigate(androidsamples.java.journalapp.fragment.EntryListFragmentDirections.addEntryAction().setEntryId(converters.fromUUID(entry.getUid()))));

    }

    void bind(JournalEntry journalEntry){
      entry = journalEntry;
      txtTitle.setText(entry.getTitle());
      txtDate.setText(converters.fromDate(entry.getDate()));
      txtStartTime.setText(converters.fromTime(entry.getStartTime()));
      txtEndTime.setText(converters.fromTime(entry.getEndTime()));
    }

  }

  private class EntryListAdapter extends  RecyclerView.Adapter<EntryViewHolder>{

    private final LayoutInflater inflater;
    private List<JournalEntry> entries;

    public EntryListAdapter(Context context){
      inflater=LayoutInflater.from(context);
    }

    public void setEntries(List<JournalEntry> journalEntries){
      entries= journalEntries;
      notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView=inflater.inflate(R.layout.fragment_entry, parent, false);
      return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
      if (entries!=null){
        JournalEntry curr= entries.get(position);
        //Log.d("The current tag", Integer.toString(position) +" "+ entries.get(position).getStartTime().toString());
        holder.bind(curr);
      }
    }

    @Override
    public int getItemCount() {
      return (entries==null)?0:entries.size();
    }
  }


}