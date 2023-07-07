package com.example.comupnandroidfinalcastillocamachogiampieros.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.MapsActivity2;
import com.example.comupnandroidfinalcastillocamachogiampieros.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartaAdapter extends RecyclerView.Adapter {

    TextView tvNC, tvAC, tvDC, tvLC, tvLoC;
    ImageView imageView;
    Button btnMapaA;
    private List<Carta> items;
    public CartaAdapter(List<Carta> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_carta_adapter,parent,false);
        NameViewHolder viewHolder = new NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Carta item = items.get(position);

        if(item == null) return;
        View view = holder.itemView;

        tvNC = view.findViewById(R.id.tvNC);
        tvAC = view.findViewById(R.id.tvAC);
        tvDC = view.findViewById(R.id.tvDC);
        tvLC = view.findViewById(R.id.tvLC);
        tvLoC = view.findViewById(R.id.tvLoC);
        imageView = view.findViewById(R.id.imageView);

        tvNC.setText(item.name);
        tvAC.setText(String.valueOf(item.puntosAtaque));
        tvDC.setText(String.valueOf(item.puntosDefensa));
        tvLC.setText(item.latitud);
        tvLoC.setText(item.longitud);

        Picasso.get().load(item.imageURL).into(imageView);

        btnMapaA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity2.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Carta item = items.get(position);
        return item == null ? 0 : 1;
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}