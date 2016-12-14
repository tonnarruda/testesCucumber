var BrowserDetect = {
	init: function ( callback ) {
		this.browser = this.searchString( this.dataBrowser ) || "browser não encontrado";
		this.version = this.searchVersion( navigator.userAgent )
			|| this.searchVersion( navigator.appVersion )
			|| "versão não encontrada";
		this.OS = this.searchString(this.dataOS) || "sistema operacional não encontrado";
		callback({
			name : this.browser, 
			version : this.version,
			OS : this.OS
		} );
	},
	searchString: function (data) {
		var dataString, dataProp
		  , i=0, size = data.length;
		for ( ; i<size; i++ ){
			dataString = data[i].string;
			dataProp = data[i].prop;
			this.versionSearchString = data[i].versionSearch || data[i].identity;
			if ( dataString ){
				if (dataString.indexOf(data[i].subString) != -1)
					return data[i].identity;
			}
			else if ( dataProp )
				return data[i].identity;
		}
	},
	searchVersion: function (dataString) {
		var index = dataString.indexOf( this.versionSearchString );
		if ( index === -1 ) return;
		return parseFloat( dataString.substring( index + this.versionSearchString.length + 1 ) );
	},
	dataBrowser: [
  		{ string: navigator.userAgent, subString: "Chrome", identity: "Chrome" },
  		{ string: navigator.userAgent, subString: "OmniWeb", versionSearch: "OmniWeb/", identity: "OmniWeb"	},
  		{ string: navigator.vendor,	subString: "Apple",	identity: "Safari",	versionSearch: "Version"},
  		{ prop: window.opera, identity: "Opera",versionSearch: "Version"},
  		{ string: navigator.vendor,	subString: "iCab",	identity: "iCab"},
  		{ string: navigator.vendor,	subString: "KDE",identity: "Konqueror"},
  		{ string: navigator.userAgent, subString: "Firefox", identity: "Firefox"},
  		{ string: navigator.vendor,	subString: "Camino", identity: "Camino"	},
  		{ string: navigator.userAgent, subString: "Netscape", identity: "Netscape"},
  		{ string: navigator.userAgent, subString: "MSIE", identity: "Explorer", versionSearch: "MSIE"},
  		{ string: navigator.userAgent, subString: "Trident", identity: "Explorer", versionSearch: "rv"},
  		{ string: navigator.userAgent, subString: "Gecko", identity: "Mozilla", versionSearch: "rv"	},
  		{ string: navigator.userAgent, subString: "Mozilla", identity: "Netscape", versionSearch: "Mozilla"	}
  	],
  	dataOS : [
  		{ string: navigator.platform, subString: "Win", identity: "Windows" },
  		{ string: navigator.platform, subString: "Mac", identity: "Mac" },
  		{ string: navigator.userAgent, subString: "iPhone", identity: "iPhone/iPod" },
  		{ string: navigator.platform, subString: "Linux", identity: "Linux" }
  	]
};

var browsersCompativeis = {
	Firefox : { versaoMinima : 4, url : 'http://br.mozdev.org/download/' },
	Chrome : { versaoMinima : 9, url : 'https://www.google.com/chrome?hl=pt-br' }, 
	Explorer: { versaoMinima : 8, url : 'http://windows.microsoft.com/pt-BR/internet-explorer/downloads/ie' },
	Safari : { versaoMinima : 4, url : 'http://www.apple.com/br/safari/download/' }
};

BrowserDetect.init( function ( informacaoesDesteBrowser ){
    var versaoMinimaDesteBrowserParaSerCompativelComSistema = browsersCompativeis[ informacaoesDesteBrowser.name ].versaoMinima;
    var isThisBrowserSupportted = ( informacaoesDesteBrowser.version >= versaoMinimaDesteBrowserParaSerCompativelComSistema );
    var url = window.location + '';
	if (location.href.indexOf('browsersCompativeis.action') == -1 && !isThisBrowserSupportted && url.indexOf("pesquisa/trafego") == -1)
	{
		var patt = /\/\w*\//g;
		window.location.href = patt.exec(window.location.pathname) + 'browsersCompativeis.action';
	}
});


