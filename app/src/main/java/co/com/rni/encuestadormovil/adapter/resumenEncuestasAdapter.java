package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.*;

/**
 * Created by ASUS on 8/05/2017.
 */

public class resumenEncuestasAdapter extends RecyclerView.Adapter<resumenEncuestasAdapter.ViewHolder> {

    private ArrayList<item_resumen_encuestas> dataset;
    private Context context;

    public resumenEncuestasAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resumen_encuestas_por_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item_resumen_encuestas ire = dataset.get(position);
        holder.usuario.setText(ire.getUsuario());
        holder.estado.setText(ire.getEstado());
        holder.total.setText(ire.getTotal());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaREncuestas(ArrayList<item_resumen_encuestas> listaRE){
        this.dataset.clear();
        dataset.addAll(listaRE);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView usuario, estado, total;

        public ViewHolder(View itemView){
            super(itemView);
            usuario = (TextView) itemView.findViewById(R.id.idUsuario);
            estado = (TextView)  itemView.findViewById(R.id.idEstado);
            total = (TextView) itemView.findViewById(R.id.idTotal);
        }

    }
}
