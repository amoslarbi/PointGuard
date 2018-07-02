package morelife.pointguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

import static morelife.pointguard.MainActivity.hello;

public class Schools extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar, searchtollbar;
    Menu search_menu;
    MenuItem item_search;

    TextView Unm, Uem, textViewww;
    ImageView Uimmg;
//
//    String [] titles = {"Valley View University", "University of Ghana", "Kwame Nkrumah University of Science and Technology"
//    , "University of Cape Coast", "University of Education, Winneba", "University for Development Studies",
//            "University of Professional Studies", "University of Mines and Technology", "Ashesi University",
//    "Central University College", "Wisconsin International University College"};
//
//    String [] descriptions = {"Excellence*Integrity*Service", "Integri Procedamus", "Nyansapɔ wɔsane no badwenma",
//    "Veritas Nobis Lumen", "Education for Service", "Knowledge for Service", "Scholarship with Professionalism",
//    "Knowledge*Truth*Excellence", "Scholarship*Leadership*Citizenship", "Faith*Integrity*Excellence",
//    "Peace*Harmony*Freedom*Truth*Knowledge"};
//
//    int [] images = {R.drawable.valleyview, R.drawable.legon, R.drawable.tech, R.drawable.ucclogo,
//            R.drawable.winneba, R.drawable.uds, R.drawable.ups, R.drawable.mines, R.drawable.ashesi, R.drawable.central,
//            R.drawable.wisconsin};
//    ListView lv;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content,new Frag2()).commit();

        setSearchtollbar();
        getSupportActionBar().setTitle("");

        textViewww = (TextView) findViewById(R.id.textViewww);
//        lv = (ListView) findViewById(R.id.idListView);
//        MyAdapter adapter = new MyAdapter(getApplicationContext(), titles, descriptions, images);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
//                String food = String.valueOf(parent.getItemAtPosition(position));
//                //Toast.makeText(getApplicationContext(), food, Toast.LENGTH_SHORT).show();
////                Intent StartIntent = new Intent(getApplicationContext(), Department.class);
////
////                //StartIntent.putExtra("food",food);
////
////                startActivity(StartIntent);
//
//                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content, new Frag2()).commit();
//
//                Toast.makeText(getApplicationContext(), "Open...",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);

        View hvv = nav.getHeaderView(0);

        Uimmg = (ImageView) hvv.findViewById(R.id.imageView);
        Unm = (TextView) hvv.findViewById(R.id.textView1);
        Uem = (TextView) hvv.findViewById(R.id.textView);

        SharedPreferences sh = getSharedPreferences(hello, 0);
        Unm.setText(sh.getString("nmm", "nmoo"));
        Uem.setText(sh.getString("nmma", "nmoo1"));
        textViewww.setText(sh.getString("nmmaa", "nmoo2"));

        String lolo = textViewww.getText().toString();
        Uri c = Uri.parse(lolo);

        Picasso.with(getApplicationContext()).load(c).into(Uimmg);

        //add this line to display menu1 when the activity is loaded

    }

//    class MyAdapter extends ArrayAdapter {
//        int[] imageArray;
//        String[] titleArray;
//        String[] descArray;
//
//        public MyAdapter(Context context, String[] titles1, String[] description1, int [] img1){
//
//            super(context, R.layout.schoolsdesign,R.id.idTitle, titles1);
//            this.imageArray=img1;
//            this.titleArray=titles1;
//            this.descArray=description1;
//
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent){
//
//            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View row = inflater.inflate(R.layout.schoolsdesign,parent,false);
//
//            ImageView myImage = (ImageView) row.findViewById(R.id.idPic);
//            TextView myTitle = (TextView) row.findViewById(R.id.idTitle);
//            TextView myDescription = (TextView) row.findViewById(R.id.idDescription);
//
//            myImage.setImageResource(imageArray[position]);
//            myTitle.setText(titleArray[position]);
//            myDescription.setText(descArray[position]);
//
//            return row;
//
//        }
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, new Frag2()).commit();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        //displaySelectedScreen(item.getItemId());

        return true;
    }

//    private void displaySelectedScreen(int itemId) {
//
//        //creating fragment object
//        Fragment fragment = null;
//
//        //initializing the fragment object which is selected
//        switch (itemId) {
//            case R.id.nav_camera:
//                fragment = new Department();
//                break;
//
//        }
//
//        //replacing the fragment
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content, fragment);
//            ft.commit();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//    }

    ///////////////////////////////////////////////////
    /////////// Code for Searchtollbar///// ///////////
    ///////////////////////////////////////////////////

    ////////below must be on///////////////

    public void setSearchtollbar()
    {
        searchtollbar = (Toolbar) findViewById(R.id.searchtoolbar);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.schools);
            search_menu=searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    else
                        searchtollbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            ////////below must be on///////////////

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar,1,true,false);

                        Toast.makeText(getApplicationContext(), "Open...",
                                Toast.LENGTH_SHORT).show();

                    }
                    else
                        searchtollbar.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "Close...",
                            Toast.LENGTH_SHORT).show();

                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();


        } else
            Log.d("toolbar", "setSearchtollbar: NULL");
    }

    ////////below must be on///////////////

    public void initSearchView()
    {
        final SearchView searchView = (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        ////////below must be on///////////////

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            ////////below must be on///////////////

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            ////////below must be on///////////////

            public void callSearch(String query) {
                //Do searching
                String st = query.toString();
                //filter(st);
                Log.i("query", "" + query);

            }

        });

    }

    ////////below must be on///////////////

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }


}
