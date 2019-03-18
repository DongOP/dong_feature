#!/usr/bin/python
# -*- coding: utf-8 -*-

import os, sys

pypath = os.path.dirname(__file__)
sys.path.append(os.path.join(pypath, './bin'))

import activate_this

import re
import json
import time
from datetime import datetime, timedelta
import traceback
import requests
import pyquery

def make_text(txt, token):
    data = {
        'text': txt
    }
    url = 'http://127.0.0.1:4000/hooks/' + token
    try:
        resp = requests.post(url, json = data)
        print(resp.text)
        if not resp.json()['success']:
            raise Exception('sending failed')
    except:
        traceback.print_exc()

def is_clean_time():
    # 晚上四点钟清理缓存的数据
    now = datetime.utcnow() + timedelta(hours=8)
    return now.hour == 4

def get_acc_minutes():
    now = datetime.utcnow() + timedelta(hours=8)
    return now.hour * 60 + now.minute

class HookRequests(object):

    def __init__(self, session):
        self._session = session

    def hook_requests(self, method, *args, **kwargs):
        if 'timeout' not in kwargs:
            kwargs['timeout'] = 10
        return getattr(self._session, method)(*args, **kwargs)

    def get(self, *args, **kwargs):
        return self.hook_requests('get', *args, **kwargs)

    def post(self, *args, **kwargs):
        return self.hook_requests('post', *args, **kwargs)

# 黄金指数爬虫

class GoldSpider(object):

    def __init__(self, token):
        self._session = self.get_sina_finace_session()
        self._token = token

    def play(self, period_no):
        if self.is_gold_trading():
            try:
                if period_no%5 == 0:
                    # 抓取黄金数据
                    message = self.make_gold_message()
                    # 把数据推给general频道
                    make_text(message, self._token)
            except:
                traceback.print_exc()
                make_text(u'黄金数据抓取失败', self._token)

    def get_sina_finace_session(self):
        headers = {
            'Referer':'http://finance.sina.com.cn/money/gold/AUTD/quote.shtml',
            'User-Agent':'Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36',
            'Accept-Language':'zh-CN,zh;q=0.8',
        }
        session = requests.Session()
        session.headers.update(headers)
        return HookRequests(session)

    def make_gold_message(self):
        resp = self._session.get('http://hq.sinajs.cn/list=hf_AUTD')
        data = resp.text
        # data = u'var hq_str_hf_AUTD="268.97,0,268.95,268.97,269.07,268.31,09:46:02,269.25,268.90,17570,25.00,47.00,2018-03-21,黄金延期";'
        result = re.search(u'var hq_str_hf_AUTD="(.+?)";', data)
        if not result:
            raise Exception('web data invalid')
        items = result.group(1).split(',')
        if len(items) != 14:
            raise Exception('bad web data format')
        current_price, _, buy_price, sell_price, \
                high_price, low_price, current_time, \
                    close_price, open_price, _, _, _, current_date = items[:13]
        if current_price == u'0.00':
            return '目前黄金无数据'
        current_time = '%s %s'%(current_date, current_time)
        tmpl = u'黄金当前 : %s , 开盘 / 昨收 : %s / %s , 买 / 卖 : %s / %s , 高 / 低 : %s / %s , 数据时间 : %s'
        return tmpl%(current_price, open_price, close_price, buy_price, sell_price, high_price, low_price, current_time)

    def is_gold_trading(self):
        now = datetime.utcnow() + timedelta(hours=8)
        if now.weekday() in [5, 6]:
            return False
        minute_count = now.hour * 60 + now.minute
        if (now.weekday() != 0) and (minute_count <= 3*60):
            return True
        if minute_count >= 8*60+30 and minute_count <= 12*60:
            return True
        if minute_count >= 13*60 and minute_count <= 16*60:
            return True
        if minute_count >= 19*60+30:
            return True
        return False

# 知名股神wu2198的博客爬虫