function marcarDesmarcarListCheckBox(frm, nameCheck, vMarcar)
{
	$(frm).find("input[name='" + nameCheck + "']:visible").each(function(i, item) {
		if(!$(item).is(':disabled'))
			$(item).attr('checked',vMarcar).change();
	});
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

		jAlert(mensagem);
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

function addChecks(divName, data, onClick, orderByData)
{
	var result = "";
	var addOnClick = "";
	var dados = new Array();
	var i = 0;

	for (var key in data) {
		dados[i] = new Array();
		dados[i][0] = data[key];
		dados[i][1] = key;
		i++;
	}
	
	if(!orderByData)
		dados.sort();

	if(onClick != null && onClick != "")
		addOnClick = "onClick='"+ onClick +"'";
	
	for (var i = 0 ;i < dados.length; i++)
	{
		result += "<label for=\"checkGroup"+ divName + dados[i][1] +"\" >";
		result += "<input name=\""+ divName +"\" value=\""+ dados[i][1] +"\" type=\"checkbox\" "+ addOnClick +" id=\"checkGroup"+ divName + dados[i][1] +"\">" + dados[i][0];
    	result += "</label>";
	}

	$('#listCheckBox'+ divName.replace("[","\\[").replace("]","\\]")).html(result);
	$('#listCheckBoxFilter' + divName + ', #listCheckBoxActive' + divName).val('');
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
			result += "<input name=\""+ divName +"\" value=\""+ dados[i] +"\" type=\"checkbox\" "+ addOnClick +" id=\"checkGroup"+ divName + i +"\">" + dados[i];
			result += "</label>";
		}
	}
	
	$('#listCheckBox'+ divName.replace("[","\\[").replace("]","\\]")).html(result);
	$('#listCheckBoxFilter' + divName + ', #listCheckBoxActive' + divName).val('');
}

function addChecksByCollection(divName, dados, label, onClick)
{
	var result = "";
	var addOnClick = "";
	var selecionado = '';
	var desabilitado = '';
	var titulo = '';
	var parameters = '';
	var label = label ? label : 'nome';

	if (onClick != null && onClick != "")
		addOnClick = "onClick='"+ onClick +"'";

	for (var prop in dados)
	{
		selecionado = dados[prop]['selecionado'] ? 'checked="checked"' : '';
		desabilitado = dados[prop]['desabilitado'] ? 'disabled="disabled"' : '';
		parameters = dados[prop]['parameters'] ? dados[prop]['parameters'] : '';
		titulo = dados[prop]['titulo'] ? 'title="' + dados[prop]['titulo'] + '"' : '';
		
		result += "<label for=\"checkGroup"+ divName + dados[prop]['id'] +"\" >";
		result += "<input name=\""+ divName +"\" value=\""+ dados[prop]['id'] +"\" type=\"checkbox\" "+ addOnClick +" id=\"checkGroup"+ divName + dados[prop]['id'] +"\" " + titulo + " " + selecionado + " " + desabilitado; 
		for ( var i in parameters)
			result += " "+i+"="+parameters[i];
		result += " />" + dados[prop][label];
    	result += "</label>";
	}

	$('#listCheckBox'+ divName).html(result);
	$('#listCheckBoxFilter' + divName + ', #listCheckBoxActive' + divName).val('');
}

function addChecksByMap(divName, data, onClick)
{
	var dados = new Array();
	var i = 0;
	
	for (var key in data) {
		dados[i] = new Array();
		dados[i][0] = data[key];
		dados[i][1] = key;
		i++;
	}
	
	dados.sort(function(a,b) {
		if (a[0].toLowerCase() < b[0].toLowerCase()) return -1;
	    if (a[0].toLowerCase() > b[0].toLowerCase()) return 1;
	    return 0;
	});
	
	var result = "";
	var addOnClick = "";

	if (onClick != null && onClick != "")
		addOnClick = "onClick='"+ onClick +"'";

	for (var i = 0 ;i < dados.length; i++)
	{
		result += "<label for=\"checkGroup"+ divName + dados[i][1] +"\" >";
		result += "<input name=\""+ divName +"\" value=\""+ dados[i][1] +"\" type=\"checkbox\" "+ addOnClick +" id=\"checkGroup"+ divName + dados[i][1] +"\" title=\"" + dados[i][0] + "\" />" + dados[i][0];
    	result += "</label>";
	}

	$('#listCheckBox'+ divName).html(result);
	$('#listCheckBoxFilter' + divName + ', #listCheckBoxActive' + divName).val('');
}

function addOptionsByCollection(selectId, dados, prompt, label)
{
	var result = prompt ? "<option value=''>" + prompt + "</option>" : "";
	var selecionado = '';
	var desabilitado = '';
	var titulo = '';
	var label = label ? label : 'nome';

	dados.sort(function(a,b) {
		if (a[label].toLowerCase() < b[label].toLowerCase()) return -1;
	    if (a[label].toLowerCase() > b[label].toLowerCase()) return 1;
	    return 0;
	});
	
	for (var prop in dados)
	{
		selecionado = dados[prop]['selecionado'] ? 'selected=\"selected\"' : '';
		desabilitado = dados[prop]['desabilitado'] ? 'disabled=\"disabled\" style=\"color:#cccccc\"' : '';
		titulo = dados[prop]['titulo'] ? 'title=\"' + dados[prop]['titulo'] + '\"' : '';
		
		result += "<option value='" + dados[prop]['id'] + "' " + titulo + " " + selecionado + " " + desabilitado + ">" + dados[prop][label] + "</option>\n";
	}

	$('#' + selectId).html(result);
}

