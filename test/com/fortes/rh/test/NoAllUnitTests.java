package com.fortes.rh.test;


import junit.framework.TestSuite;

import com.fortes.rh.test.business.auditoria.ManagerAuditaTest;
import com.fortes.rh.test.util.MailSendRunnableTest;
import com.fortes.rh.test.util.TestsNoIncludeAllUnitTest;

public class NoAllUnitTests extends TestSuite
{
	public static TestSuite suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ManagerAuditaTest.class);
        suite.addTestSuite(TestsNoIncludeAllUnitTest.class);
        suite.addTestSuite(MailSendRunnableTest.class);

        return suite;
    }
}