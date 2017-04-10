package estg.ipvc.mapatrabalho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static estg.ipvc.mapatrabalho.R.id.spinner;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,AdapterView.OnItemSelectedListener,GoogleMap.OnMapClickListener{

    LatLng status = new LatLng(41.6918,-8.8344);
    GoogleMap map;
     Spinner mMapTypeSelector;
     int mMapTypes[] = {
            GoogleMap.MAP_TYPE_NONE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mMapTypeSelector = (Spinner) findViewById(spinner);
        mMapTypeSelector.setOnItemSelectedListener(this);



        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //prepara o mapa e retorna para o metodo onMapaReady
    }

    //Marcar o marker no map ---- implements OnMapReadyCallback
    @Override
    public  void onMapReady(GoogleMap map){
        map.addMarker(new MarkerOptions()
            .position(new LatLng(41.6918,-8.8344))
            .title("Empresa XPTO"));

        //escolher o tipo de vista se é satelite,terreste ..
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        //mapa com zoom e visao vista de cima
       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(status,18));

        //manipular a camera
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(status)
                .zoom(16)
                .bearing(45)
                .tilt(60)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onMapClick(LatLng latLng){
        Intent i = new Intent(this, StreetView.class);
        i.putExtra((Utils.LAT), latLng.latitude);
        i.putExtra((Utils.LONG), latLng.longitude);
        startActivity(i);
    }

    //NAO CONSIGO FAZER PELO OPTIONS MENU NEM PELO SPINNER !!!!!!!!!!!!!!!!!!!!!!!


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        map.setMapType(mMapTypes[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



   /* public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.terrestre) {
           setMapType(GoogleMap.MAP_TYPE_HYBRID);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}

