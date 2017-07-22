package com.expose.safa.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expose.safa.MainActivity;
import com.expose.safa.R;
import com.expose.safa.adapter.DataAdapter;
import com.expose.safa.db.DatabaseHelper;
import com.expose.safa.modelclasses.Model;
import com.expose.safa.utilities.Constants;
import com.expose.safa.utilities.ImageStorage;
import com.expose.safa.utilities.Utility;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Products extends Fragment{



    private Spinner spinner_category,
                    spinner_brand;

    DatabaseHelper db;

    public RecyclerView recyclerView;

    public DataAdapter adapter = new DataAdapter();

    RelativeLayout no_Products;

    //products List for Recyclere view
    List<Model> products =new ArrayList<>();

    List<Model> pro =new ArrayList<>();
    List<Model> brands=new ArrayList<>();

    List<String> Al_category=new ArrayList<>();
    List<String> Al_brand=new ArrayList<>();

    int count=0;
    int brandId;
    int cateId;



    public Products() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_product);
        no_Products = (RelativeLayout)root.findViewById(R.id.no_products);
        no_Products.setVisibility(View.GONE);

        spinner_category=(Spinner)root.findViewById(R.id.Spinner_category);
        spinner_brand=(Spinner)root.findViewById(R.id.Spinner_brand);

        db=new DatabaseHelper(getActivity());
        pro= db.getAllCatogories();
        brands=db.getAllbrands();

        prepareCategorySpinner();

        prepareBrandSpinner();

        loadDATA();

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnItemTouchListener(new DataAdapter.MyViewHolder.RecyclerTouchListener(getActivity(), recyclerView
                , new DataAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ArrayList<Model> arraylist = (ArrayList<Model>) products;

                Bundle b=new Bundle();
                b.putInt("position",position);
                b.putSerializable("PRODUCT_LIST",arraylist);

                Fragment f=new ProductDetails_fragmnet( arraylist);
                f.setArguments(b);
                ((MainActivity)getActivity()).LoadFragments(f);


            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));


        return root;
    }




    private void prepareBrandSpinner() {

        brands=db.getAllbrands();

        for (int i=0;i<brands.size();i++)
        {
            Al_brand.add(brands.get(i).getBrand_cName());

        }

        ArrayAdapter AA_brand = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,Al_brand);
        AA_brand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_brand.setAdapter(AA_brand);
        AA_brand.notifyDataSetChanged();



        spinner_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                brandId = brands.get(i).getBrand_nId();

                if(cateId == 0 && brandId == 0)
                {
                    loadDATA();
                }
                else
                {
                    loadSelectedData(cateId,brandId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }





    private void prepareCategorySpinner() {

        for (int i=0;i<pro.size();i++)
        {
            Al_category.add(pro.get(i).getItemCategory_cName());

        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter AA_category =
                new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,Al_category);
        AA_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_category.setAdapter(AA_category);
        AA_category.notifyDataSetChanged();

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                cateId = pro.get(i).getItemCategory_nId();

                if(cateId == 0 && brandId == 0)
                {
                    loadDATA();
                }

                else
                {
                    loadSelectedData(cateId,brandId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }





    private void loadDATA() {

        List<Model> Allproducts=db.getAllProducts();

        if (Allproducts.size() == 0)
        {
            recyclerView.setVisibility(View.GONE);
            no_Products.setVisibility(View.VISIBLE);

        }

//      db.close();
        //adapter = new DataAdapter(getActivity(),Allproducts);
        setRecyclerView(Allproducts);


    }




    private void setRecyclerView(final List<Model> produ) {

        if (produ . size() == 0)
        {
            recyclerView.setVisibility(View.GONE);
            no_Products.setVisibility(View.VISIBLE);

            // Toast.makeText(getActivity(), "No Products Available", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setVisibility(View.VISIBLE);
        no_Products.setVisibility(View.GONE);

        products =produ;
        adapter = new DataAdapter(getActivity(),products);
        recyclerView.setAdapter(adapter);

        // adapter = new DataAdapter(getActivity(),products);

    }




    private void loadSelectedData(int category_selection, int brand_selection) {



        List<Model> allSelectedProducts=db.getSelectedProducts(category_selection,brand_selection);



        if (allSelectedProducts.size() == 0 )

        {

            recyclerView.setVisibility(View.GONE);
            no_Products.setVisibility(View.VISIBLE );

            // Toast.makeText(getActivity(), "No Products Available", Toast.LENGTH_SHORT).show();

        }

        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            no_Products.setVisibility(View.GONE );
            setRecyclerView(allSelectedProducts);
        }



    }





    private boolean isOnline() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }





    @Override
    public void onDetach() {

        super.onDetach();

    }







}
