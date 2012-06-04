#!/usr/bin/env python
import sys
import collections
from optparse import OptionParser, OptionGroup
import copy
import math
from HapSum import SumReader

#Author: Robert Kofler
parser = OptionParser()
parser.add_option("--sum", dest="sum", help="the sync file")
parser.add_option("--test",action="store_true", dest="test",help="run the doctest")
(options, args) = parser.parse_args()


stat=collections.defaultdict(lambda : {'selected':0, 'nonselected':0})
# popNumber -> selected/noneselected: 0 
for s in SumReader(options.sum):
	selkey="nonselected"
	if(s.isSelected):
		selkey="selected"
	
	snpshots=s.snpshots
	for i,ss in enumerate(snpshots):
		if(ss.isFixed):
			stat[i][selkey]+=1


keys=sorted(stat.keys())


for k in keys:
	t=stat[k]
	cS=t["selected"]
	cNS=t["nonselected"]
	print("{0}\t{1}\t{2}".format(k,cS,cNS))
	
		
	
