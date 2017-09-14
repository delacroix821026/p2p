package com.newland.financial.p2p.service.impl;

import com.newland.financial.p2p.dao.IInterestDao;
import com.newland.financial.p2p.dao.IProductDao;
import com.newland.financial.p2p.domain.entity.AbstractProduct;
import com.newland.financial.p2p.domain.entity.IProduct;
import com.newland.financial.p2p.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 对产品进行操作的service类.
 * @author cendaijuan
 * */
@Service
public class ProductServiceImpl implements IProductService {
    /**日志对象.*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**Dao层对象.*/
    @Autowired
    private IProductDao productDao;
    /**Dao层对象.*/
    @Autowired
    private IInterestDao interestDao;
    /**
     * 查询所有产品.
     * @return List返回所有产品
     */
    public List getProductList() {
        return productDao.findAll();
    }
    /**
     * 获取指定产品的信息.
     *@param id String产品编号
     *@return IProduct返回指定的产品
     * */
    public IProduct getProduct(final String id) {
        AbstractProduct product = productDao.findById(id);
        logger.info("service--product-------:" + product);
        product.setInterestList(interestDao.findByProId((id)));

        return product;
    }

}
