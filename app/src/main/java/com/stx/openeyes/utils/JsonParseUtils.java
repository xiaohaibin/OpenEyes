package com.stx.openeyes.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.openeyes.model.FindMoreEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhb on 2016/3/2.
 * json解析工具类
 */
public class JsonParseUtils {
    public static List<FindMoreEntity> parseFromJson(String jsonData){
        List<FindMoreEntity> entities=new ArrayList<>();
        Type listType = new TypeToken<LinkedList<FindMoreEntity>>(){}.getType();
        Gson gson = new Gson();
        LinkedList<FindMoreEntity> findMoreEntities = gson.fromJson(jsonData, listType);
        for (Iterator iterator = findMoreEntities.iterator(); iterator.hasNext();) {
            FindMoreEntity findMoreEntity = (FindMoreEntity) iterator.next();
            entities.add(findMoreEntity);
        }
        return entities;
    }
}
