package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;

import java.util.List;
import java.util.Objects;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.*;
/**
 * Created by javierperez on 15/11/15.
 */

public class buscarVictimasAdapter extends ArrayAdapter{

    List<emc_victimas_nosugar> items;
    private conformarHogarResponse vicResponse;


    public buscarVictimasAdapter(Context context, int tvID, List<emc_victimas_nosugar> objects, conformarHogarResponse vicResponse) {
        super(context, tvID, objects);
        items = objects;
        this.vicResponse = vicResponse;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_victima, null);
        }

        emc_victimas_nosugar temVictima = items.get(position);
        TextView tvIdVictima = (TextView) convertView.findViewById(R.id.tvIdVictima);
        tvIdVictima.setText(String.valueOf(temVictima.getConsPersona()));
        TextView tvTipoDocVictima = (TextView) convertView.findViewById(R.id.tvTipoDocVictima);
        tvTipoDocVictima.setText(temVictima.getTipoDoc());
        TextView tvDocumentoVictima = (TextView) convertView.findViewById(R.id.tvDocumentoVictima);
        tvDocumentoVictima.setText(temVictima.getDocumento());
        TextView tvNombreVictima = (TextView) convertView.findViewById(R.id.tvNombreVictima);
        tvNombreVictima.setText(temVictima.getNombre1() + " " + temVictima.getNombre2() + " " + temVictima.getApellido1() + " " + temVictima.getApellido2());
        TextView tvFecNacVictima = (TextView) convertView.findViewById(R.id.tvFecNacVictima);
        tvFecNacVictima.setText(temVictima.getFecNacimiento());
        TextView tvEstadoVictima = (TextView) convertView.findViewById(R.id.tvEstadoVictima);
        tvEstadoVictima.setText(temVictima.getEstado());
        PrintView pvAgregarVictima = (PrintView) convertView.findViewById(R.id.pvAgregarVictima);

        PrintView pvAgregarGRUPOFAMILIAR = (PrintView) convertView.findViewById(R.id.pvAgregarGRUPOFAMILIAR);




        pvAgregarVictima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vicResponse.onClickAgregarVictimaResponse(position);
            }
        });

        pvAgregarGRUPOFAMILIAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int conM = 0; conM < items.size(); conM++){
                    vicResponse.onClickAgregarVictimaResponse(conM);
                }

            }
        });

        return convertView;
    }


    public emc_victimas_nosugar getItem(int position){
        return items.get(position);
    }

}