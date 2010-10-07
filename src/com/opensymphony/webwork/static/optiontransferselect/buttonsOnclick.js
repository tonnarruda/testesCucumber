var iQtdeValues;

function setValuesOption(sel)
{
	iQtdeValues = 0;
	for (var i = 0; i < sel.options.length; i++)
		iQtdeValues++;
}

function verifyValuesOption(sel)
{
	var iQtde = 0;
	for (var i = 0; i < sel.options.length; i++)
		iQtde++;
	
	if(iQtde != iQtdeValues)
		return true;
	else
		return false;
}