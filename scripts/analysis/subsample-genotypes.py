#!/usr/bin/env python
import sys
import collections
from optparse import OptionParser, OptionGroup
import copy
import math
import random
from Haplotype import *

def createRandomIndices(fullSize,subSize):
	toret=[int(random.random()*fullSize) for i in range(0,subSize)]
	return toret




#Author: Dr. Robert Kofler
parser = OptionParser()
parser.add_option("--haplotypes", dest="haplotypes", help="the haplotype file")
parser.add_option("--sample-size", dest="samplesize",help="the size of the subsample in diploid individuals")
(options, args) = parser.parse_args()

subsamplesize=int(options.samplesize)
fullsamplesize=HaplotypeIO.genotypeCount(options.haplotypes)
if(subsamplesize>=fullsamplesize):
	raise Exception("subsamplesize nees to be smaller than the number of populations")
	
randindex=createRandomIndices(fullsamplesize,subsamplesize)

for line in open(options.haplotypes):
	line=line.rstrip()
	p=HaplotypeIO.parseLine(line)
	gts=p.genotypes
	novelgts=[gts[i] for i in randindex]
	novelp=PopulationGenotype(p.chrom, p.position, p.refchar, p.major, p.minor, novelgts) # (self,chrom,position,refchar,major,minor,genotypes):
	topr=HaplotypeIO.formatEntry(novelp)
	print topr
	
