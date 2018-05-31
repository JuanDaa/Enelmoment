package com.ingoskr.eminen.enelmoment.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.modelo.Rutas;

import java.util.ArrayList;

/**
 * Created by emine on 25/08/2016.
 */
public class RutaAdapter  extends RecyclerView.Adapter<RutaAdapter.Myvistaholder>{

    private Context contexto;
    private ArrayList<Rutas> ruta;

    public RutaAdapter(Context contexto, ArrayList<Rutas> ruta) {
        this.contexto = contexto;
        this.ruta = ruta;
    }

    @Override
    public Myvistaholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myvistaholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista,null));
    }

    @Override
    public void onBindViewHolder(Myvistaholder holder,final int position) {

        holder.idbus.setText(ruta.get(position).getId());
        holder.color.setText(ruta.get(position).getColor());




    }

    @Override
    public int getItemCount() {
        return ruta.size();
    }

    public class Myvistaholder extends RecyclerView.ViewHolder {

        TextView idbus,color;

        public Myvistaholder(View itemView) {
            super(itemView);

            idbus=(TextView) itemView.findViewById(R.id.idbus);
            color=(TextView) itemView.findViewById(R.id.color);



        }
    }
}
