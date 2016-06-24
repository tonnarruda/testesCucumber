<html>
	<head>
		<@ww.head/>
		<#if atividadeSegurancaPcmat.id?exists>
			<title>Editar Atividade de Segurança</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Atividade de Segurança</title>
			<#assign formAction="insert.action"/>
		</#if>
		
		<#include "../ftl/mascarasImports.ftl" />
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script>
	
		<#assign validarCampos="return validaFormulario('form', new Array('nome','dataMesAno','cargaHoraria'), new Array('dataMesAno'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="atividadeSegurancaPcmat.id" />
			<@ww.hidden name="atividadeSegurancaPcmat.pcmat.id" />
			<@ww.hidden name="ultimoPcmatId" />
			<@ww.token/>
			
			<@ww.textfield label="Nome" name="atividadeSegurancaPcmat.nome" id="nome" required="true" size="50" maxLenght="200"/>
			<@ww.textfield label="Mês/Ano" name="atividadeSegurancaPcmat.dataMesAno" id="dataMesAno" required="true" cssClass="mascaraMesAnoData" />
			<@ww.textfield label="Carga Horária" name="atividadeSegurancaPcmat.cargaHorariaMinutos" id="cargaHoraria" required="true" cssStyle="width:55px;text-align:right;" maxLength="7" cssClass="hora"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if ultimoPcmatId == atividadeSegurancaPcmat.pcmat.id>
				<button onclick="${validarCampos};" class="btnGravar"></button>
			</#if>
			<button onclick="javascript: executeLink('list.action?pcmat.id=${atividadeSegurancaPcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');" class="btnVoltar"></button>
		</div>
	</body>
</html>
