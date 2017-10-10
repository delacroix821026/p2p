package com.newland.financial.p2p.dao;

import com.newland.financial.p2p.domain.entity.TCmmCity;

import java.util.List;
/**
 * test.
 * */
public interface ITCmmCityDao {
    /**
     * @param cityCd String
     * @return TCmmCity
     * */
    TCmmCity findById(String cityCd);
    /**
     * @param provNm String
     * @return List
     * */
    List<TCmmCity> findByProvNm(String provNm);

}
