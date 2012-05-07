package mimicree.io.inversion;

import java.io.*;
import java.util.*;
import mimicree.data.*;


public class InversionHaplotypeReader {
	private BufferedReader bf;
	private int countStartSymbol=0;
	private final int haplotypeCount;
	
	private static class InvEntry
	{
		private final int firstIndex;
		private final int secondIndex;
		private final InversionHaplotype firstInv;
		private final InversionHaplotype secondInv;
		public InvEntry(int firstIndex,int secondIndex, InversionHaplotype firstInv, InversionHaplotype secondInv)
		{
			this.firstIndex=firstIndex;
			this.secondIndex=secondIndex;
			this.firstInv=firstInv;
			this.secondInv=secondInv;
		}
	}
	
	public InversionHaplotypeReader(String inversionFile, int size)
	{
		try
		{
			bf=new BufferedReader(new FileReader(inversionFile));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		this.haplotypeCount=size;
	}
	
	/** 
	 * Read the haplotpyes of the inversions
	 * @return
	 */
	public ArrayList<InversionHaplotype> getInversionHaplotypes()
	{
		// Initialize default => empty list of inversion haplotypes
		ArrayList<InversionHaplotype> toret=new ArrayList<InversionHaplotype>(this.haplotypeCount);
		for(int i=0; i<haplotypeCount; i++)
		{
			toret.set(i, new InversionHaplotype(new ArrayList<Inversion>()));
		}
		
		String line;
		while((line=readNextLine())!=null)
		{
			InvEntry e=parseLine(line);
			toret.set(e.firstIndex, e.firstInv);
			toret.set(e.secondIndex, e.secondInv);
		}
		return toret;

	}
	
	/**
	 * Parse a line containing information about the haplotype of an inversion
	 * @param line
	 * @return
	 */
	private InvEntry parseLine(String line)
	{
		String[] ar =line.split("\t");
		int tmpHapNum=Integer.parseInt(ar[0])-1;
		int index1=tmpHapNum*2;
		int index2=index1+1;
		
		ArrayList<Inversion> inv1=new ArrayList<Inversion>();
		ArrayList<Inversion> inv2=new ArrayList<Inversion>();
		if(ar.length>1 && ar[1].contains(":"))
		{
			String[] br=ar[1].split(":");
			inv1=parseInvHaplotype(br[0]);
			inv2=parseInvHaplotype(br[1]);
		}
				
		return new InvEntry(index1,index2,new InversionHaplotype(inv1), new InversionHaplotype(inv2));
	}
	
	
	/**
	 * Just parse an inversion haplotype into a list of inversions
	 * -:-
	 * @param invhaplotype
	 * @return
	 */
	private ArrayList<Inversion> parseInvHaplotype(String invhaplotype)
	{
		ArrayList<String> rawInv=new ArrayList<String>();
		if(invhaplotype=="-") {}
		else if(invhaplotype.contains(","))
		{
			
			String[] tmp=invhaplotype.split(",");
			for(String s: tmp)
			{
				rawInv.add(s);
			}
		}
		else
		{
			rawInv.add(invhaplotype);
		}
		
		ArrayList<Inversion> toret=new ArrayList<Inversion>();
		for(String s:rawInv)
		{
			toret.add(Inversion.getInversion(s));
		}
		return toret;
	}

	
	
	/**
	 * read a single line containing an inversion haplotype
	 * @return
	 */
	private String readNextLine()
	{
		String line;
		try
		{
			while((line=bf.readLine())!=null)
			{
				// Treat the case when the second '>' is encountered marking the end of the Inversion definition part
				if(this.countStartSymbol==2)
				{
					return line;
				}
				else if(line.startsWith(">")) countStartSymbol++;
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return null; // in case the end of the file has been reached
	}
	
	
}
