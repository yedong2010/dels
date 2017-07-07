package com.dels.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dels.dao.NoteMapper;
import com.dels.model.chat.Note;
import com.dels.model.chat.Reply;
import com.dels.service.INoteService;
import com.dels.utils.BasicConstants;

/**
 * 
 * @description 发帖管理
 * @author z11595
 * @time 2017年1月13日 上午11:51:16
 */
@Service
public class NoteService implements INoteService {
    Logger logger = LoggerFactory.getLogger(NoteService.class);
    @Autowired
    NoteMapper tDao;

    @Override
    public void insert(Note t) {
        // 生成时间戳作为主键ID
        t.setId(getTimeStamp());
        t.setCount(0l);
        try {
            tDao.addNote(t);
        } catch (Exception e) {
            logger.info("插入当前DMS_NOTE表出错：" + e.toString());
        }
    }

    /**
     * 
     * @description 创建当前时间戳作为主键ID
     * @author z11595
     * @return
     * @time 2017年1月13日 上午11:54:26
     */
    private String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    @Override
    public List<Note> selectByAuthor() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tDao.selectByAuthor(userDetails.getUsername());
    }

    @Override
    public void delById(String id) {
        try {
            tDao.delNoteById(id);
        } catch (Exception e) {
            logger.info("删除当前DMS_NOTE表,主键为：" + id + "出错：" + e.toString());
        }
    }

    @Override
    public void delReplyById(String id) {
        try {
            tDao.delReplyById(id);
        } catch (Exception e) {
            logger.info("删除当前DMS_NOTE表,主键为：" + id + "出错：" + e.toString());
        }
    }

    @Override
    public void approveReplyById(String id) {
        try {
            tDao.approveReplyById(id);
        } catch (Exception e) {
            logger.info("删除当前DMS_NOTE表,主键为：" + id + "出错：" + e.toString());
        }
    }

    @Override
    public void uptNote(Note t) {
        tDao.uptItem(t);
    }

    @Override
    public Note selectById(String id) {
        Note n = tDao.selectById(id);
        n.setReplys(tDao.getReplys(id));
        return n;
    }

    @Override
    public void addReply(Reply t) {
        try {
            t.setId(getTimeStamp());
            t.setStatus(BasicConstants.REPLY_STATUE_PUBLIC);// 设置当前评论发布状态
            tDao.addReply(t);
//            tDao.uptCount(t.getUid());
        } catch (Exception e) {
            logger.info("新增DMS_REPLY表,外键为：" + t.getUid() + "出错：" + e.toString());
        }
    }

    /**
     *
     * @description 主题管理——管理员可以查看所有的主题列表
     * @author z11595
     * @return
     * @time 2017年1月13日 上午11:54:26
     */
    @Override
    public List<Note> selectAll() {
        return tDao.selectAll();
    }

    /**
     *
     * @description 主题列表——用户们可以查看已经发布的主题列表
     * @author m13624
     * @return
     * @time 2017年3月09日 上午11:54:26
     */
    @Override
    public List<Note> selectPublished() {
        return tDao.selectPublished();
    }

    /**
     *
     * @description 评论管理——系统管理员查看有评论需要审核的主题
     * @author m13624
     * @return
     * @time 2017年3月09日 下午03:21:26
     */
    @Override
    public List<Note> approveList() {
        return tDao.approveList();
    }

    /**
     *
     * @description 评论管理——该主题下的所有评论
     * @author m13624
     * @return
     * @time 2017年3月09日 下午04:05:26
     */
    @Override
    public List<Note> replyList(String id) {
        return tDao.replyList(id);
    }
}
