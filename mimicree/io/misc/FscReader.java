package mimicree.io.misc;


import mimicree.data.haplotypes.Haplotype;
import mimicree.data.haplotypes.SNPCollection;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Logger;


public class FscReader {
    private final String inputFile;
    private final String chromosomeName;
    private final int populationCount;
    private Logger logger;
    public FscReader(String inputFile, String chromosomeName, int populationCount, Logger logger)
    {
        this.chromosomeName=chromosomeName;
        this.inputFile=inputFile;
        this.populationCount=populationCount;
        this.logger = logger;
    }


    public ArrayList<Haplotype> readHaplotypes()
    {

        SNPCollection snpCol;
        return new ArrayList<Haplotype>();
    }


}


class FscSNPReader{
    private BufferedReader br;
    private String chromosomeName;
    public FscSNPReader(String inputFile, String chromosomeName)
    {
        this.chromosomeName=chromosomeName;
        this.br=new BufferedReader(new StringReader(inputFile));
    }

    public SNPCollection read()
    {


    }



}

