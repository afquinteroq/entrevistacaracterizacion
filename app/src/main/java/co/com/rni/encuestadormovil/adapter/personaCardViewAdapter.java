package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.*;

/**
 * Created by ASUS on 8/05/2017.
 */

public class personaCardViewAdapter extends RecyclerView.Adapter<personaCardViewAdapter.ViewHolder> {

    private ArrayList<itempersonacard> dataset;
    private Context context;

    public personaCardViewAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_persona, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        itempersonacard ire = dataset.get(position);

        Glide.with(context).load(ire.getImageViewImagen()).into(holder.imageViewImagen);

        holder.tvPersona.setText(ire.getTvPersona());
        holder.tvNacionalidad.setText(ire.getTvNacionalidad());
        holder.textViewDatos.setText(ire.getTextViewDatos());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaREncuestas(ArrayList<itempersonacard> listaRE){
        this.dataset.clear();
        dataset.addAll(listaRE);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPersona, tvNacionalidad, textViewDatos;
        private ImageView imageViewImagen;

        public ViewHolder(View itemView){
            super(itemView);
            tvPersona = (TextView) itemView.findViewById(R.id.tvPersona);
            tvNacionalidad = (TextView)  itemView.findViewById(R.id.tvNacionalidad);
            textViewDatos = (TextView) itemView.findViewById(R.id.textViewDatos);
            imageViewImagen = (ImageView) itemView.findViewById(R.id.imageViewImagen);
        }

    }
}