function addOptionsByMap(selectId, data, prompt)
{
	var dados = new Array();
	var i = 0;
	
	for (var key in data) {
		dados[i] = new Array();
		dados[i][0] = data[key];
		dados[i][1] = key;
		i++;
	}
	
	dados.sort(function(a,b) {
		if (a[0].toLowerCase() < b[0].toLowerCase()) return -1;
	    if (a[0].toLowerCase() > b[0].toLowerCase()) return 1;
	    return 0;
	});
	
	var result = prompt ? "<option value=''>" + prompt + "</option>" : "";

	for (var i = 0 ;i < dados.length; i++)
	{
		result += "<option value='" + dados[i][1] + "' title='" + dados[i][0] + "'>" + dados[i][0] + "</option>\n";
	}

	$('#' + selectId).html(result);
}


function addChecksCheckBox(divName, data, marcados)
{
	var i = 0;
	var result = "";
	var qtdParamter = 0; 
	var dados = new Array();

	for (var key in data) {
		dados[i] = new Array();
		dados[i][0] = data[key].nome;
		dados[i][1] = data[key].id;
							
		var j = 0;
		var paramters = data[key].parameters; 
		for (var keyPar in paramters){
			dados[i][2+j] = keyPar + "=\"" + paramters[keyPar] + "\"";
			j++;
		}
		
		if(qtdParamter < j) qtdParamter = j;
		i++;
	}

	dados.sort(function(a,b) {
		if (a[0].toLowerCase() < b[0].toLowerCase()) return -1;
	    if (a[0].toLowerCase() > b[0].toLowerCase()) return 1;
	    return 0;
	});
	
	for (var i = 0 ;i < dados.length; i++)
	{
		result += "<label for=\"checkGroup"+ divName + dados[i][1] +"\" >";
		result += "<input name=\""+ divName +"\" value=\""+ dados[i][1] +"\" type=\"checkbox\" id=\"checkGroup"+ divName + dados[i][1] + "\"";

		for (var j = 0;j < qtdParamter; j++)
			if(dados[i][2 + j])
				result += " " + dados[i][2 + j];	
		
		if(marcados && marcados.indexOf(parseInt(dados[i][1])) != -1) 
			result += " checked ";
		
		result += "/>" + dados[i][0] + "</label>";
	}

	$('#listCheckBox'+ divName.replace("[","\\[").replace("]","\\]")).html(result);
	$('#listCheckBoxFilter' + divName + ', #listCheckBoxActive' + divName).val('');
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
			jAlert(document.getElementById(dataInicio).value + " Não é uma data válida.");
			document.getElementById(dataInicio).focus();
			return false;
		}

		if (dataFimSplit[0]>12)
		{
			jAlert("Data inválida.");
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
		jAlert("Datas Incompatíveis.");
		return false;
	}

	if(dataIniComparacao <= dataFimComparacao)
	{
		return true;
	}
	else
	{
		jAlert("Datas inválidas, data Inicial deve ser menor que Final.");
		return false;
	}
}

function compararStrings(a, b) 
{
    if (a.toLowerCase() < b.toLowerCase()) return -1;
    if (a.toLowerCase() > b.toLowerCase()) return 1;
    return 0;
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
			jAlert("Esta Página não existe.");
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

function loader(exibir) 
{
    if (exibir) {
	    var messageZone = document.getElementById('messageZone');
	    var iframeZone = document.getElementById('iframeZone');
	    if (!messageZone)
	    {
	
	      iframeZone = document.createElement('iframe');
	      iframeZone.setAttribute('id', 'iframeZone');
		  iframeZone.allowtransparency= "true";
	
	      document.body.appendChild(iframeZone);
	
	      messageZone = document.createElement('div');
	      messageZone.setAttribute('id', 'messageZone');
	
	      document.body.appendChild(messageZone);
	    }
	    else
	    {
	      messageZone.style.visibility = 'visible';
	      iframeZone.style.visibility = 'visible';
	    }
	    
    } else {
		document.getElementById('messageZone').style.visibility = 'hidden';
		document.getElementById('iframeZone').style.visibility = 'hidden';
    }
}

function popupVideo(videoId, hash) 
{
	window.open('http://www.fortesinformatica.com.br/videos/portal_videoteca_ver_new.php?id=' + videoId + '&hash=' + hash,'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,menubar=no,width=720,height=660')
}

function popupDetalhes(videoId) 
{
	window.open('http://www.fortesinformatica.com.br/portal_videoteca_detalhes.php?id=' + videoId, 'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,menubar=no,width=385,height=580')
}

function formataNumero(value)
{
	return 'R$' + $('<span>' + value + '</span>').format({}).text().replace(/,/g,'#').replace(/\./g,',').replace(/#/g,'.');
}