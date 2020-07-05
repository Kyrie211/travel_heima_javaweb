package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author krismile
 * @Create 2020-07-01 21:35
 */
public interface UserDao {
    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 保存用户信息
     * @param user
     */
    void save(User user);

    /**
     * 查询 激活码为code的用户信息
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 激活状态更新为Y
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
