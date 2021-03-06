package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;

public class CargoFactory
{
	public static Cargo getEntity()
	{
		Cargo cargo = new Cargo();

		cargo.setAreaFormacaos(null);
		cargo.setAreasOrganizacionais(null);
		cargo.setCompetencias("competencias");
		cargo.setConhecimentos(null);
		cargo.setEscolaridade("e");
		cargo.setExperiencia("e");
		cargo.setFaixaSalarials(null);
		cargo.setGrupoOcupacional(null);
		cargo.setId(null);
		cargo.setMissao("missão");
		cargo.setNome("nome");
		cargo.setNomeMercado("nomemercado");
		cargo.setObservacao("observação");
		cargo.setRecrutamento("recrutamento");
		cargo.setResponsabilidades("responsabilidades");
		cargo.setSelecao("0");
		cargo.setEmpresa(null);

		return cargo;
	}

	public static Cargo getEntity(Long id)
	{
		Cargo cargo = getEntity();
		cargo.setId(id);

		return cargo;
	}
	
	public static Cargo getEntity(String nome)
	{
		Cargo cargo = getEntity();
		cargo.setNome(nome);

		return cargo;
	}

	public static Collection<Cargo> getCollection()
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(getEntity());

		return cargos;
	}
	
	public static Cargo getEntity(Long id, String nome)
	{
		Cargo cargo = getEntity();
		cargo.setId(id);
		cargo.setNome(nome);

		return cargo;
	}

    public static Cargo getEntity(String string, Empresa empresa, Collection<AreaOrganizacional> areaOrganizacionais) {
        Cargo cargo = getEntity(string);
        cargo.setEmpresa(empresa);
        cargo.setAreasOrganizacionais(areaOrganizacionais);
        return cargo;
    }

}
