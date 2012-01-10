<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<#if empresa.id?exists>
		<title>Editar Empresa</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign somenteLeitura="false" />
	<#else>
		<title>Inserir Empresa</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign somenteLeitura="true" />
	</#if>


	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		function testaConexaoAC()
		{
			if(document.getElementById("integra").checked)
			{
				var grupoAC = document.getElementById("grupoAC").value;

				document.getElementById("btnTransferir").src="<@ww.url includeParams="none" value="/imgs/conectando.gif"/>";
				UtilDWR.getToken(apresenta_Msg, grupoAC);
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

		function enviaForm()
		{
			if($('#mensagemModuloExterno').val().length > 400)
			{
				jAlert("A quantidade de carácteres do campo [Mensagem a ser exibida no módulo externo] não pode ser maior que 400");
				return false;
			}

			if($('#mensagemCartaoAniversariante').val().length > 300)
			{
				jAlert("A quantidade de carácteres do campo [Mensagem do Cartão de Aniversariantes] não pode ser maior que 300");
				return false;
			}
						
			if(document.getElementById('cnpj').value.length == 8)
		 		return validaFormulario('form', new Array('nome', 'razao','uf','cidade', 'cnpj', 'remetente', 'respSetorPessoal', 'respRH'), new Array('remetente','respSetorPessoal','respRH'));
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
		<br>
		<@authz.authorize ifAllGranted="ROLE_CERTIFICADO_CURSO">
			<@ww.file label="Logo dos Certificados(110px x 110px)" name="logoCert" id="logoCert" liClass="liLeft"/>
			<li>
				<@ww.div cssStyle="width:450px; height:55px; text-align:right;">
					<ul>
						<#if empresa.id?exists && empresa.logoCertificadoUrl?exists && empresa.logoCertificadoUrl != "">
							<img border="0" width="55" height="55" src="<@ww.url includeParams="none" value="/geral/empresa/showLogoCertificado.action?empresa.logoCertificadoUrl=${empresa.logoCertificadoUrl}"/>">
						</#if>
					</ul>
				</@ww.div>
			</li>
			<br>
		</@authz.authorize>
		
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
		<@ww.textfield label="Email Resp. pelo limite de Colaboradores por Cargo" name="empresa.emailRespLimiteContrato" id="respLimite" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="120"/>
		<@ww.textfield label="Representante Legal" name="empresa.representanteLegal" cssClass="inputNome" maxLength="100"/>
		<@ww.textfield label="NIT do Representante Legal" name="empresa.nitRepresentanteLegal" cssClass="inputNome" maxLength="100" onkeypress="return(somenteNumeros(event,'./-'));"/>
		<@ww.textfield label="Horário de Trabalho" name="empresa.horarioTrabalho"  cssClass="inputNome" maxLength="50"/>
		
		<@ww.select label="Exame ASO" name="empresa.exame.id" id="exame" list="exames" disabled="${somenteLeitura}"  cssStyle="width: 300px;" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." />
		
		<@ww.textfield label="Máximo de Cargos por Candidato" name="empresa.maxCandidataCargo"  maxLength="3" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:30px;"/>
		<@ww.checkbox label="Exibir valor do salário na Solicitação de Realinhamento e na Solicitação de Pessoal" name="empresa.exibirSalario" id="exibirSalario" labelPosition="right" /><br>
		<@ww.checkbox label="Exibir dados do Ambiente nos Relatórios do SESMT" name="empresa.exibirDadosAmbiente" id="exibirDadosAmbiente" labelPosition="right" /><br>
		<@ww.checkbox label="Tornar obrigatório o preenchimento dos campos de Ambiente e Função para o colaborador" name="empresa.obrigarAmbienteFuncaoColaborador" id="obrigarAmbienteFuncaoColaborador" labelPosition="right" /><br>
		<@ww.checkbox label="Considerar para cálculo de Turnover apenas os colaboradores contratados através de uma solicitação cujo motivo esteja marcado como: Considerar para calculo de Turnover" id="turnoverPorSolicitacao" name="empresa.turnoverPorSolicitacao" liClass="liLeft" labelPosition="left"/><br>
		<@ww.textarea label="Mensagem a ser exibida no módulo externo" id="mensagemModuloExterno" name="empresa.mensagemModuloExterno" cssStyle="height:30px;"/>
		
		<li>&nbsp;</li>

		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<@ww.checkbox label="Enviar emails para candidatos não aptos" id="emailCandidatoNaoApto" name="empresa.emailCandidatoNaoApto" liClass="liLeft" labelPosition="left"/><br>
					<@ww.textarea label="Texto para email de candidatos não aptos" name="empresa.mailNaoAptos" id="mailNaoAptos" size="40"/>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
				
		<li>
			<@ww.div cssClass="divInfo">
				Configurações do Cartão dos Aniversariantes
				<br><br>
				<ul>
					<@ww.file label="Imagem (400px x 570px)" name="imgCartaoAniversariante" id="imgCartaoAniversariante" liClass="liLeft"/>
					<li>
						<@ww.div cssStyle="width:450px; height:55px; text-align:right;">
							<#if empresa.id?exists && empresa.imgAniversarianteUrl?exists && empresa.imgAniversarianteUrl != "">
								<ul>
									<a href="cartaoAniversariante.action?empresa.id=${empresa.id}" ><img title="Visualizar Cartão dos aniversariantes." border="0" width="55" height="55" src="<@ww.url includeParams="none" value="/geral/empresa/showImgAniversariante.action?empresa.imgAniversarianteUrl=${empresa.imgAniversarianteUrl}"/>"></a>
								</ul>
							</#if>
						</@ww.div>
					</li>
					<@ww.textarea label="Mensagem (Utilize a expressão #NOMECOLABORADOR#, caso queira exibir o nome do Aniversariante)" id="mensagemCartaoAniversariante" name="empresa.mensagemCartaoAniversariante" cssStyle="height:40px;"/>
					<@ww.checkbox label="Enviar email com Cartão?" name="empresa.enviarEmailAniversariante" id="enviarEmailAniversariante" labelPosition="right" /><br>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
				
		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<div style="float:right;"><img id="btnTransferir" border="0" title="Testar Conexão com AC" onclick="testaConexaoAC();" src="<@ww.url includeParams="none" value="/imgs/transferencia.gif"/>" style="cursor:pointer;"></div>
					<@ww.checkbox label="Integra com AC Pessoal" name="empresa.acIntegra" id="integra" labelPosition="right"  />
					<@ww.select label="Grupo AC" name="empresa.grupoAC" id="grupoAC" list="grupoACs" listKey="codigo" listValue="codigoDescricao" headerKey="" headerValue="Selecione..."/>
				</ul>
			</@ww.div>
		</li>
		

		<@ww.hidden name="empresa.id" />
		<@ww.hidden name="empresa.logoUrl" />
		<@ww.hidden name="empresa.logoCertificacaoUrl" />
		<@ww.hidden name="empresa.imgAniversarianteUrl" />
		<@ww.hidden name="empresa.codigoAC" id="codigoAC" />
	<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>