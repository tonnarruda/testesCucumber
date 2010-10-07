<html>
<head>
<@ww.head/>
<#if pesquisa.tipoAvaliacao == 0>
	<title>Inserir Colaboradores na Pesquisa</title>
<#elseif pesquisa.tipoAvaliacao == 1>
	<title>Inserir Colaboradores na Avaliação</title>
</#if>
<#assign formAction="filtroArea.action"/>

<script type='text/javascript'>
	function mostraEscondePercentual()
	{
		if(!document.getElementById('selecaoAleatoria').checked)
		{
			document.getElementById('porcentagem').disabled=true;
			document.getElementById('aplicarPartes').disabled=true;
		}
		else
		{
			document.getElementById('porcentagem').disabled=false;
			document.getElementById('aplicarPartes').disabled=false;
		}
	}

	function mudaAction()
	{
		filtro = document.getElementById('filtro').value;

		if(filtro == '1')
			document.form.action = "filtroArea.action";
		else
			document.form.action = "filtroGrupo.action";

		document.form.submit();
	}
</script>
</head>

<body>
<b>${pesquisa.titulo}<br>
<#if pesquisa.dataInicio?exists && pesquisa.dataFim?exists>
${pesquisa.dataInicio?string("dd'/'MM'/'yyyy")} a ${pesquisa.dataFim?string("dd'/'MM'/'yyyy")}</b>
</#if>
<br>
<br>

	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
	   	<@ww.checkbox label="Amostra Aleatória" name="selecaoAleatoria" id="selecaoAleatoria" labelPosition="left" onclick="mostraEscondePercentual()"/>
	   	<br>
		<@ww.textfield label="%" name="porcentagem" onkeypress="return(somenteNumeros(event,''));" cssStyle="text-align: right;" id="porcentagem" maxLength="3" size="3" />
		<br>
		<@ww.checkbox label="Aplicar percentual às partes" name="aplicarPartes" id="aplicarPartes" labelPosition="left"/>
		<br>
		<br>
		<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" id="filtro" list="filtros" />
		<@ww.hidden name="pesquisa.id" />
	</@ww.form>
	<br>


	<div class="buttonGroup">
	<button onclick="mudaAction()" class="btnAvancar" accesskey="A">
	</button>
	<button onclick="window.location='list.action?pesquisa.id=${pesquisa.id}'" class="btnVoltar" accesskey="V">
	</button>

	</div>
		<script language='javascript'>
				window.load = mostraEscondePercentual();
		</script>
</body>
</html>