package mimicree.io;

import java.util.ArrayList;

import mimicree.data.DiploidGenome;

public interface IDiploidGenomeReader {
	
	public abstract ArrayList<DiploidGenome> readGenomes();

}
