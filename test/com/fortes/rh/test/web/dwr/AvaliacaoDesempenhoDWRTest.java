package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.web.dwr.AvaliacaoDesempenhoDWR;

public class AvaliacaoDesempenhoDWRTest 
{
	private AvaliacaoDesempenhoDWR avaliacaoDesempenhoDWR;
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;

	@Before
	public void setUp() throws Exception
	{
		avaliacaoDesempenhoDWR = new AvaliacaoDesempenhoDWR();
		avaliacaoDesempenhoManager = mock(AvaliacaoDesempenhoManager.class);
		avaliacaoDesempenhoDWR.setAvaliacaoDesempenhoManager(avaliacaoDesempenhoManager);
	}

	@Test
	public void testGetByEmpresa()
	{
		Long empresaId = 1L;
		when(avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO)).thenReturn(new ArrayList<AvaliacaoDesempenho>());
		
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesByEmpresa(empresaId).size());
	}
	
	@Test
	public void testGetAvaliacoesNaoLiberadasByTitulo()
	{
		Long empresaId = 1L;
		String titulo = "teste";
		when(avaliacaoDesempenhoManager.findTituloModeloAvaliacao(anyInt(), anyInt(), any(Date.class), any(Date.class), eq(empresaId), eq(titulo), anyLong(), eq(true))).thenReturn(new ArrayList<AvaliacaoDesempenho>());
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesNaoLiberadasByTitulo(empresaId, titulo).size());
	}
	
	@Test
	public void testGetAvaliacoesDesempenhoByModelo()
	{
		Long modeloId = 2L;
		
		when(avaliacaoDesempenhoManager.findByModelo(modeloId)).thenReturn(new ArrayList<AvaliacaoDesempenho>());
		assertEquals(0, avaliacaoDesempenhoDWR.getAvaliacoesDesempenhoByModelo(modeloId).size());
	}
	
	@Test
	public void testGetEstabelecimentosDosParticipantes()
	{
		Long[] avaliacoesDesempenhoIds = new Long[]{1L};

		Estabelecimento estabelecimento1 = EstabelecimentoFactory.getEntity(1L);
		estabelecimento1.setNome("Estabelecimento A");
		
		Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity(2L);
		estabelecimento2.setNome("Estabelecimento B");
		
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento1, estabelecimento2);

		when(avaliacaoDesempenhoManager.findEstabelecimentosDosParticipantes(avaliacoesDesempenhoIds)).thenReturn(estabelecimentos);
		
		Map<Long, String> mapEstabelecimentos = avaliacaoDesempenhoDWR.getEstabelecimentosDosParticipantes(avaliacoesDesempenhoIds);

		Assert.assertEquals(estabelecimentos.size(), mapEstabelecimentos.size());
		Assert.assertEquals(estabelecimento1.getNome(), mapEstabelecimentos.get(estabelecimento1.getId()));
		Assert.assertEquals(estabelecimento2.getNome(), mapEstabelecimentos.get(estabelecimento2.getId()));
	}
	
	@Test
	public void testGetAreasOrganizacionaisDosParticipantes()
	{
		Long[] avaliacoesDesempenhoIds = new Long[]{1L};
		
		AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional1.setNome("Area Organizacional A");
		
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional2.setNome("Area Organizacional B");
		
		Collection<AreaOrganizacional> areaOrganizacionais = Arrays.asList(areaOrganizacional1, areaOrganizacional2);
		
		when(avaliacaoDesempenhoManager.findAreasOrganizacionaisDosParticipantes(avaliacoesDesempenhoIds)).thenReturn(areaOrganizacionais);
		
		Map<Long, String> mapAreas = avaliacaoDesempenhoDWR.getAreasOrganizacionaisDosParticipantes(avaliacoesDesempenhoIds);
		
		Assert.assertEquals(areaOrganizacionais.size(), mapAreas.size());
		Assert.assertEquals(areaOrganizacional1.getDescricaoStatusAtivo(), mapAreas.get(areaOrganizacional1.getId()));
		Assert.assertEquals(areaOrganizacional2.getDescricaoStatusAtivo(), mapAreas.get(areaOrganizacional2.getId()));
	}	
	
	@Test
	public void testGetCargossDosParticipantes()
	{
		Long[] avaliacoesDesempenhoIds = new Long[]{1L};
		
		Cargo cargo1 = CargoFactory.getEntity(1L);
		cargo1.setNome("Cargo A");
		
		Cargo cargo2 = CargoFactory.getEntity(2L);
		cargo2.setNome("Cargo B");
		
		Collection<Cargo> cargos = Arrays.asList(cargo1, cargo2);
		
		when(avaliacaoDesempenhoManager.findCargosDosParticipantes(avaliacoesDesempenhoIds)).thenReturn(cargos);
		
		Map<Long, String> mapCargos = avaliacaoDesempenhoDWR.getCargosDosParticipantes(avaliacoesDesempenhoIds);
		
		Assert.assertEquals(cargos.size(), mapCargos.size());
		Assert.assertEquals(cargo1.getNomeMercadoComStatus(), mapCargos.get(cargo1.getId()));
		Assert.assertEquals(cargo2.getNomeMercadoComStatus(), mapCargos.get(cargo2.getId()));
	}
}
