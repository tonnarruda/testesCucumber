 // Função que permite apenas teclas numéricas e todos os caracteres que estiverem na lista
// de argumentos. Deve ser chamada no evento onKeyPress desta forma:
// onKeyPress = "return(somenteNumeros(event,'(/){,}.'));"
// caso queira apenas permitir caracters
function somenteNumeros(e,args,value)
{
    if (document.all) // caso seja IE
	{
		var evt = event.keyCode;
	}
    else // do contrário deve ser Mozilla
	{
		var evt = e.charCode;
	}

	var chr = String.fromCharCode(evt); // pegando a tecla digitada
	
	if(value && value.indexOf(chr) == 0)//serve para não repetir o character no campo Ex: "-" (valor negativo).
	{
		return false;
	}

	// Se o código for menor que 20 é porque deve ser caracteres de controle
    // ex.: <ENTER>, <TAB>, <BACKSPACE> portanto devemos permitir
    // as teclas numéricas vão de 48 a 57
    if (evt < 20 || (evt > 47 && evt < 58) || (args.indexOf(chr) > -1 ))
	{
		return true;
	}

	return false;
}

function popup(caminho, altura, largura)
{
	path = caminho;
	var win = window.open(caminho,'Janela1','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,menubar=no,width='+largura+',height='+altura+',left=10,top=10');
	win.focus();
}

String.prototype.trim = function() { return this.replace(/^\s+|\s+$/, ''); };

// Tooltip
// Função retirada do site dhtmlgoodies
var dhtmlgoodies_tooltip = false;
var dhtmlgoodies_tooltipShadow = false;
var dhtmlgoodies_shadowSize = 4;
var dhtmlgoodies_tooltipMaxWidth = 200;
var dhtmlgoodies_tooltipMinWidth = 100;
var dhtmlgoodies_iframe = false;
var tooltip_is_msie = (navigator.userAgent.indexOf('MSIE')>=0 && navigator.userAgent.indexOf('opera')==-1 && document.all)?true:false;

function showTooltip(e,tooltipTxt, size)
{
	var bodyWidth = Math.max(document.body.clientWidth,document.documentElement.clientWidth) - 20;

	if(!dhtmlgoodies_tooltip)
	{
		dhtmlgoodies_tooltip = document.createElement('DIV');
		dhtmlgoodies_tooltip.id = 'dhtmlgoodies_tooltip';
		dhtmlgoodies_tooltipShadow = document.createElement('DIV');
		dhtmlgoodies_tooltipShadow.id = 'dhtmlgoodies_tooltipShadow';

		document.body.appendChild(dhtmlgoodies_tooltip);
		document.body.appendChild(dhtmlgoodies_tooltipShadow);

		if(tooltip_is_msie)
		{
			dhtmlgoodies_iframe = document.createElement('IFRAME');
			dhtmlgoodies_iframe.frameborder='5';
			dhtmlgoodies_iframe.style.backgroundColor='#FFFFFF';
			dhtmlgoodies_iframe.src = '#';
			dhtmlgoodies_iframe.style.zIndex = 100;
			dhtmlgoodies_iframe.style.position = 'absolute';
			document.body.appendChild(dhtmlgoodies_iframe);
		}

	}

	dhtmlgoodies_tooltip.style.display='block';
	dhtmlgoodies_tooltipShadow.style.display='block';
	if(tooltip_is_msie)
		dhtmlgoodies_iframe.style.display='block';

	var st = Math.max(document.body.scrollTop,document.documentElement.scrollTop);
	if(navigator.userAgent.toLowerCase().indexOf('safari')>=0)
		st=0;

	var leftPos = e.clientX + 30;

	dhtmlgoodies_tooltip.style.width = null;	// Reset style width if it's set
	dhtmlgoodies_tooltip.innerHTML = tooltipTxt;
	dhtmlgoodies_tooltip.style.left = leftPos + 'px';
	dhtmlgoodies_tooltip.style.top = e.clientY + 10 + st + 'px';

	dhtmlgoodies_tooltipShadow.style.left =  leftPos + dhtmlgoodies_shadowSize + 'px';
	dhtmlgoodies_tooltipShadow.style.top = e.clientY + 10 + st + dhtmlgoodies_shadowSize + 'px';

	if(dhtmlgoodies_tooltip.offsetWidth>dhtmlgoodies_tooltipMaxWidth)
	{	/* Exceeding max width of tooltip ? */
		dhtmlgoodies_tooltip.style.width = dhtmlgoodies_tooltipMaxWidth + 'px';
	}

	var tooltipWidth = dhtmlgoodies_tooltip.offsetWidth;
	if(tooltipWidth<dhtmlgoodies_tooltipMinWidth)
		tooltipWidth = dhtmlgoodies_tooltipMinWidth;
	if(size == undefined)
		size = 0;

	dhtmlgoodies_tooltip.style.width = tooltipWidth + size + 'px';
	dhtmlgoodies_tooltipShadow.style.width = dhtmlgoodies_tooltip.offsetWidth + 'px';
	dhtmlgoodies_tooltipShadow.style.height = dhtmlgoodies_tooltip.offsetHeight + 'px';

	if((leftPos + tooltipWidth)>bodyWidth)
	{
		dhtmlgoodies_tooltip.style.left = (dhtmlgoodies_tooltipShadow.style.left.replace('px','') - ((leftPos + tooltipWidth)-bodyWidth)) + 'px';
		dhtmlgoodies_tooltipShadow.style.left = (dhtmlgoodies_tooltipShadow.style.left.replace('px','') - ((leftPos + tooltipWidth)-bodyWidth) + dhtmlgoodies_shadowSize) + 'px';
	}

	if(tooltip_is_msie)
	{
		dhtmlgoodies_iframe.style.left = dhtmlgoodies_tooltip.style.left;
		dhtmlgoodies_iframe.style.top = dhtmlgoodies_tooltip.style.top;
		dhtmlgoodies_iframe.style.width = dhtmlgoodies_tooltip.offsetWidth + 'px';
		dhtmlgoodies_iframe.style.height = dhtmlgoodies_tooltip.offsetHeight + 'px';

	}
}

