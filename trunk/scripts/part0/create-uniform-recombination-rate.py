import sys
import random
import collections
from optparse import OptionParser, OptionGroup
#import rpy2.robjects as robjects
#from rpy2.robjects.packages import importr


# MimicrEE package
# Author: Dr. Robert Kofler

chrleng={'X':22422827,'2L':23011544,'2R':21146708,'3L':24543557,'3R':27905053,'4':1351857,'wg':120381546}
winsize=1000000
parser = OptionParser()

parser.add_option("--output",dest="output",help="A file containing uniform recombination rates for D.mel")
parser.add_option("--recombination-rate",dest="recrate",help="The recombination rate")
(options, args) = parser.parse_args()
recrate=options.recrate

ofh=open(options.output,"w")

for chr,leng in chrleng.items():
	start=0
	while(start<leng):
		end=start+winsize
		if(end>leng):
			end=leng
		topr=[]
		topr.append(chr+":"+str(start)+".."+str(end))
		topr.append(recrate)
		topr.append(recrate)
		topr.append(recrate)
		tostr="\t".join(topr)
		ofh.write(tostr+"\n")

		start=end




