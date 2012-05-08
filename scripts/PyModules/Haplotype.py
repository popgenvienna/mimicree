#!/usr/bin/env python

class PopulationGenotype:
	"""
	Represents a line in a haplotype file, which corresponds to the genotypes of a whole population
	X       9121094 T       T/C     TC CT CC CT CT CC TT CT TT TC CC CC CC CT TT
	"""
	def __init__(self,chrom,position,refchar,major,minor,genotypes):
		self.chrom=chrom
		self.position=position
		self.refchar=refchar
		self.major=major
		self.minor=minor
		self.__genotypes=genotypes


class HaplotypeIO:
	
	@classmethod
	def parseLine(cls,line):
		a=line.split("\t");
		(major,minor)=a[3].split("/")
		genotypes=a[4].split(" ")
		return PopulationGenotype(a[0],a[1],a[2],major,minor,genotypes)
	
	@classmethod
	def parseLines(cls,lines):
		parsed=[]
		for line in lines:
			parsed.append(HaplotypeIO.parseLine(line))
		return parsed
	