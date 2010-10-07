package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.GastoEmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.relatorio.GastoEmpresaTotal;
import com.fortes.rh.model.geral.relatorio.GastoRelatorio;
import com.fortes.rh.model.geral.relatorio.TotalGastoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockActionContext;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.action.geral.GastoEmpresaEditAction;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class GastoEmpresaEditActionTest extends MockObjectTestCase
{
	private GastoEmpresaEditAction action;
	private Mock manager;
	private Mock areaOrganizacionalManager;
	private Mock estabelecimentoManager;
	private Mock colaboradorManager;
	private Mock parametrosDoSistemaManager;

	protected void setUp()
	{
		action = new GastoEmpresaEditAction();
		manager = new Mock(GastoEmpresaManager.class);
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		estabelecimentoManager = new Mock(EstabelecimentoManager.class);
		colaboradorManager = new Mock(ColaboradorManager.class);
		action.setGastoEmpresaManager((GastoEmpresaManager) manager.proxy());
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
		action.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());

		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		MockSpringUtil.mocks.put("parametrosDoSistemaManager", parametrosDoSistemaManager);
		
		Mockit.redefineMethods(ActionContext.class, MockActionContext.class);
		Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testGerarRelatorioInvestimentos() throws Exception
    {
    	action.setDataIni("01/2008");
    	action.setDataFim("04/2008");

    	Colaborador colaborador = new Colaborador();
    	colaborador.setId(1L);

    	action.setColaborador(colaborador);

    	String[] areasCheck = new String[3];
    	areasCheck[0] = "1";
    	areasCheck[1] = "2";
    	areasCheck[2] = "3";

    	action.setAreasCheck(areasCheck);

    	AreaOrganizacional a1 = AreaOrganizacionalFactory.getEntity();
    	a1.setId(1L);
    	AreaOrganizacional a2 = AreaOrganizacionalFactory.getEntity();
    	a2.setId(2L);
    	AreaOrganizacional a3 = AreaOrganizacionalFactory.getEntity();
    	a3.setId(3L);

    	Collection<AreaOrganizacional> clAO = new ArrayList<AreaOrganizacional>();
    	clAO.add(a1);
    	clAO.add(a2);
    	clAO.add(a3);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
    	estabelecimento.setId(1L);
    	Collection<Estabelecimento> clE = new ArrayList<Estabelecimento>();
    	clE.add(estabelecimento);

    	GastoRelatorio gr1 = new GastoRelatorio();
    	gr1.setAreaOrganizacional(a1);
    	GastoRelatorio gr2 = new GastoRelatorio();
    	gr2.setAreaOrganizacional(a1);
    	GastoRelatorio gr3 = new GastoRelatorio();
    	gr3.setAreaOrganizacional(a1);
    	GastoRelatorio gr4 = new GastoRelatorio();
    	gr4.setAreaOrganizacional(a2);
    	GastoRelatorio gr5 = new GastoRelatorio();
    	gr5.setAreaOrganizacional(a2);
    	GastoRelatorio gr6 = new GastoRelatorio();
    	gr6.setAreaOrganizacional(a3);

    	Collection<GastoRelatorio> gastoRelatorios = new ArrayList<GastoRelatorio>();
    	gastoRelatorios.add(gr1);
    	gastoRelatorios.add(gr2);
    	gastoRelatorios.add(gr3);
    	gastoRelatorios.add(gr4);
    	gastoRelatorios.add(gr5);
    	gastoRelatorios.add(gr6);

    	TotalGastoRelatorio tgr1 = new TotalGastoRelatorio();
    	tgr1.setMesAno(DateUtil.criarDataMesAno(01, 01, 2008));
    	TotalGastoRelatorio tgr2 = new TotalGastoRelatorio();
    	tgr2.setMesAno(DateUtil.criarDataMesAno(01, 02, 2008));
    	TotalGastoRelatorio tgr3 = new TotalGastoRelatorio();
    	tgr3.setMesAno(DateUtil.criarDataMesAno(01, 03, 2008));
    	TotalGastoRelatorio tgr4 = new TotalGastoRelatorio();
    	tgr4.setMesAno(DateUtil.criarDataMesAno(01, 04, 2008));

    	Collection<TotalGastoRelatorio> totais = new ArrayList<TotalGastoRelatorio>();
    	totais.add(tgr1);
    	totais.add(tgr2);
    	totais.add(tgr3);
    	totais.add(tgr4);

    	GastoEmpresaTotal gastoEmpresaTotal = new GastoEmpresaTotal();
    	gastoEmpresaTotal.setGastoRelatorios(gastoRelatorios);
    	gastoEmpresaTotal.setTotais(totais);

    	ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setAppVersao("1");

    	manager.expects(once()).method("filtroRelatorio").with(new Constraint[]{ANYTHING}).will(returnValue(gastoRelatorios));
    	manager.expects(once()).method("getTotalInvestimentos").with(eq(gastoRelatorios)).will(returnValue(totais));
    	colaboradorManager.expects(once()).method("findColaboradorById").with(new Constraint[]{ANYTHING}).will(returnValue(colaborador));
    	
		parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
    	assertEquals("success", action.gerarRelatorioInvestimentos());

    	action.setAreasCheck(null);

    	action.setEmpresaSistema(null);
    	parametrosDoSistemaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(parametrosDoSistema));
    	areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(ANYTHING).will(returnValue(clAO));
    	manager.expects(once()).method("getTotalInvestimentos").with(eq(gastoRelatorios)).will(returnValue(totais));
    	estabelecimentoManager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(clE));
    	colaboradorManager.expects(once()).method("findColaboradorById").with(new Constraint[]{ANYTHING}).will(returnValue(null));
    	manager.expects(once()).method("filtroRelatorio").with(new Constraint[]{ANYTHING}).will(returnValue(gastoRelatorios));
    	assertEquals("input", action.gerarRelatorioInvestimentos());
    }
}