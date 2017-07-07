package com.dels.service.monitor;

import com.dels.model.monitor.QualityCheckModel;

import java.util.List;

/**
 * Created by l13595 on 2017/3/13.
 *    数据质量检测：
 *      数据质量核查反馈
 *      数据质量申诉
 *      数据质量修正
 */
public interface IQualityCheckService {

    public abstract List<QualityCheckModel> findQList(QualityCheckModel entity);

    public abstract String updateQualityCheck(QualityCheckModel entity);

    public abstract String findSeq();

    public abstract String addQualityCheck(QualityCheckModel entity);

    public abstract List<QualityCheckModel> findRepreList(QualityCheckModel entity);
    public abstract List<QualityCheckModel> findCorrectList(QualityCheckModel entity);

}
