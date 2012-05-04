package mimicree.data;

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
	public InversionHaplotype(ArrayList<Inversion> inversions)
	{
		this.inversions=new ArrayList<Inversion>(inversions);
	}
	
	

}
