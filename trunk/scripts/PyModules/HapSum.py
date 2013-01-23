#!/usr/bin/env python

import sys
import collections
import math
import re


class SNPshot:
	"""
	Short for SNP snapshot :)
	Represents the ancestral and derived counts of a SNP at a single time point;
	Immutable container type
	"""
	def __init__(self,ancestral,derived):
		coverage=ancestral+derived
		if(coverage==0):
			raise ValueError("Coverage must be larger than zero")
		self.__ancestral=ancestral
		self.__derived=derived
		self.__coverage=coverage
	
	@property
	def ancestral(self):
		return self.__ancestral
	
	@property
	def derived(self):
		return self.__derived
	
	@property
	def coverage(self):
		return self.__coverage
	
	@property
	def ancestralFrequency(self):
		return float(self.__ancestral)/float(self.__coverage)
	
	@property
	def derivedFrequency(self):
		return float(self.__derived)/float(self.__coverage)
		
	"""
	def selectedFrequency(self,snpsuc):
		if(not snpsuc.isSelected):
			return None
		if(snpsuc.snp().isMajorSelected):
			return self.majorFrequency
		else:
			return self.minorFrequency
		raise Exception("not implemented")
		return None
	
	def isSelectedFixedCorrectly(self,snpsuc):
		if(not snpsuc.isSelected):
			raise Error("SNP is not selected")
		if(not self.isFixed):
			raise Error("SNP is not fixed")
		if(snpsuc.snp().isMajorSelected):
			return self.isMajorFixed
		else:
			return self.isMinorFixed
	"""		

	@property
	def isFixed(self):
		if(self.__major == 0 or self.__minor==0):
			return True
		return False	
	
	@property
	def isAncestralFixed(self):
		if(self.__ancestral==self.__coverage):
			return True
		return False
	
	@property
	def isDerivedFixed(self):
		if(self.__ancestral==self.__coverage):
			return True
		return False


class SNPSuccession:
	def __init__(self,snp,snpshots):
		self.__snp=snp
		self.__snpshots=snpshots
		

	
	@property
	def isSelected(self):
		return self.__snp.isSelected
	
	def snp(self):
		return self.__snp
	
	@property
	def snpshots(self):
		return self.__snpshots
	
	@property
	def countSnpshots(self):
		return len(self.__snpshots)
	
	def freqOfSelected(self,index):
		if(not self.__snp.isSelected):
			return None
		act=self.__snpshots[index]
		if(self.__snp.isAncestralSelected):
			return act.ancestralFrequency
		else:
			return act.derivedFrequency
	
	
	


class SNP:
	
	#3R      13094319        A       A/G     G:-0.1:0.5              250:250 216:284 287:213 407:93  422:78  464:36  469:31  482:18  481:19  492:8   492:8
	def __init__(self,chromosome, position, refChar, majorAllele,minorAllele, isSelected, isMajorSelected,comment):
		self.chr = chromosome
		self.pos   = position
		self.refChar    = refChar
		self.majorAllele= majorAllele
		self.minorAllele= minorAllele
		self.isMajorSelected=isMajorSelected
		self.isSelected=isSelected
		self.comment=comment




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

	@classmethod
	def parseLines(cls,lines):
		return [SumReader.parseLine(l) for l in lines]

	@classmethod
	def parseLine(cls,line):
		a     = line.split()
		chr   = a.pop(0)
		pos   = int(a.pop(0))
		refc  = a.pop(0)
		b     = a.pop(0)
		ancestralChar,derivedChar=b.split("/")
		rawComment     = a.pop(0)
		comParse=CommentParser(rawComment,majChar)
		comment=comParse.comment
		isSel=comParse.isSelected
		isAncestralSel=comParse.isAncestralSelected
		# def __init__(self,chromosome, position, refChar, majorAllele,minorAllele, isSelected, isMajorSelected,comment):
		snp=SNP(chr,pos,refc,ancestralChar,derivedChar,isSel,isAncestralSel,comment)
		
		snpshots=[]
		for p in a:
			e = p.split(":")
			ancCount=int(e[0])
			derCount=int(e[1])
			t=SNPshot(ancCount,derCount)
			snpshots.append(t)
		# (self,chromosome, position, refChar, majorAllele,minorAllele, selected,s,h,snpshots):
		return SNPSuccession(snp,snpshots)
	
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
		return self.parseLine(line)
	

class CommentParser:
	def __init__(self,raw,ancestralAllele):
		self.__raw=raw
		self.__ancestralAllele=ancestralAllele
	
	@property
	def comment(self):
		return self.__raw
	
	@property
	def isSelected(self):
		if(self.__raw=="."):
			return False
		else:
			return True
	
	@property
	def isAncestralSelected(self):
		if(not self.isSelected):
			return None
		if(";" in self.__raw):
			raise Exception("multiple selections acting on a single SNP are not supported")
		if(self.__raw.startswith("A=")):
			return self.__parseAdditive()
		elif(self.__raw.startswith("E=")):
			return self.__parseEpistasis()
		else:
			raise Exception("Effect not supported")
			
	
	def __parseEpistasis(self):
		"""
		E=T:name:s
		"""
		raw=re.sub(r'E=','',self.__raw)
		a=raw.split(':')
		eChar=a[0]
		s=float(a[2])
		ancChar=self.__ancestralAllele
		if(ancChar==eChar):
			if(s<0.0):
				# A/G A:-0.1 	A=1.1	G=1.0
				return False
			else:
				# A/G A:0.1	A=0.9  G=1.0
				return True
		else:
			if(s<0.0):
				return True
			else:
				return False
			

	def __parseAdditive(self):
		"""
		Is the ancestral allele selected
		A=T:s:h
		"""
		raw=re.sub(r'A=','',self.__raw)
		a=raw.split(':')
		w11=a[0]
		s=float(a[1])
		ancChar=self.__ancestralAllele
		
		if(majChar==w11):
			if(s < 0.0):
				# A/G   A:-.1 -> A=1.0 G=0.9
				return True
			else:
				# A/G  A:0.1 -> A=1.0 G=1.1
				return False
		else:
			if(s < 0.0):
				# A/G G:-.1 -> G=1.0 A=0.9
				return False
			else:
				return True


