#!/usr/bin/env python

import sys
import collections
import math


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
	
	@property
	def isMajorFixed(self):
		if(self.__major==self.__coverage):
			return True
		return False
	
	@property
	def isMinorFixed(self):
		if(self.__minor==self.__coverage):
			return True
		return False



class SNPSuccession:
	
	#3R      13094319        A       A/G     G:-0.1:0.5              250:250 216:284 287:213 407:93  422:78  464:36  469:31  482:18  481:19  492:8   492:8
	def __init__(self,chromosome, position, refChar, majorAllele,minorAllele, w11,s,h,snpshots):
		self.chr = chromosome
		self.pos   = position
		self.refChar    = refChar
		self.majorAllele= majorAllele
		self.minorAllele= minorAllele
		self.w11   = w11
		self.s = s
		self.h = h
		self.snpshots   = snpshots
		
	@property
	def isSelected(self):
		if(self.w11 is None):
			return False
		return True
	

	@property
	def posSelectedAllele(self):
		"""
		Return the positively selected allele
		"""
		if(not self.isSelected or math.fabs(self.s)<0.00000000001):
			return None
		if(self.majorAllele==self.w11):
			if(self.s < 0.0):
				# A/G   A:-.1 -> A=1.0 G=1.1
				return self.minorAllele
			else:
				# A/G  A:0.1 -> A=1.0 G=0.9
				return self.majorAllele
		elif(self.minorAllele==self.w11):
			if(self.s < 0.0):
				# A/G G:-.1 -> G=1.0 A=1.1
				return  self.majorAllele
			else:
				return self.minorAllele
		else:
			raise ValueError("impossible state")
	
	@property
	def isMajorSelected(self):
		posAllele=self.posSelectedAllele
		if(posAllele==self.majorAllele):
			return True
		return False


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
		
	

