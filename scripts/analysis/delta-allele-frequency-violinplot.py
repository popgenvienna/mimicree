import sys
import random
import collections
import os
from optparse import OptionParser, OptionGroup
import re
from RInterface import *
from HapSum import SumReader


# MimicrEE package
# Author: Dr. Robert Kofler


def format_vioplotdata(toproces):
	components=[]
	for ar in toprocess:
		formar=RFormat.get_vector(ar)
		components.append(formar)
	toret=",".join(components)
	return toret
	



def format_rfile(rFile,outputFile, toprocess):
	
	viodat=format_vioplotdata(toprocess)
	
	rfh=open(rFile,"w")
	towrite="""
	require(vioplot)
	pdf("{0}",width=15)
	vioplot({1},drawRect=FALSE,col="bisque",h=0.01)
	dev.off()
	""".format(outputFile,viodat)
	rfh.write(towrite)
	rfh.close()
	

parser = OptionParser()
parser.add_option("--sum", 	dest="sum",help="The summary file")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()

outputFile=os.path.abspath(options.output)
dir=os.path.dirname(outputFile)
tempr=os.path.join(dir,"temp.R")

stat=collections.defaultdict(lambda:[])
for hs in SumReader(options.sum):
	if not hs.isSelected:
		continue
	ismaj=hs.isMajorSelected
	ss=hs.snpshots
	base=ss[0]
	basefreq=0.0
	if(ismaj):
		basefreq=base.majorFrequency
	else:
		basefreq=base.minorFrequency
	
	for i in range(1,len(ss)):
		derived=ss[i]
		derfreq=0.0
		if(ismaj):
			derfreq=derived.majorFrequency
		else:
			derfreq=derived.minorFrequency
		delta=derfreq-basefreq
		stat[i].append(delta)
		
toprocess=[]
for i in sorted(stat.keys()):
	ar=stat[i]
	toprocess.append(ar)


format_rfile(tempr,outputFile,toprocess)
RUtility.run(tempr)