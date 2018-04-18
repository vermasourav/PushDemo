package com.verma.mobile.android.pushdemo.ui.home;

import com.verma.mobile.android.pushdemo.models.CityListResponse;
import com.verma.mobile.android.pushdemo.networking.NetworkError;
import com.verma.mobile.android.pushdemo.networking.service.HomeService;
import com.verma.mobile.android.pushdemo.ui.contract.HomeContract;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sourav.verma on 14-02-2018.
 */

public class HomePresenter implements HomeContract.presenter {

    private final HomeService HomeService;
    private final HomeContract.View view;
    private CompositeSubscription subscriptions;

    public HomePresenter(HomeService HomeService, HomeContract.View view) {
        this.HomeService = HomeService;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void getCityList() {
        view.showWait();

        Subscription subscription = HomeService.getCityList(new HomeService.APICallback() {
            @Override
            public void onSuccess(CityListResponse cityListResponse) {
                view.removeWait();
                view.getCityListSuccess(cityListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    @Override
    public void onStopSubscriptions() {
        subscriptions.unsubscribe();
    }
}
