package com.stx.openeyes.utils;

/**
 * Created by xhb on 2016/3/2.
 * 网络请求地址工具类
 */
public class HttpAdress {
       //每日精选
       public static final String DAILY="http://baobab.wandoujia.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
       //发现更多
       public static final String FIND_MORE="http://baobab.wandoujia.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
       //热门排行
       public static final String HOT_STRATEGY="http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
       //发现更多详情接口
       public static final String FIND_DETAIL="http://baobab.wandoujia.com/api/v3/videos?categoryName=%s&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";

}
