package com.fortes.rh.business.geral;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.web.ws.AcPessoalClientOcorrencia;

public class OcorrenciaManagerImpl extends GenericManagerImpl<Ocorrencia, OcorrenciaDao> implements OcorrenciaManager
{
	private AcPessoalClientOcorrencia acPessoalClientOcorrencia;

	public void saveOrUpdate(Ocorrencia ocorrencia, Empresa empresa) throws Exception
	{
		if(empresa.isAcIntegra() && ocorrencia.getIntegraAC())
		{
			try
			{
				String codigoAC = acPessoalClientOcorrencia.criarTipoOcorrencia(bindOcorrencia(ocorrencia,ocorrencia.getEmpresa()), ocorrencia.getEmpresa());

				if(isBlank(codigoAC))
					throw new IntegraACException("Metodo: AcPessoalClientOcorrencia.criarTipoOcorrencia, codigoAc retornou null");
				else
					ocorrencia.setCodigoAC(codigoAC);
			}
			catch (Exception e)
			{
				throw new IntegraACException(e.getMessage());
			}
		}

		if (ocorrencia.getId() == null)
			getDao().save(ocorrencia);
		else
			getDao().update(ocorrencia);
	}

	public void remove(Ocorrencia ocorrencia, Empresa empresa) throws Exception
	{
		if (empresa.isAcIntegra())
		{
			ocorrencia = findById(ocorrencia.getId());
			if (!acPessoalClientOcorrencia.removerTipoOcorrencia(bindOcorrencia(ocorrencia,empresa), empresa))
				throw new IntegraACException("Não foi possível excluir esta Ocorrência no AC Pessoal.");
		}

		remove(ocorrencia.getId());
	}

	private TOcorrencia bindOcorrencia(Ocorrencia ocorrencia, Empresa empresa)
	{
		TOcorrencia tocorrencia = new TOcorrencia();
		tocorrencia.setCodigo(ocorrencia.getCodigoAC());
		tocorrencia.setEmpresa(empresa.getCodigoAC());
		tocorrencia.setDescricao(ocorrencia.getDescricao());

		return tocorrencia;
	}

	/**
	 * Grava Tipo de Ocorrência recebido do AC
	 */
	public Ocorrencia saveFromAC(Ocorrencia ocorrencia) throws Exception
	{
		Ocorrencia ocorrenciaTmp = findByCodigoAC(ocorrencia.getCodigoAC(), ocorrencia.getEmpresa().getCodigoAC(), ocorrencia.getEmpresa().getGrupoAC());

		if (ocorrenciaTmp != null)
		{
			ocorrencia.setId(ocorrenciaTmp.getId());
			ocorrencia.setPontuacao(ocorrenciaTmp.getPontuacao());
			getDao().update(ocorrencia);
		}
		else
			getDao().save(ocorrencia);

		return ocorrencia;
	}

	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId) 
	{
		Collection<Ocorrencia> ocorrenciaInteresseDeOrigem = getDao().findSincronizarOcorrenciaInteresse(empresaOrigemId);
		
		for (Ocorrencia ocorrencia : ocorrenciaInteresseDeOrigem)
		{
			clonar(ocorrencia, empresaDestinoId);
			update(ocorrencia);
		}
	}
	
	public boolean removeByCodigoAC(String codigo, Long empresaId)
	{
		return getDao().removeByCodigoAC(codigo, empresaId);
	}

	public Ocorrencia findByCodigoAC(String codigo, String codigoEmpresa, String grupoAC)
	{
		return getDao().findByCodigoAC(codigo, codigoEmpresa, grupoAC);
	}

	public void setAcPessoalClientOcorrencia(AcPessoalClientOcorrencia acPessoalClientOcorrencia)
	{
		this.acPessoalClientOcorrencia = acPessoalClientOcorrencia;
	}
	
	private void clonar(Ocorrencia ocorrenciaInteresse, Long empresaDestinoId) {
		
		ocorrenciaInteresse.setId(null);
		ocorrenciaInteresse.setEmpresaId(empresaDestinoId);
		
		getDao().save(ocorrenciaInteresse);
	}

	public Collection<Ocorrencia> findAllSelect(Long[] empresaIds)
	{
		return getDao().findAllSelect(empresaIds);
	}

	public Collection<Ocorrencia> findOcorrenciasComAbseteismo(Long empresaId) 
	{
		return getDao().findOcorrenciasComAbseteismo(empresaId);
	}

	public Collection<Ocorrencia> find(int page, int pagingSize, Ocorrencia ocorrencia, Long empresaId) {
		return getDao().find(page, pagingSize, ocorrencia, empresaId);
	}

	public Integer getCount(Ocorrencia ocorrencia, Long empresaId) {
		return getDao().getCount(ocorrencia, empresaId);
	}

	public Collection<Ocorrencia> findSemCodigoAC(Long empresaId) {
		return getDao().findSemCodigoAC(empresaId);
	}
	
	public Collection<Ocorrencia> findComCodigoAC(Long empresaId) {
		return getDao().findComCodigoAC(empresaId);
	}

	public String findCodigoACDuplicado(Long empresaId) {
		// TODO Auto-generated method stub
		return getDao().findCodigoACDuplicado(empresaId);
	}
}