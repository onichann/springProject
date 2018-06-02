package com.wt.generator;

import java.util.List;

public interface T_USERMapper {
    int deleteByPrimaryKey(String featid);

    int insert(T_USER record);

    int insertSelective(T_USER record);

    List<T_USER> selectByExample(T_USERExample example);

    T_USER selectByPrimaryKey(String featid);

    int updateByPrimaryKeySelective(T_USER record);

    int updateByPrimaryKey(T_USER record);
}