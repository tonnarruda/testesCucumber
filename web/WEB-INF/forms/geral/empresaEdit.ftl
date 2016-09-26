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

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<style type="text/css">
		.divInfo { width: 850px !important;}
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		.divInfoInterna { margin: 20px 10px 10px 5px }
		.divInfoInterna h2 {margin: -9px 0px 0 0px;
					    padding: 3px 2px;
					    background: none;
					    color: #797979;
					    border-bottom: 2px solid #999999;
			         }
			         
		.divInfoInterna ul{padding: 10px 10px 10px 15px;
					    border-top: none;
					    background: #f1f1f1;
					    border-bottom-left-radius: 5px;
					    border-bottom-right-radius: 5px;
					}
		.divInfoInterna textarea{	height: 150px;
									width: 650px;
								}
								
		input[disabled] {
			background: #EFEFEF;
		}
	</style>
	
	<script type="text/javascript">
		
		var vencimentoCertificacaoPor = ${empresa.controlarVencimentoCertificacaoPor};
		var solicitaMotivoDesintegracao = true;
		$(function() {
			$('#verificaParentescoHelp').qtip({
				content: '<div style="text-align:justify">Nos cadastros de candidato e colaborador, após preencher os campos Nome do Cônjuge, Nome do Pai ou Nome da Mãe, é realizada uma checagem se há colaboradores com os nomes informados, exibindo um popup com a lista caso seja encontrado.</div>',
				style: { width: 400 }
			});

			$('#candidatoNaoAptoHelp').qtip({
				content: '<div style="text-align:justify">Para enviar os emails para os candidatos não aptos no encerramento da solicitação de pessoal. Essa opção deverá ser configurada no gerenciador de comunicação.</div>',
				style: { width: 400 }
			});

			$('#CodigoTruCursoHelp').qtip({
				content: '<div style="text-align:justify">Esta opção possibilita a exportação do curso/turma como ocorrência para o sistema Tráfego Urbano (TRU).</div>',
				style: { width: 400 }
			});
			
			$('#tooltipHelpOrdemDeServico').qtip({
				content: 'Os dados informados a seguir serão utilizados no cadastro da Ordem de Serviço.'
			});
			
			$('#nomeHomonimoEmpresa').change(function() {
				if ($(this).is(":checked"))
					$('#nomeHomonimo').attr("disabled", true);
				else
					$('#nomeHomonimo').removeAttr('disabled');
			});
			
			$('#integra').change(function() {
				var integrado = !$(this).is(":checked");
				$('#solPessoalReabrirSolicitacao').toggleDisabled(integrado);
			});
			$('#integra').change();
			
			$('#nomeHomonimo').change(function() {
				if ($(this).is(":checked"))
					$('#nomeHomonimoEmpresa').attr("disabled", true);
				else
					$('#nomeHomonimoEmpresa').removeAttr('disabled');
			});
			
			$('#nomeHomonimoEmpresa').change();
			$('#nomeHomonimo').change();
			
			$("#senhaPadrao").toggleDisabled(!$("#criarUsuarioAutomaticamente").is(":checked"));
			$("#criarUsuarioAutomaticamente").change(function(){
				$("#senhaPadrao").toggleDisabled(!$(this).is(":checked"));
			});
		});
		
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
				jAlert("Opção: Integra com o Fortes Pessoal não selecionada.");
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
				jAlert("A quantidade de caracteres do campo [Mensagem a ser exibida no módulo externo] não pode ser maior que 400");
				return false;
			}

			if($('#mensagemCartaoAniversariante').val().length > 300)
			{
				jAlert("A quantidade de caracteres do campo [Mensagem do Cartão de Aniversariantes] não pode ser maior que 300");
				return false;
			}
			
			if($('#mensagemCartaoAnoEmpresa').val().length > 300)
			{
				jAlert("A quantidade de caracteres do campo [Mensagem do Cartão de Ano de Empresa] não pode ser maior que 300");
				return false;
			}

			$('#senhaPadrao').css("background", "#FFF");
			if($("#criarUsuarioAutomaticamente").is(":checked") && $("#senhaPadrao").val() == "")
			{
				$('#senhaPadrao').css("background", "#FFEEC2");
				jAlert("A senha padrão para usuários criados automaticamente no cadastro do colaborador está vazia.");
				return false;
			}
						
			<@authz.authorize ifAllGranted="ROLE_INTEGRA_FORTES_PESSOAL">
				<#if empresa.acIntegra >
					if ( !$("#integra").is(":checked") && solicitaMotivoDesintegracao) { 
						$('#desintegraDialog').dialog({ 
							modal: true,
							width: 470,
							title: 'Integração com o Fortes Pessoal desmarcada',
						    buttons: [{
						    	text: "Finalizar", click: function() {
						    		solicitaMotivoDesintegracao = false;
						    		$("input[name=motivoDesintegracao]").val($("#motivoDesintegracao").val());
						    		enviaForm();
						    		$(this).dialog('close');
						    	}
						    }] 
						});
						return false;
					} 
				</#if>
			</@authz.authorize>
			
			if(document.getElementById('cnpj').value.length == 8)
			    return validaFormulario('form', new Array('nome', 'razao','uf','cidade', 'cnpj', 'remetente', 'respSetorPessoal', 'respRH', 'formulaTurnover'), new Array('remetente','respSetorPessoal'));
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
		
		<@ww.textfield label="Nome" name="empresa.nome" id="nome" required="true" cssClass="inputNome" maxLength="15" onkeypress = "return(naoInseririrCharacterComValor(event,'\\''));"/>
		<@ww.textfield label="Denominação Social" name="empresa.razaoSocial" id="razao" required="true" cssClass="inputNome" maxLength="60" onkeypress = "return(naoInseririrCharacterComValor(event,'\\''));"/>
		<@ww.textfield label="Endereço" name="empresa.endereco" cssClass="inputNome" maxLength="100" />
		<@ww.textfield label="DDD" name="empresa.ddd" id="inputDDD" liClass="liLeft" maxLength="2" cssStyle="width:25px;"/>
		<@ww.textfield label="Telefone" name="empresa.telefone" id="inputTelefone" cssClass="mascaraTelefone" maxLength="9" cssStyle="width:105px;" />
		<@ww.select label="Estado" name="empresa.uf.id" id="uf" list="ufs" required="true" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="-1" headerValue="" onchange="javascript:populaCidades()"/>
		<@ww.select label="Cidade" name="empresa.cidade.id" id="cidade" list="cidades" required="true" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" />
		<@ww.textfield label="Base CNPJ" name="empresa.cnpj" id="cnpj" required="true"  onkeypress="return(somenteNumeros(event,''));" cssStyle="width:100px;" maxLength="8"/>
		<@ww.textfield label="Atividade" name="empresa.atividade" cssClass="inputNome" maxLength="200"/>
		<@ww.textfield label="CNAE 01 (Classificação Nacional de Atividades Econômicas)" name="empresa.cnae" cssStyle="width:100px;" onkeypress="return(somenteNumeros(event,'./-'));" maxLength="10"/>
		<@ww.textfield label="CNAE 02 (Classificação Nacional de Atividades Econômicas)" name="empresa.cnae2" cssStyle="width:100px;" onkeypress="return(somenteNumeros(event,'./-'));" maxLength="10"/>
		<@ww.textfield label="Grau de Risco" name="empresa.grauDeRisco" cssStyle="width:50px;" maxLength="10" onkeypress="return(somenteNumeros(event,''));"/>
		<@ww.textfield label="Email Remetente" name="empresa.emailRemetente" id="remetente" required="true" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="100"/>
		<@ww.textfield label="Email Resp. Setor Pessoal" name="empresa.emailRespSetorPessoal" id="respSetorPessoal" required="true" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="100"/>
		<@ww.textfield label="Email Resp. RH" name="empresa.emailRespRH" id="respRH" required="true" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="100"/>
		<@ww.textfield label="Email Resp. pelo limite de Colaboradores por Cargo" name="empresa.emailRespLimiteContrato" id="respLimite" cssClass="mascaraEmail" cssStyle="width:340px;" maxLength="120"/>
		<@ww.textfield label="Representante Legal" name="empresa.representanteLegal" cssClass="inputNome" maxLength="100"/>
		<@ww.textfield label="NIT do Representante Legal" name="empresa.nitRepresentanteLegal" cssClass="inputNome" maxLength="100" onkeypress="return(somenteNumeros(event,'./-'));"/>
		<@ww.textfield label="Horário de Trabalho" name="empresa.horarioTrabalho"  cssClass="inputNome" maxLength="50"/>
		<@ww.textfield label="Máximo de Cargos por Candidato" name="empresa.maxCandidataCargo"  maxLength="3" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:30px;"/>
		<@ww.checkbox label="Exibir valor do salário na Solicitação de Realinhamento" name="empresa.exibirSalario" id="exibirSalario" labelPosition="right" /><br>
		
		<li>
			<@ww.checkbox label="Exibir campo código TRU (Tráfego Urbano) ao cadastrar curso e exibir a opção de exportar curso/turma como ocorrência para o TRU em Utilitários." id="CodigoTruCurso" name="empresa.codigoTruCurso" liClass="liLeft" labelPosition="left"/>
			<img id='CodigoTruCursoHelp' src="<@ww.url value='/imgs/help.gif'/>" width='16' height='16' style='margin-left: 65px;margin-top: -25px;vertical-align: top;' />
			<br />
		<li>
		<li>
			<label>Busca de possíveis parentes nos cadastros de candidatos e colaboradores:</label>
			<img id="verificaParentescoHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" />
			<br />
			<@ww.select theme="simple" name="empresa.verificaParentesco" id="verificaParentesco" list=r"#{'N':'Desabilitada','E':'Busca nesta empresa', 'T':'Busca em todas as empresas'}" disabled="${somenteLeitura}"  cssStyle="width: 447px;" />
		<li>

		<li>&nbsp;</li>
		
		<@ww.textarea label="Mensagem a ser exibida no módulo externo" id="mensagemModuloExterno" name="empresa.mensagemModuloExterno" cssStyle="height:30px;"/>
		
		<li>&nbsp;</li>
		
		<li>
			<@ww.div cssClass="divInfo">
				<h2>Avaliação de Desempenho</h2>
				<ul>
					<@ww.checkbox label="Apresentar performance de forma parcial ao responder avaliação de desempenho" name="empresa.mostrarPerformanceAvalDesempenho" liClass="liLeft" labelPosition="left"/>
				</ul>
			</@ww.div>
		</li>

		<li>&nbsp;</li>

		<li>
			<@ww.div cssClass="divInfo">
				<h2>Cálculo de Absenteísmo</h2>
				<ul>
					<@ww.checkbox label="Considerar sábado como dia útil" name="empresa.considerarSabadoNoAbsenteismo" liClass="liLeft" labelPosition="left"/>
					<@ww.checkbox label="Considerar domingo como dia útil" name="empresa.considerarDomingoNoAbsenteismo" liClass="liLeft" labelPosition="left"/>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
		
		<li>
			<@ww.div cssClass="divInfo">
				<h2>Cálculo de Turnover</h2>
				<ul>
					<@ww.checkbox label="Considerar para cálculo de Turnover apenas as admissões feitas através de solicitações com motivo marcado como \"Considerar para cálculo de turnover\" e apenas as demissões com motivo marcado como \"Considerar para cálculo de turnover\"." id="turnoverPorSolicitacao" name="empresa.turnoverPorSolicitacao" liClass="liLeft" labelPosition="left"/>
					<li>&nbsp;</li>
					<@ww.select label="Fórmula" name="empresa.formulaTurnover" id="formulaTurnover" list="opcoesFormulaTurnover" listKey="key" listValue="value" headerKey="" headerValue="Selecione"/>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
		
		<li>
			<@ww.div cssClass="divInfo">
				<h2>Solicitação de Pessoal</h2>
				<ul>
					<@ww.checkbox label="Exibir valor do salário" name="empresa.solPessoalExibirSalario" labelPosition="right" />
					<@ww.checkbox label="Exibir o campo Colaborador Substituído" name="empresa.solPessoalExibirColabSubstituido" labelPosition="right" />
					<@ww.checkbox label="Tornar obrigatório os dados complementares" name="empresa.solPessoalObrigarDadosComplementares" labelPosition="right" />
					<@ww.checkbox label="Reabrir solicitação após cancelar contratação ou promoção no Fortes Pessoal (Somente quando integrado)" name="empresa.solPessoalReabrirSolicitacao" id="solPessoalReabrirSolicitacao" labelPosition="right" />
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
		<li>
			<@ww.div cssClass="divInfo">
				<h2>T&D</h2>
				<ul>
					<@ww.select label="Controlar vencimento da certificação por" name="empresa.controlarVencimentoCertificacaoPor" id="controlaPeriodicidadePor" list=r"#{1:'Periodicidade do curso', 2:'Periodicidade da certificação'}" cssStyle="width: 410px;"/>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
		
		<li>
			<@ww.div cssClass="divInfo">
				<h2>Info. Funcionais</h2>
				<ul>
					<@ww.checkbox label="Solicitar confirmação de gestores ao desligar colaboradores. (Ao marcar esta opção, o usuário deverá sair e realizar novo login no sistema)" id="solicitarConfirmacaoDesligamento" name="empresa.solicitarConfirmacaoDesligamento" liClass="liLeft" labelPosition="left"/>
					<@ww.checkbox label="Notificar somente os períodos de acompanhamento de experiência configurados no cadastro do colaborador na aba 'Modelos de Avaliação'." id="notificarSomentePeriodosConfigurados" name="empresa.notificarSomenteDiasConfigurados" liClass="liLeft" labelPosition="left"/>
					<@ww.checkbox label="Criar usuário automaticamente ao cadastrar colaborador." id="criarUsuarioAutomaticamente" name="empresa.criarUsuarioAutomaticamente" liClass="liLeft" labelPosition="left"/>
					<li id="wwgrp_update_empresa_senhaPadrao" class="wwgrp" style="margin-left: 25px;">
						<div id="wwlbl_update_empresa_senhaPadrao" class="wwlbl" style="float:left; margin-right: 5px;">
							<label for="update_empresa_senhaPadrao" class="desc">Senha padrão: </label>
						</div>
						<div id="wwctrl_update_empresa_senhaPadrao" class="wwctrl">
							<input type="text" name="empresa.senhaPadrao" maxlength="25" value="${empresa.senhaPadrao}" id="senhaPadrao">
						</div>
					</li>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>

		<li>
			<@ww.div cssClass="divInfo">
				<h2>SESMT</h2>
				<ul>
					<@ww.select label="Controlar risco da Medição dos Riscos e do PPRA e LTCAT por" name="empresa.controlaRiscoPor" id="controlaRiscoPor" list=r"#{'A':'Ambiente','F':'Função'}" cssStyle="width: 410px;"/>
					<br>
					<@ww.checkbox label="Tornar obrigatório o preenchimento dos campos de Ambiente e Função para o histórico do colaborador e solicitação de pessoal" name="empresa.obrigarAmbienteFuncao" id="obrigarAmbienteFuncao" labelPosition="right" /><br>
					<@ww.checkbox label="Exibir logo da empresa ao emitir PPRA e LTCAT" name="empresa.exibirLogoEmpresaPpraLtcat" id="exibirLogoEmpresaPpraLtcat" labelPosition="right" />
					
					<@ww.div cssClass="divInfoInterna">
						<h2>Ordem de Serviço <img id="tooltipHelpOrdemDeServico" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-bottom: -3px;" /> </h2>	
							<ul>
								<@ww.textarea label="Procedimentos em caso de acidente de trabalho" name="empresa.procedimentoEmCasoDeAcidente" id="procedimentoEmCasoDeAcidente"/>
								<@ww.textarea label="Termo de Responsabilidade" name="empresa.termoDeResponsabilidade" id="termoDeResponsabilidade"/>
							</ul>
					</@ww.div>
				</ul>
			</@ww.div>
		</li>

		<li>&nbsp;</li>

		<li>
			<@ww.div cssClass="divInfo">
				<h2>Texto para email de candidatos não aptos &nbsp;<img id="candidatoNaoAptoHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -5px" /></h2>
				<ul>
					<@ww.textarea name="empresa.mailNaoAptos" id="mailNaoAptos" size="40"/>
				</ul>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
		
		<li>
			<@ww.div cssClass="divInfo">
				<h2>Cartões</h2>
				<@ww.div cssClass="divInfoInterna">
					<h2>Aniversário</h2>	
					<ul>
						<@ww.file label="Imagem (400px x 570px)" name="cartaoAniversario.file" id="imgCartaoAniversariante" liClass="liLeft"/>
						<li>
							<@ww.div cssStyle="width:450px; height:55px; text-align:right;">
								<#if empresa.id?exists && cartaoAniversario.imgUrl?exists && cartaoAniversario.imgUrl != "">
									<a href="previewCartao.action?cartao.id=${cartaoAniversario.id}" ><img title="Visualizar cartão dos aniversariantes." border="0" width="55" height="55" src="<@ww.url includeParams="none" value="/geral/empresa/showImgCartao.action?cartao.imgUrl=${cartaoAniversario.imgUrl}"/>"></a>
								</#if>
							</@ww.div>
						</li>
						<@ww.textarea label="Mensagem (Utilize a expressão #NOMECOLABORADOR#, caso queira exibir o nome do Aniversariante)" id="mensagemCartaoAniversariante" name="cartaoAniversario.mensagem" cssStyle="height:40px;"/>
					</ul>
				</@ww.div>
				
				<@ww.div cssClass="divInfoInterna">
					<h2>Ano de Empresa</h2>	
					<ul>
						<@ww.file label="Imagem (400px x 570px)" name="cartaoAnoDeEmpresa.file" id="imgCartaoAnoDeEmpresa" liClass="liLeft"/>
						<li>
							<@ww.div cssStyle="width:450px; height:55px; text-align:right;">
								<#if empresa.id?exists && cartaoAnoDeEmpresa.imgUrl?exists && cartaoAnoDeEmpresa.imgUrl != "">
									<a href="previewCartao.action?cartao.id=${cartaoAnoDeEmpresa.id}" ><img title="Visualizar cartão de parabenizar por ano de empresa." border="0" width="55" height="55" src="<@ww.url includeParams="none" value="/geral/empresa/showImgCartao.action?cartao.imgUrl=${cartaoAnoDeEmpresa.imgUrl}"/>"></a>
								</#if>
							</@ww.div>
						</li>
						<@ww.textarea label="Mensagem (Utilize a expressão #NOMECOLABORADOR#, caso queira exibir o nome do colaborador que completa ano de empresa)" id="mensagemCartaoAnoEmpresa" name="cartaoAnoDeEmpresa.mensagem" cssStyle="height:40px;"/>
					</ul>
				</@ww.div>
			</@ww.div>
		</li>
		
		<li>&nbsp;</li>
				
		<@authz.authorize ifAllGranted="ROLE_INTEGRA_FORTES_PESSOAL">
			<li>
				<@ww.div cssClass="divInfo">
					<h2>Integração com o Fortes Pessoal</h2>
					<ul>
						<div style="float:right;"><img id="btnTransferir" border="0" title="Testar Conexão com AC" onclick="testaConexaoAC();" src="<@ww.url includeParams="none" value="/imgs/transferencia.gif"/>" style="cursor:pointer;"></div>
						<@ww.checkbox label="Integra com o Fortes Pessoal" name="empresa.acIntegra" id="integra" labelPosition="right"/>
						<@ww.select label="Grupo AC" name="empresa.grupoAC" id="grupoAC" list="grupoACs" listKey="codigo" listValue="codigoDescricao" headerKey="" headerValue="Selecione..."/>
					</ul>
				</@ww.div>
			</li>
			
			<input type="hidden" name="motivoDesintegracao"/>
			<div id="desintegraDialog" style="display: none;">
				<label>
					Com a finalidade de melhorar a integração com o Fortes Pessoal, um e-mail será enviado para o suporte RH reportando que a integração com o Fortes Pessoal foi desmarcada.
					<br><br>
					Caso deseje, nos informe o motivo pelo qual desabilitou a integração da empresa com o Fortes Pessoal:
				</label>
				<textarea id="motivoDesintegracao" style="height:40px; width: 435px !important;"></textarea>
			</div>
		</@authz.authorize>
		<@authz.authorize ifNotGranted="ROLE_INTEGRA_FORTES_PESSOAL">
			<@ww.hidden name="empresa.grupoAC" />
		</@authz.authorize>

		<@ww.hidden name="empresa.id" />
		<@ww.hidden name="empresa.logoUrl" />
		<@ww.hidden name="empresa.logoCertificacaoUrl" />
		<@ww.hidden name="empresa.codigoAC" id="codigoAC" />
		
		<@ww.hidden name="cartaoAniversario.empresa.id" />
		<@ww.hidden name="cartaoAniversario.tipoCartao" value="A" />
		<@ww.hidden name="cartaoAniversario.id" />
		<@ww.hidden name="cartaoAniversario.imgUrl" />
		
		<@ww.hidden name="cartaoAnoDeEmpresa.id" />
		<@ww.hidden name="cartaoAnoDeEmpresa.tipoCartao" value="E" />
		<@ww.hidden name="cartaoAnoDeEmpresa.imgUrl"/>
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