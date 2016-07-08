package com.lastminute.test;

import com.lastminute.config.TestConfiguration;
import com.lastminute.config.UnitTestDataBaseConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("unit-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, UnitTestDataBaseConfig.class})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@IntegrationTest
public abstract class ConfiguredIntegrationTest extends BasicTest {

	private static boolean inited;

	@Before
	public synchronized void init() throws IOException {
		if (!inited) {
			inited = true;
			redirectSystemOut();
		}
	}

	private void redirectSystemOut() {
		//MockMvc does a lot of System.out stuff we don't want to see if Level.INFO 
		final PrintStream oldSysOut=System.out;
		final Logger SYSOUTLOG = LoggerFactory.getLogger("redirectedSysoutLogger");

		class NewPrintStream extends PrintStream {
			public NewPrintStream(OutputStream os) {
				super(os);
			}
			
			@Override
			public void println() {
				SYSOUTLOG.debug("");
			}
			
			@Override
			public void println(String s) {
				SYSOUTLOG.debug(s);
			}
		}
		
		System.setOut(new NewPrintStream(new OutputStream() {
		     @Override
		     public void write(int b) throws IOException {
		    	 oldSysOut.write(b);
		     }
		  }));
	}
}
