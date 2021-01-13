package com.leonardo.tableappreservation.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.leonardo.tableappreservation.R;
import com.leonardo.tableappreservation.TableOptions;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderBookRef = db.collection("orders");

    private OrderAdapter adapter;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Button cancelReservation;
    String emailData;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);

        FirebaseUser email = FirebaseAuth.getInstance().getCurrentUser();
        emailData = email.getEmail();

        setUpRecyclerView();


        cancelReservation = root.findViewById(R.id.cancelButton);
        cancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("orders").whereEqualTo("email",emailData).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<String> list = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        list.add(document.getId());
                                    }
                                    DeleteData((ArrayList) list);
                                }
                            }
                        });

            }
        });




        return root;
    }

    private void DeleteData(ArrayList list) {
        WriteBatch batch = db.batch();
        for(int i = 0; i < list.size(); i++) {
            DocumentReference ref = db.collection("orders").document((String) list.get(i));
            batch.delete(ref);
        }

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "All reservations cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView(){

        Query query = orderBookRef.whereEqualTo("email", emailData);

        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.
                Builder<Order>().setQuery(query, Order.class).build();

        adapter = new OrderAdapter(options);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopListening();
    }



}
