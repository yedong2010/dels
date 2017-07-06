#!/usr/bin/python
#coding=utf8

from base.URL import urlWork
from base.Constants import *
from base.PO.ACcommentsInfoPO import *
from base.PO.ACcommentsPO import *
from multiprocessing.dummy import Pool as ThreadPool
import os
import time
import json
import datetime
import sys
reload(sys)
sys.setdefaultencoding('utf8')
import socket
import urllib
import urllib2
import logging
import re

class process(urlWork):
    '''
ACFUN的入口函数在这里。
来自delete so
修改解析规则 2017-06-15
modify: MayToMarry
    '''
        
    def work(self, ac_comments):
        #数据全在内存里，自然要多一点保护
        try:
            self.ac_comments = ac_comments
            acinfolist = self.ac_comments.db_proc.ACCommentsInfo.searchSomeAcInfo()
            # 从acfun首页获取源代码用于解析热门投稿
            url_data = self.sendGet(ACFUN)
            
            # 从HTML中得到我们所需要的数据，格式为[[u'/v/ac1873087', u'title="\u5168\u660e\u661f\u70ed\u5531happy"']...]
            parse_data = self.get_parse_data(url_data)
            
            # 从每篇投稿中获取基本的信息
            analyse_data = self.parse(parse_data, acinfolist)

            # 使用map进行多线程分析每篇投稿的评论
            pool = ThreadPool(16) 
            insert_data = pool.map(self.analyse, analyse_data) #这里的输出为ACComments结构体的数组
            pool.close()
            pool.join()
            
            # 将数据从[[], [], []]转换为[,,,]
            insert_data = self.clear_insert_data(insert_data)
            
            # 发送评论
            #for node in insert_data:
            self.ac_comments.insert(insert_data)
            # 数据清理
            #self.clearDB()

        except Exception as e:
            logging.error(str(e))
                
    def get_parse_data(self, urlContent):
        parse_data = []
        
        pattern_1 = re.compile(r'''
            <a.*?</a>     
            ''', re.VERBOSE)
        pattern_2 = re.compile(r'''
            /a/ac[0-9]* | /v/ac[0-9]* | /v/ab[0-9]* |     #从<a xxxxx </a>中拿到投稿url和title就OK
            title=".*?"
            ''', re.VERBOSE)
        
        #按照<a xxxxx </a>解析
        src = pattern_1.findall(urlContent)
        
        #从<a xxxxx </a>中拿到投稿url和title就OK
        tmp = []
        for s in src:
            tmp = pattern_2.findall(s)
            if len(tmp) == 2:
                parse_data.append(tmp)
                
        return parse_data
        
    '''
          数据结构约定：
          rows[row[投稿URL, 投稿类型, 投稿标题, 时间], row, row...]
    '''
    def parse(self, src, acinfolist):
        max_id = 0 #max_id这个字段用来查询更多的投稿
        now = 0
        rows = []#front_urlData
        for data in src:
            try:
                row = ACcommentsInfoPO() #保存一篇投稿抓取的内容
                #获取投稿类型
                if data[0][0:5] == '/v/ac':
                    row.set_id(data[0][5:])
                    row.set_type('视频')
                elif data[0][0:5] == '/a/ac':
                    row.set_id(data[0][5:])
                    row.set_type('文章')
                elif data[0][0:5] == '/v/ab':
                    #番剧的id和其他不一样，加负号以示区别
                    row.set_id('-' + data[0][5:])
                    row.set_type('番剧')
                else:
                    continue
                
                #获取acid和url
                row.set_url(ACFUN + data[0])
                
                #max_id这个字段用来查询更多的投稿，比如我从首页获取的最大投稿是ac190000，那么一会我会多抓去ac188900到ac190000的评论信息
                if max_id < int(data[0][5:]):
                    max_id = int(data[0][5:])
                    

                #先过滤掉前面几个字
                data[1] = data[1][7:]

                row.set_title(data[1])
                row.set_check_time(str(datetime.datetime.now()))

            except Exception:
                continue
            if str(row.get_id()) not in acinfolist:
                rows.append(row)
                acinfolist.append(str(row.get_id()))
        
        #开始随机抓取评论
        if len(rows) > 0:
            self.create_more(rows, max_id, acinfolist)
            #投稿信息单独放一张表
            self.ac_comments.db_proc.ACCommentsInfo.insert(rows)
           
        return acinfolist
    '''
          数据结构约定：
          rows[row[评论cid, 评论内容, 评论人用户名, 引用评论cid, 该评论楼层数, 投稿URL, 删除标志, 司机标志, 时间戳], row, row...]
    '''
    def analyse(self, src):
        #初始化一个row，不然极端情况下程序会崩溃
        row = [] #保存一篇投稿的评论
        acid = int(src)
        if acid == 3794475:
            print "$$"
        #番剧的id小于0
        if acid > 0:
            url = "http://www.acfun.cn/comment_list_json.aspx?contentId=" + str(acid) + "&currentPage=1"
        else:
            url = 'http://www.acfun.cn/comment/bangumi/web/list?bangumiId=' + str(-acid) + '&pageNo=1'

        jsonContent = self.sendGet(url)
        if not self.checkURL(jsonContent):
            logging.warning("connect acfun comments fail")
            return 
        
        try:
            j_obj = json.loads(jsonContent)
        except Exception:
            logging.warning("get acfun comments fail")
            return 
        
        #番剧的id小于0
        try:
            json_data = j_obj['data']["commentContentArr"]
        except:
            logging.error("commentContentArr is not exist")
            return
        
        #偶尔会出现找不到commentContentArr的情况
        try:
            #开始解析json评论
            for m, n in enumerate(json_data):
                comment = ACcommentsPO() #保存一条评论的内容
                comment.set_acid(int(acid)) #抓取投稿编号            
                comment.set_cid(int(json_data[n]["cid"])) #抓取评论cid
                comment.set_content(json_data[n]["content"]) #抓取评论内容
                comment.set_user_name(json_data[n]["userName"]) #抓取评论人用户名
                comment.set_layer(int(json_data[n]["count"])) #抓取该评论楼层数
                userID = int(json_data[n]["userID"]) #抓取评论人用户ID

                #删除判断
                self.checkDelete(comment, userID)
                
                #时间戳->postdata评论提交时间
                comment.set_check_time(str(json_data[n]["postDate"]))
                
                #数据下盘时间需要商量一下
                row.append(comment)
                
                #不能浪费太多时间在拥有超大评论量的投稿上
                if m > 3000:
                    logging.error("over 3000, drop it.")
                    break
                
        except Exception:
            logging.error("commentContentArr is not exist")
            return 
            
        return row
    
    def checkURL(self, urlContent):
        if urlContent == URL_EXCEPTION \
        or urlContent == URL_FUALT:
            return False
        else:
            return True
            
    def checkDelete(self, comment, userID):
        #判断是否为已删除，为-1则表示删除
        if userID == -1:
            comment.set_delete(1)
        else:
            comment.set_delete(0)
        
    def clearDB(self):
        logging.debug("clear...")
        if os.path.getsize("/var/log/acmore.log") > 1048576:
            os.system("> /var/log/acmore.log")

    # 用来构造更多投稿的字段
    def create_more(self, rows, ac_id, acinfolist):
        for i in range(1, 750):
            now_id = str(ac_id - i)
            if now_id not in acinfolist:
                row = ACcommentsInfoPO()
                row.set_id(now_id)
                row.set_url(ACFUN_URL + str(abs(int(now_id))))
                row.set_type("类型未知")
                row.set_title("标题不详")
                row.set_check_time(str(datetime.datetime.now()))
                rows.append(row)
            else:
                continue
            
    # 将以投稿形式的数组转换为以每条评论分开的数组
    def clear_insert_data(self, datas):
        result = []
        for data in datas:
            if not data:
                continue
            
            for comment in data:
                if not comment:
                    continue
                
                result.append(comment)
                
        return result
