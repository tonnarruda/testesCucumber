<html>
<head>
<@ww.head/>
	<title>Acesso aos Módulos</title>
	
	<script src='<@ww.url includeParams="none" value="/js/arvoreCheck.js?version=${versao}"/>'></script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<#if exibirPerfil?exists>
		<@ww.form name="form" action="updateModulos.action" method="POST">
			<#--//TODO remprot msgAC
			Módulos do RH configurados no AG.<br><br>
			<ul>
				<li>Recrut. e Seleção <#if modulosNaoConfigurados?contains("357")>[Não configurada]</#if></li>
				<li>Cargos e Salários <#if modulosNaoConfigurados?contains("361")>[Não configurada]</#if></li>
				<li>Pesquisa <#if modulosNaoConfigurados?contains("353")>[Não configurada]</#if></li>
				<li>Avaliação de Desempenho <#if modulosNaoConfigurados?contains("382")>[Não configurada]</#if></li>
				<li>Treina. e Desenvolvimento <#if modulosNaoConfigurados?contains("365")>[Não configurada]</#if></li>
				<li>Info. Funcionais</li>
				<li>SESMT <#if modulosNaoConfigurados?contains("75")>[Não configurada]</#if></li>
			</ul>
			-->
			<br>
			<div>Permissões</div>
			<div class="listaOpcoes">
				<ul>
					${exibirPerfil}
				</ul>
			</div>
		
		<@ww.token/>
		</@ww.form>
	</#if>

	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar">
		</button>
	</div>
</body>
</html>