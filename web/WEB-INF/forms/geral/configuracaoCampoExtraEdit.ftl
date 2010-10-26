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
	
	jQuery(document).ready(function($)
	{
		jQuery("input:checkbox[id^='ativo']").each(function()
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
		var titulo = jQuery("#titulo" + linha);
		var ordem = jQuery("#ordem" + linha);
		
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
		
		jQuery("input:checkbox[id^='ativo']").each(function()
		{
			if(jQuery(this).is(":checked") == true)
			{
				valida[posArrayValida] = "titulo" + j;
				posArrayValida = posArrayValida + 1;
			}

			j = j + 1;
		});
	}
	
	function validaDinamico()
	{
		jQuery("input[type='text']").css("background-color", "#FFF");
		validaFormulario('form', valida);
	}
</script>

	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form name="form" action="update.action" onsubmit="${validarCampos}" method="POST">
			<#assign i = 0 />
			
			<@ww.checkbox label="Habilitar campos extras no cadastro de colaboradores" name="habilitaCampoExtra" labelPosition="left"/><br>
			
			<@display.table name="configuracaoCampoExtras" id="configuracaoCampoExtra" class="dados">
			
				<@display.column title="Ativo" style="text-align: center; width:20px;">
					<@ww.checkbox theme="simple" id="ativo${i}" name="configuracaoCampoExtras[${i}].ativo" onchange="habilitaTexto(this, ${i});" labelPosition="left"/>
				</@display.column>
							
				<@display.column title="Descrição" style="vertical-align:top; text-align: center; width:120px;">
					${configuracaoCampoExtra.descricao}
				</@display.column>
				
				<@display.column title="Titulo" style="text-align: center;width:400px;">
					<@ww.textfield theme="simple" id="titulo${i}" name="configuracaoCampoExtras[${i}].titulo" maxLength="60" cssStyle="width: 500px;border:1px solid #7E9DB9;"/>
				</@display.column>
				<@display.column title="Ordem" style="vertical-align:top; text-align: center; width:30px;">
					<@ww.select id="ordem${i}" name="configuracaoCampoExtras[${i}].ordem" list="ordens"/>

					<@ww.hidden name="configuracaoCampoExtras[${i}].id" />				
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
