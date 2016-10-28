package com.fortes.rh.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.ComoFicouSabendoVagaDao;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

@Component
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
		String sql = "select como.id, como.nome, cast(coalesce(cl.qtd, 0)as Integer) as qtd, cast(coalesce (cl.percentual,0) as double precision) as percentual " +
						"from comoficousabendovaga como left join ( " +
							"select cf.id as id, cf.nome as nome, count(*) as qtd, " +
										"(cast(count(*) as double precision) / ( " +
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
						") as cl on como.id=cl.id order by como.nome";

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
			comoFicouSabendoVaga.setId(Long.parseLong(array[0].toString()));
			comoFicouSabendoVaga.setNome((String) array[1]);
			comoFicouSabendoVaga.setQtd((Integer) array[2]);
			comoFicouSabendoVaga.setPercentual((Double) array[3]);
			comoFicouSabendoVagas.add(comoFicouSabendoVaga);
		}
		
		return comoFicouSabendoVagas;
	}
}
