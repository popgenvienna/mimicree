import sys
import random
import collections
from PyModules.Sync import SyncReader
from optparse import OptionParser, OptionGroup
import re

# MimicrEE package
# Author: Dr. Robert Kofler

parser = OptionParser()
parser.add_option("--haplotypes",dest="haplotype",help="The haplotype file")
parser.add_option("--s",dest="s", help="the selection coefficient s")
parser.add_option("--h",dest="h", help="the heterozygous effect")
parser.add_option("--snp-count", dest="snp-number",help="The number of SNPs for which random additive effects should be generated")
parser.add_option("--output",dest="output",help="the output file")
(options, args) = parser.parse_args()
output = options.output
popsize=int(options.popsize)