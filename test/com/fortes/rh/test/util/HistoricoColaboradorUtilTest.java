package com.fortes.rh.test.util;

import junit.framework.TestCase;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FuncaoFactory;
import com.fortes.rh.test.factory.cargosalario.ReajusteColaboradorFactory;
import com.fortes.rh.util.HistoricoColaboradorUtil;

public class HistoricoColaboradorUtilTest extends TestCase
{

	@SuppressWarnings("unused")
	protected void setUp() throws Exception
    {
		HistoricoColaboradorUtil historicoColaboradorUtil = new HistoricoColaboradorUtil();
    }

	public void testGetMotivoReajuste(){

		AreaOrganizacional areaOrganizacionalAtual = AreaOrganizacionalFactory.getEntity(1L, "area 1",null, null);
		AreaOrganizacional areaOrganizacionalProposta = AreaOrganizacionalFactory.getEntity(2L, "area 2",null, null);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);

		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setAreaOrganizacional(areaOrganizacionalAtual);

		Cargo cargoAtual = CargoFactory.getEntity(1L);
		Cargo cargoProposto = CargoFactory.getEntity(2L);

		FaixaSalarial faixaSalarialAtual = FaixaSalarialFactory.getEntity(1L, "Atual", cargoAtual);
		FaixaSalarial faixaSalarialProposta = FaixaSalarialFactory.getEntity(2L, "Proposta", cargoProposto);

		Funcao funcaoAtual = FuncaoFactory.getEntity(1L);
		Funcao funcaoProposta = FuncaoFactory.getEntity(2L);

		ReajusteColaborador reajuste = ReajusteColaboradorFactory.getReajusteColaborador(colaborador, null, areaOrganizacionalProposta, faixaSalarialAtual, faixaSalarialProposta, funcaoAtual, funcaoProposta, 
				TipoAplicacaoIndice.VALOR, TipoAplicacaoIndice.VALOR, 10.00, 20.00);

		String motivo = HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador);

		assertEquals("Test 1", MotivoHistoricoColaborador.PROMOCAO, motivo);

		reajuste.setSalarioAtual(20.00);
		motivo = HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador);
		assertEquals("Test 2", MotivoHistoricoColaborador.PROMOCAO_HORIZONTAL, motivo);

		reajuste.setFaixaSalarialProposta(faixaSalarialAtual);
		motivo = HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador);
		assertEquals("Test 3", MotivoHistoricoColaborador.TRANSFERENCIA, motivo);

		reajuste.setAreaOrganizacionalProposta(historicoColaborador.getAreaOrganizacional());
		motivo = HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador);
		assertEquals("Test 4", MotivoHistoricoColaborador.MUDANCA_FUNCAO, motivo);
	}
}