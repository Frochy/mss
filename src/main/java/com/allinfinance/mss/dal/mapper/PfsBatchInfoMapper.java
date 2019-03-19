package com.allinfinance.mss.dal.mapper;

import com.allinfinance.mss.dal.model.PfsBatchInfo;
import com.allinfinance.mss.dal.model.PfsBatchInfoKey;

public interface PfsBatchInfoMapper {
    int deleteByPrimaryKey(PfsBatchInfoKey key);

    int insert(PfsBatchInfo record);

    int insertSelective(PfsBatchInfo record);

    PfsBatchInfo selectByPrimaryKey(PfsBatchInfoKey key);

    int updateByPrimaryKeySelective(PfsBatchInfo record);

    int updateByPrimaryKey(PfsBatchInfo record);
}