<html>
	<head>
		<@ww.head/>
		<#if gerenciadorComunicacao.id?exists>
			<title>Editar Configuração de Comunicação</title>
			<#assign formAction="update.action"/>
			<#assign edicao=true/>
		<#else>
			<title>Inserir Configuração de Comunicação</title>
			<#assign formAction="insert.action"/>
			<#assign edicao=false/>
		</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GerenciadorComunicacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
	<script type="text/javascript">
		function validacoes()
		{
			var valido = true;
			
			$('.campo').css("background-color", "#FFFFFF");
		
		 	if($('#operacao').val() == 0)//0(zero) = Operacao.NAO_INFORMADO
			{
				$('#operacao').css("background-color", "#FFEEC2");
				valido = false;
			}
			
		 	if($('#meioComunicacoes').val() == 0)
			{
				$('#meioComunicacoes').css("background-color", "#FFEEC2");
				valido = false;
			}
			
		 	if($('#enviarPara').val() == 0)
			{
				$('#enviarPara').css("background-color", "#FFEEC2");
				valido = false;
			}

			if(!valido)
			{
				jAlert("Selecione os campos indicados.");
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
			$('#enviarPara').html('<option value="0">Selecione um meio de comunicação...</option>');
			
			DWRUtil.removeAllOptions("meioComunicacoes");
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
		
		function exibeCamposEmailsAvulsos(enviarParaId)
		{
			if(enviarParaId == 99)//EviarPara.Avulso = 99
			{
				$('#emailDestinatario').show();
				$('#destinatario').removeAttr('disabled');
			} else {
				$('#destinatario').attr("disabled", 'disabled');
				$('#emailDestinatario').hide();
			}
		}
		
		$(function(){
			<#if edicao>
				populaEnviarPara(${gerenciadorComunicacao.meioComunicacao});
				exibeCamposEmailsAvulsos(${gerenciadorComunicacao.enviarPara});
			<#else>
				$('#meioComunicacoes').html('<option value="0">Selecione uma operação...</option>');
				$('#enviarPara').html('<option value="0">Selecione um meio de comunicação...</option>');
				exibeCamposEmailsAvulsos(0);
			</#if>
			
		});
	</script>
	
	</head>
	<body>
		<#assign validarCampos="return validaFormulario('form', new Array('destinatario'))"/>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Operação" name="gerenciadorComunicacao.operacao" id="operacao" cssClass="campo" list="operacoes" cssStyle="width: 310px;" liClass="liLeft" onchange="populaMeioComunicacao(this.value);" />
			<@ww.select label="Meio de Comunicação" name="gerenciadorComunicacao.meioComunicacao" id="meioComunicacoes" cssClass="campo" list="meioComunicacoes" cssStyle="width: 310px;" liClass="liLeft" onchange="populaEnviarPara(this.value);"/>
			<@ww.select label="Enviar Para" id="enviarPara" cssClass="campo" name="gerenciadorComunicacao.enviarPara" list="enviarParas" cssStyle="width: 310px;" onchange="exibeCamposEmailsAvulsos(this.value)" />
			<span id="emailDestinatario">
				<@ww.textfield label="Destinatário(s)*" id="destinatario" require="true" cssClass="mascaraEmail" cssStyle="width:937px;" name="gerenciadorComunicacao.destinatario" />
				Obs: Coloque vírgula para inserir mais de um email. 
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
