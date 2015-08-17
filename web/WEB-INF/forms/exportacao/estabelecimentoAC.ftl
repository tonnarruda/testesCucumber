<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Exportar dados para o Fortes Pessoal</title>
	
	<style>
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		ol { padding: 10px; margin-bottom: 20px; background-color: #eee; list-style: decimal inside none; }
	</style>
	
	<script>
		function enviar()
		{
			$('#form').submit();
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" id="form" action="exportarEstabelecimentoAC.action"  onsubmit="enviar()" method="POST">
	
		<ol>
			<li>Crie esses estabelecimentos no Fortes Pessoal;</li>
			<li>Marque a integarção com o RH no Fortes Pessoal;</li>
			<li>Informe na tabela abaixo os códigos gerados pelo Fortes Pessoal para os respectivos estabelecimentos;</li>
			<li><strong>Atenção!</strong> Informar códigos incorretos causará inconsistências irreversíveis no banco de dados do Fortes Pessoal.</li>
		</ol>
		
		<@ww.hidden name="empresaId"/>
		<@ww.hidden name="grupoAC"/>
		
		<@display.table name="estabelecimentos" id="estabelecimento" class="dados">
			<@display.column title="Código AC" style="text-align:center; width:80px;">
				<#assign i = estabelecimento_rowNum - 1/>
				<@ww.textfield name="codigosACs[${i}]" id="codigoAC_${i}" required="true" cssStyle="width: 35px; border: 1px solid #BEBEBE;" maxLength="4" onkeypress="return somenteNumeros(event,'');"/>
			</@display.column>
			<@display.column property="nome" title="Nome do Estabelecimento"/>
		</@display.table>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="enviar()" class="btnAvancar"></button>
	</div>
</body>
</html>