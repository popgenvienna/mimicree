import sys
import random
import collections
from PyModules.Sync import SyncReader
from optparse import OptionParser, OptionGroup
#import rpy2.robjects as robjects
#from rpy2.robjects.packages import importr


class Allele:
	def __init__(self,allele,count):
		self.allele=allele
		self.count=count

def get_two_allele_array(p):
	ar=[Allele('A',p.A), Allele('T',p.T), Allele('C',p.C), Allele('G',p.G)]
	ar=sorted(ar,key=lambda al: -al.count)
	if(ar[0].count==0 or ar[1].count==0):
		raise StandardError("Can only create haplotypes for SNPs! Encountered a entry which does not have two alleles (either A, T, C or G)")
	maj=ar[0]
	min=ar[1]
	basis=[maj.allele for i in range(0,maj.count)] + [min.allele for i in range(0,min.count)]
	return (maj.allele,min.allele,basis)
		

#Author: Robert Kofler
parser = OptionParser()
parser.add_option("--input", dest="input", help="A synchronized file containging allele counts for a SINGLE populations")
parser.add_option("--output",dest="output",help="A set of random haplotyes which may be used for analysis with MimicrEE")
parser.add_option("--population-size",dest="popsize",help="The size of the population")


(options, args) = parser.parse_args()
input = options.input
output= options.output
popsize=int(options.popsize)


ofh=open(output,"w")
for entry in SyncReader(input):
	pop=entry.populations[0]
	majall,minall,randbasis=get_two_allele_array(pop)
	arsize=len(randbasis)
	topr=[]
	topr.append(entry.chr)
	topr.append(str(entry.pos))
	topr.append(entry.refc)
	topr.append(majall+"/"+minall)
	genotypes=[]
	for i in range(0,popsize):
		r1=int(random.random()*arsize)
		r2=int(random.random()*arsize)
		genotype=randbasis[r1]+ randbasis[r2]
		genotypes.append(genotype)
	topr.append(" ".join(genotypes))
	tostr="\t".join(topr)
	ofh.write(tostr+"\n")
	
		
		

