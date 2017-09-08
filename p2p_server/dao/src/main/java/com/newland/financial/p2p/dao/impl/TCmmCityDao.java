package com.newland.financial.p2p.dao.impl;

import com.newland.financial.p2p.dao.ITCmmCityDao;
import com.newland.financial.p2p.domain.entity.TCmmCity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * test.
 * */
@Repository
public class TCmmCityDao extends MybatisBaseDao<TCmmCity>
        implements ITCmmCityDao {
    /**
     *@param cityCd String
     * @return  TCmmCity
     * */
    public TCmmCity findById(final String cityCd) {
        return super.selectByPrimaryKey(cityCd);
    }
    /**
     *@param provNm String
     *@return List
     * */
    public List<TCmmCity> findByProvNm(final String provNm) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("provNm", provNm);
        return super.select("selectByProperties", params);
    }
}
