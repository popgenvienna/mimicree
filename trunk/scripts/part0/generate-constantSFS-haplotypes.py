import sys
import random
import collections
from optparse import OptionParser, OptionGroup
#import rpy2.robjects as robjects
#from rpy2.robjects.packages import importr


# MimicrEE package
# Author: Dr. Robert Kofler


parser = OptionParser()
parser.add_option("--input", dest="input", help="A synchronized file containging allele counts for a SINGLE populations")
parser.add_option("--output",dest="output",help="A set of random haplotyes which may be used for analysis with MimicrEE")
parser.add_option("--population-size",dest="popsize",help="The size of the population")
parser.add_option("--minor-allele-freq",dest="minalfreq",help="The allele frequency of the minor allele")


(options, args) = parser.parse_args()
input = options.input
output= options.output
popsize=int(options.popsize)
minalfreq=float(options.minalfreq)
if(minalfreq < 0 or minalfreq >0.5):
	raise Exception("minimum allelefrequency invalid")
minorAllele='A'
majorAllele='G'

ofh=open(output,"w")
for line in open(input):
	line=line.rstrip()
	a=line.split('\t')
	topr=[]
	topr.append(a[0])
	topr.append(a[1])
	topr.append(majorAllele);
	topr.append(majorAllele+"/"+minorAllele)
	genotypes=[]
	for i in range(0,popsize):
		gen=""
		for k in range(0,2):
			if(random.random() < minalfreq):
				gen+=minorAllele
			else:
				gen+=majorAllele
		genotypes.append(gen)
	topr.append(" ".join(genotypes))
	
	
	tostr="\t".join(topr)
	ofh.write(tostr+"\n")

