#coding=utf-8

"""
1、py.test -q D:\Github\dong_feature\pytest_space\basic\test_class.py
执行脚本

2、pytest --html=report.html
cd 到根目录执行上面指令

"""


class TestClass:

    def func(x):
        return x + 1

    def test_func(self):
        assert self.func(3) == 5