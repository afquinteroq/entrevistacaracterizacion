package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.*;


/**
 * Created by javierperez on 15/11/15.
 */
public class miembrosHogarAdapter extends ArrayAdapter<emc_miembros_hogar>  {
    private List<emc_miembros_hogar> items;
    private conformarHogarResponse vicResponse;
    private Spinner  spTipoPersona;

    public miembrosHogarAdapter(Context context, List<emc_miembros_hogar> lista, conformarHogarResponse vicResponse) {
        super(context, 0, lista);
        this.items = lista;
        this.vicResponse = vicResponse;
    }

    public List<emc_miembros_hogar> getItems() {
        return items;
    }

    public void setItems(List<emc_miembros_hogar> items) {
        this.items = items;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)parent.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_miembro_hogar, null);
        emc_miembros_hogar temMiembro = items.get(position);

        TextView tvIDMiebro = (TextView) convertView.findViewById(R.id.tvIDMiebro);
        tvIDMiebro.setText(temMiembro.getPer_idpersona().toString());
        TextView tvTipoDocMiebro = (TextView) convertView.findViewById(R.id.tvTipoDocMiebro);
        tvTipoDocMiebro.setText(temMiembro.getTipoDoc());
        TextView tvDocumentoMiebro = (TextView) convertView.findViewById(R.id.tvDocumentoMiebro);
        tvDocumentoMiebro.setText(temMiembro.getDocumento());
        TextView tvNombreMiebro = (TextView) convertView.findViewById(R.id.tvNombreMiebro);
        tvNombreMiebro.setText(temMiembro.getNombre1() + " " + temMiembro.getNombre2() + " " + temMiembro.getApellido1() + " " + temMiembro.getApellido2());
        //17/02/2020 este toca validar si toca quitarlo
        final RadioButton rbEsJefe = (RadioButton) convertView.findViewById(R.id.rbEsJefe);
        spTipoPersona = (Spinner) convertView.findViewById(R.id.spTipoPersona);
        //spTipoPersona.setOnItemSelectedListener(this);

        //Lista de tipos de personas
        /*ArrayList<String> lsTipoPersona = new ArrayList<>();

        lsTipoPersona.add(0,"Seleccione tipo persona");
        lsTipoPersona.add(1,"Autorizado");
        lsTipoPersona.add(2,"Tutor");
        lsTipoPersona.add(3,"Cuidador Permanente");
        lsTipoPersona.add(4,"Miembro hogar");
        */
        ArrayAdapter<CharSequence> adTipoPersona = ArrayAdapter.createFromResource(getContext(),  R.array.tipopersonas, android.R.layout.simple_spinner_item);
        //ArrayAdapter<String> adTipoPersona =  new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item, lsTipoPersona);
        spTipoPersona.setAdapter(adTipoPersona);
        spTipoPersona.setSelection(0,false);

        spTipoPersona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionPersona, long id) {
                String valorseleccion = parent.getItemAtPosition(positionPersona).toString();
                int itemseleccion = positionPersona;
                /*vicResponse.onSelectTipoPersona(position);*/
                if(positionPersona == 1 || positionPersona == 2 || positionPersona == 3){
                    vicResponse.onSelectJefeHogar(position,positionPersona);
                    //vicResponse.onSelectTipoPersona(position);
                    //rbEsJefe.setChecked(true);
                }
                /*
                if(positionPersona == 4){
                    //rbEsJefe.setChecked(false);
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*
        if(temMiembro.getInd_jefe().equals("SI")){
            rbEsJefe.setChecked(true);
        }else{
            rbEsJefe.setChecked(false);
        }
        */
        /*
        rbEsJefe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    vicResponse.onSelectJefeHogar(position);
            }
        });
        */
        TextView tvEstadoMiebro = (TextView) convertView.findViewById(R.id.tvEstadoMiebro);
        tvEstadoMiebro.setText(temMiembro.getEstado());

        PrintView pvVerGrupoMiebro = (PrintView) convertView.findViewById(R.id.pvVerGrupoMiebro);
        pvVerGrupoMiebro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vicResponse.onVerHogar(position);
            }
        });

        PrintView pvEliminarMiebro = (PrintView) convertView.findViewById(R.id.pvEliminarMiebro);
        pvEliminarMiebro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vicResponse.onDeleteMiembroHogar(position);
            }
        });

        return convertView;
    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valorseleccion = parent.getItemAtPosition(position).toString();
        int itemseleccion = position;
        vicResponse.onSelectTipoPersona(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
