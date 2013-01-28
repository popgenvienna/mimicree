#!/usr/bin/env python
import sys
import collections
from optparse import OptionParser, OptionGroup
import copy
import math
import random
from HapSum import SumReader
# Author: Dr. Robert Kofler
# VetMedUni Wien


def calculatePi(major,minor):
        if(major==0 or minor ==0):
                return 0
        c=major+minor
        co2=nover2(c)
        majo2=nover2(major)
        mino2=nover2(minor)
        pi=(co2-majo2-mino2)/co2
        return pi
        

def nover2(n):
        assert(n>0)
        return ((float)(n*(n-1)))/2.0;

chrsorting=("X","2L","2R","3L","3R")
chrinfo={'X':22422827,'2L':23011544,'2R':21146708,'3L':24543557,'3R':27905053}
stat=collections.defaultdict(lambda:collections.defaultdict(lambda:0)) # chromosome -> index
timestampcount=None


parser = OptionParser()
parser.add_option("--sum", dest="sumfile", help="the summary file")
(options, args) = parser.parse_args()


for hs in SumReader(options.sumfile):
        snp=hs.snp
        chr=snp.chr
        snpshots=hs.snpshots
        if(timestampcount is None):
                timestampcount=len(snpshots)
        for index,ss in enumerate(snpshots):
                (anc,der)=(ss.ancestral,ss.derived)
                pi=calculatePi(anc,der)
                stat[chr][index]+=pi

for chr in chrsorting:
        tpl=[]
        tpl.append(chr)
        for i in range(0,timestampcount):
                spi=stat[chr][i]
                ap=spi/float(chrinfo[chr])
                apf = "{0:.6f}".format(ap)
                tpl.append(apf)
        topr="\t".join(tpl)
        print topr


