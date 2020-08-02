package com.example.shi.crabrec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Uti.Img2Base64Util;
import model.detail;
import okhttp3.Call;
import scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView;
import widget.EnjoyshopToolBar;

public class MainActivity extends AppCompatActivity implements TakePhoto.TakeResultListener, InvokeListener {
    TakePhoto takePhoto;
    InvokeParam invokeParam;
    Kawaii_LoadingView Kawaii_LoadingView;
    File test;
    Bitmap bitmap;
    ImageView pic;
    Button takePic;
    Button takePic1;
    Button selectPic;
    Button rec;
    Uri uri;
    EnjoyshopToolBar mToolbar;
    String imgUrl;
    JSONObject root = new JSONObject();
    String requestUrl = "http://47.102.146.191/selectPic/";
    //String requestUrl = "http://58.198.165.22:8000/selectPic/";
    detail d= new detail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pic = findViewById(R.id.pic);
        takePic = findViewById(R.id.takePic);
        takePic1 = findViewById(R.id.takePicNew);
        selectPic = findViewById(R.id.selectPic);
        rec = findViewById(R.id.rec);
        Kawaii_LoadingView = findViewById(R.id.Kawaii_LoadingView);
        mToolbar=findViewById(R.id.toolbar);
        changeToolbar();
        init();

    }

    private void init(){
        OkHttpUtils
                .get()
                .url(requestUrl)
                .build()
                .connTimeOut(120000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "加载模型错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(MainActivity.this, "加载模型成功！", Toast.LENGTH_SHORT).show();
                    }
                });

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = getImageCropUri();
                //拍照并裁剪
                takePhoto.onPickFromCapture(uri);
            }
        });
        takePic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
        selectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照并裁剪
                takePhoto.onPickFromGallery();
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test == null) {
                    Toast.makeText(MainActivity.this, "请选择图片或使用相机拍摄图片", Toast.LENGTH_SHORT).show();
                } else {
                    Kawaii_LoadingView.setVisibility(View.VISIBLE);
                    Kawaii_LoadingView.startMoving();
                    mToolbar.setTitle("识别中。。。");
                    OkHttpUtils
                            .post()
                            .url(requestUrl)
                            .addParams("name", "crab.jpg")
                            .addParams("base64", imgUrl)
                            .build()
                            .connTimeOut(120000)
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Kawaii_LoadingView.setVisibility(View.GONE);
                                    Kawaii_LoadingView.stopMoving();
                                    Toast.makeText(MainActivity.this, "请求超时，请重试！", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if (!response.isEmpty()) {
                                        // 3.2 停止动画
                                        Kawaii_LoadingView.setVisibility(View.GONE);
                                        Kawaii_LoadingView.stopMoving();
                                        mToolbar.showRightButtonView();
                                        // detail d=new Gson().fromJson(response, detail.class);
                                       // responseResult=response;
                                        /**/
                                        JSONObject jo = null;
                                        try {
                                            jo = new JSONObject(response);
                                            String base = jo.getString("imgUrl");
                                            d.setNumber(jo.optString("number"));
                                            d.setCategory(jo.optString("category"));
                                            d.setLocation(jo.optString("location"));
                                            d.setProductPhone(jo.optString("productPhone"));
                                            d.setWeight(jo.optString("weight"));
                                            d.setLength(jo.optString("length"));
                                            d.setWeight(jo.optString("width"));
                                            d.setSqure(jo.optString("squre"));
                                            d.setDensity(jo.optString("density"));
                                            d.setTotalYield(jo.optString("totalYield"));
                                            d.setMuYield(jo.optString("muYield"));
                                            d.setPh(jo.optString("ph"));
                                            d.setOxygen(jo.optString("oxygen"));
                                            d.setNitrogen(jo.optString("nitrogen"));
                                            d.setSalt(jo.optString("salt"));
                                            d.setHydrogen(jo.optString("hydrogen"));
                                            JSONArray json1=jo.optJSONArray("sales");
                                            List<detail.Sale>saleList=new ArrayList<>();
                                            if(json1!=null){
                                                for(int i=0;i<json1.length();i++){
                                                    JSONObject job = json1.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                                    detail.Sale s=new detail.Sale();
                                                    s.setSaleLocation(job.getString("saleLocation"));
                                                    s.setSaleName(job.getString("saleName"));
                                                    s.setSalePhone(job.getString("salePhone"));
                                                    s.setSaleTime(job.getString("saleTime"));
                                                    saleList.add(s);
                                                }
                                            }
                                            d.setSales(saleList);
                                            Bitmap result=Img2Base64Util.stringToBitmap(base);
                                            pic.setImageBitmap(result);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        //Bitmap result = Img2Base64Util.stringToBitmap(d.getImgUrl());

                                    }
                                }
                            });
                }
            }
        });
    }
    /**
     * 改变标题栏
     */
    private void changeToolbar() {
        mToolbar.hideSearchView();
        mToolbar.setRightButtonText("溯源");
        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",d);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mToolbar.hideRightButtonView();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void takeSuccess(final TResult result) {
        //成功取得照片
        test = new File(result.getImage().getOriginalPath());
        if (test.exists()) {
            imgUrl = "data:image/jpeg;base64,";
            imgUrl = imgUrl + Img2Base64Util.imageToBase64(result.getImage().getOriginalPath());
            JSONArray array = new JSONArray();
            JSONObject lan1 = new JSONObject();
            try {
                lan1.put("name", "crab.jpg").put("base64", imgUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(lan1);
            try {
                root.put("data", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeFile(result.getImage().getOriginalPath());
            pic.setImageBitmap(bitmap);
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        //取得失败
        Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        //取消
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + "crab.jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

}
