package com.example.ch3_maskinfojava;

import android.location.Location;
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
    public MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>(); //처음에는 로딩 중.

    public Location location;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    private MaskService service = retrofit.create(MaskService.class);



    //외부에서 fetchstoreinfo를 호출할때 라이브데이터를 관찰하고있다가 변경만 하게 끔 만들기.
    public void FetchStoreInfo(){ //위도 경도를 fetchstoreinfo에 넘겨서 내 위도 경도 주변에서 가까운 약국을 찾는다.

        //로딩 시작
        loadingLiveData.setValue(true);

        service.fetchStoreInfo(location.getLatitude(),location.getLongitude()).enqueue(new Callback<StoreInfo>() { //landscape를 하면 호출을 다시 하지 않는다, 따라서 clone()를 호출하여 새로고침을 가능하게 함
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                List<Store> items = response.body().getStores()
                        .stream()
                        .filter(item -> item.getRemainStat() != null )
                        .filter(item->!item.getRemainStat().equals("empty"))
                        .collect(Collectors.toList());

                for (Store store: items){
                    double distance = LocationDistance.distance(location.getLatitude(),location.getLongitude(), store.getLat(), store.getLng(),"k");
                    store.setDistance(distance);
                }

                Collections.sort(items);

                itemLiveData.postValue(items);

                //로딩 끝 -> 로딩이 끝나는 데에 false로 할 때, set이 아닌 postValue 함수를 사용해야한다.
                //true값으로 넣어져 있는 라이브데이터에 새롭게 값을 재정의해줘야함.
                loadingLiveData.postValue(false);
            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                Log.e(TAG, "failure", t);
                itemLiveData.postValue(Collections.emptyList());

                //실패했을 때 로딩 끝
                loadingLiveData.postValue(false);

            }
        });
    }
}
