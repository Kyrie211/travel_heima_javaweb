package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImp;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;


/**
 * @author krismile
 * @Create 2020-07-03 9:55
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImp();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // 封装pageBean
        PageBean<Route> pb = new PageBean<Route>();
        // 设置当前页码
        pb.setCurrentPage(currentPage);
        // 设置最大显示数
        pb.setPageSize(pageSize);
        int totalCount = routeDao.findTotalCount(cid,rname);
        // 设置总条数
        pb.setTotalCount(totalCount);
        // 设置总页数
        int totalPage = totalCount%pageSize==0? (totalCount/pageSize) : (totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        // 设置数据集合
        int start = (currentPage-1)*pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

//        System.out.println(pb);

        return pb;
    }

    @Override
    public Route findOne(int rid) {
        // 根据rid查询大部分详细信息
        Route route = routeDao.findOne(rid);
        // 查询路线图片信息
        List<RouteImg> routeImgs = routeImgDao.findRouteImgs(rid);
        route.setRouteImgList(routeImgs);
        // 查询商家信息
        int sid = route.getSid();
        Seller seller = sellerDao.findSeller(sid);
        route.setSeller(seller);
        // 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
