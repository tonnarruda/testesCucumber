<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Colaboradores x Certificações</title>
	
	<script type="text/javascript">
		function submeterAction(action)	{
			$('form[name=form]').attr('action', action);
			return validaFormulario('form', new Array('certificacao'), null);
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="relatorioColaboradorCertificacao.action" validate="true" method="POST">
		<@ww.select name="certificacao.id" id="certificacao" list="certificacoes" listKey="id" required="true" listValue="nome" label="Certificação" headerKey="" headerValue="Selecione..."/>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return submeterAction('relatorioColaboradorCertificacao.action');" class="btnRelatorio"></button>
		<button onclick="return submeterAction('relatorioColaboradorCertificacaoXLS.action');" class="btnRelatorioExportar"></button>
	</div>
	
</body>
</html>