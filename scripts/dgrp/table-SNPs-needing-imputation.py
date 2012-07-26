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


# needing-imputation
countBiallelic=0;
countNeedsImputation=0;
sheitCol=collections.defaultdict(lambda:0)

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
	countBiallelic+=1
	if(countSheit>0):
		countNeedsImputation+=1
		sheitCol[countSheit]+=1

print "Biallelic SNPs\t{0}".format(countBiallelic)
print "Max needing imputation\t{0}".format(countNeedsImputation)

snpNeedingImpuCount=0
genoNeedingImpuCount=0
for sheitNumber in sorted(sheitCol.keys()):
	count=sheitCol[sheitNumber]
	snpNeedingImpuCount+=count
	genoNeedingImpuCount+=sheitNumber*count
	print "{0}\t{1}\t{2}\t{3}".format(sheitNumber,count,snpNeedingImpuCount,genoNeedingImpuCount)



	
	


