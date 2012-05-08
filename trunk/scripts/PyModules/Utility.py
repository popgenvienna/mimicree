#!/usr/bin/env python


class File:
	
	@classmethod
	def linecount(inputFile):
		count=0;
		for line in open(inputFile):
			count+=1
		return count