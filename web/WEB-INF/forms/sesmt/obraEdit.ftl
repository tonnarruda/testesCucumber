<html>
	<head>
		<#include "../ftl/mascarasImports.ftl" />
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<@ww.head/>
		<#if obra.id?exists>
			<title>Editar Obra</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Obra</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#assign validarCampos="return validaFormulario('form', new Array('nome','ende', 'num', 'cidade'), new Array('cep'))"/>

		<style type="text/css">
			@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
		</style>
		
		<script type="text/javascript">
			$(function() {
				addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');
			});
		</script>

	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="obra.id" />
			<@ww.hidden name="obra.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Nome" name="obra.nome" id="nome" required="true" cssClass="inputNome" maxLength="100" cssStyle="width:606px;"/>
			<@ww.textfield label="Tipo da obra" name="obra.tipoObra" id="tipoObra" required="true" cssClass="inputNome" maxLength="100" cssStyle="width:606px;"/>
			<@ww.textfield label="CEP" name="obra.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft" />
			<@ww.textfield label="Logradouro" name="obra.endereco.logradouro" id="ende" required="true" cssStyle="width: 488px;" liClass="liLeft" maxLength="40"/>
			<@ww.textfield label="Nº"  name="obra.endereco.numero" id="num" required="true" cssStyle="width:40px;" maxLength="10"/>
			<@ww.select label="Estado" name="obra.endereco.uf.id" id="uf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" />
			<@ww.select label="Cidade" name="obra.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" required="true" />
			<@ww.textfield label="Bairro" name="obra.endereco.bairro" id="bairroNome" cssStyle="width: 300px;" maxLength="85"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
		</div>
		
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
	</body>
</html>
