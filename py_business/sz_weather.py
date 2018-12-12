from urllib.request import urlopen
from bs4 import BeautifulSoup
import re

# resp=urlopen('http://www.weather.com.cn/weather/101280601.shtml')

def getTodayWeather():
	resp=urlopen('http://www.weather.com.cn/weather/101280601.shtml')
	soup=BeautifulSoup(resp,'html.parser')
	tagDate=soup.find('ul', class_="t clearfix")
	dates=tagDate.h1.string

	tagToday=soup.find('p', class_="tem")
	try:
		temperatureHigh=tagToday.span.string
	except AttributeError as e:
		temperatureHigh=tagToday.find_next('p', class_="tem").span.string

	temperatureLow=tagToday.i.string
	weather=soup.find('p', class_="wea").string

	tagWind=soup.find('p',class_="win")
	winL=tagWind.i.string

	print('日期：'+dates)
	# print('风级：'+winL)
	# print('最低温度：'+temperatureLow)
	# print('最高温度：'+temperatureHigh)
	# print('天气：'+weather)
	print('风级：'+winL + ', 最低温度：'+temperatureLow + ', 最高温度：'+temperatureHigh + ', 天气：'+weather)
	print('\n')

def getTomorrowWeather():
	resp=urlopen('http://www.weather.com.cn/weather/101280601.shtml')
	soup=BeautifulSoup(resp,'html.parser')
	tagDate=soup.find('ul', class_="t clearfix")
	dayDates=tagDate.find('li', class_="sky skyid lv2")
	dates = dayDates.h1.string
	tagToday=dayDates.find('p', class_="tem")
	try:
		temperatureHigh=tagToday.span.string
	except AttributeError as e:
		temperatureHigh=tagToday.find_next('p', class_="tem").span.string

	temperatureLow=tagToday.i.string
	weather=dayDates.find('p', class_="wea").string

	tagWind=dayDates.find('p',class_="win")
	winL=tagWind.i.string

	print('日期：'+dates)
	print('风级：'+winL + ', 最低温度：'+temperatureLow + ', 最高温度：'+temperatureHigh + ', 天气：'+weather)
	# print('风级：'+winL)
	# print('最低温度：'+temperatureLow)
	# print('最高温度：'+temperatureHigh)
	# print('天气：'+weather)

def main_loop():
	getTodayWeather()
	getTomorrowWeather()

main_loop()