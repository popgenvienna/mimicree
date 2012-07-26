#!/usr/bin/env python
import sys
import collections
import math
import re
from optparse import OptionParser, OptionGroup

# X	207	C	NCNNCNNCNNCNNCCCCCNNNNNNNNNNCNCCNNCNNNNNNCNNNCNNNCNNNNCNNCCCNNNNCANNNCNNNNNCNNNNNCNCNNNCNNNNNNNNNNNNNNNNNCCCNNNNNNNCNNCNNNCNNNNCCNNNNCNCNCNNNCNNNNCNCNCNNCCNNC
# X	208	A	NANNANNANNANNAAGANNNNNNNNNNNANAANNANNNNNNANNNANANAANANANNAAANNNNAANNNANNNNNANANNNANANNNANNNNNNNNANNNNNNNNAAANNNNNNNANNANNNANNNNAANNNNNNANANNNANNNNANANANNNANNA
# X	221	G	NGNNANNGNNGGNGGGGNNGNNGNNNNNGNGGNNGGNNNTNGNNNGNGNNGGGNGNNNGGNNNNGGNNNGNNNNNGGGNNNGNGNGNGNNNNNGNNGGNNNNNNNGGGNNNNNNGGNNGNNNNNNNNGGNNNNGNGNGNNNGGNNNGGGNGNNGGNNG

# Author: Dr. Robert Kofler
#
# Description
# Creates an 'N' -allele frequency spectrum for dgrp haplotype table
# This is useful to decide which SNPs may be used and which ones not
# Note: the script treats everything not ATCG as N (including M and other nonsenese)!

parser = OptionParser()
parser.add_option("--input",dest="input",help="A file containing a haplotype table")
(options, args) = parser.parse_args()

stat=collections.defaultdict(lambda:0)

for line in open(options.input):
	line=line.rstrip('\n')
	a=line.split('\t')
	for c in a[3]:
		stat[c]+=1


for char,count in stat.items():
	print "{0}\t{1}".format(char,count) 
