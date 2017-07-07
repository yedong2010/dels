package com.dels.controller.chat;

import java.util.List;

import com.dels.model.chat.Reply;
import com.dels.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dels.model.chat.Note;

/**
 * 
 * @description 聊天室贴吧管理控制器
 * @author z11595
 * @time 2017年1月13日 上午10:55:26
 */
@RestController
@RequestMapping("/chat")
public class NoteController {
    /**
     * 业务层操作对象
     */
    @Autowired
    INoteService service;

    /**
     * 
     * @description 管理员创建发帖
     * @author z11595
     * @param t
     * @time 2017年1月13日 上午11:06:54
     */
    @RequestMapping(value = "cnote", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public void createNote(@RequestBody Note t) {
        service.insert(t);
    }

    /**
     * 
     * @description 创建回复信息
     * @author z11595
     * @param t
     * @time 2017年2月7日 上午10:59:05
     */
    @RequestMapping(value = "creply", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public void createReply(@RequestBody Reply t) {
        service.addReply(t);
    }

    /**
     * 
     * @description 主题管理——普通用户只能看到自己的
     * @author z11595
     * @return 内容编辑
     * @time 2017年1月22日 上午11:48:08
     */
    @RequestMapping(value = "self", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public List<Note> self() {
        return service.selectByAuthor();
    }

    /**
     *
     * @description 主题管理——系统管理员可以看到所有的
     * @author z11595
     * @return 内容编辑
     * @time 2017年1月22日 上午11:48:08
     */
    @RequestMapping(value = "list", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public List<Note> list() {
        return service.selectAll();
    }

    /**
     *
     * @description 主题列表——在看帖页面，只能看到已发布的主题帖子
     * @author m13624
     * @return 内容编辑
     * @time 2017年3月9日 上午11:48:08
     */
    @RequestMapping(value = "publishedList", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public List<Note> publishedList() {
        return service.selectPublished();
    }

    /**
     *
     * @description 评论管理——需要审核评论的主题列表
     * @author m13624
     * @return 主题列表数组
     * @time 2017年3月9日 下午03:15:58
     */
    @RequestMapping(value = "approveList", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public List<Note> approveList() {
        return service.approveList();
    }

    /**
     *
     * @description 评论管理——点击具体主题，获取该主题下的所有评论列表
     * @author m13624
     * @return 主题列表数组
     * @time 2017年3月9日 下午04:02:03
     */
    @RequestMapping(value = "replyList/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public List<Note> replyList(@PathVariable String id) {
        return service.replyList(id);
    }

    /**
     * 
     * @description 根据ID删除当前主题
     * @author z11595
     * @param id
     *            主键ID
     * @time 2017年1月22日 上午11:48:51
     */
    @RequestMapping(value = "del/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.DELETE)
    public void delById(@PathVariable String id) {
        service.delById(id);
    }

    /**
     *
     * @description 根据ID删除当前评论
     * @author m13624
     * @param id
     *            主键ID
     * @time 2017年3月9日 下午05:03:51
     */
    @RequestMapping(value = "delReply/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.DELETE)
    public void delReplyById(@PathVariable String id) {
        service.delReplyById(id);
    }

    /**
     *
     * @description 根据ID审核通过当前评论
     * @author m13624
     * @param id
     *            主键ID
     * @time 2017年3月9日 下午06:58:51
     */
    @RequestMapping(value = "approveReply/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.DELETE)
    public void approveReplyById(@PathVariable String id) {
        service.approveReplyById(id);
    }

    /**
     * 
     * @description 更新内容编辑
     * @author z11595
     * @param t
     *            内容编辑实体
     * @time 2017年1月22日 上午11:49:13
     */
    @RequestMapping(value = "upt", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public void uptNote(@RequestBody Note t) {
        service.uptNote(t);
    }

    /**
     * 
     * @description 查询当前内容编辑按照ID
     * @author z11595
     * @param id
     *            主键ID
     * @return
     * @time 2017年1月24日 上午11:14:03
     */
    @RequestMapping(value = "query/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public Note queryById(@PathVariable String id) {
        return service.selectById(id);
    }
}
