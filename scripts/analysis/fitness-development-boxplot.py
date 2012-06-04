import sys
import random
import collections
from optparse import OptionParser, OptionGroup
import re

# MimicrEE package
# Author: Dr. Robert Kofler

parser = OptionParser()
parser.add_option("-f",	dest="fitness", action="append", help="The fitness file")
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