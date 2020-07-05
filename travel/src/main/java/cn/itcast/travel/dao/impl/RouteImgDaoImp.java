package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author krismile
 * @Create 2020-07-03 15:40
 */
public class RouteImgDaoImp implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findRouteImgs(int rid) {
        String sql = "select * from tab_route_img where rid = ? ";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
