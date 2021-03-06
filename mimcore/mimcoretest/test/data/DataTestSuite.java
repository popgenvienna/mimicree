package test.data;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author robertkofler
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestBitArrayBuilder.class,TestBitArray.class,TestChromosome.class,TestSNPCollection.class,TestRandomAssortment.class
	,TestRecombinationEventSNP.class, TestRecombinationWindow.class,TestAdditiveSNP.class, TestAdditiveSNPFitness.class, 
	TestMatingFunction.class, TestEpistaticSNP.class, TestEpistaticSNPFitness.class, TestRsquaredIterator.class})
public class DataTestSuite {
}
