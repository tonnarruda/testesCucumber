package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.util.DateUtil;

@Component
public class DiaTurmaDWR
{
	@Autowired
	private DiaTurmaManager diaTurmaManager;
	@Autowired
	private TurmaManager turmaManager;

	public Collection<DiaTurma> getDias(String dataIniStr, String dataFimStr, Boolean aplicarPorTurno) throws Exception
	{
		Date dataIni = DateUtil.montaDataByString(dataIniStr);
		Date dataFim = DateUtil.montaDataByString(dataFimStr);

		if (dataIni.after(dataFim)) // verifica se dataIni maior q dataFim
			return null;

		return diaTurmaManager.montaListaDias(dataIni, dataFim, aplicarPorTurno);
	}
	
	public String getDiasPorTurma(Long turmaId, String divName)
	{
		String[] properties = new String[] {"dataPrevIni","dataPrevFim"};
		String[] sets = new String[] {"dataPrevIni","dataPrevFim"};;
		String[] key = new String[] {"id"};;
		Object[] value = new Long[] {turmaId};

		StringBuilder result = new StringBuilder();
		Collection<Turma> turmas = turmaManager.findToList(properties, sets, key, value);

		if(turmas != null && turmas.size() > 0)
		{
			Turma turma = (Turma) turmas.toArray()[0];
			Date dataIni = turma.getDataPrevIni();
			Date dataFim = turma.getDataPrevFim();

			if (dataIni.after(dataFim)) // verifica se dataIni maior q dataFim
				return null;

			try
			{
				String diasTurmaMarcadoDescricao;
				Collection<DiaTurma> diasTurmaMarcados = diaTurmaManager.find(new String[] { "turma.id" }, new Object[] { turmaId });
				for (DiaTurma diasTurmaMarcado : diasTurmaMarcados)
				{
					diasTurmaMarcadoDescricao = diasTurmaMarcado.getDescricao();

					result.append("<label for=\"checkGroup"+ divName + diasTurmaMarcadoDescricao +"\" >");
					result.append("<input name=\""+ divName +"\" value=\""+ diasTurmaMarcado.getId() +"\" type=\"checkbox\" id=\"checkGroup"+ divName + diasTurmaMarcadoDescricao + "\">" + diasTurmaMarcadoDescricao);
					result.append("</label>");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

		return result.toString();
	}

	public void setDiaTurmaManager(DiaTurmaManager diaTurmaManager)
	{
		this.diaTurmaManager = diaTurmaManager;
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}
}
