package com.wta.newcloudapp.jiuwei138940.latte_core.net.download;

import android.os.AsyncTask;

import com.wta.newcloudapp.jiuwei138940.latte_core.net.RestCreator;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.IError;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.IFailure;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.IRequest;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.ISuccess;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DownloadHandler {
    private final String URL;
   // private static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
   private WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();

    private final IRequest REQUEST;
    private final String DOWNLOAD_DTR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String URL,
                           IRequest REQUEST,
                           String DOWNLOAD_DTR,
                           String EXTENSION,
                           String NAME,
                           ISuccess SUCCESS,
                           IFailure FAILURE,
                           IError ERROR,
                           WeakHashMap<String,Object> PARAMS) {
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.DOWNLOAD_DTR = DOWNLOAD_DTR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.PARAMS = PARAMS;
    }

    public final void handleDownload(){
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       if (response.isSuccessful()){
                           final ResponseBody responseBody = response.body();
                           final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS);
                           task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DTR,EXTENSION,response,NAME);

                           //这里一定要注意判断，否则文件下载不全
                           if (task.isCancelled()){
                               if (REQUEST!=null){
                                   REQUEST.onRequestEnd();
                               }
                           }
                       }
                       else {
                           if (ERROR!=null){
                               ERROR.onError(response.code(),response.message());
                           }
                       }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE!=null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
