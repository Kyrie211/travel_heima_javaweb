package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author krismile
 * @Create 2020-07-03 15:40
 */
public interface RouteImgDao {

    /**
     * 根据rid查询tab_route_img表的路线详情图片信息
     * @param rid
     * @return
     */
    public List<RouteImg> findRouteImgs(int rid);
}
