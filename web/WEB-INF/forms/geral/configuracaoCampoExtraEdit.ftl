<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
		
		<title>Configurações de Campos Extras</title>
		
	<#assign validarCampos="return validaDinamico();"/>
	
<script type="text/javascript">
	
	function setDisabled($tr)
	{
		var desabilita = $tr.find('input:checkbox:checked').length == 0;
		$tr.find('input:text').attr("disabled", desabilita);
		$tr.find('select').attr("disabled", desabilita);
	}
	
	$(document).ready(function($)
	{
		$('#configuracaoCampoExtra tbody tr').each(function(){
			setDisabled($(this));
		});
	});
	
	function habilitaTexto(checkbox)
	{
		setDisabled($(checkbox).parents('tr'));
	}
	
	function validaDinamico()
	{
		if($('#empresa').val() == "")
			newConfirm('Essas configurações serão aplicadas para todas as empresas!', function(){submtDinamico()});
		else			
			submtDinamico();
	}

	function submtDinamico()
	{
		$("input[type='text']").css("background-color", "#FFF");
		var valida = $('#configuracaoCampoExtra tbody input:text[disabled=false]').map(function (){
    		return this.id;
		});
		validaFormulario('form', valida);
	}
	
	function carregarConfiguracoesEmpresa(empresaId)
	{
		var aviso = "<span style='color:red;margin-left:10px;'>Carregando configurações...</span>"
		$('#empresa').after(aviso);
		
		window.location='prepareUpdate.action?empresa.id=' + empresaId;	
	}
</script>

	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form name="form" action="update.action" onsubmit="${validarCampos}" method="POST">
			<#assign i = 0 />
			
			<@ww.select label="Aplicar na empresa" name="empresa.id" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" headerKey="" headerValue="Todas" onchange="carregarConfiguracoesEmpresa(this.value)"/>
			
			<@ww.checkbox label="Habilitar campos extras no cadastro de Colaboradores" name="habilitaCampoExtraColaborador" labelPosition="left"/>
			<@ww.checkbox label="Habilitar campos extras no cadastro de Candidato" name="habilitaCampoExtraCandidato" labelPosition="left"/><br>
			
			<@display.table name="configuracaoCampoExtras" id="configuracaoCampoExtra" class="dados">
			
				<@display.column title="Colaborador" style="text-align: center; width:40px;">
					<@ww.checkbox theme="simple" id="ativo${i}" name="configuracaoCampoExtras[${i}].ativoColaborador" onchange="habilitaTexto(this);" labelPosition="left"/>
				</@display.column>
				<@display.column title="Candidato" style="text-align: center; width:40px;">
					<@ww.checkbox theme="simple" id="ativo${i}" name="configuracaoCampoExtras[${i}].ativoCandidato" onchange="habilitaTexto(this);" labelPosition="left"/>
				</@display.column>
							
				<@display.column title="Descrição" style="vertical-align:middle; width:120px;">
					${configuracaoCampoExtra.descricao}
				</@display.column>
				
				<@display.column title="Titulo" style="text-align: center;width:400px;">
					<@ww.textfield theme="simple" id="titulo${i}" name="configuracaoCampoExtras[${i}].titulo" maxLength="60" cssStyle="width: 500px;border:1px solid #BEBEBE;"/>
				</@display.column>
				<@display.column title="Ordem" style="vertical-align:middle; text-align: center; width:30px;">
					<@ww.select id="ordem${i}" name="configuracaoCampoExtras[${i}].ordem" list="ordens"/>

					<@ww.hidden name="configuracaoCampoExtras[${i}].id" />				
					<@ww.hidden name="configuracaoCampoExtras[${i}].empresa.id" />				
					<@ww.hidden name="configuracaoCampoExtras[${i}].descricao" />				
					<@ww.hidden name="configuracaoCampoExtras[${i}].nome" />				
					<@ww.hidden name="configuracaoCampoExtras[${i}].tipo" />				
					<@ww.hidden name="configuracaoCampoExtras[${i}].posicao" />				
					<#assign i = i+1 />
				</@display.column>
				
			</@display.table>
				
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
		</div>
		
	</body>
</html>
