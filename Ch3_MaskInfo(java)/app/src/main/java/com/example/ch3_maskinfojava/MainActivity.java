package com.example.ch3_maskinfojava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ch3_maskinfojava.models.Store;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel viewModel;

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                performAction();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        }; //ㅇㅓ떤 처리를 해놓은건지 정의해 놓은 함수
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        //
    }

    @SuppressLint("MissingPermission") //permision 처리 안해서 나오는 에러를 그냥 처리 안해도 에러를 숨기려고 어노테이션 처리됨
    private void performAction() {
        fusedLocationClient.getLastLocation()
                .addOnFailureListener(this,e->{
                    Log.e(TAG, "performAction: ", e.getCause());
                })
                .addOnSuccessListener(this, location -> {
                    Log.d(TAG,"performAction : "+location);
                    if (location != null) {
                        Log.d(TAG,"getLatitude"+location.getLatitude());
                        Log.d(TAG, "getLongitude"+location.getLongitude());

                        viewModel.location = location;
                        viewModel.FetchStoreInfo();
                    }
                });


        RecyclerView recyclerView = findViewById(R.id.recyclerview_store);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

        final StoreAdapter adapter = new StoreAdapter();
        recyclerView.setAdapter(adapter);

        //ui 변경 감지 후 업데이트
        viewModel.itemLiveData.observe(this, stores -> {    //해당 activity 를 관찰
            adapter.updateItems(stores);
            getSupportActionBar().setTitle("마스크 재고 있는 곳 : "+stores.size());
        });

        viewModel.loadingLiveData.observe(this, isLoading ->{
            if(isLoading){
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            }else{
                findViewById(R.id.progressBar).setVisibility(View.GONE);

            }
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
        holder.tv_dist.setText(String.format("%.2f km",store.getDistance()));

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