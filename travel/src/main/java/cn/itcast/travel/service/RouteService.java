package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author krismile
 * @Create 2020-07-03 9:53
 */
public interface RouteService {

    /**
     * 根据分类编号、开始下标以及最大响应页码 分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据路线rid 查询路线详情信息
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