function hideTooltip()
{
	dhtmlgoodies_tooltip.style.display='none';
	dhtmlgoodies_tooltipShadow.style.display='none';
	if(tooltip_is_msie)dhtmlgoodies_iframe.style.display='none';
}

function limpaCamposMascaraCpf(cpfSemMascara)
{
	if(cpfSemMascara != "")
	{
		cpfSemMascara = cpfSemMascara.replace('.','');
		cpfSemMascara = cpfSemMascara.replace('.','');
		cpfSemMascara = cpfSemMascara.replace('-','');

		cpfSemMascara = cpfSemMascara.trim();
	}

	return cpfSemMascara;
}

function limpaCamposMascaraCnpj(cnpj)
{
	if(cnpj != "")
	{
		cnpj = cnpj.replace('.','');
		cnpj = cnpj.replace('.','');
		cnpj = cnpj.replace('/','');
		cnpj = cnpj.replace('-','');

		cnpj = cnpj.trim();
	}

	return cnpj;
}

function limpaCamposMascaraCep(cep)
{
	if(cep != "")
	{
		cep = cep.replace('-','');
		cep = cep.trim();
	}

	return cep;
}

function limpaCamposMascaraDataMesAno(data)
{
	data = data.replace('/','');
	data = data.trim();

	return data;
}

function limpaCamposMascaraData(data)
{
	data = data.replace('/','');
	data = data.replace('/','');
	data = data.trim();

	return data;
}

function validaCampos(campos)
{
	var qtdElementos = campos.length;
	var campo;
	var erro = 0;
	var campoFocus = "";

	var valueDataMesAno = "  /    ";
	var valueData = "  /  /    ";
	var valueCnpj = "  .   .   /    -  ";
	var valueCpf = "   .   .   -  ";
	var valueCep = "     -   ";
	var valueHora = "  :  ";

	var campoClass;

	for(var contador = 0; contador < qtdElementos; contador++)
	{
		var validacao = true;
		campo = document.getElementById(campos[contador]);

		if(campo)
		{
			campoClass = campo.className;
			// valida campos
			if(campoClass.substring(0, 17) == "mascaraMesAnoData" && campo.value != valueDataMesAno)
				validacao = validaDateMesAno(campo);
			else if(campoClass.substring(0, 11) == "mascaraData" && campo.value != valueData && campo.value != "")
				validacao = validaDate(campo);
			else if(campoClass == "mascaraCnpj" && campo.value != valueCnpj)
				validacao = validaCpfCnpj(campo);
			else if(campoClass == "mascaraCpf" && campo.value != valueCpf)
				validacao = validaCpfCnpj(campo);
			else if(campoClass == "mascaraCep" && campo.value != valueCep)
				validacao = true;
			else if(campoClass == "mascaraHora" && campo.value != valueHora)
				validacao = validaHora(campo.value);
			else if(campoClass == "mascaraPis")
				validacao = validaPIS(campo);
			else if (campoClass == "mascaraEmail" && campo.value != "")
				validacao = validaEmail(campo.value, campo.id);
			else if (campoClass == "currency" && campo.value != "")
				validacao = validaCurrency(campo.value);
			
			if(validacao)
			{
				campo.style.background = "#FFF"; // Cor do campo
													// validado(depois de
													// corrigido)
			}
			else
			{
				if(campoFocus == "")
					campoFocus = campo;

				campo.style.background = "#FF6347"; // Cor do campo invalidado.
				erro += 1;
			}
		}
	}

	if(erro > 0)
	{
		jAlert("Campos inválidos.");
		campoFocus.focus();
		return false;
	}
	else
		return true;
}
// Função Generica para validação de campos obrigatorios do formulario
// onsubmit="return validarCampos(new Array('campo1','campo2',...));"
// return true ou false

