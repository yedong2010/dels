# -*- coding: utf-8 -*-
# !/usr/bin/env python
"""
-------------------------------------------------
   File Name：     ProxyGetSchedule.py
   Description :  代理定时验证
   Author :       MaytoMarry
   date：          2017/07/04
-------------------------------------------------
"""

import time
from DB.MysqlPool import *
from util.utilFunction import *



class ProxyValidateSchedule():
    """
    获取有效的代理
    """
    def getDBproxys(self, ms):
        rows = []
        ret = ms.getAll("select proxy from proxysinfo where errornum < 3")
        for data in ret:
            rows.append(str(data['proxy']))
        return rows

    def deleteFaildProxys(self, ms):
        ms.delete("delete from proxysinfo where errornum>=3")

    """
    代理定时验证
    """
    def validateProxy(self, ms):
        proxys = self.getDBproxys(ms)
        for proxy in proxys:
            if validUsefulProxy(proxy) is False:
                ms.update("update proxysinfo set errornum=errornum+1 where proxy='%s'" % proxy)
        ms.end()
        self.deleteFaildProxys(ms)
        ms.dispose()
        print 'validate success..\n'


def run():
    # main()
    while True:
        pv = ProxyValidateSchedule()
        ms = Mysql()
        pv.validateProxy(ms)
        time.sleep(60)


if __name__ == '__main__':
    run()
