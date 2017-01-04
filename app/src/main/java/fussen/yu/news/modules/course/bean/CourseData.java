package fussen.yu.news.modules.course.bean;

import java.util.ArrayList;

/**
 * Created by Fussen on 2016/12/28.
 */

public class CourseData {

    /**
     * status : 200
     * showType : 5
     * msg : 官方模板分类列表
     * data : {"dataList":[{"categoryId":"45","categoryName":"减脂"},{"categoryId":"46","categoryName":"增肌"},{"categoryId":"47","categoryName":"塑形"},{"categoryId":"77","categoryName":"保健"},{"categoryId":"78","categoryName":"拉伸"}]}
     */


    public ArrayList<DataListUser> dataList;

    public static class DataListUser {
        /**
         * categoryId : 45
         * categoryName : 减脂
         */

        public String categoryId;
        public String imageUrl;
        public String categoryName;
    }
}
