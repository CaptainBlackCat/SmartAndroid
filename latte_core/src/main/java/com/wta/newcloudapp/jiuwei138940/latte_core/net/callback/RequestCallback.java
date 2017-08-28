package com.wta.newcloudapp.jiuwei138940.latte_core.net.callback;

import com.wta.newcloudapp.jiuwei138940.latte_core.ui.LatteLoader;
import com.wta.newcloudapp.jiuwei138940.latte_core.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestCallback implements Callback<String>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;

    public RequestCallback(IRequest REQUEST, ISuccess SUCCESS, IFailure FAILURE, IError ERROR, LoaderStyle style) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
         if (response.isSuccessful()) {
             if (call.isExecuted()) {
                 if (SUCCESS != null) {
                     SUCCESS.onSuccess(response.body());
                 }
             }
         }else {
             if (ERROR!=null){
                 ERROR.onError(response.code(),response.message());
             }
         }

         if (LOADER_STYLE!=null){
             stopLoading();
         }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
         if (FAILURE != null){
             FAILURE.onFailure();
         }
         if (REQUEST != null){
             REQUEST.onRequestEnd();
         }

         stopLoading();
    }

    private void stopLoading(){
        if (LOADER_STYLE != null){
            LatteLoader.stopLoading();
        }
    }
}
