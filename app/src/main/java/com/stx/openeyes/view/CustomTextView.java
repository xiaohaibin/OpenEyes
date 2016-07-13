package com.stx.openeyes.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xhb on 2016/2/29.
 * 自定义TextView
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 设置字体
     * @param context
     */
    private void init(Context context){
        AssetManager assets = context.getAssets();
        Typeface font = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf");
        setTypeface(font);
    }
}
