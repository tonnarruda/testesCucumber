<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Incluir candidato "${candidato.nome}" em uma solicitação em aberto</title>
<#assign validarCampos="return validaFormulario('form', new Array('@solicitacaosCheckIds'), null)"/>
</head>
<body>

	<@ww.form name="form" action="gravarSolicitacoesCandidato.action" onsubmit="${validarCampos}"  validate="true" method="POST">
		<@ww.hidden name="candidato.id"/>
		<@frt.checkListBox label="Solicitações disponíveis" name="solicitacaosCheckIds" id="solicitacao" list="solicitacaosCheck" width="600"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="${validarCampos};" accesskey="I">
		</button>
		<button onclick="window.location='../../captacao/candidato/list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>