package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class CertificacaoManagerImpl extends GenericManagerImpl<Certificacao, CertificacaoDao> implements CertificacaoManager
{
	private FaixaSalarialManager faixaSalarialManager;
	public Collection<Certificacao> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Certificacao> findByFaixa(Long faixaId)
	{
		return getDao().findByFaixa(faixaId);
	}

	public Collection<MatrizTreinamento> getByFaixasOrCargos(String[] faixaSalarialsCheck, String[] cargosCheck)
	{
		Collection<Long> faixaIds = null;
		
		if (faixaSalarialsCheck != null && faixaSalarialsCheck.length > 0)
			faixaIds = LongUtil.arrayStringToCollectionLong(faixaSalarialsCheck);
		else
		{
			Collection<FaixaSalarial> faixas = faixaSalarialManager.findByCargos(LongUtil.arrayStringToArrayLong(cargosCheck)); 
			faixaIds = LongUtil.collectionToCollectionLong(faixas);
		}

		return getDao().findMatrizTreinamento(faixaIds);
	}
	
	public Collection<CheckBox> populaCheckBoxDesabilitandoSemPeriodicidade(Long empresaId)
	{
		try
		{
			Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
			Collection<Certificacao> certificacoesTemp = getDao().findAllSelect(empresaId);
			CheckBox checkBox = null;
			
			for (Certificacao certificacao : certificacoesTemp)
			{
				checkBox = new CheckBox();
				checkBox.setId(certificacao.getId());
				checkBox.setNome(certificacao.getNome());
				checkBox.setDesabilitado(true);
				
				if (certificacao.getPeriodicidade() == null)
					checkBox.setTitulo("Essa certificação não possui periodicidade");
				else
					checkBox.setDesabilitado(false);
				
				checkboxes.add(checkBox);
			}
			
			return checkboxes;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}


	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public Certificacao findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public Collection<MatrizTreinamento> montaMatriz(boolean imprimirMatriz, String[] faixaSalarialId, Collection<ColaboradorTurma> colaboradorTurmas)
	{
		if (imprimirMatriz)
		{
			Collection<MatrizTreinamento> matrizTreinamentos = getByFaixasOrCargos(faixaSalarialId, null);
			for (MatrizTreinamento matrizTreinamento : matrizTreinamentos)
			{
				for (ColaboradorTurma ct : colaboradorTurmas)
				{
					if (ct.getCurso().getId().equals(matrizTreinamento.getCursoId()))
					{
						matrizTreinamento.setRealizado(ct.isAprovado());
						break;
					}
				}
			}

			return matrizTreinamentos;
		}
		else
			return null;
	}

	public Collection<Certificacao> findAllSelect(Integer page, Integer pagingSize, Long id, String nomeBusca) {
		return getDao().findAllSelect(page, pagingSize, id, nomeBusca);
	}

	public Integer getCount(Long empresaId, String nomeBusca) 
	{
		return getDao().getCount(empresaId, nomeBusca);
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception {
		getDao().deleteByFaixaSalarial(faixaIds);
	}

	public Collection<Colaborador> findColaboradoresNaCertificacoa(Long certificacaoId) 
	{
		return getDao().findColaboradoresNaCertificacoa(certificacaoId);
	}

	public Collection<Certificacao> findAllSelectNotCertificacaoId(Long empresaId, Long certificacaoId) {
		return getDao().findAllSelectNotCertificacaoId(empresaId, certificacaoId);
	}
	
	public Collection<Certificacao> findByCursoId(Long cursoId) {
		return getDao().findByCursoId(cursoId);
	}
}