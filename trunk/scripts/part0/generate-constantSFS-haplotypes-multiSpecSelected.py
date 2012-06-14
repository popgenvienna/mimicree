import sys
import random
import collections
from optparse import OptionParser, OptionGroup
#import rpy2.robjects as robjects
#from rpy2.robjects.packages import importr


class AlleleFreqGen:
	def __init__(self,majA,minA,majCount,minCount):
		self.majA=majA
		self.minA=minA
		self.majCount=majCount
		self.minCount=minCount
		self.constAr=[majA for i in range(0,majCount)]+ [minA for i in range(0,minCount)]
	def getContAr(self):
		return list(self.constAr)
		
		


# MimicrEE package
# Author: Dr. Robert Kofler


parser = OptionParser()
parser.add_option("--input", dest="input", help="A synchronized file containging allele counts for a SINGLE populations")
parser.add_option("--output",dest="output",help="A set of random haplotyes which may be used for analysis with MimicrEE")
parser.add_option("--population-size",dest="popsize",help="The size of the population")
parser.add_option("--frequency-of-allele-A",dest="afreq",help="A coma separated list of allele frequencies of allele A")

secondaryAllele='G'
targetAllele='A'

(options, args) = parser.parse_args()
input = options.input
output= options.output
popsize=int(options.popsize)
hapcount=popsize*2

targetfreqs=[float(i) for i in options.afreq.split(',')]

afglist=[]
for tf in targetfreqs:
	if(tf < 0 or tf >1.0):
		raise Exception("Frequency of allele A is not valid")

	targetCount=int(tf*hapcount);
	secCount=hapcount-targetCount
	afg= AlleleFreqGen(targetAllele,secondaryAllele,targetCount,secCount)
	afglist.append(afg)


ofh=open(output,"w")
for line in open(input):
	line=line.rstrip()
	a=line.split('\t')
	topr=[]
	topr.append(a[0])
	topr.append(a[1])
	topr.append(targetAllele);
	topr.append(targetAllele+"/"+secondaryAllele)
	afg=afglist[int(random.random()*len(afglist))]
	
	ar=afg.getContAr()
	genotypes=[]
	for i in range(0,popsize):
		gen=""
		r1=int(random.random()*len(ar))
		gen+=ar.pop(r1)

		r2=int(random.random()*len(ar))
		gen+=ar.pop(r2)
		genotypes.append(gen)
	topr.append(" ".join(genotypes))

	tostr="\t".join(topr)
	ofh.write(tostr+"\n")

