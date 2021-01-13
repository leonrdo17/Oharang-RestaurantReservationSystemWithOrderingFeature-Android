package com.leonardo.tableappreservation.menu;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.leonardo.tableappreservation.R;
import com.squareup.picasso.Picasso;

public class MenuAdapter extends FirestoreRecyclerAdapter<Menu, MenuAdapter.MenuHolder> {


    public MenuAdapter(@NonNull FirestoreRecyclerOptions<Menu> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MenuHolder holder, int position, @NonNull Menu model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        Picasso.get().load(model.getImage()).into(holder.menuImage);

    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuHolder(v);
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView menuImage;


        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            menuImage = itemView.findViewById(R.id.menuImage);
        }
    }
}
