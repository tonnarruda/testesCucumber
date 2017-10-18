package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.model.type.File;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.dao.sesmt.MedicoCoordenadorDao;
import com.fortes.rh.exception.ValidacaoAssinaturaException;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.MedicoCoordenador;

public class MedicoCoordenadorManagerImpl extends GenericManagerImpl<MedicoCoordenador, MedicoCoordenadorDao> implements MedicoCoordenadorManager
{
	EstabelecimentoManager estabelecimentoManager;
	
	public MedicoCoordenador findByDataEmpresa(Long empresaId, Date data)
	{
		return getDao().findByDataEmpresa(empresaId, data);
	}

	public MedicoCoordenador findByIdProjection(Long medicoCoordenadorId)
	{
		return getDao().findByIdProjection(medicoCoordenadorId);
	}

	public Collection<MedicoCoordenador> findByEmpresa(Long empresaId)
	{
		return getDao().findResponsaveisPorEstabelecimento(empresaId, null);
	}

	public File getAssinaturaDigital(Long medicoCoordenadorId) throws Exception
	{
		return getDao().getFile("assinaturaDigital", medicoCoordenadorId);
	}
	
	public Collection<MedicoCoordenador> findResponsaveisPorEstabelecimento(Date data, Colaborador colaborador)
	{
		Collection<MedicoCoordenador> medicosCoordenadores = getDao().findResponsaveisPorEstabelecimento(colaborador.getEmpresa().getId(), colaborador.getHistoricoColaborador().getEstabelecimento().getId());
		Collection<MedicoCoordenador> resultado = new ArrayList<MedicoCoordenador>();
		
		for (MedicoCoordenador medicoCoordenador : medicosCoordenadores)
		{
			if (medicoCoordenador.getInicio().compareTo(data) <= 0) { 
				if (colaborador.getDataAdmissao().compareTo(data) <= 0 
						&& (colaborador.getDataDesligamento() == null || medicoCoordenador.getInicio().compareTo(colaborador.getDataDesligamento()) <= 0) 
						&& (medicoCoordenador.getFim() == null || colaborador.getDataAdmissao().compareTo(medicoCoordenador.getFim()) <= 0)) 
				{
					medicoCoordenador.setDataDesligamento(colaborador.getDataDesligamento());
					resultado.add(medicoCoordenador);
				} 
			}
			else
				break;
		}
		
		return resultado;
	}
	
	public void insere(MedicoCoordenador medicoCoordenador, Long[] estabelecimentosIds) throws Exception {
		try {
			validaAssinatura(medicoCoordenador.getAssinaturaDigital());
			medicoCoordenador.setEstabelecimentos(getEstabelecimentosMarcados(medicoCoordenador.getEstabelecimentoResponsavel(), estabelecimentosIds));
		} catch (Exception e) {
			medicoCoordenador.setAssinaturaDigital(null);
			throw e;
		}
		
		save(medicoCoordenador);		
	}

	public void atualiza(MedicoCoordenador medicoCoordenador, Long[] estabelecimentosIds, boolean manterAssinatura) throws Exception {
		if (manterAssinatura)
			medicoCoordenador.setAssinaturaDigital(getAssinaturaDigital(medicoCoordenador.getId()));
		else 
			validaAssinatura(medicoCoordenador.getAssinaturaDigital());
		
		medicoCoordenador.setEstabelecimentos(getEstabelecimentosMarcados(medicoCoordenador.getEstabelecimentoResponsavel(), estabelecimentosIds));

		update(medicoCoordenador);		
	}

	private Collection<Estabelecimento> getEstabelecimentosMarcados(String tipoEstabelecimentoResponsavel, Long[] estabelecimentosIds) {
		if(ArrayUtils.isEmpty(estabelecimentosIds) || TipoEstabelecimentoResponsavel.TODOS.equals(tipoEstabelecimentoResponsavel))
			return null;

		return estabelecimentoManager.findById(estabelecimentosIds);
	}

	private void validaAssinatura(File assinatura) throws ValidacaoAssinaturaException
	{
		if (assinatura != null && assinatura.getContentType().length() >= 5) {
			if (!assinatura.getContentType().startsWith("image"))
				throw new ValidacaoAssinaturaException("Tipo de arquivo não suportado.");
			else if (assinatura.getSize() > 524288)
				throw new ValidacaoAssinaturaException("Tamanho do arquivo maior que o suportado.");
		}
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

}