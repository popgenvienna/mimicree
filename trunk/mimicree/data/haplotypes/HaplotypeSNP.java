package mimicree.data.haplotypes;

/**
 * Contains a haplotype for all SNPs
 * Immutable
 * @author robertkofler
 *
 */
public class HaplotypeSNP {
	private final SNPCollection snpcollection;
	private final BitArray haplotype;
	public HaplotypeSNP(BitArray haplotype, SNPCollection snpcollection)
	{
		this.haplotype=haplotype;
		this.snpcollection=snpcollection;
	}
	
}
