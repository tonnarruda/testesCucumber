function marcarDesmarcarListCheckBox(frm, nameCheck, vMarcar)
{
	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == nameCheck && elements[i].type == 'checkbox')
			{
				elements[i].checked = vMarcar;
 			}
		}
	}
}

function marcarListCheckBox(frm, nameCheck, marcados)
{
	var checks = new Array();
	checks = marcados.toString().split(',');
	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == nameCheck && elements[i].type == 'checkbox')
			{
				for(j = 0; j < checks.length; j++)
				{
					if(elements[i].value == checks[j])
					{
						elements[i].checked = true;
					}
				}
 			}
		}
	}
}

function qtdeChecksSelected(frm, nameCheck)
{
	var result = 0;

	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == nameCheck && elements[i].type == 'checkbox' && elements[i].checked)
			{
				result++;
			}
		}
	}

	return result;
}

function validaCheck(frm, nameCheck, mensagem)
{
	var valido = false;

	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == nameCheck && elements[i].type == 'checkbox' && elements[i].checked)
			{
				valido = true;
				break;
			}
		}
	}
	if (!valido)
	{
		if (mensagem == null || mensagem == "undefined" )
			mensagem = "Selecione pelo menos uma opção.";

		alert(mensagem);
	}
	return valido;
}

function getArrayCheckeds(frm, nameCheck)
{
	var result = new Array();
	var j = 0;

	with(frm)
	{
		for(i = 0; i < elements.length; i++)
		{
			if(elements[i].name == nameCheck && elements[i].type == 'checkbox' && elements[i].checked)
			{
				result[j] = elements[i].value;
				j++;
			}
		}
	}

	return result;
}
function addChecks(divName, dados, onClick)
{
	var result = "";
	var addOnClick = "";

	if(onClick != null && onClick != "")
		addOnClick = "onClick='"+ onClick +"'";

	for (var prop in dados)
	{
		result += "<label for=\"checkGroup"+ divName + prop +"\" >";
		result += "<input name=\""+ divName +"\" value=\""+ prop +"\" type=\"checkbox\" "+ addOnClick +" id=\"checkGroup"+ divName + prop +"\">" + dados[prop];
    	result += "</label><br>";
	}

	var obj = document.getElementById('listCheckBox'+ divName);
	obj.innerHTML = result;
}

function addChecksArray(divName, dados, onClick)
{
	var result = "";
	var addOnClick = "";
	
	if(onClick != null && onClick != "")
		addOnClick = "onClick='"+ onClick +"'";
	
	if(dados && dados.length)
	{
		for (var i=0; i < dados.length; i++) 
		{
			result += "<label for=\"checkGroup"+ divName + i +"\" >";
			result += "<input name=\""+ divName +"\" value=\""+ i +"\" type=\"checkbox\" "+ addOnClick +" id=\"checkGroup"+ divName + i +"\">" + dados[i];
			result += "</label><br>";
		}
	}
	
	var obj = document.getElementById('listCheckBox'+ divName);
	obj.innerHTML = result;
}

