package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoReuniaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.dicionario.TipoComissaoReuniao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.ComissaoPeriodo;
import com.fortes.rh.model.sesmt.ComissaoReuniao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;

public class ComissaoReuniaoManagerImpl extends GenericManagerImpl<ComissaoReuniao, ComissaoReuniaoDao> implements ComissaoReuniaoManager
{
	private ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager;
	private ComissaoMembroManager comissaoMembroManager;
	private ComissaoPeriodoManager comissaoPeriodoManager;

	public ComissaoReuniao findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public Collection<ComissaoReuniao> findByComissao(Long comissaoId)
	{
		return getDao().findByComissao(comissaoId);
	}

	public Collection<ComissaoReuniao> findImprimirCalendario(Long comissaoId) throws ColecaoVaziaException
	{
		Collection<ComissaoReuniao> comissaoReuniaos = getDao().findByComissao(comissaoId);
		Collection<ComissaoReuniao> resultado = new ArrayList<ComissaoReuniao>();

		for (ComissaoReuniao comissaoReuniao : comissaoReuniaos)
		{
			if (comissaoReuniao.getTipo().equals(TipoComissaoReuniao.ORDINARIA))
				resultado.add(comissaoReuniao);
		}

		if (resultado.isEmpty())
		{
			throw new ColecaoVaziaException("Nenhuma reunião para esta comissão.");
		}

		return resultado;
	}

	public ComissaoReuniao saveOrUpdate(ComissaoReuniao comissaoReuniao, String[] colaboradorChecks, String[] colaboradorIds, String[] justificativas) throws Exception
	{
	if (comissaoReuniao.getId() == null)
		this.save(comissaoReuniao);
	else
		this.update(comissaoReuniao);

		comissaoReuniaoPresencaManager.saveOrUpdateByReuniao(comissaoReuniao.getId(), comissaoReuniao.getComissao().getId(), colaboradorChecks, colaboradorIds, justificativas);

		return comissaoReuniao;
	}

	@Override
	public void remove(Long id)
	{
		comissaoReuniaoPresencaManager.removeByReuniao(id);
		super.remove(id);
	}

	@Override
	public void remove(Long[] ids)
	{
		for (Long id : ids)
		{
			comissaoReuniaoPresencaManager.removeByReuniao(id);
		}
		super.remove(ids);
	}

	public void removeByComissao(Long comissaoId)
	{
		Collection<ComissaoReuniao> comissaoReuniaos = findByComissao(comissaoId);
		CollectionUtil<ComissaoReuniao> util = new CollectionUtil<ComissaoReuniao>();
		Long[] ids = util.convertCollectionToArrayIds(comissaoReuniaos);

		this.remove(ids);
	}

	public Collection<ComissaoMembro> findImprimirAta(ComissaoReuniao comissaoReuniao, Long comissaoId)
	{
		ComissaoReuniao comissaoReuniaoTmp = getDao().findByIdProjection(comissaoReuniao.getId());
		
		Collection<ComissaoPeriodo> comissaoPeriodos = comissaoPeriodoManager.findByComissao(comissaoId);
		
		Collection<ComissaoReuniaoPresenca> comissaoReuniaoPresencas = comissaoReuniaoPresencaManager.findByReuniao(comissaoReuniaoTmp.getId());
		
		Map<Long, String> presencaDosColaboradores = new HashMap<Long, String>();
		for (ComissaoReuniaoPresenca comissaoReuniaoPresenca : comissaoReuniaoPresencas) {
			if(!comissaoReuniaoPresenca.getPresente()){
				presencaDosColaboradores.put(comissaoReuniaoPresenca.getColaborador().getId(), comissaoReuniaoPresenca.getJustificativaFalta());
			}
		}
		
		Collection<ComissaoMembro> comissaoMembros = getMembrosNoPeriodo(comissaoReuniaoTmp.getData(), comissaoPeriodos);
		
		for (ComissaoMembro comissaoMembro : comissaoMembros) {
			if(presencaDosColaboradores.get(comissaoMembro.getColaborador().getId()) != null){
				comissaoMembro.setPresente(Boolean.FALSE);
				comissaoMembro.setJustificativaFalta(presencaDosColaboradores.get(comissaoMembro.getColaborador().getId()));
			} else {
				comissaoMembro.setPresente(Boolean.TRUE);
			}
		}

		// Os dados da reunião sao passados por referência
		comissaoReuniao.setDados(comissaoReuniaoTmp.getId(), comissaoReuniaoTmp.getDescricao(), comissaoReuniaoTmp.getTipo(), comissaoReuniaoTmp.getData(), comissaoReuniaoTmp.getHorario(), comissaoReuniaoTmp.getLocalizacao(), comissaoReuniaoTmp.getAta());
		comissaoReuniao.setObsReuniaoAnterior(comissaoReuniaoTmp.getObsReuniaoAnterior());
		// Os participantes são passados no retorno do método.
		return comissaoMembros;
	}

