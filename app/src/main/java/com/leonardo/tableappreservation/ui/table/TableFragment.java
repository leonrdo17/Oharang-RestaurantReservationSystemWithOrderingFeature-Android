package com.leonardo.tableappreservation.ui.table;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.leonardo.tableappreservation.R;
import com.leonardo.tableappreservation.TableOptions;

public class TableFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView tableNumberOne;
    private ImageView tableNumberTwo;
    private ImageView tableNumberThree;
    private ImageView tableNumberFour;
    private ImageView tableNumberFive;
    private ImageView tableNumberSix;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_table, container, false);

        tableNumberOne = root.findViewById(R.id.tableNumberOne);
        tableNumberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableOptions.class);
                intent.putExtra("tableNumber", "1");
                startActivity(intent);
            }
        });

        tableNumberTwo = root.findViewById(R.id.tableNumberTwo);
        tableNumberTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableOptions.class);
                intent.putExtra("tableNumber", "2");
                startActivity(intent);
            }
        });

        tableNumberThree = root.findViewById(R.id.tableNumberThree);
        tableNumberThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableOptions.class);
                intent.putExtra("tableNumber", "3");
                startActivity(intent);
            }
        });

        tableNumberFour = root.findViewById(R.id.tableNumberFour);
        tableNumberFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableOptions.class);
                intent.putExtra("tableNumber", "4");
                startActivity(intent);
            }
        });

        tableNumberFive = root.findViewById(R.id.tableNumberFive);
        tableNumberFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableOptions.class);
                intent.putExtra("tableNumber", "5");
                startActivity(intent);
            }
        });

        return root;
    }
}
