package com.example.shi.crabrec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import adapter.SaleAdapter;
import model.detail;

public class ResnetDetailActivity extends AppCompatActivity {
    TextView text_number;
    TextView text_category;
    TextView text_productPhone;
    TextView text_weight;
    TextView text_length;
    TextView text_width;
    TextView text_location;
    TextView text_squre;
    TextView text_density;
    TextView text_totalYield;
    TextView text_muYield;
    TextView text_hydrogen;
    TextView text_oxygen;
    TextView text_nitrogen;
    TextView text_salt;
    TextView text_ph;
    RecyclerView recyclerview_sale;
    TextView text_checkLocation;

    TextView text_checkName;
    TextView text_checkPhone;
    TextView text_protin;
    TextView text_fat;
    TextView text_grey;
    TextView text_bacteria;
    TextView text_checkTime;
    LinearLayout layout_checkReport;
    private SaleAdapter saleAdapter;

    TextView text_checkHint;
    TextView text_locationHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resnetdetail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        detail d = (detail) bundle.getSerializable("data");
        text_number = findViewById(R.id.text_number);
        text_category = findViewById(R.id.text_category);
        text_productPhone = findViewById(R.id.text_productPhone);
        text_weight = findViewById(R.id.text_weight);
        text_length = findViewById(R.id.text_length);
        text_width = findViewById(R.id.text_width);
        text_location = findViewById(R.id.text_location);
        text_squre = findViewById(R.id.text_squre);
        text_density = findViewById(R.id.text_density);
        text_totalYield = findViewById(R.id.text_totalYield);
        text_muYield = findViewById(R.id.text_muYield);
        text_hydrogen = findViewById(R.id.text_hydrogen);
        text_oxygen = findViewById(R.id.text_oxygen);
        text_nitrogen = findViewById(R.id.text_nitrogen);
        text_salt = findViewById(R.id.text_salt);
        text_ph = findViewById(R.id.text_ph);
        recyclerview_sale = findViewById(R.id.recyclerview_sale);

        text_checkLocation= findViewById(R.id.text_checkLocation);
        text_checkName= findViewById(R.id.text_checkName);
        text_checkPhone= findViewById(R.id.text_checkPhone);
        text_protin= findViewById(R.id.text_protin);
        text_fat= findViewById(R.id.text_fat);
        text_grey= findViewById(R.id.text_grey);
        text_bacteria= findViewById(R.id.text_bacteria);
        text_checkTime= findViewById(R.id.text_checkTime);
        layout_checkReport= findViewById(R.id.layout_checkReport);
        text_checkHint= findViewById(R.id.text_checkHint);
        text_locationHint=findViewById(R.id.text_locationHint);
        text_checkHint.setText("粗蛋白 >=15.4毫克/千克 粗脂肪 >=7.2毫克/千克 灰分 <=1.8毫克/千克 副溶血性弧菌 <25毫克/千克");
        text_locationHint.setText( "溶解氧 4～6毫克/升 氨氮 <0.2毫克/升 亚硝酸盐 <0.01毫克/升 硫化氢  <0.2毫克/升");
        //显示图片
        /*
        resultView=findViewById(R.id.result);
        byte[] appIcons = intent.getByteArrayExtra("bitmap");
        Bitmap bitmap= BitmapFactory.decodeByteArray(appIcons,0,appIcons.length);
        resultView.setImageBitmap(bitmap);
        */
        if(d.getText_checkName()!=""){
            layout_checkReport.setVisibility(View.VISIBLE);
            text_checkLocation.setText(d.getText_checkLocation());
            text_checkName.setText(d.getText_checkName());
            text_checkPhone.setText(d.getText_checkPhone());
            text_protin.setText(d.getText_protin());
            text_fat.setText(d.getText_fat());
            text_grey.setText(d.getText_grey());
            text_bacteria.setText(d.getText_bacteria());
            text_checkTime.setText(d.getText_checkTime());
        }
        else
            layout_checkReport.setVisibility(View.INVISIBLE);

        if(d.getValidateResult().equals("0")){
        text_number.setText(d.getNumber());
        text_category.setText(d.getCategory());
        text_productPhone.setText(d.getProductPhone());
        text_weight.setText(d.getWeight());
        text_length.setText(d.getLength());
        text_width.setText(d.getWidth());
        text_location.setText(d.getLocation());
        text_squre.setText(d.getSqure());
        text_density.setText(d.getDensity());
        text_totalYield.setText(d.getTotalYield());
        text_muYield.setText(d.getMuYield());
        text_hydrogen.setText(d.getHydrogen());
        text_oxygen.setText(d.getOxygen());
        text_nitrogen.setText(d.getNitrogen());
        text_salt.setText(d.getSalt());
        text_ph.setText(d.getPh());
        saleAdapter = new SaleAdapter(d.getSales());
        recyclerview_sale.setAdapter(saleAdapter);
        recyclerview_sale.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview_sale.setItemAnimator(new DefaultItemAnimator());
        }
        else
        {
            Intent resultintent=new Intent(this, FailActivity.class);
            resultintent.putExtra("ValidateResult",d.getValidateResult());
            resultintent.putExtra("category","1");
            startActivity(resultintent);
            this.finish();
        }
    }
}
