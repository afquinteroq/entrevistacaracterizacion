package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_veredas;

/**
 * Created by ASUS on 22/08/2018.
 */

public class veredasAdapter extends ArrayAdapter {

    List<emc_veredas> items;

    public veredasAdapter(Context context, int tvID, List<emc_veredas> objects) {
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
        valID.setText(items.get(position).getCoddane_ver());
        TextView valNombre=(TextView)row.findViewById(R.id.valNombre);
        valNombre.setText(items.get(position).getNom_ver());
        return row;
    }

    public emc_veredas getItem(int position){
        return items.get(position);
    }


}
