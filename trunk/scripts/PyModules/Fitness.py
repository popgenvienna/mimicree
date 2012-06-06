#!/usr/bin/env python




# 35      7.572318250538542E23    7.572318250538542E23    1.0
class Fitness:
	def __init__(self,id,fitness,additive,epistatic):
		self.id=id
		self.fitness=fitness
		self.additive=additive
		self.epistatic=epistatic



class FitnessReader:
	
	@classmethod
	def readFile(cls,input):
		popfit=[]
		for f in FitnessReader(input):
			popfit.append(f)
		return popfit
	
	"""
	Iterator for reading a fitness file
	"""
	def __init__(self,file):
		if(isinstance(file,str)):
			self.__filehandle=open(file,"r")
		else:
			self.__filehandle=file


	def __iter__(self):
		return self
	
	def next(self):
		line=""
		# 35      7.572318250538542E23    7.572318250538542E23    1.0
		while(1):
			line=self.__filehandle.readline()
			if line=="":
				raise StopIteration
			line=line.rstrip('\n')
			if line != "":
				break
		
		a     = line.split()
		id= int(a[0])
		fitness=float(a[1])
		additive=float(a[2])
		epistatic=float(a[3])
	
		return Fitness(id,fitness,additive,epistatic)