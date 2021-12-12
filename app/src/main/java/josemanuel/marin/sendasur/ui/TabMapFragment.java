package josemanuel.marin.sendasur.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import josemanuel.marin.sendasur.R;
import josemanuel.marin.sendasur.controller.SendaViewModel;
import josemanuel.marin.sendasur.model.Senda;

public class TabMapFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mMap;
    Activity mActivity;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    public static final String EXTRA_MESSAGE = "josemanuel.marin.sendasur.extra.MESSAGE";
    SendaViewModel viewModel;

    public TabMapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment,container,false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                viewModel =new SendaViewModel(mActivity.getApplication());
                viewModel.getAllSendas().observe(getViewLifecycleOwner(), new Observer<List<Senda>>() {
                    @Override
                    public void onChanged(List<Senda> sendas) {
                        if (sendas!=null) {
                            for (int i = 0; i < sendas.size(); i++) {
                                LatLng nSenda = new LatLng(sendas.get(i).getLatitud(), sendas.get(i).getLongitud());
                                mMap.addMarker(new MarkerOptions().position(nSenda).title(sendas.get(i).getNombre()));

                            }
                        }
                    }
                });

                mMap = googleMap;
                setMapStyle(mMap);
                enableMyLocation();
                setInfoWindowClickToPanorama(mMap);

                LatLng cadiz = new LatLng(36.4664461, -5.7864458);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cadiz, 8.5f));

                mMap.setMinZoomPreference(8.5f);

                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });

        return view;
    }
    private void setMapStyle(GoogleMap map) {
        try {
            boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mActivity =(Activity) context;
        }
    }

    private void setInfoWindowClickToPanorama(GoogleMap map) {
        map.setOnInfoWindowClickListener(
                new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if(MainActivity.getTwoPane()){
                            String title = marker.getTitle();

                            MostrarInfoTabletFragment fragment = MostrarInfoTabletFragment.newInstance(title);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.senda_container, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                        else {
                            Intent intent = new Intent(getContext(), mostrarInfo.class);
                            intent.putExtra(EXTRA_MESSAGE, marker.getTitle());
                            startActivity(intent);
                        }
                    }
                });
    }
}
