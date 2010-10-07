<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>

	<#include "../ftl/mascarasImports.ftl" />

<script language="javascript">
	var diasIds = "";
	function populaDias(frm)
	{
		var dIni = document.getElementById('prevIni');
		var dFim = document.getElementById('prevFim');

		if(validaDate(dIni) && validaDate(dFim))
		{
			if(dIni.value != "  /  /    " && dFim.value != "  /  /    ")
			{
				diasIds = getArrayCheckeds(frm, "diasCheck");
				DWRUtil.useLoadingMessage('Carregando...');
				DiaTurmaDWR.getDias(montaListDias, dIni.value, dFim.value);
			}
		}
	}

	function montaListDias(data)
	{
		if(data != null)
		{
			addChecks('diasCheck',data)
			marcaCheckListBoxString(diasIds);
		}
		else
			alert("Data Inválida.");
	}

	function marcaCheckListBoxString(checks)
	{
		for(var count = 0; count < checks.length; count++)
		{
			elemento = document.getElementById("checkGroupdiasCheck"+checks[count]);
			if(elemento != null)
				elemento.checked = true;
		}
	}
</script>


<@ww.head/>
<#if turma?exists && turma.id?exists>
	<title>Editar Turma - ${turma.curso.nome}</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Turma</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('curso','desc','inst','custo','prevIni','prevFim'), new Array('prevIni', 'prevFim'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Curso" name="turma.curso.id" list="cursos" id="curso" listKey="id" listValue="nome"  headerKey="" headerValue="Selecione..." />
		<@ww.textfield required="true" label="Descrição" name="turma.descricao" id="desc" cssStyle="width: 637px;" maxLength="100"/>
		<@ww.textfield label="Horário" maxLength="20" name="turma.horario" id="horario" liClass="liLeft" />
		<@ww.textfield required="true" label="Instrutor" size="55" name="turma.instrutor" id="inst" maxLength="100" liClass="liLeft"/>
		<@ww.textfield required="true" label="Custo (R$)" size="12" name="turma.custo" id="custo" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" />

		<@ww.textfield label="Instituição" maxLength="100" name="turma.instituicao" id="instituicao"  cssStyle="width: 437px;" liClass="liLeft"/>
		<@ww.textfield label="Qtd. Prevista de Participantes" name="turma.qtdParticipantesPrevistos" id="qtdParticipantesPrevistos" cssStyle="width:30px; text-align:right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
		<@ww.select label="Realizada" name="turma.realizada" list=r"#{true:'Sim',false:'Não'}"/>

		<#assign dataIni=""/>
		<#if turma?exists && turma.dataPrevIni?exists>
			<#assign dataIni=turma.dataPrevIni/>
		</#if>
		<#assign dataFim=""/>
		<#if turma?exists && turma.dataPrevFim?exists>
			<#assign dataFim=turma.dataPrevFim/>
		</#if>

		Período:*<br>

		<#if turma.temPresenca?exists && turma.temPresenca>
			<@ww.textfield name="turma.dataPrevIni" value="${dataIni}" id="prevIni" liClass="liLeft" readonly='true' cssStyle="width:80px;" />
			<@ww.label value="a" liClass="liLeft"/>
			<@ww.textfield name="turma.dataPrevFim" value="${dataFim}" id="prevFim" readonly='true' maxlength="10" cssStyle="width:80px;"/>
			<@frt.checkListBox name="diasCheck" label="Dias Previstos" list="diasCheckList" readonly=true valueString=true/>
		<#else>
			<@ww.datepicker required="true" name="turma.dataPrevIni" value="${dataIni}" id="prevIni" liClass="liLeft" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataIni"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.datepicker required="true" name="turma.dataPrevFim" value="${dataFim}" id="prevFim" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataFim" />
			<@frt.checkListBox name="diasCheck" label="Dias Previstos" list="diasCheckList" readonly=false valueString=true/>
		</#if>

		<#if avaliacaoRespondida == true>
			<#assign somenteLeitura = "true"/>
			<@ww.hidden name="turma.avaliacaoTurma.id" />
		<#else>
			<#assign somenteLeitura = "false"/>
		</#if>
		<@ww.select disabled="${somenteLeitura}" label="Questionário de Avaliação de Curso" name="turma.avaliacaoTurma.id" list="avaliacaoTurmas" listKey="id" listValue="questionario.titulo" headerValue="Selecione..." headerKey=""/>

		<@ww.hidden name="turma.id" />
		<@ww.hidden name="turma.empresa.id" />
		<@ww.hidden name="planoTreinamento" />

	</@ww.form>

	<#if planoTreinamento>
		<#assign urlVoltar="filtroPlanoTreinamento.action"/>
	<#else>
		<#assign urlVoltar="list.action?curso.id=${curso.id}"/>
	</#if>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='${urlVoltar}'" class="btnVoltar"></button>
	</div>
</body>
</html>