package tests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;



@Suite
@RunWith(JUnitPlatform.class)
@SelectClasses({ConverterTest.class,HorarioTest.class})
public class AllTests {

	
	
}
