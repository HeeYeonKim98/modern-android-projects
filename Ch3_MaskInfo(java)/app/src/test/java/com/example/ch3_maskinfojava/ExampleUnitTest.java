package com.example.ch3_maskinfojava;

import com.example.ch3_maskinfojava.models.StoreInfo;
import com.example.ch3_maskinfojava.repository.MaskService;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //통신 test
    @Test
    public void retrofitText() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MaskService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        MaskService service = retrofit.create(MaskService.class);

        //코드가 아닌, 받아오는 준비를 함
        Call<StoreInfo> storeInfoCall = service.fetchStoreInfo();

        //동기 방식으로 값을 바로 불러옴 / execute를 실행했으므로 try-catch를 불러오지 않으면 throws로 던짐
        StoreInfo storeInfo =  storeInfoCall.execute().body();

        assertEquals(222, storeInfo.getCount());
        assertEquals(222, storeInfo.getStores().size());
    }
}