/* autor: Moesio Medeiros
 * data: 16/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
 */

package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.FormacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.Colaborador;

public class FormacaoManagerImpl extends GenericManagerImpl<Formacao, FormacaoDao> implements FormacaoManager
{
	public void removeCandidato(Candidato candidato)
	{
		getDao().removeCandidato(candidato);
	}

	public void removeColaborador(Colaborador colaborador)
	{
		getDao().removeColaborador(colaborador);
	}

	public Collection<Formacao> findInCandidatos(Long[] candidatoIds)
	{
		return getDao().findInCandidatos(candidatoIds);
	}

	public void montaFormacaosBDS(Collection<Formacao> formacaos, Candidato candidato)
	{
		for (Formacao formacao : formacaos)
		{
			formacao.setId(null);
			formacao.setColaborador(null);
			formacao.setAreaFormacao(null);
			formacao.setCandidato(candidato);

			save(formacao);
		}
	}

	public Collection<Formacao> findByColaborador(Long colaboradorId)
	{
		return getDao().findByColaborador(colaboradorId);
	}

	public Collection<Formacao> findByCandidato(Long candidatoId)
	{
		return getDao().findByCandidato(candidatoId);
	}

	public Collection<Formacao> retornaListaSemDuplicados(Collection<Formacao> formacaos) {
		Collection<Formacao> formacoesAux = new ArrayList<Formacao>();
		Collection<Formacao> formacoesRetorno = new ArrayList<Formacao>();
		for (Iterator<Formacao> iterator = formacaos.iterator(); iterator.hasNext();) {
			Formacao formacao = (Formacao) iterator.next();
			
			if (formacoesAux.isEmpty()) {
				formacoesAux.add(formacao);
				formacoesRetorno.add(formacao);
			} else {
				
				for (Iterator<Formacao> iterator2 = formacoesAux.iterator(); iterator2.hasNext();) {
					if (checaMesmoElemento(formacao,formacoesRetorno)) {
						break;
					} else {
						formacoesRetorno.add(formacao);
					}
				}
			}
		}
		return formacoesRetorno;
	}
	public boolean checaMesmoElemento(Formacao formacao, Collection<Formacao> formacoesRetorno) {
			
			for (Iterator<Formacao> iterator = formacoesRetorno.iterator(); iterator.hasNext();) {
				Formacao formacaoRetorno = (Formacao) iterator.next();
				AreaFormacao areaFormacao = formacao.getAreaFormacao();
				AreaFormacao areaFormacaoRetorno = formacaoRetorno.getAreaFormacao();
	
				boolean mesmoCurso = formacao.getCurso().equals(formacaoRetorno.getCurso());
				boolean mesmaConclusao = formacao.getConclusao().equals(formacaoRetorno.getConclusao());
				boolean mesmaAreaFormacao=areaFormacao !=null && formacaoRetorno.getAreaFormacao()!=null? areaFormacao.getId().equals(areaFormacaoRetorno.getId()):false; 
				boolean mesmaSituacao =formacao.getSituacao() == formacaoRetorno.getSituacao();
				boolean mesmoTipo =formacao.getTipo() == formacaoRetorno.getTipo();
				boolean mesmoLocal = formacao.getLocal().equals(formacaoRetorno.getLocal());
				
				if(mesmoCurso && mesmaConclusao && mesmaAreaFormacao && mesmaSituacao && mesmoTipo && mesmoLocal)
					return true;
				else
					continue;
			}
					return false;
		}
}