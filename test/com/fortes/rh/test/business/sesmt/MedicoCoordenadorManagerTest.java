package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.model.type.File;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManagerImpl;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;

public class MedicoCoordenadorManagerTest extends MockObjectTestCase
{
	private MedicoCoordenadorManagerImpl medicoCoordenadorManager;
	private Mock medicoCoordenadorDao;
	
	// Utilizado em testGetMedicosAteData
	private int contador = 0;

	protected void setUp() throws Exception
    {
        super.setUp();

        medicoCoordenadorManager = new MedicoCoordenadorManagerImpl();

        medicoCoordenadorDao = new Mock(MedicoCoordenadorDao.class);

        medicoCoordenadorManager.setDao((MedicoCoordenadorDao) medicoCoordenadorDao.proxy());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

	public void testFindByDataEmpresa() throws Exception
	{
		MedicoCoordenador medico = new MedicoCoordenador();
		medico.setId(1L);

		Empresa empresa = new Empresa();
		empresa.setId(2L);

		Date data = new Date();

		medicoCoordenadorDao.expects(once()).method("findByDataEmpresa").with(eq(empresa.getId()), eq(data)).will(returnValue(medico));

		MedicoCoordenador medicoRetorno = medicoCoordenadorManager.findByDataEmpresa(empresa.getId(), data);

		assertEquals(medico, medicoRetorno);
	}

	public void testFindByIdProjection() throws Exception
	{
		MedicoCoordenador medico = new MedicoCoordenador();
		medico.setId(1L);

		Empresa empresa = new Empresa();
		empresa.setId(2L);

		medicoCoordenadorDao.expects(once()).method("findByIdProjection").with(eq(medico.getId())).will(returnValue(medico));

		MedicoCoordenador medicoRetorno = medicoCoordenadorManager.findByIdProjection(medico.getId());

		assertEquals(medico, medicoRetorno);
	}

	public void testGetAssinaturaDigital() throws Exception
	{
		Long id = 1L;
		medicoCoordenadorDao.expects(once()).method("getFile").with(eq("assinaturaDigital"), eq(id)).will(returnValue(new File()));

		assertNotNull(medicoCoordenadorManager.getAssinaturaDigital(id));
	}

	public void testGetAssinaturaDigitalException()
	{
		Long id = 1L;
		Exception exception = null;

		try
		{
			medicoCoordenadorDao.expects(once()).method("getFile").with(eq("assinaturaDigital"), eq(id)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(id,""))));
			medicoCoordenadorManager.getAssinaturaDigital(id);
		}
		catch(Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testFindByEmpresa()
	{
		medicoCoordenadorDao.expects(once()).method("findByEmpresa").with(eq(1L),ANYTHING).will(returnValue(new ArrayList<MedicoCoordenador>()));
		assertNotNull(medicoCoordenadorManager.findByEmpresa(1L));
	}
	
	/*
	 * Este teste contempla apenas as regras individuais, sempre heverá 1(Um) ou 0(Zero) engenheiro
	 * que fará parte dp PPP.
	 */
	public void testGetMedicosAteDataComUmOuZeroEngenheiro()
	{
		Date data_1 = DateUtil.criarDataMesAno(01, 01, 2013);
		Date data_2 = DateUtil.criarDataMesAno(01, 02, 2013);
		Date data_3 = DateUtil.criarDataMesAno(01, 03, 2013);
		Date data_4 = DateUtil.criarDataMesAno(01, 04, 2013);
		Date data_5 = DateUtil.criarDataMesAno(01, 04, 2013);

		boolean estaNaRelacao = true;
		boolean naoEstaNaRelacao = false;
		
		// Teste com desligamento e fim não nulos
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_1, data_1, data_1, data_1, estaNaRelacao);
		
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_1, data_5, data_2, data_4, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_1, data_4, data_2, data_5, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_1, data_3, data_2, data_5, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_1, data_3, data_2, data_4, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_1, data_5, data_2, data_3, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_1, data_4, data_2, data_3, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, data_5, data_3, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, data_4, data_3, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, data_3, data_4, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_1, data_2, data_3, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_1, data_2, data_3, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_1, data_2, data_3, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_2, data_4, data_1, data_5, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_2, data_5, data_1, data_4, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_2, data_3, data_1, data_5, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_2, data_3, data_1, data_4, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_2, data_5, data_1, data_3, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_2, data_4, data_1, data_3, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_3, data_4, data_1, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_3, data_5, data_1, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_4, data_5, data_1, data_3, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_5, data_3, data_4, data_1, data_2, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_3, data_5, data_1, data_2, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_4, data_5, data_1, data_2, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, data_5, data_3, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, data_4, data_3, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, data_3, data_4, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_3, data_5, data_2, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_3, data_4, data_2, data_5, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_4, data_5, data_2, data_3, naoEstaNaRelacao);

