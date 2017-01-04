package fussen.yu.news.utils.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import fussen.yu.news.User;
import fussen.yu.news.UserDao;

/**
 * Created by Fussen on 2016/12/30.
 */

public class UserMannager extends BaseDao<User> {
    public UserMannager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private User loadById(long id) {
        return daoSession.getUserDao().load(id);
    }

    /**
     * 获取某个对象的主键ID
     *
     * @param User
     * @return
     */
    private long getID(User User) {

        return daoSession.getUserDao().getKey(User);
    }

    /**
     * 通过名字获取Customer对象
     *
     * @return
     */
    private List<User> getUserByName(String key) {
        QueryBuilder queryBuilder = daoSession.getUserDao().queryBuilder();
        queryBuilder.where(UserDao.Properties.NickName.eq(key));
        int size = queryBuilder.list().size();
        if (size > 0) {
            return queryBuilder.list();
        } else {
            return null;
        }
    }

    /**
     * 通过名字获取user对象
     *
     * @return
     */
    private List<Long> getIdByName(String key) {
        List<User> Users = getUserByName(key);
        List<Long> ids = new ArrayList<Long>();
        int size = Users.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ids.add(Users.get(i).getId());
            }
            return ids;
        } else {
            return null;
        }
    }

    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    private void deleteById(long id) {

        daoSession.getUserDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {
        daoSession.getUserDao().deleteByKeyInTx(ids);
    }

    /*********************************** 
     * 在次添加一些User特有的数据库操作语句 
     * ************************************/
}
