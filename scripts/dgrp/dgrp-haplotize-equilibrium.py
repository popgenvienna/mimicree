import sys
import collections
import math
import re
import random
from optparse import OptionParser, OptionGroup



def get_randar(majorallele,minorallel,majorcount,minorcount,popsize):
	if((majorcount+minorcount)/2 > popsize):
		raise Error("DGRP population size must be larger than 2*populationsize ")
	basicar= [majorallele for i in range(0,majorcount)] + [minorallele for i in range(0,minorcount)]
	todraw=list(basicar)
	toret=[]
	while(len(toret) < 2*popsize):
		if(len(todraw)==0):
			todraw=list(basicar)
		r=int(random.random()*len(todraw))
		toret.append(todraw.pop(r))
	return toret
	
	

def get_allele_array(line):
	
	stat=collections.defaultdict(lambda:0)
	for n in list(line):
		stat[n]+=1
	tosort=stat.items()
	if(len(tosort)!=2):
		raise Error("A SNP in the DGRP table must have exactly two alleles")
	tosort=sorted(tosort,key=lambda a: -a[1])
	# majoralle, minorallele, majorcount, minorcount
	return (tosort[0][0],tosort[1][0],tosort[0][1],tosort[1][1])	
	
	
### INPUT
# X	207	C	NCNNCNNCNNCNNCCCCCNNNNNNNNNNCNCCNNCNNNNNNCNNNCNNNCNNNNCNNCCCNNNNCANNNCNNNNNCNNNNNCNCNNNCNNNNNNNNNNNNNNNNNCCCNNNNNNNCNNCNNNCNNNNCCNNNNCNCNCNNNCNNNNCNCNCNNCCNNC
# X	208	A	NANNANNANNANNAAGANNNNNNNNNNNANAANNANNNNNNANNNANANAANANANNAAANNNNAANNNANNNNNANANNNANANNNANNNNNNNNANNNNNNNNAAANNNNNNNANNANNNANNNNAANNNNNNANANNNANNNNANANANNNANNA
# X	221	G	NGNNANNGNNGGNGGGGNNGNNGNNNNNGNGGNNGGNNNTNGNNNGNGNNGGGNGNNNGGNNNNGGNNNGNNNNNGGGNNNGNGNGNGNNNNNGNNGGNNNNNNNGGGNNNNNNGGNNGNNNNNNNNGGNNNNGNGNGNNNGGNNNGGGNGNNGGNNG


### OUTPUT
# 2L      686891    G      G/A    AG GG GG GG GG
# 2L      936681    A      A/G    GG AA AA AA AA
# 2L      861026    T      A/T    TT AT AA AA TT
# 2L      966618    C      T/C    TC TC TT TT TT
# 2R      134298    A      A/C    AC AC CC CC CC


# Author: Dr. Robert Kofler
#
# Description
# Creates an 'N' -allele frequency spectrum for dgrp haplotype table
# This is useful to decide which SNPs may be used and which ones not
# Note: the script treats everything not ATCG as N (including M and other nonsenese)!

dgrpsize=158
parser = OptionParser()
parser.add_option("--input",dest="input",help="A file containing a haplotype table")
parser.add_option("--population-size",dest="popsize", help="The size of the population to build")
(options, args) = parser.parse_args()
popsize=int(options.popsize)


for line in open(options.input):
	line=line.rstrip('\n')
	a=line.split('\t')
	(majorallele,minorallele,majorcount,minorcount)=get_allele_array(a[3])
	randar=get_randar(majorallele,minorallele,majorcount,minorcount,popsize)
	
	diploids=[]
	while(len(randar)>0):
		diploids.append(randar.pop(0)+randar.pop(0))
		
	topr=[]
	topr.append(a[0])
	topr.append(a[1])
	topr.append(a[2])
	topr.append(majorallele+"/"+minorallele)
	topr.append(" ".join(diploids))
	strf="\t".join(topr)
	print strf
		
