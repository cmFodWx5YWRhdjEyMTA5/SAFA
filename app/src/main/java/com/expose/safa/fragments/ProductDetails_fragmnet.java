package com.expose.safa.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.expose.safa.R;
import com.expose.safa.modelclasses.Model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class ProductDetails_fragmnet extends Fragment {


    TextView tv_productName,tv_productPrice,tv_productDescription,tv_productUnit;
    ImageView img_detail_image;

    List<Model> PRODUCTS;
    private MyViewPagerAdapter myViewPagerAdapter;
    Integer position;
    private ViewPager viewPager;
    ImageButton leftNav,rightNav;





    public ProductDetails_fragmnet() {

        // Required empty public constructor
    }

    public ProductDetails_fragmnet(ArrayList<Model> arraylist) {
        this.PRODUCTS = arraylist;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_product_details_, container, false);

        viewPager = (ViewPager) root.findViewById(R.id.viewpager);

        leftNav = (ImageButton) root.findViewById(R.id.left_nav);
        rightNav = (ImageButton) root.findViewById(R.id.right_nav);

        position = getArguments().getInt("position");
        leftNav.setVisibility(View.INVISIBLE);


        //setting view pager adapter
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        //setting current image position to the view pager

        viewPager.setCurrentItem(position);



        //left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;

                    viewPager.setCurrentItem(tab);

                } else if (tab == 0) {

                    viewPager.setCurrentItem(tab);


                }



            }
        });


        //  right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int tab = viewPager.getCurrentItem();


                tab++;
                viewPager.setCurrentItem(tab);
            }
        });

        return root;
    }

    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {



        @Override
        public void onPageSelected(int po) {



/**
 * hiding the right left arrow at begining
 * and Hidng right arrow at Last position**/

            if (po == 0)
            {
                leftNav.setVisibility(View.INVISIBLE);
                rightNav.setVisibility(View.VISIBLE);
            }

            if (po == PRODUCTS.size() -1)
            {
                rightNav.setVisibility(View.INVISIBLE);
                leftNav.setVisibility(View.VISIBLE);
            }
            else
            {
                leftNav.setVisibility(View.VISIBLE);
                rightNav.setVisibility(View.VISIBLE);
            }

        }



        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {





        }



        @Override
        public void onPageScrollStateChanged(int arg0) {




        }


    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



    @Override
    public void onDetach() {
        super.onDetach();

    }




    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;




        public MyViewPagerAdapter() {
        }



        @Override
        public Object instantiateItem(ViewGroup container, int itemPosition) {



            // Toast.makeText(getActivity(), "glidepos"+position, Toast.LENGTH_SHORT).show();

            layoutInflater = (LayoutInflater) getActivity().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View root = layoutInflater.inflate(R.layout.product_fullscreen_preview, container, false);

            tv_productName = (TextView)root.findViewById(R.id.tv_detail_productName);
            tv_productDescription = (TextView)root.findViewById(R.id.tv_detail_prodcutDescription);
            tv_productPrice = (TextView)root.findViewById(R.id.tv_detail_productPrice);
            tv_productUnit = (TextView)root.findViewById(R.id.tv_detail_productUnit);
            img_detail_image = (ImageView) root.findViewById(R.id.img_detail_image);

            tv_productName.setText(PRODUCTS.get(itemPosition).getItem_cName());
            tv_productDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. In non risus ut purus rhoncus consectetur non sit amet sem. Sed vel eros sed turpis sagittis molestie sit amet auctor elit. In tincidunt mi eu commodo pellentesque. Vestibulum blandit ut arcu ac rutrum. Nunc pretium ut diam at condimentum. Etiam diam neque, convallis id sem sit amet, vehicula vestibulum est. Phasellus dapibus arcu nec scelerisque iaculis. Phasellus sed interdum orci. Suspendisse elementum orci nec diam iaculis volutpat et ac nibh.");
            tv_productPrice.setText("\u20B9 "+PRODUCTS.get(itemPosition).getItem_price());
            tv_productUnit.setText(PRODUCTS.get(itemPosition).getUnit());



            Glide.with(getActivity())
                    .load(new File(Environment.getExternalStorageDirectory()+"/Android/data/com.expose.safa/"+PRODUCTS.get(itemPosition).getImageurl())) // Uri of the picture
                    .placeholder(R.drawable.safa_logo)
                    .override(1000,1000)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_detail_image);

            container.addView(root);



            return root;
        }



        @Override
        public int getCount() {

            return PRODUCTS.size()                         ;
        }




        @Override
        public boolean isViewFromObject(View view, Object obj) {

            return view == ((View) obj);
        }




        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }




    }
}
