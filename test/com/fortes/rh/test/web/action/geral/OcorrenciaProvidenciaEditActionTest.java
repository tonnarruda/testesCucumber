package com.fortes.rh.test.web.action.geral;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.OcorrenciaProvidenciaEditAction;

public class OcorrenciaProvidenciaEditActionTest
{
	private OcorrenciaProvidenciaEditAction action;
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ProvidenciaManager providenciaManager;
	FaixaSalarialManager faixaSalarialManager; 

	@Before
	public void setUp() throws Exception {
		action = new OcorrenciaProvidenciaEditAction();
		colaboradorOcorrenciaManager = mock(ColaboradorOcorrenciaManager.class);
		action.setColaboradorOcorrenciaManager(colaboradorOcorrenciaManager);
		
		providenciaManager = mock(ProvidenciaManager.class);
		action.setProvidenciaManager(providenciaManager);
		
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		
		
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresa);
	}
		
	@After
	public void tearDown() throws Exception {
        MockSecurityUtil.verifyRole = false;
    }
	
	@Test
	public void prepare() throws Exception {
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(1L);
		action.setColaboradorOcorrencia(colaboradorOcorrencia);
		
		when(colaboradorOcorrenciaManager.findById(colaboradorOcorrencia.getId())).thenReturn(colaboradorOcorrencia);
		when(providenciaManager.find(new String[]{"empresa.id"}, new Object[]{action.getEmpresaSistema().getId()}, new String[]{"descricao"})).thenReturn(new ArrayList<Providencia>());
		
		assertEquals("success", action.prepare());
	}

	@Test
	public void update() throws Exception {
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(1L);
		colaboradorOcorrencia.setProvidenciaId(1L);
		action.setColaboradorOcorrencia(colaboradorOcorrencia);
		assertEquals("success", action.update());
	}
	
	@Test
	public void list() throws Exception
	{
		assertEquals("success", action.list());
	}
}