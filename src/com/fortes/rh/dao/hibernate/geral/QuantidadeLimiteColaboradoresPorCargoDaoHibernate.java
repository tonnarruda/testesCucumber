package com.fortes.rh.dao.hibernate.geral;

import org.hibernate.Query;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.dao.GenericDaoHibernate;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;

public class QuantidadeLimiteColaboradoresPorCargoDaoHibernate extends GenericDaoHibernate<QuantidadeLimiteColaboradoresPorCargo> implements QuantidadeLimiteColaboradoresPorCargoDao
{

	public void save(AreaOrganizacional areaOrganizacional, Cargo cargo, int limite) {
		String hql = "insert into QuantidadeLimiteColaboradoresPorCargo(areaorganizacional.id, cargo.id, limite) select :areaId, :cargoId, :limite";
		Query query = getSession().createQuery(hql);

		query.setLong("areaId", areaOrganizacional.getId());
		query.setLong("cargoId", cargo.getId());
		query.setInteger("limite", limite);

		query.executeUpdate();

		
	}
}
