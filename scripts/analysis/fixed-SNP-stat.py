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


keyset=set([])
stat=collections.defaultdict(lambda : {'selected':0,'selcorrect':0, 'nonselected':0})
# popNumber -> selected/noneselected: 0 
for s in SumReader(options.sum):
	snpshots=s.snpshots
	selected=s.isSelected
	isMajorSelected=s.isMajorSelected
	for i,ss in enumerate(snpshots):
		keyset.add(i)
		if(ss.isFixed):
			if(selected):
				stat[i]['selected']+=1
				correct=False
				if(isMajorSelected):
					if(ss.isMajorFixed):
						correct=True
				else:
					if(ss.isMinorFixed):
						correct=True
				if(correct):
					stat[i]['selcorrect']+=1
			else:
				stat[i]['nonselected']


keys=sorted(list(keyset))


for k in keys:
	t=stat[k]
	cS=t["selected"]
	cScor=t['selcorrect']
	cNS=t["nonselected"]
	print("{0}\t{1}\t{2}\t{3}".format(k,cS,cScor,cNS))
	
		
	
