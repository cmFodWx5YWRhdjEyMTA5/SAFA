package com.expose.safa.retrofit;

import com.expose.safa.modelclasses.Model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by vineesh on 03/04/2017.
 */

public interface ApiInterface {

    @GET("XPOProductService.svc/GetProductCategoryNames")
    Call<Model> getCategoryNames();

    @GET("XPOBrandService.svc/GetBrandNames")
    Call<Model> getallBrandNames();

    @GET("XPOProductService.svc/GetProducts")
    Call<Model> getProducts (
            @Query("CategoryId") Integer CategoryId,
             @Query("BrandId") Integer BrandId);


    /**
     * downloading Image using retrofit from URL to SD card **/

    @GET
    Call<ResponseBody> getImageFromUrl(@Url String url);

}
