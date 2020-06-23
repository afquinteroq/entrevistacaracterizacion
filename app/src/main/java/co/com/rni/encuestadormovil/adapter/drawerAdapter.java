package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.github.johnkil.print.PrintView;
import java.util.List;

import co.com.rni.encuestadormovil.*;
import co.com.rni.encuestadormovil.model.*;

/**
 * Created by javierperez on 15/11/15.
 */
public class drawerAdapter extends ArrayAdapter {

    public drawerAdapter(Context context, List<opcionMenu> objects) {
        super(context, 0, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_item, null);
        }

        PrintView pvIcono = (PrintView) convertView.findViewById(R.id.pvIcono);
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);

        final opcionMenu item = (opcionMenu) getItem(position);
        pvIcono.setIconText(item.getIcono());
        tvItem.setText(item.getTexto());

        return convertView;
    }

}
