package me.nereo.multi_image_selector;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import me.nereo.multi_image_selector.adapter.PreviewPagerAdapter;
import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by meetu on 2016/6/1.
 */
public class PreviewActivity extends Activity{
    private ImageView selectImv;
    private ViewPager photoPager;
    private PreviewPagerAdapter adapter;
    private ArrayList<Image> allImgs = new ArrayList<>();
    private ArrayList<String> resultImgs = new ArrayList<>();
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.preview_layout);
        selectImv = (ImageView) findViewById(R.id.select_imv);
        photoPager = (ViewPager) findViewById(R.id.photo_scan_pager);
        getIntentInfo();
        selectImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultImgs.contains(allImgs.get(currentIndex).path)){
                    resultImgs.remove(allImgs.get(currentIndex).path);
                    changeSeiectState();
                }else{
                    if(resultImgs.size() < 9){
                        resultImgs.add(allImgs.get(currentIndex).path);
                        changeSeiectState();
                    }else{
                        Toast.makeText(PreviewActivity.this,R.string.msg_amount_limit,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getIntentInfo() {
        allImgs = (ArrayList<Image>) getIntent().getSerializableExtra("allImgs");
        resultImgs = getIntent().getStringArrayListExtra("selectImgs");
        currentIndex = getIntent().getIntExtra("currentIndex",0);
        setData();
    }

    private void setData(){
        photoPager.setOffscreenPageLimit(3);
        adapter = new PreviewPagerAdapter(getApplicationContext(), allImgs);
        photoPager.setAdapter(adapter);
        photoPager.setCurrentItem(currentIndex);
        changeSeiectState();
        photoPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                currentIndex = arg0;
                changeSeiectState();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    private void changeSeiectState(){
        if(resultImgs.contains(allImgs.get(currentIndex).path)){
            selectImv.setImageResource(R.drawable.btn_selected);
        }else{
            selectImv.setImageResource(R.drawable.btn_unselected);
        }
    }
}