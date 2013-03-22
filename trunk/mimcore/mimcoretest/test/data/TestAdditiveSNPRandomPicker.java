package test.data;

import mimcore.data.statistic.*;
import org.junit.Test;
import mimcore.data.fitness.*;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: robertkofler
 * Date: 3/22/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestAdditiveSNPRandomPicker {

	@Test
	public void test_af08()
	{
		PopulationAlleleCount pac=new PACReducer(SharedDataFactory.getDiploidGenomesMf08()).reduce();
		for(int i=0; i<10; i++)
		{
			AdditiveSNPRandomPicker rp=new AdditiveSNPRandomPicker(pac,0.1,0.5,5,0.3);
			AdditiveSNPFitness af= rp.getAdditiveSNPs();
			for(AdditiveSNP a : af.getAdditiveSNPs())
			{
				// Af=0.8 Df=0.2 max-freq 0.3
				// Df=0.2    derived smaller than 0.3 derived may be selected; in this case the allele with fitness 1 w11 would be 'A'
				assertThat(a.w11Char(), anyOf(equalTo('C'), equalTo('A')));
			}
		}
	}

	@Test
	public void test_af02()
	{
		PopulationAlleleCount pac=new PACReducer(SharedDataFactory.getDiploidGenomesMf02()).reduce();
		for(int i=0; i<10; i++)
		{
			AdditiveSNPRandomPicker rp=new AdditiveSNPRandomPicker(pac,0.1,0.5,5,0.3);
			AdditiveSNPFitness af= rp.getAdditiveSNPs();
			for(AdditiveSNP a : af.getAdditiveSNPs())
			{
				// Af=0.8 Df=0.2 max-freq 0.3
				// Df=0.2    derived smaller than 0.3 derived may be selected; in this case the allele with fitness 1 w11 would be 'A'
				assertThat(a.w11Char(), anyOf(equalTo('T'), equalTo('G')));
			}
		}
	}



}
