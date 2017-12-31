package com.example.ehsan.safeparking;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ParkingArea extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ParkingArea() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParkingArea.
     */
    // TODO: Rename and change types and number of parameters
    public static ParkingArea newInstance(String param1, String param2) {
        ParkingArea fragment = new ParkingArea();
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

    List<Map<String,Integer>> list1=new LinkedList();
    List<Map<String,Integer>> list2=new LinkedList();
    List<Map<String,Integer>> list3=new LinkedList();
    List<Map<String,Integer>> list4=new LinkedList();
    List<String> Statuslist1=new LinkedList();
    List<String> Statuslist2=new LinkedList();
    List<String> Statuslist3=new LinkedList();
    List<String> Statuslist4=new LinkedList();
    int[] left_images={R.drawable.left_car_empty,R.drawable.no_parking,R.drawable.left_reserved,R.drawable.left_car1,R.drawable.left_car2,R.drawable.left_car3};
    int[] right_images={R.drawable.right_car_empty,R.drawable.right_no_parking,R.drawable.right_reserved,R.drawable.right_car1,R.drawable.right_car2,R.drawable.right_car3};
    String [] Status={"empty","no_parking","reserved","occupied","occupied","occupied"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        for(int i=0;i<12;i++)
        {
            Random rand = new Random();
            int index=(int) rand.nextInt(6);
            Map<String,Integer> map1=new HashMap<>();
            map1.put("image",left_images[index]);
            list1.add(map1);
            Statuslist1.add(Status[index]);
            index=(int)rand.nextInt(6);
            Map<String,Integer> map2=new HashMap<>();
            map2.put("image",right_images[index]);
            list2.add(map2);
            Statuslist2.add(Status[index]);
            index=(int)rand.nextInt(6);
            Map<String,Integer> map3=new HashMap<>();
            map3.put("image",left_images[index]);
            list3.add(map3);
            Statuslist3.add(Status[index]);
            index=(int)rand.nextInt(6);
            Map<String,Integer> map4=new HashMap<>();
            map4.put("image",right_images[index]);
            list4.add(map4);
            Statuslist4.add(Status[index]);

        }
        View view=inflater.inflate(R.layout.fragment_parking_area, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Fragment selectedFragment = MapFragment.newInstance(null, null);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, selectedFragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });
        final ListView listview1=(ListView)view.findViewById(R.id.First_parking_Row);
        ListView listview2=(ListView)view.findViewById(R.id.Second_parking_Row);
        ListView listview3=(ListView)view.findViewById(R.id.Third_parking_Row);
        ListView listview4=(ListView)view.findViewById(R.id.Fourth_parking_Row);
        final SimpleAdapter adapter1=new SimpleAdapter(getActivity().getApplicationContext(),list1,R.layout.parkinglot_adapter,new String[]{"image"},new int[]{R.id.imageView2});
        listview1.setAdapter(adapter1);
        final SimpleAdapter adapter2=new SimpleAdapter(getActivity().getApplicationContext(),list2,R.layout.parkinglot_adapter,new String[]{"image"},new int[]{R.id.imageView2});
        listview2.setAdapter(adapter2);
        final SimpleAdapter adapter3=new SimpleAdapter(getActivity().getApplicationContext(),list3,R.layout.parkinglot_adapter,new String[]{"image"},new int[]{R.id.imageView2});
        listview3.setAdapter(adapter3);
       final SimpleAdapter adapter4=new SimpleAdapter(getActivity().getApplicationContext(),list4,R.layout.parkinglot_adapter,new String[]{"image"},new int[]{R.id.imageView2});
        listview4.setAdapter(adapter4);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),"Long press for Reservation!",Toast.LENGTH_SHORT).show();

            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),"Long press for Reservation!",Toast.LENGTH_SHORT).show();

            }
        });
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),"Long press for Reservation!",Toast.LENGTH_SHORT).show();

            }
        });
        listview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(),"Long press for Reservation!",Toast.LENGTH_SHORT).show();

            }
        });
        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int indx=i;
                if(Statuslist1.get(i).equals("empty"))
                {
                    if(Login.isLoggedIn)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                                .setTitle("Slot Reservation")
                                .setView(R.layout.reservation_dialogue)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Reservation Confirmed",Toast.LENGTH_SHORT).show();
                                       list1.remove(indx);
                                        Map<String,Integer> map=new HashMap<>();
                                        map.put("image",left_images[2]);
                                       list1.add(indx,map);
                                       adapter1.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).setCancelable(false);
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Sorry! Login First.",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getActivity().getApplicationContext(),Statuslist1.get(i),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int indx=i;
                if(Statuslist2.get(i).equals("empty"))
                {
                    if(Login.isLoggedIn)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                                .setTitle("Slot Reservation")
                                .setView(R.layout.reservation_dialogue)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Reservation Confirmed",Toast.LENGTH_SHORT).show();
                                        list2.remove(indx);
                                        Map<String,Integer> map=new HashMap<>();
                                        map.put("image",right_images[2]);
                                        list2.add(indx,map);
                                        adapter2.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).setCancelable(false);
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Sorry! Login First.",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getActivity().getApplicationContext(),Statuslist1.get(i),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listview3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int indx=i;
                if(Statuslist3.get(i).equals("empty"))
                {
                    if(Login.isLoggedIn)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                                .setTitle("Slot Reservation")
                                .setView(R.layout.reservation_dialogue)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Reservation Confirmed",Toast.LENGTH_SHORT).show();
                                        list3.remove(indx);
                                        Map<String,Integer> map=new HashMap<>();
                                        map.put("image",left_images[2]);
                                        list3.add(indx,map);
                                        adapter3.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).setCancelable(false);
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Sorry! Login First.",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getActivity().getApplicationContext(),Statuslist1.get(i),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listview4.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int indx=i;
                if(Statuslist4.get(i).equals("empty"))
                {
                    if(Login.isLoggedIn)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                                .setTitle("Slot Reservation")
                                .setView(R.layout.reservation_dialogue)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Reservation Confirmed",Toast.LENGTH_SHORT).show();
                                        list4.remove(indx);
                                        Map<String,Integer> map=new HashMap<>();
                                        map.put("image",right_images[2]);
                                        list4.add(indx,map);
                                        adapter4.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).setCancelable(false);
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),"Sorry! Login First.",Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getActivity().getApplicationContext(),Statuslist1.get(i),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
