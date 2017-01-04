package fussen.yu.news.utils.db;

import de.greenrobot.dao.query.QueryBuilder;
import fussen.yu.news.App;
import fussen.yu.news.User;
import fussen.yu.news.UserDao;

/**
 * Created by Fussen on 2016/12/30.
 */

public class DbUtils {


    public static boolean isExit(String id) {
        QueryBuilder<User> query = App.getUserDao().queryBuilder();

        query.where(UserDao.Properties.Id.eq(id));

        long count = query.buildCount().count();

        return count > 0 ? true : false;
    }

    public static void insertUser(User user) {
        UserDao userDao = App.getUserDao();
        userDao.insertOrReplace(user);
    }


    public static void updateUser(User user) {
        UserDao userDao = App.getUserDao();
        userDao.refresh(user);
    }

    public static void deleteUser(User user) {
        UserDao userDao = App.getUserDao();
        userDao.delete(user);
    }

    public static User getUserById(String id) {

        if (!DbUtils.isExit(id)) {
            return null;
        }
        QueryBuilder<User> query = App.getUserDao().queryBuilder();

        query.where(UserDao.Properties.Id.eq(id));
        return query.unique();
    }

    public static void deleteUserById(String userId) {

    }
}
