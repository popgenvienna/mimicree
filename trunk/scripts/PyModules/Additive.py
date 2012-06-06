#!/usr/bin/env python



# 2L      1574961 A       -0.1    0.5
# X       20456501        G       -0.1    0.5
class AdditiveEffect:
	def __init__(self,chr,pos,w11char,s,h):
		self.chr=chr
		self.pos=pos
		self.w11char=w11char
		self.s=s
		self.h=h
		
	@property
	def maxfitness(self):
		fit=[]
		fit.append(1-self.s)
		fit.append(1-self.h*self.s)
		max=1
		for f in fit:
			if f>max:
				max=f
		return max
		

		
		

class AdditiveEffectUtil:
	
	@classmethod
	def get_effects(cls,inputfile):
		toret=[]
		for line in open(inputfile):
			line=line.rstrip()
			a=line.split("\t");
			ae=AdditiveEffect(a[0],int(a[1]),a[2],float(a[3]),float(a[4]))
			toret.append(a)
		return toret
	
	@classmethod
	def get_effect_hash(cls,effects):
		"""
		key=chr:pos
		"""
		toret={};
		for e in effects:
			key=e.chr+":"+str(e.pos)
			toret[key]=e
		return toret
		
			