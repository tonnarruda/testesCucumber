package com.fortes.rh.dao.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.AnaliseDesempenhoOrganizacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;

public interface AvaliacaoDesempenhoDao extends GenericDao<AvaliacaoDesempenho> 
{
	Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao);
	public AvaliacaoDesempenho findByIdProjection(Long id);
	void liberarOrBloquear(Long id, boolean liberar);
	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds);
	Integer findCountTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada);
	Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada);
	Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId);
	Collection<AvaliacaoDesempenho> findComCompetencia(Long empresaId);
	boolean isExibiNivelCompetenciaExigido(Long avaliacaoDesempenhoId);
	Collection<AvaliacaoDesempenho> findByCncfId(Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<AvaliacaoDesempenho> findByModelo(Long modeloId);
	Collection<Estabelecimento> findEstabelecimentosDosParticipantes(Long[] avaliacoesDesempenhoIds);
	Collection<AreaOrganizacional> findAreasOrganizacionaisDosParticipantes(Long[] avaliacoesDesempenhoIds);
	Collection<Cargo> findCargosDosParticipantes(Long[] avaliacoesDesempenhoIds);
	Collection<AnaliseDesempenhoOrganizacao> findAnaliseDesempenhoOrganizacao(Long[] avaliacoesDesempenhoIds, Long[] estabelecimentosIds, Long[] cargosIds, Long[] areasIds, Long[] competenciasIds, String agrupamentoDasCompetencias, Long empresaId);
}