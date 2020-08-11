package com.example.internshipproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class birthdayaddingitems extends AppCompatActivity {
    private String mParam1;
    private String mParam2;
    Button save;
    int myear,mmonth,mday;
    EditText phone;
    String selectedda,selectmon;
    FirebaseDatabase database;
    FirebaseStorage storage;
    DatabaseReference reference;
    StorageReference storagereference;
    ImageView img;
    ArrayList<Pojo> list;
    ViewGroup layout;
    ImageView im;

    TextView selectedday,selectedmonth;
    Spinner day,month;
    ArrayList<String> li=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdayaddingite);
         list=new ArrayList<>();
        storage=FirebaseStorage.getInstance();
        storagereference= storage.getReference();

        // Inflate the layout for this fragment

        final EditText name=findViewById(R.id.name);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("userdetails");
        final String nam=name.getText().toString();
        String days[]={"Please selectct day","10","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
        String months[]={"Please selectct month","1","2","3","4","5","6","7","8","9","10","11","12"};
        final TextView selectedday=findViewById(R.id.selectedday);
        save=findViewById(R.id.savebutton);
        phone=findViewById(R.id.phnum);
        img=findViewById(R.id.image);


        final TextView selectedmonth=findViewById(R.id.selectedmonth);
        final Spinner day=findViewById(R.id.day);
        final Spinner month=(Spinner)findViewById(R.id.month);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item ,days);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        day.setAdapter(adapter);
        ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item ,months);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        month.setAdapter(monthadapter);


        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedda= (String)day.getSelectedItem();
                selectedday.setText("SELECTED DAY : "+selectedda);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectmon=(String)month.getSelectedItem();
                selectedmonth.setText("SELECTED MONTH :"+selectmon);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,1);
            }

        });



        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String mname=name.getText().toString();
                final String mday=day.getSelectedItem().toString();
                final String mmonth=month.getSelectedItem().toString();
                final String ph=phone.getText().toString();

                storagereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String imageuri=uri.toString();
                        Pojo pojo=new Pojo(mname,mday,mmonth,ph,imageuri);
                        reference.push().setValue(pojo);
                    }
                });



            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Pojo pojo = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    pojo = dataSnapshot.getValue(Pojo.class);
                    list.add(pojo);

                }



                date();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
               Uri u=data.getData();
               img.setImageURI(u);
               saveimg(u);
            }
        }
    }

    private void saveimg(Uri u) {


        storagereference=storagereference.child("Images/"+ UUID.randomUUID().toString());
        storagereference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(birthdayaddingitems.this, "succesful img", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(birthdayaddingitems.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void date() {

        int i;

        Calendar c=Calendar.getInstance();
        myear=c.get(Calendar.YEAR);
        mmonth=c.get(Calendar.MONTH);
        mday=c.get(Calendar.DATE);


        DatePickerDialog da=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            }
        },myear,mmonth,mday);
        li.add("mnnd123");

        for(i=0;i<list.size();i++){
            if(mday==Integer.parseInt(list.get(i).getMyday()) && mmonth==Integer.parseInt(list.get(i).getMymonth()) ){

                if(li.contains(list.get(i).getMyname())){
                    Toast.makeText(this, "repeated--"+list.get(i).getMyname(), Toast.LENGTH_SHORT).show();

                }
                else{
                    NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        NotificationChannel notificationChannel=new NotificationChannel("manu","manohar",NotificationManager.IMPORTANCE_HIGH);
                        notificationChannel.setLockscreenVisibility(1);
                        notificationChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"manu");
                    builder.setSmallIcon(R.drawable.s);
                    builder.setContentTitle("Birthday reminder");
                    builder.setContentText(list.get(i).getMyname()+"is celebrating his birthday today");
                    notificationManager.notify(i,builder.build());
                    li.add(list.get(i).getMyname());




                }



            }



        }










    }


    // TODO: Rename method, update argument and hook method into UI event



}

