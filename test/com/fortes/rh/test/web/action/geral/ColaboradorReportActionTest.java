package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.TabelaReajusteColaboradorFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockCheckListBoxUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.geral.ColaboradorReportAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class ColaboradorReportActionTest extends MockObjectTestCase
{
	private ColaboradorReportAction action;
	private Mock colaboradorManager;
	private Mock estabelecimentoManager;
	private Mock areaOrganizacionalManager;
	private Mock tabelaReajusteColaboradorManager;
	private Mock grupoOcupacionalManager;
	private Mock cargoManager;
	private Mock parametrosDoSistemaManager;

	protected void setUp()
	{
		action = new ColaboradorReportAction();

		colaboradorManager = new Mock(ColaboradorManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		tabelaReajusteColaboradorManager = new Mock(TabelaReajusteColaboradorManager.class);
		grupoOcupacionalManager = new Mock(GrupoOcupacionalManager.class);
		cargoManager = new Mock(CargoManager.class);
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);

		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setTabelaReajusteColaboradorManager((TabelaReajusteColaboradorManager) tabelaReajusteColaboradorManager.proxy());
		action.setGrupoOcupacionalManager((GrupoOcupacionalManager) grupoOcupacionalManager.proxy());
		action.setCargoManager((CargoManager) cargoManager.proxy());
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);

		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(CheckListBoxUtil.class, MockCheckListBoxUtil.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

	protected void tearDown() throws Exception
	{
        MockSecurityUtil.verifyRole = false;
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testPrepareProjecaoSalarialFiltro() throws Exception
	{
	 	estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(null));
	 	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(null));
	 	tabelaReajusteColaboradorManager.expects(once()).method("findAllSelectByNaoAprovada").with(ANYTHING, eq(TipoReajuste.COLABORADOR)).will(returnValue(null));
	 	grupoOcupacionalManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(null));
	 	cargoManager.expects(once()).method("populaCheckBox").with(ANYTHING, ANYTHING).will(returnValue(null));

	 	String retorno = action.prepareProjecaoSalarialFiltro();

	 	assertEquals("success", retorno);
	}

	public void testGerarRelatorioProjecaoSalarial() throws Exception
	{
		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);

		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador1);
		colaboradors.add(colaborador2);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		action.setTabelaReajusteColaborador(tabelaReajusteColaborador);

		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
		tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(tabelaReajusteColaborador));
		colaboradorManager.expects(once()).method("findProjecaoSalarial").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colaboradors));

		String retorno = action.gerarRelatorioProjecaoSalarial();

		assertEquals("datasource não vazio", "success", retorno);
	}

	public void testGerarRelatorioProjecaoSalarialInput() throws Exception
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);

		Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
		colaboradors.add(colaborador1);
		colaboradors.add(colaborador2);

		TabelaReajusteColaborador tabelaReajusteColaborador = TabelaReajusteColaboradorFactory.getEntity(1L);
		action.setTabelaReajusteColaborador(tabelaReajusteColaborador);

		tabelaReajusteColaboradorManager.expects(once()).method("findByIdProjection").with(eq(tabelaReajusteColaborador.getId())).will(returnValue(tabelaReajusteColaborador));
		colaboradorManager.expects(once()).method("findProjecaoSalarial").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(null));

		estabelecimentoManager.expects(once()).method("populaCheckBox").will(returnValue(null));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(null));
		tabelaReajusteColaboradorManager.expects(once()).method("findAllSelectByNaoAprovada").with(ANYTHING, eq(TipoReajuste.COLABORADOR)).will(returnValue(null));
		grupoOcupacionalManager.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(null));
		cargoManager.expects(once()).method("populaCheckBox").with(ANYTHING, ANYTHING).will(returnValue(null));

		String retorno = action.gerarRelatorioProjecaoSalarial();

		assertEquals("datasource vazio", "input", retorno);
	}

	public void testGetsSets()
	{
		action.getAreaOrganizacionals();
		action.getColaboradorManager();
		action.getDataSource();
		action.getEstabelecimentosCheck();
		action.getEstabelecimentosCheckList();
		action.getFiltro();
		action.getGrupoOcupacionals();
		action.getParametros();
		action.getEmptyDataSource();
		action.getCargosCheck();
		action.getCargosCheckList();
		action.getData();
		action.getAreasCheck();
		action.getAreasCheckList();
		action.getGruposCheck();
		action.getGruposCheckList();
		action.getTabelaReajusteColaboradors();
		action.getTabelaReajusteColaborador();

		action.setAreaOrganizacionals(null);
		action.setDataSource(null);
		action.setEstabelecimentosCheck(null);
		action.setEstabelecimentosCheckList(null);
		action.setFiltro(null);
		action.setGrupoOcupacionals(null);
		action.setCargosCheck(null);
		action.setCargosCheckList(null);
		action.setData(null);
		action.setAreasCheck(null);
		action.setAreasCheckList(null);
		action.setGruposCheck(null);
		action.setGruposCheckList(null);
		action.setTabelaReajusteColaboradors(null);
		action.setTabelaReajusteColaborador(null);
	}

}
