package fussen.yu.news.subject.bean;

import java.util.List;

/**
 * Created by Fussen on 2016/12/21.
 */

public class WeekEvent{

    /**
     * course : [{"courseId":"1594","studentId":"396","nickName":"给你欢乐","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1467005337.jpg","mobile":"18219200511","templateId":"0","templateName":"","courseTime":"2016-12-06","startTime":"2016-12-06 12:00:00","endTime":"2016-12-06 13:00:00","isAssign":"0","isComment":"0","state":"0","uploadPath":""},{"courseId":"1598","studentId":"396","nickName":"给你欢乐","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1467005337.jpg","mobile":"18219200511","templateId":"512","templateName":"葵花宝典3","courseTime":"2016-12-06","startTime":"2016-12-06 14:00:00","endTime":"2016-12-06 15:00:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1599","studentId":"11","nickName":"Jame","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1464924323.png","mobile":"13146171161","templateId":"570","templateName":"经常你v猜","courseTime":"2016-12-06","startTime":"2016-12-06 17:00:00","endTime":"2016-12-06 18:00:00","isAssign":"1","isComment":"1","state":"1","uploadPath":"http://dev.bodyplus.cc:8088//upload/userId11/coach/20161206/201612062710.zip"},{"courseId":"1604","studentId":"11","nickName":"Jame","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1464924323.png","mobile":"13146171161","templateId":"512","templateName":"葵花宝典3","courseTime":"2016-12-06","startTime":"2016-12-06 20:00:00","endTime":"2016-12-06 21:00:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1610","studentId":"11","nickName":"Jame","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1464924323.png","mobile":"13146171161","templateId":"113","templateName":"DEV 跑步拉伸 运营课程","courseTime":"2016-12-07","startTime":"2016-12-07 18:30:00","endTime":"2016-12-07 19:30:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1602","studentId":"11","nickName":"Jame","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1464924323.png","mobile":"13146171161","templateId":"553","templateName":"室内滑雪训练","courseTime":"2016-12-08","startTime":"2016-12-08 13:00:00","endTime":"2016-12-08 14:00:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1597","studentId":"","nickName":"发你觉得","avatarUrl":"http://dev.bodyplus.cc:8088//images/avatar.png","mobile":"","templateId":"553","templateName":"室内滑雪训练","courseTime":"2016-12-09","startTime":"2016-12-09 19:00:00","endTime":"2016-12-09 20:00:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1601","studentId":"11","nickName":"Jame","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1464924323.png","mobile":"13146171161","templateId":"573","templateName":"没出内存车","courseTime":"2016-12-10","startTime":"2016-12-10 17:00:00","endTime":"2016-12-10 18:00:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1603","studentId":"11","nickName":"Jame","avatarUrl":"http://dev.bodyplus.cc:8088/images/avatar/avatar_1464924323.png","mobile":"13146171161","templateId":"553","templateName":"室内滑雪训练","courseTime":"2016-12-11","startTime":"2016-12-11 10:00:00","endTime":"2016-12-11 11:00:00","isAssign":"1","isComment":"0","state":"0","uploadPath":""},{"courseId":"1618","studentId":"474","nickName":"哦哟哟哟","avatarUrl":"http://dev.bodyplus.cc:8088//images/avatar/avatar_1477906243.png","mobile":"13823736053","templateId":"579","templateName":"葵花宝典第二部","courseTime":"2016-12-13","startTime":"2016-12-13 18:30:00","endTime":"2016-12-13 19:30:00","isAssign":"1","isComment":"1","state":"1","uploadPath":"http://dev.bodyplus.cc:8088//upload/userId474/coach/20161213/201612133031.zip"}]
     * groupCourse : [{"courseId":"1596","courseDate":"2016-12-09","startTime":"2016-12-09 17:00:00","endTime":"2016-12-09 18:00:00","is_start":"0","status":"0","gcId":"118910","courseName":"不错经常","team_id":"118910"}]
     * noCourse : []
     * workDay : [0]
     * workTime : {"startime":"08:00","endTime":"22:00"}
     */

    public WorkTimeUser workTime;
    public List<CourseUser> course;
    public List<GroupCourseUser> groupCourse;
    public List<String> noCourse;
    public List<String> workDay;

    public static class WorkTimeUser {
        /**
         * startime : 08:00
         * endTime : 22:00
         */

        public String startime;
        public String endTime;
    }

    public static class CourseUser {
        /**
         * courseId : 1594
         * studentId : 396
         * nickName : 给你欢乐
         * avatarUrl : http://dev.bodyplus.cc:8088/images/avatar/avatar_1467005337.jpg
         * mobile : 18219200511
         * templateId : 0
         * templateName :
         * courseTime : 2016-12-06
         * startTime : 2016-12-06 12:00:00
         * endTime : 2016-12-06 13:00:00
         * isAssign : 0
         * isComment : 0
         * state : 0
         * uploadPath :
         */

        public String courseId;
        public String studentId;
        public String nickName;
        public String avatarUrl;
        public String mobile;
        public String templateId;
        public String templateName;
        public String courseTime;
        public String startTime;
        public String endTime;
        public String isAssign;
        public String isComment;
        public String state;
        public String uploadPath;
    }

    public static class GroupCourseUser {
        /**
         * courseId : 1596
         * courseDate : 2016-12-09
         * startTime : 2016-12-09 17:00:00
         * endTime : 2016-12-09 18:00:00
         * is_start : 0
         * status : 0
         * gcId : 118910
         * courseName : 不错经常
         * team_id : 118910
         */

        public String courseId;
        public String courseDate;
        public String startTime;
        public String endTime;
        public String is_start;
        public String status;
        public String gcId;
        public String courseName;
        public String team_id;
    }
}
