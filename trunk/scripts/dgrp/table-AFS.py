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

allele_number_stat=collections.defaultdict(lambda:0)
# bi-allelic SNP minor allele count
bamac=collections.defaultdict(lambda:0)

for line in open(options.input):
	line=line.rstrip('\n')
	a=line.split('\t')
	counta=collections.defaultdict(lambda:0)
	for c in a[3]:
		if(c=='A' or c=='T' or c=='G' or c=='C'):
			counta[c]+=1
	allele_number=len(counta)
	allele_number_stat[allele_number]+=1
	
	if(allele_number==2):
		tmp=counta.items()
		tmps=sorted(tmp,key=lambda a: a[1])
		# minor allele count is the first
		mac=tmps[0][1]
		bamac[mac]+=1

print "SNP allele count"
for an,count in allele_number_stat.items():
	print "{0}\t{1}".format(an,count)
	
print "minor allele count"
for mac in sorted(bamac.keys()):
	count=bamac[mac]
	print "{0}\t{1}".format(mac,count)
	
	


