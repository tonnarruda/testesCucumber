package com.fortes.rh.util;

import java.util.Collection;

import com.fortes.rh.model.geral.Empresa;

public class EmpresaUtil {

	public static Long[] empresasSelecionadas(Long empresaId, Collection<Empresa> empresas){
		if(empresaId == null || empresaId.equals(-1L)){
			CollectionUtil<Empresa> collectionUtil = new CollectionUtil<Empresa>();
			return collectionUtil.convertCollectionToArrayIds(empresas);
		}else{
			return new Long[]{empresaId};
		}
	}
	
	public static Long[] empresasSelecionadas(Long empresaId, Long[] empresas){
		if(empresaId == null || empresaId.equals(-1L)){
			return empresas;
		}else{
			return new Long[]{empresaId};
		}
	}
	
	public static Long[] empresasSelecionadas(Long[] idEmpresasSelecionadas, Long[] idsEmpresasPermitidas){
        if(idEmpresasSelecionadas != null && idEmpresasSelecionadas.length > 0){
            return idEmpresasSelecionadas;
        }else{
            return idsEmpresasPermitidas;
        }
    }
}