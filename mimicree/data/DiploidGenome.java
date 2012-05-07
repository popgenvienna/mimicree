package mimicree.data;

import mimicree.data.BitArray.BitArray;
import mimicree.data.haplotypes.SNPCollection;

/**
 * Immutable representation of a diploid genome
 * Only SNPs are encoded
 * @author robertkofler
 *
 */
public class DiploidGenome {
	private final SNPCollection snpcol;
	private final BitArray hapA;
	private final BitArray hapB;
	public DiploidGenome(SNPCollection snpcol,BitArray haplotypeA,BitArray haplotypeB)
	{
		this.snpcol=snpcol;
		this.hapA=haplotypeA;
		this.hapB=haplotypeB;
	}

}
