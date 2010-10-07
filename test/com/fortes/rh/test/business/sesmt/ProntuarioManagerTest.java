package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.pesquisa.FichaMedicaManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ProntuarioManagerImpl;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.dao.sesmt.ProntuarioDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.FichaMedica;
import com.fortes.rh.model.relatorio.ProntuarioRelatorio;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Prontuario;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.sesmt.ProntuarioFactory;

public class ProntuarioManagerTest extends MockObjectTestCase
{
	private ProntuarioManagerImpl prontuarioManager = new ProntuarioManagerImpl();
	private Mock prontuarioDao;
	private Mock colaboradorManager;
	private Mock realizacaoExameManager;
	private Mock fichaMedicaManager;
	private Mock solicitacaoExameManager;
	private Mock colaboradorAfastamentoManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        prontuarioDao = new Mock(ProntuarioDao.class);
        prontuarioManager.setDao((ProntuarioDao) prontuarioDao.proxy());

        colaboradorManager = mock(ColaboradorManager.class);
        prontuarioManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

        realizacaoExameManager = mock(RealizacaoExameManager.class);
        prontuarioManager.setRealizacaoExameManager((RealizacaoExameManager) realizacaoExameManager.proxy());

        fichaMedicaManager = mock(FichaMedicaManager.class);
        prontuarioManager.setFichaMedicaManager((FichaMedicaManager) fichaMedicaManager.proxy());
        
        solicitacaoExameManager = mock(SolicitacaoExameManager.class);
        prontuarioManager.setSolicitacaoExameManager((SolicitacaoExameManager) solicitacaoExameManager.proxy());
        
        colaboradorAfastamentoManager = mock(ColaboradorAfastamentoManager.class);
        prontuarioManager.setColaboradorAfastamentoManager((ColaboradorAfastamentoManager) colaboradorAfastamentoManager.proxy());
    }

	public void testFindByColaborador() throws Exception
	{
		prontuarioDao.expects(once()).method("findByColaborador").with(ANYTHING).will(returnValue(new ArrayList<Prontuario>()));

		assertNotNull(prontuarioManager.findByColaborador(null));
	}

	public void testFindRelatorioProntuario() throws Exception
	{
		Empresa empresa = EmpresaFactory.getEmpresa(2L);
	
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo Nome");
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("Faixa Teste");
		faixaSalarial.setCargo(cargo);
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setFaixaSalarial(faixaSalarial);

		Collection<Prontuario> prontuarios= new ArrayList<Prontuario>();
		prontuarios.add(ProntuarioFactory.getEntity(55L));

		colaboradorManager.expects(once()).method("findByIdDadosBasicos").with(eq(colaborador.getId())).will(returnValue(colaborador));
		prontuarioDao.expects(once()).method("findByColaborador").with(eq(colaborador)).will(returnValue(prontuarios));
		realizacaoExameManager.expects(once()).method("findRealizadosByColaborador").will(returnValue(new ArrayList<RealizacaoExame>()));
		fichaMedicaManager.expects(once()).method("findByColaborador").will(returnValue(new ArrayList<FichaMedica>()));
		solicitacaoExameManager.expects(once()).method("findByCandidatoOuColaborador").will(returnValue(new ArrayList<SolicitacaoExame>()));
		solicitacaoExameManager.expects(once()).method("findByCandidatoOuColaborador").will(returnValue(new ArrayList<SolicitacaoExame>()));
		colaboradorAfastamentoManager.expects(once()).method("findByColaborador").will(returnValue(new ArrayList<ColaboradorAfastamento>()));
		
		ProntuarioRelatorio prontuarioRelatorio = prontuarioManager.findRelatorioProntuario(empresa, colaborador);

		assertTrue(prontuarioRelatorio.getPossuiResultados());
	}
}