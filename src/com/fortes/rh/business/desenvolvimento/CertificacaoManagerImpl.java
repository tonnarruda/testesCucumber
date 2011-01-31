package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.util.LongUtil;

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
		if(faixaSalarialsCheck != null && faixaSalarialsCheck.length > 0)
			faixaIds = LongUtil.arrayStringToCollectionLong(faixaSalarialsCheck);
		else
			faixaIds = faixaSalarialManager.findByCargos(LongUtil.arrayStringToCollectionLong(cargosCheck));

		return getDao().findMatrizTreinamento(faixaIds);
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
}