<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />

<html>
<head>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/candidato.css?version=${versao}"/>" media="screen" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>" media="screen" type="text/css">
<@ww.head/>
	<title>Currículo Escaneado - ${candidato.nome}</title>
	<script>
		function abas(value, direcao)
		{
			var link = 0;

			var qtdAbas = 2; //Quantidade de abas existentes.

			if (value == -1)
			{
				if (direcao == 'A')
				{
					link = 	Number(document.getElementById('aba').value) * 1 + 1;
					document.getElementById('aba').value = link;
				}
				else
				{
					link = 	document.getElementById('aba').value * 1 - 1;
					document.getElementById('aba').value = link;
				}
			}
			else
			{
				document.getElementById('aba').value = value;
				link = value;
			}

			for(i = 1; i <= qtdAbas; i++)
			{
				document.getElementById('content' + i).style.display	= link == i ? "block"				: "none";
				document.getElementById('aba' + i).style.background		= link == i ? "#E9E9E9"				: "#D5D5D5";
				document.getElementById('aba' + i).style.borderBottom	= link == i ? "1px solid #E9E9E9"	: "1px solid #000";
			}
		}
		function popUp(caminho)
		{
			path = caminho;
			window.open(caminho,'Currículo','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,menubar=no,left=10,top=10')
		}
	</script>

</head>
<body>
	<@ww.actionerror />

	<div id="abas">
		<div id="aba1"><a href="javascript: abas(1, '')">Curriculo Digital</a></div>
		<div id="aba2"><a href="javascript: abas(2, '')">Texto digitalizado</a></div>
	</div>

	<div id="content1" style="display: display;">
		<#list candidato.candidatoCurriculos as candidatoCurriculos>
			<a href="javascript:popUp('<@ww.url includeParams="none" value="../candidato/showCurriculo.action?candidato.id=${candidato.id}&nomeImg=${candidatoCurriculos.curriculo}"/>')"><img border="0"  heigth="230" width="230"  src="<@ww.url includeParams="none" value="../candidato/showCurriculo.action?candidato.id=${candidato.id}&nomeImg=${candidatoCurriculos.curriculo}"/>" title="Clique para ampliar"></a>
		</#list>
	</div>

	<div id="content2" style="display: none;">
		<@ww.form name="form" action="updateCurriculo.action" validate="true" method="POST">
			<@ww.hidden name="candidato.id"/>
			<@ww.textarea label="Currículo" name="candidato.ocrTexto" cssStyle="width: 720px;height: 500px"/>
			<div class="buttonGroup">
				<button onclick="document.form.submit();" class="btnGravar" style="background-color:#E9E9E9;">
				</button>
			</div>
		</@ww.form>
	</div>

		<#-- Campo para controle das abas -->
		<@ww.hidden id="aba" name="aba" value="1"/>

	<div class="buttonGroup">
		<button onclick="executeLink('prepareInsertCurriculoPlus.action?candidato.id=${candidato.id}');" class="btnInserirEditar"></button>		
		<button onclick="window.location='list.action'" class="btnConcluir"></button>
	</div>

</body>
</html>