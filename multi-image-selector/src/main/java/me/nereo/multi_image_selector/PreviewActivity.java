package me.nereo.multi_image_selector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.adapter.PreviewPagerAdapter;
import me.nereo.multi_image_selector.bean.Image;
import me.nereo.multi_image_selector.picpreview.ImageDetailFragment;

/**
 * Created by meetu on 2016/6/1.
 */
public class PreviewActivity extends FragmentActivity{
    private ImageView selectImv;
    private ViewPager photoPager;
    private ImagePagerAdapter mAdapter;
    private Button completeBtn;
    private RelativeLayout backRl;
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
        completeBtn = (Button) findViewById(R.id.complete_btn);
        backRl = (RelativeLayout) findViewById(R.id.back_rl);
        getIntentInfo();
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("resultList",resultImgs);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        selectImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultImgs.contains(allImgs.get(currentIndex).path)){
                    resultImgs.remove(allImgs.get(currentIndex).path);
                    changeSeiectState();
                    if(resultImgs.size()>0){
                        completeBtn.setText("完成("+resultImgs.size()+"/9)");
                    }else{
                        completeBtn.setText("完成");
                    }
                }else{
                    if(resultImgs.size() < 9){
                        resultImgs.add(allImgs.get(currentIndex).path);
                        changeSeiectState();
                        if(resultImgs.size()>0){
                            completeBtn.setText("完成("+resultImgs.size()+"/9)");
                        }else{
                            completeBtn.setText("完成");
                        }
                    }else{
                        Toast.makeText(PreviewActivity.this,R.string.msg_amount_limit,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentInfo() {
        allImgs = (ArrayList<Image>) getIntent().getSerializableExtra("allImgs");
        resultImgs = getIntent().getStringArrayListExtra("selectImgs");
        currentIndex = getIntent().getIntExtra("currentIndex",0);
        if(resultImgs.size()>0){
            completeBtn.setText("完成("+resultImgs.size()+"/9)");
        }else{
            completeBtn.setText("完成");
        }
        setData();
    }

    private void setData(){
        photoPager.setOffscreenPageLimit(3);
       // adapter = new PreviewPagerAdapter(getApplicationContext(), allImgs);
        ArrayList<String> list = new ArrayList<>();
        if(allImgs.size()==0){
            return;
        }
        for(int i=0;i<allImgs.size();i++){
            list.add(allImgs.get(i).path);
        }
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(),list);
        photoPager.setAdapter(mAdapter);
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
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public List<String> fileList;
        public ArrayList<Bitmap> mImgList;

        public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
            mImgList = new ArrayList<Bitmap>();
            for (int i = 0; i < getCount(); i++) {
                mImgList.add(null);
            }
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            ImageDetailFragment frag = ImageDetailFragment.newInstance(url);
            frag.setRetainInstance(true);
            frag.mImgList = mImgList;
            frag.mIndex = position;
            return frag;
        }

    }
}
