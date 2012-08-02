import sys
import random
import collections
import os
from optparse import OptionParser, OptionGroup
import re
from RInterface import *
from Fitness import FitnessReader
from Additive import AdditiveEffectUtil


# MimicrEE package
# Author: Dr. Robert Kofler


def format_fitness(toproces):
	fitness=[]
	factor=[]
	counter=1
	minFit=None
	for filear in toprocess:
		for fit in filear:
			fitadd=fit.additive
			if(minFit is None or fitadd<minFit):
				minFit=fitadd
			fitness.append(str(fitadd))
			factor.append(str(counter))
		counter+=1
	return (RFormat.get_vector(fitness),RFormat.get_vector(factor),minFit)



def format_rfile(rFile,outputFile, toprocess, maxFitness):
	x,f,ymin=format_fitness(toprocess)
	mf=[str(maxFitness) for i in range(0,len(toprocess))]
	mf=RFormat.get_vector(mf)
	ymax=maxFitness
	rfh=open(rFile,"w")
	towrite="""
	x<-{0}
	f<-factor({1})
	pdf("{2}",width=15)
	boxplot(x~f,ylim=c({3},{4}))
	lines({5},type="l",lty=2)
	dev.off()
	""".format(x,f,outputFile,ymin,ymax,mf)
	rfh.write(towrite)
	rfh.close()
	

parser = OptionParser()
parser.add_option("-f",	dest="fitnessfile", action="append", help="The fitness file")
parser.add_option("--additive", 	dest="additive",help="The additive effect")
parser.add_option("--output",		dest="output",help="the output file")
(options, args) = parser.parse_args()

outputFile=os.path.abspath(options.output)
dir=os.path.dirname(outputFile)

tempr=os.path.join(dir,"temp.R")

toprocess=[]
for file in options.fitnessfile:
	red=FitnessReader.readFile(file)
	toprocess.append(red)
	

maxfit=AdditiveEffectUtil.get_maxfitness(AdditiveEffectUtil.read_effect_file(options.additive))


format_rfile(tempr,outputFile,toprocess,maxfit)
RUtility.run(tempr)