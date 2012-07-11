import sys
import random
import collections
from optparse import OptionParser, OptionGroup
#import rpy2.robjects as robjects
#from rpy2.robjects.packages import importr


# MimicrEE package
# Author: Dr. Robert Kofler

class AlleleFreqGen:
	def __init__(self,majA,minA,majCount,minCount):
		self.majA=majA
		self.minA=minA
		self.majCount=majCount
		self.minCount=minCount
		self.constAr=[majA for i in range(0,majCount)]+ [minA for i in range(0,minCount)]
	def getContAr(self):
		return list(self.constAr)
		
		

parser = OptionParser()
parser.add_option("--output",dest="output",help="A set of random haplotyes which may be used for analysis with MimicrEE")
parser.add_option("--population-size",dest="popsize",help="The size of the population")
parser.add_option("--loci-count",dest="locicount",help="Loci count")
# parser.add_option("--minor-allele-freq",dest="minalfreq",help="The allele frequency of the minor allele")

minorAllele='G'
majorAllele='A'
minalfreq=0.5

(options, args) = parser.parse_args()
output= options.output
locicount=int(options.locicount)

popsize=int(options.popsize)
hapcount=popsize*2

if(minalfreq < 0 or minalfreq >0.5):
	raise Exception("minimum allelefrequency invalid")
	
minCount=int(minalfreq*hapcount);
majCount=hapcount-minCount
afg= AlleleFreqGen(majorAllele,minorAllele,majCount,minCount)



ofh=open(output,"w")
for i in range(1,locicount+1):
	topr=[]
	topr.append("chr"+str(i))
	topr.append("1")
	topr.append(majorAllele);
	topr.append(majorAllele+"/"+minorAllele)
	
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
