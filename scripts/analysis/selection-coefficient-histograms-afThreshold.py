import sys
import random
import collections
import os
from optparse import OptionParser, OptionGroup
import re
from RInterface import *
from HapSum import SumReader

deviationfactor=0.0001



	
def format_rfile(rFile,outputFile,all,above,intermediate,below,ut,lt):
	mi=min(all)
	ma=max(all)
	
	allc=RFormat.get_vector(all)
	abovec=RFormat.get_vector(above)
	intermediatec=RFormat.get_vector(intermediate)
	belowc=RFormat.get_vector(below)

	rfh=open(rFile,"w")
	towrite="""
	pdf("{0}",width=15)
	par(mfrow=c(2,2))
	hist({1}, xlim=c({7},{8}), breaks=20, xlab="selection coefficient", main="all")
	hist({2}, xlim=c({7},{8}), breaks=20, xlab="selection coefficient", main="above th({5})")
	hist({3}, xlim=c({7},{8}), breaks=20, xlab="selection coefficient", main="intermediate")
	hist({4}, xlim=c({7},{8}), breaks=20, xlab="selection coefficient", main="below th({6})")
	dev.off()
	""".format(outputFile,allc,abovec,intermediatec,belowc,ut,lt,mi,ma)
	rfh.write(towrite)
	rfh.close()
	

parser = OptionParser()
parser.add_option("--sum", 	dest="sum",help="The summary file")
parser.add_option("--population-number",dest="popnum",help="The number of the population")
parser.add_option("--upper-threshold", dest="ut", help="upper threshold of the population")
parser.add_option("--lower-threshold", dest="lt", help="lower threshold of the population")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()

outputFile=os.path.abspath(options.output)
dir=os.path.dirname(outputFile)
tempr=os.path.join(dir,"temp.R")

popid=int(options.popnum)-1
ut=float(options.ut)
lt=float(options.lt)


all=[]
aboveTh=[]
intermediate=[]
belowTh=[]

for hs in SumReader(options.sum):
	if not hs.isSelected:
		continue
	ismaj=hs.isMajorSelected
	ss=hs.snpshots
	s=hs.s
	active=hs.snpshots[popid]
	
	freq=0.0
	if(ismaj):
		freq=active.majorFrequency
	else:
		freq=active.minorFrequency
	all.append(s)
	if(freq>=ut):
		aboveTh.append(s)
	elif(freq<=lt):
		belowTh.append(s)
	else:
		intermediate.append(s)


format_rfile(tempr,outputFile,all,aboveTh,intermediate,belowTh,ut,lt)
RUtility.run(tempr)