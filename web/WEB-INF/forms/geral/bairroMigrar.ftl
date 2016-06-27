<html>
<head>
<@ww.head/>

	<title>Unir Bairros</title>

	<#assign validarCampos="return validaFormulario('form', new Array('bairro','bairroDestino'), null)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
		#wwlbl_bairro
		{
			color: #F00;
			border-bottom: 2px dotted #FFF;
		}
	</style>
	<script type="text/javascript">
		function populaCidades()
		{
			if(document.getElementById('estado').value == "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DWRUtil.removeAllOptions("cidade");
			}
			else
			{
				CidadeDWR.getCidades(createListCidades, document.getElementById("estado").value);
			}
		}

		function createListCidades(data)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			DWRUtil.removeAllOptions("cidade");
			DWRUtil.addOptions("cidade", data);
			populaBairros();
		}

		function populaBairros()
		{
			BairroDWR.getBairrosMap(createListBairros, document.getElementById("cidade").value);
		}

		function createListBairros(data)
		{
			DWRUtil.removeAllOptions("bairro");
			DWRUtil.addOptions("bairro", data);
			
			DWRUtil.removeAllOptions("bairroDestino");
			DWRUtil.addOptions("bairroDestino", data);
		}
	</script>
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="migrar.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Estado" name="estado.id" id="estado" list="estados" listKey="id" listValue="nome" liClass="liLeft" cssStyle="width: 150px;" headerKey="" headerValue="Selecione..." onchange="javascript:populaCidades()"/>
		<@ww.select label="Cidade" name="cidade.id" id="cidade" list="cidades" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue="" onchange="javascript:populaBairros()"/>

		<@ww.select label="Bairro (ATENÇÃO: este bairro será excluído)" name="bairro.id" id="bairro" list="bairros" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue="" required="true"/>
		<@ww.select label="Transferir registros para este Bairro" name="bairroDestino.id" id="bairroDestino" list="bairros" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue="" required="true"/>
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="javascript: executeLink('list.action');" class="btnVoltar"></button>
	</div>
</body>
</html>