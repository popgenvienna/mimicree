package mimicree.io.misc;


import mimicree.data.Chromosome;
import mimicree.data.GenomicPosition;
import mimicree.data.haplotypes.*;
import mimicree.data.BitArray.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 *    Reading the output of fastsimcoal
 */
public class FscReader {
    private final String inputFile;
    private final String chromosomeName;
    private Logger logger;
    private BufferedReader br;
    private final char refChar='A';
    private final char derivedChar='C';

    public FscReader(String inputFile, String chromosomeName, Logger logger)
    {
        this.chromosomeName=chromosomeName;
        this.inputFile=inputFile;

        br=new BufferedReader(new StringReader(this.inputFile));
        this.logger = logger;
    }


    public ArrayList<Haplotype> readHaplotypes()
    {

        SNPCollection snpCol;
        ArrayList<Integer> snpPositions =new ArrayList<Integer>();
        ArrayList<BitArray> haplotypes=new ArrayList<BitArray>();
        String line="";
        boolean foundSegsites=false;
        boolean readHaplotypes=true;
        try
        {
            while((line=br.readLine())!=null)
            {
                if(readHaplotypes)
                {
                    // Obtain the haplotypes
                    haplotypes.add(parseHaplotypes(line));
                }
                else if(foundSegsites)
                {
                    snpPositions=parseSNPPositions(line);
                    readHaplotypes=true;
                }
                else if(line.startsWith("segsites")) foundSegsites = true;

            }

        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(0);
        }

        return processHaplotypes(snpPositions, haplotypes, Chromosome.getChromosome(this.chromosomeName));
    }


	/**
	 * Convert the SNP positions and the bitarrays to haplotypes
	 * @param positions
	 * @param haplotypes
	 * @param chromosome
	 * @return
	 */
    private ArrayList<Haplotype> processHaplotypes(ArrayList<Integer> positions,ArrayList<BitArray> haplotypes, Chromosome chromosome)
    {
        ArrayList<SNP> snps=new ArrayList<SNP>();

        // Detect minor and major allele
        // 0 is ancestral (refchar) 1 is derived most are zero and a few are 1 (according to popgen expectation)
        for(int i=0; i <positions.size();i++)
        {
            char minorAllele=this.derivedChar;
            char majorAllele=this.refChar;
            int counter=0;
            for(BitArray ba : haplotypes)
            {
                if(ba.hasBit(i)) counter++;
            }
            if(counter > haplotypes.size()/2)
            {
                minorAllele=this.refChar;
                majorAllele=this.derivedChar;
            }

            SNP s = new SNP(new GenomicPosition(chromosome,positions.get(i)),this.refChar,majorAllele,minorAllele);
            snps.add(s);
        }


		// Finally create the haplotypes by combining a bitarray and a snpcollection
        SNPCollection snpCollection=new SNPCollection(snps);
		ArrayList<Haplotype> haps=new ArrayList<Haplotype>();
		for(BitArray ba: haplotypes)
		{
			Haplotype h= new Haplotype(ba,snpCollection);
		}
		return haps;
    }


    private ArrayList<Integer> parseSNPPositions(String line)
    {
        if(line.equals("")) throw new IllegalArgumentException("There is no SNP definition after the segsites");
        String[] rawPositions=line.split("\\s+");

        ArrayList<Integer> positions=new ArrayList<Integer>();
        for(String s: rawPositions)
        {
            positions.add(Integer.parseInt(s));
        }
        return positions;
    }


    private BitArray parseHaplotypes(String line)
    {
        String[] t=line.split("");
        BitArrayBuilder builder=new BitArrayBuilder(t.length);
        for(int i=0; i<t.length; i++)
        {
            String s=t[i];
            if(s.equals("1"))
            {
                 builder.setBit(i);
            }
            else if(s.equals("0"))
            {

            }
            else throw new IllegalArgumentException("Can not parse character"+s.toString());
        }
        return builder.getBitArray();
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

        ArrayList<Integer> snpPositions =getSNPPositions();
        SNP s= new SNP();
        SNPCollection sc=new SNPCollection();

    }







}

