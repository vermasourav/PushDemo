package com.verma.mobile.android.pushdemo.networking.service;


import com.verma.mobile.android.pushdemo.models.CityListResponse;
import com.verma.mobile.android.pushdemo.networking.APIClient;
import com.verma.mobile.android.pushdemo.networking.NetworkError;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sourav.verma on 14-02-2018.
 */


public class HomeService {

    private final com.verma.mobile.android.pushdemo.networking.APIClient APIClient;

    public HomeService(APIClient APIClient) {
        this.APIClient = APIClient;
    }

    public Subscription getCityList(final APICallback callback) {

        return APIClient.getCityList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CityListResponse>>() {
                    @Override
                    public Observable<? extends CityListResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CityListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(CityListResponse cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    public interface APICallback {
        void onSuccess(CityListResponse cityListResponse);
        void onError(NetworkError networkError);
    }
}
