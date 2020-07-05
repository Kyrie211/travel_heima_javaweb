package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author krismile
 * @Create 2020-07-03 9:58
 */
public interface RouteDao {
    /**
     * 查询总记录数
     */
    public int findTotalCount(int cid, String rname);

    /**
     * 查询记录数据集合
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 通过rid查询路线详情信息
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
