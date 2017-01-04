package fussen.yu.news.utils.db;

import fussen.yu.news.App;
import fussen.yu.news.User;
import fussen.yu.news.UserDao;
import fussen.yu.news.modules.login.bean.UserInfo;

/**
 * Created by Fussen on 2016/12/29.
 */

public class UserTableManager {

    public static void initUser(UserInfo info){

        UserDao userDao = App.getUserDao();

        User user = new User();

        user.setNickName(info.nickName);

        user.setAvatarUrl(info.avatarUrl);

        user.setGender(info.gender);

        user.setMobile(info.mobile);

        userDao.insert(user);
    }
}
