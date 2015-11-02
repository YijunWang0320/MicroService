__author__ = 'wangyijun'
import sqlite3
import os.path


class Config(object):
    def __init__(self, filename):
        self._config = dict()
        if os.path.exists(filename):
            fp = open(filename, 'r')
            for line in fp:
                key, value = line.split('=')
                self._config[key.strip()] = value.strip()
            fp.close()

    def update(self, key, value):
        self._config[key] = value

    def get(self, key):
        return self._config[key] if key in self._config.keys() else None
