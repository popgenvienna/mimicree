import sys
import random
import collections
import os
from optparse import OptionParser, OptionGroup
import re
from RInterface import *
from HapSum import SumReader

deviationfactor=0.0001

def slightly_deviate(todeviate):
	dev=[]
	for i,d in enumerate(todeviate):
		changed=0.0
		if(i%2==0):
			changed=d-deviationfactor
		else:
			changed=d+deviationfactor
		dev.append(changed)
	return dev

##
def format_vioplotdata(toproces):
	components=[]
	for ar in toprocess:
		formar=RFormat.get_vector(ar)
		components.append(formar)
	toret=",".join(components)
	return toret
	


def format_rfile(rFile,outputFile,td):
	# first write the data to a R file
	data=format_vioplotdata(td)
	
	rfh=open(rFile,"w")
	towrite="""
	require(vioplot)
	pdf("{0}",width=15)
	vioplot({1}, drawRect=FALSE, col="bisque",ylim=c(0.0,1.0), h=0.01)
	dev.off()
	""".format(outputFile,data)
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
	for i in range(0,hs.countSnpshots):
		stat[i].append(hs.freqOfSelected(i))

toprocess=[]
for i in sorted(stat.keys()):
	ar=stat[i]
	toprocess.append(ar)
	
# well well...correction for fucking R bug in violinplot; having 10.000 values with totally the same value are not allowed (strangely 100 are allowed)
toprocess[0]=slightly_deviate(toprocess[0])


format_rfile(tempr,outputFile,toprocess)
RUtility.run(tempr)