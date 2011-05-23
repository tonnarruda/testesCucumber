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
	var i = 0;
	var desabilita = "true"
	
	var posArrayValida = 0;
	var valida = new Array();
	
	$(document).ready(function($)
	{
		$("input:checkbox[id^='ativo']").each(function()
		{
				var titulo = $("#titulo" + i);
				var ordem = $("#ordem" + i);
				 	
				if($(this).is(":checked") == false)
				{
					titulo.attr("disabled", true);
					ordem.attr("disabled", true);
				}
				else
				{
					valida[posArrayValida] = "titulo" + i;
					posArrayValida = posArrayValida + 1;
				}

				i = i + 1;
			});
	});
	
	
	function habilitaTexto(checkBox, linha)
	{
		var titulo = $("#titulo" + linha);
		var ordem = $("#ordem" + linha);
		
		if(checkBox.checked)
		{
			titulo.removeAttr("disabled");
			ordem.removeAttr("disabled");
		}	
		else
		{
			titulo.attr("disabled", true);
			ordem.attr("disabled", true);
		}	

		ajustaValida();			
	}

	function ajustaValida()
	{
		var j = 0;
		posArrayValida = 0;
		valida = new Array();
		
		$("input:checkbox[id^='ativo']").each(function()
		{
			if($(this).is(":checked") == true)
			{
				valida[posArrayValida] = "titulo" + j;
				posArrayValida = posArrayValida + 1;
			}

			j = j + 1;
		});
	}
	
	function validaDinamico()
	{
		if($('#empresa').val() == "")
			newConfirm('Essas configurações serão aplicadas para dodas as empresas!', function(){submtDinamico()});
		else			
			submtDinamico();
	}

	function submtDinamico()
	{
		$("input[type='text']").css("background-color", "#FFF");
		validaFormulario('form', valida);
	}
</script>

	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form name="form" action="update.action" onsubmit="${validarCampos}" method="POST">
			<#assign i = 0 />
			
			<@ww.select label="Aplicar na empresa" name="empresa.id" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" headerKey="" headerValue="Todas" onchange="window.location='?empresa.id=' + this.value"/>
			
			<@ww.checkbox label="Habilitar campos extras no cadastro de colaboradores" name="habilitaCampoExtra" labelPosition="left"/><br>
			
			<@display.table name="configuracaoCampoExtras" id="configuracaoCampoExtra" class="dados">
			
				<@display.column title="Ativo" style="text-align: center; width:20px;">
					<@ww.checkbox theme="simple" id="ativo${i}" name="configuracaoCampoExtras[${i}].ativo" onchange="habilitaTexto(this, ${i});" labelPosition="left"/>
				</@display.column>
							
				<@display.column title="Descrição" style="vertical-align:middle; width:120px;">
					${configuracaoCampoExtra.descricao}
				</@display.column>
				
				<@display.column title="Titulo" style="text-align: center;width:400px;">
					<@ww.textfield theme="simple" id="titulo${i}" name="configuracaoCampoExtras[${i}].titulo" maxLength="60" cssStyle="width: 500px;border:1px solid #7E9DB9;"/>
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
