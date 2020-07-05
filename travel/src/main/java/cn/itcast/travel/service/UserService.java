package cn.itcast.travel.service;

import cn.itcast.travel.domain.User; /**
 * @author krismile
 * @Create 2020-07-01 21:33
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活 激活码为code的用户
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);
}
