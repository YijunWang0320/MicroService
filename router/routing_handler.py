#!/usr/bin/env python
from operations import configOpts

PS = 'protocols static'

class routingHanlder(configOpts):
    def route(self, action, type, suffix):
        routing_params = [PS, type]