var exibeLabelDosCamposNaoPreenchidos = false;
function validaCamposObrigatorios(campos, formulario)
{
	var qtdElementos = campos.length;
	var campo;
	var nameCampo;
	var nameCampoSemIdentificador;
	var erro = 0;
	var campoFocus = "";

	var valueDataMesAno = "  /    ";
	var valueData = "  /  /    ";
	var valueCnpj = "  .   .   /    -  ";
	var valueCpf = "   .   .   -  ";
	var valueCep = "     -   ";
	var valueHora = "  :  ";

	var campoClass;

	for(var contador = 0; contador < qtdElementos; contador++)
	{
		var validacao = false;
		nameCampo = campos[contador];
		if(nameCampo.substr(0,1) == '@')
		{
			nameCampoSemIdentificador = nameCampo.replace('@','');
			nameCampo = 'listCheckBox' + nameCampoSemIdentificador;
		}

		console.log(nameCampo);
		campo = document.getElementById(nameCampo);
		campoClass = campo.className;

		if(campo)
		{

			// valida campos
			if(campoClass == "listCheckBox" && qtdeChecksSelected(document.getElementsByName(formulario)[0], nameCampoSemIdentificador) != 0)
				validacao = true;
			else if(campoClass.substring(0, 17) == "mascaraMesAnoData" && campo.value != valueDataMesAno)
				validacao = true;
			else if(campoClass.substring(0, 11) == "mascaraData" && campo.value != valueData)
				validacao = true;
			else if(campoClass == "mascaraCnpj" && campo.value != valueCnpj)
				validacao = true;
			else if(campoClass == "mascaraCpf" && campo.value != valueCpf)
				validacao = true;
			else if(campoClass == "mascaraCep" && campo.value != valueCep)
				validacao = true;
			else if(campoClass == "mascaraHora" && campo.value != valueHora)
				validacao = true;
			else if(campoClass == "pontuacao" && campo.value != '')
				validacao = true;
			else if((campoClass != "listCheckBox" && campoClass != "mascaraHora" && campoClass != "mascaraCnpj" && campoClass != "mascaraCep" && campoClass != "mascaraCpf" && campoClass.substring(0, 11) != "mascaraData" && campoClass.substring(0, 17) != "mascaraMesAnoData" && campo.value.trim() != "" && campo.value.trim() != "-1") || (campo.type == "select-multiple" && campo.length > 0))
				validacao = true;

			if(validacao)
			{
				campo.style.background = "#FFF"; // Cor do campo validado que
													// foi preenchido(depois de
													// corrigido)
			}
			else
			{
				if(campoFocus == "")
					campoFocus = campo;
				//CUIDADO com a cor, utilizo o RGB
				campo.style.background = "#FFEEC2"; // UTILIZO O RGB DESSA COR rgb(255, 238, 194) Cor do campo validado que
													// foi deixado em branco.
				erro += 1;
			}
		}
	}

	if(erro > 0)
	{
		if(exibeLabelDosCamposNaoPreenchidos)
		{
			desmarcarAbas();
			var camposNaoPreenchidos = [];
			
			//CUIDADO pegando a cor pelo rgb do #FFEEC2
			$("input:text,textarea,select,.listCheckBox").each(function() {
				var labelInput = $(this);
				if(labelInput.css('background-color') == "rgb(255, 238, 194)" || labelInput.css('background-color') == "#ffeec2") {
					//var campoId = labelInput.attr('i').find('label').text().match(/(.+):/);
					var campoId = labelInput.attr('id');
					
					if (labelInput.hasClass('listCheckBox'))
						var label =	$("label[for='" + campoId.replace('listCheckBox','') + "']");
					else
						var label =	$("label[for='" + campoId + "']");
					
					if (label)
						camposNaoPreenchidos.push(label.text().replace(':','').replace('*','').replace('\n','').trim());
					
					marcarAbas('#' + campoId);
				}
			});
			
			jAlert("Preencha os campos indicados:\n" + camposNaoPreenchidos.join(", "));		
		}
		else
			jAlert("Preencha os campos indicados.");
		
		campoFocus.focus();
		return false;
	}
	else
		return true;
}

function limpaMascaras(campos)
{
	if(campos != null)
	{
		var qtdElementos = campos.length;
		var campo;
		var valueDataMesAno = "  /    ";
		var valueData = "  /  /    ";

		for(var contador = 0; contador < qtdElementos; contador++)
		{
			campo = document.getElementById(campos[contador]);
			if(campo)
			{
				campoClass = campo.className;
	
				if(campoClass.substring(0, 17) == "mascaraMesAnoData" && campo.value == valueDataMesAno)
					campo.value = limpaCamposMascaraDataMesAno(campo.value);
				else if(campoClass.substring(0, 11) == "mascaraData" && campo.value == valueData)
					campo.value = limpaCamposMascaraData(campo.value);
				if(campoClass == "mascaraCnpj")
					campo.value = limpaCamposMascaraCnpj(campo.value);
				else if(campoClass == "mascaraCpf")
					campo.value = limpaCamposMascaraCpf(campo.value);
				else if(campoClass == "mascaraCep")
					campo.value = limpaCamposMascaraCep(campo.value);
			}
		}
	}
}

// Limpa todos os campos e coloca máscaras vazias nos campos de data.
function resetFormulario(campos)
{
	if(campos != null)
	{
		var qtdElementos = campos.length;
		var campo;
		var valueDataMesAno = "  /    ";
		var valueData = "  /  /    ";

		for(var contador = 0; contador < qtdElementos; contador++)
		{
			campo = document.getElementById(campos[contador]);
			campo.style.background="#FFF";
			campoClass = campo.className;

			if(campoClass.substring(0, 17) == "mascaraMesAnoData")
				campo.value = valueDataMesAno;
			else if(campoClass.substring(0, 11) == "mascaraData")
				campo.value = valueData;
			else
				campo.value = "";
		}
	}
}

function validaFormulario(formulario, camposObrigatorios, camposValidos, noSubmit, urlImg)
{
	var validacao = true;

	if(camposObrigatorios != null && camposObrigatorios.length > 0)
		validacao = validaCamposObrigatorios(camposObrigatorios, formulario);

	if(validacao && camposValidos != null && camposValidos.length > 0)
		validacao = validaCampos(camposValidos);

	if(validacao)
	{
		if(urlImg)
			processando(urlImg);
		
		limpaMascaras(camposValidos);
		if(!noSubmit)
		{
			var form0 = document.getElementsByName(formulario)[0];
			form0.submit();
		}
		return true;
	}

	return false;
}


function validaDate(campo)
{
	var expReg = /^((0[1-9]|[12]\d)\/(0[1-9]|1[0-2])|30\/(0[13-9]|1[0-2])|31\/(0[13578]|1[02]))\/(19|20)?\d{2}$/;
 	var aRet = true;

	if ((campo) && (campo.value.match(expReg)) && (campo.value != ''))
	{
		var dia = campo.value.substring(0,2);
		var mes = campo.value.substring(3,5);
		var ano = campo.value.substring(6,10);

		if ((mes == 4 || mes == 6 || mes == 9 || mes == 11 ) && dia > 30)
			aRet = false;
		else if ((ano % 4) != 0 && mes == 2 && dia > 28)
			aRet = false;
		else if ((ano%4) == 0 && mes == 2 && dia > 29)
			aRet = false;
	}
	else
		aRet = false;

	return aRet;
}

function validaDateMesAno(campo)
{
	var expRegDataMesAno = /^(0?[1-9]|1[0-2])\/(19|20)?\d{2}$/;
	return campo.value.match(expRegDataMesAno);
}

function validaCurrency(campo)
{
	var campoValue = campo.replace(/\./g, "");
	var expCurrency = /^\d*(?:,\d{0,2})?$/;
	return campoValue.match(expCurrency);
}

function replaceAll(string, token, newtoken)
{
	while (string.indexOf(token) != -1) {
		string = string.replace(token, newtoken);
	}
	return string;
}

