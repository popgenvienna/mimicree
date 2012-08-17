import sys
import random
import collections
import os
from optparse import OptionParser, OptionGroup
import re


def get_key(chr,pos):
	return str(chr)+":"+str(pos)

def get_selected_snps(file):
	posset=set({})
	for line in open(file):
		line=line.rstrip()
		a=line.split("\t")
		key=get_key(a[0],a[1])
		posset.add(key)
	return posset

parser = OptionParser()
parser.add_option("--cmh-file", 	dest="cmhfile",help="The cmh test file")
parser.add_option("--additive-file",dest="additivefile",help="The number of SNPs which should be randomly drawn")
parser.add_option("--top-n",dest="topn",help="Consider the top n candidates; Mandatory")
parser.add_option("--output-prefix",dest="outputprefix",help="The prefix for the output files")
(options, args) = parser.parse_args()


topn=int(options.topn)
outprefix=options.outputprefix
outSelected=outprefix+".selected"
outNotselected=outprefix+".notselected"
ofhs=open(outSelected,"w")
ofhns=open(outNotselected,"w")
posset=get_selected_snps(options.additivefile)
counter=0


for line in open(options.cmhfile):
	if counter>=topn:
		break
	line=line.rstrip()
	a=line.split("\t")
	key=get_key(a[0],a[1])

	if(key in posset):
		ofhs.write(line+"\n")
	else:
		ofhns.write(line+"\n")
	counter+=1;

	