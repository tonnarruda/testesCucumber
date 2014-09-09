package com.fortes.rh.test.dao.hibernate.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.TipoDespesaDao;
import com.fortes.rh.dao.geral.TurmaTipoDespesaDao;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.geral.TipoDespesaFactory;
import com.fortes.rh.test.factory.geral.TurmaTipoDespesaFactory;
import com.fortes.rh.util.DateUtil;

public class TurmaTipoDespesaDaoHibernateTest extends GenericDaoHibernateTest<TurmaTipoDespesa>
{
	private TurmaTipoDespesaDao turmaTipoDespesaDao;
	private TurmaDao turmaDao;
	private TipoDespesaDao tipoDespesaDao;
	private EmpresaDao empresaDao;
	
	@Override
	public TurmaTipoDespesa getEntity()
	{
		return TurmaTipoDespesaFactory.getEntity();
	}

	@Override
	public GenericDao<TurmaTipoDespesa> getGenericDao()
	{
		return turmaTipoDespesaDao;
	}

	public void setTurmaTipoDespesaDao(TurmaTipoDespesaDao turmaTipoDespesaDao)
	{
		this.turmaTipoDespesaDao = turmaTipoDespesaDao;
	}
	
	public void testFindTipoDespesaTurma() 
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity();
		tipoDespesaDao.save(tipoDespesa);
		
		TurmaTipoDespesa turmaTipoDespesa = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa.setTurma(turma);
		turmaTipoDespesa.setTipoDespesa(tipoDespesa);
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		
		assertEquals(1, turmaTipoDespesaDao.findTipoDespesaTurma(turma.getId()).size());
	}

	public void testRemoveByTurma() 
	{
		Exception ex = null;
		try {
			turmaTipoDespesaDao.removeByTurma(1L);
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	public void testRemove() 
	{
		Turma turma = TurmaFactory.getEntity();
		turmaDao.save(turma);
		
		TipoDespesa tipoDespesa = TipoDespesaFactory.getEntity();
		tipoDespesaDao.save(tipoDespesa);
		
		TurmaTipoDespesa turmaTipoDespesa = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa.setTurma(turma);
		turmaTipoDespesa.setTipoDespesa(tipoDespesa);
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		
		turmaTipoDespesaDao.save(turmaTipoDespesa);
		TurmaTipoDespesa turmaTipoDespesaResult = (TurmaTipoDespesa) turmaTipoDespesaDao.findById(turmaTipoDespesa.getId());
		
		assertEquals(turma.getId(),turmaTipoDespesaResult.getTurma().getId());
		
		Exception ex = null;
		try {
			turmaTipoDespesaDao.remove(turmaTipoDespesaResult.getId());
		} catch (Exception e) {
			ex = e;
		}
		assertNull(ex);
	}
	
	public void testSomaDespesasPorTipo() 
	{
		Date dataIni = DateUtil.criarDataMesAno(1, 1, 2012);
		Date dataFim = DateUtil.criarDataMesAno(2, 1, 2012);
		
		Empresa empresa1 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa1);
		
		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);
		
		Turma turma1 = TurmaFactory.getEntity();
		turma1.setEmpresa(empresa1);
		turma1.setDataPrevIni(dataIni);
		turma1.setDataPrevFim(dataFim);
		turma1.setRealizada(true);
		turmaDao.save(turma1);
		
		Turma turma2 = TurmaFactory.getEntity();
		turma2.setEmpresa(empresa2);
		turma2.setDataPrevIni(dataIni);
		turma2.setDataPrevFim(dataFim);
		turma2.setRealizada(true);
		turmaDao.save(turma2);
		
		TipoDespesa tipoDespesaAlim = TipoDespesaFactory.getEntity();
		tipoDespesaAlim.setDescricao("alimentacao");
		tipoDespesaDao.save(tipoDespesaAlim);

		TipoDespesa tipoDespesaTrans = TipoDespesaFactory.getEntity();
		tipoDespesaTrans.setDescricao("transporte");
		tipoDespesaDao.save(tipoDespesaTrans);
		
		TurmaTipoDespesa turmaTipoDespesa1 = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa1.setTurma(turma1);
		turmaTipoDespesa1.setTipoDespesa(tipoDespesaAlim);
		turmaTipoDespesa1.setDespesa(200.0);
		turmaTipoDespesaDao.save(turmaTipoDespesa1);
		
		TurmaTipoDespesa turmaTipoDespesa2 = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa2.setTurma(turma1);
		turmaTipoDespesa2.setTipoDespesa(tipoDespesaAlim);
		turmaTipoDespesa2.setDespesa(150.0);
		turmaTipoDespesaDao.save(turmaTipoDespesa2);
		
		TurmaTipoDespesa turmaTipoDespesa3 = TurmaTipoDespesaFactory.getEntity();
		turmaTipoDespesa3.setTurma(turma1);
		turmaTipoDespesa3.setTipoDespesa(tipoDespesaTrans);
		turmaTipoDespesa3.setDespesa(75.0);
		turmaTipoDespesaDao.save(turmaTipoDespesa3);
		
		Collection<TipoDespesa> tipoDespesas = turmaTipoDespesaDao.somaDespesasPorTipo(dataIni, dataFim, new Long[]{empresa1.getId(), empresa2.getId()}, null);
		Collection<TipoDespesa> tipoDespesasOutraEmpresa = turmaTipoDespesaDao.somaDespesasPorTipo(dataIni, dataFim, new Long[]{1111111111111L}, null);
		
		assertEquals(2, tipoDespesas.size());
		assertEquals(350.0, ((TipoDespesa)tipoDespesas.toArray()[0]).getTotalDespesas());
		assertEquals(75.0, ((TipoDespesa)tipoDespesas.toArray()[1]).getTotalDespesas());
		assertEquals(0, tipoDespesasOutraEmpresa.size());
	}
	
	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public void setTipoDespesaDao(TipoDespesaDao tipoDespesaDao) {
		this.tipoDespesaDao = tipoDespesaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao) {
		this.empresaDao = empresaDao;
	}
}
