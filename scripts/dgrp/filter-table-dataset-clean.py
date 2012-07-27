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


for line in open(options.input):
	line=line.rstrip('\n')
	a=line.split('\t')
	counta=collections.defaultdict(lambda:0)
	countSheit=0
	for c in a[3]:
		if(c=='A' or c=='T' or c=='G' or c=='C'):
			counta[c]+=1
		else:
			countSheit+=1
	allele_number=len(counta)
	
	if(allele_number!=2):
		continue
	if(countSheit>0):
		continue
	print line




	
	


