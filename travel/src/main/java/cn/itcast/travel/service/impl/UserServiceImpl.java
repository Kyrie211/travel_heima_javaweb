package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author krismile
 * @Create 2020-07-01 21:35
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        // 先查询数据库中是否有同名用户
        User u = userDao.findByUsername(user.getUsername());

        // 有， 直接返回false 注册失败
        if(u!=null){
            return false;
        }

        // 设置激活status状态，及code(唯一性)
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        // 没有， 调用userDao保存用户数据，实现注册功能s
        userDao.save(user);

        // 发送激活邮件正文
        String context = "<a href='http://localhost/travel/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
        // 工具类发送邮件
        MailUtils.sendMail(user.getEmail(), context, "激活邮件");

        return true;
    }

    @Override
    public boolean active(String code) {
        // 先查询是否含有此人
        User user = userDao.findByCode(code);
        // 没有， 返回false
        if(user==null){
            return false;
        }
        // 含有，更新status为Y
        userDao.updateStatus(user);
        return true;
    }

    @Override
    public User login(User user) {
        // 判断用户是否存在
        User u = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
       return u;
    }
}
