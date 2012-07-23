import sys
import collections
from PyModules.Sync import SyncReader
from optparse import OptionParser, OptionGroup
#import rpy2.robjects as robjects
#from rpy2.robjects.packages import importr


	

#Author: Robert Kofler
parser = OptionParser()
parser.add_option("--input", dest="input", help="A synchronized file containging allele counts for the different populations")
parser.add_option("--output",dest="output",help="A synchronized file filtered for SNPs and a single population")
parser.add_option("--select-population",dest="selpop",help="The number of the population (1-based counts) which should be filtered for SNPs and provided as output")
parser.add_option("--min-coverage",dest="mincov",help="Minimum coverage")
parser.add_option("--max-coverage",dest="maxcov",help="Max coverage")
parser.add_option("--min-count",dest="mincount",help="the minimum count")
(options, args) = parser.parse_args()
input = options.input
output= options.output
popnum=int(options.selpop)-1
mincov=int(options.mincov)
maxcov=int(options.maxcov)
mincount=int(options.mincount)

ofh=open(output,"w")
for popsnp in SyncReader(input):
	pop=popsnp.populations[popnum]
	if(pop.isvalidcoverage(mincov,maxcov) and pop.issnp(mincount) and not pop.isindel(mincount)):
		subpop=popsnp.subpopulation([popnum,])
		topr=str(subpop)
		ofh.write(topr+"\n")
		

