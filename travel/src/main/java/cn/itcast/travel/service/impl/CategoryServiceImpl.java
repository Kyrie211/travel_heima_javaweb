package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author krismile
 * @Create 2020-07-02 20:20
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        // 先取redis缓存找，没有则去数据库中查找
        // jedis是Java访问Redis的一个"接口"，相当于jdbc访问数据库
        Jedis jedis = JedisUtil.getJedis();
        //使用sortedset排序查询
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> ret = null;
        if(categorys==null || categorys.size()==0){
            System.out.println("从数据库查");
            // 不存在，访问数据库查询
            ret = categoryDao.findAll();
            // 加入到redis 缓存 提高之后效率

            for (Category c : ret) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
        }else {
            System.out.println("从redis查");
            // redis中存在
            ret = new ArrayList<>();
            for (Tuple category : categorys) {
                Category c = new Category();
                c.setCid((int) category.getScore());
                c.setCname(category.getElement());
                ret.add(c); // 加入到ret中
            }
        }
        return ret;
    }
}
