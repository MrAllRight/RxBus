package com.rxbus.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rxbus.R;
import com.rxbus.RxBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private Disposable subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable.timer(3, TimeUnit.SECONDS).repeat(10)
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept( Long aLong) throws Exception {
                          Log.d("info","Rxbus post="+aLong);
                          RxBus.getDefault().post(aLong);
                      }
                  });
        subject =RxBus.getDefault().toObservable(Long.class).subscribe(new Consumer<Long>() {
            @Override
            public void accept( Long aLong) throws Exception {
                Log.d("info","Rxbus receive="+aLong);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("info","Rxbus onDestroy=");
        subject.dispose();
    }
}
