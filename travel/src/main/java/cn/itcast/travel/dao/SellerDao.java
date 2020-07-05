package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author krismile
 * @Create 2020-07-03 15:41
 */
public interface SellerDao {

    /**
     * 根据sid查询商家信息
     * @param sid
     * @return
     */
    public Seller findSeller(int sid);
}
