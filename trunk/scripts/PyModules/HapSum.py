#!/usr/bin/env python

import sys
import collections


class SNPshot:
	"""
	Short for SNP snapshot :)
	Represents the major and minor counts of a SNP at a single time point;
	Immutable container type
	"""
	def __init__(self,major,minor):
		coverage=major+minor
		if(coverage==0):
			raise ValueError("Coverage must be larger than zero")
		self.__major=major
		self.__minor=minor
		self.__coverage=coverage
	
	@property
	def major(self):
		return self.__major
	
	@property
	def minor(self):
		return self.__minor
	
	@property
	def coverage(self):
		return self.__coverage
	
	@property
	def majorFrequency(self):
		return float(self.__major)/float(self.__coverage)
	
	@property
	def minorFrequency(self):
		return float(self.__minor)/float(self.__coverage)
	
	@property
	def isFixed(self):
		if(self.__major == 0 or self.__minor==0):
			return True
		return False



class SNPSuccession:
	
	#3R      13094319        A       A/G     G:-0.1:0.5              250:250 216:284 287:213 407:93  422:78  464:36  469:31  482:18  481:19  492:8   492:8
	def __init__(self,chromosome, position, refChar, majorAllele,minorAllele, selected,s,h,snpshots):
		self.chr = chromosome
		self.pos   = position
		self.refChar    = refChar
		self.majorAllele= majorAllele
		self.minorAllele= minorAllele
		self.selected   = selected
		self.s = s
		self.h = h
		self.snpshots   = snpshots
		
	@property
	def isSelected(self):
		if(self.selected is None):
			return False
		return True


class SumReader:
	"""
	Iterator for reading a haplotype-summary file line-by-line.
	Every line of a sync file is represented as  
	"""
	def __init__(self,file):
		if(isinstance(file,str)):
			self.__filehandle=open(file,"r")
		else:
			self.__filehandle=file


	def __iter__(self):
		return self
	
	def next(self):
		line=""
		# 3R      13094319        A       A/G     G:-0.1:0.5              250:250 216:284 287:213 407:93  422:78  464:36  469:31  482:18  481:19  492:8   492:8
		while(1):
			line=self.__filehandle.readline()
			if line=="":
				raise StopIteration
			line=line.rstrip('\n')
			if line != "":
				break
		
		a     = line.split()
		chr   = a.pop(0)
		pos   = int(a.pop(0))
		refc  = a.pop(0)
		b     = a.pop(0)
		majChar,minChar=b.split("/")
		c     = a.pop(0)
		selected=None
		s=0.0
		h=0.0
		if(c !="."):
			d=c.split(":")
			selected = d[0]
			s        = float(d[1])
			h        = float(d[2])
		
		snpshots=[]
		for p in a:
			e = p.split(":")
			majCount=int(e[0])
			minCount=int(e[1])
			t=SNPshot(majCount,minCount)
			snpshots.append(t)
		# (self,chromosome, position, refChar, majorAllele,minorAllele, selected,s,h,snpshots):
		return SNPSuccession(chr,pos,refc,majChar,minChar,selected,s,h,snpshots)
		
	

