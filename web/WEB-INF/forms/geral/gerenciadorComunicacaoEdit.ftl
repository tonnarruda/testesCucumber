<html>
	<head>
		<@ww.head/>
		<#if gerenciadorComunicacao.id?exists>
			<title>Editar GerenciadorComunicacao</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir GerenciadorComunicacao</title>
			<#assign formAction="insert.action"/>
		</#if>
	<script type="text/javascript">
		function validaEmailDestinatario()
		{
			if($('#enviarPara').val() == 3)
			{	
				$.each($('#destinatario').val().split(','), function()
				{
					if(this == "")
					{
						$('#destinatario').css("background-color", "#FFEEC2");
						jAlert("Preencha o campo.");
					}else if(!validaEmail(this.trim()))
					{
						$('#destinatario').css("background-color", "#FF6347");
						jAlert("Email(s) inválido(s).");
					}else
						document.form.submit();
				});
			}else
				document.form.submit();
		}
		
		$(function(){
			$('#enviarPara').change(function(){
				if($(this).val() == 3){
					$('#emailDestinatario').show();
					$('#destinatario').removeAttr('disabled');
				} else {
					$('#destinatario').attr("disabled", 'disabled');
					$('#emailDestinatario').hide();
				}
			});
			
			$('#enviarPara').change();
		});
	</script>
	
	</head>
	<body>
		<#assign validarCampos="return validaFormulario('form', new Array('destinatario'))"/>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Operação" name="gerenciadorComunicacao.operacao" list="operacoes" cssStyle="width: 310px;" liClass="liLeft" />
			<@ww.select label="Meio Comunicacao" name="gerenciadorComunicacao.meioComunicacao" list="meioComunicacoes" cssStyle="width: 310px;" liClass="liLeft"/>
			<@ww.select label="Enviar Para" id="enviarPara" name="gerenciadorComunicacao.enviarPara" list="enviarParas" cssStyle="width: 310px;" />
			<span id="emailDestinatario">
				<@ww.textfield label="Destinatário(s)*" id="destinatario" require="true" cssClass="mascaraEmail" cssStyle="width:937px;" name="gerenciadorComunicacao.destinatario" />
				Obs: Coloque vígula para inserir mais de um email. 
			</span>
			<@ww.hidden name="gerenciadorComunicacao.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="validaEmailDestinatario();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
