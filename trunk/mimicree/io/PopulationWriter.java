package mimicree.io;

import mimicree.data.*;
import java.util.logging.Logger;
import java.util.*;
import mimicree.data.haplotypes.Haplotype;
import java.io.*;
import mimicree.io.haplotypes.HaplotypeWriter;
import mimicree.io.inversion.InversionWriter;

public class PopulationWriter {
	private final Population population;
	private final String outputDir;
	private final int generation;
	private final int simulationNumber;
	private final Logger logger;
	public PopulationWriter(Population population, String outputDir, int  generation, int simulationNumber, Logger logger)
	{
		this.population=population;
		this.outputDir=outputDir;
		this.generation=generation;
		this.simulationNumber=simulationNumber;
		this.logger=logger;
	}
	
	
	public void write()
	{
		ArrayList<Haplotype> haplotypes=new ArrayList<Haplotype>();
		ArrayList<InversionHaplotype> invHaplotypes =new ArrayList<InversionHaplotype>();
		for(Specimen spec: population.getSpecimen()){
			haplotypes.add(spec.getGenome().getHaplotypeA().getSNPHaplotype());
			haplotypes.add(spec.getGenome().getHaplotypeB().getSNPHaplotype());
			invHaplotypes.add(spec.getGenome().getHaplotypeA().getInversionHaplotype());
			invHaplotypes.add(spec.getGenome().getHaplotypeB().getInversionHaplotype());
		}
		
		String haplotypeOFile="";
		String inversionOFile="";
		
		try
		{
			 haplotypeOFile = new File(this.outputDir,"haplotypes.r" + this.simulationNumber + ".g"+this.generation).getCanonicalPath();
			 
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		try{
			inversionOFile=new File(this.outputDir,"inversions.r"+this.simulationNumber+".g"+this.generation).getCanonicalPath();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		new HaplotypeWriter(haplotypeOFile,this.logger).write(haplotypes);
		// Inversions should only be written when they were present in the original sample
		if(Inversion.getInversionCount()>0)
		{
			new InversionWriter(inversionOFile,this.logger).write(invHaplotypes);
		}
		
	}
	

}
