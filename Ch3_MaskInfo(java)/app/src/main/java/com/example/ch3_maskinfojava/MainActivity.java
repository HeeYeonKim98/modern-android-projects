package com.example.ch3_maskinfojava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ch3_maskinfojava.models.Store;
import com.example.ch3_maskinfojava.models.StoreInfo;
import com.example.ch3_maskinfojava.repository.MaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_store);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

        final StoreAdapter adapter = new StoreAdapter();
        recyclerView.setAdapter(adapter);

        //ui 변경 감지 후 업데이트
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.itemLiveData.observe(this, stores -> {    //해당 activity 를 관찰
            adapter.updateItems(stores);
            getSupportActionBar().setTitle("마스크 재고 있는 곳 : "+stores.size());
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_meun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                viewModel.FetchStoreInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        String count = "100개 이상";
        String remainStat = "충분";
        int color = Color.BLUE;

        switch (store.getRemainStat()){
            case "planty":
                count = "100개 이상";
                remainStat = "충분";
                color = Color.GREEN;
                break;

            case "some":
                count = "30개 이상";
                remainStat = "여유";
                color = Color.YELLOW;
                break;

            case  "few":
                count="2개 이상";
                remainStat = "매진 임박";
                color = Color.RED;
                break;

            case  "empty":
                count="1개 이상";
                remainStat = "매진";
                color = Color.GRAY;
                break;

            default:

        }
        holder.tv_remain.setText(remainStat);
        holder.tv_count.setText(count);
        
        holder.tv_remain.setTextColor(color);
        holder.tv_count.setTextColor(color);


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