package mimicree.data.fitness;

public class FitnessFunction {
	private final EpistaticSNPFitness epiFitness;
	private final AdditiveSNPFitness addFitness;
	public FitnessFunction(EpistaticSNPFitness epiFitness,AdditiveSNPFitness addFitness)
	{
		this.epiFitness=epiFitness;
		this.addFitness=addFitness;
	}

}
