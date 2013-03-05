package mimcore.io.misc;

import java.io.*;
import mimcore.data.LD.*;

public class RsquaredWriter {
	
	private BufferedWriter bw;
	private String outputFile;
	private java.util.logging.Logger logger;
	public RsquaredWriter(String outputFile, java.util.logging.Logger logger)
	{
		try
		{
			bw=new BufferedWriter(new FileWriter(outputFile));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		this.outputFile=outputFile;
		this.logger=logger;
	}
	
	/**
	 * Write the content of the iterator to a file
	 * @param genomeIterator
	 */
	public void write(IRsquaredIterator genomeIterator)
	{
		this.logger.info("Start writing pairwise r^2 into file "+this.outputFile);
		PairwiseRsquared  entry=null;
		int counter=0;
		while((entry=genomeIterator.next())!=null)
		{
			String formated=formatEntry(entry);
			try
			{
				bw.write(formated+"\n");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
			counter++;
		}
		this.logger.info("Finished writting r^2 entries, wrote "+counter+" entries");
	}
	
	
	
	private String formatEntry(PairwiseRsquared entry)
	{
		StringBuilder sb=new StringBuilder();
		sb.append(entry.positionA().chromosome().toString()); sb.append("\t");
		sb.append(entry.positionA().position()); sb.append("\t");
		sb.append(entry.positionB().chromosome().toString()); sb.append("\t");
		sb.append(entry.positionB().position()); sb.append("\t");
		
		sb.append(entry.distance()); sb.append("\t");
		sb.append(entry.rsquared()); sb.append("\t");
		return sb.toString();
	}
	
	
	public void close()
	{
		try
		{
			this.bw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
	}
	

}
