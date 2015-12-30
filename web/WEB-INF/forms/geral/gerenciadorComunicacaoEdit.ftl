<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<#assign validarCampos="return validaFormulario('form', new Array('destinatario'))"/>
		<@ww.head/>
		<#if gerenciadorComunicacao.id?exists>
			<title>Editar configuração do gerenciador de comunicação</title>
			<#assign formAction="update.action"/>
			<#assign edicao=true/>
		<#else>
			<title>Configurar gerenciador de comunicação</title>
			<#assign formAction="insert.action"/>
			<#assign edicao=false/>
		</#if>
	
		<#include "../ftl/showFilterImports.ftl" />
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GerenciadorComunicacaoDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<style type="text/css">
	    	@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	    	@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	    	
	    	.addDias { margin-left: 7px; }
	    	.dias { background-color: #DEDEDE; padding: 2px 5px; }
	    	.del { cursor: pointer; background-color: #DEDEDE; padding: 2px 3px; margin-right: 4px; }
	    	.del:hover { text-decoration: none; background-color: #CCC; }
	    	.divFiltro {
	    		width:600px;
			}
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
				
			
				if($('#enviarParas').val() == 1)//EviarPara.USUARIOS = 1
				{	
					if(qtdeChecksSelected(document.getElementsByName('form')[0],'usuariosCheck') == 0)
					{
						$('#listCheckBoxusuariosCheck').css("background-color", "#FFEEC2");
						valido =  false;	
					}
				}
				
				if($('#operacao').val() == ${lembreteQuestionarioNaoLiberadoId} || $('#operacao').val() == ${avaliacaoPeriodoExperienciaVencendoId} || $('#operacao').val() == ${habilitacaoAVencerId} ||
				   $('#operacao').val() == ${lembreteAberturaSolicitacaoEpiId} || $('#operacao').val() == ${lembreteEntregaSolicitacaoEpiId} || $('#operacao').val() == ${lembreteTerminoContratoTemporarioColaboradorId} ||
				   $('#operacao').val() == ${notificarCursosAVencer} || $('#operacao').val() == ${notificarCertificacoesAVencer} )
				{
					if($('.dias').size() == 0 )
					{
						$('#qtdDias').css("background-color", "#FFEEC2");
						valido = false;
					}	
				}
	
				if(!valido)
				{
					jAlert("Preencha os campos indicados.");
					return false;
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
				
				$('#qtdDiasLembrete').val($('.dias').map(function() { return $(this).text() }).get().join('&'));
				
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
			
			function exibeCampoPermitirResponderAvaliacao(operacaoId, meioComunicacaoId, enviarParaId)
			{
				if($('#operacao').val() == 9 && $('#meioComunicacoes').val() == 1 && $('#enviarParas').val() == 1)//EnviarPara.USUARIOS = 1
				{
					$('#campoPermitirResponderAvaliacao').show();
				} else {
					$('#campoPermitirResponderAvaliacao').hide();
				}
				
			}
			
			function exibeCampoQtdDiasLembrete(operacaoId)
			{
				$('#camposQtdDiasLembrete').toggle(operacaoId == ${lembreteQuestionarioNaoLiberadoId} || operacaoId == ${avaliacaoPeriodoExperienciaVencendoId} || operacaoId == ${habilitacaoAVencerId} ||
												   operacaoId == ${lembreteAberturaSolicitacaoEpiId} || operacaoId == ${lembreteEntregaSolicitacaoEpiId} || operacaoId == ${lembreteTerminoContratoTemporarioColaboradorId} || 
												   operacaoId == ${notificarCursosAVencer} || operacaoId == ${notificarCertificacoesAVencer} );
										   
				var label = (operacaoId == ${lembreteAberturaSolicitacaoEpiId} || operacaoId == ${lembreteEntregaSolicitacaoEpiId}) ? 'Dias de prazo para o aviso:' : 'Dias de antecedência para o aviso:';
				$('#camposQtdDiasLembrete label').text(label);
			}
			
			function addDia(qtd)
			{
				if($(".dias").size() >= 20 ){
					jAlert("Não é possível cadastrar mais que 20 dias.");
				}
				else { 
				    	if(qtd != "" && $('.dias[id="' + qtd + '"]').size() == 0)
							$('#configDias').append('<span class="dias" id="' + qtd + '">' + qtd + '</span><span class="del" title="Excluir configuração" onclick="delDia(this)"><img src="<@ww.url includeParams="none" value="/imgs/remove.png"/>" border="0" /></span>');
				
						$('#qtdDias').val('').focus();
				}
			}
	
			function delDia(item)
			{
				$(item).prev().remove();
				$(item).remove();
			}
			
			function populaUsuarios()
			{
				var areasIds = getArrayCheckeds(document.getElementById('form'), 'areasCheck');
				var estabelecimentosIds = getArrayCheckeds(document.getElementById('form'), 'estabelecimentosCheck');
				
				console.log(areasIds);
				UsuarioDWR.getByAreaOrganizacionalEstabelecimento(createListUsuarios, areasIds, estabelecimentosIds);
			}
	
			function createListUsuarios(data)
			{
				addChecksByCollection('usuariosCheck',data);
			}
			$(function(){
				<#if edicao>
					exibeCamposEmailsAvulsos(${gerenciadorComunicacao.enviarPara});
					exibeUsuarios(${gerenciadorComunicacao.enviarPara});
					exibeCampoQtdDiasLembrete(${gerenciadorComunicacao.operacao});
					
					$('#operacao').val(${gerenciadorComunicacao.operacao});
					exibeCampoPermitirResponderAvaliacao(); 
					
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
					exibeCampoPermitirResponderAvaliacao();
				</#if>
				
				
			});
		</script>
	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form id="form" name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<li id="wwgrp_operacao" class="wwgrp">    
				<div id="wwlbl_operacao" class="wwlbl">
					<label for="operacao" class="desc">Notificar quando:</label>
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
			
			<@ww.select label="Através de" name="gerenciadorComunicacao.meioComunicacao" id="meioComunicacoes" cssClass="campo" list="meioComunicacoes" cssStyle="width: 600px;" onchange="populaEnviarPara(this.value);"/>
			<@ww.select label="Para" id="enviarParas" cssClass="campo" name="gerenciadorComunicacao.enviarPara" list="enviarParas" cssStyle="width: 600px;" onchange="exibeCamposEmailsAvulsos(this.value);exibeUsuarios(this.value);exibeCampoPermitirResponderAvaliacao();" />
			<span id="emailDestinatario">
				<@ww.textfield label="Destinatário(s)*" id="destinatario" require="true" cssClass="mascaraEmail" cssStyle="width:937px;" name="gerenciadorComunicacao.destinatario" />
				Obs: Coloque vírgula para inserir mais de um email. 
			</span>
			<span id="usuariosCheck">
				<br />
				<#include "../util/topFiltro.ftl" />
					<@frt.checkListBox label="Estabelecimentos" name="estabelecimentosCheck" id="estabelecimentosCheck" list="estabelecimentosCheckList" onClick="populaUsuarios();" width="588" filtro="true" />
					<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" onClick="populaUsuarios();" width="588" filtro="true"/>
				<#include "../util/bottomFiltro.ftl" />
				<br />
				<@frt.checkListBox label="Usuários" id="usuariosMarcados" name="usuariosCheck" list="usuariosCheckList" width="600" filtro="true" />
			</span>
			<span id="camposQtdDiasLembrete">
				<@ww.hidden id="qtdDiasLembrete" name="gerenciadorComunicacao.qtdDiasLembrete"/>
				<label></label> 
				<span id="configDias"></span>
				<span class="addDias">
					<@ww.textfield theme="simple" id="qtdDias" size="2" maxlength="2" onkeypress="return somenteNumeros(event,'');" />
					<img title="Inserir configuração" src="<@ww.url includeParams="none" value="/imgs/add.png"/>" border="0" onclick="addDia($('#qtdDias').val())" style="cursor:pointer;" />
				</span>
			</span>
			<span id="campoPermitirResponderAvaliacao">
				<@ww.checkbox id="permitirResponderAvalicao" name="gerenciadorComunicacao.permitirResponderAvaliacao" label="Permitir os usuários marcados acima a responderem o acompanhamento do período de experiência pela caixa de mensagem." labelPosition="left"/>
			</span>
			<@ww.hidden name="gerenciadorComunicacao.id" />
			<@ww.hidden name="gerenciadorComunicacao	.empresa.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="validacoesGerenciadorComunicacao();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
