<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
	<title>Editar Configurações do Sistema</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	
	<#assign statusInicialAutorizacaoGerstorNaSolicitacaoPessoal = "${parametrosDoSistema.autorizacaoGestorNaSolicitacaoPessoal?string}" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">
		function testaEmail()
		{
			var email = prompt("Enviar mensagem para o e-mail:", "");
			if(email != null)
			{
				if(email.trim() != "")
				{
					DWRUtil.useLoadingMessage('Enviando...');
					DWREngine.setErrorHandler(error);				
					UtilDWR.enviaEmail(apresentaMsg, email, $('#autenticacao').is(':checked'), $('#tls').is(':checked'));
				}
				else
					jAlert("Email Inválido!");
			}
		}

		function error(msg)
  		{
    		jAlert(msg);
  		}
  		
		function apresentaMsg(data)
		{
			jAlert(data);
		}
		
		
		$(document).ready(function(){
			$('#tooltipHelp').qtip({
				content: 'Destinado ao envio de emails referentes a assuntos que envolvem todas as empresas. <br /> Ex: Aviso de Backup'
			});
			
			$('#tooltipHelpAutorizacao').qtip({
					content: 'Ao marcar essa opção favor averiguar o perfil "Autorizar participação do colaborador na solicitação de pessoal" para os usuários que irão utilizar a funcionalidade.'
			});
			
			$('#versaoAcademicaHelp').qtip({
				content: 'Ao marcar essa opção, o sistema limitará a quantidade de 10 empregados por empresa e alguns relatórios sairão com o nome versão acadêmica.'
			});
			
			if ($('#autenticacao').is(':checked')){
				$('#divAutenticacao').show();
				$("#emailUser").removeAttr('disabled');
				$("#emailPass").removeAttr('disabled');
				
			} else {
				$('#divAutenticacao').hide();
				$("#emailUser").attr('disabled','disabled');
				$("#emailPass").attr('disabled','disabled');
			}
			
			$("#autenticacao").click(function(){
				if ($('#autenticacao').is(':checked')){
					$('#divAutenticacao').show();
					$("#emailUser").removeAttr('disabled');
					$("#emailPass").removeAttr('disabled');
				} else {
					$('#divAutenticacao').hide();
					$("#emailUser").attr('disabled','disabled');
					$("#emailPass").attr('disabled','disabled');
				}
			});
			
			$("#qtdColabPesquisa").val(${parametrosDoSistema.quantidadeColaboradoresRelatorioPesquisaAnonima});
			$("#inibirGerarRelatorioPesquisaAnonima").attr('checked', ${parametrosDoSistema.inibirGerarRelatorioPesquisaAnonima?string});
			$("#autorizacaoGestorNaSolicitacaoPessoal").attr('checked', ${parametrosDoSistema.autorizacaoGestorNaSolicitacaoPessoal?string});
			
			$('#inibirGerarRelatorioPesquisaAnonima').change(function(){
				abiltaOuDesabilitaCampoQtdResposta();
			});
			
			$('#inibirGerarRelatorioPesquisaAnonima').change();			
		});
		
		function abiltaOuDesabilitaCampoQtdResposta()
		{
			if ($('#inibirGerarRelatorioPesquisaAnonima').is(":checked"))
				$('#qtdColabPesquisa').removeAttr('disabled').css('background', '#FFFFFF');
			else
				$('#qtdColabPesquisa').attr('disabled', true).css('background', '#F6F6F6');
		} 
		
		
		function validaSessionTimeout()
		{
			if($('#sessionTimeout').val() < 30){
				$('#sessionTimeout').css('background-color', '#FFEEC2');
				jAlert("O valor do tempo para expirar a sessão deve ser maior ou igual a 30.");
				return false;
			}
			return true;
		}
		
		function submitForm()
		{
			if (!validaSessionTimeout())
				return false;
		
			if ($("#autenticacao").is(':checked')) {
				return validaFormulario('form', new Array('appUrl', 'appContext', 'sessionTimeout','atualizadorPath','servidorRemprot','perfil','emailDoSuporteTecnico', 'emailUser'), new Array('emailDoSuporteTecnico','emailRemetente','proximaVersao'));
			} else {
				return validaFormulario('form', new Array('appUrl', 'appContext', 'sessionTimeout','atualizadorPath','servidorRemprot','perfil','emailDoSuporteTecnico'), new Array('emailDoSuporteTecnico','emailRemetente','proximaVersao'));			
			}
		}
		
		function renderizaAviso(){
			if (!$('#autorizacaoGestorNaSolicitacaoPessoal').is(":checked") && ${statusInicialAutorizacaoGerstorNaSolicitacaoPessoal}){
				$('.avisoRemoverAutorizacaoGestorNaSolicitacaoPessoalDiv').show();	
			}
			else
				$('.avisoRemoverAutorizacaoGestorNaSolicitacaoPessoalDiv').hide();
		}
		
	</script>

	<style type="text/css">
		#wwgrp_modulosSistemaCheck{ 
								margin-top:-10px;
							}
		.avisoRemoverAutorizacaoGestorNaSolicitacaoPessoalDiv{	min-height: 50px;
									width: 501px;
								    padding: 14px 12px;
								    margin-bottom: 5px;
    								margin-top: 5px;
								    border-radius: 5px;
								    color: #fff;
								    background-color: #f0ad4e;
								    border-color: #eea236;
								    position: relative;
								    font-weight: bold;	
								}
								
		.avisoRemoverAutorizacaoGestorNaSolicitacaoPessoalDiagonal{	position: absolute;
    									-ms-transform: rotate(7deg); /* IE 9 */
    									-webkit-transform: rotate(7deg); /* Chrome, Safari, Opera */
    									transform: rotate(45deg);
										width: 15px;
									    height: 15px;
									    background: #f0ad4e;
									    top: -3px;
									    left: 32px;
									}
		
	</style>	
	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}"   validate="true" method="POST">
	<@ww.textfield label="URL da aplicação" name="parametrosDoSistema.appUrl" id="appUrl" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Contexto da aplicação" name="parametrosDoSistema.appContext" id="appContext" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Tempo para expirar a sessão(em segundos)" name="parametrosDoSistema.sessionTimeout" id="sessionTimeout" onkeypress="return(somenteNumeros(event,''));" size="8" maxlength="8" required="true"/>
	<@ww.textfield label="Atualizador" name="parametrosDoSistema.atualizadorPath" id="atualizadorPath" size="80" maxLength="100" required="true"/>
	<@ww.textfield label="Configuração do autenticador" name="parametrosDoSistema.servidorRemprot" id="servidorRemprot" size="80" maxLength="100" required="true"/>
	<@ww.textfield label="Réplica do backup do banco de dados" name="parametrosDoSistema.caminhoBackup" id="caminhoBackup" size="80" maxLength="200" />
	<@frt.checkListBox label="Marque as horas desejadas do dia para efetuar o backup do banco de dados" name="horariosBackup" id="horariosBackup" list="horariosBackupList" width="562"/>
	<@ww.select label="Perfil padrão" name="parametrosDoSistema.perfilPadrao.id" list="perfils" cssStyle="width: 300px;" listKey="id" listValue="nome" required="true" id="perfil" />
	<@ww.select label="Tela inicial do módulo externo" id="telainicialmoduloexterno" name="parametrosDoSistema.telaInicialModuloExterno" list=r"#{'L':'Tela de login','V':'Tela de vagas disponíveis'}" required="true" cssStyle="width: 300px;" />
	<@ww.checkbox label="Forçar caixa alta nos campos do módulo externo" id="capitalizarCampos" name="parametrosDoSistema.upperCase" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar candidatos e vagas entre empresas." id="compartilharCandidato" name="parametrosDoSistema.compartilharCandidatos" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar colaboradores entre empresas." id="compartilharColaborador" name="parametrosDoSistema.compartilharColaboradores" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar cursos entre empresas." id="compartilharCurso" name="parametrosDoSistema.compartilharCursos" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Utilizar captcha no login do sistema." id="utilizarCaptchaNoLogin" name="parametrosDoSistema.utilizarCaptchaNoLogin" liClass="liLeft" labelPosition="left"/>
	
	<li style="margin-bottom: 5px">
		<input type="checkbox" name="parametrosDoSistema.autorizacaoGestorNaSolicitacaoPessoal" id="autorizacaoGestorNaSolicitacaoPessoal" value="true" onchange="renderizaAviso()"/>
		Autorizar participação do colaborador em uma solicitação de pessoal. 
		<img id="tooltipHelpAutorizacao" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16"/><br>
		<div class="wwctrl avisoRemoverAutorizacaoGestorNaSolicitacaoPessoalDiv" style="display:none" >
  			<div class="avisoRemoverAutorizacaoGestorNaSolicitacaoPessoalDiagonal"></div>
  			Ao desmarcar essa opção e clicar no botão gravar, a permissão "Autorizar participação do colaborador na solicitação de pessoal" será removida dos perfis 
  			e os gerenciadores de comunicação: "Existir colaborador aguardando autorização para paticipar de uma solicitacao de pessoal" 
  			e "Alterar status de autorização do colaborador para participar de uma solicitacao de pessoal" serão excluídos.'
  		</div>
	</li>
	
	<input type="checkbox" name="parametrosDoSistema.inibirGerarRelatorioPesquisaAnonima" value="true" id="inibirGerarRelatorioPesquisaAnonima"/>
	<label for="inibirGerarRelatorioPesquisaAnonima" class="checkboxLabel">
		Impossibilitar gerar relatório de uma pesquisa anônima que possua 
	</label>
	<select name="parametrosDoSistema.quantidadeColaboradoresRelatorioPesquisaAnonima" id="qtdColabPesquisa">
		<#list 1..10 as n>
			<option value=${n}>${n}</option>
		</#list>
	</select>	
	ou menos respostas.
	
	<br /><br />
	<@ww.textfield label="E-mail do suporte técnico" name="parametrosDoSistema.emailDoSuporteTecnico" id="emailDoSuporteTecnico" cssClass="mascaraEmail" size="40" maxLength="39" required="true"/>
	
	Email remetente: &nbsp;&nbsp;&nbsp;
	<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -22px" />
	<br />
	<@ww.textfield name="parametrosDoSistema.emailRemetente" id="emailRemetente" cssClass="mascaraEmail" size="100" maxLength="99"  theme="simple"/>
	<br /><br />
	
	<@ww.textfield label="Tamanho máximo para upload de documentos anexos pelo módulo externo (Mb)" name="parametrosDoSistema.tamanhoMaximoUpload" id="tamanhoMaximoUpload" onkeypress="return(somenteNumeros(event,''));" size="4" maxlength="2" required="true"/>
	<br />
	
	<div id="suporte2">
		<div id="quadrado">
			<h2>
				<img src="<@ww.url includeParams="none" value="/imgs/chat_fortes_pequeno.png" theme="simple"/>">	
				Configuração para suporte:
			</h2>
			<@ww.textfield label="Codigo do cliente" name="parametrosDoSistema.codClienteSuporte" id="codClienteSuporte" size="18" maxLength="10" required="false"/>
		</div>
	</div>
	<br/>

	<@ww.hidden name="parametrosDoSistema.id" />
	<@ww.hidden name="parametrosDoSistema.appVersao" />
	<@ww.hidden name="parametrosDoSistema.acVersaoWebServiceCompativel" />
	<@ww.hidden name="parametrosDoSistema.codEmpresaSuporte" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoVisivel" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoObrigatorio" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoTabs" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoExternoVisivel" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoExternoObrigatorio" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoExternoTabs" />
	<@ww.hidden name="parametrosDoSistema.camposColaboradorVisivel" />
	<@ww.hidden name="parametrosDoSistema.camposColaboradorObrigatorio" />
	<@ww.hidden name="parametrosDoSistema.camposColaboradorTabs" />
	<@ww.hidden name="parametrosDoSistema.versaoImportador" />
	
	<li>
		<@ww.div cssClass="divInfo">
			<ul>
				<h2>
					Configuração para envio de email:
				</h2>
				<div style="float:right;"><img border="0" title="Testar envio de email" onclick="testaEmail();" src="<@ww.url includeParams="none" value="/imgs/testeEmail.gif"/>" style="cursor:pointer;"></div>
				<@ww.textfield label="Servidor SMTP" name="parametrosDoSistema.emailSmtp" id="emailSmtp" size="40" maxLength="100" />
				<@ww.textfield label="Porta SMTP" name="parametrosDoSistema.emailPort" id="emailPort" size="40" maxLength="100" />
				<@ww.checkbox label="Requer autenticação" id="autenticacao" name="parametrosDoSistema.autenticacao" liClass="liLeft" labelPosition="left"/>
				<div id="divAutenticacao">
					<@ww.textfield label="Usuário" name="parametrosDoSistema.emailUser" id="emailUser" size="40" maxLength="50" />
					<@ww.password label="Senha" name="parametrosDoSistema.emailPass" id="emailPass" size="40" maxLength="50"  after="*Para manter a senha, deixe o campo em branco."/>
					<@ww.checkbox label="Usar TLS" id="tls" name="parametrosDoSistema.tls" liClass="liLeft" labelPosition="left"/>
					<@ww.checkbox label="Usar o remetente de emails igual ao usuário de autenticação do servidor SMTP" id="smtpRemetente" name="parametrosDoSistema.smtpRemetente" liClass="liLeft" labelPosition="left"/>
				</div>
			</ul>
		</@ww.div>
	</li>
	
	<#assign usuarioId>
		<@authz.authentication operation="id"/>
	</#assign>
	
	<#if usuarioId?exists && usuarioId?string == "1">
		<#if parametrosDoSistema?exists && parametrosDoSistema.proximaVersao?exists>
			<#assign dataVersao = parametrosDoSistema.proximaVersao?date/>
		<#else>
			<#assign dataVersao = ""/>
		</#if>
		
		&nbsp;&nbsp;&nbsp;		
		<@ww.checkbox label="Enviar email" id="enviarEmail" name="parametrosDoSistema.enviarEmail" labelPosition="left"/>	
		<@ww.datepicker label="Data da versão" id="proximaVersao" name="parametrosDoSistema.proximaVersao" value="${dataVersao}" cssClass="mascaraData" />

		<@ww.checkbox label="Versão acadêmica" id="versaoAcademica" name="parametrosDoSistema.versaoAcademica" labelPosition="left"/>
		<img id='versaoAcademicaHelp' src="<@ww.url value='/imgs/help.gif'/>" width='16' height='16' style='margin-left: 133px;margin-top: -20px;vertical-align: top;' />
		<@frt.checkListBox name="modulosSistemaCheck" id="modulosSistemaCheck" label="Módulos do Sistema" list="modulosSistema"/>
	<#else>
		<@ww.hidden name="parametrosDoSistema.proximaVersao" />
		<@ww.hidden name="parametrosDoSistema.versaoAcademica" />
		<@ww.hidden name="parametrosDoSistema.enviarEmail" />
	</#if>
	
</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="submitForm();" class="btnGravar" accesskey="${accessKey}">
		</button>
	</div>
</body>
</html>