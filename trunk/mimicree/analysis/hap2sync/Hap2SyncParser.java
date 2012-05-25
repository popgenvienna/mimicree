package mimicree.analysis.hap2sync;

import java.util.*;
import java.util.logging.Logger;

public class Hap2SyncParser {
	
	
	public static void parseCommandline(Logger logger, LinkedList<String> args)
	{
		ArrayList<String> haplotypeFiles=new ArrayList<String>();
		String outputFile="";
		
		 while(args.size()>0)
	     {
	            String cu=args.remove(0);
	            
	            if(cu.equals("-h"))
	            {
	                haplotypeFiles.add(args.remove(0));
	            }
	            else if(cu.equals("--output"))
	            {
	            	outputFile=args.remove(0);
	            }
	            else
	            {
	            	 throw new IllegalArgumentException("Do not recognize command line option "+cu);
	            }
	     }		
		 
		 
		 Hap2SyncFramework h2sf=new Hap2SyncFramework(haplotypeFiles,outputFile,logger);
		 h2sf.run();
	}

}
