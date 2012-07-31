import sys
import collections
import math
import re
import random
from optparse import OptionParser, OptionGroup

class SelectedCandidate:
	def __init__(self,chr,pos,w11):
		self.chr=chr
		self.pos=pos
		self.w11=w11

def parse_line(line):
	"""
	2L      686891    G      G/A    AG GG GG GG GG
	2L      936681    A      A/G    GG AA AA AA AA
	2L      861026    T      A/T    TT AT AA AA TT
	"""
	a=line.split("\t")
	chr=a[0]
	pos=a[1]
	major,minor=a[3].split("/")
	b=a[4].split(" ")
	nucs=[]
	for i in b:
		nucs.extend(list(i))
	mincount=0
	totcount=0
	for n in nucs:
		totcount+=1
		if(n==minor):
			mincount+=1

	minfreq=float(mincount)/float(totcount)	
	return (chr,pos,major,minfreq)


parser = OptionParser()
parser.add_option("--input",dest="input",help="A file containing the dgrp haplotypes (MimicrEE input)")
parser.add_option("-n","--number-selected",dest="numsel",help="Number of selected SNPs")
parser.add_option("-e","--heterozygous",dest="het",help="Heterozygous effect")
parser.add_option("-s","--selection",dest="selcoef",help="Selection coefficient")
parser.add_option("-f","--min-freq",dest="minfreq",help="the minimum minor allele frequency")
(options, args) = parser.parse_args()

numsel=int(options.numsel)
h=float(options.het)
s=float(options.selcoef)
f=float(options.minfreq)

cand=[]

for line in open(options.input):
	line=line.rstrip()
	(chr,pos,w11,minorfreq)=parse_line(line)
	if(minorfreq<f):
		continue
	cand.append(SelectedCandidate(chr,pos,w11))

if(len(cand)<numsel):
	raise ValueError("not enough SNPs with a minor allele frequency larger than the provided value")

toprint=[]
while(len(toprint)<numsel):
	r=int(random.random()*len(cand))
	toprint.append(cand.pop(r))
	

for t in toprint:
	o=[]
	o.append(t.chr)
	o.append(t.pos)
	o.append(t.w11)
	o.append(str(s))
	o.append(str(h))
	tmp="\t".join(o)
	print tmp