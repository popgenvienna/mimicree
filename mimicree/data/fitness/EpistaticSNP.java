package mimicree.data.fitness;

import mimicree.data.*;
import java.util.*;

/**
 * Immutable representation of an epistatic effect of several SNPs (>=2)
 * @author robertkofler
 *
 */
public class EpistaticSNP {
	private final String name;
	private final double s; // selection coefficient
	private final ArrayList<EpistaticSubeffectSNP> epiSNPs;
	public EpistaticSNP(String name, double s, ArrayList<EpistaticSubeffectSNP> epiSNPs)
	{
		this.name=name;
		this.s=s;
		this.epiSNPs=new ArrayList<EpistaticSubeffectSNP>(epiSNPs);
	}

}


