<html>
<head>
<@ww.head/>

	<#if empresa.id?exists>
		<title>Editar Empresa</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Empresa</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#assign validarCampos2="return validaFormulario('form', new Array('nome', 'razao','uf','cidade', 'cnpj','acUsuario','acUrlSoap','acUrlWdsl', 'remetente', 'respSetorPessoal', 'respRH'), new Array('remetente','respSetorPessoal','respRH'))"/>
	<#assign validarCampos="return validaFormulario('form', new Array('nome', 'razao','uf','cidade', 'cnpj', 'remetente', 'respSetorPessoal', 'respRH'), new Array('remetente','respSetorPessoal','respRH'))"/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		function testaConexaoAC()
		{
			if(document.getElementById("integra").checked)
			{
				var acUsuario = document.getElementById("acUsuario").value;
				var acSenha = document.getElementById("acSenha").value;
				var acUrlSoap = document.getElementById("acUrlSoap").value;
				var codigoAC = document.getElementById("codigoAC").value;

				document.getElementById("btnTransferir").src="<@ww.url includeParams="none" value="/imgs/conectando.gif"/>";
				UtilDWR.getToken(apresenta_Msg, acUsuario, acSenha, acUrlSoap, codigoAC);
			}
			else
			{
				jAlert("Opção: Integra com AC Pessoal não selecionada.");
			}
		}
		
		function populaCidades()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CidadeDWR.getCidades(createListCidades, document.getElementById("uf").value);
		}

		function createListCidades(data)
		{
			DWRUtil.removeAllOptions("cidade");
			DWRUtil.addOptions("cidade", data);
		}

		function apresenta_Msg(msg)
		{
			document.getElementById("btnTransferir").src="<@ww.url includeParams="none" value="/imgs/transferencia.gif"/>";
			jAlert(msg);
		}

		function habilitaDesabilitaCamposAC()
		{
			if(document.getElementById("integra").checked)
			{
				document.getElementById("acUsuario").disabled=false;
				document.getElementById("acSenha").disabled=false;
				document.getElementById("acUrlSoap").disabled=false;
				document.getElementById("acUrlWdsl").disabled=false;
			}
			else
			{
				document.getElementById("acUsuario").disabled=true;
				document.getElementById("acSenha").disabled=true;
				document.getElementById("acUrlSoap").disabled=true;
				document.getElementById("acUrlWdsl").disabled=true;
			}
		}
		function enviaForm()
		{
			if(document.getElementById('mensagemModuloExterno').value.length > 400)
			{
				jAlert("A quantidade de carácteres não pode ser maior que 400");
				return false;
			}
						
			if(document.getElementById('cnpj').value.length == 8)
			{			
				if(document.getElementById('integra').checked)
					${validarCampos2};
			 	else 
			 		${validarCampos};
			}
			else
				jAlert("Base CNPJ deve ter 8 dígitos.");
		}
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();" validate="true" method="POST" enctype="multipart/form-data">
		<@ww.file label="Logo dos Relatórios(55px x 55px)" name="logo" id="logo" liClass="liLeft"/>
			<li>
				<@ww.div cssStyle="width:450px; height:55px; text-align:right;">
					<ul>
						<#if empresa.id?exists && empresa.logoUrl?exists && empresa.logoUrl != "">
							<img border="0" width="55" height="55" src="<@ww.url includeParams="none" value="/geral/empresa/showLogo.action?empresa.logoUrl=${empresa.logoUrl}"/>">
						</#if>
					</ul>
				</@ww.div>
			</li>

		<@ww.textfield label="Nome" name="empresa.nome" id="nome" required="true" cssClass="inputNome" maxLength="15"/>
		<@ww.textfield label="Denominação Social" name="empresa.razaoSocial" id="razao" required="true" cssClass="inputNome" maxLength="60" />
		<@ww.textfield label="Endereço" name="empresa.endereco"  cssClass="inputNome" maxLength="100" />
		<@ww.select label="Estado" name="empresa.uf.id" id="uf" list="ufs" required="true" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="-1" headerValue="" onchange="javascript:populaCidades()"/>
		<@ww.select label="Cidade" name="empresa.cidade.id" id="cidade" list="cidades" required="true" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" />
		<@ww.textfield label="Base CNPJ" name="empresa.cnpj" id="cnpj" required="true"  onkeypress="return(somenteNumeros(event,''));" cssStyle="width:100px;" maxLength="8"/>
		<@ww.textfield label="Atividade" name="empresa.atividade" cssClass="inputNome" maxLength="200"/>
		<@ww.textfield label="CNAE (Classificação Nacional de Atividades Econômicas)" name="empresa.cnae" cssStyle="width:100px;" onkeypress="return(somenteNumeros(event,'./-'));" maxLength="10"/>
		<@ww.textfield label="Grau de Risco" name="empresa.grauDeRisco" cssStyle="width:50px;" maxLength="10" onkeypress="return(somenteNumeros(event,''));"/>
		<@ww.textfield label="Email Remetente" name="empresa.emailRemetente" id="remetente" required="true" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="100"/>
		<@ww.textfield label="Email Resp. Setor Pessoal" name="empresa.emailRespSetorPessoal" id="respSetorPessoal" required="true" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="100"/>
		<@ww.textfield label="Email Resp. RH" name="empresa.emailRespRH" id="respRH" required="true" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="100"/>
		<@ww.textfield label="Representante Legal" name="empresa.representanteLegal" cssClass="inputNome" maxLength="100"/>
		<@ww.textfield label="NIT do Representante Legal" name="empresa.nitRepresentanteLegal" cssClass="inputNome" maxLength="100" onkeypress="return(somenteNumeros(event,'./-'));"/>
		<@ww.textfield label="Horário de Trabalho" name="empresa.horarioTrabalho"  cssClass="inputNome" maxLength="50"/>
		<@ww.textfield label="Máximo de Cargos por Candidato" name="empresa.maxCandidataCargo"  maxLength="3" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:30px;"/>
		<@ww.checkbox label="Exibir valor do salário na Solicitação de Realinhamento e na Solicitação de Pessoal" name="empresa.exibirSalario" id="exibirSalario" labelPosition="right" /><br>
		<@ww.checkbox label="Exibir dados do Ambiente nos Relatórios do SESMT" name="empresa.exibirDadosAmbiente" id="exibirDadosAmbiente" labelPosition="right" /><br>
		<@ww.textarea label="Mensagem a ser exibida no módulo externo" id="mensagemModuloExterno" name="empresa.mensagemModuloExterno" cssStyle="height:30px;"/>

		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<div style="float:right;"><img id="btnTransferir" border="0" title="Testar Conexão com AC" onclick="testaConexaoAC();" src="<@ww.url includeParams="none" value="/imgs/transferencia.gif"/>" style="cursor:pointer;"></div>

					<@ww.checkbox label="Integra com AC Pessoal" name="empresa.acIntegra" id="integra" labelPosition="right" onclick="habilitaDesabilitaCamposAC();" />
					<@ww.textfield label="Usuário AC" name="empresa.acUsuario" id="acUsuario" cssClass="inputNome" maxLength="50"/>
					<@ww.password label="Senha AC" name="empresa.acSenha" id="acSenha" cssStyle="width:100px;" maxLength="15" after="*Para manter a senha, deixe o campo em branco."/>
					<@ww.textfield label="URL WS" name="empresa.acUrlSoap" id="acUrlSoap" cssClass="inputNome" maxLength="100"/>
					<@ww.textfield label="URL WSDL" name="empresa.acUrlWsdl" id="acUrlWdsl" cssClass="inputNome" maxLength="100"/>
				</ul>
			</@ww.div>
		</li>
<a href="<@ww.url includeParams="none" value="/webservice"/>/RHService?wsdl">WebService</a>

		<@ww.hidden name="empresa.id" />
		<@ww.hidden name="empresa.logoUrl" />
		<@ww.hidden name="empresa.codigoAC" id="codigoAC" />
	<@ww.token/>
	</@ww.form>
	<#if empresa.logo?exists>
		<img src="<@ww.url includeParams="none" value="/geral/empresa/showLogo.action?empresa.id=${empresa.id}"/>" style="display:''" id="logoImg">
	</#if>

	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>

	<script type="text/javascript">
		habilitaDesabilitaCamposAC();
	</script>
</body>
</html>