package com.expose.safa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expose.safa.db.DatabaseHelper;
import com.expose.safa.fragments.AboutFragment;
import com.expose.safa.fragments.ContactFragment;
import com.expose.safa.fragments.EnquiryFragment;
import com.expose.safa.fragments.Products;
import com.expose.safa.modelclasses.Model;
import com.expose.safa.retrofit.ApiClient;
import com.expose.safa.utilities.Constants;
import com.expose.safa.utilities.ImageStorage;
import com.google.gson.Gson;
import com.squareup.picasso.Target;

import java.io.FileOutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Licence



public class MainActivity extends AppCompatActivity
{



    //Layouts clicks
    /**

     Copyright [2016] [VINEESH-EXPOSE]

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

     **/


    /**
     *
     * Activity for loading layout resources
     *
     * This activity is used to display different layout resources and fragment
     *
     * @author VINEESH-EXPOSE
     * @version 2016.001-alpha
     *
     *
     */

    ProgressDialog pDialog;
    DatabaseHelper db;
    private static final int REQUEST_CALL = 1;
    private ApiClient mApiClient;
    BackgroundTask backtask;
    private Bitmap bitmap ;
    private FileOutputStream fos;
    public static String imagePath;

    ImageStorage img;
    Toolbar toolbar;
    CoordinatorLayout coordinatorLayout;





    int d_name=0;
    int count=0;
    boolean doubleBackToExitPressedOnce = false;
    PopupWindow mpopup;

    LinearLayout layout_product,layout_contact,layout_about,layout_enquiry,foote;

    LinearLayout layout_call,layout_facebook,layout_web;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setIcon(R.drawable.ic_home_black);
        getSupportActionBar().setIcon(R.drawable.ic_home_black);
        getSupportActionBar().setTitle(null);

        InitializationOf();

        layout_call=(LinearLayout)findViewById(R.id.layout_call);
        layout_facebook=(LinearLayout)findViewById(R.id.layout_facebook);
        layout_web = (LinearLayout)findViewById(R.id.layout_website);

