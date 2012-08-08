package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.List;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.dao.sesmt.ComissaoMembroDao;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoPresencaDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoMembroFactory;
import com.fortes.rh.test.factory.sesmt.ComissaoPeriodoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.util.DateUtil;

public class ComissaoDaoHibernateTest extends GenericDaoHibernateTest<Comissao>
{
	ComissaoDao comissaoDao;
	ComissaoPeriodoDao comissaoPeriodoDao;
	ComissaoMembroDao comissaoMembroDao;
	ComissaoReuniaoDao comissaoReuniaoDao;
	ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao;
	ColaboradorDao colaboradorDao;
	EleicaoDao eleicaoDao;
	EmpresaDao empresaDao;

	public void setComissaoDao(ComissaoDao comissaoDao)
	{
		this.comissaoDao = comissaoDao;
	}

	@Override
	public Comissao getEntity()
	{
		return ComissaoFactory.getEntity();
	}

	@Override
	public GenericDao<Comissao> getGenericDao()
	{
		return comissaoDao;
	}

	public void testFindByEleicao()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);

		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setEleicao(eleicao);
		comissaoDao.save(comissao);

		Collection<Comissao> comissaos = comissaoDao.findByEleicao(eleicao.getId());
		assertEquals(1, comissaos.size());
	}

	public void testFindByIdProjection()
	{
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicaoDao.save(eleicao);
		
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setEleicao(eleicao);
		comissaoDao.save(comissao);

		assertEquals(comissao.getId(), comissaoDao.findByIdProjection(comissao.getId()).getId());
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		Eleicao eleicao = EleicaoFactory.getEntity();
		eleicao.setEmpresa(empresa);
		eleicaoDao.save(eleicao);
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setEleicao(eleicao);
		comissaoDao.save(comissao);

		Collection<Comissao> colecao = comissaoDao.findAllSelect(empresa.getId());
		assertEquals(1,colecao.size());
	}
	
	public void testUpdateTextosComunicados()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setAtaPosseTexto1("Texto de Ata de instalação e posse...");
		comissao.setAtaPosseTexto2("");
		comissaoDao.save(comissao);
		
		comissao.setAtaPosseTexto2("Segunda parte do Texto de Ata de instalação e posse...");
		assertTrue(comissaoDao.updateTextosComunicados(comissao));
	}

	public void testFindColaboradoresByDataReuniao()
	{
		Comissao comissao = ComissaoFactory.getEntity();
		comissao.setDataIni(DateUtil.criarDataMesAno(1, 1, 2012));
		comissao.setDataFim(DateUtil.criarDataMesAno(31, 12, 2012));
		comissaoDao.save(comissao);
		
		Colaborador joao = ColaboradorFactory.getEntity();
		joao.setNome("joao");
		colaboradorDao.save(joao);
		
		Colaborador maria = ColaboradorFactory.getEntity();
		maria.setNome("maria");
		colaboradorDao.save(maria);
		
		Colaborador gil = ColaboradorFactory.getEntity();
		gil.setNome("gil");
		colaboradorDao.save(gil);
		
		ComissaoPeriodo cp1 = ComissaoPeriodoFactory.getEntity();
		cp1.setComissao(comissao);
		cp1.setaPartirDe(DateUtil.criarDataMesAno(1, 1, 2012));
		comissaoPeriodoDao.save(cp1);
		
		ComissaoPeriodo cp2 = ComissaoPeriodoFactory.getEntity();
		cp2.setComissao(comissao);
		cp2.setaPartirDe(DateUtil.criarDataMesAno(1, 7, 2012));
		comissaoPeriodoDao.save(cp2);
		
		ComissaoMembro cmJoao1 = ComissaoMembroFactory.getEntity();
		cmJoao1.setColaborador(joao);
		cmJoao1.setComissaoPeriodo(cp1);
		comissaoMembroDao.save(cmJoao1);
		
		ComissaoMembro cmMaria1 = ComissaoMembroFactory.getEntity();
		cmMaria1.setColaborador(maria);
		cmMaria1.setComissaoPeriodo(cp1);
		comissaoMembroDao.save(cmMaria1);
		
		ComissaoMembro cmMaria2 = ComissaoMembroFactory.getEntity();
		cmMaria2.setColaborador(maria);
		cmMaria2.setComissaoPeriodo(cp2);
		comissaoMembroDao.save(cmMaria2);
		
		ComissaoMembro cmGil2 = ComissaoMembroFactory.getEntity();
		cmGil2.setColaborador(gil);
		cmGil2.setComissaoPeriodo(cp2);
		comissaoMembroDao.save(cmGil2);
		
		List<Colaborador> membros1 = comissaoDao.findColaboradoresByDataReuniao(DateUtil.criarDataMesAno(1, 2, 2012), comissao.getId());
		assertEquals(2, membros1.size());
		assertEquals("joao", ((Colaborador)membros1.toArray()[0]).getNome());
		assertEquals("maria", ((Colaborador)membros1.toArray()[1]).getNome());
		
		List<Colaborador> membros2 = comissaoDao.findColaboradoresByDataReuniao(DateUtil.criarDataMesAno(1, 8, 2012), comissao.getId());
		assertEquals(2, membros2.size());
		assertEquals("gil", ((Colaborador)membros2.toArray()[0]).getNome());
		assertEquals("maria", ((Colaborador)membros2.toArray()[1]).getNome());
	}
	
	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	public void setComissaoPeriodoDao(ComissaoPeriodoDao comissaoPeriodoDao) {
		this.comissaoPeriodoDao = comissaoPeriodoDao;
	}

	public void setComissaoMembroDao(ComissaoMembroDao comissaoMembroDao) {
		this.comissaoMembroDao = comissaoMembroDao;
	}

	public void setComissaoReuniaoPresencaDao(ComissaoReuniaoPresencaDao comissaoReuniaoPresencaDao) {
		this.comissaoReuniaoPresencaDao = comissaoReuniaoPresencaDao;
	}

	public void setComissaoReuniaoDao(ComissaoReuniaoDao comissaoReuniaoDao) {
		this.comissaoReuniaoDao = comissaoReuniaoDao;
	}
}