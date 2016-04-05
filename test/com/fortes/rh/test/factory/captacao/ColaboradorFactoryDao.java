package com.fortes.rh.test.factory.captacao;

import java.util.Date;

import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;

public class ColaboradorFactoryDao
{
	private ColaboradorDao colaboradorDao;
	HistoricoColaboradorDao historicoColaboradorDao;
	
	public ColaboradorFactoryDao(ColaboradorDao colaboradorDao, HistoricoColaboradorDao historicoColaboradorDao) {
		this.colaboradorDao = colaboradorDao;
		this.historicoColaboradorDao = historicoColaboradorDao;
	}
	
	public Colaborador getColaborador(Empresa empresa){
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);
		return colaborador;
	}
	
	public Colaborador getColaboradorComHistorico(Empresa empresa, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date dataHistorico, int statusRetornoAC){
		Colaborador colaborador = getColaborador(empresa);
		getHistoricoColaborador(colaborador, estabelecimento, areaOrganizacional, dataHistorico, statusRetornoAC);
		return colaborador;
	}
	
	public HistoricoColaborador getHistoricoColaborador(Colaborador colaborador, Estabelecimento estabelecimento, AreaOrganizacional areaOrganizacional, Date dataHistorico, int statusRetornoAC){
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(dataHistorico);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setStatus(statusRetornoAC);
		historicoColaboradorDao.save(historicoColaborador);
		return historicoColaborador;
	}
}
