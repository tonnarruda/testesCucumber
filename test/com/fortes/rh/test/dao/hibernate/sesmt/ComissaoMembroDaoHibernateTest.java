package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.model.dicionario.FuncaoComissao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoMembroDaoHibernateTest extends GenericDaoHibernateTest<ComissaoMembro>
{
	ComissaoMembroDao comissaoMembroDao;
	ComissaoDao comissaoDao;
	ComissaoPeriodoDao comissaoPeriodoDao;
	ColaboradorDao colaboradorDao;

	public void setComissaoMembroDao(ComissaoMembroDao comissaoMembroDao)
	{
		this.comissaoMembroDao = comissaoMembroDao;
	}

	@Override
	public ComissaoMembro getEntity()
	{
		return new ComissaoMembro();
	}

	@Override
	public GenericDao<ComissaoMembro> getGenericDao()
	{
		return comissaoMembroDao;
	}

	public void setComissaoPeriodoDao(ComissaoPeriodoDao comissaoPeriodoDao)
	{
		this.comissaoPeriodoDao = comissaoPeriodoDao;
	}

	public void testFindByComissaoPeriodo()
	{
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodoDao.save(comissaoPeriodo);

		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembroDao.save(comissaoMembro);

		Long[] comissaoPeriodoIds = new Long[]{comissaoPeriodo.getId()};

		Collection<ComissaoMembro> colecao = comissaoMembroDao.findByComissaoPeriodo(comissaoPeriodoIds);

		assertEquals(1,colecao.size());
	}

	public void testUpdateFuncaoETipo()
	{
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setFuncao(FuncaoComissao.PRESIDENTE);
		comissaoMembro.setTipo(TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro);

		comissaoMembroDao.updateFuncaoETipo(comissaoMembro.getId(), FuncaoComissao.VICE_PRESIDENTE, TipoMembroComissao.INDICADO_EMPRESA);
	}

	public void testRemoveByComissaoPeriodo()
	{
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodoDao.save(comissaoPeriodo);
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembro.setFuncao(FuncaoComissao.PRESIDENTE);
		comissaoMembro.setTipo(TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro);

		comissaoMembroDao.removeByComissaoPeriodo(new Long[]{comissaoPeriodo.getId()});
	}

	public void testFindColaboradorByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador2);

		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembro.setColaborador(colaborador);
		comissaoMembroDao.save(comissaoMembro);

		ComissaoMembro comissaoMembro2 = ComissaoMembroFactory.getEntity();
		comissaoMembro2.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembro2.setColaborador(colaborador2);
		comissaoMembroDao.save(comissaoMembro2);

		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo2);
		ComissaoMembro comissaoMembro3 = ComissaoMembroFactory.getEntity();
		comissaoMembro3.setComissaoPeriodo(comissaoPeriodo2);
		comissaoMembro3.setColaborador(colaborador);
		comissaoMembroDao.save(comissaoMembro3);

		Collection<Colaborador> resultado = comissaoMembroDao.findColaboradorByComissao(comissao.getId());
		assertEquals(2, resultado.size());
	}

	public void testFindDistinctByComissaoPeriodo()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		Colaborador colaborador1 = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador1);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setDataDesligamento(DateUtil.criarDataMesAno(20, 12, 2009));
		colaboradorDao.save(colaborador2);
		
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(9, 3, 2010);

		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoPeriodo comissaoPeriodo2Fora = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2Fora.setComissao(comissao);
		comissaoPeriodo2Fora.setaPartirDe(dataPeriodo2);
		comissaoPeriodoDao.save(comissaoPeriodo2Fora);
		
		ComissaoMembro comissaoMembro1 = ComissaoMembroFactory.getEntity(comissaoPeriodo, colaborador1, FuncaoComissao.SECRETARIO, TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro1);
		
		ComissaoMembro comissaoMembro2 = ComissaoMembroFactory.getEntity(comissaoPeriodo, colaborador2, FuncaoComissao.MEMBRO_TITULAR, TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro2);

		Collection<ComissaoMembro> resultado = comissaoMembroDao.findDistinctByComissaoPeriodo(comissaoPeriodo.getId(), DateUtil.criarDataMesAno(21, 12, 2009));
		assertEquals(1, resultado.size());
	}
	
	public void testFindByComissao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(9, 3, 2010);

		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2.setComissao(comissao);
		comissaoPeriodo2.setaPartirDe(dataPeriodo2);
		comissaoPeriodoDao.save(comissaoPeriodo2);
		
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo2);
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setFuncao(FuncaoComissao.SECRETARIO);
		comissaoMembro.setTipo(TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro);
		
		ComissaoMembro comissaoMembro2 = ComissaoMembroFactory.getEntity();
		comissaoMembro2.setComissaoPeriodo(comissaoPeriodo2);
		comissaoMembro2.setColaborador(colaborador);
		comissaoMembro2.setFuncao(FuncaoComissao.SECRETARIO);
		comissaoMembro2.setTipo(TipoMembroComissao.INDICADO_EMPRESA);
		comissaoMembroDao.save(comissaoMembro2);
		
		assertEquals(1, comissaoMembroDao.findByComissao(comissao.getId(), TipoMembroComissao.ELEITO).size());
	}
	
	public void testFindByColaborador()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		Comissao comissao2 = ComissaoFactory.getEntity();
		comissaoDao.save(comissao2);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(9, 3, 2010);

		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoPeriodo comissaoPeriodo2 = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo2.setComissao(comissao2);
		comissaoPeriodo2.setaPartirDe(dataPeriodo2);
		comissaoPeriodoDao.save(comissaoPeriodo2);
		
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo2);
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setFuncao(FuncaoComissao.SECRETARIO);
		comissaoMembro.setTipo(TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro);
		
		ComissaoMembro comissaoMembro2 = ComissaoMembroFactory.getEntity();
		comissaoMembro2.setComissaoPeriodo(comissaoPeriodo2);
		comissaoMembro2.setColaborador(colaborador);
		comissaoMembro2.setFuncao(FuncaoComissao.PRESIDENTE);
		comissaoMembro2.setTipo(TipoMembroComissao.ELEITO);
		comissaoMembroDao.save(comissaoMembro2);
		
		Collection<ComissaoMembro> comissaoMembros = comissaoMembroDao.findByColaborador(colaborador.getId());
		assertEquals(2, comissaoMembros.size());
	}

	public void testFindColaboradoresNaComissao()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Comissao comissao = ComissaoFactory.getEntity();
		comissaoDao.save(comissao);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodo.setaPartirDe(new Date());
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembroDao.save(comissaoMembro);
		
		Collection<Long> colaboradorIds = new ArrayList<Long>();
		colaboradorIds.add(colaborador.getId());
		
		Collection<Colaborador> colabCollection = comissaoMembroDao.findColaboradoresNaComissao(comissao.getId());
		assertEquals(1, colabCollection.size());
	}
	
	public void testColaboradoresComEstabilidade()
	{
		Date hoje = new Date();
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setDataFim(hoje);
		comissaoDao.save(comissao);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodo.setaPartirDe(DateUtil.criarDataMesAno(01, 01, 2012));
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembroDao.save(comissaoMembro);
		
		//migué consulta SQL
		comissaoMembroDao.find(comissaoMembro);
		
		Long[] colaboradoresIds = new Long[] {colaborador.getId()};
		Map<Long, Date> colaboradoresComEstabilidade = comissaoMembroDao.colaboradoresComEstabilidade(colaboradoresIds);
		
		assertEquals(1, colaboradoresComEstabilidade.size());
		assertEquals(hoje.getYear()+1, colaboradoresComEstabilidade.get(colaborador.getId()).getYear());
	}
	
	public void testFindDataColaboradorComissaoMembro()
	{
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Comissao comissao = ComissaoFactory.getEntity();
		Date dataPeriodo1 = DateUtil.criarDataMesAno(10, 12, 2009);
		Date dataPeriodo2 = DateUtil.criarDataMesAno(9, 3, 2010);
		comissao.setDataIni(dataPeriodo1);
		comissao.setDataFim(dataPeriodo2);
		comissaoDao.save(comissao);
		
		ComissaoPeriodo comissaoPeriodo = ComissaoPeriodoFactory.getEntity();
		comissaoPeriodo.setComissao(comissao);
		comissaoPeriodo.setaPartirDe(dataPeriodo1);
		comissaoPeriodoDao.save(comissaoPeriodo);
		
		ComissaoMembro comissaoMembro = ComissaoMembroFactory.getEntity();
		comissaoMembro.setColaborador(colaborador);
		comissaoMembro.setComissaoPeriodo(comissaoPeriodo);
		comissaoMembroDao.save(comissaoMembro);
		
		Collection<Comissao> comissaoResults = comissaoMembroDao.findComissaoByColaborador(colaborador.getId());
		assertEquals(comissao.getDataFim(), ((Comissao)comissaoResults.toArray()[0]).getDataFim());
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}
}