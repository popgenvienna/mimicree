package mimicree.io.inversion;

import java.io.*;
import java.util.regex.*;

import mimicree.data.Chromosome;
import mimicree.data.Inversion;

public class InversionReader {
	private final int  haplotpyeCount;
	private final String inversionFile;
	public InversionReader(String inversionFile,int haplotypeCount)
	{
		this.haplotpyeCount=haplotypeCount;
		this.inversionFile=inversionFile;
	}
	
	
	public ArrayList<Inversion> getInversions()
	{
		
	}
}


/**
 * Read the inversion definition part from the inversion file
 * @author robertkofler
 *
 */
class InversionDefinitionReader
{
	private BufferedReader bf;
	private boolean started =false;
	private static Pattern p= Pattern.compile("(\\w+)\\s*=\\s*([^:]+):(\\d+)-(\\d+)");
			//("gene_id\\s+\"([^\"]+)\";");
	
	public InversionDefinitionReader(String inversionFile)
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
	}
	
	public void setupInversionDefinitions()
	{
		String line;
		while((line=readNextLine())!=null)
		{
			Matcher m= p.matcher(line);
			if(!m.find()) throw new IllegalArgumentException("Could not parse inversion defintion "+ line);
			String name =m.group(1);
			Chromosome chromosome=Chromosome.getChromosome(m.group(2));
			int start=Integer.parseInt(m.group(3));
			int end=Integer.parseInt(m.group(4));
			
			// Set up the definition of the inversion
			Inversion.setInversion(name, chromosome, start, end);
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
	}
	
	private String readNextLine()
	{
		String line;
		try
		{
			while((line=bf.readLine())!=null)
			{
				// Treat the case when the second '>' is encountered marking the end of the Inversion definition part
				if(started && line.startsWith(">")) return null;
				if(started)
				{
					return line;
				}
				else if(line.startsWith(">"))
				{
					started=true;
				}
				
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



class InversionHaplotypeReader
{
	
}