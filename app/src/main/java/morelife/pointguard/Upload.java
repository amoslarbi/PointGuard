package morelife.pointguard;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class Upload extends Fragment {

    String[] titles = {"Valley View University", "University of Ghana", "Kwame Nkrumah University of Science and Technology"
            , "University of Cape Coast", "University of Education, Winneba", "University for Development Studies",
            "University of Professional Studies", "University of Mines and Technology", "Ashesi University",
            "Central University College", "Wisconsin International University College"};

    ListView lv;
    Animation Open, Close, Clockwise, Anticlockwise;
    private FloatingActionButton nLogOutBtn, usee, logg, sposted;
    TextView textView19, textView20, textView5;
    Spinner s1, s2, s3;
    TextView scroll, textView8;
    EditText naa, edin;
    Button choose;
    //pdf adapter

    PdfAdapter pdfAdapter;
    ArrayList<String> depa;
    ArrayList<String> ccode;
    ArrayList<String> lec;
    //an array to hold the different pdf objects
    ArrayList<Pdf> pdfList= new ArrayList<Pdf>();

    boolean isOpen = false;
    boolean favSelected = true;

    AlertDialog.Builder sett;
    View mview;

    //Pdf request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //public static final String UPLOAD_URL = "http://192.168.43.234/pdf/pdf.php";
    public static final String UPLOAD_URL = "http://gtuc.one957.com/pdf.php";


    //Uri to store the image uri
    private Uri filePath;
    String path;

    ListView listView;

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
        listView = (ListView) view.findViewById(R.id.idListView);

        requestStoragePermission();

        textView5 = (TextView) view.findViewById(R.id.textView5);
        //textView5.setText(getIntent().getStringExtra("food"));
        //Toast.makeText(getApplicationContext(), textView5.getText().toString(), Toast.LENGTH_SHORT).show();
        HashMap<String, String> postData = new HashMap<String, String>();

        postData.put("depa", textView5.getText().toString());
        //postData.put("mobile","android");

        PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(),postData, new AsyncResponse() {
            @Override
            public void processFinish(String str) {

                if(str.contains("success")) {

                    try {
                        JSONArray jArray = new JSONArray(str);
                        //Toast.makeText(getApplicationContext(), String.valueOf(str), Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jsonObject = jArray.getJSONObject(i);


                            //Declaring a Pdf object to add it to the ArrayList  pdfList
                            Pdf pdf  = new Pdf();
                            String pdfName = jsonObject.getString("name");
                            String pdfUrl = jsonObject.getString("url");
                            String pdfDepartment = jsonObject.getString("program");
                            String pdfProgram = jsonObject.getString("coursename");
                            //String pdfAcademicyear = jsonObject.getString("academicyear");
                            String pdfLname = jsonObject.getString("lname");
                            pdf.setUrl(pdfUrl);
                            pdf.setDepartment(pdfDepartment);
                            pdf.setProgram(pdfProgram);
                            //pdf.setAcademicyear(pdfAcademicyear);
                            pdf.setLname(pdfLname);
                            pdfList.add(pdf);

                        }

                        pdfAdapter=new PdfAdapter(getActivity(),R.layout.depalist, pdfList);
                        listView.setAdapter(pdfAdapter);
                        pdfAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                if(str.contains("Failed")){

                    SweetAlertDialog su = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                    su.setTitleText("Sorry no questions available");
                    su.show();

                }

            }
        });

        task.execute( "http://gtuc.one957.com/getPdfs.php");
        //task.execute( "http://192.168.43.234/pdf/getPdfs.php");
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getActivity(), "Cannot Connect to server  ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getActivity(), "URL Error ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getActivity(), "Protocol Error ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getActivity(), "Encoding Error ", Toast.LENGTH_SHORT).show();

            }
        });

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


        usee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                sett = new AlertDialog.Builder(getActivity());
                mview = getLayoutInflater().inflate(R.layout.uploadpdf,null);

                sposted = (FloatingActionButton) mview.findViewById(R.id.button3);
                s1 = (Spinner) mview.findViewById(R.id.editText2);
                s2 = (Spinner) mview.findViewById(R.id.watts2);
                //s3  = (Spinner) mview.findViewById(R.id.num2);
                edin  = (EditText) mview.findViewById(R.id.num33);
                naa  = (EditText) mview.findViewById(R.id.naa3);
                choose  = (Button) mview.findViewById(R.id.button2);
                scroll  = (TextView) mview.findViewById(R.id.textView7);
                textView8  = (TextView) mview.findViewById(R.id.textView8);

                //textView8.setText(getIntent().getStringExtra("lname"));

                scroll.setSelected(true);

                String[] items1 = new String[]{"SELECT DEPARTMENT FIRST", "BSC Business Administration", "BSC Computer Engineering", "BSC Information Technology",
                        "BSC Telecom Engineering", "MBA Strategic Management",
                        "BBA Human Resourse Management", "BBA Marketing", "MAB Banking and Finance", "MED Educational Administration and leadership"
                        , "BBA Accounting", "BSC Agribusiness","BSC Mathematics and Statistics","BSC Computer Science",
                        "BED English Language"};
                ArrayAdapter<String> adapters1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items1);
                s1.setAdapter(adapters1);


                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                               long arg3) {

                        // TODO Auto-generated method stub
                        String sp1 = String.valueOf(s1.getSelectedItem());
                        //Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
                        if (sp1.contentEquals("BSC Business Administration")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BSC Computer Engineering")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BSC Information Technology")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("Advanced System Analysis and Design");
                            list.add("Artificial Intelligence");
                            list.add("Data Communication and Networking");
                            list.add("Mobile Application and Development");
                            list.add("Distributed Systems");
                            list.add("Computer and Cyber Forencics");
                            list.add("Computer Security");
                            list.add("Programming with C++");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BSC Telecom Engineering")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("MBA Strategic Management")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BBA Human Resourse Management")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BBA Marketing")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("MAB Banking and Finance")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("MED Educational Administration and leadership")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BBA Accounting")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BSC Agribusiness")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }



                        if (sp1.contentEquals("BSC Mathematics and Statistics")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }



                        if (sp1.contentEquals("BSC Computer Science")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }

                        if (sp1.contentEquals("BED English Language")) {
                            List<String> list = new ArrayList<String>();
                            list.add("SELECT COURSE");
                            list.add("ONE");
                            list.add("TWO");
                            list.add("THREE");
                            list.add("FOUR");
                            list.add("FIVE");
                            list.add("SIX");
                            list.add("SEVEN");
                            list.add("EIGHT");

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter);

                        }


                        if (sp1.contentEquals("SELECT DEPARTMENT")) {
                            List<String> list = new ArrayList<String>();
                            list.add("");

                            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dataAdapter3.notifyDataSetChanged();
                            s2.setAdapter(dataAdapter3);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                sett.setView(mview);
                final AlertDialog dialog = sett.create();

                choose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);

                    }

                });

                sposted.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        //onActivityResult();
                        String str1 = s1.getSelectedItem().toString();
                        //onActivityResult();
                        if (str1.contentEquals("SELECT DEPARTMENT FIRST")) {

                            SweetAlertDialog su = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                            su.setTitleText("Inappropriate Department selection");
                            su.show();
                            return;
                        }

                        String str2 = s2.getSelectedItem().toString();

                        if (str2.contentEquals("SELECT COURSE")) {

                            SweetAlertDialog su = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                            su.setTitleText("Inappropriate Course selection");
                            su.show();
                            return;
                        }

                        if (filePath == null || filePath.equals("")) {

                            SweetAlertDialog su = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                            su.setTitleText("No file chosen");
                            su.show();
                            return;
                        }

                        String name = edin.getText().toString().trim();
                        //String send1 = password.getText().toString();

                        if (TextUtils.isEmpty(name)) {
                            edin.setError("Field cant be Empty");
                            edin.requestFocus();
                            return;
                        }

                        String course = naa.getText().toString().trim();
                        //String send1 = password.getText().toString();

                        if (TextUtils.isEmpty(course)) {
                            naa.setError("Field cant be Empty");
                            naa.requestFocus();
                            return;
                        }


                        path = FilePath.getPath(getActivity(), filePath);

                        if (path == null) {

                            Toast.makeText(getActivity(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
                        }

                        else {
                            //Uploading code
                            try {
                                String uploadId = UUID.randomUUID().toString();

                                //Creating a multi part request
                                new MultipartUploadRequest(getActivity(), uploadId, UPLOAD_URL)
                                        .addFileToUpload(path, "pdf") //Adding file
                                        .addParameter("name", name) //Adding text parameter to the request
                                        .addParameter("department", s1.getSelectedItem().toString()) //Adding text parameter to the request
                                        .addParameter("program", s2.getSelectedItem().toString()) //Adding text parameter to the request
                                        .addParameter("coursecode", naa.getText().toString()) //Adding text parameter to the request
                                        //.addParameter("academicyear", s3.getSelectedItem().toString()) //Adding text parameter to the request
                                        .addParameter("lname", textView8.getText().toString()) //Adding text parameter to the request
                                        .setNotificationConfig(new UploadNotificationConfig())
                                        .setMaxRetries(2)
                                        .startUpload(); //Starting the upload


                                //Toast.makeText(Leccourselist.this, uploadId, Toast.LENGTH_SHORT).show();

                            }

                            catch (Exception exc) {
                                Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }


                    }
                });


//                sett.setView(mview);
//                AlertDialog dialog = sett.create();
                dialog.show();
            }
        });


        return view;
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            if (filePath != null ) {
                scroll.setText(String.valueOf(filePath));
                //Toast.makeText(Leccourselist.this, String.valueOf(path), Toast.LENGTH_LONG).show();

            }

        }


    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
