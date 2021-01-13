package com.leonardo.tableappreservation.request;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.leonardo.tableappreservation.R;

public class RequestAdapter extends FirestoreRecyclerAdapter<Request, RequestAdapter.RequestHolder> {
    private OnItemClickListener listener;
    private Context context;

    public RequestAdapter(@NonNull FirestoreRecyclerOptions<Request> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final RequestHolder holder, int position, @NonNull final Request model) {
        holder.textViewFullName.setText(model.getFullName());
        holder.textViewEmail.setText(model.getEmail());
        holder.textViewPhone.setText(model.getPhone());
        holder.textViewTableNumber.setText(model.getTableNumber());
        holder.textViewOrderTime.setText(model.getOrderTime());
        holder.textViewOrderDate.setText(model.getOrderDate());
        holder.textViewPartySize.setText(model.getPartySize());
        holder.textViewVoucher.setText(model.getVoucher());
        holder.textViewMenuOrder.setText(model.getMenuOrder());
        holder.textViewOrderStatus.setText(model.getOrderStatus());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditRequest.class);
                intent.putExtra("orderID", model.getOrderID());
                intent.putExtra("orderStatus", model.getOrderStatus());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        return new RequestHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    class RequestHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName;
        TextView textViewEmail;
        TextView textViewPhone;
        TextView textViewTableNumber;
        TextView textViewOrderTime;
        TextView textViewOrderDate;
        TextView textViewPartySize;
        TextView textViewVoucher;
        TextView textViewMenuOrder;
        TextView textViewOrderStatus;
        CardView parentLayout;



        public RequestHolder(View itemView) {
            super(itemView);
            textViewFullName = itemView.findViewById(R.id.nameTextView);
            textViewEmail = itemView.findViewById(R.id.emailTextView);
            textViewPhone = itemView.findViewById(R.id.phoneTextView);
            textViewTableNumber = itemView.findViewById(R.id.tableNumberTextView);
            textViewOrderTime = itemView.findViewById(R.id.orderTimeTextView);
            textViewOrderDate = itemView.findViewById(R.id.dateTextView);
            textViewPartySize = itemView.findViewById(R.id.partySizeTextView);
            textViewVoucher = itemView.findViewById(R.id.voucherTextView);
            textViewMenuOrder = itemView.findViewById(R.id.menuOrderTextView);
            textViewOrderStatus = itemView.findViewById(R.id.orderStatusTextView);
            parentLayout = itemView.findViewById(R.id.requestItemCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
