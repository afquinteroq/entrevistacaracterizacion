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
import co.com.rni.encuestadormovil.model.*;




/**
 * Created by javierperez on 15/11/15.
 */
public class drawerTemasAdapter extends ArrayAdapter{

    String hogCodigo;
    private String usuarioLogin;

    public drawerTemasAdapter(Context context, List<emc_temas> objects, String hogCodigo, String usuarioLogin) {
        super(context, 0, objects);
        this.hogCodigo = hogCodigo;
        this.usuarioLogin = usuarioLogin;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drawer_temas_item, null);
        }

        LinearLayout llBgTema = (LinearLayout) convertView.findViewById(R.id.llBgTema);
        PrintView pvIcono = (PrintView) convertView.findViewById(R.id.pvIcono);
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);

        emc_temas itemT = (emc_temas) getItem(position);

        //500 Encuestador
        //499 Administrador
        //498 Andagueda
        //800 Prueba
        //658 NRC
        //710 ACNUR

            emc_temas item = (emc_temas) getItem(position);
            Integer Orden = item.getTem_orden();

            List<emc_usuarios> ls = emc_hogares.find(emc_usuarios.class, " UPPER(NOMBREUSUARIO) = (\n" +
                    "SELECT UPPER(usuusuariocreacion) FROM EMCHOGARES WHERE UPPER(HOGCODIGO) = ?)", hogCodigo.toUpperCase());

        List<emc_hogares> lsHogar = emc_hogares.find(emc_hogares.class, "UPPER(HOGCODIGO) = ? ", hogCodigo.toUpperCase());
        //Configuración ACNUR

        try{

            if(lsHogar.size()>0)
            {

                if(Orden.toString().equals("8") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/ )
                {
                    tvItem.setText("4" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("9") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("5" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("10") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("6" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("11") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("7" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("13") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("8" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("31") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("9" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("32") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("10" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("33") && ls.get(0).getIdPerfil().equals("710") /*&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("11" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("34") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("12" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("35") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("13" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("36") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("14" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("37") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("15" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("38") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("16" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("39") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("17" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("40") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("18" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("41") && ls.get(0).getIdPerfil().equals("710") /*"&& lsHogar.get(0).getHog_tipo().equals("B")*/)
                {
                    tvItem.setText("19" + ". " + item.getTem_nombretema());

                }
                //
                //Configuración NRC
                else if(Orden.toString().equals("24"))
                {
                    tvItem.setText("1" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("25"))
                {
                    tvItem.setText("2" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("26"))
                {
                    tvItem.setText("3" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("27"))
                {
                    tvItem.setText("4" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("28"))
                {
                    tvItem.setText("5" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("29"))
                {
                    tvItem.setText("6" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("30"))
                {
                    tvItem.setText("7" + ". " + item.getTem_nombretema());
                }
                else{
                    tvItem.setText(Orden.toString() + ". " + item.getTem_nombretema());
                }
                //

            }else{

                //
                //Configuración NRC
                if(Orden.toString().equals("24"))
                {
                    tvItem.setText("1" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("25"))
                {
                    tvItem.setText("2" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("26"))
                {
                    tvItem.setText("3" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("27"))
                {
                    tvItem.setText("4" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("28"))
                {
                    tvItem.setText("5" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("29"))
                {
                    tvItem.setText("6" + ". " + item.getTem_nombretema());

                }else if(Orden.toString().equals("30"))
                {
                    tvItem.setText("7" + ". " + item.getTem_nombretema());
                }
                else{
                    tvItem.setText(Orden.toString() + ". " + item.getTem_nombretema());
                }
                //
            }


        }catch (Exception e){
            e.getMessage();
        }


            String[] parCap = {hogCodigo, item.getTem_idtema().toString()};
            List<emc_capitulos_terminados> lsCapTer = emc_capitulos_terminados.find(emc_capitulos_terminados.class, "HOGCODIGO = ? AND TEMIDTEMA = ?", parCap);


            if(lsCapTer.size() > 0){
                pvIcono.setIconText(getContext().getResources().getString(R.string.f_ok_outline));
                //Color verde
                llBgTema.setBackgroundColor(getContext().getResources().getColor(R.color.emc_estado_ok));
                llBgTema.setBackground(getContext().getDrawable(R.drawable.border_radius_gren));
            }else{
                //Color rojo
                llBgTema.setBackgroundColor(getContext().getResources().getColor(R.color.emc_red));
                llBgTema.setBackground(getContext().getDrawable(R.drawable.border_radius_red));
            }


        return convertView;
    }
}
