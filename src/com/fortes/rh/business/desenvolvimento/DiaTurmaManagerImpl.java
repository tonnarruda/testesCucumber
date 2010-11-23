package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.DiaTurmaDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("deprecation")
public class DiaTurmaManagerImpl extends GenericManagerImpl<DiaTurma, DiaTurmaDao> implements DiaTurmaManager
{
	private PlatformTransactionManager transactionManager;

	public Collection<DiaTurma> montaListaDias(Date dataPrevIni, Date dataPrevFim)
	{
		Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();

		if(dataPrevIni != null && dataPrevFim != null && (dataPrevIni.before(dataPrevFim) || dataPrevIni.equals(dataPrevFim)))
		{
			Long dif = dataPrevFim.getTime() - dataPrevIni.getTime();
			dif = 1 +(dif / (24*60*60*1000));

			DiaTurma diaTurma;

			for(Long cont = 1L;cont <= dif; cont++)
			{
				diaTurma = new DiaTurma();
				diaTurma.setId(cont);

				Date data = (Date) dataPrevIni.clone();
				diaTurma.setDia(data);

				diaTurmas.add(diaTurma);

				dataPrevIni.setDate(dataPrevIni.getDate() + 1 );
			}
		}

		return diaTurmas;
	}
    // sempre deleta todos os dias e salva os novos.
	public void saveDiasTurma(Turma turma, String[] diasCheck) throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			deleteDiasTurma(turma.getId());

			if(diasCheck != null)
			{
				DiaTurma diaTurmaTmp;
				for (String valueDias : diasCheck)
				{
					String dataStr = valueDias.substring(0, 10);

					diaTurmaTmp = new DiaTurma();
					diaTurmaTmp.setDia(DateUtil.montaDataByString(dataStr));
					diaTurmaTmp.setTurma(turma);

					save(diaTurmaTmp);
				}
			}

			transactionManager.commit(status);
		}
		catch(Exception e)
		{
			transactionManager.rollback(status);
			throw e;
		}
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