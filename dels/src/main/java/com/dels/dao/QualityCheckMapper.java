package com.dels.dao;


import com.dels.model.monitor.QualityCheckModel;

import java.util.List;

/**
 * Created by l13595 on 2017/3/13.
 *  数据质量检测：
 *      数据质量核查反馈
 *      数据质量申诉
 *      数据质量修正
 */
public interface QualityCheckMapper {

    public String findRoleId(String userName);
    public List<QualityCheckModel> findQKList(QualityCheckModel entity);
    public int findSeq();
    public void insertQuality(QualityCheckModel entity);
    public void updateQuality(QualityCheckModel entity);
    public void deleteQuality(QualityCheckModel entity);

}
