package mimicree.analysis.fcs2hap;

import mimicree.MimicrEE;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: robertkofler
 * Date: 1/18/13
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Fcs2HapParser {

    public static void parseCommandline(Logger logger, LinkedList<String> args)
    {
        String inputFile="";
        String outputFile="";
        String chromosome="";
        int haplotypeCount=0;

        while(args.size()>0)
        {
            String cu=args.remove(0);

            if(cu.equals("--input"))
            {
                inputFile=args.remove(0);
            }
            else if(cu.equals("--output"))
            {
                outputFile=args.remove(0);
            }
            else if(cu.equals("--chromosome"))
            {
                chromosome=args.remove(0);
            }
            else if(cu.equals("--help"))
            {
                printHelp();
            }
            else
            {
                throw new IllegalArgumentException("Do not recognize command line option "+cu);
            }
        }


        Fcs2HapFramework fc2h=new Fcs2HapFramework(inputFile,outputFile,chromosome,logger);
        fc2h.run();
    }

    public static void printHelp()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("mode fcs2hap: convert fastcoalsim outpout to MimicrEE haplotypes\n");
        sb.append("--input					    the fastsimcoal file\n");
        sb.append("--chromosome                 the chromosome\n");
        sb.append("--output					the output file (sync)\n");
        sb.append(MimicrEE.getGeneralHelpmessage());
        System.out.print(sb.toString());
        System.exit(1);
    }
}
