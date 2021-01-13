package com.leonardo.tableappreservation.manageTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.leonardo.tableappreservation.R;

public class TableAdapter extends FirestoreRecyclerAdapter<Table, TableAdapter.TableHolder> {

    public TableAdapter(@NonNull FirestoreRecyclerOptions<Table> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TableHolder holder, int position, @NonNull Table model) {
        holder.textViewTableNumber.setText(model.getTableNumber());
        holder.textViewOrderTime.setText(model.getOrderTime());
        holder.textViewOrderDate.setText(model.getOrderDate());

    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);

        return new TableHolder(v);
    }

    public void deleteTable(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }



    class TableHolder extends RecyclerView.ViewHolder {
        TextView textViewTableNumber;
        TextView textViewOrderTime;
        TextView textViewOrderDate;

        public TableHolder(@NonNull View itemView) {
            super(itemView);
            textViewTableNumber = itemView.findViewById(R.id.tableNumberText);
            textViewOrderTime = itemView.findViewById(R.id.orderTimeText);
            textViewOrderDate = itemView.findViewById(R.id.orderDateText);
        }
    }
}
