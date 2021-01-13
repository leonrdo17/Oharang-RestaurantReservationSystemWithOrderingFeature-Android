package com.leonardo.tableappreservation.ui.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.leonardo.tableappreservation.R;

public class OrderAdapter extends FirestoreRecyclerAdapter<Order, OrderAdapter.OrderHolder> {


    public OrderAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull Order model) {
        holder.fullNameText.setText(model.getFullName());
        holder.emailText.setText(model.getEmail());
        holder.phoneText.setText(model.getPhone());
        holder.tableNumberText.setText(model.getTableNumber());
        holder.orderTimeText.setText(model.getOrderTime());
        holder.orderDateText.setText(model.getOrderDate());
        holder.partySizeText.setText(model.getPartySize());
        holder.voucherText.setText(model.getVoucher());
        holder.orderStatusText.setText(model.getOrderStatus());
        holder.menuOrderText.setText(model.getMenuOrder());
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class OrderHolder extends RecyclerView.ViewHolder {

        TextView fullNameText;
        TextView emailText;
        TextView phoneText;
        TextView tableNumberText;
        TextView orderTimeText;
        TextView orderDateText;
        TextView partySizeText;
        TextView voucherText;
        TextView orderStatusText;
        TextView menuOrderText;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            fullNameText = itemView.findViewById(R.id.nameTextView);
            emailText = itemView.findViewById(R.id.emailTextView);
            phoneText = itemView.findViewById(R.id.phoneTextView);
            tableNumberText = itemView.findViewById(R.id.tableNumberTextView);
            orderTimeText = itemView.findViewById(R.id.orderTimeTextView);
            orderDateText = itemView.findViewById(R.id.dateTextView);
            partySizeText = itemView.findViewById(R.id.partySizeTextView);
            voucherText = itemView.findViewById(R.id.voucherTextView);
            orderStatusText = itemView.findViewById(R.id.orderStatusTextView);
            menuOrderText = itemView.findViewById(R.id.menuOrderTextView);
        }
    }
}
