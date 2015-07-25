package com.hhf.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhf.model.user.Geo;

public interface GeoMapper {
    int deleteByPrimaryKey(Integer geoId);

    int insert(Geo record);

    int insertSelective(Geo record);

    Geo selectByPrimaryKey(Integer geoId);

    int updateByPrimaryKeySelective(Geo record);

    int updateByPrimaryKey(Geo record);

	List<Geo> getGeoByLevel(@Param("geoLevel") String geoLevel);

	List<Geo> getGeoByFid(@Param("fid") long fid);
}