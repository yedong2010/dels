package com.dels.dao;

import com.dels.model.chat.Note;
import com.dels.model.chat.Reply;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @description 内容编辑讨论
 * @author z11595
 * @time 2017年2月7日 下午1:50:59
 */
public interface NoteMapper {
    /**
     * 
     * @description 讨论内容列表
     * @author z11595
     * @return 内容数组
     * @time 2017年2月7日 下午1:51:19
     */
    List<Note> selectAll();
    
    /**
     * 
     * @description 查询本人发布的讨论内容
     * @author z11595
     * @param uname
     * @return
     * @time 2017年2月7日 下午7:41:32
     */
    List<Note> selectByAuthor(@Param("author")String author);

    /**
     * 
     * @description 新增内容编辑贴
     * @author z11595
     * @param t 内容实例
     * @time 2017年2月7日 下午1:51:43
     */
    void addNote(Note t);

    /**
     * 
     * @description 根据ID主键删除内容编辑帖
     * @author z11595
     * @param id 主键ID
     * @time 2017年2月7日 下午1:51:59
     */
    void delNoteById(String id);

    /**
     *
     * @description 根据ID主键删除评论
     * @author m13624
     * @param id 主键ID
     * @time 2017年3月9日 下午5:06:19
     */
    void delReplyById(String id);

    /**
     *
     * @description 根据ID主键审核通过评论
     * @author m13624
     * @param id 主键ID
     * @time 2017年3月9日 下午7:06:53
     */
    void approveReplyById(String id);

    /**
     * 
     * @description 更新内容编辑帖
     * @author z11595
     * @param t 更新内容实例
     * @time 2017年2月7日 下午1:52:19
     */
    void uptItem(Note t);

    /**
     * 
     * @description 根据主键ID查找帖子内容
     * @author z11595
     * @param id
     * @return
     * @time 2017年2月7日 下午1:52:34
     */
    Note selectById(@Param("id") String id);

    /**
     * 
     * @description 新增当前回复
     * @author z11595
     * @param t 回复实例
     * @time 2017年2月7日 下午1:52:55
     */
    void addReply(Reply t);

    /**
     * 
     * @description 更新当前内容评论数
     * @author z11595
     * @param id 主键ID
     * @time 2017年2月7日 下午1:53:10
     */
    void uptCount(@Param("id") String id);

    /**
     * 
     * @description 根据当前外键ID获取评论信息
     * @author z11595
     * @param id 内容编辑ID
     * @return 评论信息（按照时间顺序）
     * @time 2017年2月7日 下午5:11:04
     */
    List<Reply> getReplys(String id);

    /**
     *
     * @description 已经发布的讨论内容列表
     * @author m13624
     * @return 内容数组
     * @time 2017年3月9日 下午10:35:56
     */
    List<Note> selectPublished();

    /**
     *
     * @description 有评论需要审核的主题列表
     * @author m13624
     * @return 主题数组
     * @time 2017年3月9日 下午03:35:56
     */
    List<Note> approveList();

    /**
     *
     * @description 该主题下的所有评论
     * @author m13624
     * @return 评论数组
     * @time 2017年3月9日 下午04:06:56
     */
    List<Note> replyList(String id);
}