package me.nereo.multi_image_selector.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.R;
import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by meetu on 2016/6/1.
 */
public class PreviewPagerAdapter extends PagerAdapter{

    Context context;
    ArrayList<Image> photos;
    public PreviewPagerAdapter(Context context,ArrayList<Image> photos) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.photos = photos;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return photos.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View)object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        Image bean = photos.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.preview_item_layout, null);
        container.addView(view);
        ImageView imv = (ImageView) view.findViewById(R.id.photo_imv);
        File imageFile = new File(bean.path);
        // 显示图片
        Picasso.with(context)
                .load(imageFile)
                .into(imv);
        return view;
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

}
