package com.example.ch3_maskinfojava;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ch3_maskinfojava.models.Store;
import com.example.ch3_maskinfojava.models.StoreInfo;
import com.example.ch3_maskinfojava.repository.MaskService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainViewModel extends ViewModel {
    private static final String TAG = MainActivity.class.getSimpleName();

    public MutableLiveData<List<Store>> itemLiveData = new MutableLiveData<>();
    //변경이 가능한 라이브데이터


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    private MaskService service = retrofit.create(MaskService.class);

    private Call<StoreInfo> storeInfoCall = service.fetchStoreInfo();

    public MainViewModel(){
        FetchStoreInfo();
    }

    //외부에서 fetchstoreinfo를 호출할때 라이브데이터를 관찰하고있다가 변경만 하게 끔 만들기.
    public void FetchStoreInfo(){
        storeInfoCall.clone().enqueue(new Callback<StoreInfo>() { //landscape를 하면 호출을 다시 하지 않는다, 따라서 clone()를 호출하여 새로고침을 가능하게 함
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                List<Store> items = response.body().getStores()
                        .stream()
                        .filter(item -> item.getRemainStat() != null)
                        .collect(Collectors.toList());

                itemLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                Log.e(TAG, "failure", t);
                itemLiveData.postValue(Collections.emptyList());

            }
        });
    }
}
