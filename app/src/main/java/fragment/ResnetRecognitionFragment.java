package fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shi.crabrec.Camera2Activity;
import com.example.shi.crabrec.CameraActivity;
import com.example.shi.crabrec.CameraActivity1;
import com.example.shi.crabrec.DetailActivity;
import com.example.shi.crabrec.FailActivity;
import com.example.shi.crabrec.MainActivity;
import com.example.shi.crabrec.R;
import com.example.shi.crabrec.ResnetDetailActivity;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Uti.Img2Base64Util;
import model.detail;
import okhttp3.Call;
import scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView;
import widget.EnjoyshopToolBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResnetRecognitionFragment extends TakePhotoFragment {
    TakePhoto takePhoto;
    InvokeParam invokeParam;
    scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView Kawaii_LoadingView;
    File test;
    static Bitmap bitmap;
    static ImageView pic;
    Button takePic;
    Button takePic1;
    Button selectPic;
    Button rec;
    Uri uri;
    EnjoyshopToolBar mToolbar;
    static String imgUrl;
    static JSONObject root = new JSONObject();

    //String requestUrl = "http://39.107.102.60:8000/selectPic/";
    String requestUrl = "http://47.102.146.191/resnetRecongnize/";
    // String requestUrl = "http://192.168.1.104:8000/resnetRecongnize/";
    detail d= new detail();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_resnetrecognition, container, false);
        pic = view.findViewById(R.id.pic);
        takePic = view.findViewById(R.id.takePic);
        takePic1 = view.findViewById(R.id.takePicNew);
        selectPic = view.findViewById(R.id.selectPic);
        rec = view.findViewById(R.id.rec);
        Kawaii_LoadingView = view.findViewById(R.id.Kawaii_LoadingView);
        mToolbar=view.findViewById(R.id.toolbar);

        //takePhoto=getTakePhoto();
        changeToolbar();
        init();
        return view;
    }

    @Override
    public void onResume() {
        mToolbar.setTitle("智能识别");
        super.onResume();
    }

    private void init(){
        uri = getImageCropUri();
        test = new File(uri.getPath());
        /*
        OkHttpUtils
                .get()
                .url(requestUrl)
                .build()
                .connTimeOut(50000000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(), "加载模型错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Toast.makeText(getContext(), "加载模型成功！", Toast.LENGTH_SHORT).show();
                    }
                });*/

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
                Intent intent=new Intent(getContext(), Camera2Activity.class);
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
                    Toast.makeText(getContext(), "请选择图片或使用相机拍摄图片", Toast.LENGTH_SHORT).show();
                } else {
                    Kawaii_LoadingView.setVisibility(View.VISIBLE);
                    Kawaii_LoadingView.startMoving();
                    mToolbar.setTitle("智能识别中。。。");
                    OkHttpUtils
                            .post()
                            .url(requestUrl)
                            .addParams("name", "crab.jpg")
                            .addParams("base64", imgUrl)
                            .build()
                            .connTimeOut(50000000)
                            .readTimeOut(50000000)
                            .writeTimeOut(50000000)
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Kawaii_LoadingView.setVisibility(View.GONE);
                                    Kawaii_LoadingView.stopMoving();
                                    Toast.makeText(getContext(), "请求超时，请重试！", Toast.LENGTH_SHORT).show();
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
                                            //String base = jo.getString("imgUrl");
                                            d.setValidateResult(jo.optString("validateResult"));
                                            d.setNumber(jo.optString("number"));
                                            d.setCategory(jo.optString("category"));
                                            d.setLocation(jo.optString("location"));
                                            d.setProductPhone(jo.optString("productPhone"));
                                            d.setWeight(jo.optString("weight"));
                                            d.setLength(jo.optString("length"));
                                            d.setWidth(jo.optString("width"));
                                            d.setSqure(jo.optString("squre"));
                                            d.setDensity(jo.optString("density"));
                                            d.setTotalYield(jo.optString("totalYield"));
                                            d.setMuYield(jo.optString("muYield"));
                                            d.setPh(jo.optString("ph"));
                                            d.setOxygen(jo.optString("oxygen"));
                                            d.setNitrogen(jo.optString("nitrogen"));
                                            d.setSalt(jo.optString("salt"));
                                            d.setHydrogen(jo.optString("hydrogen"));
                                            String checkReport=jo.optString("checkReport");
                                            if(checkReport!=""){
                                                JSONObject jo1 = new JSONObject(jo.optString("checkReport"));
                                                d.setText_checkLocation(jo1.optString("checkLocation"));
                                                d.setText_checkName(jo1.optString("checkName"));
                                                d.setText_checkPhone(jo1.optString("checkPhone"));
                                                d.setText_protin(jo1.optString("protin"));
                                                d.setText_fat(jo1.optString("fat"));
                                                d.setText_grey(jo1.optString("grey"));
                                                d.setText_bacteria(jo1.optString("bacteria"));
                                                d.setText_checkLocation(jo1.optString("checkLocation"));
                                                d.setText_checkTime(jo1.optString("checkTime"));
                                            }



                                            JSONArray json1=jo.optJSONArray("sales");
                                            List<detail.Sale> saleList=new ArrayList<>();
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
                                           // Bitmap result= Img2Base64Util.stringToBitmap(base);

                                            if(d.getValidateResult().equals("0")){
                                                /*
                                                Intent resultintent=new Intent(getContext(), FailActivity.class);
                                                resultintent.putExtra("ValidateResult",d.getValidateResult());
                                                resultintent.putExtra("category","1");
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                result.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                byte[] bytes = baos.toByteArray();
                                                resultintent.putExtra("bitmap", bytes);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("data",d);
                                                resultintent.putExtras(bundle);
                                                startActivity(resultintent);*/
                                                /**/
                                                //跳转溯源详情页面
                                                Intent intent=new Intent(getContext(), ResnetDetailActivity.class);
                                                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                // result.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                // byte[] bytes = baos.toByteArray();
                                                // intent.putExtra("bitmap", bytes);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("data",d);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                            else{
                                                Intent resultintent=new Intent(getContext(), FailActivity.class);
                                                resultintent.putExtra("ValidateResult",d.getValidateResult());
                                                resultintent.putExtra("category","1");
                                                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                //result.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                                //byte[] bytes = baos.toByteArray();
                                                //resultintent.putExtra("bitmap", bytes);
                                                startActivity(resultintent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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
       // mToolbar.setRightButtonText("溯源");
        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",d);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mToolbar.hideRightButtonView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);

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
    public static void takeSuccessNew(){
        Uri path= getImageCropUri();
        imgUrl = "data:image/jpeg;base64,";
            /*
            getTakePhoto().onEnableCompress(new
                            CompressConfig.Builder().setMaxSize(342*342).create(),
                    true).onPicSelectCrop(imageUri);
                    */
        //imgUrl = imgUrl + Img2Base64Util.imageToBase64(result.getImage().getOriginalPath());
        imgUrl = imgUrl + Img2Base64Util.imageToBase64(path.getPath());
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
        bitmap = BitmapFactory.decodeFile(path.getPath());
        pic.setImageBitmap(bitmap);
    }
    @Override
    public void takeFail(TResult result, String msg) {
        //取得失败
        Toast.makeText(getContext(), "拍照失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        //取消
    }

    //获得照片的输出保存Uri
    private static Uri getImageCropUri() {
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
