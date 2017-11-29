package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.dwr.ColaboradorDWR;
import com.fortes.rh.web.dwr.HistoricoColaboradorDWR;
import com.fortes.web.tags.CheckBox;

public class HistoricoColaboradorDWRTest
{
	private HistoricoColaboradorDWR historicoColaboradorDWR;
	private ColaboradorManager colaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;

	@Before
	public void setUp() throws Exception
	{
		historicoColaboradorDWR = new HistoricoColaboradorDWR();

		colaboradorManager = mock(ColaboradorManager.class);
		faixaSalarialManager = mock(FaixaSalarialManager.class);

		historicoColaboradorDWR.setColaboradorManager(colaboradorManager);
		historicoColaboradorDWR.setFaixaSalarialManager(faixaSalarialManager);
	}

	
	@Test
	public void testGetFaixasColaborador() {
		String tipoPessoaChave = "COLABORADOR";
		String dataSolicitacaoExame = "25/04/2017";

		Empresa empresa = EmpresaFactory.getEmpresa();
		Colaborador colaborador = ColaboradorFactory.getEntity(1l);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1l);

		when(colaboradorManager.findByData(colaborador.getId(), DateUtil.criarDataDiaMesAno(dataSolicitacaoExame))).thenReturn(colaborador);
		when(faixaSalarialManager.findFaixas(empresa, Cargo.ATIVO, null)).thenReturn(Arrays.asList(faixaSalarial));

		Collection<FaixaSalarial> faixas = historicoColaboradorDWR.getFaixas(tipoPessoaChave, dataSolicitacaoExame, colaborador.getId(), empresa.getId());

		assertEquals(1, faixas.size());
	}
	
	@Test
	public void testGetFaixasColaboradorSemDataSolicitacaoExame() {
		String tipoPessoaChave = "COLABORADOR";
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		Colaborador colaborador = ColaboradorFactory.getEntity(1l);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1l);
		
		when(colaboradorManager.findByData(eq(colaborador.getId()), any(Date.class))).thenReturn(colaborador);
		when(faixaSalarialManager.findFaixas(empresa, Cargo.ATIVO, null)).thenReturn(Arrays.asList(faixaSalarial));
		
		Collection<FaixaSalarial> faixas = historicoColaboradorDWR.getFaixas(tipoPessoaChave, "", colaborador.getId(), empresa.getId());
		
		assertEquals(1, faixas.size());
	}
	
	@Test
	public void testGetFaixasCandidato()
	{
		String tipoPessoaChave="CANDIDATO";
		String dataSolicitacaoExame = "25/04/2017";
		
		Empresa empresa = EmpresaFactory.getEmpresa(1l);
		Colaborador colaborador = ColaboradorFactory.getEntity(1l);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1l);
		
		when(colaboradorManager.findByData(colaborador.getId(),DateUtil.criarDataDiaMesAno(dataSolicitacaoExame))).thenReturn(colaborador);
		when(faixaSalarialManager.findFaixas(empresa, Cargo.ATIVO, null)).thenReturn(Arrays.asList(faixaSalarial));
		
		Collection<FaixaSalarial> faixas = historicoColaboradorDWR.getFaixas(tipoPessoaChave, dataSolicitacaoExame, colaborador.getId(), empresa.getId());
		
		assertEquals(1, faixas.size());
	}	
}