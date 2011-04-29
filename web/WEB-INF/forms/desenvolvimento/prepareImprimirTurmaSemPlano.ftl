<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Colaboradores Sem Indicação de Treinamento</title>
	<#assign validarCampos="return validaFormulario('form', new Array('meses'), null)"/>
</head>
<body>
<@ww.actionmessage />
<@ww.form name="form" action="imprimirRelatorioTreinamentosSemPlano.action" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.textfield id="meses" label="Não participam de treinamentos há mais de" name="meses" required="true" onkeypress = "return(somenteNumeros(event,''));" maxLength="3" after="meses" cssStyle="width:30px; text-align:right;" />

	<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList"/>
	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>

</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};"  class="btnRelatorio" accesskey="I">
	</button>
</div>
</body>
</html>