<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Matriz de Qualificação</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('data','@estabelecimentosCheck','@areasCheck'), new Array('data'));"/>
</head>

<body>
<@ww.actionmessage />
<@ww.actionerror />
<@ww.form name="form" action="imprimirMatriz.action" onsubmit="${validarCampos}"  validate="true" method="POST">

	<@ww.datepicker label="A partir de" required="true" name="data" id="data" cssClass="mascaraData"/>

	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" />
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnRelatorio" accesskey="I">
	</button>
</div>
</body>
</html>