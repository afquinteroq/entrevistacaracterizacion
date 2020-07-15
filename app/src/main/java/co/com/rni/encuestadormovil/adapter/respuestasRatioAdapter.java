package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.*;

/**
 * Created by javierperez on 15/11/15.
 */
public class respuestasRatioAdapter extends ArrayAdapter{

    ArrayList<respuestasIdValor> items;
    List<emc_respuestas_instrumento> alRespuestas;
    ratiosResponse callback;
    Integer positionRespuesta;


    public respuestasRatioAdapter(Context context, int tvID, ArrayList<respuestasIdValor> objects, ratiosResponse callback, Integer positionRespuesta, List<emc_respuestas_instrumento> alRespuestas) {
        super(context, tvID, objects);
        this.items = objects;
        this.callback = callback;
        this.positionRespuesta = positionRespuesta;
        this.alRespuestas = alRespuestas;
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
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View row=inflater.inflate(R.layout.item_id_ratio_texto, parent, false);
        View row=inflater.inflate(R.layout.item_id_ratio_texto_autocompletar, parent, false);
        TextView valID=(TextView)row.findViewById(R.id.valID);
        valID.setText(items.get(position).getId());

        TextView valNombre=(TextView)row.findViewById(R.id.valNombre);
        valNombre.setText(items.get(position).getValor());

        //EditText textOtro = (EditText) row.findViewById(R.id.textOtro);
        AutoCompleteTextView textOtro = (AutoCompleteTextView) row.findViewById(R.id.textOtro);

        //Asignar Color
        /*LinearLayout ll_item_ration_texto = (LinearLayout) row.findViewById(R.id.ll_item_ration_texto) ;
        ll_item_ration_texto.setBackground(getContext().getDrawable(R.drawable.border_radius_gray));*/

        textOtro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callback.onOtroChanged(position, positionRespuesta, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        RadioButton rbIdentificador = (RadioButton) row.findViewById(R.id.rbIdentificador);
        if(items.get(position).isSelected()){
            rbIdentificador.setChecked(true);
            if(alRespuestas.get(position).getPre_campotex() != null){
                if(alRespuestas.get(position).getPre_campotex().toString().equals("AB"))
                {
                    textOtro.setVisibility(View.VISIBLE);
                }else if (alRespuestas.get(position).getPre_campotex().toString().equals("ABC"))
                {
                    Context context = parent.getContext();
                    String[] profesiones =  context.getResources().getStringArray(R.array.profesiones);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, profesiones);
                    textOtro.setThreshold(3);
                    textOtro.setAdapter(adapter);

                    textOtro.setVisibility(View.VISIBLE);


                }else if (alRespuestas.get(position).getPre_campotex().toString().equals("ABCP"))
                {
                    Context context = parent.getContext();
                    String[] paises =  context.getResources().getStringArray(R.array.paises);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, paises);
                    textOtro.setThreshold(3);
                    textOtro.setAdapter(adapter);

                    textOtro.setVisibility(View.VISIBLE);


                }else if (alRespuestas.get(position).getPre_campotex().toString().equals("ABCR"))
                {
                    Context context = parent.getContext();
                    String[] resguardos =  context.getResources().getStringArray(R.array.resguardos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, resguardos);
                    textOtro.setThreshold(3);
                    textOtro.setAdapter(adapter);

                    textOtro.setVisibility(View.VISIBLE);


                }else if (alRespuestas.get(position).getPre_campotex().toString().equals("ABCE"))
                {
                    Context context = parent.getContext();
                    String[] enfermedadesruinosas =  context.getResources().getStringArray(R.array.resguardos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, enfermedadesruinosas);
                    textOtro.setThreshold(3);
                    textOtro.setAdapter(adapter);

                    textOtro.setVisibility(View.VISIBLE);


                }else if (alRespuestas.get(position).getPre_campotex().toString().equals("ABCN"))
                {
                    Context context = parent.getContext();
                    String[] comunidadesnegras =  context.getResources().getStringArray(R.array.comunidadesnegras);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, comunidadesnegras);
                    textOtro.setThreshold(3);
                    textOtro.setAdapter(adapter);

                    textOtro.setVisibility(View.VISIBLE);


                }
                /*else if (alRespuestas.get(position).getPre_campotex().toString().equals("TR"))
                {
                    Toast.makeText(getContext(), "Terminar encuesta", Toast.LENGTH_SHORT).show();
                }*/

            }
        }else{
            rbIdentificador.setChecked(false);
            textOtro.setVisibility(View.GONE);
        }
        rbIdentificador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callback.onItemSelected(position, positionRespuesta);
                }

            }
        });

        return row;
    }





    public respuestasIdValor getItem(int position){
        return items.get(position);
    }

}