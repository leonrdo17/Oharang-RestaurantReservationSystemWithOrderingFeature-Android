package com.leonardo.tableappreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

public class ReservationOnlyConfirm extends AppCompatActivity {

    Button confirmButton ;
    TextView fullNameText, emailText, phoneText, tableNumberText, orderTimeText, partySizeText, orderStatusText, menuOrderText, dateText, voucherText;
    String orderStatus, menuOrder;




    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_only_confirm);

        confirmButton = findViewById(R.id.confirmButton);

        fullNameText = findViewById(R.id.nameTextView);
        emailText = findViewById(R.id.emailTextView);
        phoneText = findViewById(R.id.phoneTextView);
        tableNumberText = findViewById(R.id.tableNumberTextView);
        orderTimeText = findViewById(R.id.orderTimeTextView);
        partySizeText = findViewById(R.id.partySizeTextView);
        orderStatusText = findViewById(R.id.orderStatusTextView);
        menuOrderText = findViewById(R.id.menuOrderTextView);
        dateText = findViewById(R.id.dateTextView);
        voucherText = findViewById(R.id.voucherTextView);

        orderStatus = "PENDING";
        menuOrder = "NO ORDERED MENU";


        final String selectedTable = getIntent().getStringExtra("tableNumber");
        final String selectedDate = getIntent().getStringExtra("selectedDate");
        final String selectedTime = getIntent().getStringExtra("selectedTime");
        final String selectedSize = getIntent().getStringExtra("selectedSize");
        final String appliedVoucher = getIntent().getStringExtra("appliedVoucher");
        final String menuOrder = getIntent().getStringExtra("menuOrder");


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        final String[] fullName = new String[1];
        final String[] email = new String[1];
        final String[] phoneNumber = new String[1];

        final String userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fullName[0] = documentSnapshot.getString("fullName");
                email[0] = documentSnapshot.getString("email");
                phoneNumber[0] = documentSnapshot.getString("phone");

                fullNameText.setText(documentSnapshot.getString("fullName"));
                emailText.setText(documentSnapshot.getString("email"));
                phoneText.setText(documentSnapshot.getString("phone"));

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email[0] == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Email not found", Toast.LENGTH_SHORT).show();
                }

                if(fullName[0] == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Full name not found", Toast.LENGTH_SHORT).show();
                }

                if(phoneNumber[0] == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Phone number not found", Toast.LENGTH_SHORT).show();
                }

                if(selectedTime == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Order time not found", Toast.LENGTH_SHORT).show();
                }

                if(selectedDate == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Order date not found", Toast.LENGTH_SHORT).show();
                }

                if(selectedSize == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Order party size not found", Toast.LENGTH_SHORT).show();
                }

                if(selectedTable == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Table not found", Toast.LENGTH_SHORT).show();
                }

                if(appliedVoucher == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Order time not found", Toast.LENGTH_SHORT).show();
                }

                if(orderStatus == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Status not found", Toast.LENGTH_SHORT).show();
                }

                if(menuOrder == null){
                    Toast.makeText(ReservationOnlyConfirm.this, "Create order failed! Menu order not found", Toast.LENGTH_SHORT).show();
                }


                String orderID = UUID.randomUUID().toString();

                DocumentReference documentReference1 = fStore.collection("orders").document(orderID);
                Map<String, Object> order = new HashMap<>();
                order.put("fullName",fullName[0]);
                order.put("email",email[0]);
                order.put("phone",phoneNumber[0]);
                order.put("tableNumber",selectedTable);
                order.put("orderTime",selectedTime);
                order.put("orderDate",selectedDate);
                order.put("partySize",selectedSize);
                order.put("voucher",appliedVoucher);
                order.put("menuOrder",menuOrder);
                order.put("orderStatus",orderStatus);
                order.put("orderID", orderID);

                DocumentReference documentReference2 = fStore.collection("tables").document();
                Map<String, Object> tableCheck = new HashMap<>();
                tableCheck.put("tableNumber",selectedTable);
                tableCheck.put("orderTime",selectedTime);
                tableCheck.put("orderDate",selectedDate);

                if (appliedVoucher.equalsIgnoreCase("RM50 OFF VOUCHER")) {
                    FirebaseFirestore.getInstance().collection("vouchers")
                            .whereEqualTo("email", email[0])
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    WriteBatch batch = FirebaseFirestore.getInstance().batch();

                                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot snapshot: snapshotList) {
                                        batch.delete(snapshot.getReference());
                                    }

                                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ReservationOnlyConfirm.this, "Your voucher has been used!", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ReservationOnlyConfirm.this, "There is an error at voucher!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                }

                documentReference2.set(tableCheck);

                documentReference1.set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ReservationOnlyConfirm.this, "Order has been created!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReservationOnlyConfirm.this, "Order failed!", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        fullNameText.setText(fullName[0]);
        emailText.setText(email[0]);
        phoneText.setText(phoneNumber[0]);
        tableNumberText.setText(selectedTable);
        orderTimeText.setText(selectedTime);
        partySizeText.setText(selectedSize);
        orderStatusText.setText(orderStatus);
        menuOrderText.setText(menuOrder);
        dateText.setText(selectedDate);
        voucherText .setText(appliedVoucher);

    }
}
