package mimicree.data;

import java.util.*;

/**
 * Immutable representation of an inversion;
 * Contains information about the chromosome, the start and the end position of the inversion (both are 1-based)
 * @author robertkofler
 *
 */
public class Inversion {
	private final Chromosome chromosome;
	private final int start;
	private final int end;
	
	//------------------- INSTANCE METHODS ---------------------------- //
	private Inversion(Chromosome chr, int start, int end)
	{
		if(!(end>start)) throw new IllegalArgumentException("End position of the inversion needs to be larger than start position; Inversions of size zero are not allowed");
		this.chromosome=chr;
		this.start=start;
		this.end=end;
	}
	
	
	public Chromosome chromosome(){
		return this.chromosome;
	}
	
	public int start(){
		return this.start;
	}
	public int end(){
		return this.end;
	}

	public String name()
	{
		return Inversion.getName(this);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Inversion)) return false;
		Inversion i= (Inversion)o;
		if(		this.chromosome().equals(i.chromosome()) &&
				this.start()== i.start()&&
				this.end()==i.end()) return true;
		return false;
	}
	
	@Override
	public int hashCode()
	{
		/**
		 * only the chromosome, the start, and the end are used for identity of the inversion. 
		 */
        int hc=17;
        hc=hc*31+this.chromosome().hashCode();
        hc=hc*31+this.start();
        hc=hc*31+this.end();
        return hc;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%d-%d", this.chromosome(),this.start(),this.end());
	}
	
	
	// ----------------------- STATIC MEMBERS --------------------------------- //
	
	
	
	private static HashMap<String,Inversion> name2inv=new HashMap<String,Inversion>();
	private static HashMap<Inversion,String> inv2name=new HashMap<Inversion,String>();
	
	/**
	 * Create a new Inversion
	 * Will throw an error if inversion already exists
	 * @param chromosome the chromosome of the inversion
	 * @param start the start position of the inversion
	 * @param end the end position of the inversion
	 * @return
	 */
	public static Inversion setInversion(String name, Chromosome chromosome, int start, int end)
	{
		Inversion newInv=new Inversion(chromosome,start,end);
		if(inv2name.containsKey(newInv)) throw new IllegalArgumentException("Inversion already exists "+ newInv.toString());
		if(name2inv.containsKey(name)) throw new IllegalArgumentException("Name already exists" + name);
		
		// Set the different hashes
		name2inv.put(name,newInv);
		inv2name.put(newInv,name);
		return newInv;
	}
	
	/** 
	 * Retrieve an inversion by its name
	 * @param name
	 * @return
	 */
	public static Inversion getInversion(String name)
	{
		if(!name2inv.containsKey(name)) throw new IllegalArgumentException("Inversion with the name " + name +" has not been defined ");
		return name2inv.get(name);
	}
	
	/**
	 * Retrieve the name of an inversion
	 * @param inv
	 * @return
	 */
	public static String getName(Inversion inv)
	{
		if(!inv2name.containsKey(inv)) throw new IllegalArgumentException("No Name has been assigned to the inversion "+inv.toString());
		return inv2name.get(inv);
	}
	
	/**
	 * Get the number of distinct inversions.
	 * @return
	 */
	private static int getInversionCount()
	{
		return inv2name.size();
	}
	

	

	
	
	
	
	

}
