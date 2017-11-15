package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.IOrderDao;
import com.newland.financial.p2p.entity.OrderInfo;
import org.springframework.stereotype.Repository;

/**
 *订单处理DaoImpl.
 * @author Gregory
 */
@Repository
public class OrderDaoImpl extends MybatisBaseDao<OrderInfo> implements IOrderDao{

}
