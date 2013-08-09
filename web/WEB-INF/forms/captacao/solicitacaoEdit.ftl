<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<#if solicitacao.id?exists>
	<title>Editar Solicitação de Pessoal</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign DataSolicitacao = "${solicitacao.dataFormatada}"/>
<#else>
	<title>Inserir Solicitação de Pessoal</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	<#assign DataSolicitacao = "${dataDoDia}"/>
</#if>

<#if visualizar?exists && cargo?exists>
	<#assign formAction = formAction + "?visualizar=${visualizar}&cargo.id=${cargo.id}"/>
</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery-ui/redmond.css"/>');
	</style>

	<#assign empresaId><@authz.authentication operation="empresaId"/></#assign>
	<script type="text/javascript">
		function populaEmails(id)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getEmailsResponsaveis(createListEmails, id, ${empresaId});
		}
	
		function createListEmails(data)
		{
			addChecks("emailsCheck", data);
		}
		
		function populaCidades()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			if(document.getElementById('estado').value == "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DWRUtil.removeAllOptions("cidade");
			}else{
				CidadeDWR.getCidades(createListCidades, document.getElementById('estado').value);
			}
		}
	
		function createListCidades(data)
		{
			DWRUtil.removeAllOptions("cidade");
			DWRUtil.addOptions("cidade", data);
		}
	
		function populaBairros()
		{
			if(document.getElementById('cidade').value == "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DWRUtil.removeAllOptions("bairrosCheck");
			}else{
				BairroDWR.getBairrosMap(createListBairros, document.getElementById('cidade').value);
			}
		}
	
		function createListBairros(data)
		{
			addChecks("bairrosCheck",data)
		}
	
		var bairrosMarcados = new Array();
	
		var retorno = new Array();
	
		function calculaSalario()
		{
			tipoSalario     = 1;// CARGO
			faixaSalarialId = document.getElementById("faixa").value;
			IndiceId        = 0;
			quantidade      = 0;
			salario         = 0;
	
			ReajusteDWR.calculaSalario(setSalarioCalculado, tipoSalario, faixaSalarialId, IndiceId, quantidade, salario);
		}
	
		function setSalarioCalculado(data)
		{
			document.getElementById("remuneracao").value = data;
		}
	
		function compl()
		{
			var compl = document.getElementById("complementares");
			var img = document.getElementById("imgCompl");
			if(compl.style.display == "none")
			{
				compl.style.display = "";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_up.gif"/>"
			}
			else
			{
				compl.style.display = "none";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"
			}
		}
		
		function validacaoFormulario()
		{
			var camposObg = new Array('descricao','horarioComercial','estabelecimento','area','dataSol','faixa','quantidade','motivoSolicitacaoId');
		
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<#if obrigarAmbienteFuncao>
					camposObg.push('ambiente','funcao');
				</#if>
			</@authz.authorize>
		
			if(validaFormulario('form', camposObg, new Array ('dataSol')))
			{
				$('#gravar').attr('disabled', true);
				
				$('.colaboradorSubstituido').each(function() {
					if ($(this).val() == '')
						$(this).parent().remove();
				});
				
				return true;
			} 

			return false;
		}
		
		function populaFuncao(faixaId)
		{
			if(faixaId != "null" && faixaId != "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				FuncaoDWR.getFuncaoByFaixaSalarial(createListFuncao, faixaId);
			}
		}

		function createListFuncao(data)
		{
			DWRUtil.removeAllOptions("funcao");
			DWRUtil.addOptions("funcao", data);
		}
		
		function populaAmbiente(estabelecimentoId)
		{
			if(estabelecimentoId != "null")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AmbienteDWR.getAmbienteByEstabelecimento(createListAmbiente, estabelecimentoId);
			}
		}

		function createListAmbiente(data)
		{
			DWRUtil.removeAllOptions("ambiente");
			DWRUtil.addOptions("ambiente", data);
		}
		
		var contador = 0;
		function adicionarCampoColaboradorSubstituto(nome) 
		{
			if (!nome) nome = '';
			var campo = "<li>";
			campo += "<input type='text' id='colaboradorSubstituido" + contador + "' name='colaboradoresSusbstituidos' class='colaboradorSubstituido' size='40' value='" + nome + "'  maxLength='100' cssClass='inputNome' liClass='liLeft' style='margin-top: 5px;'/>";
			campo += "<img title='Remover' src='<@ww.url includeParams="none" value="/imgs/delete.gif"/>' onclick='javascript:$(this).parent().remove();' style='cursor:pointer;'/>";
			campo += "</li>";
			$('ul#camposSubstituto').append(campo);
			inicializaAutocompleteColabSubstituto(contador);
			contador++;
		}
		
		function inicializaAutocompleteColabSubstituto(contador)
		{
			var colabSub = "colaboradorSubstituido" + contador; 
			$('#'+ colabSub).autocomplete({
				minLength: 3,
				source: function( request, response ) {
					DWRUtil.useLoadingMessage('Carregando...');
					ColaboradorDWR.findByNome(request.term, ${empresaId}, function(dados) {
						response( dados );
					});
				},
				focus: function( event, ui ) {
					return false;
				},
				select: function( event, ui ) {
					DWRUtil.useLoadingMessage('Carregando...');
					ComissaoDWR.dataEstabilidade(ui.item.value, 
												function(data) 
												{
													if (data)
													{
														var msg = 'O Colaborador '+ ui.item.nome +', faz parte <br>da CIPA e possui estabilidade até o dia ' + data + '.' +
																	'<br /> Deseja realmente substituí-lo?';

														$('<div>'+ msg +'</div>').dialog({	title: 'Alerta!',
																							modal: true, 
																							height: 150,
																							width: 500,
																								buttons: [ 	{ text: "Sim", click: function() { $('#'+ colabSub).val(ui.item.nome); $(this).dialog("close"); } },
																							    		{ text: "Não", click: function() { $('#'+ colabSub).val(""); $(this).dialog("close"); } } ] 
																						});
														
													}
													else
													{
														$('#'+ colabSub).val(ui.item.nome);
													}
												});
					return false;
				}
			}).data( "autocomplete" )._renderItem = function( ul, item ) {
				return $( "<li></li>" )
					.data( "item.autocomplete", item )
					.append( "<a>" + item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex( $('#'+ colabSub).val() ) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>" ) + "</a>" )
					.appendTo( ul );
			};		
		}
		
		$(function() {
			<@authz.authorize ifAllGranted="ROLE_LIBERA_SOLICITACAO">
				$('#statusSolicitcao').removeAttr('disabled');
				$('#obsAprova').removeAttr('disabled');
			</@authz.authorize>
			
			<#if exibeColaboradorSubstituido>
				<#list colaboradoresSusbstituidos as colaboradorSusbstituido>
					adicionarCampoColaboradorSubstituto("${colaboradorSusbstituido}");
				</#list>
			
				if ($('.colaboradorSubstituido').size() == 0)
					adicionarCampoColaboradorSubstituto();
		
				$('#tooltipHelp').qtip({
					content: 'Digite ao lado o nome do colaborador que será substituído, para que o sistema valide se o mesmo tem estabilidade ou não na CIPA.'
				});
			</#if>
		});
	</script>

	<#assign validarCampos="return validacaoFormulario(); "/>
</head>
<body>
	<@ww.actionmessage/>
	<@ww.actionerror/>

	<#include "../ftl/mascarasImports.ftl" />
	
	<@ww.form name="form" id="form" action="${formAction}" validate="true" onsubmit="${validarCampos}" method="POST">
		<@ww.datepicker label="Data" name="solicitacao.data" required="true" id="dataSol" value="${DataSolicitacao}" cssClass="mascaraData"/>
		
		<@ww.textfield label="Descrição" name="solicitacao.descricao" id="descricao" cssClass="inputNome" maxlength="67" required="true"/>
		<@ww.textfield label="Horário comercial" name="solicitacao.horarioComercial" id="horarioComercial" cssClass="inputNome" maxlength="20" required="true"/>

		<#if !clone && somenteLeitura && solicitacao.estabelecimento?exists && solicitacao.estabelecimento.id?exists>
			<@ww.textfield readonly="true" label="Estabelecimento" name="solicitacao.estabelecimento.nome" id="estabelecimento" cssStyle="width: 347px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.estabelecimento.id"/>
		<#else>
			<#assign funcaoEstabelecimento="populaAmbiente(this.value);"/>
			<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
					<#assign funcaoEstabelecimento=""/>
			</@authz.authorize>
			<@ww.select label="Estabelecimento" name="solicitacao.estabelecimento.id" id="estabelecimento" list="estabelecimentos" onchange="${funcaoEstabelecimento}" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." required="true" cssStyle="width: 347px;"/>
		</#if>

		<#if !clone && somenteLeitura && solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.id?exists>
			<@ww.textfield readonly="true" label="Área" name="solicitacao.areaOrganizacional.nome" id="area" cssStyle="width: 347px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.areaOrganizacional.id"/>
		<#else>
			<#if solicitacao.id?exists>
				<#assign funcaoPopulaEmails=""/>
			<#else>
				<#assign funcaoPopulaEmails="javascript:populaEmails(this.value);"/>
			</#if>
			<@ww.select label="Área Organizacional" name="solicitacao.areaOrganizacional.id" id="area" list="areas" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." required="true" cssStyle="width: 347px;" onchange="${funcaoPopulaEmails}" />
		</#if>

		<#if !clone && somenteLeitura && solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.id?exists>
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<@ww.textfield readonly="true" label="Ambiente" name="solicitacao.ambiente.nome" id="ambiente" cssStyle="width: 347px;background: #EBEBEB;"/>
				<@ww.textfield readonly="true" label="Cargo/Faixa" name="solicitacao.faixaSalarial.descricao" id="faixa" cssStyle="width: 347px;background: #EBEBEB;"/>
				<@ww.textfield readonly="true" label="Função" name="solicitacao.funcao.nome" id="funcao" cssStyle="width: 347px;background: #EBEBEB;"/>
			</@authz.authorize>
			<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
				<@ww.textfield readonly="true" label="Cargo/Faixa" name="solicitacao.faixaSalarial.descricao" id="faixa" cssStyle="width: 347px;background: #EBEBEB;"/>
				<@ww.hidden name="solicitacao.ambiente.id" id="ambiente"/>
				<@ww.hidden name="solicitacao.funcao.id" id="funcao"/>
			</@authz.authorize>
		<#else>
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<@ww.select label="Ambiente" name="solicitacao.ambiente.id" id="ambiente" required="${obrigarAmbienteFuncao?string}" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhum" cssStyle="width: 347px;"/>
				<@ww.select label="Cargo/Faixa" name="solicitacao.faixaSalarial.id" onchange="javascript:calculaSalario();populaFuncao(this.value);" list="faixaSalarials" id="faixa" listKey="id" headerKey="" headerValue="Selecione..." listValue="descricao" required="true" cssStyle="width: 347px;"/>
				<@ww.select label="Função" name="solicitacao.funcao.id" id="funcao" required="${obrigarAmbienteFuncao?string}" list="funcoes" listKey="id" listValue="nome" headerValue="Nenhuma" headerKey="" cssStyle="width: 347px;"/>
			</@authz.authorize>
			<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
				<@ww.select label="Cargo/Faixa" name="solicitacao.faixaSalarial.id" onchange="javascript:calculaSalario();" list="faixaSalarials" id="faixa" listKey="id" headerKey="" headerValue="Selecione..." listValue="descricao" required="true" cssStyle="width: 347px;"/>
				<@ww.hidden name="solicitacao.ambiente.id" id="ambiente"/>
				<@ww.hidden name="solicitacao.funcao.id" id="funcao"/>
			</@authz.authorize>
		</#if>
		
		<#if !clone && somenteLeitura && (qtdAvaliacoesRespondidas > 0)>
			<@frt.checkListBox name="avaliacoesCheck" id="avaliacoesCheck" label="Avaliações" list="avaliacoesCheckList" readonly=true />
		<#else>
			<@frt.checkListBox name="avaliacoesCheck" id="avaliacoesCheck" label="Avaliações" list="avaliacoesCheckList" readonly=false />
		</#if>
		
		<@ww.textfield label="Nº Vagas" id="quantidade" name="solicitacao.quantidade" onkeypress = "return(somenteNumeros(event,''));" required="true" cssStyle="width:35px; text-align:right;" maxLength="4" />
		<@ww.select  id="motivoSolicitacaoId" label="Motivo da Solicitação" name="solicitacao.motivoSolicitacao.id" list="motivoSolicitacaos"  required="true" cssStyle="width: 250px;" listKey="id" listValue="descricao"  headerKey="" headerValue="" />
		
		<#if exibeColaboradorSubstituido>
			<div>
				<label>Colaborador Substituído:</label>
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-top:20px;" />
				<ul id="camposSubstituto"></ul>
				<a href="javascript:;" onclick="javascript:adicionarCampoColaboradorSubstituto();" style="text-decoration: none;">
					<img src='<@ww.url includeParams="none" value="/imgs/mais.gif"/>'/> 
					Inserir mais um colaborador substituído
				</a>
				<br clear="all"/>
			</div>
			<br />
		</#if>
		
		<#if !solicitacao.id?exists>
			<@frt.checkListBox name="emailsCheck" id="emailsCheck" label="Comunicar responsáveis da Área Organizacional" list="emailsCheckList" />
		</#if>
		<br/>
		
		<li>
			<a href="javascript:compl();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"> Dados complementares</a>
		</li>
		<li>
			<@ww.div id="complementares" cssStyle="display:none;" cssClass="divInfo">
				<ul>
					<@ww.select label="Vínculo" name="solicitacao.vinculo" list=r"vinculos" cssStyle="width: 85px;"/>
					<#if exibeSalario>
						<@ww.textfield label="Remuneração (R$)" id="remuneracao" name="solicitacao.remuneracao" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" />
					<#else>
						<@ww.hidden name="solicitacao.remuneracao"  id="remuneracao"/>
					</#if>
					<@ww.select label="Escolaridade mínima" name="solicitacao.escolaridade" list="escolaridades" cssStyle="width: 256px;"  headerKey="" headerValue=""/>
					<@ww.select label="Sexo" name="solicitacao.sexo" list="sexos" liClass="liLeft"/>
					
					<li>
					<span>Idade Preferencial:</span>
					</li>
					<@ww.textfield name="solicitacao.idadeMinima" id="dataPrevIni" liClass="liLeft" cssStyle="width: 30px;text-align: right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
					<@ww.label value="a" liClass="liLeft"/>
					<@ww.textfield name="solicitacao.idadeMaxima" id="dataPrevFim" cssStyle="width: 30px;text-align: right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft"/>
					<@ww.label value="anos"/><div style="clear: both"></div>
					
					<@ww.select label="Estado"  id="estado" name="estado.id" list="estados" listKey="id" listValue="sigla" liClass="liLeft" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
					<@ww.select label="Cidade"  id="cidade" name="solicitacao.cidade.id" list="cidades" listKey="id" listValue="nome" cssStyle="width: 250px;" headerKey="" headerValue="" onchange="javascript:populaBairros()" />
					<@frt.checkListBox name="bairrosCheck" id="bairrosCheck" label="Bairros" list="bairrosCheckList" width="695"/>
					<@ww.textarea label="Informações Complementares" name="solicitacao.infoComplementares" cssStyle="width:445px;" cssStyle="width: 695px;"/>
					
					<@ww.hidden name="solicitacao.liberador.id"  />
					<@ww.div id="divcomplementar"/>
				</ul>
			</@ww.div>
		</li>
		<br/>

		<li>
			<@ww.div cssClass="divInfo">
				<ul>
					<#if nomeLiberador?exists>
						Atualizado por: ${nomeLiberador} 
						<br><br>					
					</#if>	
					Status da solicitação (Inicia o processo de seleção de pessoal)<br><br>
					<@ww.select  name="solicitacao.status"  list="status" id="statusSolicitcao" disabled="true" />
					<@ww.textarea label="Observação" name="solicitacao.observacaoLiberador" id="obsAprova" disabled="true" />
				</ul>
			</@ww.div>
		</li>

		<@ww.hidden name="solicitacao.id" />
		<@ww.hidden name="solicitacao.solicitante.id" />
		<@ww.hidden name="solicitacao.empresa.id" />
		
		<@authz.authorize !ifAllGranted="ROLE_LIBERA_SOLICITACAO">
			<@ww.hidden name="solicitacao.status" />
			<@ww.hidden name="solicitacao.observacaoLiberador" />
		</@authz.authorize>

	<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}" id="gravar">
		</button>
		<#if visualizar?exists && cargo?exists>
			<button onclick="window.location='list.action?visualizar=${visualizar}&cargo.id=${cargo.id}'" class="btnCancelar" accesskey="V">
		<#else>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V">
		</#if>
		</button>
	</div>
</body>
</html>