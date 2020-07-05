package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author krismile
 * @Create 2020-07-03 15:41
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findSeller(int sid) {
        String sql = "select * from tab_seller where sid = ? ";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