function validaCpfCnpj(numero)
{
	id = numero.id;
	numero = replaceAll(numero.value,".","");
	numero = replaceAll(numero,"-","");
	numero = replaceAll(numero,"/","");
	numero = numero.trim();

	// Rotina para CPF
	if (numero.length == 11 )
	{
		cpf = numero;
		erro = new String;
		if (cpf.length < 11) erro += "Sao necessarios 11 digitos para verificacao do CPF. \n\n";
			var nonNumbers = /\D/;
			if (nonNumbers.test(cpf)) erro += "A verificacao de CPF suporta apenas numeros. \n\n";
			if (cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999" || cpf == "00000000000"){
				erro += "Campo CPF Inválido.";
			}
			var a = [];
			var b = new Number;
			var c = 11;
			for (i=0; i<11; i++){
				a[i] = cpf.charAt(i);
					if (i < 9) b += (a[i] * --c);
			}
			if ((x = b % 11) < 2) { a[9] = 0; } else { a[9] = 11-x; }
				b = 0;
				c = 11;
				for (y=0; y<10; y++) b += (a[y] * c--);
					if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }
					if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])){
				erro +="Digito verificador com problema.";
			}
			if (erro.length > 0){
				return false;
			}
			return true;
		}
	   // Rotina para CNPJ
       else if (numero.length == 14)
       {
		CNPJ = numero;
		erro = new String;

		// substituir os caracteres que nao sao numeros
		if(document.layers && parseInt(navigator.appVersion) == 4)
		{
			x = CNPJ.substring(0,2);
			x += CNPJ.substring(3,6);
			x += CNPJ.substring(7,10);
			x += CNPJ.substring(11,15);
			x += CNPJ.substring(16,18);
			CNPJ = x;
		}
		else
		{
			CNPJ = CNPJ.replace(".","");
			CNPJ = CNPJ.replace(".","");
			CNPJ = CNPJ.replace("-","");
			CNPJ = CNPJ.replace("/","");
		}
		var nonNumbers = /\D/;
		if (nonNumbers.test(CNPJ)) erro += "A verificacao de CNPJ suporta apenas numeros. \n\n";
		var a = [];
		var b = new Number;
		var c = [6,5,4,3,2,9,8,7,6,5,4,3,2];
		for (i=0; i<12; i++){
			a[i] = CNPJ.charAt(i);
			b += a[i] * c[i+1];
		}
		if ((x = b % 11) < 2) { a[12] = 0; } else { a[12] = 11-x; }
		b = 0;
		for (y=0; y<13; y++)
		{
			b += (a[y] * c[y]);
		}
		if ((x = b % 11) < 2)
		{
			a[13] = 0;
	 	}
		else
		{
			a[13] = 11-x;
		}
		if ((CNPJ.charAt(12) != a[12]) || (CNPJ.charAt(13) != a[13]))
		{
			erro +="Digito verificador com problema.";
		}
		if (erro.length > 0)
		{
			if(exibeLabelDosCamposNaoPreenchidos)
				marcarAbas('#' + id);
			
			return false;
		}
		else
		{
			return true;
		}

	}
	else
	{
		if(exibeLabelDosCamposNaoPreenchidos)
			marcarAbas('#' + id);
		
  	 	return false;
	}
}

function ativaOuDesativa(elementId)
{
	var element = document.getElementById(elementId);
	if (element.disabled)
	{
		element.disabled = false;
		return;
	}
	else
	{
		element.disabled = true;
		element.value = "";
		return;
	}
	element.focus();
}
function habilitaCampo(libera, campoId)
{
	var campo = document.getElementById(campoId);
	if(libera == "true")
	{
		campo.disabled = false;
		campo.style.background="#FFF";
	}
	else
	{
		campo.disabled = true;
		campo.style.background="#DEDEDE";
	}
}

function somenteLeitura(libera, campoId)
{
	var campo = document.getElementById(campoId);
	if(libera)
	{
		campo.readOnly = false;
		campo.style.background="#FFF";
	}
	else
	{
		campo.readOnly = true;
		campo.style.background="#DEDEDE";
	}
}

function exibeFiltro(urlImg, filtroId)
{
	var filtro = document.getElementById(filtroId);
	var linkFiltro = document.getElementById("linkFiltro");
	var labelLink = document.getElementById("labelLink");
	var showFilter ='true';

	if(filtro.style.display == "none" || labelLink.innerHTML == "Exibir Filtro")
	{
		filtro.style.display = "block";
		linkFiltro.innerHTML = '<img alt="Ocultar\Exibir Filtro" src="'+urlImg+'/arrow_up.gif"/>  <span id="labelLink" class="labelLink">Ocultar Filtro</span></a>';
		jQuery('#showFilter').val(true);
	}
	else
	{
		filtro.style.display = "none";
		linkFiltro.innerHTML = '<img alt="Ocultar\Exibir Filtro" src="'+urlImg+'/arrow_down.gif"/>  <span id="labelLink" class="labelLink">Exibir Filtro</span></a>';
		jQuery('#showFilter').val(false);
		showFilter = 'false';
	}
	jQuery.ajax({url: 'updateFilter.action?showFilter=' + showFilter});	
	
}

function exibeDiv(divId, linkId, texto)
{
	var filtro = document.getElementById(divId);
	var linkFiltro = document.getElementById(linkId);

	if(filtro.style.display == "none")
	{
		filtro.style.display = "block";
		linkFiltro.innerHTML = 'Ocultar ' + texto + '</a>';
	}
	else
	{
		filtro.style.display = "none";
		linkFiltro.innerHTML = 'Exibir ' + texto + '</a>';
	}
}

