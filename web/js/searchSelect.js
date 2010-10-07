var memoria			= new Array();
var memoriaValue	= new Array();
var oldValue;

function searchSelect(valor,selectListId)
{
	if(valor == oldValue)
		return false;
	else
		oldValue = valor;

	if(!selectObject)
		var selectObject = document.getElementById(selectListId);
	if(memoria.length == 0 && memoriaValue.length == 0)
	{
		for(var count in selectObject.options)
		{
			memoria[count]		= selectObject.options[count].innerHTML;
			memoriaValue[count]	= selectObject.options[count].value;

			if(count == selectObject.options.length - 1)//gambi
				break;
		}
	}
	var exibir		= new Array();
	var exibirValue	= new Array();
	var contador	= 0;
	var jaAchou		= false;

	for(var i in memoria)
	{
		if(valor.toLowerCase() == memoria[i].substring(0,valor.length).toLowerCase())
		{
			exibir[contador]		= memoria[i];
			exibirValue[contador]	= memoriaValue[i];
			contador++;
			if(!jaAchou)
				jaAchou = true;
		}
		else
		{
			if(jaAchou)
				break;
		}
	}
	selectObject.options.length = null;

	for(var i in exibir)
		selectObject.options[i] = new Option(exibir[i],exibirValue[i]);
}