		// Teste com desligamento e fim nulos
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_1, null, data_1, null, estaNaRelacao);
		
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_1, null, data_2, null, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, null, data_3, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_2, null, data_1, null, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_3, null, data_1, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, null, data_3, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_3, null, data_2, null, naoEstaNaRelacao);
		
		// Teste com desligamento nulo
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_1, null, data_1, data_1, estaNaRelacao);
		
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_1, null, data_2, data_4, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_1, null, data_2, data_3, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, null, data_3, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_2, null, data_1, data_4, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_2, null, data_1, data_3, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_3, null, data_1, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_4, null, data_1, data_3, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, null, data_3, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_3, null, data_2, data_4, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_4, null, data_2, data_3, naoEstaNaRelacao);
		
		// Teste com fim nulo
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_1, data_1, data_1, null, estaNaRelacao);
		
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_1, data_4, data_2, null, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_1, data_3, data_2, null, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, data_4, data_3, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_1, data_3, data_4, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_1, data_2, data_4, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_1, data_2, data_3, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_3, data_2, data_4, data_1, null, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_4, data_2, data_3, data_1, null, estaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_2, data_3, data_4, data_1, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, data_4, data_3, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_2, data_3, data_4, null, naoEstaNaRelacao);
		testaSeMedicoFazParteDoRelatorioPPP(data_1, data_3, data_4, data_2, null, naoEstaNaRelacao);
	}

	public void testGetMedicosAteDataComVariosMedicos()
	{
		Date admissao = DateUtil.criarDataMesAno(01, 01, 2013);
		Date desligamento = DateUtil.criarDataMesAno(01, 04, 2013);

		Date inicio_1 = DateUtil.criarDataMesAno(01, 02, 2013);
		Date inicio_2 = DateUtil.criarDataMesAno(10, 02, 2013);
		Date fim = DateUtil.criarDataMesAno(01, 03, 2013);
		
		Date dataPPP = DateUtil.criarDataMesAno(05, 02, 2013);
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaboradorDoPPP = ColaboradorFactory.getEntity();
		colaboradorDoPPP.setEmpresa(empresa);
		colaboradorDoPPP.setDataAdmissao(admissao);
		colaboradorDoPPP.setDataDesligamento(desligamento);
		
		MedicoCoordenador medicoCoordenador1 = new MedicoCoordenador();
		medicoCoordenador1.setId(1L);
		medicoCoordenador1.setInicio(inicio_1);
		medicoCoordenador1.setFim(fim);
		
		MedicoCoordenador medicoCoordenador2 = new MedicoCoordenador();
		medicoCoordenador2.setId(2L);
		medicoCoordenador2.setInicio(inicio_1);
		medicoCoordenador2.setFim(fim);
		
		MedicoCoordenador medicoCoordenador3 = new MedicoCoordenador();
		medicoCoordenador3.setId(3L);
		medicoCoordenador3.setInicio(inicio_1);
		medicoCoordenador3.setFim(null);
		
		MedicoCoordenador medicoCoordenador4 = new MedicoCoordenador();
		medicoCoordenador4.setId(4L);
		medicoCoordenador4.setInicio(inicio_2);
		medicoCoordenador4.setFim(fim);
		
		MedicoCoordenador medicoCoordenador5 = new MedicoCoordenador();
		medicoCoordenador5.setId(5L);
		medicoCoordenador5.setInicio(inicio_2);
		medicoCoordenador5.setFim(fim);
		
		MedicoCoordenador[] medicoCoordenadores = new MedicoCoordenador[]{medicoCoordenador1, medicoCoordenador2, medicoCoordenador3, medicoCoordenador4, medicoCoordenador5};
		medicoCoordenadorDao.expects(once()).method("findByEmpresa").with(eq(1L), eq(true)).will(returnValue(Arrays.asList(medicoCoordenadores)));
		
		Collection<MedicoCoordenador> resultado = medicoCoordenadorManager.getMedicosAteData(dataPPP, colaboradorDoPPP);
		
		assertEquals(3, resultado.size());
		assertEquals(medicoCoordenador1.getId(), ((MedicoCoordenador)resultado.toArray()[0]).getId());
		assertEquals(medicoCoordenador2.getId(), ((MedicoCoordenador)resultado.toArray()[1]).getId());
		assertEquals(medicoCoordenador3.getId(), ((MedicoCoordenador)resultado.toArray()[2]).getId());
	}

	private void testaSeMedicoFazParteDoRelatorioPPP(Date ppp, Date admissao, Date desligamento, Date inicio, Date fim, boolean estaNaRelacao)
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaboradorDoPPP = ColaboradorFactory.getEntity();
		colaboradorDoPPP.setEmpresa(empresa);
		
		MedicoCoordenador medicoCoordenador1 = new MedicoCoordenador();
		medicoCoordenador1.setInicio(inicio);
		medicoCoordenador1.setFim(fim);
		
		medicoCoordenadorDao.expects(once()).method("findByEmpresa").with(eq(1L), eq(true)).will(returnValue(Arrays.asList(new MedicoCoordenador[]{medicoCoordenador1})));
		
		colaboradorDoPPP.setDataAdmissao(admissao);
		colaboradorDoPPP.setDataDesligamento(desligamento);
		Collection<MedicoCoordenador> resultado = medicoCoordenadorManager.getMedicosAteData(ppp, colaboradorDoPPP);
		
		assertEquals(++contador+"o teste.",estaNaRelacao, resultado.size() == 1);
	}

}