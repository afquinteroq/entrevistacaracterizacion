package co.com.rni.encuestadormovil;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import co.com.rni.encuestadormovil.adapter.personaCardViewAdapter;
import co.com.rni.encuestadormovil.model.itempersonacard;

public class PersonasCardViewd extends AppCompatActivity {


    private RecyclerView recyclerPersona;
    private personaCardViewAdapter personaCardViewAdapter;
    private LinearLayout llIrInicio;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        llIrInicio = (LinearLayout) findViewById(R.id.llIrInicio);


        layoutManager = new GridLayoutManager(this, 2);

        llIrInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent irMainActividy = new Intent(v.getContext(), MainActivity.class);
                startActivity(irMainActividy);

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerPersona = (RecyclerView) findViewById(R.id.RecyclerPersona);
        //recyclerPersona.setLayoutManager(new LinearLayoutManager(this));
        recyclerPersona.setLayoutManager(layoutManager);
        //checkScreenSize();

        personaCardViewAdapter = new personaCardViewAdapter(this);
        //agregarPersonajesDragonBall();
        personaCardViewAdapter.adicionarListaREncuestas(obtenerPersonajesDragonBall());
        recyclerPersona.setAdapter(personaCardViewAdapter);


    }



    public ArrayList<itempersonacard> obtenerPersonasCard(){
        ArrayList<itempersonacard> persona = new ArrayList<>();
        persona.add(new itempersonacard("Goku","Tierra","","https://www.eldescomunal.com/wp-content/uploads/2018/03/goku.jpg"));
        persona.add(new itempersonacard("Veguete","Vegetta","","https://img.peru21.pe/files/ec_article_multimedia_gallery/uploads/2018/11/28/5bff1d83c9685.jpeg"));
        persona.add(new itempersonacard("Freezer","Desconocido","","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQnoL9vJf0CSWU-QVQ5N4oChMYSoawvXDjlLZdhPX4Dy329hTd0iA"));
        persona.add(new itempersonacard("Jiren","Universo 11","","https://pm1.narvii.com/6694/543121207610a19df37c157f8c4c8f2dd16a0bcf_hq.jpg"));
        persona.add(new itempersonacard("Broly","Veguita","","https://i.pinimg.com/originals/c4/34/80/c4348064c53909359784fb5f3214153c.jpg"));
        persona.add(new itempersonacard("Hit","Universo 7","","https://gaminguardian.com/wp-content/uploads/2018/01/Fighterz-Hit.png"));
        persona.add(new itempersonacard("Android 17","Tierra","","https://www.sonyers.com/wp-content/uploads/2018/09/Dragon-Ball-FighterZ-Androide-17-2.jpg"));
        persona.add(new itempersonacard("Yamcha","Tierra","","https://i.ytimg.com/vi/mcfFp9QgSNE/maxresdefault.jpg"));
        persona.add(new itempersonacard("Picollo","Namekusein","","https://banner2.kisspng.com/20180516/wke/kisspng-piccolo-dragon-ball-fighterz-gohan-trunks-majin-bu-5afbe42a6f5499.122848981526457386456.jpg"));
        persona.add(new itempersonacard("Majin buu","Desconocido","","https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-623200.png"));
        return persona;
    }

    public void agregarPersonajesDragonBall(){
        itempersonacard p;
        p = new itempersonacard("Goku","Tierra","","https://www.eldescomunal.com/wp-content/uploads/2018/03/goku.jpg");p.save();
        p = new itempersonacard("Veguete","Vegetta","","https://img.peru21.pe/files/ec_article_multimedia_gallery/uploads/2018/11/28/5bff1d83c9685.jpeg");p.save();
        p = new itempersonacard("Freezer","Desconocido","","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQnoL9vJf0CSWU-QVQ5N4oChMYSoawvXDjlLZdhPX4Dy329hTd0iA");p.save();
        p = new itempersonacard("Jiren","Universo 11","","https://pm1.narvii.com/6694/543121207610a19df37c157f8c4c8f2dd16a0bcf_hq.jpg");p.save();
        p = new itempersonacard("Broly","Veguita","","https://i.pinimg.com/originals/c4/34/80/c4348064c53909359784fb5f3214153c.jpg");p.save();
        p = new itempersonacard("Hit","Universo 7","","https://gaminguardian.com/wp-content/uploads/2018/01/Fighterz-Hit.png");p.save();
        p = new itempersonacard("Android 17","Tierra","","https://www.sonyers.com/wp-content/uploads/2018/09/Dragon-Ball-FighterZ-Androide-17-2.jpg");p.save();
        p = new itempersonacard("Yamcha","Tierra","","https://i.ytimg.com/vi/mcfFp9QgSNE/maxresdefault.jpg");p.save();
        p = new itempersonacard("Picollo","Namekusein","","https://banner2.kisspng.com/20180516/wke/kisspng-piccolo-dragon-ball-fighterz-gohan-trunks-majin-bu-5afbe42a6f5499.122848981526457386456.jpg");p.save();
        p = new itempersonacard("Majin buu","Desconocido","","https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-623200.png");p.save();

    }


    public ArrayList<itempersonacard> obtenerPersonajesDragonBall(){
        ArrayList<itempersonacard> ArrayPersonas = new ArrayList<>();

        List<itempersonacard> lsPersonasSugar = itempersonacard.find(itempersonacard.class,null,null);

        for (int c = 0; c < lsPersonasSugar.size(); c++){
            itempersonacard tmPersona = lsPersonasSugar.get(c);
            ArrayPersonas.add(tmPersona);

        }
        return ArrayPersonas;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent irMainActividy = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(irMainActividy);
                    finish();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }

            return false;
        }
    };


    public void checkScreenSize() {


        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {

            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        900 );
                recyclerPersona.setLayoutParams(lp1);
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:

                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:

                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        420 );
                recyclerPersona.setLayoutParams(lp3);
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                break;
            default:
                Toast.makeText(getBaseContext(), "Screen no definido: ", Toast.LENGTH_SHORT).show();
        }
    }


    // Detectar cuando se presiona el botón de retroceso
    @Override
    public void onBackPressed() {
        finish();
        Intent irParametricas = new Intent(getApplicationContext(), Parametricas.class);
        startActivity(irParametricas);
    }

}
