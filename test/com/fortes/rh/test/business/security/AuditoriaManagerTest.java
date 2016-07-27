package com.fortes.rh.test.business.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.security.AuditoriaManagerImpl;
import com.fortes.rh.dao.security.AuditoriaDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.security.Auditoria;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;

public class AuditoriaManagerTest extends MockObjectTestCase
{
	private AuditoriaManagerImpl auditoriaManager = new AuditoriaManagerImpl();
	private Mock auditoriaDao;
	
	protected void setUp() throws Exception
	{
		super.setUp();
		auditoriaDao = new Mock(AuditoriaDao.class);
		auditoriaManager.setDao((AuditoriaDao) auditoriaDao.proxy());
	}

	public void testFindOperacoesPeloModulo() {
		auditoriaDao.expects(once()).method("findOperacoesPeloModulo").with(eq("Cadastro de Eventos"));
		auditoriaManager.findOperacoesPeloModulo("Cadastro de Eventos");
	}
	
	public void testFindEntidade()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		auditoriaDao.expects(once()).method("findEntidade").with(eq(empresa.getId()));
		auditoriaManager.findEntidade(empresa.getId());
	}
	
	public void testProjectionFindById()
	{
		Auditoria auditoria = new Auditoria();
		auditoria.setId(1L);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		auditoriaDao.expects(once()).method("projectionFindById").with(eq(auditoria.getId()), eq(empresa.getId())).will(returnValue(auditoria));
		assertEquals(auditoria, auditoriaManager.projectionFindById(auditoria.getId(), empresa.getId()));
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Integer count = new Integer(1);
		auditoriaDao.expects(once()).method("getCount").with(ANYTHING, eq(empresa.getId())).will(returnValue(count));
		assertEquals(count, auditoriaManager.getCount(null, empresa.getId()));
	}

	public void testList()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Auditoria> auditorias = new ArrayList<Auditoria>();
		
		auditoriaDao.expects(once()).method("list").with(eq(1), eq(10), ANYTHING, eq(empresa.getId())).will(returnValue(auditorias));
		assertEquals(auditorias, auditoriaManager.list(1, 10, null, empresa.getId()));
	}
	
	public void testAudita() {
		
		Usuario usuario = UsuarioFactory.getEntity(1L);
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		String recurso = "Evento";
		String operacao = "save";
		String chave = "evento";
		String dados = "Cadastrado evento 'Fortes 2010'";
		
		Auditoria auditoria = new Auditoria();
		auditoria.audita(usuario, empresa, recurso, operacao, chave, dados);
		
		auditoriaDao.expects(once()).method(operacao).with(ANYTHING).will(returnValue(auditoria));
		auditoriaManager.audita(recurso, operacao, chave, dados);
	}
	
	public void testAuditaConfirmacaoDesligamentoNoAC()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		
		Colaborador colab1 = ColaboradorFactory.getEntity();
		colab1.setCodigoAC("001");
		colab1.setNome("José");
		
		Colaborador colab2 = ColaboradorFactory.getEntity();
		colab2.setCodigoAC("002");
		colab2.setNome("Maria");
		
		Colaborador colab3 = ColaboradorFactory.getEntity();
		colab3.setCodigoAC("003");
		colab3.setNome("Raimundo");
		
		Colaborador colab4 = ColaboradorFactory.getEntity();
		colab4.setCodigoAC("004");
		colab4.setNome("joaquim");
		
		Collection<Colaborador> colaboradores = Arrays.asList(colab1, colab2, colab3, colab4);
		
		Date dataDesligamento = DateUtil.criarDataMesAno(1, 1, 2015);
		
		auditoriaDao.expects(once()).method("save").withAnyArguments().isVoid();
		
		Exception ex = null;
		try {
			auditoriaManager.auditaConfirmacaoDesligamentoNoAC(colaboradores, dataDesligamento, empresa);
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	} 
}
