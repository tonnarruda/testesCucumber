<html>
<head>
<@ww.head/>

<#if bairro.id?exists>
	<title>Editar Bairro</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Bairro</title>
	<#assign formAction="insert.action"/>
</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('cidade','bairroNome'), null)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');

		.yui-ac-container, .yui-ac-content, .yui-ac-shadow, .yui-ac-content ul{
			width: 400px;
		}
	</style>
	
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>


</head>

<body>
	<@ww.actionerror />

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Estado" name="estado.id" id="uf" list="estados" listKey="id" listValue="nome" liClass="liLeft" cssStyle="width: 150px;" headerKey="" headerValue="Selecione..." />
		<@ww.select label="Cidade" name="bairro.cidade.id" id="cidade" list="cidades" required="true" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue="" />

		<@ww.textfield label="Nome" name="bairro.nome" id="bairroNome" cssClass="inputNome" maxLength="85" required="true"/>
		<@ww.div id="bairroContainer"/>

		<@ww.hidden name="bairro.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar">
		</button>
	</div>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
</body>
</html>