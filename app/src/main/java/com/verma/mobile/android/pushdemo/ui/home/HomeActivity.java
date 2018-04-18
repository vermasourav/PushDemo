package com.verma.mobile.android.pushdemo.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.verma.mobile.android.pushdemo.R;
import com.verma.mobile.android.pushdemo.models.CityListData;
import com.verma.mobile.android.pushdemo.models.CityListResponse;
import com.verma.mobile.android.pushdemo.networking.service.HomeService;
import com.verma.mobile.android.pushdemo.ui.BaseApp;
import com.verma.mobile.android.pushdemo.ui.contract.HomeContract;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by sourav.verma on 14-02-2018.
 */

public class HomeActivity extends BaseApp implements HomeContract.View {
    HomePresenter presenter;

    @Inject public HomeService HomeService;
    @BindView(R.id.list) public RecyclerView list;
    @BindView(R.id.progress) public ProgressBar progressBar;

    @Override
    public int initContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void init(){
        getDeps().inject(this);
        presenter = new HomePresenter(HomeService, this);
        LinearLayoutManager linearLayoutManager  =new LinearLayoutManager(this);
        list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.getCityList();
    }


    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

  /*  @OnClick(R.id.list)
    public void listItemsClick(CityListData Item){
        Toast.makeText(getApplicationContext(), Item.getName(),Toast.LENGTH_LONG).show();
    }*/
    @Override
    public void getCityListSuccess(CityListResponse cityListResponse) {

        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), cityListResponse.getData(),
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(CityListData Item) {
                        Toast.makeText(getApplicationContext(), Item.getName(),Toast.LENGTH_LONG).show();
                    }
                });

        list.setAdapter(adapter);
    }
}
