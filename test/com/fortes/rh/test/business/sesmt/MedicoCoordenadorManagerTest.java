package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.model.type.File;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManagerImpl;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

public class MedicoCoordenadorManagerTest extends MockObjectTestCase
{
	private MedicoCoordenadorManagerImpl medicoCoordenadorManager;
	private Mock medicoCoordenadorDao;

	protected void setUp() throws Exception
    {
        super.setUp();

        medicoCoordenadorManager = new MedicoCoordenadorManagerImpl();

        medicoCoordenadorDao = new Mock(MedicoCoordenadorDao.class);

        medicoCoordenadorManager.setDao((MedicoCoordenadorDao) medicoCoordenadorDao.proxy());
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

	public void testFindByDataEmpresa() throws Exception
	{
		MedicoCoordenador medico = new MedicoCoordenador();
		medico.setId(1L);

		Empresa empresa = new Empresa();
		empresa.setId(2L);

		Date data = new Date();

		medicoCoordenadorDao.expects(once()).method("findByDataEmpresa").with(eq(empresa.getId()), eq(data)).will(returnValue(medico));

		MedicoCoordenador medicoRetorno = medicoCoordenadorManager.findByDataEmpresa(empresa.getId(), data);

		assertEquals(medico, medicoRetorno);
	}

	public void testFindByIdProjection() throws Exception
	{
		MedicoCoordenador medico = new MedicoCoordenador();
		medico.setId(1L);

		Empresa empresa = new Empresa();
		empresa.setId(2L);

		medicoCoordenadorDao.expects(once()).method("findByIdProjection").with(eq(medico.getId())).will(returnValue(medico));

		MedicoCoordenador medicoRetorno = medicoCoordenadorManager.findByIdProjection(medico.getId());

		assertEquals(medico, medicoRetorno);
	}

	public void testGetAssinaturaDigital() throws Exception
	{
		Long id = 1L;
		medicoCoordenadorDao.expects(once()).method("getFile").with(eq("assinaturaDigital"), eq(id)).will(returnValue(new File()));

		assertNotNull(medicoCoordenadorManager.getAssinaturaDigital(id));
	}

	public void testGetAssinaturaDigitalException()
	{
		Long id = 1L;
		Exception exception = null;

		try
		{
			medicoCoordenadorDao.expects(once()).method("getFile").with(eq("assinaturaDigital"), eq(id)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(id,""))));
			medicoCoordenadorManager.getAssinaturaDigital(id);
		}
		catch(Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}
	
	public void testFindByEmpresa()
	{
		medicoCoordenadorDao.expects(once()).method("findByEmpresa").with(eq(1L),ANYTHING).will(returnValue(new ArrayList<MedicoCoordenador>()));
		assertNotNull(medicoCoordenadorManager.findByEmpresa(1L));
	}
	
	public void testGetMedicosAteData()
	{
		Calendar hoje = Calendar.getInstance();
		Calendar antes = Calendar.getInstance();
		antes.add(Calendar.MONTH, -2);
		Calendar depois = Calendar.getInstance();
		depois.add(Calendar.MONTH, +2);
		
		MedicoCoordenador medicoCoordenador1 = new MedicoCoordenador();
		medicoCoordenador1.setCrm("123");
		medicoCoordenador1.setNome("João Adalberto Jr.");
		medicoCoordenador1.setInicio(antes.getTime());
		medicoCoordenador1.setFim(depois.getTime());
		
		MedicoCoordenador medicoCoordenador2 = new MedicoCoordenador();
		medicoCoordenador2.setCrm("5235");
		medicoCoordenador2.setNome("Maria Joaquina Silva");
		medicoCoordenador2.setInicio(hoje.getTime());
		medicoCoordenador2.setFim(null);
		
		MedicoCoordenador medicoCoordenadorFora = new MedicoCoordenador();
		medicoCoordenadorFora.setCrm("995");
		medicoCoordenadorFora.setNome("Amaral Gomes");
		medicoCoordenadorFora.setInicio(depois.getTime());
		medicoCoordenadorFora.setFim(depois.getTime());
		
		Collection<MedicoCoordenador> medicoCoordenadors = new ArrayList<MedicoCoordenador>();
		medicoCoordenadors.add(medicoCoordenador1);
		medicoCoordenadors.add(medicoCoordenador2);
		medicoCoordenadors.add(medicoCoordenadorFora);
		
		medicoCoordenadorDao.expects(once()).method("findByEmpresa").with(eq(1L),ANYTHING).will(returnValue(medicoCoordenadors));
		
		Collection<MedicoCoordenador> resultado = medicoCoordenadorManager.getMedicosAteData(1L, hoje.getTime(), dataDesligamento);
		
		assertEquals(2, resultado.size());
		
		MedicoCoordenador medico2 = ((MedicoCoordenador) resultado.toArray()[1]);
		assertEquals("Deve ter anulado a data FIM, pois ela é futura à data desejada, então fica em aberto", 
				null, medico2.getFim());
	}

}