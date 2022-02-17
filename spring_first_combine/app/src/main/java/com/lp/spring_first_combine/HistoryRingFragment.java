package com.lp.spring_first_combine;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class HistoryRingFragment extends Fragment {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    /*
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<Ring> arrayList = new ArrayList<>();
    ArrayAdapter<Ring> arrayAdapter;
    Ring ring;


    String[] actionMovies = {"Final Score","Venom","Overlord","Hunter Killer","Beirut","Tomb Raider","Den of Thieves","Upgrade",
            "Aquaman","Mohawk","Braven","Mandy","Black Panther","The Commuter","Revenge"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ring,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listviewring);

        arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,actionMovies);

        //setting ArrayAdapter on listView
        listView.setAdapter(arrayAdapter);
    }

     */
}
