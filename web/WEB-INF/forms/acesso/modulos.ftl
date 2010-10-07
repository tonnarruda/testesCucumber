<html>
<head>
<@ww.head/>
	<title>Acesso aos Módulos</title>
	
	<script src='<@ww.url includeParams="none" value="/js/arvoreCheck.js"/>'></script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="updateModulos.action" method="POST">
	
		<div>Permissões</div>
		<div class="listaOpcoes">
			<ul>
				${exibirPerfil}
			</ul>
		</div>
	
	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnGravar">
		</button>
	</div>
</body>
</html>