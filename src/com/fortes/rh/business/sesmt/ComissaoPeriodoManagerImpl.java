package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoPeriodoDao;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

@Component
public class ComissaoPeriodoManagerImpl extends GenericManagerImpl<ComissaoPeriodo, ComissaoPeriodoDao> implements ComissaoPeriodoManager
{
	private CandidatoEleicaoManager candidatoEleicaoManager;
	private ComissaoMembroManager comissaoMembroManager;
	private ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager;

	@Autowired
	ComissaoPeriodoManagerImpl(ComissaoPeriodoDao fooDao) {
		setDao(fooDao);
	}
	
	public void save(Long comissaoId, Long eleicaoId, Date aPartirDe)
	{
		ComissaoPeriodo comissaoPeriodo = new ComissaoPeriodo(aPartirDe);
		Comissao comissao = new Comissao();
		comissao.setId(comissaoId);
		comissaoPeriodo.setComissao(comissao);

		super.save(comissaoPeriodo);
		Collection<CandidatoEleicao> candidatoEleicaos = candidatoEleicaoManager.findByEleicao(eleicaoId);

		for (CandidatoEleicao candidatoEleicao : candidatoEleicaos)
		{
			if (candidatoEleicao.isEleito())
			{
				ComissaoMembro comissaoMembro = new ComissaoMembro(candidatoEleicao.getCandidato(),comissaoPeriodo);
				comissaoMembroManager.save(comissaoMembro);
			}
		}
	}

	public ComissaoPeriodo clonar(Long comissaoPeriodoId, Date aPartirDe) throws Exception
	{
		ComissaoPeriodo comissaoPeriodo = getDao().findByIdProjection(comissaoPeriodoId);
		Collection<ComissaoMembro> comissaoMembros = comissaoMembroManager.findByComissaoPeriodo(comissaoPeriodo);

		comissaoPeriodo.setId(null);
		comissaoPeriodo.setaPartirDe(dataValida(aPartirDe, comissaoPeriodo));
		ComissaoPeriodo comissaoPeridoClonado = super.save(comissaoPeriodo);
		
		for (ComissaoMembro comissaoMembro : comissaoMembros)
		{

			comissaoMembro.setId(null);
			// referencia de comissaoPeriodo com o novo id
			comissaoMembro.setComissaoPeriodo(comissaoPeriodo);

			comissaoMembroManager.save(comissaoMembro);
		}
		
		return comissaoPeridoClonado;
	}

	public Date dataValida (Date aPartirDe, ComissaoPeriodo comissaoPeriodo)
	{
		Date maxAPartirDE = getDao().maxDataComissaoPeriodo(comissaoPeriodo.getComissao().getId());
		return DateUtil.incrementaMes(maxAPartirDE, 1);
	}
	
	public Collection<ComissaoPeriodo> findByComissao(Long comissaoId)
	{
		Collection<ComissaoPeriodo> comissaoPeriodos = getDao().findByComissao(comissaoId);
		
		for (ComissaoPeriodo comissaoPeriodo : comissaoPeriodos) {
			comissaoPeriodo.setFim(this.getDataFim(comissaoPeriodo));
		}

		Collection<Long> colaboradorIds = new ArrayList<Long>();
		CollectionUtil<ComissaoPeriodo> collectionUtil = new CollectionUtil<ComissaoPeriodo>();
		Long[] comissaoPeriodoIds =  collectionUtil.convertCollectionToArrayIds(comissaoPeriodos);

		// buscando os membros de cada periodo
		Collection<ComissaoMembro> comissaoMembros = comissaoMembroManager.findByComissaoPeriodo(comissaoPeriodoIds);

		for (ComissaoPeriodo comissaoPeriodo : comissaoPeriodos)
		{
			for (ComissaoMembro comissaoMembro : comissaoMembros)
			{
				if (comissaoMembro.getComissaoPeriodo().getId().equals(comissaoPeriodo.getId()))
				{
					comissaoPeriodo.addMembro(comissaoMembro);
					colaboradorIds.add(comissaoMembro.getColaborador().getId());
				}
			}
			comissaoPeriodo.setPermitirExcluir(!comissaoReuniaoPresencaManager.existeReuniaoPresenca(comissaoId, colaboradorIds));
		}
		

		return comissaoPeriodos;
	}

	public ComissaoPeriodo findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public void update(ComissaoPeriodo comissaoPeriodo, String[] comissaoMembroIds, String[] funcaoComissaos, String [] tipoComissaos) throws Exception
	{
		super.update(comissaoPeriodo);
		comissaoMembroManager.updateFuncaoETipo(comissaoMembroIds, funcaoComissaos, tipoComissaos);
	}

	@Override
	public void remove(ComissaoPeriodo comissaoPeriodo)
	{
		if (getCount(new String[] {"comissao.id"}, new Object[]{comissaoPeriodo.getComissao().getId()}) <= 1 )
			throw new PersistenceException("Não é permitido remover o último período da comissão.");

		this.remove(new Long[]{comissaoPeriodo.getId()});
	}

	@Override
	public void remove(Long[] ids) throws PersistenceException
	{
		comissaoMembroManager.removeByComissaoPeriodo(ids);
		getDao().remove(ids);
	}

	public void removeByComissao(Long comissaoId)
	{
		Collection<ComissaoPeriodo> comissaoPeriodos = getDao().findByComissao(comissaoId);
		CollectionUtil<ComissaoPeriodo> util = new CollectionUtil<ComissaoPeriodo>();
		Long[] ids = util.convertCollectionToArrayIds(comissaoPeriodos);

		this.remove(ids);
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager)
	{
		this.comissaoMembroManager = comissaoMembroManager;
	}
	public void setCandidatoEleicaoManager(CandidatoEleicaoManager candidatoEleicaoManager)
	{
		this.candidatoEleicaoManager = candidatoEleicaoManager;
	}
	
	public Date getDataFim(ComissaoPeriodo comissaoPeriodo) {
		
		if (comissaoPeriodo == null || comissaoPeriodo.getId() == null)
			return null;

		comissaoPeriodo = getDao().findByIdProjection(comissaoPeriodo.getId());
		
		ComissaoPeriodo proximoComissaoPeriodo = getDao().findProximo(comissaoPeriodo);
		
		Calendar dataFim = Calendar.getInstance();
		
		// se não há próximo período, o atual termina ao fim da comissão 
		if (proximoComissaoPeriodo == null)
		{
			dataFim.setTime(comissaoPeriodo.getComissao().getDataFim());
		}
		// se há próximo período, o atual termina 
		else
		{
			dataFim.setTime(proximoComissaoPeriodo.getaPartirDe());
			dataFim.add(Calendar.DAY_OF_YEAR, -1);
		}
		
		return dataFim.getTime();
	}

	public boolean validaDataComissaoPeriodo(Date data, Long comissaoPeriodoId) {
		
		ComissaoPeriodo comissaoPeriodo = getDao().findByIdProjection(comissaoPeriodoId);
		comissaoPeriodo.setaPartirDe(data);
		
		//data fora do período da CIPA
		if (!DateUtil.between(data, comissaoPeriodo.getComissao().getDataIni(), comissaoPeriodo.getComissao().getDataFim()))
			return false;
		
		// outra comissão na mesma data
		if (getDao().verificaComissaoNaMesmaData(comissaoPeriodo))
			return false;
		
		return true;
	}
	
	public void setComissaoReuniaoPresencaManager(ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager) {
		this.comissaoReuniaoPresencaManager = comissaoReuniaoPresencaManager;
	}
}