function validaPIS(pis)
{
	var ftap="3298765432";
	var total=0;
	var i;
	var resto=0;
	var numPIS=0;
	var strResto="";

	total=0;
	resto=0;
	numPIS=0;
	strResto="";

	numPIS = pis.value;
	for(i=0;i<=9;i++)
	{
		resultado = (numPIS.slice(i,i+1))*(ftap.slice(i,i+1));
		total=total+resultado;
	}

	resto = (total % 11);

	if (resto != 0)
		resto=11-resto;

	if (resto==10 || resto==11)
	{
		strResto=resto+"";
		resto = strResto.slice(1,2);
	}

	if (resto!=(numPIS.slice(10,11))){
		if(exibeLabelDosCamposNaoPreenchidos)
			marcarAbas('#' + pis.id);
		
		return false;
	}

	return true;
}

function validaHora(hora)
{
	var times = hora.split(":");

	if(times[0] > 23)
	    return false;
	if(times[1] > 59)
	    return false;

	return true;
}

function validaEmail(email, id)
{
	var retorno = true;
	var ultimaPos = email.length-1;
	var posicaoAt = email.indexOf('@');
	var posicaoDot = email.lastIndexOf('.');
	
	
	// tem espaço
	if (email.indexOf(" ") != -1)
		retorno = false;
	
	// nao tem @ ou . ou tem @. juntos
	if (posicaoAt < 1 || posicaoDot < 1 || posicaoDot-posicaoAt < 2)
		retorno = false;
	
	// @ ou . no final
	if (posicaoAt == ultimaPos || posicaoDot == ultimaPos)
		retorno = false;
	
	// mais de um @
	if (posicaoAt != email.lastIndexOf('@'))
		retorno = false;

	if(!retorno && exibeLabelDosCamposNaoPreenchidos)
		marcarAbas('#' + id);
	
	return retorno;
}

function apresentaMsg(data) { jAlert(data); }

function GetWidth() {
    var x = 0;
    if (self.innerHeight) {
            x = self.innerWidth;
    } else if (document.documentElement && document.documentElement.clientHeight) {
            x = document.documentElement.clientWidth;
    } else if (document.body) {
            x = document.body.clientWidth;
    }
    return x;
}

function atualizaChecks(classCheck, valor)
{
	jQuery('.' + classCheck).each(function () {
		this.checked = valor;	    
	});
}

// Para a validação de campos currency
jQuery.fn.insertAtCaret = function (tagName) {
    return this.each(function(){
      if (document.selection) {
        // IE support
        this.focus();
        sel = document.selection.createRange();
        sel.text = tagName;
      }else if (this.selectionStart || this.selectionStart == '0') {
        this.focus();
        // MOZILLA/NETSCAPE support
        startPos = this.selectionStart;
        endPos = this.selectionEnd;
        scrollTop = this.scrollTop;
        this.value = this.value.substring(0, startPos) + tagName + this.value.substring(endPos,this.value.length);
        this.focus();
        this.selectionStart = startPos + tagName.length;
        this.selectionEnd = startPos + tagName.length;
        this.scrollTop = scrollTop;
      } else {
        this.value += tagName;
        this.focus();
      }
    });
  };

  jQuery(function() {
  	$('.moeda').live('focus', function() {
		$(this).priceFormat();
	});
  	
  	$('.hora').live('focus', function() {
			$(this).priceFormat({
		    	prefix: '',
		    	centsSeparator: ':',
		    	thousandsSeparator: ''
			});
	});
  	
	jQuery(".currency").each(function(){
		if(jQuery(this).val() != "")
		{
			jQuery(this).val(jQuery(this).val().replace(/\./g, ""));
			jQuery(this).format({format:"#,###.00", locale:"br"});
		}
    });
	  
    jQuery(".currency").blur(function(){
    	var val = jQuery(this).val();
    	if (val != "")
    	{
    		jQuery(this).format({format:"#,###.00", locale:"br"});
    		if (!parseInt(jQuery(this).val()))
    			jQuery(this).val(val);
    	}
    });

    jQuery(".currency").keypress(function(event) {
      var charI = String.fromCharCode(event.which);

      if (charI.match(/[0-9]/) || !(event.keyCode == 0)) 
        return;

      event.preventDefault();
      
      if (charI.match(/[\.,]/) && (jQuery(this).val().indexOf(',') < 0))
        jQuery(this).insertAtCaret(',');
    });

    jQuery(".currency").focus(function() {
         jQuery(this).val(jQuery(this).val().replace(/\./g, ""));
    });
    
  });

// VALIDA DATA POSTERIOR A OUTRA
 function validaDatasPeriodo(dataIni, dataFim) 
 {
	if(dataIni != "" && dataFim != "")
	{
		dt1 = dataIni.split( "/" )[2].toString() + dataIni.split( "/" )[1].toString() + dataIni.split( "/" )[0].toString();
		dt2 = dataFim.split( "/" )[2].toString() + dataFim.split( "/" )[1].toString() + dataFim.split( "/" )[0].toString();


		if( parseInt(dt2) >= parseInt(dt1) )
			return true;
		else
			return false;
	}
	else
	{
		return false;
	}
}
  
