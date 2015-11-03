__author__ = 'wei'
import os


class RouterConfig(object):

    def __init__(self, filename):
        self._config = dict()
        if os.path.exists(filename):
            file_contents = open(filename, 'r')
            for line in file_contents:
                pair = line.split("=")
                self._config[pair[0].strip()] = pair[1].strip()
            file_contents.close()

    def get(self, key):
        return self._config[key] if key in self._config.keys() else None

    def get_all_api(self):
        return self._config["API"].split(",") if "API" in self._config.keys() else None

    def get_all_services(self):
        return self._config["SERVICE"].split(",") if "SERVICE" in self._config.keys() else None
