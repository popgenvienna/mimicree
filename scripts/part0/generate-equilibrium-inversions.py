import sys
import random
import collections
from PyModules.Sync import SyncReader
from optparse import OptionParser, OptionGroup
import re

# MimicrEE package
# Author: Dr. Robert Kofler


class Inversion:
	def __init__(self, name, chromosome,start,end, frequency):
		self.name=name
		self.chromosome=chromosome
		self.start=start
		self.end=end
		self.frequency=frequency
	
	def __str__(self):
		# In2 = 3R:1023334-11454555
		# InP = 3R:14000232-18000455
		# In3 = 2L:1200000-1500000
		return "{0} = {1}:{2}-{3}".format(self.name,self.chromosome,self.start,self.end)


def parseInversion(invDefinition):
	# re.search(r"Parent=([^;]+)",annotation).group(1)
	# In2=3R:12345-23456@0.54
	m=re.search(r"([^=]+)=([^:]+):(\d+)-(\d+)@(.+)",invDefinition)
	if(not m):
		raise Exception("Can not parse Inversion definition "+ invDefinition)
	name 	= m.group(1)
	chr 	= m.group(2)
	start	= int(m.group(3))
	end	= int(m.group(4))
	frequency=float(m.group(5))
	inv=Inversion(name,chr,start,end,frequency)
	return inv
	

parser = OptionParser()
parser.add_option("--output",dest="output",help="A set of random haplotyes which may be used for analysis with MimicrEE")
parser.add_option("--inversion",dest="rawinversion",action="append",help="Define a single inversion and its equilibrium frequency\n"+
		  "Following an example: 'In2=3R:12345-23456@0.54'\n"+
		  "'name=chromosome:start-end@frequency'; This option may be provided several times to define more than one inversion")
parser.add_option("--population-size",dest="popsize",help="The size of the population")
(options, args) = parser.parse_args()
output= options.output
popsize=int(options.popsize)

inversions=[]
for s in options.rawinversion:
	inv=parseInversion(s)
	inversions.append(inv)

# create an inversion collection; key is the number of the haplotype; (number of individum is number of haplotype /2)

icol=collections.defaultdict(lambda:[])



for i in range(0,2*popsize):
	for inversion in inversions:
		rand=random.random()
		if(rand<inversion.frequency):
			icol[i].append(inversion.name)

ofw=open(output,"w")
ofw.write("> Inversion definition\n")
for inv in inversions:
	ofw.write(str(inv)+"\n")

ofw.write("> Population description\n")
for i in range(0,popsize):
	indexh1=2*i
	indexh2=indexh1+1
	invtype1=",".join(icol[indexh1])
	invtype2=",".join(icol[indexh2])
	if(not invtype1):
		invtype1="-"
	if(not invtype2):
		invtype2="-"
	ofw.write("{0}\t{1}:{2}\n".format(i+1,invtype1,invtype2))
	
		
