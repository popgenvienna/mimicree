import sys
import collections
from optparse import OptionParser, OptionGroup


class DGRPEntry:
	def __init__(self,position,line,char):
		self.position=position
		self.line=line
		self.char=char

class DGRPHapReader:
	"""
	Iterator for reading a DGRP haplotype line by line 
	"""
	def __init__(self,file):
		self.__filehandle=open(file,"r")
		self.__prevLine=""
		self.__previousStop=False
		self.__previousEntry=None

	def __iter__(self):
		return self


	def parseEntries(self,toParse,position):
		toret={}
		toret['position']=position # Schiach..i wana puke..
		for e in toParse:
			toret[e.line]=e
		return toret
	
	def next(self):
		"""
		Actually midly complicated algorithm for parsing a DGRP haplotype file
		"""
		if self.__previousStop:
			raise StopIteration
		
		toParse=[]
		prevPos=None
		if(self.__previousEntry is not None):
			toParse.append(self.__previousEntry)
			prevPos=self.__previousEntry.position
		
		while(1):
			line=self.__filehandle.readline()
			if line=="":
				self.__previousStop=True
				break
			# strip line of carriage return
			line=line.rstrip('\n')
			# discard the header line
			if line=="position,Line,SNP":
				continue
			
			a=line.split(",")
			de=DGRPEntry(a[0],a[1],a[2])
			if prevPos is None:
				prevPos=de.position
			
			if(prevPos == de.position):
				toParse.append(de)
			else:
				self.__previousEntry=de
				break
		return self.parseEntries(toParse,prevPos)


# cat lines.txt|awk '{print $2}'|perl -pe "s/(.*)/'\1'/" |perl -pe 's/\n/,/g'
linelist=['line_101','line_105','line_109','line_129','line_136','line_138','line_142','line_149','line_153','line_158','line_161','line_176','line_177','line_181','line_195','line_208','line_21','line_217','line_227','line_228','line_229','line_233','line_235','line_237','line_239','line_256','line_26','line_28','line_280','line_287','line_309','line_310','line_313','line_317','line_318','line_320','line_321','line_325','line_332','line_338','line_350','line_352','line_356','line_357','line_358','line_359','line_362','line_365','line_367','line_370','line_371','line_373','line_374','line_375','line_377','line_379','line_38','line_380','line_381','line_383','line_386','line_391','line_392','line_399','line_40','line_405','line_406','line_409','line_41','line_42','line_426','line_427','line_437','line_439','line_440','line_441','line_443','line_45','line_461','line_49','line_491','line_492','line_502','line_508','line_509','line_513','line_517','line_531','line_535','line_555','line_563','line_57','line_589','line_59','line_591','line_595','line_639','line_642','line_646','line_69','line_703','line_705','line_707','line_712','line_714','line_716','line_721','line_727','line_73','line_730','line_732','line_737','line_738','line_75','line_757','line_761','line_765','line_774','line_776','line_783','line_786','line_787','line_790','line_796','line_799','line_801','line_802','line_804','line_805','line_808','line_810','line_812','line_818','line_820','line_822','line_83','line_832','line_837','line_85','line_852','line_855','line_857','line_859','line_861','line_879','line_88','line_882','line_884','line_887','line_890','line_892','line_894','line_897','line_907','line_908','line_91','line_911','line_93']

parser = OptionParser()

parser.add_option("--input",dest="input",help="A file containing the genotypes (haplotypes as inbred) of the dgrp")
parser.add_option("--chromosome",dest="chr",help="The chromosome of the current collection")
(options, args) = parser.parse_args()

chr	=options.chr
for e in DGRPHapReader(options.input):
	toPr=[]
	toPr.append(chr)
	toPr.append(e['position'])
	toPr.append(e['line_Ref'].char)
	
	tmp=[e[line].char for line in linelist]
	assert(len(tmp)==158)
	tmpstr="".join(tmp)
	toPr.append(tmpstr)
	strFinal="\t".join(toPr)
	print(strFinal)

