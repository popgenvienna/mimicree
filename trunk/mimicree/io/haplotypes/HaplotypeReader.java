package mimicree.io.haplotypes;

import java.util.*;

import mimicree.data.BitArray.BitArray;
import mimicree.data.haplotypes.*;
import java.io.*;

/**
 * Read the haplotypes.
 * Proper haplotypes actually contain SNP and inversion information
 * @author robertkofler
 *
 */
public class HaplotypeReader {
	private String input;
	private java.util.logging.Logger logger;
	private boolean inputIsStringStream;
	
	public HaplotypeReader(String haplotypeFile, java.util.logging.Logger logger)
	{
		this.logger=logger;
		this.input=haplotypeFile;
		this.inputIsStringStream=false;
	}
	
	public HaplotypeReader(String inputStringStream, java.util.logging.Logger logger, boolean inputIsStringStream)
	{
		this.input=inputStringStream;
		this.logger=logger;
		this.inputIsStringStream=true;
	}
	
	public ArrayList<Haplotype> getHaplotypes()
	{
		this.logger.info("Starting reading haplotypes from file "+this.input);
		this.logger.fine("Start reading the SNPs");
		SNPCollection snpcol= new HaplotypeSNPReader(getBufferedReader()).getSNPcollection();
		this.logger.fine("Finished reading SNPs; SNPs read "+ snpcol.size());
		this.logger.fine("Start reading haplotype information");
		ArrayList<BitArray> haps=new HaplotypeHaplotypeReader(getBufferedReader(),snpcol).getHaplotypes();
		this.logger.fine("Finished reading haplotype information; Haplotypes read " + haps.size());
		
		ArrayList<Haplotype> haplotypes=new ArrayList<Haplotype>();
		for (BitArray ba : haps)
		{
			haplotypes.add(new Haplotype(ba,snpcol));
		}
		this.logger.info("Finished reading haplotypes; Read "+snpcol.size() + " SNPs and " + haplotypes.size() + " haplotypes");
		
		return haplotypes;
	}
	
	/**
	 * Factory method for obtaining a new BufferedReader to the input
	 * BufferedReader either to a File or to a String (for debugging)
	 * @return
	 */
	private BufferedReader getBufferedReader()
	{
		// Peace of shit, need a default
		BufferedReader bf=new BufferedReader(new StringReader(""));
		if(this.inputIsStringStream)
		{
			bf=new BufferedReader(new StringReader(this.input));
		}
		else
		{
			try
			{
				bf= new BufferedReader(new FileReader(this.input));
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}
		return bf;
		
	}

}
