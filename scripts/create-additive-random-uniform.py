import sys
import random
import collections
from PyModules.Sync import SyncReader
from PyModules.Utility import File
from PyModules.Haplotype import HaplotypeIO
from optparse import OptionParser, OptionGroup
import re

# MimicrEE package
# Author: Dr. Robert Kofler

parser = OptionParser()
parser.add_option("--haplotype-file",	dest="haplotype",help="The haplotype file")
parser.add_option("--s",		dest="s", help="the selection coefficient s")
parser.add_option("--h",		dest="h", help="the heterozygous effect")
parser.add_option("--snp-count", 	dest="snpnumber",help="The number of SNPs for which random additive effects should be generated")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()
haplotypefile=options.haplotype
output = options.output
snpcount=int(options.snpnumber)
heteffect=float(options.h)
selcoefficient=float(options.s)



randgentypes=HaplotypeIO.parseLines(File.randomly_draw_lines(haplotypefile,snpcount))
assert(len(randgentypes)==snpcount)
ofh=open(output,"w")

for gt in randgentypes:
	topr=[]
	topr.append(gt.chrom)
	topr.append(str(gt.position))
	if(random.random()<0.5):
		topr.append(gt.major)
	else:
		topr.append(gt.minor)
	topr.append(str(selcoefficient))
	topr.append(str(heteffect))
	ofh.write("\t".join(topr)+"\n")
