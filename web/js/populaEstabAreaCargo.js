function populaCargosByArea()
{
	DWREngine.setAsync(true);
	DWRUtil.useLoadingMessage('Carregando...');
	var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
	var empresaId = $('#empresa').val();
	
	if ($('#cargoSemArea').is(":checked"))
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresasMaisSemAreaRelacionada(createListCargosByArea, empresaId, empresaIds);
		else
			CargoDWR.getCargoByAreaMaisSemAreaRelacionada(createListCargosByArea, areasIds, "getNomeMercadoComEmpresaEStatus", empresaId);
	}
	else
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
		else
			CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresaEStatus", empresaId);
	}
}

function populaCargosByAreaVinculados(empresaId, empresaIds)
{
	DWREngine.setAsync(true);
	DWRUtil.useLoadingMessage('Carregando...');
	var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
	var empresaId = $('#empresa').val();
	
	if ($('#cargosVinculadosAreas').is(":checked"))
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
		else
			CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresaEStatus", empresaId);
	}
	else
		CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
}

function createListCargosByArea(data)
{
	addChecksByMap('cargosCheck', data);
}

function verificaCargoSemAreaRelacionada(empresaId)
{
	CargoDWR.verificaCargoSemAreaRelacionada(exibeCheckCargoSemArea, empresaId);
}

function exibeCheckCargoSemArea(data)
{
	$('#wwgrp_cargoSemArea').toggle(data);
}

function changeEmpresa(value)
{
	populaEstabelecimento(value);
	populaArea(value);
	populaCargosByArea();
	verificaCargoSemAreaRelacionada(value);
	
	$('#cargoSemArea').attr('checked', false);
}

function newChangeEmpresa(empresaId)
{
	$("input[name='areasCheck']:checked").attr('checked',false);
	var empresaIds = new Array();
	if(empresaId == null || empresaId == 0 || empresaId == -1 ){
		var optionsEmpresaIds = $(".empresaSelect").find("option");
		$(optionsEmpresaIds).each(function (i, option) {
			if($(option).val() != "" && $(option).val() != -1 && $(option).val() != 0 )
				empresaIds.push($(option).val());
		});
	}

	populaAreaComCargoVinculado(empresaId, empresaIds);
	populaEstabelecimento(empresaId, empresaIds);
	populaCargosByAreaVinculados(empresaId, empresaIds);
}

function populaEstabelecimento(empresaId, empresaIds)
{
	DWREngine.setAsync(true);
	DWRUtil.useLoadingMessage('Carregando...');
	EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
}

function createListEstabelecimento(data)
{
	addChecks('estabelecimentosCheck',data);
}

function populaArea(empresaId)
{
	DWREngine.setAsync(true);
	DWRUtil.useLoadingMessage('Carregando...');
	AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
}

function createListArea(data)
{
	addChecks('areasCheck',data, 'populaCargosByArea();');
}

function populaAreaComCargoVinculado(empresaId,empresaIds)
{
	DWREngine.setAsync(true);
	DWRUtil.useLoadingMessage('Carregando...');
	AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
}

function createListArea(data)
{
	addChecks('areasCheck',data, 'populaCargosByAreaVinculados();');
}