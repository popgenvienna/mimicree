import sys
import random
import collections
from PyModules.Sync import SyncReader
from PyModules.Utility import File
from PyModules.Haplotype import HaplotypeIO
from optparse import OptionParser, OptionGroup
import re

# MimicrEE package
# Author: Dr. Robert Kofler

parser = OptionParser()
parser.add_option("--haplotype-file",	dest="haplotype",help="The haplotype file")
parser.add_option("--s",		dest="s", help="the selection coefficient s")
parser.add_option("--effect-count", 	dest="effectnumber",help="The number of epistatic effects")
parser.add_option("--effect-size",	dest="effectsize", help="The size of a single epistatic effect; mostly=2")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()
haplotypefile=options.haplotype
output = options.output
effectsize=int(options.effectsize)
effectnumber=int(options.effectnumber)
selcoefficient=float(options.s)

randgentypes=HaplotypeIO.parseLines(File.randomly_draw_lines(haplotypefile,effectsize*effectnumber))

ofh=open(output,"w")
for i in range(0,effectnumber):
	snplist=[]
	for k in range(0,effectsize):
		size=len(randgentypes)
		index=int(random.random()*size)
		snplist.append(randgentypes.pop(index))
	
	ofh.write(">{0}:{1}\n".format(i,selcoefficient))
	for s in snplist:
		topr=[]
		topr.append(s.chrom)
		topr.append(s.position)
		if(random.random()<0.5):
			topr.append(s.major)
		else:
			topr.append(s.minor)
		to="\t".join(topr)
		ofh.write(to+"\n")
		

