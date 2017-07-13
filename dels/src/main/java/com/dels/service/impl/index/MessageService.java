package com.dels.service.impl.index;

import com.dels.dao.index.AccommentMapper;
import com.dels.dao.index.MessageMapper;
import com.dels.model.index.Accomment;
import com.dels.model.index.Message;
import com.dels.service.index.IAccommentService;
import com.dels.service.index.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MaytoMarry on 2017/7/12.
 */
@Service
public class MessageService implements IMessageService{

    @Autowired
    MessageMapper msDao;

    @Override
    public List<Message> getMessages() {
        return msDao.selectContents();
    }

    @Override
    public String insertMessage(String content) {
        try {
            msDao.insertMessage(content);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "500 error.";
    }
}