function compararData(dataInicio, dataFinal)
{
	var dataIni = document.getElementById(dataInicio).value;
	var dataFim = document.getElementById(dataFinal).value;

	var dataIniSplit = dataIni.split("/");
	var dataFimSplit = dataFim.split("/");

	var dataIniComparacao = new Date();
	var dataFimComparacao = new Date();

	if(dataIniSplit.length == 3 && dataFimSplit.length == 3)
	{
		dataIniComparacao.setYear(Number(dataIniSplit[2]) + 1900);
		dataIniComparacao.setMonth(dataIniSplit[1]);
		dataIniComparacao.setDate(dataIniSplit[0]);

		dataFimComparacao.setYear(Number(dataFimSplit[2]) + 1900);
		dataFimComparacao.setMonth(dataFimSplit[1]);
		dataFimComparacao.setDate(dataFimSplit[0]);
	}
	else if(dataIniSplit.length == 2 && dataFimSplit.length == 2)
	{
		if (dataIniSplit[0]>12)
		{
			alert(document.getElementById(dataInicio).value + " Não é uma data válida.");
			document.getElementById(dataInicio).focus();
			return false;
		}

		if (dataFimSplit[0]>12)
		{
			alert("Data inválida.");
			document.getElementById(dataFinal).focus();
			return false;
		}

		dataIniComparacao.setYear(Number(dataIniSplit[1]) + 1900);
		dataIniComparacao.setMonth(dataIniSplit[0]);
		dataIniComparacao.setDate(1);

		dataFimComparacao.setYear(Number(dataFimSplit[1]) + 1900);
		dataFimComparacao.setMonth(dataFimSplit[0]);
		dataFimComparacao.setDate(0);
	}
	else
	{
		alert("Datas Incompatíveis.");
		return false;
	}

	if(dataIniComparacao <= dataFimComparacao)
	{
		return true;
	}
	else
	{
		alert("Datas inválidas, data Inicial deve ser menor que Final.");
		return false;
	}
}

function paging(pg, formulario)
{
	var submete = true;
	try
	{
		submete = document.getElementById(formulario).onsubmit();
	}
	catch(err)
	{
		submete = true;
	}

	if (submete)
	{
		document.getElementById('pagina').value = pg;
		document.getElementById(formulario).submit();
	}
}

function getKeyCode(evento)
{
	var keyCode;
	if (window.event)
		keyCode = window.event.keyCode;
	else if (evento)
		keyCode = evento.which;

	return keyCode;
}

function validaPagina(evento, pagina, maxPaginas)
{
	var keyCode = getKeyCode(evento);

	if(keyCode == 13)
	{
		if(pagina > 0 && pagina <= maxPaginas)
			return true;
		else
		{
			alert("Esta Página não existe.");
			return false;
		}
	}
}
function mudaPagina(evento, pagina, maxPaginas, formulario)
{
	if(validaPagina(evento, pagina, maxPaginas))
		paging(pagina, formulario);
}

function mudaPaginaLink(evento, pagina, maxPaginas, link)
{
	if(validaPagina(evento, pagina, maxPaginas))
		window.location=link + pagina;
}

function mostrar(obj)
{
	if (obj.style.display == '')
		obj.style.display = 'none';
	else
		obj.style.display = '';
}

function setDisplay(obj, valor)
{
	document.getElementById(obj).style.display = valor;
}

function optionsToArray(options)
{
	var arr = new Array();
	for (i = 0; i < options.length; i++)
	{
		arr[i] = options[i].value;
	}

	return arr;
}

function imprimir(divId, configuracao, classFile)
{
	// Abre a janela conforme a configuração informada
	var WinPrint;
	var configuracaoPadrao = 'letf=20,top=20,width=680,height=368,toolbar=0,scrollbars=1,status=0';
	if(configuracao)
		WinPrint = window.open('','', configuracao);
	else
		WinPrint = window.open('','', configuracaoPadrao);

	// Seta o arquivo de estilo. Caso este não exista utiliza o estilo padrão.
	var css = "";
	if(classFile)
	{
		css = "<link rel='stylesheet' type='text/css' href='" + classFile + "'/>"
	}
	else
	{
		css += "<style>";
		css += "body {";
		css += "	background-color: #FFFFFF;";
		css += "	font-family: verdana,sans-serif";
		css += "	font-size: 13px;";
		css += "}";
		css += "li {";
		css += "	font-family: Verdana, sans-serif;";
		css += "	font-size: 13px;";
		css += "}";
		css += "</style>";
	}


	var prtContent = document.getElementById(divId);
	var body =  "<body>"+prtContent.innerHTML+"</body>";
	WinPrint.document.write(css + body);

	WinPrint.document.close();
	WinPrint.focus();
	WinPrint.print();
	WinPrint.close();
}