// VALIDAÇÃO DE FORMS COM PERÍODOS (2 CAMPOS DATA)
// LEMBRAR DE ADICIONAR AS CLASSES validaDataIni E validaDataFim NOS CAMPOS.
  function validaFormularioEPeriodo(formulario, camposObrigatorios, camposValidos, noSubmit) 
  {
	var data1 = jQuery(".validaDataFim").val();
	var data2 = jQuery(".validaDataIni").val();
	
	if(data2 && data1)
	{
		dt2 = data2.split( "/" )[2].toString() + data2.split( "/" )[1].toString() + data2.split( "/" )[0].toString();
		dt1 = data1.split( "/" )[2].toString() + data1.split( "/" )[1].toString() + data1.split( "/" )[0].toString();


		if( parseInt(dt2) > parseInt(dt1) )
		{
			jAlert("Data final inferior a data inicial.");
			return false;
		}
		else
		{
			return validaFormulario(formulario, camposObrigatorios, camposValidos, noSubmit);
		}
	}
	else
	{
		return validaFormulario(formulario, camposObrigatorios, camposValidos, noSubmit);
	}
}
// VALIDAÇÃO DE FORMS COM PERÍODOS (2 CAMPOS DATA) SOMENTE MES E ANO
// LEMBRAR DE ADICIONAR AS CLASSES validaDataIni E validaDataFim NOS CAMPOS.
 function validaFormularioEPeriodoMesAno(formulario, camposObrigatorios, camposValidos, noSubmit) 
 {
	var data1 = jQuery(".validaDataFim").val();
	var data2 = jQuery(".validaDataIni").val();

	if(data2 != "" && data1 != "")
	{
		dt2 = data2.split( "/" )[1].toString() + data2.split( "/" )[0].toString();
		dt1 = data1.split( "/" )[1].toString() + data1.split( "/" )[0].toString();


		if( parseInt(dt2) > parseInt(dt1) )
		{
			jAlert("Data final inferior a data inicial.");
			return false;
		}
		else
		{
			return validaFormulario(formulario, camposObrigatorios, camposValidos, noSubmit);
		}
	}
	else
	{
		return validaFormulario(formulario, camposObrigatorios, camposValidos, noSubmit);
	}
}

function validaPercentual()
{
	var valorPercentual = document.form.percentualMinimoFrequencia.value;
	
	if(valorPercentual < 0 || valorPercentual > 100)
		jAlert("Percentual Informado Inválido"); 
	if(valorPercentual == null || valorPercentual == 0 )
		valorPercentual = 70;	
}
 
function verificaCpfDuplicado(valorCpf, empresaId, isModuloExterno, id, isCandidato)
{
	$("#msgCPFDuplicado").dialog('close');
	
	if(valorCpf.length == 14)
	{
		DWRUtil.useLoadingMessage('Carregando...');
		PessoaDWR.verificaCpfDuplicado(valorCpf, empresaId, id, function(pessoas) {
			var msg = "";
			var link = "";

			$.each(pessoas, function(i, pessoa) {
				
				if (pessoa.tipoPessoa.chave == 'A')
				{
					if (isModuloExterno)
					{
						msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" href=\"prepareRecuperaSenha.action?empresaId=" + empresaId + "&cpf=" + valorCpf+ "\">" + "Já existe um currículo com esse CPF. Clique aqui para solicitar o envio de senha para seu e-mail." + "</a><br>";
						return false;
					}
					else
					{
						link = (isCandidato ? "list.action?cpfBusca=" : "listCandidato.action?cpfBusca=") + valorCpf;
						msg += "Candidato: <br /><a href='" + link + "'>" + pessoa.nome + "</a><br /><br />";
					}
				}
				else if (pessoa.tipoPessoa.chave == 'C')
				{
					if (isModuloExterno)
					{
						msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" >" + "Entre em contato com a empresa, pois o CPF informado é de um colaborador." + "</a><br>";
						return false;
					}
					else
					{
						link = (isCandidato ? "../../geral/colaborador/list.action?cpfBusca=" : "list.action?cpfBusca=") + valorCpf;
						msg += "Colaborador: <br /><a href='" + link + "'>" + pessoa.nome + "</a><br /><br />";
					}
				}
			});

			if (msg != "")
				$("#msgCPFDuplicado").html(msg).dialog({ modal: true, title: "CPF já cadastrado" });
		});
	}
}

function processaValidacao(data, valorCpf, isModuloExterno, empresaId, isCandidato)
{
	if (data == '')
	{
		$("#msgCPFDuplicado").dialog('close');
		return false;
	}
	
	var msg = "";
	
	if (data == "candidato")
	{
		if (isCandidato)
			msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" href=\"list.action?cpfBusca=" + valorCpf+ "\">" + "Existe(m) Candidato(s) cadastrado(s) com esse CPF (clique aqui para visualizar)" + "</a><br>";
		else
			msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" href=\"listCandidato.action?cpfBusca=" + valorCpf+ "\">" + "Existe(m) Candidato(s) cadastrado(s) com esse CPF (clique aqui para visualizar)" + "</a><br>";
		
		if (isModuloExterno)
			msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" href=\"prepareRecuperaSenha.action?empresaId=" + empresaId + "&cpf=" + valorCpf+ "\">" + "Já existe um currículo com esse CPF. Clique aqui para solicitar o envio de senha para seu e-mail." + "</a><br>";
	}
	
	if (data == "colaborador")
	{
		if (isCandidato)
			msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" href=\"listColaborador.action?cpfBusca=" + valorCpf+ "\">" + "Existe(m) Colaborador(es) cadastrado(s) com esse CPF (clique aqui para visualizar)" + "</a><br>";
		else
			msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" href=\"list.action?cpfBusca=" + valorCpf+ "\">" + "Existe(m) Colaborador(es) cadastrado(s) com esse CPF (clique aqui para visualizar)" + "</a><br>";
		
		if (isModuloExterno)
			msg = "<a title=\"Ver Informação\" style=\"text-decoration: none\" >" + "Entre em contato com a empresa, pois o CPF informado é de um colaborador." + "</a><br>";
	}
	
	$("#msgCPFDuplicado").html(msg).dialog({ modal: true, title: "CPF já cadastrado" });
}

function exibeEscondeConteudo(){
	jQuery(document).ready(function(){
		jQuery("#conteudoExibeEsconde h4").click(function(){
			jQuery(this).toggleClass('fechado');
			jQuery(this).next().slideToggle(50);
		});
	});
}
var link = "";
function getCandidatosHomonimos(url)
{
	link = url;
	if(document.getElementById("nome").value.length >= 3)
	{
		DWRUtil.useLoadingMessage('Carregando...');
		CandidatoDWR.getCandidatosHomonimos(createListCandidatosHomonimos, document.getElementById("nome").value);
	}
	else
		document.getElementById("homonimos").style.display = "none";
}

