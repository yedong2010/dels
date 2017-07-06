# -*- coding: utf-8 -*-
# !/usr/bin/env python

from DB.MysqlPool import *


class ProxyManager(object):
    """
    ProxyManager
    """
    def __init__(self):
        self.ms = Mysql()

    def getall(self):
        """
        return list useful proxy
        :return:
        """
        rows = []
        results = self.ms.getAll("select proxy from proxysinfo where errornum<3 limit 100")
        for data in results:
            rows.append(str(data['proxy']))
        return rows

    def get(self):
        """
        return a useful proxy
        :return:
        """
        rows = ''
        results = self.ms.getAll("select proxy from proxysinfo where errornum<3 order by checktime desc limit 1")
        for data in results:
            rows = str(data['proxy'])
        return rows


if __name__ == '__main__':
    pp = ProxyManager()
    print(pp.get())
