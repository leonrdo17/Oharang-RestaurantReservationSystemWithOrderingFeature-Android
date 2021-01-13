package com.leonardo.tableappreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.leonardo.tableappreservation.manageTable.OwnerManageTable;
import com.leonardo.tableappreservation.manageVoucher.OwnerManageVoucher;
import com.leonardo.tableappreservation.menu.RestaurantOrderMenu;
import com.leonardo.tableappreservation.request.OwnerReservationRequest;

public class OwnerOptions extends AppCompatActivity {

    Button menuOrder, orderRequest, manageTable, manageVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_options);

        menuOrder = findViewById(R.id.manageMenuButton);
        orderRequest = findViewById(R.id.checkReqButton);
        manageTable = findViewById(R.id.manageTableButton);
        manageVoucher = findViewById(R.id.manageVoucherButton);

        menuOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RestaurantOrderMenu.class);
                startActivity(intent);
            }
        });

        orderRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnerReservationRequest.class);
                startActivity(intent);
            }
        });

        manageTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnerManageTable.class);
                startActivity(intent);
            }
        });

        manageVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OwnerManageVoucher.class);
                startActivity(intent);
            }
        });



    }
}
