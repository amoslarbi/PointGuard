package morelife.pointguard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    Animation Open, Close, Clockwise, Anticlockwise;
    private FloatingActionButton nLogOutBtn, usee, logg;
    TextView textView19, textView20;

    boolean isOpen = false;
    boolean favSelected = true;

    public Upload() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_upload, container, false);

        nLogOutBtn = (FloatingActionButton) view.findViewById(R.id.forward);
        usee = (FloatingActionButton) view.findViewById(R.id.usee);
        logg = (FloatingActionButton) view.findViewById(R.id.logoutt);
        textView19 = (TextView) view.findViewById(R.id.textView19);
        textView20 = (TextView) view.findViewById(R.id.textView20);

        Open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        Close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        Clockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_clockwise);
        Anticlockwise = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anticlockwise);

        nLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOpen){

                    usee.startAnimation(Close);
                    logg.startAnimation(Close);
                    textView19.startAnimation(Close);
                    textView20.startAnimation(Close);
                    nLogOutBtn.startAnimation(Anticlockwise);
                    usee.setClickable(false);
                    logg.setClickable(false);
                    isOpen = false;

                }
                else{

                    usee.startAnimation(Open);
                    logg.startAnimation(Open);
                    textView19.startAnimation(Open);
                    textView20.startAnimation(Open);
                    nLogOutBtn.startAnimation(Clockwise);
                    usee.setClickable(true);
                    logg.setClickable(true);
                    isOpen = true;

                }

            }
        });



        return view;
    }


}
