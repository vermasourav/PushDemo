package com.verma.mobile.android.pushdemo.ui.contract;

import com.verma.mobile.android.pushdemo.models.CityListResponse;

/**
 * Created by sourav.verma on 15-02-2018.
 */

public interface HomeContract {

    public interface View {
        void showWait();
        void removeWait();
        void onFailure(String appErrorMessage);
        void getCityListSuccess(CityListResponse cityListResponse);

    }

    public interface presenter {
        public void getCityList();
        public void onStopSubscriptions();
    }
}
