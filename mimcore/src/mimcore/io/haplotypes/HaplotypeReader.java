package mimcore.io.haplotypes;

import java.util.*;

import mimcore.data.BitArray.BitArray;
import mimcore.data.haplotypes.*;
import java.io.*;
import java.util.zip.GZIPInputStream;

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
		BufferedReader bf;
		if(this.inputIsStringStream)
		{
			bf=new BufferedReader(new StringReader(this.input));
		}
		else
		{
			bf=getBufferedFileReader(this.input);
		}
		return bf;
		
	}


	/**
	 * Get a BufferedReader for a input file;
	 * Decides from the file extension whether the file is zipped ".gz"
	 * @param inputFile
	 * @return
	 */
	private BufferedReader getBufferedFileReader(String inputFile)
	{
		BufferedReader br=null;
		if(inputFile.endsWith(".gz"))
		{
			try{
				br=new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(inputFile))));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		else{

			try{
				br= new BufferedReader(new FileReader(inputFile));
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
				System.exit(1);
			}

		}
		return br;
	}


}
