package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiItemVO;
import com.fortes.rh.model.sesmt.relatorio.SolicitacaoEpiVO;

public interface SolicitacaoEpiDao extends GenericDao<SolicitacaoEpi>
{
	SolicitacaoEpiVO findAllSelect(int page, int pagingSize, Long empresaId, Date dataIni, Date dataFim, Colaborador colaborador, String situacaoSolicitacaoEpi, Long tipoEpi, String situacaoColaborador, Long[] estabelecimentoCheck, char ordem);
	SolicitacaoEpi findByIdProjection(Long solicitacaoEpiId);
	Collection<SolicitacaoEpi> findVencimentoEpi(Long empresaId, Date data, boolean exibirVencimentoCA, Long[] tipoEPIIds, Long[] areasIds, Long[] estabelecimentoIds, char agruparPor);
	public Collection<SolicitacaoEpiItemEntrega> findEntregaEpi(Long empresaId, Date dataIni, Date dataFim, Long[] epiCheck, Long[] areaIds, Long[] colaboradorCheck, char agruparPor, boolean exibirDesligados);
	public Collection<SolicitacaoEpiItemVO> findEpisWithItens(Long empresaId, Date dataIni, Date dataFim, String situacao, Colaborador colaborador, Long tipoEpi, String situacaoColaborador, Long[] estabelecimentoCheck, char ordem);
	Collection<SolicitacaoEpi> findByColaboradorId(Long colaboradorId);
	Collection<SolicitacaoEpiItemDevolucao> findDevolucaoEpi(Long empresaId, Date dataIni, Date dataFim, Long[] epiIds, Long[] areaIds, Long[] colaboradorCheck, char agruparPor, boolean exibirDesligados);
}