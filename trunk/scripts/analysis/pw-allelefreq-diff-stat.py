#!/usr/bin/env python
import sys
import collections
from optparse import OptionParser, OptionGroup
import copy
import math
from HapSum import SumReader

def parse_tocompare(tocompare):
	t1,t2 = tocompare.split("-") # split, convert to integer and swap if necessary
	t1,t2= (int(t1),int(t2))
	if(t1==0 or t2==0):
		raise ValueError("")
	if(t1>t2):
		t1,t2=(t2,t1)
	return (t1,t2)


#Author: Robert Kofler
parser = OptionParser()
parser.add_option("--sum", dest="sum", help="the sync file")
parser.add_option("--to-compare",dest="tocompare",help="the index of the two populations that should be compared eg.: 1-11")
parser.add_option("--test",action="store_true", dest="test",help="run the doctest")
(options, args) = parser.parse_args()

i1,i2=parse_tocompare(options.tocompare)

for s in SumReader(options.sum):
	ss=s.snapshots
	sample1,sample2=(ss[i1-1],ss[i2-1])
	
	
