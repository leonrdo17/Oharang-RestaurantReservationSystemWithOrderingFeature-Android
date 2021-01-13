package com.leonardo.tableappreservation.restaurantMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.leonardo.tableappreservation.R;
import com.leonardo.tableappreservation.ReservationOnly;
import com.leonardo.tableappreservation.ReservationOnlyConfirm;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenu extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference listMenuRef = db.collection("menuOrders");

    private ListMenuAdapter adapter;

    ArrayList<String> orderList;

    Button confirmOrderButton;

    String menuOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        orderList = new ArrayList<>();

        final String selectedTable = getIntent().getStringExtra("tableNumber");
        final String selectedDate = getIntent().getStringExtra("selectedDate");
        final String selectedTime = getIntent().getStringExtra("selectedTime");
        final String selectedSize = getIntent().getStringExtra("selectedSize");
        final String appliedVoucher = getIntent().getStringExtra("appliedVoucher");


        confirmOrderButton = findViewById(R.id.confirmOrderButton);

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderList = adapter.getAllMenu();
                menuOrder = orderList.toString();




                if (menuOrder == "[]") {
                    Toast.makeText(RestaurantMenu.this, "Please select the menu!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(RestaurantMenu.this, "Your menu has been confirm!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ReservationOnlyConfirm.class);
                    intent.putExtra("tableNumber", selectedTable);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedTime", selectedTime);
                    intent.putExtra("selectedSize", selectedSize);
                    intent.putExtra("appliedVoucher", appliedVoucher);
                    intent.putExtra("menuOrder", menuOrder);
                    startActivity(intent);
                    finish();

                }

            }
        });


        setUpRecyclerView();
    }

    public void setOrder(String chosenOrderList) {
        orderList.add(chosenOrderList);
    }

    private void setUpRecyclerView() {
        Query query = listMenuRef.orderBy("title");

        FirestoreRecyclerOptions<ListMenu> options = new FirestoreRecyclerOptions.Builder<ListMenu>()
                .setQuery(query, ListMenu.class)
                .build();

        adapter = new ListMenuAdapter(options, RestaurantMenu.this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
