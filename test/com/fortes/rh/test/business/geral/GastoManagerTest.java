package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.GastoManagerImpl;
import com.fortes.rh.dao.geral.GastoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;

public class GastoManagerTest extends MockObjectTestCase
{
	GastoManagerImpl gastoManager = null;
	Mock gastoDao = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		gastoManager = new GastoManagerImpl();
		gastoDao = new Mock(GastoDao.class);
		gastoManager.setDao((GastoDao) gastoDao.proxy());
	}
	
	public void testDeveriaAgruparQuandoHouverGastos() throws Exception {
		
		GrupoGasto grupo = new GrupoGasto();
		grupo.setId(69L);
		
		String[] gastosCheck = new String[] { "1", "2", "3" };
		
		gastoDao.expects(once())
			.method("updateGrupoGastoByGastos")
				.with(eq(69L), eq(new Long[]{1L, 2L, 3L}));
		
		gastoManager.agrupar(grupo, gastosCheck);
		
		gastoDao.verify();
	}
	
	public void testFindGastosDoGrupo() {
		
		Long grupoId = 69L;
		
		dadoQueExisteGastosParaOGrupoDeId(grupoId);
		
		Collection<Gasto> gastos = gastoManager.findGastosDoGrupo(grupoId);
		
		assertEquals("gastos encontrados", 1, gastos.size());
	}
	
	@SuppressWarnings("unchecked")
	private void dadoQueExisteGastosParaOGrupoDeId(Long grupoId) {
		Collection<Gasto> gastos = Arrays.asList(new Gasto[]{new Gasto()});
		
		gastoDao.expects(once())
			.method("findGastosDoGrupo")
				.with(eq(grupoId)).will(returnValue(gastos));
	}

	public void testGastosSemGrupo() {
		Long empresaId = 69L;

		dadoQueExistemGastosSemGrupoCadastradosParaEmpresaComId(empresaId);
		
		Collection<Gasto> gastos = gastoManager.getGastosSemGrupo(empresaId);
		
		assertEquals("gastos encontrados", 1, gastos.size());
	}

	@SuppressWarnings("unchecked")
	private void dadoQueExistemGastosSemGrupoCadastradosParaEmpresaComId(Long empresaId) {

		Collection<Gasto> gastos = Arrays.asList(new Gasto[]{new Gasto()});
		
		gastoDao.expects(once())
			.method("getGastosSemGrupo")
				.with(eq(empresaId)).will(returnValue(gastos));
	}

	public void testFindByEmpresa()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);

		Gasto gasto = new Gasto();
		gasto.setId(1L);
		gasto.setEmpresa(empresa);

		Collection<Gasto> gastos = new ArrayList<Gasto>();
		gastos.add(gasto);

		gastoDao.expects(once()).method("findByEmpresa").with(eq(empresa.getId())).will(returnValue(gastos));

		Collection<Gasto> retorno = gastoManager.findByEmpresa(empresa.getId());

		assertEquals(1, retorno.size());
	}

	public void testFindByIdProjection() throws Exception
	{
		Gasto gasto = new Gasto();
		gasto.setId(1L);

		gastoDao.expects(once()).method("findByIdProjection").with(eq(gasto.getId())).will(returnValue(gasto));

		Gasto gastoRetorno = gastoManager.findByIdProjection(gasto.getId());

		assertEquals(gasto, gastoRetorno);
	}

}