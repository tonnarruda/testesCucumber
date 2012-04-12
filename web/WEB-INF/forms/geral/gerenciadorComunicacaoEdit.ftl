<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<#if gerenciadorComunicacao.id?exists>
			<title>Editar Gerenciador de Comunicação</title>
			<#assign formAction="update.action"/>
			<#assign edicao=true/>
		<#else>
			<title>Inserir Gerenciador de Comunicação</title>
			<#assign formAction="insert.action"/>
			<#assign edicao=false/>
		</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GerenciadorComunicacaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<style type="text/css">
    	@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
    	@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
    	
    	.addDias { margin-left: 7px; }
    	.dias { background-color: #DEDEDE; padding: 2px 5px; }
    	.del { cursor: pointer; background-color: #DEDEDE; padding: 2px 3px; margin-right: 4px; }
    	.del:hover { text-decoration: none; background-color: #CCC; }
  	</style>
		
	<script type="text/javascript">
		function validacoesGerenciadorComunicacao()
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
			
		 	if($('#enviarParas').val() == 0)
			{
				$('#enviarParas').css("background-color", "#FFEEC2");
				valido = false;
			}
			
			if(!valido)
			{
				jAlert("Selecione os campos indicados.");
				return false;
			}	
		
			if($('#enviarParas').val() == 1)//EviarPara.USUARIOS = 1
			{	
				if(qtdeChecksSelected(document.getElementsByName('form')[0],'usuariosCheck') == 0)
				{
					$('#listCheckBoxusuariosCheck').css("background-color", "#FFEEC2");
					jAlert("Preencha o campo indicado.");
					return false;	
				}
			}
		
		 	var submeter = true;
			if($('#enviarParas').val() == 99)//EviarPara.AVULSO = 99
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
			
			$('#qtdDiasLembrete').val($('.dias').map(function() { return this.innerText }).get().join('&'));
			
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
			$('#enviarParas').html('<option value="0">Selecione um meio de comunicação...</option>');
			
			DWRUtil.removeAllOptions("meioComunicacoes");
			DWRUtil.addOptions("meioComunicacoes", data);
			if ($("#meioComunicacoes option").size() == 2)
			{
				$("#meioComunicacoes option:last").attr("selected", "selected");
				$("#meioComunicacoes").change();
			}
		}

		function populaEnviarPara(meioComunicacaoId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			GerenciadorComunicacaoDWR.getEnviarPara(createListEnviarPara, meioComunicacaoId);
		}		

		function createListEnviarPara(data)
		{
			DWRUtil.removeAllOptions("enviarParas");
			DWRUtil.addOptions("enviarParas", data);
			if ($("#enviarParas option").size() == 2)
			{
				$("#enviarParas option:last").attr("selected", "selected");
			}
			$('#enviarParas').change();
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

		function exibeUsuarios(enviarParaId)
		{
			if(enviarParaId == 1)//EnviarPara.USUARIOS = 1
			{
				$('#usuariosCheck').show();
			} else {
				$('#usuariosCheck').hide();
			}
		}
		
		function exibeCampoQtdDiasLembrete(operacaoId)
		{
			$('#camposQtdDiasLembrete').toggle(operacaoId == ${lembreteQuestionarioNaoLiberadoId} || operacaoId == ${avaliacaoPeriodoExperienciaVencendoId} ||
											   operacaoId == ${lembreteAberturaSolicitacaoEpiId} || operacaoId == ${lembreteEntregaSolicitacaoEpiId});
		}
		
		function addDia(qtd)
		{
			if (qtd != "" && $('.dias[id="' + qtd + '"]').size() == 0)
				$('#configDias').append('<span class="dias" id="' + qtd + '">' + qtd + '</span><span class="del" title="Excluir configuração" onclick="delDia(this)"><img src="<@ww.url includeParams="none" value="/imgs/remove.png"/>" border="0" /></span>');
			
			$('#qtdDias').val('').focus();
		}

		function delDia(item)
		{
			$(item).prev().remove();
			$(item).remove();
		}
		
		$(function(){
			<#if edicao>
				exibeCamposEmailsAvulsos(${gerenciadorComunicacao.enviarPara});
				exibeUsuarios(${gerenciadorComunicacao.enviarPara});
				exibeCampoQtdDiasLembrete(${gerenciadorComunicacao.operacao});
				$('#operacao').val(${gerenciadorComunicacao.operacao});
				
				<#if gerenciadorComunicacao.qtdDiasLembrete?exists && gerenciadorComunicacao.qtdDiasLembrete != "">
					var arrayDias = $('${gerenciadorComunicacao.qtdDiasLembrete}'.split('&')).map(function(item) { return parseInt(this); });
					arrayDias.sort(function(a, b) { return a - b; });  
					$(arrayDias).each(function(){ addDia(this); });
				</#if>
			<#else>
				<#if gerenciadorComunicacao?exists && gerenciadorComunicacao.operacao?exists>
					$('#operacao').val(${gerenciadorComunicacao.operacao});
				</#if>
				<#if meioComunicacoes?size == 0>
					$('#meioComunicacoes').html('<option value="0">Selecione uma operação...</option>');
				</#if>
				<#if enviarParas?size == 0>
					$('#enviarParas').html('<option value="0">Selecione um meio de comunicação...</option>');
				</#if>
				exibeCamposEmailsAvulsos(0);
				exibeUsuarios(0);
				exibeCampoQtdDiasLembrete(0);
			</#if>
		});
	</script>
	
	</head>
	<body>
		<#assign validarCampos="return validaFormulario('form', new Array('destinatario'))"/>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<li id="wwgrp_operacao" class="wwgrp">    
				<div id="wwlbl_operacao" class="wwlbl">
					<label for="operacao" class="desc">Operação:</label>
				</div> 
				<div id="wwctrl_operacao" class="wwctrl">
					<select name="gerenciadorComunicacao.operacao" id="operacao" class="campo" style="width: 600px;" onchange="populaMeioComunicacao(this.value);exibeCampoQtdDiasLembrete(this.value);">
						<option value="0">Selecione...</option>
						<@ww.iterator value="operacoes">
							<optgroup label="<@ww.property value="key"/>">
								<#list value as operacao>
									<option value="${operacao.id}">${operacao.descricao}</option>
								</#list>
							</optgroup>
						</@ww.iterator>
					</select>
				</div>
			</li>			
			
			<@ww.select label="Meio de Comunicação" name="gerenciadorComunicacao.meioComunicacao" id="meioComunicacoes" cssClass="campo" list="meioComunicacoes" cssStyle="width: 600px;" onchange="populaEnviarPara(this.value);"/>
			<@ww.select label="Enviar Para" id="enviarParas" cssClass="campo" name="gerenciadorComunicacao.enviarPara" list="enviarParas" cssStyle="width: 600px;" onchange="exibeCamposEmailsAvulsos(this.value);exibeUsuarios(this.value);" />
			<span id="emailDestinatario">
				<@ww.textfield label="Destinatário(s)*" id="destinatario" require="true" cssClass="mascaraEmail" cssStyle="width:937px;" name="gerenciadorComunicacao.destinatario" />
				Obs: Coloque vírgula para inserir mais de um email. 
			</span>
			<span id="usuariosCheck">
				<@frt.checkListBox label="Usuários" id="usuariosMarcados" name="usuariosCheck" list="usuariosCheckList"/>
			</span>
			<span id="camposQtdDiasLembrete">
				<@ww.hidden id="qtdDiasLembrete" name="gerenciadorComunicacao.qtdDiasLembrete"/>
				Dias de antecedência para o aviso: 
				<span id="configDias"></span>
				<span class="addDias">
					<@ww.textfield theme="simple" id="qtdDias" size="2" maxlength="2" onkeypress="return somenteNumeros(event,'');" />
					<img title="Inserir configuração" src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" onclick="addDia($('#qtdDias').val())" style="cursor:pointer;" />
				</span> 				
			</span>
			<@ww.hidden name="gerenciadorComunicacao.id" />
			<@ww.hidden name="gerenciadorComunicacao.empresa.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="validacoesGerenciadorComunicacao();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
