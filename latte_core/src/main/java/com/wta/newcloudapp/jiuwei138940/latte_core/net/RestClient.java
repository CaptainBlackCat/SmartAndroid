package com.wta.newcloudapp.jiuwei138940.latte_core.net;

import android.content.Context;

import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.IError;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.IFailure;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.IRequest;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.ISuccess;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.callback.RequestCallback;
import com.wta.newcloudapp.jiuwei138940.latte_core.net.download.DownloadHandler;
import com.wta.newcloudapp.jiuwei138940.latte_core.ui.LatteLoader;
import com.wta.newcloudapp.jiuwei138940.latte_core.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;



public class RestClient {

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
    private final RequestBody BODY;
    private final File FILE;
    private LoaderStyle LOADER_STYLE;
    private Context CONTEXT;


    public RestClient(String URL,
                      WeakHashMap<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      File file,
                      Context context,
                      LoaderStyle loaderStyle) {
        this.URL = URL;
        this.PARAMS = params;
        this.DOWNLOAD_DTR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }


    private void request(HttpMethod method){
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE!=null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call =service.postRaw(URL,BODY);
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call  = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                // call = RestCreator.getRestService().upload(URL,body);
                call = service.upload(URL,body);
                break;
            default:
                break;
        }
        if (call!=null){
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback(){
        return new RequestCallback(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){
        if (BODY == null) {
            request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }

    }

    public final void put(){
        if (BODY == null) {
            request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }

    public final void download(){
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DTR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR,PARAMS)
                .handleDownload();
    }
}
