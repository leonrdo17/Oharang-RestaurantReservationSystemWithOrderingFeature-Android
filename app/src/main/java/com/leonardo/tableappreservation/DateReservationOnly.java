package com.leonardo.tableappreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.util.Calendar;

import javax.annotation.Nullable;

public class DateReservationOnly extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView dateText, tableNumberText, selectedTable;
    Button selectDate, nextButton;
    private String date;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String checkTableNum, checkDate, checkTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_reservation_only);

        final String selectedTable = getIntent().getStringExtra("tableNumber");
        final String menuOrder = getIntent().getStringExtra("menuOrder");


        dateText = findViewById(R.id.dateText);
        tableNumberText = findViewById(R.id.tableNumberText);
        selectDate = findViewById(R.id.openCalendarButton);
        nextButton = findViewById(R.id.nextButton);

        tableNumberText.setText("You are on table number: " + selectedTable);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("tables").document();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                checkTableNum = documentSnapshot.getString("tableNumber");
                checkDate = documentSnapshot.getString("orderDate");
                checkTime = documentSnapshot.getString("orderTime");

            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date == null){
                    Toast.makeText(DateReservationOnly.this, "Please select the date!", Toast.LENGTH_SHORT).show();

                } else {
                    if(menuOrder.equalsIgnoreCase("NO ORDERED MENU")){
                        Intent intent = new Intent(getApplicationContext(), ReservationOnly.class);
                        intent.putExtra("tableNumber", selectedTable);
                        intent.putExtra("selectedDate", date);
                        intent.putExtra("menuOrder", menuOrder);
                        startActivity(intent);

                    } else if(menuOrder.equalsIgnoreCase("HAVE NOT SELECTED")){
                        Intent intent = new Intent(getApplicationContext(), ReservationOnly.class);
                        intent.putExtra("tableNumber", selectedTable);
                        intent.putExtra("selectedDate", date);
                        intent.putExtra("menuOrder", menuOrder);
                        startActivity(intent);
                    } else{
                        Toast.makeText(DateReservationOnly.this, "Problem at table options! +" + menuOrder, Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) ,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7 * 2));
        Calendar.getInstance().add(Calendar.YEAR, 0);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        date = dayOfMonth + "/" + (month+1) + "/" + year;

        dateText.setText(date);
    }
}
