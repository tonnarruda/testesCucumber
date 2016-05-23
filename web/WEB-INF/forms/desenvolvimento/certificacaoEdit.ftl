<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<#if certificacao.id?exists>
			<title>Editar Certificação</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Certificação</title>
			<#assign formAction="insert.action"/>
		</#if>
		<#assign validarCampos="return validaFormulario('form', new Array('nome', '@cursosCheck'), null)"/>
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<script type="text/javascript">
			$(function() {
				<#if bloquearEdicao>
					$('#tooltipHelp,#tooltipHelpMult,#tooltipHelpMultAv').qtip({
						content: 'O conteúdo não pode ser editado, pois existem colaboradores certificados para essa certificação.'
					});
				
					$('#listCheckBoxcursosCheck,#listCheckBoxavaliacoesPraticasCheck,#certificacaoPreRequisitoId').css("background-color", "#EFEFEF");
				</#if>
			});
		</script>
	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Nome" id="nome" name="certificacao.nome"  cssStyle="width:500px" maxLength="100" required="true"/>
			<#if empresaSistema.controlarVencimentoPorCertificacao>
				<@ww.textfield label="Periodicidade em meses" name="certificacao.periodicidade" id="periodicidade" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/>

				Certificação pré-requisito:	
				<#if bloquearEdicao><img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  /></#if>
				<@ww.select name="certificacao.certificacaoPreRequisito.id" id="certificacaoPreRequisitoId" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 500px;" disabled=bloquearEdicao />
			<#else>
				<@ww.hidden name="certificacao.periodicidade" />
				<@ww.hidden name="certificacao.certificacaoPreRequisito.id" />	
			</#if>
			
			Cursos:*
			<#if bloquearEdicao><img id="tooltipHelpMult" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  /></#if>
        	<@frt.checkListBox name="cursosCheck" list="cursosCheckList" filtro="true" readonly =bloquearEdicao/>
	        
	        <#if empresaSistema.controlarVencimentoPorCertificacao>
	        	Avaliações Práticas:
	        	<#if bloquearEdicao><img id="tooltipHelpMultAv" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"  /></#if>
        		<@frt.checkListBox name="avaliacoesPraticasCheck" list="avaliacoesPraticasCheckList" filtro="true" readonly =bloquearEdicao />
	        </#if>
			
			<@ww.hidden name="certificacao.id" />
			<@ww.token/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>