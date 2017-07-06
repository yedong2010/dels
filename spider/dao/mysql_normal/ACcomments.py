#coding=utf8

from pymysql.err import *
import pymysql

class ACcomments(object):
    '''
评论搜集表各项操作
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
        print("comments start...");
        for j, k in enumerate(data):
            try:
                cursor.execute("INSERT INTO ACcomments(cid, content, userName, layer, acid, isDelete, checkTime) VALUES(%s, %s, %s, %s, %s, %s, %s)",
                                      (k.get_cid(),
                                       k.get_content(),
                                       k.get_user_name(),
                                       k.get_layer(),
                                       k.get_acid(),
                                       k.get_delete(),
                                       k.get_check_time()))
            except IntegrityError:
                print("已存在该评论，判断是否被删除")
                isd = k.get_delete()
                if k.get_acid() == 3794475:
                    print k
                if isd == 1:
                    print("已删除，更新标识")
                    cursor.execute("UPDATE ACcomments set isDelete = %s WHERE acid = %s and layer = %s and isDelete=0",
                                   (k.get_delete(), k.get_acid(), k.get_layer()))
            except Exception as e:
                print("未知错误: ", e)
        
        print("comments done!")
        cursor.close()
        conn.commit()
        conn.close()

    def clear(self):
        conn = pymysql.connect(host = self.__dbinfo.get_host(),
                                    port = self.__dbinfo.get_port(),
                                    user = self.__dbinfo.get_user(),
                                    passwd = self.__dbinfo.get_pwd(),
                                    db = self.__dbinfo.get_dbname(),
                                    charset = self.__dbinfo.get_charset())
        cursor = conn.cursor()
        deleteSor = conn.cursor()
        row = []
        try:
            cursor.execute("SELECT cid FROM ACcomments WHERE isdelete = 0 AND checkTime < DATE_SUB(NOW(), INTERVAL 7 DAY")
            for data in cursor:
                row.append(data);
                deleteSor.execute("DELETE FROM ACcomments WHERE cid = '", data, "'")
        except IntegrityError:
            print("ACcomments主键重复")
        except Exception:
            print("未知错误")

        cursor.close()
        conn.commit()
        conn.close()
        return row
    