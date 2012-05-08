#!/usr/bin/env python
import random

class File:
	
	@classmethod
	def linecount(cls,inputFile):
		count=0;
		for line in open(inputFile):
			count+=1
		return count

	@classmethod
	def randomly_draw_lines(cls,inputFile, toDraw):
		remainingfilesize=float(File.linecount(inputFile))
		drawn=[]
		for line in open(inputFile):
			threshold=float(toDraw)/remainingfilesize;
			remainingfilesize-=1.0;	
			if(toDraw>0 and random.random() < threshold): # needs to be < threshold! in case of <=threshold it is possible to succeed when zero more are needed (when threshold=0)
				drawn.append(line)
				toDraw-=1
		return drawn

			
		
		
		