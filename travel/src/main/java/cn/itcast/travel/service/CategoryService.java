package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author krismile
 * @Create 2020-07-02 20:20
 */
public interface CategoryService {
    /**
     * 查询分类栏数据
     * @return
     */
    public List<Category> findAll();
}
