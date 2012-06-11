#!/usr/bin/env python

import sys
import collections
import math
import commands


class RUtility:
	
	@classmethod
	def run(cls,rfile):
		"""
		run the following R file
		"""
		
		command="R --vanilla --slave <"+ rfile

		#print command
		commands.getstatusoutput(command)

class RFormat:
	@classmethod
	def get_vector(cls,values):
		conv=[str(i) for i in values]
		t="c("+ ",".join(conv)+")"
		return t
