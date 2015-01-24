<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if conhecimento.id?exists>
	<title>Editar Conhecimento</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Conhecimento</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return validaFormulario('form', new Array('nome','@areasCheck'), null)"/>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">

	<@ww.textfield label="Nome" name="conhecimento.nome" id="nome" required="true" cssClass="inputNome" maxLength="100" cssStyle="width:500px;"/>

	<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais *" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
	<@frt.checkListBox name="cursosCheck" id="cursosCheck" label="Cursos/Treinamentos Sugeridos" list="cursosCheckList" filtro="true"/>
	<@ww.textarea label="Observação" name="conhecimento.observacao" id="observacao" cssStyle="width:500px;"/>

	<@ww.hidden label="Id" name="conhecimento.id" />

<@ww.token/>
</@ww.form>


<div class="buttonGroup">
	<button onclick="${validarCampos};" class="btnGravar" ></button>
	<button onclick="window.location='list.action'" class="btnCancelar" ></button>
</div>
</body>
</html>