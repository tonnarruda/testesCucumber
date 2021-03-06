package com.fortes.rh.test;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.fortes.rh.test.business.auditoria.ManagerAuditaTest;
import com.fortes.rh.test.security.AccessTest;
import com.fortes.rh.test.util.ImportacaoCSVUtilTest;
import com.fortes.rh.test.util.MailSendRunnableTest;
import com.fortes.rh.test.util.TestsNoIncludeAllUnitTest;
import com.fortes.rh.test.web.dwr.EnderecoDWRTest;
import com.fortes.rh.test.web.dwr.UtilDWRTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ManagerAuditaTest.class,
	TestsNoIncludeAllUnitTest.class,
	MailSendRunnableTest.class,
	ImportacaoCSVUtilTest.class,
	AccessTest.class,
	UtilDWRTest.class,
	EnderecoDWRTest.class
})
public class NoAllUnitTests{}