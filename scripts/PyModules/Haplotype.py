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
	
	@property
	def genotypes(self):
		return self.__genotypes
	
	@property
	def genotypeCount(self):
		return len(self.genotypes)


class HaplotypeIO:
	
	@classmethod
	def genotypeCount(cls,inputFile):
		fh=open(inputFile)
		line=fh.next()
		pa=HaplotypeIO.parseLine(line)
		return pa.genotypeCount
	
	@classmethod
	def formatEntry(cls,entry):
		gt=entry.genotypes
		tof=[]
		tof.append(entry.chrom)
		tof.append(str(entry.position))
		tof.append(entry.refchar)
		tof.append(entry.major+"/"+entry.minor)
		tof.append(" ".join(gt))
		form="\t".join(tof)
		return form
	
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
	