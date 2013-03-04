package io.fitness;

import java.io.File;
import java.util.logging.Logger;
import java.util.*;

import mimicree.data.fitness.*;

public class FitnessFunctionLoader {
	private String epistaticFile;
	private String additiveFile;
	private Logger logger;
	public FitnessFunctionLoader(String additiveFile,String epistaticFile,Logger logger)
	{
		this.additiveFile=additiveFile;
		this.epistaticFile=epistaticFile;
		this.logger=logger;
	}
	
	public FitnessFunction loadFitnessFunction()
	{
		AdditiveSNPFitness addFit;
		if(new File(additiveFile).exists())
		{
			this.logger.info("Found an existing file for additive effects");
			addFit=new AdditiveSNPReader(this.additiveFile,this.logger).readAdditiveFitness();
		}
		else
		{
			this.logger.info("Did not find an additive file; Using default (no additive effects)");
			addFit=new AdditiveSNPFitness(new ArrayList<AdditiveSNP>());
		}
		
		
		EpistaticSNPFitness epiFit;
		if(new File(this.epistaticFile).exists()){ 
			this.logger.info("Found an existing file for epistatic effects"); 
			epiFit=new EpistaticSNPReader(this.epistaticFile,this.logger).readEpistaticFitness();
		}
		else
		{
			this.logger.info("Did not find an epistasis file; Using default (no epistatic effects)");
			epiFit=new EpistaticSNPFitness(new ArrayList<EpistaticSNP>());
		}
		
		return new FitnessFunction(addFit,epiFit);

	}

}
