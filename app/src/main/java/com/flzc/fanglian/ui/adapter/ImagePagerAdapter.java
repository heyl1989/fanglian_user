package com.flzc.fanglian.ui.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flzc.fanglian.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;


/**
 * ImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context       context;
    private List<String> imageIdList;

    private int           size;
    private boolean       isInfiniteLoop;

    private ImageLoader imageLoader;
    
    public ImagePagerAdapter(Context context, List<String> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = imageIdList.size();
        System.out.println("///////////////////size//" + size);
//        imageLoader = ImageCacheManager.getInstance().getImageLoader();
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     * 
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new NetworkImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        
//        holder.imageView.setImageResource(imageIdList.get(getPosition(position)));
        holder.imageView.setScaleType(ScaleType.FIT_XY);
        holder.imageView.setDefaultImageResId(R.drawable.loading_750_380);
        holder.imageView.setErrorImageResId(R.drawable.loading_750_380);
        if (imageIdList != null && imageIdList.size() != 0) {
        	if (imageIdList.get(getPosition(position)) != null) {
        		holder.imageView.setImageUrl(imageIdList.get(getPosition(position)).toString(), imageLoader);
        	}
		}else {
			System.out.println("/////////////imageListSize////////////" + imageIdList.size());
			holder.imageView.setImageResource(R.drawable.loading_750_380);
		}
        return view;
    }

    private static class ViewHolder {

        NetworkImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
