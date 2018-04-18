package com.verma.mobile.android.pushdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.verma.mobile.android.pushdemo.deps.DaggerDeps;
import com.verma.mobile.android.pushdemo.deps.Deps;
import com.verma.mobile.android.pushdemo.networking.NetworkModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sourav.verma on 14-02-2018.
 */


public abstract class BaseApp  extends AppCompatActivity {

    public Deps deps;
    private Unbinder unbinder;

    public abstract int initContentView();
    public abstract void init();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        unbinder = ButterKnife.bind(this);
        deps = DaggerDeps.builder()
                .networkModule(new NetworkModule(this, NetworkModule.LogLevel.BODY))
                .build();
        init();
    }

    @Override
    protected void onDestroy() {
        if(unbinder!=null )
            unbinder.unbind();
        super.onDestroy();
    }

    public Deps getDeps(){
        return deps;
    }
}
