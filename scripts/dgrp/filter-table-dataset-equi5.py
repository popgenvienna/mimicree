#!/usr/bin/env python
import sys
import collections
import math
import re
import random
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
	
	gtList=list(a[3])
	for c in gtList:
		if(c=='A' or c=='T' or c=='G' or c=='C'):
			counta[c]+=1
		else:
			countSheit+=1
	allele_number=len(counta)
	
	# discard SNPs with less than two alleles or more than two alleles
	if(allele_number!=2):
		continue
	# discard SNPs with more than 5 sheit characters
	if(countSheit>5):
		continue
	
	if(countSheit>0):
		# sheit count is between 0 and 5 -> needs ammendment
		allele1=counta.keys()[0]
		allele2=counta.keys()[1]
		count1=counta[allele1]
		count2=counta[allele2]
		threshold=float(count1)/float(count1+count2)
		
		# search and replace the sheit
		for i in range(0,len(gtList)):
			alchar=gtList[i]
			if(alchar!='A' and alchar!='T' and alchar!='G' and alchar!='C'):
				rand=random.random()
				novelgenotype=""
				if(rand<threshold):
					novelgenotype=allele1
				else:
					novelgenotype=allele2
				gtList[i]=novelgenotype
		# update the to print information
		a[3]=''.join(gtList)
	toprint="\t".join(a)
	print toprint




	
	


