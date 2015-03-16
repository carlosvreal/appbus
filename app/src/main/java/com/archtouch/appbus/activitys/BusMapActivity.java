package com.archtouch.appbus.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.archtouch.appbus.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BusMapActivity extends FragmentActivity {

    private GoogleMap mMap;
    private boolean showedLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * init map
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

   private void setUpMap() {

       mMap.setMyLocationEnabled(true);
       mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
           @Override
           public void onMyLocationChange(Location location) {

               if (!showedLocation) {
                   CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                   CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                   mMap.moveCamera(center);
                   mMap.animateCamera(zoom);
                   showedLocation = true;
               }
           }
       });

       mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

           @Override
           public void onMapClick(LatLng latLng) {

               MarkerOptions mOptions = new MarkerOptions();
               mOptions.position(latLng);
               mOptions.title("Select Street");

               mMap.clear();
               mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
               Marker marker = mMap.addMarker(mOptions);

               marker.showInfoWindow();
           }
       });

       mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
           public void onInfoWindowClick(final Marker marker) {
           String[] options = {"Ok", "Cancel"};
           AlertDialog.Builder dialogPin = new AlertDialog.Builder(BusMapActivity.this);
           dialogPin.setItems(options, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                  if(which == 0) {
                       Intent intent = new Intent();
                       String street_name = findStreetName(marker);
                       intent.putExtra(ListMainActivity.STREET_NAME,street_name);
                       setResult(ListMainActivity.MAP_RESULT, intent);
                       finish();
                  }
               }
           });
           dialogPin.show();
           }
       });
   }

    /**
     * find street name
     * @param marker
     * @return string of street name
     */
    private String findStreetName(Marker marker){
        String street = "";
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geo.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,10);
           if (addresses.size() > 0) {
                street = addresses.get(0).getAddressLine(0).toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return street;
    }
}