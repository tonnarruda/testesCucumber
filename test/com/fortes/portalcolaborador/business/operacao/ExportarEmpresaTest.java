package com.fortes.portalcolaborador.business.operacao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.portalcolaborador.business.TransacaoPCManager;
import com.fortes.portalcolaborador.model.EmpresaPC;
import com.fortes.portalcolaborador.model.dicionario.URLTransacaoPC;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

public class ExportarEmpresaTest extends MockObjectTestCase {
	
	private Mock historicoColaboradorManager;
	private Mock empresaManager;
	private Mock transacaoPCManager;
	private Mock mail; 
	private ExportarEmpresa exportarEmpresa;
	
	public void setUp(){
		exportarEmpresa = new ExportarEmpresa();
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		
		historicoColaboradorManager = new Mock(HistoricoColaboradorManager.class);
		MockSpringUtil.mocks.put("historicoColaboradorManager", historicoColaboradorManager);
		
		empresaManager = new Mock(EmpresaManager.class);
		MockSpringUtil.mocks.put("empresaManager", empresaManager);
		
		transacaoPCManager = new Mock(TransacaoPCManager.class);
		MockSpringUtil.mocks.put("transacaoPCManager", transacaoPCManager);
		
		mail = mock(Mail.class);
		MockSpringUtil.mocks.put("mail", mail);
	}

	public void testGerarTransacao()
	{
		Empresa empresa = new Empresa(1L);
		EmpresaPC empresaPC = new EmpresaPC(empresa);
		String parametros = empresaPC.getIdentificadoresToJson();
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Colaborador");
		colaborador.setPessoalCpf("12345678910");
		
		HistoricoColaborador historico1 = HistoricoColaboradorFactory.getEntity();
		historico1.setColaborador(colaborador);
		historico1.setData(DateUtil.incrementaDias(new Date(), -1));
		
		HistoricoColaborador historico2 = HistoricoColaboradorFactory.getEntity();
		historico2.setColaborador(colaborador);
		historico2.setData(new Date());
				
		List<HistoricoColaborador> historicoColaboradores = Arrays.asList(historico1, historico2);
		
		empresaManager.expects(once()).method("findEmailsEmpresa").with(eq(empresa.getId())).will(returnValue(empresa));
		historicoColaboradorManager.expects(once()).method("findByEmpresaPC").with(eq(empresa.getId())).will(returnValue(historicoColaboradores));
		historicoColaboradorManager.expects(once()).method("montaSituacaoHistoricoColaborador").with(eq(historicoColaboradores)).will(returnValue(historicoColaboradores));
		transacaoPCManager.expects(once()).method("enfileirar").with(eq(URLTransacaoPC.ATUALIZAR_COLABORADOR), ANYTHING).isVoid();
		transacaoPCManager.expects(once()).method("enfileirar").with(eq(URLTransacaoPC.ENVIAR_EMAIL), ANYTHING).isVoid();
		
		Exception ex = null;
		try {
			exportarEmpresa.gerarTransacao(parametros);
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	public void testGerarTransacaoException(){
		Empresa empresa = new Empresa(1L);
		EmpresaPC empresaPC = new EmpresaPC(empresa);
		String parametros = empresaPC.getIdentificadoresToJson();
		
		empresaManager.expects(once()).method("findEmailsEmpresa").with(eq(empresa.getId())).will(returnValue(empresa));
		historicoColaboradorManager.expects(once()).method("findByEmpresaPC").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		mail.expects(once()).method("send").withAnyArguments().isVoid();
		
		Exception ex = null;
		try {
			exportarEmpresa.gerarTransacao(parametros);
		} catch (Exception e) {
			ex = e;
		}
		assertNotNull(ex);
	}
}
