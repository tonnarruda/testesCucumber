package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoTurno;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("deprecation")
public class DiaTurmaManagerImpl extends GenericManagerImpl<DiaTurma, DiaTurmaDao> implements DiaTurmaManager
{
	private PlatformTransactionManager transactionManager;

	public Collection<DiaTurma> montaListaDias(Date dataPrevIni, Date dataPrevFim, Boolean aplicarPorTurno)
	{
		Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();

		if (dataPrevIni != null && dataPrevFim != null && (dataPrevIni.before(dataPrevFim) || dataPrevIni.equals(dataPrevFim)))
		{
			Date data;
			DiaTurma diaTurma;
			int qtdDias = DateUtil.diferencaEntreDatas(dataPrevIni, dataPrevFim, false);
			
			for (int d = 0; d <= qtdDias; d++)
			{
				data = DateUtil.incrementaDias(dataPrevIni, d);
				
				if (aplicarPorTurno == null || !aplicarPorTurno)
				{
					diaTurma = new DiaTurma();
					diaTurma.setDia(data);
					
					diaTurmas.add(diaTurma);
				}
				else
				{
					Set<Entry<Character, String>> tiposTurno = new TipoTurno().entrySet();
					for (Entry<Character, String> tipoTurno : tiposTurno) 
					{
						if (!tipoTurno.getKey().equals(TipoTurno.DIA))
						{
							diaTurma = new DiaTurma();
							diaTurma.setDia(data);
							diaTurma.setTurno(tipoTurno.getKey());
							
							diaTurmas.add(diaTurma);
						}
					}
				}
			}
		}

		return diaTurmas;
	}
    
	// sempre deleta todos os dias e salva os novos.
	public void saveDiasTurma(Turma turma, String[] diasCheck, String[] horasIni, String[] horasFim) throws Exception
	{
		deleteDiasTurma(turma.getId());
		if(diasCheck != null){
			DiaTurma diaTurmaTmp;
			for (int i=0; i < diasCheck.length; i++){
				String[] dataTurno = diasCheck[i].split(";");

				if(dataTurno.length == 1)
					dataTurno = diasCheck[i].split("-");
				
				diaTurmaTmp = new DiaTurma();
				diaTurmaTmp.setDia(DateUtil.montaDataByString(dataTurno[0].trim()));
				diaTurmaTmp.setTurma(turma);
				
				if(dataTurno.length > 1 && dataTurno[1].length() == 1)
					diaTurmaTmp.setTurno(dataTurno[1].charAt(0));
				
				if(horasIni != null && !"".equals(horasIni[i]))
					diaTurmaTmp.setHoraIni(horasIni[i]);
				
				if(horasFim != null && !"".equals(horasFim[i]))
					diaTurmaTmp.setHoraFim(horasFim[i]);
				
				save(diaTurmaTmp);
			}
		}
		
		getDao().getHibernateTemplateByGenericDao().flush();
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
	
	public void deleteDiasTurma(Long turmaId)
	{
		getDao().deleteDiasTurma(turmaId);
	}
	
	public Collection<DiaTurma> findByTurma(Long turmaId)
	{
		return getDao().findByTurma(turmaId);
	}

	public Collection<DiaTurma> findByTurmaAndPeriodo(Long turmaId, Date dataIni, Date dataFim)
	{
		return getDao().findByTurmaAndPeriodo(turmaId, dataIni, dataFim);
	}

	public Integer qtdDiasDasTurmas(Long turmaId)
	{
		if (turmaId != null)
			return getDao().qtdDiasDasTurmas(turmaId);
		else
			return 0;
	}
	
	public void clonarDiaTurmasDeTurma(Turma turmaOrigem, Turma turmaDestino) 
	{
		Collection<DiaTurma> diaTurmas = getDao().findByTurma(turmaOrigem.getId());
		
		for (DiaTurma diaTurma : diaTurmas) 
		{
			DiaTurma diaTurmaClonada = diaTurma;
			diaTurmaClonada.setId(null);
			diaTurmaClonada.setTurma(turmaDestino);
			
			getDao().save(diaTurmaClonada);
		}
	}
}