function createListCandidatosHomonimos(data)
{
	var nomes = "";
	for (var prop in data)
	{
		nomes += " - <a title=\"Ver Informação\" style=\"color:red;text-decoration: none\" href=\"javascript:popup('" + link + "?palavras=&forma=&candidato.id=" + prop + "', 580, 750)\">" + data[prop] + "</a><br>";
	}

	if(nomes != "")
	{
		document.getElementById("homonimos").style.display = "";
		document.getElementById("nomesHomonimos").innerHTML = nomes;
	}
	else
		document.getElementById("homonimos").style.display = "none";
}

function addBuscaCEP(cepFieldId, logradouroFieldId, bairroFieldId, cidadeFieldId, estadoFieldId)
{
	var $cep = jQuery('#' + cepFieldId)
	
	$cep.data("oldValue", $cep.val());
	
	//Não foi possível utilizar o  evento change, pois o componente de mascara já utiliza e o indisponibiliza. 
	$cep.blur(function(){
		if (
				(jQuery(this).data("oldValue") == jQuery(this).val())
				||
				(jQuery(this).val().replace(/\s/g, "").length < 9)
			)
				return;
		
		jQuery(this).data("oldValue", jQuery(this).val());
		
		if(jQuery.trim($cep.val()) != "")
		{
			 /*
			 Para conectar no serviço e executar o json, precisamos usar a função
			 getScript do jQuery, o getScript e o dataType:"jsonp" conseguem fazer o cross-domain, os outros
			 dataTypes não possibilitam esta interação entre domínios diferentes
			 Estou chamando a url do serviço passando o parâmetro "formato=javascript" e o CEP digitado no formulário
			 http://cep.republicavirtual.com.br/web_cep.php?formato=javascript&cep="+jQuery("cep").val()
			 */
			 var $logradouro = jQuery("#" + logradouroFieldId);
			 var $bairro = jQuery("#" + bairroFieldId);
			 var $cidade = jQuery("#" + cidadeFieldId);
			 var $estado = jQuery("#" + estadoFieldId);
			 var $numero = jQuery("#num");

			 $logradouro.attr('disabled', true);
			 $bairro.attr('disabled', true);
			 $cidade.attr('disabled', true);
			 $estado.attr('disabled', true);

			 $numero.focus();
			 
			 jsonCEP = "";
			 EnderecoDWR.buscaPorCep($cep.val(), {timeout:10000, callback: function(data) {
				 
				 jsonCEP = jQuery.parseJSON(data);
				 				 
				 if (jsonCEP.sucesso == 1)
				 {
			 		 $logradouro.val(jsonCEP.logradouro);
					 
					jQuery("#uf option").each(function() {
						if (jQuery(this).text() == jsonCEP.estado)
						{
							jQuery(this).attr("selected", true);
							return;
						}
					});
					 $estado.change();
					 $bairro.val(jsonCEP.bairro);
				 }
				 else if (jsonCEP.sucesso == 2)
				 {
					 jAlert("Não possivel estabelecer a conexão com o servidor remoto. Favor verificar acesso a internet."); 
					 $logradouro.val("");
					 $bairro.val("");
					 $cidade.val("");
					 $estado.val("");
					 $estado.change();
				 }
				 else
				 {
					 jAlert("Endereço não encontrado");
					 $logradouro.val("");
					 $bairro.val("");
					 $cidade.val("");
					 $estado.val("");
					 $estado.change();
				 }
				 
				 $logradouro.attr('disabled', false);
				 $bairro.attr('disabled', false);
				 $cidade.attr('disabled', false);
				 $estado.attr('disabled', false);				 
			 },
			 errorHandler: function (errorString, exception){
				 $logradouro.attr('disabled', false);				 
				 $bairro.attr('disabled', false);
				 $cidade.attr('disabled', false);
				 $estado.attr('disabled', false);				 

				 jAlert("Não possivel estabelecer a conexão com o servidor remoto. Favor verificar acesso a internet."); 
			 }});
		}
	});
}


// DWR global error handler

jQuery(function() {		
	//if (window.DWREngine != undefined)
	if(typeof(DWREngine) !== 'undefined') 
		DWREngine.setErrorHandler(function (msg, exc) {
		  jAlert(msg);
	});
});


function arrayDiff(ara1, ara2) {
   var aRes = new Array() ;
   for(var i=0;i<ara1.length;i++) {
	   	if($.inArray(ara1[i], ara2) == -1)
     	{
    	 	aRes.push(ara1[i]);
    	}
	}
	return aRes ;
}	   

function liberaCampo(checkbox) {
	if (checkbox.attr('checked'))
		checkbox.next().removeAttr('disabled').css('background-color', '#FFF');
	else
		desabilita(checkbox.next());
}

function desabilita(campo) {
	campo.attr('disabled','disabled').css('background-color', '#ECECEC');
}

function listaParentes(dados, empresaNome, submit)
{
	
	if ($(dados).size() > 0)
	{
    	var lista = "<strong>Dados do colaborador</strong>";
    	lista += "<div class='divInfoColabDestaque'>";
    	lista += montaTabelaDados($('#nome').val(), $('#nomeConjuge').val(), $('#nomePai').val(), $('#nomeMae').val(), $('#ende').val() + ' ' + $('#num').val() + ' ' + $('#complemento').val(), $('#cidade option:selected').text(), $('#uf option:selected').text(), $('#ddd').val() + ' ' + $('#fone').val() + ' ' + $('#celular').val(), empresaNome);
    	lista += "</div>";
    	
    	lista += "<strong>Possíveis parentes</strong>";
    	
    	$(dados).each(function(i, colaborador) {
    		 lista += '<div class="divInfoColab">';
    		 lista += montaTabelaDados(colaborador.nome, colaborador.conjuge, colaborador.pai, colaborador.mae, colaborador.endereco, colaborador.cidade, colaborador.uf, colaborador.fone, colaborador.empresaNome );
    		 lista += '</div>';
    	});
    	
    	botoes = [{ text: "Fechar", click: function() { $(this).dialog("close"); }}];

    	if(submit)
			botoes.push({ text: "Gravar", click: submit});
    	
    	$('#parentesDialog').html(lista)
    						.dialog({	title: 'Verificação de Parentesco',
    									modal: true,
    									width: 700,
    									height: 360,
    									buttons: botoes
									});
	}
	else if (submit)
		submit();
}

