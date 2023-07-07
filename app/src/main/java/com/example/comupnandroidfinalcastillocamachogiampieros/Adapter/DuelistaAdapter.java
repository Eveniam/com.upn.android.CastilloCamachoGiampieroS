package com.example.comupnandroidfinalcastillocamachogiampieros.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comupnandroidfinalcastillocamachogiampieros.DetalleDuelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Carta;
import com.example.comupnandroidfinalcastillocamachogiampieros.Entities.Duelista;
import com.example.comupnandroidfinalcastillocamachogiampieros.R;

import java.util.List;

public class DuelistaAdapter extends RecyclerView.Adapter {

    TextView tvID, tvName, tvDuelistaSincro;
    private List<Duelista> items;
    public DuelistaAdapter(List<Duelista> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_duelista_adapter,parent,false);
        NameViewHolder viewHolder = new NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Duelista item = items.get(position);
        View view = holder.itemView;

        tvID = view.findViewById(R.id.tvID);
        tvName = view.findViewById(R.id.tvName);
        tvDuelistaSincro = view.findViewById(R.id.tvDuelistaSincro);

        tvID.setText(String.valueOf(item.id));
        tvName.setText(item.name);
        tvDuelistaSincro.setText(String.valueOf(item.sincD));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetalleDuelista.class);
                intent.putExtra("id",item.id);
                v.getContext().startActivity(intent);
                Log.d("APP_MAIN: IDCuenta", String.valueOf(item.id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}