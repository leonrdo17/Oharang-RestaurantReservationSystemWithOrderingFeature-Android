package com.leonardo.tableappreservation.restaurantMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.leonardo.tableappreservation.R;
import com.leonardo.tableappreservation.request.EditRequest;
import com.leonardo.tableappreservation.request.RequestAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListMenuAdapter extends FirestoreRecyclerAdapter<ListMenu, ListMenuAdapter.ListMenuHolder> {

    private Context context;
    ArrayList<String> chosenOrderList;
//    Declare List / Array Global

    public ListMenuAdapter(@NonNull FirestoreRecyclerOptions<ListMenu> options, Context context) {
        super(options);
        this.context = context;
        chosenOrderList = new ArrayList<>();
    }

    public ArrayList<String> getAllMenu() {
        return chosenOrderList;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListMenuHolder holder, int position, @NonNull final ListMenu model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        Picasso.get().load(model.getImage()).into(holder.menuImage);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Add the selected menu?")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                chosenOrderList.add(model.getTitle());
                                Toast.makeText(context, "Menu has been added!", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    @NonNull
    @Override
    public ListMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item, parent, false);
        return new ListMenuHolder(v);
    }

    class ListMenuHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView menuImage;
        CardView parentLayout;

        public ListMenuHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            menuImage = itemView.findViewById(R.id.menuImage);

            parentLayout = itemView.findViewById(R.id.listMenuCardView);
        }
    }
}
