package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author robertkofler
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({test.data.DataTestSuite.class, test.io.MimicreeIOTestSuite.class})
public class MimicrEETestSuite {

}
