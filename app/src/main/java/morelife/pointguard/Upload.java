package morelife.pointguard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Upload extends Fragment {

    String[] titles = {"Valley View University", "University of Ghana", "Kwame Nkrumah University of Science and Technology"
            , "University of Cape Coast", "University of Education, Winneba", "University for Development Studies",
            "University of Professional Studies", "University of Mines and Technology", "Ashesi University",
            "Central University College", "Wisconsin International University College"};

    ListView lv;

    public Upload() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_upload, container, false);

        return view;
    }


}
