<html>
	<head>
		<@ww.head/>
		<#if gerenciadorComunicacao.id?exists>
			<title>Editar Configuração de Comunicação</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Configuração de Comunicação</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GerenciadorComunicacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
	<script type="text/javascript">
		function validacoes()
		{
		 	if($('#operacao').val() == 0)//0(zero) = Operacao.NAO_INFORMADO
			{
				$('#operacao').css("background-color", "#FFEEC2");
				jAlert("Preencha o campo indicado.");
				return false;	
			}
		 	
		 	var submeter = true;
			if($('#enviarPara').val() == 99)//EviarPara.Avulso = 99
			{	
				if($('#destinatario').val() == "")
				{
					$('#destinatario').css("background-color", "#FFEEC2");
					jAlert("Preencha o campo indicado.");
					return false;	
				}
			
				$.each($('#destinatario').val().split(','), function()
				{
					if(!validaEmail(this.trim()))
					{
						$('#destinatario').css("background-color", "#FF6347");
						submeter = false;						
					}
				});
			}
			
			if(submeter)
				document.form.submit();
			else
				jAlert("Email(s) inválido(s).");
		}
		
		function populaMeioComunicacao(operacaoId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			GerenciadorComunicacaoDWR.getMeioComunicacao(createListMeioComunicacao, operacaoId);
		}

		function createListMeioComunicacao(data)
		{
			DWRUtil.removeAllOptions("meioComunicacoes");
			DWRUtil.removeAllOptions("enviarPara");
			DWRUtil.addOptions("meioComunicacoes", data);
		}

		function populaEnviarPara(meioComunicacaoId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			GerenciadorComunicacaoDWR.getEnviarPara(createListEnviarPara, $('#operacao').val(), meioComunicacaoId);
		}		

		function createListEnviarPara(data)
		{
			DWRUtil.removeAllOptions("enviarPara");
			DWRUtil.addOptions("enviarPara", data);
			$('#enviarPara').change();
		}
		
		$(function(){
			$('#enviarPara').change(function(){
				if($(this).val() == 99)//EviarPara.Avulso = 99
				{
					$('#emailDestinatario').show();
					$('#destinatario').removeAttr('disabled');
				} else {
					$('#destinatario').attr("disabled", 'disabled');
					$('#emailDestinatario').hide();
				}
			});
			
			$('#operacao').change();
			$('#enviarPara').change();
		});
	</script>
	
	</head>
	<body>
		<#assign validarCampos="return validaFormulario('form', new Array('destinatario'))"/>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Operação" name="gerenciadorComunicacao.operacao" id="operacao" list="operacoes" cssStyle="width: 310px;" liClass="liLeft" onchange="populaMeioComunicacao(this.value);" />
			<@ww.select label="Meio de Comunicação" name="gerenciadorComunicacao.meioComunicacao" id="meioComunicacoes" list="meioComunicacoes" cssStyle="width: 310px;" liClass="liLeft" onchange="populaEnviarPara(this.value);"/>
			<@ww.select label="Enviar Para" id="enviarPara" name="gerenciadorComunicacao.enviarPara" list="enviarParas" cssStyle="width: 310px;" />
			<span id="emailDestinatario">
				<@ww.textfield label="Destinatário(s)*" id="destinatario" require="true" cssClass="mascaraEmail" cssStyle="width:937px;" name="gerenciadorComunicacao.destinatario" />
				Obs: Coloque vígula para inserir mais de um email. 
			</span>
			<@ww.hidden name="gerenciadorComunicacao.id" />
			<@ww.hidden name="gerenciadorComunicacao.empresa.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="validacoes();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
