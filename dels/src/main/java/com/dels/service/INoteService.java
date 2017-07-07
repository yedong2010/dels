package com.dels.service;

import java.util.List;

import com.dels.model.chat.Reply;
import com.dels.model.chat.Note;

/**
 * 
 * @description 发帖管理
 * @author z11595
 * @time 2017年1月13日 上午10:56:50
 */
public abstract interface INoteService {
    /**
     * 
     * @description 通过贴信息传参保存当前发言人发言内容
     * @author z11595
     * @param t
     *            主题帖信息
     * @time 2017年1月13日 上午10:57:43
     */
    public abstract void insert(Note t);

    /**
     * 
     * @description 查询当前所有发帖信息
     * @author z11595
     * @return
     * @time 2017年1月16日 上午9:47:17
     */
    public abstract List<Note> selectByAuthor();

    /**
     * 
     * @description 删除发帖信息
     * @author z11595
     * @param id
     * @return void
     * @time 2017年1月16日 下午4:06:23
     */
    public abstract void delById(String id);

    /**
     *
     * @description 删除评论信息
     * @author m13624
     * @param id
     * @return void
     * @time 2017年3月9日 下午5:05:42
     */
    public abstract void delReplyById(String id);

    /**
     *
     * @description 审核通过评论信息
     * @author m13624
     * @param id
     * @return void
     * @time 2017年3月9日 下午7:00:42
     */
    public abstract void approveReplyById(String id);

    /**
     * 
     * @description 更新发帖信息
     * @author z11595
     * @param t
     *            更新后的实体内容
     * @time 2017年1月22日 上午11:50:03
     */
    public abstract void uptNote(Note t);

    /**
     * 
     * @description 查询当前贴内容
     * @author z11595
     * @param id
     *            主键ID
     * @return
     * @time 2017年1月24日 上午11:15:09
     */
    public abstract Note selectById(String id);

    /**
     * 
     * @description 创建发帖回复
     * @author z11595
     * @param t
     * @time 2017年2月7日 上午10:59:43
     */
    public abstract void addReply(Reply t);

    /**
     * 
     * @description 查询所有贴
     * @author z11595
     * @return
     * @time 2017年2月8日 上午10:52:22
     */
    public abstract List<Note> selectAll();

    /**
     *
     * @description 查询已经发布的帖子所有贴
     * @author m13624
     * @return
     * @time 2017年3月9日 上午10:33:42
     */
    public abstract List<Note> selectPublished();

    /**
     *
     * @description 查询有评论需要审核的主题列表
     * @author m13624
     * @return
     * @time 2017年3月9日 下午03:18:42
     */
    public abstract List<Note> approveList();

    /**
     *
     * @description 查询该主题下的所有评论
     * @author m13624
     * @return
     * @time 2017年3月9日 下午03:18:42
     */
    public abstract List<Note> replyList(String id);
}
