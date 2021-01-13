package com.leonardo.tableappreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TableOptions extends AppCompatActivity {

    Button reservationOnlyButton, reservationWithOrderButton;
    TextView tableNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_options);
        final String selectedTable = getIntent().getStringExtra("tableNumber");


        reservationOnlyButton = findViewById(R.id.reservationOnlyButton);
        reservationWithOrderButton = findViewById(R.id.reservationWithOrder);

        tableNumberView = findViewById(R.id.tableNumberText);

        reservationOnlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuOrder = "NO ORDERED MENU";

                Intent intent = new Intent(getApplicationContext(), DateReservationOnly.class);
                intent.putExtra("tableNumber", selectedTable);
                intent.putExtra("menuOrder", menuOrder);

                startActivity(intent);

            }
        });

        reservationWithOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuOrder = "HAVE NOT SELECTED";

                Intent intent = new Intent(getApplicationContext(), DateReservationOnly.class);
                intent.putExtra("tableNumber", selectedTable);
                intent.putExtra("menuOrder", menuOrder);

                startActivity(intent);
            }
        });

        tableNumberView.setText("You are on table number: "+ selectedTable);
    }
}