	private Collection<ComissaoMembro> getMembrosNoPeriodo(Date dataDaReuniao, Collection<ComissaoPeriodo> comissaoPeriodos)
	{
		Long comissaoPeriodoId=null;
		
		// selecionamos o ComissaoPeriodo ao qual a reunião se aplica
		for (ComissaoPeriodo periodo : comissaoPeriodos) {
			
			if (DateUtil.between(dataDaReuniao, periodo.getaPartirDe(), periodo.getFim()))
					comissaoPeriodoId = periodo.getId();
		}
		
		Collection<ComissaoMembro> comissaoMembros = comissaoMembroManager.findDistinctByComissaoPeriodo(comissaoPeriodoId, dataDaReuniao);
		
		return comissaoMembros;
	}

	public Collection<ComissaoReuniaoPresenca> findRelatorioPresenca(Long comissaoId) throws FortesException
	{
		Collection<ComissaoReuniaoPresenca> presencas = comissaoReuniaoPresencaManager.findPresencasByComissao(comissaoId);
		if (presencas == null || presencas.isEmpty())
			throw new FortesException("Não existem registros de membros em reuniões desta comissão");
		
		Collection<Colaborador> colaboradoresNaUltimaComissao = comissaoMembroManager.findColaboradoresNaComissao(comissaoId);
		
		for (ComissaoReuniaoPresenca presenca : presencas) 
			if (presenca.getColaborador() != null && LongUtil.contains(presenca.getColaborador().getId(), colaboradoresNaUltimaComissao))
				presenca.getColaborador().setMembroComissaoCipa(true);
		
		return presencas;
	}

	public void setComissaoReuniaoPresencaManager(ComissaoReuniaoPresencaManager comissaoReuniaoPresencaManager)
	{
		this.comissaoReuniaoPresencaManager = comissaoReuniaoPresencaManager;
	}

	public void setComissaoMembroManager(ComissaoMembroManager comissaoMembroManager)
	{
		this.comissaoMembroManager = comissaoMembroManager;
	}

	public void setComissaoPeriodoManager(ComissaoPeriodoManager comissaoPeriodoManager) {
		this.comissaoPeriodoManager = comissaoPeriodoManager;
	}
	
	public void sugerirReuniao(Comissao comissao)
	{
		java.util.Date sugerirData = comissao.getDataIni();
		for(int i = 1; i <= 12; i++)
		{
			ComissaoReuniao comissaoReuniao = new ComissaoReuniao();
			comissaoReuniao.setComissao(comissao);
			comissaoReuniao.setData(sugerirData);
			sugerirData = DateUtil.setaMesPosterior(sugerirData);
			comissaoReuniao.setHorario("10:00");
			comissaoReuniao.setTipo("O");
			comissaoReuniao.setDescricao(i + "a. Reunião Ordinária");
			comissaoReuniao.setComissaoReuniaoPresencas(null);
			save(comissaoReuniao);
		}
	}
}