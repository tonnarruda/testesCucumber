function populaCargosByArea()
{
	dwr.engine.setAsync(true);
	dwr.util.useLoadingMessage('Carregando...');
	var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
	var empresaId = $('#empresa').val();
	
	if ($('#cargoSemArea').is(":checked"))
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresasMaisSemAreaRelacionada(empresaId, empresaIds, createListCargosByArea);
		else
			CargoDWR.getCargoByAreaMaisSemAreaRelacionada(areasIds, "getNomeMercadoComEmpresa", empresaId, createListCargosByArea);
	}
	else
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresas(empresaId, empresaIds, createListCargosByArea);
		else
			CargoDWR.getCargoByArea(areasIds, "getNomeMercadoComEmpresa", empresaId, createListCargosByArea);
	}
}

function populaCargosByAreaVinculados(empresaId, empresaIds)
{
	dwr.engine.setAsync(true);
	dwr.util.useLoadingMessage('Carregando...');
	var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
	var empresaId = $('#empresa').val();
	
	if ($('#cargosVinculadosAreas').is(":checked"))
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresas(empresaId, empresaIds, createListCargosByArea);
		else
			CargoDWR.getCargoByArea(areasIds, "getNomeMercadoComEmpresaEStatus", empresaId, createListCargosByArea);
	}
	else
		CargoDWR.getByEmpresas(empresaId, empresaIds, createListCargosByArea);
}

function createListCargosByArea(data)
{
	addChecksByMap('cargosCheck', data);
}

function verificaCargoSemAreaRelacionada(empresaId)
{
	CargoDWR.verificaCargoSemAreaRelacionada(empresaId, exibeCheckCargoSemArea);
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
	dwr.engine.setAsync(true);
	dwr.util.useLoadingMessage('Carregando...');
	EstabelecimentoDWR.getByEmpresas(empresaId, empresaIds, createListEstabelecimento);
}

function createListEstabelecimento(data)
{
	addChecks('estabelecimentosCheck',data);
}

function populaArea(empresaId)
{
	dwr.engine.setAsync(true);
	dwr.util.useLoadingMessage('Carregando...');
	AreaOrganizacionalDWR.getByEmpresas(empresaId, empresaIds, createListArea);
}

function createListArea(data)
{
	addChecks('areasCheck',data, 'populaCargosByArea();');
}

function populaAreaComCargoVinculado(empresaId,empresaIds)
{
	dwr.engine.setAsync(true);
	dwr.util.useLoadingMessage('Carregando...');
	AreaOrganizacionalDWR.getByEmpresas(empresaId, empresaIds, createListArea);
}

function createListArea(data)
{
	addChecks('areasCheck',data, 'populaCargosByAreaVinculados();');
}