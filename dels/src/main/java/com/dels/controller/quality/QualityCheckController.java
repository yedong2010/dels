package com.dels.controller.quality;

import com.dels.model.monitor.QualityCheckModel;
import com.dels.service.monitor.IQualityCheckService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/13.
 *  数据质量检测：
 *      数据质量核查反馈
 *      数据质量申诉
 *      数据质量修正
 */
@RestController
@RequestMapping("/qualityCheck")
public class QualityCheckController {

    @Autowired
    IQualityCheckService service;

    /**
     * @description: 反馈列表
     * @author: l13595
     * @param entity: 查询参数
     * @return: 返回结果列表
     * @time: 2017/3/14 19:54
     */
    @RequestMapping(value = "feedList")
    public List<QualityCheckModel> feedList(QualityCheckModel entity){
        List<QualityCheckModel> list = new ArrayList<>();
        list = service.findQList(entity);
        return list;
    }

    /**
     * @description: 申诉列表
     * @author: l13595
     * @param entity: 查询参数
     * @return: 结果列表
     * @time: 2017/3/14 19:55
     */
    @RequestMapping(value = "repreList")
    public List<QualityCheckModel> repreList(QualityCheckModel entity){
        if(null==entity.getState()){
            entity.setState(QualityCheckModel.STATE_FEED);
        }
        return service.findRepreList(entity);
    }

    /**
     * @description: 修正信息列表
     * @author: l13595
     * @param entity: 查询参数
     * @return: 返回结果列表
     * @time: 2017/3/14 19:56
     */
    @RequestMapping(value = "correctList")
    public List<QualityCheckModel> correctList(QualityCheckModel entity){
        if(null==entity.getState()){
            entity.setState(QualityCheckModel.STATE_WAIT);
        }
        return service.findCorrectList(entity);
    }

    /**
     * @description: 录入反馈信息
     * @author: l13595
     * @param entity: title-标题、question-问题
     * @return: 返回增加结果 。 增加成功，返回ok；增加失败，返回失败提醒
     * @time: 2017/3/14 19:58
     */
    @RequestMapping(value = "addFeedInfo")
    public Map<String,String> addFeedInfo(QualityCheckModel entity){
        entity.setId(service.findSeq());
        String resultMsg = service.addQualityCheck(entity);
        Map<String,String> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 更新反馈问题
     * @author: l13595
     * @param entity: 录入内容。state-状态，resultMsg-处理内容
     * @return: 返回更新结果。更新成功，返回ok，更新失败，返回失败提醒
     * @time: 2017/3/14 20:01
     */
    @RequestMapping(value = "updateQualityCheck")
    public  Map<String,String> updateQualityCheck(QualityCheckModel entity){
        String resultMsg = service.updateQualityCheck(entity);
        Map<String,String> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description:唯一性校验
     * @author: l13595
     * @time: 2017/3/14 20:03
     */
    @RequestMapping(value = "checkTitle")
    public  Map<String,Boolean> checkTitle(String addFeedBackName){
        QualityCheckModel param = new QualityCheckModel();
        param.setTitle(addFeedBackName);
        List<QualityCheckModel> list = service.findQList(param);
        Map<String,Boolean> rep = new HashedMap();
        if(null != list && list.size()>0){
            rep.put("resultMsg",false);
        }else{
            rep.put("resultMsg",true);
        }
        return rep;
    }


}
