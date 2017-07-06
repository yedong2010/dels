# -*- coding: utf-8 -*-
# !/usr/bin/env python
"""
-------------------------------------------------
   File Name：     GetFreeProxy.py
   Description :  抓取免费代理
   Author :       JHao
   date：          2016/11/25
-------------------------------------------------
   modify: Maytomarry
   date: 2017-06-21
-------------------------------------------------
"""
import re
import requests
from util.utilFunction import *

HEADER = {'Connection': 'keep-alive',
          'Cache-Control': 'max-age=0',
          'Upgrade-Insecure-Requests': '1',
          'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko)',
          'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
          'Accept-Encoding': 'gzip, deflate, sdch',
          'Accept-Language': 'zh-CN,zh;q=0.8',
          }

DICT = {'HBZIE': '8998',
        'GEZEE': '8118',
        'GEA': '80',
        'GEGE': '808',
        'HIDHG': '9797',
        'GEGEI': '8081',
        'HBBAE': '8888'
        }


class GetFreeProxy(object):
    """
    proxy getter
    """
    def freeProxyFirst(self, page):
        """
        抓取快代理IP http://www.kuaidaili.com/
        :param page: 翻页数
        modify:  Maytomarry
        date: 2017-06-21
        """
        i = 1
        url_list = []
        proxys = []
        while i <= page:
            url_list.append('http://www.kuaidaili.com/free/inha/%s/' % str(i))
            i = i + 1
            # 页数不用太多， 后面的全是历史IP， 可用性不高
        for url in url_list:
            tree = getHtmlTree(url)
            proxy_list = tree.xpath('.//div[@id="list"]//tbody/tr')
            for proxy in proxy_list:
                proxys.append(':'.join(proxy.xpath('./td/text()')[0:2]))
        return proxys

    def freeProxySecond(self, page):
        """
        抓取代理66 http://www.66ip.cn/
        :param page: 翻页数
        modify:  Maytomarry
        date: 2017-06-21
        """
        i = 1
        url_list = []
        proxys = []
        while i <= page:
            url_list.append('http://www.66ip.cn/%s.html' % str(i))
            i = i + 1
        for url in url_list:
            tree = getHtmlTree(url)
            proxy_list = tree.xpath('.//div/table[@bordercolor="#6699ff"]//tr')[1:]
            for proxy in proxy_list:
                proxys.append(':'.join(proxy.xpath('./td/text()')[0:2]))
        return proxys

    def freeProxyThird(self):
        """
        抓取西刺代理 http://api.xicidaili.com/free2016.txt
        modify:  Maytomarry
        date: 2017-06-21
        """
        url = 'http://api.xicidaili.com/free2016.txt'
        resp = requests.get(url=url, headers=HEADER, timeout=30).content
        proxys = resp.split('\r\n')
        return proxys

    def freeProxyFourth(self):
        """
        抓取guobanjia http://www.goubanjia.com/free/gngn/index.shtml
        网页前端做了处理，需要额外解析
        modify:  Maytomarry
        date: 2017-06-21
        """
        proxys = []
        url = "http://www.goubanjia.com/free/gngn/index{page}.shtml"
        for page in range(1, 10):
            page_url = url.format(page=page)
            tree = getHtmlTree(page_url)
            proxy_list = tree.xpath('//td[@class="ip"]')
            for each_proxy in proxy_list:
                proxy = ''
                for node in each_proxy:
                    style = node.attrib.get('style')
                    clas = node.attrib.get('class')
                    if (style is None or 'inline-block' in style) and node.text is not None:
                        if clas is None:
                            proxy = proxy + str(node.text).strip()
                        else:
                            pcode = clas.split(' ')[1]
                            if pcode in DICT:
                                proxy = proxy + ':' + DICT.get(pcode)
                                proxys.append(proxy)
        return proxys

    def getproxys(self):
        proxys = []
        proxys.extend(self.freeProxyFirst(10))
        proxys.extend(self.freeProxySecond(10))
        proxys.extend(self.freeProxyThird())
        proxys.extend(self.freeProxyFourth())
        return proxys


if __name__ == '__main__':
    gg = GetFreeProxy()
    # for e in gg.freeProxyFirst():
    #     print e

    # for e in gg.freeProxySecond():
    #     print e

    # for e in gg.freeProxyThird():
    #     print e
    #
    # for e in gg.freeProxyFourth():
    #     print e
    aa = gg.freeProxyFourth()
