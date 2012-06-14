import sys
import random
import collections
from Sync import SyncReader
from Utility import File
from Haplotype import HaplotypeIO
from optparse import OptionParser, OptionGroup
import re



# MimicrEE package
# Author: Dr. Robert Kofler


class RandSelGen:
	def __init__(self,smin,smax):
		min=1/(-1*smin)
		max=1/(-1*smax)
		range=max-min
		self.__shift=min
		self.__range=range
	
	def getRandSel(self):
		r=(random.random()*self.__range)+self.__shift
		ps=1/r
		return -1*ps
		

parser = OptionParser()
parser.add_option("--haplotype-file",	dest="haplotype",help="The haplotype file")
parser.add_option("--s-min",		dest="smin", help="the minimum negative selection coefficient (eg: -0.5)")
parser.add_option("--s-max",		dest="smax",  help="the maximum negative selection coefficient (eg: -0.1)")
parser.add_option("--h",		dest="h", help="the heterozygous effect")
parser.add_option("--w11-char",		dest="w11char", help="the neutral character (the other allele is selected)")
parser.add_option("--snp-count", 	dest="snpnumber",help="The number of SNPs for which random additive effects should be generated")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()
haplotypefile=options.haplotype
output = options.output
snpcount=int(options.snpnumber)
heteffect=float(options.h)
smin=float(options.smin)
smax=float(options.smax)
if(smin>=0 or smax >=0):
	raise Exception("Invalid selection coefficients need to be negative")
w11char=options.w11char

rand=RandSelGen(smin,smax)

randgentypes=HaplotypeIO.parseLines(File.randomly_draw_lines(haplotypefile,snpcount))

assert(len(randgentypes)==snpcount)
ofh=open(output,"w")

for gt in randgentypes:
	topr=[]
	topr.append(gt.chrom)
	topr.append(str(gt.position))
	topr.append(w11char)
	topr.append(str(rand.getRandSel))
	topr.append(str(heteffect))
	ofh.write("\t".join(topr)+"\n")
	
