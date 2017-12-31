package com.example.ehsan.safeparking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * { interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap googleMap;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
////                    Fragment selectedFragment = SearchFragment.newInstance(null, "Launcher");
////                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////                    transaction.replace(R.id.content, selectedFragment);
////                    transaction.commit();
//                    return true;
//                }
//                return false;
//            }
//        });

        mMapView = (MapView) view.findViewById(R.id.parking_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // Getting LocationManager object from System Service LOCATION_SERVICE
                final LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

                // Creating a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Getting the name of the best provider
                final String provider = locationManager.getBestProvider(criteria, true);

                // Getting Current Location
                final Location loc = locationManager.getLastKnownLocation(provider);

                    if(loc!=null){
                        //PLACE THE INITIAL MARKER
                        drawMarker(loc);
                        LatLng origin=new LatLng(loc.getLatitude(),loc.getLongitude());
                        LatLng dest=new LatLng(MainActivity.latitude,MainActivity.longitude);
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        downloadTask.execute(url);
                    }
                    locationManager.requestLocationUpdates(provider, 20000, 0, new android.location.LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            drawMarker(location);

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });


//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(MainActivity.latitude, MainActivity.longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.mrkr)));
                marker.setTag("University");
                LatLng coordinate = new LatLng(MainActivity.latitude, MainActivity.longitude);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 12);
                mMap.animateCamera(yourLocation);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        if(marker.getTag().equals("University"))
                        {
                            Fragment selectedFragment = ParkingArea.newInstance(null, null);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.content, selectedFragment);
                            transaction.commit();
                        }
                        return true;
                    }
                });

//                LatLng origin = new LatLng(3.214732, 101.747047);
//                LatLng dest = new LatLng(3.214507, 101.749697);

// Getting URL to the Google Directions API

            }

//                googleMap = mMap;
//                int count = 0;
//                for (Map<String, Object> map : SearchFragment.userList) {
//                    double longitude = (double) map.get("longitude");
//                    double latitude = (double) map.get("latitude");
//                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.blood)));
//                    marker.setTag("UR " + count);
//                    count++;
//                }
//                count = 0;
//                for (Map<String, Object> map : SearchFragment.BBList) {
//                    double longitude = (double) map.get("longitude");
//                    double latitude = (double) map.get("latitude");
//                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));
//                    marker.setTag("BB " + count);
//                    count++;
//                }
//                LatLng coordinate = new LatLng(MainActivity.latitude, MainActivity.longitude);
//                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 10);
//                mMap.animateCamera(yourLocation);
//                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(Marker marker) {
//                        String[] str = marker.getTag().toString().split(" ");
//                        int position = Integer.parseInt(str[1]);
//                        String name = null, bgroup = null, address = null, contct = null, city = null, title = null, addr = null;
//                        //Bitmap image;
//                        if (str[0].equals("UR")) {
//                            name = SearchFragment.userList.get(position).get("name").toString();
//                            bgroup = SearchFragment.userList.get(position).get("bloodGroup").toString();
//                            address = SearchFragment.userList.get(position).get("gender").toString().equals("1") ? "Male" : "Female";
//                            contct = SearchFragment.userList.get(position).get("contact").toString();
//                            city = SearchFragment.userList.get(position).get("city").toString();
//                            addr = "Gender : ";
//                            title = "Blood Doner";
//                            //image = (Bitmap) SearchFragment.userList.get(position).get("image");
//                        } else if (str[0].equals("BB")) {
//                            name = SearchFragment.BBList.get(position).get("BankName").toString();
//                            bgroup = SearchFragment.BBList.get(position).get("bloodGroup").toString();
//                            address = SearchFragment.BBList.get(position).get("bankAddress").toString();
//                            contct = SearchFragment.BBList.get(position).get("bankContact").toString();
//                            city = SearchFragment.BBList.get(position).get("bankCity").toString();
//                            title = "BloodBank";
//                            addr = "Address : ";
//                            //image = (Bitmap) SearchFragment.userList.get(position).get("image");
//                        }
//                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        View view = layoutInflater.inflate(R.layout.donor_detail, null);
//                        TextView nameTXT = (TextView) view.findViewById(R.id.nameTXT);
//                        TextView bgroupTXT = (TextView) view.findViewById(R.id.bgroupTXT);
//                        TextView addressTXT = (TextView) view.findViewById(R.id.addressTXT);
//                        TextView AddrTXT = (TextView) view.findViewById(R.id.Addr);
//                        TextView contactTXT = (TextView) view.findViewById(R.id.contactTXT);
//                        TextView cityTXT = (TextView) view.findViewById(R.id.cityTXT);
//                        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//                        nameTXT.setText(name);
//                        bgroupTXT.setText(bgroup);
//                        addressTXT.setText(address);
//                        AddrTXT.setText(addr);
//                        contactTXT.setText(contct);
//                        cityTXT.setText(city);
//                        //imageView.setImageBitmap(image);
//                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setIcon(R.drawable.blood).setTitle(title + " Detail")
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                }).setView(view).create();
//                        alertDialog.show();
//                        //Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                });
//
//
            //}
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void drawMarker(Location location){
       // googleMap.clear();
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(currentPosition)
                .snippet("Lat:" + location.getLatitude() + "Lng:"+ location.getLongitude())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("ME"))
                .setTag("My Location");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private String getDirectionsUrl(LatLng origin,LatLng dest){


        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }



    /** A method to download json data from url */
    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionJSONParser parser = new DirectionJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }

}
