package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.com.rni.encuestadormovil.DiligenciarPregunta;
import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_respuestas_instrumento;
import co.com.rni.encuestadormovil.model.respuestasIdValor;

/**
 * Created by javierperez on 15/11/15.
 */
public class respuestasCheckAdapter extends ArrayAdapter {

    ArrayList<respuestasIdValor> items;
    List<emc_respuestas_instrumento> alRespuestas;
    ratiosResponse callback;
    Integer positionRespuesta;

    public respuestasCheckAdapter(Context context, int tvID, ArrayList<respuestasIdValor> objects, ratiosResponse callback, Integer positionRespuesta, List<emc_respuestas_instrumento> alRespuestas) {
        super(context, tvID, objects);
        this.items = objects;
        this.callback = callback;
        this.positionRespuesta = positionRespuesta;
        this.alRespuestas = alRespuestas;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View
        final View row = inflater.inflate(R.layout.item_id_check_texto, parent, false);

        TextView valID = (TextView) row.findViewById(R.id.valID);
        valID.setText(items.get(position).getId());

        TextView valNombre = (TextView) row.findViewById(R.id.valNombre);
        valNombre.setText(items.get(position).getValor());

        final EditText textOtro = (EditText) row.findViewById(R.id.textOtro);
        //final AutoCompleteTextView textOtro = (AutoCompleteTextView) row.findViewById(R.id.textOtro);
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


        CheckBox chIdentificador = (CheckBox) row.findViewById(R.id.chIdentificador);
        if (items.get(position).isSelected()) {
            chIdentificador.setChecked(true);
            if (alRespuestas.get(position).getPre_campotex() != null) {
                if (alRespuestas.get(position).getPre_campotex().toString().equals("AB")) {
                    textOtro.setVisibility(View.VISIBLE);
                }
            }

        } else {
            chIdentificador.setChecked(false);
            textOtro.setVisibility(View.GONE);
        }

        chIdentificador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callback.onItemSelected(position, positionRespuesta);

                CheckBox chIdentificador = (CheckBox) row.findViewById(R.id.chIdentificador);
                if (items.get(position).isSelected()) {
                    chIdentificador.setChecked(true);
                    if (alRespuestas.get(position).getPre_campotex() != null) {
                        if (alRespuestas.get(position).getPre_campotex().toString().equals("AB")) {
                            textOtro.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    chIdentificador.setChecked(false);
                    textOtro.setVisibility(View.GONE);
                }




            }
        });

        return row;
    }

}