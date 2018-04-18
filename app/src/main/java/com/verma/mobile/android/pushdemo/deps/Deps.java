package com.verma.mobile.android.pushdemo.deps;


import com.verma.mobile.android.pushdemo.networking.NetworkModule;
import com.verma.mobile.android.pushdemo.ui.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sourav.verma on 14-02-2018.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
 }
