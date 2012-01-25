<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Lista de Candidatos Aptos das Solicitações Abertas</title>
	<#assign validarCampos="return validaFormulario('form', new Array('@etapaCheck'), null)"/>
</head>

<body>
<@ww.actionmessage />
<@ww.form name="form" action="imprimirRelatorio.action" onsubmit="${validarCampos}"  validate="true" method="POST">
	<@frt.checkListBox name="etapaCheck" id="etapaCheck" label="Etapas" list="etapaSeletivaCheckList" />
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnRelatorio">
	</button>
</div>
</body>
</html>