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

public class Department extends Fragment {

    String[] titles = new String[]{"BSC Business Administration", "BSC Computer Engineering", "BSC Information Technology",
            "BSC Telecom Engineering", "MBA Strategic Management",
            "BBA Human Resourse Management", "BBA Marketing", "MAB Banking and Finance", "MED Educational Administration and leadership"
            , "BBA Accounting", "BSC Agribusiness","BSC Mathematics and Statistics","BSC Computer Science",
            "BED English Language"};
    ListView lv;
    TextView text;
    String value;

    public Department() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_department, container, false);

        text = (TextView) view.findViewById(R.id.fragmentText);//Find textview Id

        if(getArguments()!=null) {
            Bundle bundle = getArguments();
            //String customer_id = (String) getArguments().get("depa");
            value =  bundle.getString("depa", "default");
            //value = bundle.getString("depa", "default");

        }
        else
        {
            Toast.makeText(getActivity(), "hello",
                    Toast.LENGTH_SHORT).show();


        }
        text.setText(value);

        lv = (ListView) view.findViewById(R.id.idListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, titles);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                String food = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getActivity(), food, Toast.LENGTH_SHORT).show();
//                Intent StartIntent = new Intent(getApplicationContext(), Department.class);
//
//                //StartIntent.putExtra("food",food);
//
//                startActivity(StartIntent);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, new Upload()).commit();
                fragmentTransaction.addToBackStack(null);

            }

        });

        return view;
    }

}
