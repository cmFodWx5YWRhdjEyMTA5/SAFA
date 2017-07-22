package com.expose.safa.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.expose.safa.MainActivity;

import com.expose.safa.R;
import com.expose.safa.fragments.ProductDetails_fragmnet;
import com.expose.safa.fragments.Products;
import com.expose.safa.modelclasses.Model;
import com.expose.safa.utilities.ImageStorage;

import java.io.File;
import java.util.List;

/**
 * Created by vineesh on 04/04/2017.
 */

public class DataAdapter extends  RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<Model> allproducts;
    private Context mContext;




    // empty constructor
    public DataAdapter() {
    }




    public DataAdapter(Context mContext , List<Model> allproducts) {
        this.allproducts = allproducts;
        this.mContext=mContext;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_for_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(DataAdapter.MyViewHolder holder, int i) {

        holder.tv_title.setText(allproducts.get(i).getItem_cName());
        holder.tv_price.setText(String.valueOf(allproducts.get(i).getItem_price()));
        holder.tv_details.setText(allproducts.get(i).getDetails());
        holder.tv_unit.setText(allproducts.get(i).getUnit());


        Glide.with(mContext)
                .load(new File( Environment.getExternalStorageDirectory()+"/Android/data/com.expose.safa/"+allproducts.get(i).getImageurl())) // Uri of the picture
                .placeholder(R.drawable.safa_logo)
                .into(holder.img_product);
    }




    @Override
    public int getItemCount() {

        return allproducts.size();

    }





    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }





    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_details, tv_price,tv_title,tv_unit;
        ImageView img_product;

        public MyViewHolder(final View view) {
            super(view);
            tv_details=(TextView)view.findViewById(R.id.product_details);
            tv_price=(TextView)view.findViewById(R.id.product_price);
            tv_title=(TextView)view.findViewById(R.id.product_title);
            tv_unit=(TextView)view.findViewById(R.id.product_unit);

            img_product=(ImageView)view.findViewById(R.id.img_product);

        }



        public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

            private GestureDetector gestureDetector;
            private DataAdapter.ClickListener clickListener;

            public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final DataAdapter.ClickListener clickListener) {
                this.clickListener = clickListener;
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && clickListener != null) {
                            clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                        }
                    }
                });
            }



            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                    clickListener.onClick(child, rv.getChildPosition(child));
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }



        }


    }

}
