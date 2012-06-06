#!/usr/bin/env python

import sys
import collections
import math
import subprocess


class RUtility:
	
	@classmethod
	def run(cls,rfile):
		"""
		run the following R file
		"""
		
		command="R --vanilla --slave < "+ rfile
		subprocess.call(command)

class RFormat:
	@classmethod
	def get_vector(cls,values):
		t="c("+ ",".join(values)+")"
		return t
