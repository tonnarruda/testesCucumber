<html>
<head>
<@ww.head/>
	<title>Erro na configuração do RH</title>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/default.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');
	</style>
	
	<!--[if IE]>
	<style type="text/css" media="screen">
		body
		{
			behavior: url(<@ww.url includeParams="none" value="/css/csshover2.htc"/>);
		}
		.smenuDiv
		{
			width: 100%;
		}
		#mainDiv
		{
		
		}
		
		.liLeft
		{
			margin-right:0px;
		}
		
		span.botao
		{
			padding:2px 0 1px 0;
		}
		fieldset
		{
			padding: 10px;
		}
		
	</style>
	<![endif]-->
		
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<div id="waDiv">
		<br>
		<div class="waDivFormulario">
			<@ww.form name="form" action="updateConf.action" method="POST">
			<@ww.password label="Senha do Banco de Dados" name="senhaBD" cssClass="inputNome"/>

			<button onclick="document.form.submit();" class="btnGravar" ></button>
			<br/><br/>
			
			<#if versao?exists>
				Conexão efetuada com sucesso, versão do Banco de Dados: ${versao}
			</#if>			
		</@ww.form>
	
				
		</div>
		<br><br>
	</div>
</body>

</html>