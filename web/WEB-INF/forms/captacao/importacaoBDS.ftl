<html>
<head>

<title>Importar Candidatos do BDS</title>
</head>
<body>

	<div class="buttonGroup">
	<@ww.actionerror />
	<@ww.form name="form" action="importacaoBDS.action" validate="true" method="POST" enctype="multipart/form-data">

		<@ww.file label="Arquivo BDS (.fortesrh)" name="arquivoBDS" id="arquivoBDS" size="50"/>
		<@ww.hidden name="solicitacao.id" />

	</@ww.form>
	<div class="buttonGroup">
		<button class="btnImportar" onclick="document.form.submit();" accesskey="I">
		</button>
		<button onclick="window.location='../candidatoSolicitacao/listTriagem.action?solicitacao.id=${solicitacao.id}'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>