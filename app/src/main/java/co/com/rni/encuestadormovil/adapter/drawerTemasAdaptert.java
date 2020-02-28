package co.com.rni.encuestadormovil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;

import java.util.List;

import co.com.rni.encuestadormovil.R;
import co.com.rni.encuestadormovil.model.emc_capitulos_terminados;
import co.com.rni.encuestadormovil.model.emc_temas;


/**
 * Created by javierperez on 15/11/15.
 */
public class drawerTemasAdaptert extends ArrayAdapter{

    String hogCodigo;
    private String usuarioLogin;

    public drawerTemasAdaptert(Context context, List<emc_temas> objects, String hogCodigo, String usuarioLogin) {
        super(context, 0, objects);
        this.hogCodigo = hogCodigo;
        this.usuarioLogin = usuarioLogin;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String Result = usuarioLogin;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_temas_item, null);
        }

        LinearLayout llBgTema = (LinearLayout) convertView.findViewById(R.id.llBgTema);
        PrintView pvIcono = (PrintView) convertView.findViewById(R.id.pvIcono);
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);

        emc_temas itemT = (emc_temas) getItem(position);
        Integer OrdenT = itemT.getTem_orden();

        //500 Encuestador
        //499 Administrador
        //498 Andagueda
        //800 Prueba
        //658 NRC

            emc_temas item = (emc_temas) getItem(position);
            Integer Orden = item.getTem_orden();

            tvItem.setText(Orden.toString() + ". " + item.getTem_nombretema());

            String[] parCap = {hogCodigo, item.getTem_idtema().toString()};
            List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);


            if(lsCapTer.size() > 0){
                pvIcono.setIconText(getContext().getResources().getString(R.string.f_ok_outline));
                //Color verde
                llBgTema.setBackgroundColor(getContext().getResources().getColor(R.color.emc_red_pressed));
                llBgTema.setBackground(getContext().getDrawable(R.drawable.border_radius_gren));
            }/*else{
                //Color rojo
                llBgTema.setBackgroundColor(getContext().getResources().getColor(R.color.emc_red));
            }*/


        return convertView;
    }
}
