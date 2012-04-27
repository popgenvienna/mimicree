package mimicree.io.haplotypes;

import java.io.*;
import java.util.ArrayList;
import mimicree.data.GenomicPosition;
import mimicree.data.haplotypes.*;

/**
 * Read only the SNPs from a haplotype file.
 * @author robertkofler
 *
 */
class HaplotypeSNPReader {

	private BufferedReader bf;
	
	public HaplotypeSNPReader(String haplotypeFile)
	{
		try
		{
		
			this.bf=new BufferedReader(new FileReader(haplotypeFile));
		}
		catch(FileNotFoundException fe)
		{
			fe.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Obtain all SNPs from a haplotype file.
	 * @return a unsorted collection of SNPs
	 */
	public SNPCollection getSNPcollection()
	{
		
		ArrayList<SNP> snpcol=new ArrayList<SNP>();
		SNP s;
		while((s=next())!=null)
		{
			snpcol.add(s);
		}
		
		
		try
		{
			bf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return new SNPCollection(snpcol);
	}
	
	
	/**
	 * Read one SNP at the time from a haplotype file;
	 * @return
	 */
	private SNP next()
	{
		String line=null;
		try
		{
			line=bf.readLine();
		}
		catch(IOException fe)
		{
			fe.printStackTrace();
			System.exit(0);
		}
		if(line==null) return null;
		return parseSNP(line);
	}
	
	/**
	 * Parse a line of the file to a SNP
	 * @param line
	 * @return
	 */
	private SNP parseSNP(String line)
	{
		//3L	13283707	T	G/T	GT GG GG GG
		String[] a =line.split("\t");
		char ref=a[2].charAt(0);
		char maj=a[3].charAt(0);
		char min=a[3].charAt(2);
		GenomicPosition genpos=new GenomicPosition(a[0],Integer.parseInt(a[1]));
		return new SNP(genpos,ref,maj,min);
	}
	
	

}
