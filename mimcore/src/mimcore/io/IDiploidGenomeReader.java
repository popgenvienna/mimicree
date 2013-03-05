package mimcore.io;

import java.util.ArrayList;

import mimcore.data.DiploidGenome;

public interface IDiploidGenomeReader {
	
	public abstract ArrayList<DiploidGenome> readGenomes();

}
