package com.wt.generator;

import java.util.List;

public interface T_USERMapper {
    int insert(T_USER record);

    int insertSelective(T_USER record);

    List<T_USER> selectByExample(T_USERExample example);
}