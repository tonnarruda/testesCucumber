package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.ComissaoDao;
import com.fortes.rh.model.dicionario.TipoMembroComissao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.relatorio.AtaPosseRelatorio;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

@Component
public class ComissaoManagerImpl extends GenericManagerImpl<Comissao, ComissaoDao> implements ComissaoManager
{
	@Autowired private EleicaoManager eleicaoManager;
	@Autowired private ComissaoMembroManager comissaoMembroManager;
	@Autowired private ComissaoReuniaoManager comissaoReuniaoManager;
	@Autowired private ComissaoPeriodoManager comissaoPeriodoManager;
	@Autowired private ComissaoPlanoTrabalhoManager comissaoPlanoTrabalhoManager;

	@Autowired
	ComissaoManagerImpl(ComissaoDao fooDao) {
		setDao(fooDao);
	}
	
	public Collection<Comissao> findByEleicao(Long eleicaoId)
	{
		return getDao().findByEleicao(eleicaoId);
	}

	public Comissao findByIdProjection(Long comissaoId)
	{
		return getDao().findByIdProjection(comissaoId);
	}

	public Collection<Comissao> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	@Override
	public Comissao save(Comissao comissao)
	{
		super.save(comissao);
		// Cria a comissão inicial
		comissaoPeriodoManager.save(comissao.getId(), comissao.getEleicao().getId(), comissao.getDataIni());

		return comissao;
	}

	@Override
	public void remove(Long id)
	{
		comissaoPeriodoManager.removeByComissao(id);
		comissaoReuniaoManager.removeByComissao(id);
		comissaoPlanoTrabalhoManager.removeByComissao(id);
		getDao().remove(id);
	}

	public void removeByEleicao(Long eleicaoId)
	{
		Collection<Comissao> comissaos = getDao().findByEleicao(eleicaoId);
		CollectionUtil<Comissao> util = new CollectionUtil<Comissao>();
		Long[] ids = util.convertCollectionToArrayIds(comissaos);

		for (Long id : ids)
		{
			this.remove(id);
		}
	}
	
	public boolean validaData(Date data, Long comissaoId)
	{
		boolean valido=false;
		
		Comissao comissao = findByIdProjection(comissaoId);
		
		if (DateUtil.between(data, comissao.getDataIni(), comissao.getDataFim()))
			valido=true;
		
		return valido;
	}
	
	public boolean updateTextosComunicados(Comissao comissao) {
		
		if (comissao == null || comissao.getId() == null)
			return false;
		
		return getDao().updateTextosComunicados(comissao);
	}

	public AtaPosseRelatorio montaAtaPosse(Comissao comissao) {
		
		Collection<ComissaoMembro> membrosPresentes = comissaoMembroManager.findByComissao(comissao.getId(), null);
		
		AtaPosseRelatorio ataPosseRelatorio = new AtaPosseRelatorio(membrosPresentes, comissao.getAtaPosseTexto1(), comissao.getAtaPosseTexto2());
		ataPosseRelatorio.montarTexto();
		
		Collection<ComissaoMembro> membrosEleitos = comissaoMembroManager.findByComissao(comissao.getId(), TipoMembroComissao.ELEITO);
		Collection<ComissaoMembro> membrosIndicados = comissaoMembroManager.findByComissao(comissao.getId(), TipoMembroComissao.INDICADO_EMPRESA);
		
		ataPosseRelatorio.montarMembrosEleitos(membrosEleitos);
		ataPosseRelatorio.montarMembrosIndicados(membrosIndicados);
		
		return ataPosseRelatorio;
	}
	
	public Collection<ParticipacaoColaboradorCipa> getParticipacoesDeColaboradorNaCipa(Long colaboradorId)
	{
		Collection<ParticipacaoColaboradorCipa> participacoesNaCipa = null;
		
		// participações em eleições
		participacoesNaCipa = eleicaoManager.getParticipacoesDeColaboradorEmEleicoes(colaboradorId);
		// participação em comissões
		Collection<ComissaoMembro> comissaoMembros = comissaoMembroManager.findByColaborador(colaboradorId);
		
		for (ComissaoMembro comissaoMembro : comissaoMembros)
			participacoesNaCipa.add( new ParticipacaoColaboradorCipa(comissaoMembro) );
		
		return participacoesNaCipa;
	}

	public List<Colaborador> findColaboradoresByDataReuniao(Date dataReuniao, Long comissaoId) 
	{
		return getDao().findColaboradoresByDataReuniao(dataReuniao, comissaoId);
	}
}