#coding=utf8

from pymysql.err import *
import pymysql

class ACcommentsInfo(object):
    '''
投稿信息表各项操作
    '''
    __dbinfo = "";

    def __init__(self, dbinfo):
        self.__dbinfo = dbinfo;
        
    def insert(self, data):
        conn = pymysql.connect(host = self.__dbinfo.get_host(), \
                                    port = self.__dbinfo.get_port(), \
                                    user = self.__dbinfo.get_user(), \
                                    passwd = self.__dbinfo.get_pwd(), \
                                    db = self.__dbinfo.get_dbname(), \
                                    charset = self.__dbinfo.get_charset());
        cursor = conn.cursor();
        for j, k in enumerate(data):
            try:
                cursor.execute("INSERT INTO ACcommentsInfo(id, type, title, checkTime, url) VALUES(%s, %s, %s, %s, %s)",
                                  (k.get_id(),
                                   k.get_type(),
                                   k.get_title(),
                                   k.get_check_time(),
                                   k.get_url()))
            except IntegrityError:
                print("ACcommentsInfo主键重复");
            except InternalError:
                print ("数据格式错误：\n")
                print str(k.get_id) + str(k.get_check_time) + str(k.get_title) + str(k.get_type)+str(k.get_url)
            except Exception as e:
                print("未知错误: ", e)
        cursor.close()
        conn.commit()
        conn.close()

    # 查询最近7天入库的acid
    def searchSomeAcInfo(self):
        conn = pymysql.connect(host=self.__dbinfo.get_host(), \
                               port=self.__dbinfo.get_port(), \
                               user=self.__dbinfo.get_user(), \
                               passwd=self.__dbinfo.get_pwd(), \
                               db=self.__dbinfo.get_dbname(), \
                               charset=self.__dbinfo.get_charset())
        cursor = conn.cursor()
        rows = []
        try:
            cursor.execute("SELECT id FROM ACcommentsInfo WHERE checkTime >= DATE_SUB(NOW(), INTERVAL 7 DAY)")
            for data in cursor:
                rows.append(str(list(data)[0]))
        except Exception as e:
            print ("查询最近7天acinfo错误: ", e)
        return rows