        layout_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(Constants.WEB));
                startActivity(intent);
            }
        });


        img = new ImageStorage();


        db = new DatabaseHelper(getApplicationContext());
        mApiClient = new ApiClient();


        if (isOnline()) {


            backtask = new BackgroundTask();

            backtask.execute("");

        } else {

            /**
             *
             * if inter net connection is no
             *             avialable show snackbar**/

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Network Available Go Offline !!! ",10000);
            View view = snackbar.getView();
            CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.BOTTOM;

            view.setLayoutParams(params);
            // Changing snackbar background color
            ViewGroup group = (ViewGroup) snackbar.getView();
            group.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);

            snackbar.show();

        }


        layout_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment f = new Products();
                LoadFragments(f);
            }
        });





        layout_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = new AboutFragment();
                LoadFragments(f);
            }
        });





        layout_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = new ContactFragment();
                LoadFragments(f);
            }
        });




        layout_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment f = new EnquiryFragment();
                LoadFragments(f);
            }
        });




        layout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(Constants.FACEBOOK));
                startActivity(intent);

            }
        });

        layout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has not been granted.
                    // requestCallPermission();

                }
                else
                {
                    Intent call=new Intent(Intent.ACTION_CALL,
                            Uri.parse("tel:"+Constants.GT_PH_NO.trim()));
                    startActivity(call);
                }

            }
        });




    }



    private void requestCallPermission() {

        //Log.i("hia", "CAMERA permission has NOT been granted. Requesting permission.");
        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);

        }

        else

        {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL);
        }



    }




    private void InitializationOf() {

        layout_product=(LinearLayout)findViewById(R.id.layout_products);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        layout_about = (LinearLayout)findViewById(R.id.layout_aboutUs);
        layout_contact = (LinearLayout)findViewById(R.id.layout_Contact_us);
        layout_enquiry = (LinearLayout)findViewById(R.id.layout_enquiry);


    }




    public void Product() {


        Call<Model> call = mApiClient.getApiInterface().getProducts(00,00);


        try {

            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {

                    if (response.isSuccessful())
                    {

                        List<Model> areaData = response.body().getProductsResult();


                        //  areaData = response.body().getProductsResult();

                        int size = areaData.size();
                        //Check Array not null
                        if (size != 0){
                            Model p = new Model();

                            if (db.getAllProducts()==null)
                            {
                                for(int i = 0; i< size; i++){

                                    p.setBrand_nId(areaData.get(i).getBrand_nId());
                                    p.setDetails(areaData.get(i).getDetails());
                                    p.setCategory_nId(areaData.get(i).getCategory_nId());
                                    p.setItem_nId(areaData.get(i).getItem_nId());


                                    String img_name=areaData.get(i).getImageurl();
                                    loadURL(img_name);

                                    p.setImageurl(img_name);
                                    p.setItem_price(areaData.get(i).getItem_price());
                                    p.setUnit(areaData.get(i).getUnit());
                                    p.setItem_cName(areaData.get(i).getItem_cName());

                                    db.addProducts(p);


                                }
                            }

                            else

                            {
                                db.deleteProducts();

                                for(int i = 0; i< size; i++){

                                    p.setBrand_nId(areaData.get(i).getBrand_nId());
                                    p.setDetails(areaData.get(i).getDetails());
                                    p.setCategory_nId(areaData.get(i).getCategory_nId());

                                    String img_name=areaData.get(i).getImageurl();

                                    loadURL(img_name);

                                    p.setImageurl(img_name);
                                    p.setItem_nId(areaData.get(i).getItem_nId());
                                    p.setItem_price(areaData.get(i).getItem_price());
                                    p.setUnit(areaData.get(i).getUnit());
                                    p.setItem_cName(areaData.get(i).getItem_cName());
                                    //

                                    db.addProducts(p);




                                }


                            }


                            db.close();
                        }


                        //   progress.hide();

                        // progress.hide();
                    }



                }

                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    //Toast.makeText(getApplication(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    //    Log.w("hai"," Error: "+t.getMessage());
                    //  progress.hide();
                }
            });
        } catch (Exception e) {



            e.printStackTrace();
        }


    }



    public void SaveImage(String name) {


        if(!ImageStorage.checkifImageExists(name))
        {

            ImageStorage.saveToSdCard(bitmap, name);
        }



    }







    public void loadURL(final String img_title) {

        String url=Constants.CATEGORY_IMG_URL+img_title;

        //final String j1= String.valueOf(j);

        Log.d("url",url.toString());

        Call<ResponseBody> call = mApiClient.getApiInterface().getImageFromUrl(url);
        call.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        bitmap= BitmapFactory.decodeStream(response.body().byteStream());

                        SaveImage(img_title);

                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // TODO
            }
        });


    }



    public void Categories() {

        Call<Model> call = mApiClient.getApiInterface().getCategoryNames();


        call.enqueue(new Callback<Model>() {
            @SuppressLint("Boom")
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.d("responsecode", String.valueOf(response.code()));


                // Log.d("FullResponce",new Gson().toJson(response.body().getGetProductCategoryNamesResult()));


                if(response.isSuccessful()) {


                    //CATEGORIES
                    List<Model> item = response.body().getGetProductCategoryNamesResult();

                    Model m = new Model();

                    if (db.getAllCatogories().equals(null)) {


                        for (int i = 0; i < item.size(); i++) {


                            m.setItemCategory_cName(item.get(i).getItemCategory_cName());
                            m.setItemCategory_nId(item.get(i).getItemCategory_nId());

                            db.addcategory(m);
                            //Log.d("Al_category",item.get(i).getItemCategory_cName().toString());
                        }

                        Log.d("addedcat", "added items");
                    } else {
                        db.deleteCatogories();
                        Log.d("deletedcat", "deleted called");


                        for (int i = 0; i < item.size(); i++) {


                            m.setItemCategory_cName(item.get(i).getItemCategory_cName());
                            m.setItemCategory_nId(item.get(i).getItemCategory_nId());

                            db.addcategory(m);
                            //Log.d("Al_category",item.get(i).getItemCategory_cName().toString());
                        }
                        Log.d("addedcat2", "added items");
                    }

                    db.close();

                }


            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Toast.makeText(MainActivity.this, "please Make sure that you are connected with internet1 ", Toast.LENGTH_SHORT).show();

            }

        });

    }





    private void BrandLists() {



        Call<Model> call = mApiClient.getApiInterface().getallBrandNames();


        call.enqueue(new Callback<Model>() {
            @SuppressLint("Boom")
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.d("responsecode2", String.valueOf(response.code()));


                Log.d("FullResponce",new Gson().toJson(response.body().getGetBrandNamesResult()));

                if (response.isSuccessful()) {


                    //CATEGORIES
                    List<Model> item = response.body().getGetBrandNamesResult();

                    Model m = new Model();


                    if (db.getAllbrands().equals(null))
                    {


                        for (int i = 0; i < item.size(); i++) {


                            m.setBrand_cName(item.get(i).getBrand_cName());
                            m.setBrand_nId(item.get(i).getBrand_nId());

                            db.addBrands(m);

                            //Log.d("Al_category",item.get(i).getItemCategory_cName().toString());
                        }

                        Log.d("addedbrands", "added items");
                    }

                    else
                    {
                        db.deleteBrands();
                        Log.d("deletedbrands", "deleted called");


                        for (int i = 0; i < item.size(); i++) {


                            m.setBrand_cName(item.get(i).getBrand_cName());
                            m.setBrand_nId(item.get(i).getBrand_nId());

                            db.addBrands(m);
                            Log.d("addedbrands", "added items");
                            //Log.d("Al_category",item.get(i).getItemCategory_cName().toString());
                        }
                        Log.d("addedb", "added items");
                    }

                }

                db.close();

                // db.close();



            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                Toast.makeText(MainActivity.this, "please Make sure that you are connected with internet2 ", Toast.LENGTH_SHORT).show();

            }

        });




    }




    private boolean isOnline() {


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }




    //Load Fragments here

    public void LoadFragments(Fragment fr_company) {
        SetToolBar();


        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Container_for_Fragments,fr_company,"done");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack("back");
        ft.commit();


    }



    private void SetToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setIcon(null);



        if (getSupportFragmentManager().getBackStackEntryCount()>=0)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // getSupportActionBar().setHomeAsUpIndicator(null);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do something you want
                    onBackPressed();
                }
            });
        }


    }


    @Override
    public void onBackPressed() {

                /* if (getSupportFragmentManager().getClass().equals(SlideshowActivity.class))
                 {

                     hideLayout();
                 }



*/               if(getSupportFragmentManager().getBackStackEntryCount() == 1)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setIcon(R.drawable.ic_home_black);
            getSupportActionBar().setTitle(null);

        }



        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;



            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Press back Again to exit ",Snackbar.LENGTH_LONG);
            snackbar.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            return;
        }
    }




    public class BackgroundTask extends AsyncTask<String,Void,String> {





        public BackgroundTask() {

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait..cat.");
            pDialog.setCancelable(false);
            pDialog.show();

        }




        @Override
        protected String doInBackground(String... strings) {

            Categories();

            BrandLists();

            Product();

            return null;
        }




        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (pDialog.isShowing())
                pDialog.dismiss();


        }
    }




}
