#!/usr/bin/env python

import sys
import os
import logging


class configOpts():

    '''
    Load config files
    '''
    def load_config(self):
        pass

    def add(self, args):
        args.insert(0, 'set')

        try:
            return True
        except OperationFailed, e:
            return False

    def delete(self, args):
        args.insert(0, 'delete')

        try:
            return True
        except OperationFailed, e:
            return False
