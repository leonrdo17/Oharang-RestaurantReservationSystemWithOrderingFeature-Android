package com.leonardo.tableappreservation.manageVoucher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.leonardo.tableappreservation.R;

public class VoucherAdapter extends FirestoreRecyclerAdapter<Voucher, VoucherAdapter.VoucherHolder> {

    public VoucherAdapter(@NonNull FirestoreRecyclerOptions<Voucher> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VoucherHolder holder, int position, @NonNull Voucher model) {
        holder.textViewEmail.setText(model.getEmail());
        holder.textViewVoucher.setText(model.getVoucher());

    }

    @NonNull
    @Override
    public VoucherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new VoucherHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    class VoucherHolder extends RecyclerView.ViewHolder {
        TextView textViewEmail;
        TextView textViewVoucher;

        public VoucherHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.emailText);
            textViewVoucher = itemView.findViewById(R.id.voucherText);
        }
    }
}
