package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.business.sesmt.PcmatManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ObraFactory;
import com.fortes.rh.test.factory.sesmt.PcmatFactory;
import com.fortes.rh.web.action.sesmt.PcmatEditAction;

public class PcmatEditActionTest extends MockObjectTestCase
{
	private PcmatEditAction action;
	private Mock manager;
	private Mock obraManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		action = new PcmatEditAction();
		
		manager = new Mock(PcmatManager.class);
		action.setPcmatManager((PcmatManager) manager.proxy());

		obraManager = new Mock(ObraManager.class);
		action.setObraManager((ObraManager) obraManager.proxy());

		action.setPcmat(new Pcmat());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa());
	}

	protected void tearDown() throws Exception
	{
		manager = null;
		action = null;
		super.tearDown();
	}

	public void testList() throws Exception
	{
		obraManager.expects(once()).method("findAllSelect").will(returnValue(new ArrayList<Obra>()));
		assertEquals("success", action.list());
		assertNotNull(action.getObras());
	}

	public void testDelete() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);

		manager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("success", action.delete());
	}

	public void testClonar() throws Exception
	{
		Obra obra =  ObraFactory.getEntity(1L);
		obra.setNome("Fortes");
		action.setObra(obra);
		
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("validaDataMaiorQueUltimoHistorico").withAnyArguments().isVoid();
		manager.expects(once()).method("clonar").withAnyArguments().isVoid();
		manager.expects(once()).method("findByObra").with(eq(obra.getId())).will(returnValue(new ArrayList<Pcmat>()));
		manager.expects(once()).method("findUltimoHistorico").with(eq(null), eq(obra.getId())).will(returnValue(pcmat));
		obraManager.expects(once()).method("findByIdProjection").with(eq(obra.getId())).will(returnValue(obra));
		
		assertEquals("success", action.clonar());
	}
	
	public void testClonarException() throws Exception
	{
		Obra obra =  ObraFactory.getEntity(1L);
		obra.setNome("Fortes");
		action.setObra(obra);
		
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		action.setPcmat(pcmat);
		
		manager.expects(once()).method("validaDataMaiorQueUltimoHistorico").will(throwException(new FortesException("")));
		manager.expects(once()).method("findByObra").with(eq(obra.getId())).will(returnValue(new ArrayList<Pcmat>()));
		manager.expects(once()).method("findUltimoHistorico").with(eq(null), eq(obra.getId())).will(returnValue(pcmat));
		obraManager.expects(once()).method("findByIdProjection").with(eq(obra.getId())).will(returnValue(obra));
		
		assertEquals("success", action.clonar());
	}

	public void testInsert() throws Exception
	{
		Obra obra =  ObraFactory.getEntity(1L);
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		pcmat.setObra(obra);
		action.setPcmat(pcmat);

		manager.expects(once()).method("validaDataMaiorQueUltimoHistorico").withAnyArguments().isVoid();
		manager.expects(once()).method("save").with(eq(pcmat)).will(returnValue(pcmat));

		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Obra obra =  ObraFactory.getEntity(1L);
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		pcmat.setObra(obra);
		action.setPcmat(pcmat);

		manager.expects(once()).method("validaDataMaiorQueUltimoHistorico").withAnyArguments().isVoid();
		manager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		assertEquals("input", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Obra obra =  ObraFactory.getEntity(1L);
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		pcmat.setObra(obra);
		action.setPcmat(pcmat);

		manager.expects(once()).method("validaDataMaiorQueUltimoHistorico").withAnyArguments().isVoid();
		manager.expects(once()).method("update").with(eq(pcmat)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Obra obra =  ObraFactory.getEntity(1L);
		Pcmat pcmat = PcmatFactory.getEntity(1L);
		pcmat.setObra(obra);
		action.setPcmat(pcmat);

		manager.expects(once()).method("validaDataMaiorQueUltimoHistorico").withAnyArguments().isVoid();
		manager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));

		assertEquals("input", action.update());
	}

	public void testGetSet() throws Exception
	{
		action.setPcmat(null);

		assertNotNull(action.getPcmat());
		assertTrue(action.getPcmat() instanceof Pcmat);
	}
}
