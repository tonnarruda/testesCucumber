package com.fortes.rh.config.backup;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.config.backup.notificador.NotificadorDeBackupViaEmailImpl;
import com.fortes.rh.util.Mail;

public class NotificadoDeBackupViaEmailImplTest extends MockObjectTestCase {

	private static final String EMAIL_SUPORTE_TECNICO = "admin@fortes.com.br";

	NotificadorDeBackupViaEmailImpl notificador;
	
	Mock mailMock;
	Mock parametrosDoSistemaManagerMock;
	
	public void setUp() {
		notificador = new NotificadorDeBackupViaEmailImpl();
		notificador.setMail(mockaMail());
		notificador.setParametrosDoSistemaManager(mockaParametrosDoSistemaManager());
	}
	
	public void testbackAndSendaMail() {
		// dado que
		seUrlDaAplicacaoFor("http://localhost:8080/fortesrh/backup/download.action?filename=backup.sql");
		seEmailDoSuporteTecnicoFor(EMAIL_SUPORTE_TECNICO);
		// quando
		notificador.notifica("backup.sql");
		// entao
		assertQueEmailFoiEnviadoAoSuporteTecnico();
	}

	private void assertQueEmailFoiEnviadoAoSuporteTecnico() {
		mailMock.verify();
	}

	private void seEmailDoSuporteTecnicoFor(String email) {
		// parametros
		parametrosDoSistemaManagerMock
			.expects(once()).method("getEmailDoSuporteTecnico")
			.withNoArguments()
				.will(returnValue(email));
		// mail
		mailMock.expects(once()).method("send")
			.with(new Constraint[] {ANYTHING, ANYTHING, ANYTHING, ANYTHING, eq(new String[]{email})});
	}

	private void seUrlDaAplicacaoFor(String url) {
		parametrosDoSistemaManagerMock
			.expects(once()).method("getUrlDaAplicacao")
			.withNoArguments()
				.will(returnValue(url));
	}

	private ParametrosDoSistemaManager mockaParametrosDoSistemaManager() {
		parametrosDoSistemaManagerMock = mock(ParametrosDoSistemaManager.class);
		return (ParametrosDoSistemaManager) parametrosDoSistemaManagerMock.proxy();
	}
	
	private Mail mockaMail() {
		mailMock = mock(Mail.class);
		return (Mail) mailMock.proxy();
	}

}
