package mimicree.io.haplotypes;

import java.io.*;
import java.util.ArrayList;
import mimicree.data.haplotypes.SNP;
import mimicree.data.GenomicPosition;

/**
 * Read only the SNPs from a haplotype file.
 * @author robertkofler
 *
 */
class HaplotypeSNPReader {
	private String haplotypeFile;
	private BufferedReader bf;
	
	public HaplotypeSNPReader(String haplotypeFile)
	{
		this.haplotypeFile=haplotypeFile;
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
	public ArrayList<SNP> getSNPs()
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
		return snpcol;
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