class Wu2198BlogSpider(object):

    def __init__(self, token):
        self._session = self.get_sina_blog_session()
        self._token = token
        self._old_blog_minutes = get_acc_minutes() - 5
        print('init blog minutes is ', self._old_blog_minutes)

    def play(self, period_no):
        if self.is_stock_trading():
            try:
                max_minutes, last_entries = self.get_blog_latest_entries(self._old_blog_minutes)
                if max_minutes > self._old_blog_minutes:
                    self._old_blog_minutes = max_minutes
                print('latest blog minutes is ', self._old_blog_minutes)
                for title, content in last_entries:
                    make_text(u'@all {}'.format(content), self._token)
            except:
                traceback.print_exc()
                make_text(u'博客数据抓取失败', self._token)
        elif is_clean_time():
            # 自动清除老旧数据直到开市
            self._session = self.get_sina_blog_session()
            self._old_blog_minutes = get_acc_minutes() - 5

    def is_stock_trading(self):
        now = datetime.utcnow() + timedelta(hours=8)
        if now.weekday() in [5, 6]:
            return False
        minute_count = now.hour * 60 + now.minute
        if minute_count >= 9*60+10 and minute_count <= 11*60+40:
            return True
        if minute_count >= 13*60 and minute_count <= 15*60+10:
            return True
        return False

    def get_article_list(self):
        resp = self._session.get('http://blog.sina.cn/dpool/blog/wu2198')
        if not resp.ok:
            raise Exception('get blog index failed')
        result = re.search(r'"blogerUid" : "(\d+?)"', resp.text)
        if not result:
            raise Exception('get bloger uid failed')
        uid = result.group(1)
        url = 'http://blog.sina.cn/dpool/blog/newblog/riaapi/mblog/get_articlelist.php'
        resp = self._session.post(url, data={'uid':uid,'pagesize':20,'page':1,'class_id':-1})
        if not resp.ok:
            raise Exception('get blog articles failed')
        # print(json.dumps(resp.json(), indent=4, ensure_ascii=False))
        return resp.json().get('data',{}).get('msg',[])

    def check_article_title(self, article_title):
        result = re.search(ur'(\d+?)月(\d+?)日.*?股市直播.*?', article_title)
        if not result:
            return False
        month_str, day_str = result.groups()
        months = int(month_str)
        days = int(day_str)
        now = datetime.utcnow() + timedelta(hours = 8)
        return now.month == months and now.day == days

    def get_article_content(self, article):
        article_id = article.get('article_id', '')
        article_url = article.get('url', '')
        self._session.get(article_url)
        data = {
            'blogid':article_id,
            'page':2
        }
        headers = {
            'Origin':'http://blog.sina.cn',
            'Referer':'http://blog.sina.cn/dpool/blog/s/blog_48874cec0102y4f8.html?type=-1',
            'X-Requested-With':'XMLHttpRequest',
            'Accept':'application/json, text/javascript, */*; q=0.01'
        }
        cookies = {
            'historyRecord':'{"href":"http://blog.sina.cn/dpool/blog/s/blog_%s.html","refer":""}'%(article_id),
            'vt':'99',
        }
        url = 'http://blog.sina.cn/dpool/blog/newblog/mblog/controllers/article.php'
        resp = self._session.post(url, data = data, headers = headers, cookies = cookies)
        return resp.content

    def get_article_entries(self, article_content):
        p = pyquery.PyQuery(article_content)
        raw_entries = re.findall(ur'(\d{1,3}:\d{1,3}\s+.+?)\n', p.text(), re.DOTALL)
        entries = []
        for raw_entry in raw_entries:
            result = re.search(ur'(\d{1,3}):(\d{1,3})\s+(.+?)$', raw_entry)
            if result:
                hour_str, minute_str, desc = result.groups()
                hours = int(hour_str)
                minutes = int(minute_str)
                entries.append({
                    'hours':hours,
                    'minutes':minutes,
                    'desc':desc
                })
        return entries

    def get_blog_latest_entries(self, last_minutes):
        article_list = self.get_article_list()
        valid_article = None
        for article in article_list:
            title = article.get('article_title', '')
            if self.check_article_title(title):
                valid_article = article
                break
        max_minutes = last_minutes
        valid_entries = []
        if valid_article:
            title = valid_article.get('article_title', '')
            content = self.get_article_content(article)
            entries = self.get_article_entries(content)
            latest_desc = ''
            print('find article : ', title)
            for entry in entries:
                entry_minutes = entry['hours'] * 60 + entry['minutes']
                if entry_minutes > last_minutes:
                    latest_desc = u'{}:{} {}'.format(entry['hours'], entry['minutes'], entry['desc'])
                    print('new entry : ', entry_minutes, last_minutes, latest_desc)
                    valid_entries.append((title, latest_desc))
                if entry_minutes > max_minutes:
                    max_minutes = entry_minutes
        return max_minutes, valid_entries

    def get_sina_blog_session(self):
        session = requests.Session()
        session.headers.update({
            'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1',
            'Referer': 'http://blog.sina.cn'
        })
        return HookRequests(session)


class SolidotsSpider(object):

    def __init__(self, token):
        self._token = token
        self._session = self.get_solidots_session()
        self._article_map = {}

    def play(self, period_no):
        if self.is_working_hours():
            try:
                new_articles = self.load_article_map(self._article_map)
                for article in new_articles:
                    make_text(u'@yu {}\n链接：{}\n{}'.format(article['title'],
                            article['link'], article['desc']), self._token)
            except:
                traceback.print_exc()
                make_text(u'科技数据抓取失败', self._token)

    def is_working_hours(self):
        now = datetime.utcnow() + timedelta(hours=8)
        minute_count = now.hour * 60 + now.minute
        if minute_count >= 8*60 and minute_count <= 22*60:
            return True
        return False

    def get_solidots_session(self):
        session = requests.Session()
        session.headers.update({
            'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1',
            'Referer': 'https://www.solidot.org'
        })
        return HookRequests(session)

    def get_rss_content(self):
        resp = self._session.get('https://www.solidot.org/index.rss')
        return resp.content

    def load_article_map(self, article_map):
        page = self.get_rss_content()
        boot = not article_map
        new_articles = []
        links = []
        for item in pyquery.PyQuery(page)('item'):
            obj = pyquery.PyQuery(item)
            title, link, desc = tuple(obj(k).text() for k in ('title','link','description'))
            links.append(link)
            if link not in article_map:
                desc = pyquery.PyQuery(' ' + desc).text()
                article = {'title':title, 'link':link, 'desc':desc}
                article_map[link] = article
                if not boot:
                    new_articles.append(article)
        if links:
            bad_links = []
            for link in article_map:
                if link not in links:
                    bad_links.append(link)
            for link in bad_links:
                del article_map[link]
        return new_articles


def main_loop():
    n = 0
    chat_token = 'tEqhwdDY5nfERbokE/HT4rAjDgsMpQpig6LfBJSzXJhSMo2hTScZoRxY5ZiSTbaba2'
    make_text(u'@all Welcome!', chat_token)
    gold_spider = GoldSpider(chat_token)
    blog_spider = Wu2198BlogSpider(chat_token)
    # solidots_spider = SolidotsSpider(chat_token)
    while True:
        if n >= 120: n = 0
        gold_spider.play(n)
        blog_spider.play(n)
        # solidots_spider.play(n)
        time.sleep(30)
        n = n + 1


main_loop()
