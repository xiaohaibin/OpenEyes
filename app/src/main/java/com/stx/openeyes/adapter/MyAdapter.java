package com.stx.openeyes.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.openeyes.R;
import com.stx.openeyes.model.HomePicEntity;

import java.util.List;

/**
 * Created by ASUS on 2016/2/29.
 */
public class MyAdapter extends BaseAdapter {
    private static final String TAG = "MyAdapter";
    public static final int VIDEO = 1;
    public static final int TEXT = 2;
    private Context mContext;
    List<HomePicEntity.IssueListEntity.ItemListEntity> mItemList;
    private View rlText;


    public MyAdapter(Context mContext, List<HomePicEntity.IssueListEntity.ItemListEntity> itemList) {
        this.mContext = mContext;
        this.mItemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {

        HomePicEntity.IssueListEntity.ItemListEntity itemListEntity = mItemList.get(position);
        if ("video".equals(itemListEntity.getType())) {
            return VIDEO;
        }


        return TEXT;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private ViewHolder mHolder;
    private ViewHolder2 mHolder2;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HomePicEntity.IssueListEntity.ItemListEntity itemListEntity = mItemList.get(position);

        int type = getItemViewType(position);

        String feed = "1";
        String title = "1";
        String category = "1";
        int duration = 0;
        String text = "1";


        mHolder = new ViewHolder();
        mHolder2 = new ViewHolder2();


        switch (type) {
            case VIDEO:
                //得到不同类型所需要的数据
                feed = itemListEntity.getData().getCover().getFeed();
                title = itemListEntity.getData().getTitle();
                category = itemListEntity.getData().getCategory();
                category = "#" + category + "  /  ";

                duration = itemListEntity.getData().getDuration();

                int last = duration % 60;
                String stringLast;
                if (last <= 9) {
                    stringLast = "0" + last;
                } else {
                    stringLast = last + "";
                }

                String durationString;
                int minit = duration / 60;
                if (minit < 10) {
                    durationString = "0" + minit;

                } else {
                    durationString = "" + minit;

                }
                String stringTime = durationString + "' " + stringLast + '"';

                //设置布局
                View view = LayoutInflater.from(mContext).inflate(R.layout.list_home_vedio_item, parent, false);
                convertView = view;
                if (convertView == null) {
                    mHolder.imageView = (ImageView) convertView.findViewById(R.id.iv);
                    mHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                    mHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);

                    convertView.setTag(mHolder);

                } else {
                    if (convertView.getTag() instanceof ViewHolder) {

                        mHolder = (ViewHolder) convertView.getTag();

                    } else {
                        convertView = view;
                        mHolder.imageView = (ImageView) convertView.findViewById(R.id.iv);
                        mHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                        mHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);

                        convertView.setTag(mHolder);

                    }
                }


                //set data

                Uri uri = Uri.parse(feed);
                SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.iv);
                draweeView.setImageURI(uri);


                mHolder.tvTitle.setText(title);
                mHolder.tvTime.setText(category + stringTime);


                return convertView;

            case TEXT:

                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_home_text_item, parent, false);
                TextView textView = (TextView) convertView.findViewById(R.id.tv_home_text);

                //set data
                text = itemListEntity.getData().getText();
                textView.setText(text);

                String image = mItemList.get(position).getData().getImage();

                if (!TextUtils.isEmpty(image)) {
                    textView.setTextSize(20);
                    textView.setText("-Weekend  special-");
                }


                return convertView;
            default:
                return null;
        }

    }

    static class ViewHolder {
        ImageView imageView;
        TextView tvTitle;
        TextView tvTime;

    }

    static class ViewHolder2 {
        TextView tvTime;

    }
}
