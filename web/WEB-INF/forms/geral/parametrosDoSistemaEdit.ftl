<html>
<head>
<@ww.head/>
	<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
	<title>Editar Configurações do Sistema</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>


	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	
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
			
		});
		
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
				return validaFormulario('form', new Array('appUrl','appContext', 'sessionTimeout','atualizadorPath','servidorRemprot','perfil','emailDoSuporteTecnico', 'emailUser'), new Array('emailDoSuporteTecnico','emailRemetente','proximaVersao'));
			} else {
				return validaFormulario('form', new Array('appUrl','appContext', 'sessionTimeout','atualizadorPath','servidorRemprot','perfil','emailDoSuporteTecnico'), new Array('emailDoSuporteTecnico','emailRemetente','proximaVersao'));			
			}
		}
		
	</script>
	
	<#if parametrosDoSistema?exists && parametrosDoSistema.proximaVersao?exists>
		<#assign dataVersao = parametrosDoSistema.proximaVersao?date/>
	<#else>
		<#assign dataVersao = ""/>
	</#if>
	<#include "../ftl/mascarasImports.ftl" />
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}"   validate="true" method="POST">
	<@ww.textfield label="URL da Aplicação" name="parametrosDoSistema.appUrl" id="appUrl" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Contexto da Aplicação" name="parametrosDoSistema.appContext" id="appContext" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Tempo para expirar a sessão(em segundos)" name="parametrosDoSistema.sessionTimeout" id="sessionTimeout" onkeypress="return(somenteNumeros(event,''));" size="8" maxlength="8" required="true"/>
	<@ww.textfield label="Atualizador" name="parametrosDoSistema.atualizadorPath" id="atualizadorPath" size="80" maxLength="100" required="true"/>
	<@ww.textfield label="Configuração do Autenticador" name="parametrosDoSistema.servidorRemprot" id="servidorRemprot" size="80" maxLength="100" required="true"/>
	<@ww.textfield label="Réplica do Backup do Banco de Dados" name="parametrosDoSistema.caminhoBackup" id="caminhoBackup" size="80" maxLength="200" />
	<br/>
	
	<@ww.select label="Perfil Padrão" name="parametrosDoSistema.perfilPadrao.id" list="perfils" cssStyle="width: 300px;" listKey="id" listValue="nome" required="true" id="perfil"/>
	<@ww.checkbox label="Forçar caixa alta nos campos do módulo externo" id="capitalizarCampos" name="parametrosDoSistema.upperCase" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar Candidatos entre empresas." id="compartilharCandidato" name="parametrosDoSistema.compartilharCandidatos" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar Colaboradores entre empresas." id="compartilharColaborador" name="parametrosDoSistema.compartilharColaboradores" liClass="liLeft" labelPosition="left"/>
	<br />
	<@ww.textfield label="E-mail do Suporte Técnico" name="parametrosDoSistema.emailDoSuporteTecnico" id="emailDoSuporteTecnico" cssClass="mascaraEmail" size="40" maxLength="39" required="true"/>
	
	Email Remetente: &nbsp;&nbsp;&nbsp;
	<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -22px" />
	<br />
	<@ww.textfield name="parametrosDoSistema.emailRemetente" id="emailRemetente" cssClass="mascaraEmail" size="100" maxLength="99"  theme="simple"/>
	<br /><br />
	<div id="suporte">
		<div id="quadro">
			<h2>
				<img src="<@ww.url includeParams="none" value="/imgs/ChatFortes.gif" theme="simple"/>">	
				Configuração para Suporte:
			</h2>
			<@ww.textfield label="Codigo do Cliente" name="parametrosDoSistema.codClienteSuporte" id="codClienteSuporte" size="18" maxLength="10" required="false"/>
		</div>
	</div>
	<br/>

	<@ww.hidden name="parametrosDoSistema.id" />
	<@ww.hidden name="parametrosDoSistema.appVersao" />
	<@ww.hidden name="parametrosDoSistema.enviarEmail" />
	<@ww.hidden name="parametrosDoSistema.acVersaoWebServiceCompativel" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoVisivel" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoObrigatorio" />
	<@ww.hidden name="parametrosDoSistema.camposCandidatoTabs" />
	<@ww.hidden name="parametrosDoSistema.codEmpresaSuporte" />
	
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
				</div>
			</ul>
		</@ww.div>
	</li>

	
	<#assign usuarioId><@authz.authentication operation="id"/></#assign>
	<#if usuarioId?exists && usuarioId?string == "1">
		<@ww.datepicker label="Data da Versão" id="proximaVersao" name="parametrosDoSistema.proximaVersao" value="${dataVersao}" cssClass="mascaraData" />	
	</#if>
</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="submitForm();" class="btnGravar" accesskey="${accessKey}">
		</button>
	</div>

</body>
</html>