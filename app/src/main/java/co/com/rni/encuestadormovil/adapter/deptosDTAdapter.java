package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.EMC_DTPUNTOSATENCION;

public class deptosDTAdapter extends ArrayAdapter {
    List<EMC_DTPUNTOSATENCION> items;

    public deptosDTAdapter(Context context, int tvID, List<EMC_DTPUNTOSATENCION> objects){
        super(context, tvID, objects);
        items = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.item_id_texto, parent, false);
        TextView valID=(TextView)row.findViewById(R.id.valID);
        valID.setText(items.get(position).getIddepartamento().toString());
        TextView valNombre=(TextView)row.findViewById(R.id.valNombre);
        valNombre.setText(items.get(position).getDepartamento());
        return row;
    }

    public EMC_DTPUNTOSATENCION getItem(int position){
        return items.get(position);
    }
}
