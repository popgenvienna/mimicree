package mimicree.data;

import java.util.*;

/**
 * Immutable representation of a chromosome
 * Can only be instantiated by calling the static factory method
 * @author robertkofler
 *
 */
public class Chromosome implements Comparable<Chromosome> {
	private final String chrstr;
	private static HashMap<String,Chromosome> buffer=new HashMap<String,Chromosome>();
	
	/**
	 * does not allow public instance creation
	 * @param chromosome
	 */
	private Chromosome(String chromosome)
	{
		this.chrstr=chromosome;
	}
	
	
	/**
	 * Obtain a new instance of a chromosome
	 * @param chromosome
	 * @return
	 */
	public static Chromosome getChromosome(String chromosome)
	{
		if(buffer.containsKey(chromosome)) return buffer.get(chromosome);
		
		Chromosome chr= new Chromosome(chromosome);
		buffer.put(chromosome,chr);
		return chr;
	}
	
    @Override
    public int compareTo(Chromosome b)
    {
        return this.chrstr.compareTo(b.chrstr);
    }
	
	@Override
	public boolean equals(Object o)
	{
        if(!(o instanceof Chromosome)){return false;}
        Chromosome t= (Chromosome)o;
        return this.chrstr.equals(t.chrstr);
	}
	
	@Override
	public int hashCode()
	{
		return this.chrstr.hashCode();
	}
	
	@Override
	public String toString()
	{
		return this.chrstr;
	}

}
