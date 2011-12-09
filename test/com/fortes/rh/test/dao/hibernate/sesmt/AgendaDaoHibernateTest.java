package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AgendaDao;
import com.fortes.rh.dao.sesmt.EventoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Agenda;
import com.fortes.rh.model.sesmt.Evento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.AgendaFactory;
import com.fortes.rh.test.factory.sesmt.EventoFactory;
import com.fortes.rh.util.DateUtil;

public class AgendaDaoHibernateTest extends GenericDaoHibernateTest<Agenda>
{
	private AgendaDao agendaDao;
	private EstabelecimentoDao estabelecimentoDao;
	private EventoDao eventoDao;
	private EmpresaDao empresaDao;

	public void setEventoDao(EventoDao eventoDao)
	{
		this.eventoDao = eventoDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	@Override
	public Agenda getEntity()
	{
		return AgendaFactory.getEntity();
	}

	@Override
	public GenericDao<Agenda> getGenericDao()
	{
		return agendaDao;
	}

	public void setAgendaDao(AgendaDao agendaDao)
	{
		this.agendaDao = agendaDao;
	}
	
	public void testFindByPeriodo()
	{
		Date dataIni = DateUtil.criarDataMesAno(01, 02, 2009);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);

		Evento evento = EventoFactory.getEntity();
		eventoDao.save(evento);
		
		Agenda agenda = AgendaFactory.getEntity();
		agenda.setData(dataIni);
		agenda.setEvento(evento);
		agenda.setEstabelecimento(estabelecimento);
		agendaDao.save(agenda);
		
		Collection<Agenda> agendas = agendaDao.findByPeriodo(dataIni, DateUtil.criarDataMesAno(03, 03, 2010), empresa.getId(), estabelecimento);
		assertEquals(1, agendas.size());
	}
	
	public void testFindByIdProjection()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Date dataIni = DateUtil.criarDataMesAno(01, 02, 2009);
		
		Evento evento = EventoFactory.getEntity();
		eventoDao.save(evento);
		
		Agenda agenda = AgendaFactory.getEntity();
		agenda.setData(dataIni);
		agenda.setEvento(evento);
		agenda.setEstabelecimento(estabelecimento);
		agendaDao.save(agenda);
		
		Agenda retorno = agendaDao.findByIdProjection(agenda.getId());
		assertEquals(agenda, retorno);
	}
	
	public void testFindByPeriodoEvento()
	{
		Date dataIni = DateUtil.criarDataMesAno(01, 02, 2009);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);

		Evento evento = EventoFactory.getEntity();
		eventoDao.save(evento);
		
		Agenda agenda = AgendaFactory.getEntity();
		agenda.setData(dataIni);
		agenda.setEvento(evento);
		agenda.setEstabelecimento(estabelecimento);
		agendaDao.save(agenda);
		
		Agenda agenda2 = AgendaFactory.getEntity();
		agenda2.setData(dataIni);
		agenda2.setNovaData(agenda2.getData(), 3, 0);
		agenda2.setEvento(evento);
		agenda2.setEstabelecimento(estabelecimento);
		agendaDao.save(agenda2);
		
		Agenda agenda3 = AgendaFactory.getEntity();
		agenda3.setData(dataIni);
		agenda3.setNovaData(agenda3.getData(), 6, 0);
		agenda3.setEvento(evento);
		agenda3.setEstabelecimento(estabelecimento);
		agendaDao.save(agenda3);
		
		Collection<Agenda> agendas = agendaDao.findByPeriodoEvento(dataIni, DateUtil.incrementaMes(dataIni, 9), estabelecimento, evento);
		assertEquals(3, agendas.size());
	}
	
	public void testDeleteByEstabelecimento() {
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimentoDao.save(estabelecimento);
		
		Agenda agenda = AgendaFactory.getEntity();
		agenda.setEstabelecimento(estabelecimento);
		agendaDao.save(agenda);
		
		Exception exception = null;
		try {
			agendaDao.deleteByEstabelecimento(new Long[] {estabelecimento.getId()});
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
	}
}