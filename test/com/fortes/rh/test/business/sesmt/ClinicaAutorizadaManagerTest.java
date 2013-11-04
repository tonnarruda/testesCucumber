package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ClinicaAutorizadaManagerImpl;
import com.fortes.rh.dao.sesmt.ClinicaAutorizadaDao;
import com.fortes.rh.model.dicionario.TipoClinica;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.test.factory.sesmt.ExameFactory;

public class ClinicaAutorizadaManagerTest extends MockObjectTestCase
{
	private ClinicaAutorizadaManagerImpl clinicaAutorizadaManager;
	private Mock clinicaAutorizadaDao;

	protected void setUp() throws Exception
    {
        super.setUp();

        clinicaAutorizadaManager = new ClinicaAutorizadaManagerImpl();

        clinicaAutorizadaDao = new Mock(ClinicaAutorizadaDao.class);

        clinicaAutorizadaManager.setDao((ClinicaAutorizadaDao) clinicaAutorizadaDao.proxy());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

	public void testFindByDataEmpresa() throws Exception
	{
		ClinicaAutorizada clinica = new ClinicaAutorizada();
		clinica.setId(1L);

		Collection<ClinicaAutorizada> clinicas = new ArrayList<ClinicaAutorizada>();

		clinicas.add(clinica);

		Empresa empresa = new Empresa();
		empresa.setId(2L);

		Date data = new Date();

		clinicaAutorizadaDao.expects(once()).method("findByDataEmpresa").with(eq(empresa.getId()), eq(data), eq(true)).will(returnValue(clinicas));

		Collection<ClinicaAutorizada> clinicasRetorno = clinicaAutorizadaManager.findByDataEmpresa(empresa.getId(), data, true);

		assertEquals(1, clinicasRetorno.size());
	}

	public void testFindByExame() throws Exception
	{
		ClinicaAutorizada clinica = new ClinicaAutorizada();
		clinica.setId(1L);
		
		Collection<ClinicaAutorizada> clinicas = new ArrayList<ClinicaAutorizada>();
		
		clinicas.add(clinica);
		
		Empresa empresa = new Empresa();
		empresa.setId(2L);
		
		Date data = new Date();
		
		Exame exame = ExameFactory.getEntity(1L);
		exame.setEmpresa(empresa);
		
		clinicaAutorizadaDao.expects(once()).method("findByExame").with(eq(empresa.getId()),eq(exame.getId()), eq(data)).will(returnValue(clinicas));
		
		Collection<ClinicaAutorizada> clinicasRetorno = clinicaAutorizadaManager.findByExame(empresa.getId(), exame.getId(), data );
		
		assertEquals(1, clinicasRetorno.size());
	}

	@SuppressWarnings("static-access")
	public void testSelecionaPorTipo()
	{
		TipoClinica tipo = new TipoClinica();
		ClinicaAutorizada clinica1 = new ClinicaAutorizada();
		clinica1.setId(1L);
		clinica1.setTipo(tipo.CLINICA);

		ClinicaAutorizada medico1 = new ClinicaAutorizada();
		medico1.setId(1L);
		medico1.setTipo(tipo.MEDICO);

		ClinicaAutorizada clinica2 = new ClinicaAutorizada();
		clinica2.setId(1L);
		clinica2.setTipo(tipo.CLINICA);

		ClinicaAutorizada medico2 = new ClinicaAutorizada();
		medico2.setId(1L);
		medico2.setTipo(tipo.MEDICO);

		ClinicaAutorizada medico3 = new ClinicaAutorizada();
		medico3.setId(1L);
		medico3.setTipo(tipo.MEDICO);


		Collection<ClinicaAutorizada> clinicaMedicos = new ArrayList<ClinicaAutorizada>();
		clinicaMedicos.add(clinica1);
		clinicaMedicos.add(clinica2);
		clinicaMedicos.add(medico1);
		clinicaMedicos.add(medico2);
		clinicaMedicos.add(medico3);

		Collection<ClinicaAutorizada> clinicas = clinicaAutorizadaManager.selecionaPorTipo(clinicaMedicos, tipo.CLINICA);
		assertEquals(2, clinicas.size());

		Collection<ClinicaAutorizada> medicos = clinicaAutorizadaManager.selecionaPorTipo(clinicaMedicos, tipo.MEDICO);
		assertEquals(3, medicos.size());

	}

}