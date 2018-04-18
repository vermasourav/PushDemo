package com.verma.mobile.android.pushdemo.networking;


import com.verma.mobile.android.pushdemo.models.CityListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sourav.verma on 14-02-2018.
 */

public interface APIClient {
    @GET("v1/city")
    Observable<CityListResponse> getCityList();

}
