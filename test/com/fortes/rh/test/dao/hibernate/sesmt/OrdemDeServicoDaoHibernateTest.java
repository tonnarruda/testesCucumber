package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.sesmt.OrdemDeServicoFactory;

public class OrdemDeServicoDaoHibernateTest extends GenericDaoHibernateTest<OrdemDeServico>
{
	private OrdemDeServicoDao ordemDeServicoDao;
	private ColaboradorDao colaboradorDao;

	@Override
	public OrdemDeServico getEntity()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity();
		ordemDeServico.setColaborador(colaborador);
		return ordemDeServico;
	}

	@Override
	public GenericDao<OrdemDeServico> getGenericDao()
	{
		return ordemDeServicoDao;
	}

	public void setOrdemDeServicoDao(OrdemDeServicoDao ordemDeServicoDao)
	{
		this.ordemDeServicoDao = ordemDeServicoDao;
	}
	
	public void setColaboradorDao(ColaboradorDao colaboradorDao) {
		this.colaboradorDao = colaboradorDao;
	}

	
	public void testFindOrdemServicoProjection(){
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Teste");
		colaboradorDao.save(colaborador);
		OrdemDeServico ordemDeServico = saveOrdemDeServico(colaborador, 1.0, new Date());
		
		OrdemDeServico ordemDeServicoDoBanco = ordemDeServicoDao.findOrdemServicoProjection(ordemDeServico.getId());
		assertEquals(ordemDeServico.getId(), ordemDeServicoDoBanco.getId());
	}
	
	
	public void testOrdemDeServicoAtual(){
		Colaborador colaborador = ColaboradorFactory.getEntity(1L, "Teste");
		colaboradorDao.save(colaborador);
		
		saveOrdemDeServico(colaborador, 1.0, new Date());
		OrdemDeServico ordemDeServico2 = saveOrdemDeServico(colaborador, 2.0, new Date());
		
		OrdemDeServico ordemDeServicoDoBanco = ordemDeServicoDao.ultimaOrdemDeServico(colaborador.getId());
		assertEquals(ordemDeServico2.getRevisao(), ordemDeServicoDoBanco.getRevisao());
	}
	
	private OrdemDeServico saveOrdemDeServico(Colaborador colaborador, Double revisao, Date dataOS){
		OrdemDeServico ordemDeServico = OrdemDeServicoFactory.getEntity();
		ordemDeServico.setColaborador(colaborador);
		ordemDeServico.setNomeColaborador(colaborador.getNome());
		ordemDeServico.setDataAdmisaoColaborador(colaborador.getDataAdmissao());
		ordemDeServico.setNomeFuncao("Desenvolvedor");
		ordemDeServico.setCodigoCBO("000000");
		ordemDeServico.setData(dataOS);
		ordemDeServico.setRevisao(revisao);
		ordemDeServico.setAtividades("atividades");
		ordemDeServico.setEpis("epis");
		ordemDeServico.setMedidasPreventivas("medidasPreventivas");
		ordemDeServico.setRiscos("riscos");
		ordemDeServico.setTreinamentos("treinamentos");
		ordemDeServico.setMedidasPreventivas("medidasPreventivas");
		ordemDeServico.setNormasInternas("normasInternas");
		ordemDeServico.setProcedimentoEmCasoDeAcidente("procedimentoEmCasoDeAcidente");
		ordemDeServico.setTermoDeResponsabilidade("termoDeResponsabilidade");
		ordemDeServicoDao.save(ordemDeServico);
		return ordemDeServico;
	}
}
