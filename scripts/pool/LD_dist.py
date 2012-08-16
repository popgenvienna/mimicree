import sys
import collections
from optparse import OptionParser,OptionGroup
import copy
import math


def load_candidates(candidateFile):
	cch=collections.defaultdict(lambda:[])
	for line in open(candidateFile):
		line=line.rstrip()
		a=line.split('\t')
		chr=a[0]
		pos=int(a[1])
		cch[chr].append(pos)
	return cch
		
def median(x):
	"""
	>>> median([float("1e-12"),float("1e-14"),float("1e-10")])
	9.9999999999999998e-13
	>>> median([float("1e-11"),float("1e-20"),float("1e-12")])
	9.9999999999999998e-13
	>>> median([float("1e-8"),float("1e-11"),float("1e-12"),float("1e-20")])
	3.1622776601683786e-12
	>>> median([float("1e-8"),float("1e-10")])
	1.0000000000000007e-09
	"""
	mid =int(len(x)/2)
	sort=sorted(x)
	if len(x)==0:
		return None
	if len(x)%2==0:
		lower=sort[mid-1]
		upper=sort[mid]
		return (float(lower)+float(upper))/2.0
	else:
		return sort[mid]
	

#Author: Martin Kapun & Robert Kofler
#########################################################   HELP   #########################################################################
usage="python %prog --candidates candidates.cmh -snps full.cmh --bins 10 --maxdist  1000 --measure median --output candidates.ld"
parser = OptionParser(usage=usage)
group=OptionGroup(parser,"""				
H E L P:
_________

Description:
This script uses sync files with any statistic (P-value, allele frequency change, FST,etc.) in the last column as input. Based on the parameters --bins and --maxdist, the script averages the statistic of SNPs around a candidate. --bins describes the total numbers of bins generated and --maxdist describes the maximum distance in one direction until which adjacent SNPs are considered. Thus, the bin-size calculates as follows: bins/2*maxdist. Note that the script by default log-scales the statistic, which only applies to P-Values. Thus if you use any other statistic disable log-scaling by setting --log False. Additionally you can choose between calcultating the geometric mean and the median with the parameter --measure. Note that the candidate is excluded from the data.

Input: 
--candidates and --snps:
Any sync file with a statistic in the last column. 

Output:
A tab delimited with two columns for the chromosome and position followed by columns for every bin starting from negative to positive 
E.g. if you set --bin 4 and --maxdist 100, the columns would correspond to the following bins and relative positions to the candidate:
Col1: Chromosome 
Col2: Position 
Col3: averaged statistic of all SNPs in a distance of -100 to -50 basepairs away from the candidate
Col4: averaged statistic of all SNPs in a distance of -50 to -0 basepairs away from the candidate
Col5: averaged statistic of all SNPs in a distance of 0 to 50 basepairs away from the candidate
Col6: averaged statistic of all SNPs in a distance of 50 to 100 basepairs away from the candidate

""") 
#########################################################   CODE   #########################################################################

parser.add_option("-c", "--candidates", dest="cand", help="*.sync or cmh output file")
parser.add_option("-s", "--snps", dest="snps", help="cmh output with all SNPs")
parser.add_option("-b", "--bin-size", dest="binsize", help="the size of the bins")
parser.add_option("-m", "--distance", dest="maxdist", help="the distance to either side of a candidate SNP")
parser.add_option("-o", "--output", dest="out", help="outputfile for boxplot")
# median, and log=true
parser.add_option_group(group)
(options, args) = parser.parse_args()
binsize=int(options.binsize)
distance=int(options.maxdist)
bins=math.ceil((2*distance)/binsize)


stat=collections.defaultdict(lambda:[])
# chromosome candidate hash
cch=load_candidates(options.cand)


# iterate of SNPs
for line in open(options.snps):
	line=line.rstrip();
	a=line.split("\t")
	chr=a[0]
	pos=int(a[1])
	pval=float(a[-1])
	
	ch=cch[chr]
	for candpos in ch:
		rangestart=candpos-distance
		rangeend=candpos+distance-1
		if(pos<rangestart):
			continue
		if(pos>rangeend):
			continue
		if(candpos==pos): # get rid of the candidate!
			continue
		relativeposition=pos-rangestart
		binindex=int(relativeposition/binsize)
		stat[binindex].append(pval)

for i in sorted(stat.keys()):
	ar=stat[i]
	med=median(ar)
	print "{0}\t{1}".format(i,med)


