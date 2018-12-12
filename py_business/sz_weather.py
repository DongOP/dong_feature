from urllib.request import urlopen
from bs4 import BeautifulSoup
import re
import time

# resp=urlopen('http://www.weather.com.cn/weather/101280601.shtml')

def getWebMessage():
	resp = urlopen('http://www.weather.com.cn/weather/101280601.shtml')
	return resp

def getTodayWeather():
	resp = getWebMessage()
	soup = BeautifulSoup(resp,'html.parser')
	tagDate = soup.find('ul', class_="t clearfix")
	dates = tagDate.h1.string

	tagToday = soup.find('p', class_="tem")
	try:
		temperatureHigh = tagToday.span.string
	except AttributeError as e:
		temperatureHigh = tagToday.find_next('p', class_="tem").span.string

	temperatureLow = tagToday.i.string
	weather = soup.find('p', class_="wea").string

	tagWind = soup.find('p',class_="win")
	winL = tagWind.i.string

	print('日期：' + dates)
	print('风级：' + winL + ', 最低温度：' + temperatureLow + ', 最高温度：' + temperatureHigh + ', 天气：' + weather + '\n')

def getTomorrowWeather():
	resp = getWebMessage()
	soup = BeautifulSoup(resp,'html.parser')
	tagDate = soup.find('ul', class_="t clearfix")
	dayDates = tagDate.find('li', class_="sky skyid lv2")
	dates = dayDates.h1.string
	tagTomorrow = dayDates.find('p', class_="tem")
	try:
		temperatureHigh = tagTomorrow.span.string
	except AttributeError as e:
		temperatureHigh = tagTomorrow.find_next('p', class_="tem").span.string

	temperatureLow = tagTomorrow.i.string
	weather = dayDates.find('p', class_="wea").string

	tagWind = dayDates.find('p',class_="win")
	winL = tagWind.i.string

	print('日期：' + dates)
	print('风级：' + winL + ', 最低温度：'+temperatureLow + ', 最高温度：'+temperatureHigh + ', 天气：'+weather)
	print('\n' + '---------------------------我是分割线------------------------' + '\n')

def main_loop():

	while True:
		getTodayWeather()
		getTomorrowWeather()
		time.sleep(30)

main_loop()