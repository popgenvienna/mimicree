package mimcore.data;

import java.util.*;

/**
 * Represents a collection of inversions for a haploid genome.
 * Immutable
 * @author robertkofler
 *
 */
public class InversionHaplotype {
	// Immutable
	// the array collection can not be accessed from outside
	// the composing objects (Inversion) are immutable by itself
	private final ArrayList<Inversion> inversions;
	private final HashSet<Inversion> invSet;
	public InversionHaplotype(ArrayList<Inversion> inversions)
	{
		this.inversions=new ArrayList<Inversion>(inversions);
		invSet=new HashSet<Inversion>(inversions);
	}
	
	
	/**
	 * Obtain the inversion-haplotype for a position.
	 * Will return default inversion-genotype (=absence of inversions) if 
	 * no inversion can be found for the given position
	 * @param position
	 * @return
	 */
	public Inversion getGenotype(GenomicPosition position)
	{
		Inversion toret=null;
		for(Inversion in: this.inversions)
		{
			if(in.chromosome().equals(position.chromosome()) && position.position()>=in.start()  && position.position()<=in.end())
			{
				assert(toret==null); // Only a single inversion may be present at any position;
				toret=in;
			}
		}
		
		// If no inversion was found return the default = absence of inversion
		if(toret==null) toret=Inversion.getDefaultInversion();
		return toret;
	}
	
	
	/**
	 * does the inversion-haplotpye have the given inversion
	 * @param inv
	 * @return
	 */
	public boolean hasInversion(Inversion inv)
	{
		return this.invSet.contains(inv);
	}
	
	/**
	 * Return a collection of inversions
	 * @return
	 */
	public ArrayList<Inversion> getInversions()
	{
		return new ArrayList<Inversion>(this.inversions);
	}
	
	

}
