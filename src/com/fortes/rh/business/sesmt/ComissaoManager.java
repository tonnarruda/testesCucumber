package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.relatorio.ParticipacaoColaboradorCipa;
import com.fortes.rh.model.sesmt.Comissao;
import com.fortes.rh.model.sesmt.ComissaoReuniaoPresenca;
import com.fortes.rh.model.sesmt.relatorio.AtaPosseRelatorio;

public interface ComissaoManager extends GenericManager<Comissao>
{
	Collection<Comissao> findByEleicao(Long eleicaoId);
	Comissao findByIdProjection(Long comissaoId);
	Collection<Comissao> findAllSelect(Long empresaId);
	void removeByEleicao(Long eleicaoId);
	boolean validaData(Date data, Long comissaoId);
	boolean updateTextosComunicados(Comissao comissao);
	AtaPosseRelatorio montaAtaPosse(Comissao comissao);
	Collection<ParticipacaoColaboradorCipa> getParticipacoesDeColaboradorNaCipa(Long colaboradorId);
	List<Colaborador> findColaboradoresByDataReuniao(Date dataReuniao, Long comissaoId);
	List<ComissaoReuniaoPresenca> findPresencaColaboradoresByReuniao(Long comissaoReuniaoId);
}