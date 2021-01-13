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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leonardo.tableappreservation.restaurantMenu.RestaurantMenu;

import javax.annotation.Nullable;

public class ReservationOnlyVoucher extends AppCompatActivity {

    Button confirmButton;
    MaterialSpinner voucherSpinner;
    String appliedVoucher;
    String voucher;

    Query query;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference voucherReference = fStore.collection("vouchers");

    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_only_voucher);

        final String selectedTable = getIntent().getStringExtra("tableNumber");
        final String selectedDate = getIntent().getStringExtra("selectedDate");
        final String selectedTime = getIntent().getStringExtra("selectedTime");
        final String selectedSize = getIntent().getStringExtra("selectedSize");
        final String menuOrder = getIntent().getStringExtra("menuOrder");

        final String[] email = new String[1];

        fAuth = FirebaseAuth.getInstance();

        final String userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                email[0] = documentSnapshot.getString("email");
                showVoucher(documentSnapshot.getString("email"));

            }
        });

        voucher = "RM50 OFF VOUCHER";

        confirmButton = findViewById(R.id.confirmButton);
        voucherSpinner = findViewById(R.id.statusSpinner);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuOrder.equalsIgnoreCase("NO ORDERED MENU")){
                    if(appliedVoucher == null){
                        Toast.makeText(ReservationOnlyVoucher.this, "Please select the voucher!", Toast.LENGTH_SHORT).show();

                    }else{
                        Intent intent = new Intent(getApplicationContext(), ReservationOnlyConfirm.class);
                        intent.putExtra("tableNumber", selectedTable);
                        intent.putExtra("selectedDate", selectedDate);
                        intent.putExtra("selectedTime", selectedTime);
                        intent.putExtra("selectedSize", selectedSize);
                        intent.putExtra("appliedVoucher", appliedVoucher);
                        intent.putExtra("menuOrder", menuOrder);
                        startActivity(intent);
                    }
                } else if(menuOrder.equalsIgnoreCase("HAVE NOT SELECTED")){
                    if(appliedVoucher == null){
                        Toast.makeText(ReservationOnlyVoucher.this, "Please select the voucher!", Toast.LENGTH_SHORT).show();

                    }else{
                        Intent intent = new Intent(getApplicationContext(), RestaurantMenu.class);
                        intent.putExtra("tableNumber", selectedTable);
                        intent.putExtra("selectedDate", selectedDate);
                        intent.putExtra("selectedTime", selectedTime);
                        intent.putExtra("selectedSize", selectedSize);
                        intent.putExtra("appliedVoucher", appliedVoucher);
//                        intent.putExtra("menuOrder", menuOrder);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(ReservationOnlyVoucher.this, "Problem at date and size selection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showVoucher(String email) {
        query = voucherReference.whereEqualTo("email", email)
                .whereEqualTo("voucher", voucher);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        voucherSpinner.setItems("NO VOUCHER", "RM50 OFF VOUCHER");
                        voucherSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                                switch (position) {
                                    case 0:
                                        appliedVoucher = "NO VOUCHER";

                                        return;

                                    case 1:
                                        appliedVoucher = "RM50 OFF VOUCHER";

                                        return;

                                }
                            }
                        });

                    } else {
                        voucherSpinner.setItems("NO VOUCHER");
                        voucherSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                                switch (position) {
                                    case 0:
                                        appliedVoucher = "NO VOUCHER";

                                        return;

                                }

                            }
                        });

                    }
                }
            }
        });
    }
}
