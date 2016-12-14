package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.web.dwr.ColaboradorDWR;
import com.fortes.web.tags.CheckBox;

public class ColaboradorDWRTest_JUnit4
{
	private ColaboradorDWR colaboradorDWR;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private HistoricoColaboradorManager historicoColaboradorManager;

	@Before
	public void setUp() throws Exception
	{
		colaboradorDWR = new ColaboradorDWR();

		colaboradorManager = mock(ColaboradorManager.class);
		empresaManager = mock(EmpresaManager.class);
		historicoColaboradorManager = mock(HistoricoColaboradorManager.class);

		colaboradorDWR.setColaboradorManager(colaboradorManager);
		colaboradorDWR.setEmpresaManager(empresaManager);
		colaboradorDWR.setHistoricoColaboradorManager(historicoColaboradorManager);
	}

	@Test
	public void testGetByAreasIds()
	{
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		colaborador1.setNome("nomeColab1");
		colaborador1.setNomeComercial("nomeComercial1");
		colaborador1.setAreaOrganizacionalNome("AreaNome");
		colaborador1.setEstabelecimentoNomeProjection("estabelecimentoNome");
		colaborador1.setEmpresaNome("empresaNome");
		colaborador1.setAreaOrganizacionalId(1L);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		colaborador2.setNome("nomeColab2");
		colaborador2.setNomeComercial("nomeComercial2");
		colaborador2.setEmpresaNome("empresaNome");
		colaborador2.setEstabelecimentoNomeProjection("estabelecimentoNome");
		colaborador2.setAreaOrganizacionalNome("AreaNome");
		colaborador2.setAreaOrganizacionalId(1L);
		
		Collection<Colaborador> colaboradores = Arrays.asList(colaborador1, colaborador2);
		
		Long[] areasIds = new Long[]{1L};
		
		when(colaboradorManager.findByAreasIds(areasIds)).thenReturn(colaboradores);
		
		Collection<CheckBox> checkBoxs = colaboradorDWR.getByAreasIds(areasIds);
		
		assertEquals(2, checkBoxs.size());
		assertEquals(colaborador1.getNomeMaisNomeComercial(), ((CheckBox)checkBoxs.toArray()[0]).getNome());
		assertEquals(5, ((CheckBox)checkBoxs.toArray()[1]).getParameters().size());
	}	
}