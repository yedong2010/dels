# -*- coding: utf-8 -*-
# !/usr/bin/env python
"""
-------------------------------------------------
   File Name：     ProxyGetSchedule.py
   Description :  代理定时获取
   Author :       MaytoMarry
   date：          2017/07/04
-------------------------------------------------
"""

import time
from ProxyGetter.getFreeProxy import *
from DB.MysqlPool import *


class ProxyGetSchedule():
    """
    代理定时获取
    """

    def getProxy(self, ms):
        """
        获取代理
        :return:
        """
        gp = GetFreeProxy()
        proxys = gp.getproxys()
        for proxy in proxys:
            if len(proxy) > 0:
                ms.insertOne("insert into proxysinfo (proxy) values (%s)", proxy)
        ms.end()
        ms.dispose()
        print 'proxys get success.\n'


def run():
    # main()
    while True:
        pg = ProxyGetSchedule()
        ms = Mysql()
        pg.getProxy(ms)
        time.sleep(900)


if __name__ == '__main__':
    run()
