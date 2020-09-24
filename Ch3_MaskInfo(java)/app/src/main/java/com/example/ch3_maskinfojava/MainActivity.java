package com.example.ch3_maskinfojava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ch3_maskinfojava.models.Store;
import com.example.ch3_maskinfojava.models.StoreInfo;
import com.example.ch3_maskinfojava.repository.MaskService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_store);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

        final StoreAdapter adapter = new StoreAdapter();
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MaskService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        MaskService service = retrofit.create(MaskService.class);

        //코드가 아닌, 받아오는 준비를 함
        Call<StoreInfo> storeInfoCall = service.fetchStoreInfo();

        //동기 방식으로 값을 바로 불러옴 / execute를 실행했으므로 try-catch를 불러오지 않으면 throws로 던짐
        storeInfoCall.enqueue(new Callback<StoreInfo>() {
            @Override
            public void onResponse(Call<StoreInfo> call, Response<StoreInfo> response) {
                List<Store> items = response.body().getStores();
                adapter.updateItems(items);

            }

            @Override
            public void onFailure(Call<StoreInfo> call, Throwable t) {
                Log.e(TAG, "failure", t);
            }
        });



    }
}

class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder>{

    private List<Store> mItems = new ArrayList<>();

    public  void updateItems(List<Store> items){
        mItems=items;
        notifyDataSetChanged(); //ui 갱
    }

    //뷰홀더를 만드는 부분
    //뷰홀더 클래스를 만들어주고 리턴시켜야함
    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false); //adapter가 항상 쓰는 패턴, 사 외워야하는건 아니고.. 개발자 홈페이지에 가이드 보고 하면 되는 것임.

        return new StoreViewHolder(view);
    }

    //실제 데이터를 표시하는 부분
    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = mItems.get(position);

        holder.tv_name.setText(store.getName());
        holder.tv_addr.setText(store.getAddr());
        holder.tv_dist.setText("1.0km");
        holder.tv_remain.setText(store.getRemainStat());
        holder.tv_count.setText("100개");

    }

    //데이터의 갯수
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //아이템 뷰 정보를 가지고 있는 클래스
    static class StoreViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_addr;
        TextView tv_dist;
        TextView tv_remain;
        TextView tv_count;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name= itemView.findViewById(R.id.textview_name);
            tv_addr= itemView.findViewById(R.id.textview_addr);
            tv_dist= itemView.findViewById(R.id.textview_dist);
            tv_remain= itemView.findViewById(R.id.textview_remain);
            tv_count= itemView.findViewById(R.id.textview_count);

        }
    }

}