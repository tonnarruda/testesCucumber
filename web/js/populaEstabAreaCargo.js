function populaCargosByArea()
{
	DWRUtil.useLoadingMessage('Carregando...');
	var areasIds = getArrayCheckeds(document.forms[0],'areasCheck');
	var empresaId = $('#empresa').val();
	
	if ($('#cargoSemArea').is(":checked"))
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresasMaisSemAreaRelacionada(createListCargosByArea, empresaId, empresaIds);
		else
			CargoDWR.getCargoByAreaMaisSemAreaRelacionada(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa", empresaId);
	}
	else
	{
		if(areasIds.length == 0)
			CargoDWR.getByEmpresas(createListCargosByArea, empresaId, empresaIds);
		else
			CargoDWR.getCargoByArea(createListCargosByArea, areasIds, "getNomeMercadoComEmpresa", empresaId);
	}
}

function createListCargosByArea(data)
{
	addChecks('cargosCheck',data);
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

function populaEstabelecimento(empresaId)
{
	DWRUtil.useLoadingMessage('Carregando...');
	EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
}

function createListEstabelecimento(data)
{
	addChecks('estabelecimentosCheck',data);
}

function populaArea(empresaId)
{
	DWRUtil.useLoadingMessage('Carregando...');
	AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
}

function createListArea(data)
{
	addChecks('areasCheck',data, 'populaCargosByArea();');
}