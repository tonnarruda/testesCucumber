package com.fortes.rh.test.dao.hibernate.sesmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.FuncaoDao;
import com.fortes.rh.dao.sesmt.HistoricoFuncaoDao;
import com.fortes.rh.dao.sesmt.RiscoDao;
import com.fortes.rh.dao.sesmt.RiscoFuncaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest_JUnit4;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFactory;
import com.fortes.rh.test.factory.sesmt.RiscoFuncaoFactory;
import com.fortes.rh.util.DateUtil;

public class RiscoFuncaoDaoHibernateTest extends GenericDaoHibernateTest_JUnit4<RiscoFuncao>
{
	@Autowired private RiscoFuncaoDao riscoFuncaoDao;
	@Autowired private HistoricoFuncaoDao historicoFuncaoDao;
	@Autowired private FuncaoDao funcaoDao;
	@Autowired private EmpresaDao empresaDao;
	@Autowired private RiscoDao riscoDao;

	@Override
	public RiscoFuncao getEntity()
	{
		return RiscoFuncaoFactory.getEntity();
	}

	@Override
	public GenericDao<RiscoFuncao> getGenericDao()
	{
		return riscoFuncaoDao;
	}
	
	@Test
	public void testRemoveByHistoricoFuncao()
	{
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncaoDao.save(historicoFuncao);
		
		RiscoFuncao riscoFuncao = RiscoFuncaoFactory.getEntity();
		riscoFuncao.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao);
		
		assertTrue(riscoFuncaoDao.removeByHistoricoFuncao(historicoFuncao.getId()));
	}

	@Test
	public void testRemoveByFuncao()
	{
		Funcao funcao = new Funcao();
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncaoDao.save(historicoFuncao);
		
		RiscoFuncao riscoFuncao = RiscoFuncaoFactory.getEntity();
		riscoFuncao.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao);
		
		Exception exception = null;
		try {
			riscoFuncaoDao.removeByFuncao(historicoFuncao.getId());
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}

	@Test
	public void testFindRiscosByFuncaoEData()
	{
		Date hoje = DateUtil.criarDataMesAno(29, 3, 2012);
		Date doisMesesAtras = DateUtil.incrementaAno(hoje, -2);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Funcao funcao = new Funcao();
		funcaoDao.save(funcao);

		Funcao funcao2 = new Funcao();
		funcaoDao.save(funcao2);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setData(hoje);
		historicoFuncao.setFuncao(funcao);
		historicoFuncaoDao.save(historicoFuncao);

		HistoricoFuncao historicoFuncaoAntigo = new HistoricoFuncao();
		historicoFuncaoAntigo.setData(doisMesesAtras);
		historicoFuncaoAntigo.setFuncao(funcao2);
		historicoFuncaoDao.save(historicoFuncaoAntigo);
		
		Risco risco1 = RiscoFactory.getEntity();
		risco1.setEmpresa(empresa);
		risco1.setDescricao("Calor");
		riscoDao.save(risco1);

		Risco risco2 = RiscoFactory.getEntity();
		risco2.setEmpresa(empresa);
		risco2.setDescricao("Ruído");
		riscoDao.save(risco2);
		
		RiscoFuncao riscoFuncao1 = RiscoFuncaoFactory.getEntity();
		riscoFuncao1.setRisco(risco1);
		riscoFuncao1.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao1);
		
		RiscoFuncao riscoFuncao2 = RiscoFuncaoFactory.getEntity();
		riscoFuncao2.setRisco(risco2);
		riscoFuncao2.setHistoricoFuncao(historicoFuncaoAntigo);
		riscoFuncaoDao.save(riscoFuncao2);

		RiscoFuncao riscoFuncao3 = RiscoFuncaoFactory.getEntity();
		riscoFuncao3.setRisco(risco2);
		riscoFuncao3.setHistoricoFuncao(historicoFuncaoAntigo);
		riscoFuncaoDao.save(riscoFuncao3);
		
		Collection<Risco> riscos = riscoFuncaoDao.findRiscosByFuncaoData(funcao.getId(), hoje);
		
		assertEquals(1, riscos.size());
		assertEquals(risco1, (Risco)riscos.toArray()[0]);
	}
	
	@Test
	public void testRemoveByFuncoes()
	{
		Funcao funcao = new Funcao();
		funcaoDao.save(funcao);
		
		HistoricoFuncao historicoFuncao = new HistoricoFuncao();
		historicoFuncao.setFuncao(funcao);
		historicoFuncaoDao.save(historicoFuncao);
		
		Risco risco = RiscoFactory.getEntity();
		riscoDao.save(risco);
		
		RiscoFuncao riscoFuncao = RiscoFuncaoFactory.getEntity();
		riscoFuncao.setRisco(risco);
		riscoFuncao.setHistoricoFuncao(historicoFuncao);
		riscoFuncaoDao.save(riscoFuncao);
		assertEquals(1, riscoFuncaoDao.riscosByHistoricoFuncao(historicoFuncao).size());
		riscoFuncaoDao.removeByFuncoes(new Long[]{funcao.getId()});
		assertEquals(0, riscoFuncaoDao.riscosByHistoricoFuncao(historicoFuncao).size());
	}
}