function montaTabelaDados(nome, conjuge, pai, mae, endereco, cidade, uf, fone, empresaNome)
{
	var dados = '<strong>' + nome + '</strong><br />';
	dados += "<table>";
	dados += '<tr><td>';
	dados += 'Nome do Cônjuge: ' + conjuge + '<br />';
	dados += 'Nome do Pai: ' + pai + '<br />';
	dados += 'Nome da Mãe: ' + mae + '<br />';
	dados += '</td><td>';
	dados += 'Endereço: ' + endereco + ' ' + cidade + ' ' + uf +  '<br />';
	dados += 'Fone: ' + fone +  '<br />';
	dados += 'Empresa: ' + empresaNome;
	dados += '</td></tr>';
	dados += '</table>';
	
	return dados;
}

function contraste(className)
{
	try {
		$('.' + className).each(function() {
			var rgb = /rgb\((\d+), (\d+), (\d+)\)/.exec( window.getComputedStyle(this, null).backgroundColor );
			
			var r = parseInt(rgb[1], 10), 
				g = parseInt(rgb[2], 10), 
				b = parseInt(rgb[3], 10);
			
			var brilho = (r*299 + g*587 + b*114) / 1000;
		    
		    $(this).css('color', (brilho > 125) ? '#333' : '#FFF');
		});
	} catch(e) {}
}

function marcarAbas(seletor) {
	$(seletor).parents("div[id^='content']").each(function() { 
		var aba = $(this).attr('id').replace('content','aba');
		$('#' + aba + ' a' ).css('color','red'); 
	});
}

function desmarcarAbas() {
	$("div[id^='aba'] a").css('color','inherit');
}

function removerAcento(texto){
	texto = texto.replace(new RegExp('[ÁÀÂÃ]','gi'), 'A');
	texto = texto.replace(new RegExp('[ÉÈÊ]','gi'), 'E');
	texto = texto.replace(new RegExp('[ÍÌÎ]','gi'), 'I');
	texto = texto.replace(new RegExp('[ÓÒÔÕ]','gi'), 'O');
	texto = texto.replace(new RegExp('[ÚÙÛ]','gi'), 'U');
	texto = texto.replace(new RegExp('[Ç]','gi'), 'C');
	texto = texto.toUpperCase();
	return texto;
}

function numDias(mes,ano) {
    if((mes<8 && mes%2==1) || (mes>7 && mes%2==0)) return 31;
    if(mes!=2) return 30;
    if(ano%4==0) return 29;
    return 28;
}

function somaDias(data, dias) {
   data=data.split('/');
   diafuturo=parseInt(data[0])+parseInt(dias);
   mes=parseInt(data[1]);
   ano=parseInt(data[2]);
   
   while(diafuturo > numDias(mes,ano)) {
       diafuturo -= numDias(mes,ano);
       mes++;
       if(mes>12) {
           mes=1;
           ano++;
       }
   }

   if(diafuturo<10) diafuturo='0'+diafuturo;
   if(mes<10) mes='0'+mes;

   return diafuturo+"/"+mes+"/"+ano;
}

function naoInseririrCharacterComValor(e,value)
{
    if (document.all) 
		var evt = event.keyCode;
    else 
		var evt = e.charCode;
	
	if(value.indexOf(String.fromCharCode(evt)) == 0)
		return false;

	return true;
}

function checkListBoxSearch(name) 
{
	nameReplaced=name.replace("[", "\\[").replace("]","\\]");
	var filtroTexto = $('#listCheckBoxFilter' + nameReplaced).val();
	var filtroAtivo = $('#listCheckBoxActive' + nameReplaced).val();
	var nomeTeste;
	
	$("input:checkbox[name='" + name + "']").not(":disabled").each(function() {
		nomeTeste = removerAcento( $( this ).parent( 'label' ).text().toUpperCase() );
		$( this ).parent().toggle( ( typeof filtroTexto == "undefined" || nomeTeste.indexOf(removerAcento(filtroTexto.toUpperCase())) > -1)
				&& ( typeof filtroAtivo == "undefined" || nomeTeste.indexOf( filtroAtivo ) > -1 ));
	});
}

var processandoTime = "";
function processando(urlImg){
	processandoTime = getCookieProcessando();
	
	var background = document.createElement('div');
	background.style.cssText = 'position: fixed; z-index: 4000; background-color: black; opacity: 0.1; bottom: 0; right: 0; top: 0; left: 0;'
	background.setAttribute("class", "processando");
		
	var img = document.createElement('img');
	img.setAttribute("class", "processando");
	var div = document.createElement('div');
	div.setAttribute("class", "processando");
	img.src = urlImg + '/loadingGeral.gif';
	div.innerHTML = "<h3 style='z-index: 5000;'>Processando...</h3><br />";
	div.style.cssText = 'position: fixed; top: 30%; left: 40%; z-index: 5000; width: 300px; text-align: center;';
	div.appendChild(img);
	
	document.body.appendChild(div);
	document.body.appendChild(background);
	
	setTimeout("checkProcessandoTime()",500);
}

function checkProcessandoTime(){
	if(processandoTime != getCookieProcessando())
		$('.processando').remove();
	else
		setTimeout("checkProcessandoTime()",500);
}

function getCookieProcessando() {
    var nameEQ = "processando=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
		while (c.charAt(0)==' ') 
			c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) 
			return c.substring(nameEQ.length,c.length);
    }
    return
}

(function($) {
    $.fn.toggleDisabled = function(disabled){
        return this.each(function(){
        	if (typeof disabled == "undefined")
        		this.disabled = !this.disabled;
        	else
        		this.disabled = disabled;
        });
    };
})(jQuery);