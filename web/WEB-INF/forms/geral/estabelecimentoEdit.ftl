<html>
<head>
<@ww.head/>
<#if estabelecimento.id?exists>
	<title>Editar Estabelecimento</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign idEstabelecimento=estabelecimento.id/>
<#else>
	<title>Inserir Estabelecimento</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign idEstabelecimento="0"/>
</#if>
	<#assign idEmpresa=empresaSistema.id>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('nome','complementoCnpj','ende','num','bairroNome','uf','cidade'), new Array('cep'))"/>

	<script type='text/javascript'>
		function populaCidades()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CidadeDWR.getCidades(createListCidades, document.getElementById("uf").value);
		}

		function createListCidades(data)
		{
			DWRUtil.removeAllOptions("cidade");
			DWRUtil.addOptions("cidade", data);
		}

		var antigoComplemento = "";

		function calcularDV(complemento)
		{
			if(complemento.length == 4)
			{
				if(antigoComplemento != complemento)
					antigoComplemento = complemento;
				else
					return false;

				var cnpj = document.getElementById("cnpj").value.substr(0,8) + "" + document.getElementById("complementoCnpj").value
				DWRUtil.useLoadingMessage('Carregando...');
				EstabelecimentoDWR.calcularDV(exibirDV, cnpj, ${idEstabelecimento}, ${idEmpresa});
			}
			else
			{
				document.getElementById("dv").value = "";
				antigoComplemento = "";
			}
		}

		function exibirDV(data)
		{
			if(data == "XX")
			{
				document.getElementById("complementoCnpj").style.backgroundColor = "#FEE";
				jAlert("CNPJ já cadastrado");
			}
			else
				document.getElementById("complementoCnpj").style.backgroundColor = "#FFF";

			document.getElementById("dv").value = data;
		}
		function verificaCnpj()
		{
			if(document.getElementById('complementoCnpj').value.length != 4)
			{
				jAlert("Complemento do CNPJ deve ter 4 dígitos.");
				document.getElementById("complementoCnpj").style.backgroundColor = "#FEE";
				return false;
			}
		
			if(document.getElementById("dv").value == "XX")
			{
				jAlert("CNPJ já existe.")
				return false;
			}
			${validarCampos}
		}
		
		$(function() 
		{
			addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');			
		});
		
	</script>


	<#if !integradoAC>
		<#assign desabilita="false"/>
	<#else>
		<#assign desabilita="true"/>
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="return verificaCnpj();" validate="true" method="POST">
		<@ww.textfield label="Nome" name="estabelecimento.nome" id="nome"  required="true" cssStyle="width:525px;" maxLength="30" liClass="liLeft" disabled="${desabilita}"/>
		<@ww.textfield label="CNPJ" name="estabelecimento.empresa.cnpj" id="cnpj" size="8" liClass="liLeft" maxLength="8" disabled="true" after="/" />
		<@ww.textfield label="" name="estabelecimento.complementoCnpj" id="complementoCnpj" maxlength="4" cssStyle="width:34px;" disabled="${desabilita}" after="-" liClass="liLeft" onkeyup="javascript: calcularDV(this.value);" onkeypress="return(somenteNumeros(event,''));" />
		<@ww.textfield label="" name="digitoVerificador" id="dv" cssStyle="width:18px;" disabled="${desabilita}" disabled="true"/>

		<br>
		<@ww.textfield label="CEP" name="estabelecimento.endereco.cep" id="cep" cssClass="mascaraCep" disabled="${desabilita}" liClass="liLeft"/>
		<@ww.textfield label="Logradouro" name="estabelecimento.endereco.logradouro" id="ende" required="true" cssStyle="width: 300px;"  maxLength="40" liClass="liLeft" disabled="${desabilita}"/>
		<@ww.textfield label="Nº" name="estabelecimento.endereco.numero" id="num" required="true" cssStyle="width:40px;"  maxLength="10" liClass="liLeft" disabled="${desabilita}"/>
		<@ww.textfield label="Complemento" name="estabelecimento.endereco.complemento" cssStyle="width: 252px;" maxLength="20" disabled="${desabilita}"/>
		<@ww.select label="Estado" name="estabelecimento.endereco.uf.id" id="uf" list="ufs" required="true" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="-1" headerValue="" disabled="${desabilita}"/>
		<@ww.select label="Cidade" name="estabelecimento.endereco.cidade.id" id="cidade" required="true" list="cidades"  listKey="id" listValue="nome" cssStyle="width: 300px;" headerKey="" headerValue="" disabled="${desabilita}" liClass="liLeft"/>
		<@ww.textfield label="Bairro" name="estabelecimento.endereco.bairro" id="bairroNome" cssStyle="width: 307px;" maxLength="85" required="true"  disabled="${desabilita}"/>
		<@ww.div id="bairroContainer"/>

		<@ww.hidden name="estabelecimento.id" />
		<@ww.hidden name="estabelecimento.codigoAC" />
		<@ww.hidden name="estabelecimento.empresa.id" />
	</@ww.form>

	<div class="buttonGroup">
		<#if !integradoAC>
		<button onclick="verificaCnpj();;" class="btnGravar" accesskey="${accessKey}">
		</button>
		</#if>
		<button onclick="javascript: executeLink('list.action');" class="btnCancelar" accesskey="V">
		</button>
	</div>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
</body>
</html>