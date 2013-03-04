#!/usr/bin/env python
import sys
import random
from optparse import OptionParser, OptionGroup
import collections



class SelectionFecundity:
        def __init__(self,s,h):
                self.s=s
                self.h=h
                self.geno2fit={0:1 , 1:1+h*s , 2:1+s}
        
        def nextGeneration(self,twone,population):
                totfitsum=0
                diploids=population.getIndividuals()
                cumsum=[]
                for dipl in diploids:
                        genot=dipl.get_genotype()
                        totfitsum+=self.geno2fit[genot]
                        cumsum.append(totfitsum)
                ne=int(twone/2.0)
                novelpop=[]
                for i in range(0,ne):
                        r1,r2=(random.random()*totfitsum,random.random()*totfitsum)
                        i1,i2=(self.__get_index(cumsum,r1),self.__get_index(cumsum,r2))
                        while(i1==i2):
                                # exclude selfing
                                r2=random.random()*totfitsum
                                i2=self.__get_index(cumsum,r2)
                        d1,d2=diploids[i1],diploids[i2]
                        genotype=d1.get_gamete()+d2.get_gamete()
                        novelpop.append(Diploid(genotype))
                return Population(novelpop)

        def __get_index(self,cumsum,randnum):
                for i, cs in enumerate(cumsum):
                        if randnum< cs:
                                return i
                raise ValueError("fitness out of range; max possible "+str(cumsum[-1])+" found " + str(randnum) )




class SelectionViability:
        def __init__(self,s,h):
                self.s=s
                self.h=h
                maxfit=max(1.0, 1+h*s, 1+s)
                self.survivalprob={0: (1.0/maxfit) , 1: (1+h*s)/maxfit , 2: (1+s)/maxfit}
        
        def nextGeneration(self,twone,population):
                diploids=population.getIndividuals()
                survivors=[]
                for dipl in diploids:
                        genot=dipl.get_genotype()
                        survivalprob = self.survivalprob[genot]
                        if random.random()< survivalprob:
                                survivors.append(dipl)
                ne=int(twone/2.0)
                novelpop=[]
                survivorcount=len(survivors)
                for i in range(0,ne):
                        i1,i2=(int(random.random()*survivorcount) , int(random.random()*survivorcount))
                        while(i1==i2):
                                i2=int(random.random()*survivorcount)
 
                        d1,d2=survivors[i1],survivors[i2]
                        genotype=d1.get_gamete()+d2.get_gamete()
                        novelpop.append(Diploid(genotype))
                return Population(novelpop)
                

class Diploid:
        def __init__(self,genotype):
                self.__genotype=genotype
                
                
                
        def get_genotype(self):
                """
                genotype code
                0=w11
                1=w12
                2=w22
                thus genotype=gamete1+gamete2
                """
                return self.__genotype
                # return 0=w11, 1=w12, or 2=w22
        
        def get_gamete(self):
                if(self.__genotype==0):
                        return 0
                elif(self.__genotype==2):
                        return 1
                elif(self.__genotype==1):
                        if(random.random()<0.5):
                                return 0
                        else:
                                return 1
                else:
                        raise ValueError("Invalid genotype")

class Population:
        def __init__(self,diploids):
                self.__diploids=diploids
                self.__twone=float(2.0 * len(diploids))
                if(self.__twone==0):
                        raise ValueError("Invalid population size; must be larger than zero")
                genosum=0
                for d in diploids:
                        genosum+=d.get_genotype()
                self.__genosum=genosum
                
        def getIndividuals(self):
                return self.__diploids
                

        @classmethod
        def initialize_population(cls,twone,ns):
                count_one=ns
                count_zero=twone-count_one
                haps=[0 for i in range(0,count_zero)]+[1 for i in range(0,count_one)]
                diploids=[]
                while(len(haps)>0):
                        draw_one=haps.pop(int(random.random()*len(haps)))
                        draw_two=haps.pop(int(random.random()*len(haps)))
                        diploids.append(Diploid(draw_one+draw_two)) # genotype is just the sum of the two gametes 0=0+0 1=0+1 2=1+1
                return Population(diploids)
                
        def isfixed(self):
                genosum=self.__genosum
                twone=self.__twone
                if(genosum==0 or genosum==twone):
                        return True
                else:
                        return False
                
        def isSelectedFixed(self):
                if(not self.isfixed()):
                        raise ValueError("nothing is fixed")
                if(self.__genosum > 0):
                        return True
                else:
                        return False
        
        def get_frequency(self):
                twone=self.__twone
                gensum=float(self.__genosum)
                return gensum/twone
        
        def getNextGeneration(self,selectionregime,twone):
                nextGen=selectionregime.nextGeneration(twone,self)
                return nextGen
        

                        
                
                        

def pad_trajectories(trajectories):
        maxlen=0;
        for traj in trajectories:
                if(len(traj)>maxlen):
                        maxlen=len(traj)
        
        for traj in trajectories:
                lastelement=traj[-1]
                difference=maxlen-len(traj)
                for i in range(0,difference):
                        traj.append(lastelement)
        if(len(traj)!=maxlen):
                raise ValueError("failed padding")


parser = OptionParser()
parser.add_option("--Ne", dest="ne", help="the number of diploid individuals")
parser.add_option("-e",dest="h",help="the heterozygosity")
parser.add_option("-s",dest="s",help="the selection coefficient")
parser.add_option("-p",dest="p", help="start allele frquency")
parser.add_option("--viability", action="store_true",dest="viability",help="Flag switch on viability selection")
parser.add_option("--repeat-simulations",dest="repsim")
parser.add_option("--max-generations",dest="maxgen")
(options, args) = parser.parse_args()
repsim = int(options.repsim)
twone  = int(options.ne)*2
s      = float(options.s)
h      = float(options.h)
p      = float(options.p)
maxgen = float(options.maxgen)
startc = int(twone*p)
viability=bool(options.viability)

selfunc=None
if(viability):
        selfunc=SelectionViability(s,h)
else:
        selfunc=SelectionFecundity(s,h)



trajectories=[]
for i in range(0,repsim):
        pop=Population.initialize_population(twone,startc)
        freqs=[]
        freqs.append(pop.get_frequency())
        counter=0
        while(not pop.isfixed()):
                pop=pop.getNextGeneration(selfunc,twone)
                freqs.append(pop.get_frequency())
                if counter>=maxgen:
                        break        
                counter+=1
                
        trajectories.append(freqs)

pad_trajectories(trajectories)
        
maxgen=len(trajectories[0])
for i in range(0,maxgen):
        topr=[]
        for k in range(0,repsim):
                topr.append(trajectories[k][i])
        toprstr="\t".join(map(str,topr))
        print toprstr


