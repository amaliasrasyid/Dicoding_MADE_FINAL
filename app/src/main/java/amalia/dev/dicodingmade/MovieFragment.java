package amalia.dev.dicodingmade;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie,container,false);

        RecyclerView rv = view.findViewById(R.id.rv_item);
        MovieAdapter adapter = new MovieAdapter(getActivity());

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //set adapter ke recyclerview
        rv.setAdapter(adapter);
        return view;
    }

}
