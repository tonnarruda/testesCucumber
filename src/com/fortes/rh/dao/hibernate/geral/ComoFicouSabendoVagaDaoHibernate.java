package com.fortes.rh.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;

import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;

public class ComoFicouSabendoVagaDaoHibernate extends GenericDaoHibernate<ComoFicouSabendoVaga> implements ComoFicouSabendoVagaDao
{

	@SuppressWarnings("unchecked")
	public Collection<ComoFicouSabendoVaga> findAllSemOutros() 
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ComoFicouSabendoVaga(c.id, c.nome) ");
		hql.append("from ComoFicouSabendoVaga as c ");
		hql.append("	where c.id <> 1 ");
		hql.append("order by c.nome ");

		Query query = getSession().createQuery(hql.toString());

		return  query.list();
	}

	@SuppressWarnings("unchecked")
	public Collection<ComoFicouSabendoVaga> findCandidatosComoFicouSabendoVaga(Date dataIni, Date dataFim, Long empresaId) {
		String sql = "select como.nome, coalesce (cl.percentual,0) " +
						"from comoficousabendovaga como left join ( " +
							"select cf.id as id, cf.nome as nome, " +
										"(count(*) * 100 / ( " +
											"select count(*) from candidato " +
											"where comoficousabendovaga_id is not null " +
											"and dataAtualizacao between :dataIni and :dataFim " +
											"and empresa_id = :empresaId )) as percentual " +
							"from candidato c, comoficousabendovaga cf " +
							"where c.comoficousabendovaga_id = cf.id " +
								"and c.comoficousabendovaga_id is not null " +
								"and c.dataAtualizacao between :dataIni and :dataFim " +
								"and c.empresa_id = :empresaId " +
							"group by cf.id, cf.nome " +
						") as cl on como.id=cl.id ";

		Query query = getSession().createSQLQuery(sql);
		query.setDate("dataIni", dataIni);
		query.setDate("dataFim", dataFim);
		query.setLong("empresaId", empresaId);

		List<Object[]> objetos = query.list();
		Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas = new ArrayList<ComoFicouSabendoVaga>();
		for (Iterator<Object[]> it = objetos.iterator(); it.hasNext();)
		{
			Object[] array = it.next();
			ComoFicouSabendoVaga comoFicouSabendoVaga = new ComoFicouSabendoVaga();
			comoFicouSabendoVaga.setNome((String) array[0]);
			comoFicouSabendoVaga.setPercentual(Double.parseDouble(array[1].toString()));
			comoFicouSabendoVagas.add(comoFicouSabendoVaga);
		}
		
		return comoFicouSabendoVagas;
	}
}
