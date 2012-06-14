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

parser = OptionParser()
parser.add_option("--haplotype-file",	dest="haplotype",help="The haplotype file")
parser.add_option("--s",		dest="s", help="the selection coefficient s")
parser.add_option("--h",		dest="h", help="the heterozygous effect")
parser.add_option("--w11-char",		dest="w11char", help="the character not being selected")
parser.add_option("--snp-count", 	dest="snpnumber",help="The number of SNPs for which random additive effects should be generated")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()
haplotypefile=options.haplotype
output = options.output
snpcount=int(options.snpnumber)
heteffect=float(options.h)
selcoefficient=float(options.s)
w11char=options.w11char


randgentypes=HaplotypeIO.parseLines(File.randomly_draw_lines(haplotypefile,snpcount))
assert(len(randgentypes)==snpcount)
ofh=open(output,"w")

for gt in randgentypes:
	topr=[]
	topr.append(gt.chrom)
	topr.append(str(gt.position))
	topr.append(w11char)
	topr.append(str(selcoefficient))
	topr.append(str(heteffect))
	ofh.write("\t".join(topr)+"\n")
	
