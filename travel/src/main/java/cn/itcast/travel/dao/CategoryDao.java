package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author krismile
 * @Create 2020-07-02 20:16
 */
public interface CategoryDao {
    /**
     * 查询分类栏数据
     * @return
     */
    public List<Category> findAll();
}
