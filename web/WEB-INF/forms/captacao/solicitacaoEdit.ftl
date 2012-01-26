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

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>


	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
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
			if(validaFormulario('form', new Array('descricao','horarioComercial','estabelecimento','area','dataSol','faixa','quantidade','motivoSolicitacaoId'), new Array ('dataSol')))
			{
				$('#gravar').attr('disabled', true);
				return true;
			} 

			return false;
		}
		
		$(function() {
			<@authz.authorize ifAllGranted="ROLE_LIBERA_SOLICITACAO">
				$('#statusSolicitcao').removeAttr('disabled');
				$('#obsAprova').removeAttr('disabled');
			</@authz.authorize>
		});
	</script>

	<#assign validarCampos="return validacaoFormulario(); "/>
</head>
<body>

	<#include "../ftl/mascarasImports.ftl" />
	
	<@ww.form name="form" id="form" action="${formAction}" validate="true" onsubmit="${validarCampos}" method="POST">
		<@ww.datepicker label="Data" name="solicitacao.data" required="true" id="dataSol" value="${DataSolicitacao}" cssClass="mascaraData"/>
		
		<@ww.textfield label="Descrição" name="solicitacao.descricao" id="descricao" cssClass="inputNome" maxlength="67" required="true"/>
		<@ww.textfield label="Horário comercial" name="solicitacao.horarioComercial" id="horarioComercial" cssClass="inputNome" maxlength="20" required="true"/>

		<#if !clone && somenteLeitura && solicitacao.estabelecimento?exists && solicitacao.estabelecimento.id?exists>
			<@ww.textfield readonly="true" label="Estabelecimento" name="solicitacao.estabelecimento.nome" id="estabelecimento" required="true" cssStyle="width: 347px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.estabelecimento.id"/>
		<#else>
			<@ww.select label="Estabelecimento" name="solicitacao.estabelecimento.id" id="estabelecimento" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." required="true" cssStyle="width: 347px;"/>
		</#if>

		<#if !clone && somenteLeitura && solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.id?exists>
			<@ww.textfield readonly="true" label="Área" name="solicitacao.areaOrganizacional.nome" id="area" required="true" cssStyle="width: 347px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.areaOrganizacional.id"/>
		<#else>
			<@ww.select label="Área Organizacional" name="solicitacao.areaOrganizacional.id" id="area" list="areas" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." required="true" cssStyle="width: 347px;" onchange="javascript:populaEmails(this.value);" />
		</#if>

		<#if !clone && somenteLeitura && solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.id?exists>
			<@ww.textfield readonly="true" label="Cargo/Faixa" name="solicitacao.faixaSalarial.descricao" id="faixa" required="true" cssStyle="width: 347px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.faixaSalarial.id"/>
		<#else>
			<@ww.select label="Cargo/Faixa" name="solicitacao.faixaSalarial.id" onchange="javascript:calculaSalario();" list="faixaSalarials" id="faixa" listKey="id" headerKey="" headerValue="Selecione..." listValue="descricao" required="true" cssStyle="width: 347px;"/>
		</#if>
		
		<#if !clone && somenteLeitura && solicitacao.avaliacao?exists && solicitacao.avaliacao.id?exists>
			<@ww.textfield readonly="true" label="Modelo Avaliação do Candidato" name="solicitacao.avaliacao.titulo" id="avaliacaoId" cssStyle="width: 347px;background: #EBEBEB;"/>
			<@ww.hidden name="solicitacao.avaliacao.id"/>
		<#else>
			<@ww.select label="Modelo Avaliação do Candidato" name="solicitacao.avaliacao.id" id="avaliacaoId" list="avaliacoes" cssStyle="width: 250px;" listKey="id" listValue="titulo"  headerKey="" headerValue="" />
		</#if>
		
		<@ww.textfield label="Nº Vagas" id="quantidade" name="solicitacao.quantidade" onkeypress = "return(somenteNumeros(event,''));" required="true" cssStyle="width:35px; text-align:right;" maxLength="4" />
		<@ww.select  id="motivoSolicitacaoId" label="Motivo da Solicitação" name="solicitacao.motivoSolicitacao.id" list="motivoSolicitacaos"  required="true" cssStyle="width: 250px;" listKey="id" listValue="descricao"  headerKey="" headerValue="" />
		
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
						Atulizado por: ${nomeLiberador} 
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