package com.leonardo.tableappreservation.request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.leonardo.tableappreservation.R;
import com.leonardo.tableappreservation.ReservationOnlyConfirm;

public class EditRequest extends AppCompatActivity {

    TextView orderStatusText;
    MaterialSpinner statusSpinner;

    String status;
    Button updateButton;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    CollectionReference requestRef = fStore.collection("orders");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_request);

        orderStatusText = findViewById(R.id.orderStatusTextView);
        updateButton = findViewById(R.id.updateButton);
        statusSpinner = findViewById(R.id.statusSpinner);

        final String orderStatus = getIntent().getStringExtra("orderStatus");
        final String orderID = getIntent().getStringExtra("orderID");


        orderStatusText.setText(orderStatus);

        statusSpinner.setItems("PENDING", "CONFIRM", "COMPLETED");

        statusSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {

                    case 0:
                        status = "PENDING";

                        return;

                    case 1:
                        status = "CONFIRM";

                        return;

                    case 2:
                        status = "COMPLETED";

                        return;

                }

            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == null) {
                    Toast.makeText(EditRequest.this, "Please select the status!", Toast.LENGTH_SHORT).show();

                } else {
                    requestRef.document(orderID).update("orderStatus", status)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(EditRequest.this, "Status updated!", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            });
                }

            }
        });






    }
}
