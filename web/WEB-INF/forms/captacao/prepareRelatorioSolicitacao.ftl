<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Lista de Candidatos da Seleção</title>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>
</head>

<body>
<@ww.actionmessage />
<@ww.form name="form" action="imprimirRelatorio.action" onsubmit="${validarCampos}"  validate="true" method="POST">
	<@frt.checkListBox name="etapaCheck" id="etapaCheck" label="Etapas" list="etapaSeletivaCheckList" />
	<@ww.select label="Solicitações de Pessoal" name="statusSolicitacao" list=r"#{'T':'Abertas e Encerradas', 'A':'Abertas', 'E':'Encerradas'}"/>
	<@ww.select label="Situação dos Candidatos" name="situacaoCandidato" list=r"#{'I':'Aptos e Não Aptos','S':'Aptos','N':'Não Aptos'}" />
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnRelatorio">
	</button>
</div>
</body>
</html>