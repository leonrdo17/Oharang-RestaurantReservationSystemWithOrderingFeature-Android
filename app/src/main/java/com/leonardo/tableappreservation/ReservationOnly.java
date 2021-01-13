package com.leonardo.tableappreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class ReservationOnly extends AppCompatActivity {

    MaterialSpinner timeSpinner, sizeSpinner;

    Button createOrderButton;

    String time;
    String size;

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    // Query things
    Query query;
    CollectionReference tableReference = fStore.collection("tables");

    String checkTableNum, checkDate, checkTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_only);

        final String selectedTable = getIntent().getStringExtra("tableNumber");
        final String selectedDate = getIntent().getStringExtra("selectedDate");
        final String menuOrder = getIntent().getStringExtra("menuOrder");

        timeSpinner = findViewById(R.id.statusSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);

        createOrderButton = findViewById(R.id.createOrderButton);


        timeSpinner.setItems("10.00 AM","11.00 AM","12.00 PM",
                "01.00 PM","02.00 PM","03.00 PM","04.00 PM","05.00 PM",
                "06.00 PM","07.00 PM","08.00 PM","09.00 PM");

        sizeSpinner.setItems("One Person", "Two Person", "Three Person",
                "Four Person","Five Person","Six Person");

        timeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        time = "10.00 AM";

                        return;
                    case 1:
                        time = "11.00 AM";

                        return;
                    case 2:
                        time = "12.00 PM";

                        return;
                    case 3:
                        time = "01.00 PM";

                        return;
                    case 4:
                        time = "02.00 PM";

                        return;
                    case 5:
                        time = "03.00 PM";

                        return;
                    case 6:
                        time = "04.00 PM";

                        return;
                    case 7:
                        time = "05.00 PM";

                        return;
                    case 8:
                        time = "06.00 PM";

                        return;
                    case 9:
                        time = "07.00 PM";

                        return;
                    case 10:
                        time = "08.00 PM";

                        return;
                    case 11:
                        time = "09.00 PM";

                        return;
                }

            }
        });

        sizeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        size = "ONE PERSON";

                        return;
                    case 1:
                        size = "TWO PERSONS";
                        return;
                    case 2:
                        size = "THREE PERSONS";

                        return;
                    case 3:
                        size = "FOUR PERSONS";

                        return;
                    case 4:
                        size = "FIVE PERSONS";

                        return;
                    case 5:
                        size = "SIX PERSONS";

                        return;

                }
            }
        });



        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time == null){
                    Toast.makeText(ReservationOnly.this, "Please select the order time!", Toast.LENGTH_SHORT).show();

                } else if(size == null){
                    Toast.makeText(ReservationOnly.this, "Please select the party size!", Toast.LENGTH_SHORT).show();
                }

                else{
                    query = tableReference.whereEqualTo("orderDate", selectedDate)
                            .whereEqualTo("orderTime", time)
                            .whereEqualTo("tableNumber", selectedTable);

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           if(menuOrder.equalsIgnoreCase("NO ORDERED MENU")){
                               if (task.isSuccessful()) {
                                   if (task.getResult().isEmpty()) {
                                       Toast.makeText(ReservationOnly.this, "Time available!", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(getApplicationContext(), ReservationOnlyVoucher.class);
                                       intent.putExtra("tableNumber", selectedTable);
                                       intent.putExtra("selectedDate", selectedDate);
                                       intent.putExtra("selectedTime", time);
                                       intent.putExtra("selectedSize", size);
                                       intent.putExtra("menuOrder", menuOrder);
                                       startActivity(intent);
                                   } else {
                                       Toast.makeText(ReservationOnly.this, "The selected time is occupied!", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           } else if(menuOrder.equalsIgnoreCase("HAVE NOT SELECTED")){
                               if (task.isSuccessful()) {
                                   if (task.getResult().isEmpty()) {
                                       Toast.makeText(ReservationOnly.this, "Time available!", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(getApplicationContext(), ReservationOnlyVoucher.class);
                                       intent.putExtra("tableNumber", selectedTable);
                                       intent.putExtra("selectedDate", selectedDate);
                                       intent.putExtra("selectedTime", time);
                                       intent.putExtra("selectedSize", size);
                                       intent.putExtra("menuOrder", menuOrder);
                                       startActivity(intent);
                                   } else {
                                       Toast.makeText(ReservationOnly.this, "The selected time is occupied!", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           } else{
                               Toast.makeText(ReservationOnly.this, "Problem at date selection!!", Toast.LENGTH_SHORT).show();
                           }

                        }
                    });
                }



            }
        });

    }

}
