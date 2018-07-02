package morelife.pointguard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Frag2 extends Fragment {

    TextView Unm, Uem, textViewww;

    String [] titles = {"Valley View University", "University of Ghana", "Kwame Nkrumah University of Science and Technology"
            , "University of Cape Coast", "University of Education, Winneba", "University for Development Studies",
            "University of Professional Studies", "University of Mines and Technology", "Ashesi University",
            "Central University College", "Wisconsin International University College"};

    String [] descriptions = {"Excellence*Integrity*Service", "Integri Procedamus", "Nyansapɔ wɔsane no badwenma",
            "Veritas Nobis Lumen", "Education for Service", "Knowledge for Service", "Scholarship with Professionalism",
            "Knowledge*Truth*Excellence", "Scholarship*Leadership*Citizenship", "Faith*Integrity*Excellence",
            "Peace*Harmony*Freedom*Truth*Knowledge"};

    int [] images = {R.drawable.valleyview, R.drawable.legon, R.drawable.tech, R.drawable.ucclogo,
            R.drawable.winneba, R.drawable.uds, R.drawable.ups, R.drawable.mines, R.drawable.ashesi, R.drawable.central,
            R.drawable.wisconsin};

    ListView lv;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    public Frag2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_frag2, container, false);

        mFragmentManager = getChildFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.content,new Frag2()).commit();

        textViewww = (TextView) view.findViewById(R.id.textViewww);
        lv = (ListView) view.findViewById(R.id.idListView);
        MyAdapter adapter = new MyAdapter(getActivity(), titles, descriptions, images);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                String food = String.valueOf(parent.getItemAtPosition(position));
                //Toast.makeText(getApplicationContext(), food, Toast.LENGTH_SHORT).show();
//                Intent StartIntent = new Intent(getApplicationContext(), Department.class);
//
//                //StartIntent.putExtra("food",food);
//
//                startActivity(StartIntent);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, new Department()).commit();
                fragmentTransaction.addToBackStack(null);

            }

        });



        return view;

    }
    class MyAdapter extends ArrayAdapter {
        int[] imageArray;
        String[] titleArray;
        String[] descArray;

        public MyAdapter(Context context, String[] titles1, String[] description1, int [] img1){

            super(context, R.layout.schoolsdesign,R.id.idTitle, titles1);
            this.imageArray=img1;
            this.titleArray=titles1;
            this.descArray=description1;

        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.schoolsdesign,parent,false);

            ImageView myImage = (ImageView) row.findViewById(R.id.idPic);
            TextView myTitle = (TextView) row.findViewById(R.id.idTitle);
            TextView myDescription = (TextView) row.findViewById(R.id.idDescription);

            myImage.setImageResource(imageArray[position]);
            myTitle.setText(titleArray[position]);
            myDescription.setText(descArray[position]);

            return row;

        }

    }


    }
