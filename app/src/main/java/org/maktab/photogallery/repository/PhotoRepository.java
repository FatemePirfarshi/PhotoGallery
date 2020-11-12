package org.maktab.photogallery.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.remote.NetworkParams;
import org.maktab.photogallery.data.remote.retrofit.FlickerService;
import org.maktab.photogallery.data.remote.retrofit.RetrofitInstance;
import org.maktab.photogallery.network.FlickrFetcher;
import org.w3c.dom.ls.LSInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoRepository {

    private static final String TAG = "PhotoRepository";

    private final FlickerService mFlickerService;
    private final MutableLiveData<List<GalleryItem>> mPopularItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GalleryItem>> mSearchItemsLiveData = new MutableLiveData<>();

    public MutableLiveData<List<GalleryItem>> getPopularItemsLiveData(){
        return mPopularItemsLiveData;
    }

    public MutableLiveData<List<GalleryItem>> getSearchItemsLiveData(){
        return mSearchItemsLiveData;
    }

    public PhotoRepository() {
        Retrofit retrofit = RetrofitInstance.getInstance().getRetrofit();
        mFlickerService = retrofit.create(FlickerService.class);
    }

    public List<GalleryItem> fetchPopularItems(){
        Call<List<GalleryItem>> call = mFlickerService.listItems(NetworkParams.getPopularOptions());
        try {
            Response<List<GalleryItem>> response = call.execute();
            return response.body();
        }catch (IOException e){
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void  fetchPopularItemsAsync(){
        Call<List<GalleryItem>> call =
                mFlickerService.listItems(NetworkParams.getPopularOptions());

        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                List<GalleryItem> items = response.body();
                mPopularItemsLiveData.setValue(items);
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public List<GalleryItem> fetchSearchItems(String query){
        Call<List<GalleryItem>> call = mFlickerService.listItems(NetworkParams.getSearchOptions(query));
        try {
            Response<List<GalleryItem>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void fetchSearchItemsAsync(String query){
        Call<List<GalleryItem>> call =
                mFlickerService.listItems(NetworkParams.getSearchOptions(query));

        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                List<GalleryItem> items = response.body();
                mSearchItemsLiveData.setValue(items);
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}