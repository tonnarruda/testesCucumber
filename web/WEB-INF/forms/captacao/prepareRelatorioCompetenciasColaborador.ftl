<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Colaboradores com Nível de Competência inferior ao exigido pela Faixa Salarial</title>
	<#assign validarCampos="return validaFormulario('form', new Array('data', 'faixa', '@competenciasCheck'), new Array('data'))"/>	

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CompetenciaDWR.js"/>'></script>
	
	<script type='text/javascript'>
		$(function() {
			$('#data').val($.datepicker.formatDate('dd/mm/yy',new Date()));
		});

		function populaCompetencia()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CompetenciaDWR.getCompetenciasColaboradorByFaixaSalarialAndData(createListCompetencia, $('#faixa').val(), $('#data').val());
		}

		function createListCompetencia(data)
		{
			$('.info').remove();
			addChecks('competenciasCheck',data);

			if($('[name="competenciasCheck"]').size() == 0)
				montaAlerta();
		}
		
		function montaAlerta()
		{
			var content =  ' <div class="info"> ';
			content +=	' <ul>';
			content +=	' 	<li>Não existem competências avaliadas para os colaboradores com o filtro informado.</li>';
			content +=	' </ul>';
			content +=	'</div>';
			
			$('#listCheckBoxcompetenciasCheck').append(content);			
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="imprimirRelatorioCompetenciasColaborador.action" onsubmit="${validarCampos}"  method="POST">
		<@ww.datepicker label="A partir de*" name="data" id="data" cssClass="mascaraData" onchange="populaCompetencia();"/>
		<@ww.select label="Cargo/Faixa Salarial" name="faixaSalarial.id" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." cssStyle="width: 502px;" onchange="populaCompetencia();" />
		<@frt.checkListBox  label="Competências da Faixa Salarial *" name="competenciasCheck" id="competenciasCheck" list="competenciasCheckList" height="250" filtro="true"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>