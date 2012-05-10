package mimicree.data.fitness;

import mimicree.data.GenomicPosition;

/**
 * Represents a single SNP of an epistatic effect
 * @author robertkofler
 */
public class EpistaticSubeffectSNP {
	private final GenomicPosition position;
	private final char epistaticChar; // the dominant epistatic character
	public EpistaticSubeffectSNP(GenomicPosition position, char epistaticChar)
	{
		this.position=position;
		this.epistaticChar=epistaticChar;
	}
	
	public GenomicPosition position()
	{
		return this.position;
	}
	
	public char epistaticChar()
	{
		return this.epistaticChar;
	}
	

}
