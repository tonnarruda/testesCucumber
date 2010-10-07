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
import com.fortes.rh.util.HistoricoColaboradorUtil;

public class HistoricoColaboradorUtilTest extends TestCase
{

	@SuppressWarnings("unused")
	protected void setUp() throws Exception
    {
		HistoricoColaboradorUtil historicoColaboradorUtil = new HistoricoColaboradorUtil();
    }

	public void testGetMotivoReajuste(){

		AreaOrganizacional areaOrganizacionalAtual = new AreaOrganizacional();
		areaOrganizacionalAtual.setId(1L);
		areaOrganizacionalAtual.setNome("area 1");

		AreaOrganizacional areaOrganizacionalProposta = new AreaOrganizacional();
		areaOrganizacionalProposta.setId(2L);
		areaOrganizacionalProposta.setNome("area 2");

		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);

		HistoricoColaborador historicoColaborador = new HistoricoColaborador();
		historicoColaborador.setAreaOrganizacional(areaOrganizacionalAtual);

		Cargo cargoAtual = new Cargo();
		cargoAtual.setId(1L);

		Cargo cargoProposto = new Cargo();
		cargoProposto.setId(2L);

		FaixaSalarial faixaSalarialAtual = new FaixaSalarial();
		faixaSalarialAtual.setId(1L);
		faixaSalarialAtual.setCargo(cargoAtual);

		FaixaSalarial faixaSalarialProposta = new FaixaSalarial();
		faixaSalarialProposta.setId(2L);
		faixaSalarialProposta.setCargo(cargoProposto);

		Funcao funcaoAtual = new Funcao();
		funcaoAtual.setId(1L);

		Funcao funcaoProposta = new Funcao();
		funcaoProposta.setId(2L);

		ReajusteColaborador reajuste = new ReajusteColaborador();
		reajuste.setColaborador(colaborador);
		reajuste.setTipoSalarioAtual(TipoAplicacaoIndice.VALOR);
		reajuste.setSalarioAtual(10.00);
		reajuste.setTipoSalarioProposto(TipoAplicacaoIndice.VALOR);
		reajuste.setSalarioProposto(20.00);
		reajuste.setAreaOrganizacionalProposta(areaOrganizacionalProposta);
		reajuste.setFaixaSalarialAtual(faixaSalarialAtual);
		reajuste.setFaixaSalarialProposta(faixaSalarialProposta);
		reajuste.setFuncaoAtual(funcaoAtual);
		reajuste.setFuncaoProposta(funcaoProposta);

		String motivo = HistoricoColaboradorUtil.getMotivoReajuste(reajuste, historicoColaborador);

		assertEquals("Test 1", MotivoHistoricoColaborador.PROMOCAO_VERTICAL, motivo);

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