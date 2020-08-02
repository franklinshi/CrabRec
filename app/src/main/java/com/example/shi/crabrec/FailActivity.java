package com.example.shi.crabrec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Uti.Img2Base64Util;
import widget.EnjoyshopToolBar;

public class FailActivity extends AppCompatActivity {
    EnjoyshopToolBar mToolbar;
    TextView textView;
    ImageView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
        mToolbar=findViewById(R.id.toolbar);
        textView=findViewById(R.id.txt_result);
        resultView=findViewById(R.id.result);
        Intent intent = getIntent();
        String ValidateResult= intent.getStringExtra("ValidateResult");
        String category= intent.getStringExtra("category");

        if(category.equals("0")){
            mToolbar.setTitle("比对结果");
            if(ValidateResult.equals("0"))
            {
                byte[] appIcons = intent.getByteArrayExtra("bitmap");
                Bitmap bitmap= BitmapFactory.decodeByteArray(appIcons,0,appIcons.length);
                resultView.setImageBitmap(bitmap);
                textView.setText("比对成功，点击溯源获取详细信息！");
                mToolbar.showRightButtonView();
                Bundle bundle = intent.getExtras();
                changeToolbar(bundle);
            }
            if(ValidateResult.equals("1"))
            {
                byte[] appIcons = intent.getByteArrayExtra("bitmap");
                Bitmap bitmap= BitmapFactory.decodeByteArray(appIcons,0,appIcons.length);
                resultView.setImageBitmap(bitmap);
                textView.setText("比对失败，不是同一只螃蟹！");
            }
            if(ValidateResult.equals("2"))
                textView.setText("该编号螃蟹数据库未录入！");
        }
        else{
            mToolbar.setTitle("识别结果");
            if(ValidateResult.equals("0"))
            {
                byte[] appIcons = intent.getByteArrayExtra("bitmap");
                Bitmap bitmap= BitmapFactory.decodeByteArray(appIcons,0,appIcons.length);
                resultView.setImageBitmap(bitmap);
                textView.setText("识别成功，点击溯源获取详细信息！");
                mToolbar.showRightButtonView();
                Bundle bundle = intent.getExtras();
                changeToolbar(bundle);
            }
            if(ValidateResult.equals("2"))
                textView.setText("该螃蟹数据库未录入！");
        }


    }


    private void changeToolbar(final Bundle bundle) {
        mToolbar.setRightButtonText("溯源");
        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FailActivity.this, DetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
