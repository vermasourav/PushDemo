package com.verma.mobile.android.pushdemo.networking;

import android.content.Context;

import com.verma.mobile.android.pushdemo.networking.service.HomeService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by sourav.verma on 14-02-2018.
 */


@Module
public class NetworkModule {
    private String BASEURL = "http://private-b8cf44-androidcleancode.apiary-mock.com/";
    private long CACHE_SIZE = 10 * 1024 * 1024;
    private long CACHE_TIME = 432000;
    private String FILE_NAME = "CACHE";
    public Context mContext;


    public enum LogLevel {NONE, BASIC, HEADERS, BODY}

    ;

    private LogLevel logLevel = LogLevel.NONE;

    public NetworkModule(Context pContext, LogLevel logLevel) {
        mContext = pContext;
        this.logLevel = logLevel;
    }

    public NetworkModule(Context pContext) {
        mContext = pContext;
    }


    

    @Provides
    @Singleton
    File provideCacheFile() {
        File cacheFile = new File(mContext.getCacheDir(), FILE_NAME);
        return cacheFile;
    }

    @Provides
    @Singleton
    Cache provideCache(File cacheFile) {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    @Provides
    @Singleton
    Interceptor provideInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Customize the request
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .removeHeader("Pragma")
                        .header("Cache-Control", String.format("max-age=%d", CACHE_TIME))
                        .build();

                okhttp3.Response response = chain.proceed(request);
                response.cacheResponse();
                return response;
            }
        };

    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, Interceptor interceptor, HttpLoggingInterceptor logging) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .addInterceptor(logging)
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    Retrofit provideCall(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public APIClient providesNetworkService(Retrofit retrofit) {
        return retrofit.create(APIClient.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public HomeService providesService(APIClient APIClient) {
        return new HomeService(APIClient);
    }


    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public HttpLoggingInterceptor providesHttpLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (logLevel == LogLevel.NONE) {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        } else if (logLevel == LogLevel.BASIC) {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else if (logLevel == LogLevel.HEADERS) {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        } else if (logLevel == LogLevel.BODY) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return logging;
    }

}
