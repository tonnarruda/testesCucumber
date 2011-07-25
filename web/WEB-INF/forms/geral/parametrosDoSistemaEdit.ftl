<html>
<head>
<@ww.head/>
	<title>Editar Configurações do Sistema</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>

	<#assign validarCampos="return validaFormulario('form', new Array('appUrl','appContext','atualizadorPath','servidorRemprot','diasLembretePesquisa'), null)"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
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
					UtilDWR.enviaEmail(apresentaMsg, email);
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
	</script>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="form" action="${formAction}"  onsubmit="${validarCampos}"  validate="true" method="POST">
	<@ww.textfield label="URL da Aplicação" name="parametrosDoSistema.appUrl" id="appUrl" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Contexto da Aplicação" name="parametrosDoSistema.appContext" id="appContext" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Atualizador" name="parametrosDoSistema.atualizadorPath" id="atualizadorPath" size="40" maxLength="100" required="true"/>
	<@ww.textfield label="Configuração do Autenticador" name="parametrosDoSistema.servidorRemprot" id="servidorRemprot" size="40" maxLength="100" required="true"/>
	
	<br/>
	Alertar sobre liberação de Pesquisas
	<@ww.textfield theme="simple" name="parametrosDoSistema.diasLembretePesquisa" onkeypress = "return(somenteNumeros(event,'&'));" id="diasLembretePesquisa" maxLength="20" required="true" cssStyle="width:55px; text-align:right;"/>
	dias antes da data inicial. (Exemplo: 1&2&3) 
	<br/><br/>
	
	Alertar sobre Acompanhamento do Período de Experiência com 
	<@ww.textfield theme="simple" name="parametrosDoSistema.diasLembretePeriodoExperiencia" onkeypress = "return(somenteNumeros(event,'&'));" id="diasLembretePeriodoExperiencia" maxLength="20" required="true" cssStyle="width:55px; text-align:right;" />
	dias de antecedência. (Exemplo: 1&2&3)
	<br/><br/>
	
	<@ww.select label="Perfil Padrão" name="parametrosDoSistema.perfilPadrao.id" list="perfils" cssStyle="width: 300px;" listKey="id" listValue="nome"/>
	
	<@ww.checkbox label="Forçar caixa alta nos campos do módulo externo" id="capitalizarCampos" name="parametrosDoSistema.upperCase" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar Candidatos entre empresas." id="compartilharCandidato" name="parametrosDoSistema.compartilharCandidatos" liClass="liLeft" labelPosition="left"/>
	<@ww.checkbox label="Compartilhar Colaboradores entre empresas." id="compartilharColaborador" name="parametrosDoSistema.compartilharColaboradores" liClass="liLeft" labelPosition="left"/>
	<br>

	<@ww.textfield label="E-mail do Suporte Técnico" name="parametrosDoSistema.emailDoSuportTecnico" id="emailDoSuporteTecnico" size="40" maxLength="39" required="true"/>

	<br>
	<div id="suporte">
		<div id="quadro">
			<h2>
				<img src="<@ww.url includeParams="none" value="/imgs/ChatFortes.gif" theme="simple"/>">	
				Configuração para Suporte:
			</h2>
			<@ww.textfield label="Codigo da Empresa" name="parametrosDoSistema.codEmpresaSuporte" id="codEmpresaSuporte" size="18" maxLength="10" required="false" liClass="liLeft"/>
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
	
	<li>
		<@ww.div cssClass="divInfo">
			<ul>
				<div style="float:right;"><img border="0" title="Testar envio de email" onclick="testaEmail();" src="<@ww.url includeParams="none" value="/imgs/testeEmail.gif"/>" style="cursor:pointer;"></div>
				<@ww.textfield label="Servidor SMTP" name="parametrosDoSistema.emailSmtp" id="emailSmtp" size="40" maxLength="100" />
				<@ww.textfield label="Porta SMTP" name="parametrosDoSistema.emailPort" id="emailPort" size="40" maxLength="100" />
				<@ww.textfield label="Usuário" name="parametrosDoSistema.emailUser" id="emailUser" size="40" maxLength="50" />
				<@ww.password label="Senha" name="parametrosDoSistema.emailPass" id="emailPass" size="40" maxLength="50"  after="*Para manter a senha, deixe o campo em branco."/>
			</ul>
		</@ww.div>
	</li>

	
</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}">
		</button>
	</div>

</body>
</html>