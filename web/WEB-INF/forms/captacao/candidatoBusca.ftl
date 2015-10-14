<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		
		#menuBusca a.ativaAvancada{ border-bottom: 2px solid #5292C0; }
	</style>

	<#if solicitacao?exists && solicitacao.id?exists>
		<title>Inserir Candidatos na Solicitação</title>
	<#else>
		<title>Triagem de currículos</title>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ConhecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<#include "../ftl/mascarasImports.ftl" />

	<style type="text/css">
		.fieldsetClass {
			background-color: #EEEEEE;
			width: 430px;
		}
	</style>
	
	<script type="text/javascript">
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		$(function(){
			$('#tooltipPalavraChave').qtip({
				content: 'Este filtro irá incidir sobre os campos "Curso" e "Outros Cursos" contidos na aba "FORMAÇÃO ESCOLAR" e nos campos "Outro Cargo" e "Informações Adicionais" contidos na aba "EXPERIÊNCIAS".'
			});
			$('#tooltipExperienciaTrabalho').qtip({
				content: 'A quantidade de meses deve ser preenchida quando for selecionado pelo menos um cargo abaixo.'
			});
		
			enviaEmpresa($('#empresaSelect').val());
			
			populaBairros();
			var obj = document.getElementById("legendas");
			if(obj != null)
				obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #009900;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Participa ou participou de processo seletivo";
		});
	
		function enviaEmpresa(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			<!-- Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado -->
			CargoDWR.getByEmpresa(createListCargo, empresaId, empresaIds);
			ConhecimentoDWR.getByEmpresa(createListConhecimentos, empresaId,empresaIds);
			$("#opcaoTodasEmpresas").val(empresaId == -1);
		}
		
		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		
		function createListConhecimentos(data)
		{
			addChecks('conhecimentosCheck',data)
		}
		
		function populaBairros()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			
			var cidadesIds = getArrayCheckeds(document.forms[0], 'cidadesCheck');
			
			BairroDWR.getBairrosCheckList(createListBairros, cidadesIds);
		}

		function createListBairros(data)
		{
			addChecks("bairrosCheck",data)
		}

		function limparFiltro()
		{
			var valueCpf = "   .   .   -  ";
			var valueData = "  /  /    ";
			var campos = new Array('indicadoPor', 'nomeBusca', 'dataPrevIni', 'dataPrevFim', 'tempoExperiencia', 'palavrasChaveCurriculoEscaneado','palavrasChaveOutrosCampos', 'escolaridade', 'idioma', 'nivel', 'uf');
			$('#qtdRegistros').val(100);
			$('#ordenar').val('dataAtualizacao');
			$('#listCheckBoxcidadesCheck').empty();
			
			for(var contador = 0; contador < campos.length; contador++)
			{
				document.getElementById(campos[contador]).value = "";
			}

			populaCidadesCheckList();
			document.getElementById('cpfBusca').value = valueCpf;
			document.getElementById('dataCadIni').value = valueData;
			document.getElementById('dataCadFim').value = valueData;

			document.getElementById('sexo').value = "I";
			document.getElementById('veiculo').value = "I";
			document.getElementById('formas').value = "2";
			document.getElementById('deficiencia').value = "0";

			marcarDesmarcarListCheckBox(document.forms[0], 'bairrosCheck',false);
			marcarDesmarcarListCheckBox(document.forms[0], 'cargosCheck',false);
			marcarDesmarcarListCheckBox(document.forms[0], 'areasCheck',false);
			marcarDesmarcarListCheckBox(document.forms[0], 'areasFormacaoCheck',false);
			marcarDesmarcarListCheckBox(document.forms[0], 'conhecimentosCheck',false);
			marcarDesmarcarListCheckBox(document.forms[0], 'experienciasCheck',false);
		}
	</script>

	<#if dataCadIni?exists>
		<#assign dataIni = dataCadIni?date/>
	<#else>
		<#assign dataIni = ""/>
	</#if>
	<#if dataCadFim?exists>
		<#assign dataFim = dataCadFim?date/>
	<#else>
		<#assign dataFim = ""/>
	</#if>

<#include "../ftl/showFilterImports.ftl" />
<#assign validarCampos="return validaFormularioEPeriodo('formBusca', new Array('qtdRegistros'), new Array('dataCadIni','dataCadFim'))"/>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>

<body>
	<#include "buscaCandidatoSolicitacaoLinks.ftl" />

	<#include "../util/topFiltro.ftl" />
		<button onclick="limparFiltro();" class="btnLimparFiltro grayBGE"></button>

		<@ww.form name="formBusca" id="formBusca" action="busca.action" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="opcaoTodasEmpresas" id="opcaoTodasEmpresas" value=""/>

			<#list empresas as empresa>	
				<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
			</#list>

			<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" onchange="enviaEmpresa(this.value)" liClass="liLeft" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>

			<@ww.hidden name="BDS"/>
			<#if solicitacao?exists && solicitacao.id?exists>
				<@ww.hidden name="solicitacao.id"/>
			</#if>

			<@ww.textfield label="Indicado por" id="indicadoPor" name="indicadoPorBusca" cssStyle="width: 300px;"/>

			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 280px;" liClass="liLeft"/>
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" liClass="liLeft" cssClass="mascaraCpf"/>

			<li>
				<span>Cadastrado/Atualizado entre:</span>
			</li>
			<@ww.datepicker name="dataCadIni" id="dataCadIni" value="${dataIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
			<@ww.label value="e" liClass="liLeft" />
			<@ww.datepicker name="dataCadFim" id="dataCadFim" value="${dataFim}"  cssClass="mascaraData validaDataFim"/>

			<@ww.select label="Escolaridade mínima" name="escolaridade" id="escolaridade" list="escolaridades" liClass="liLeft" cssStyle="width: 220px;" headerKey="" headerValue=""/>
			<@ww.select label="Idioma" name="idioma" id="idioma" list="idiomas" listKey="id" listValue="nome" liClass="liLeft" cssStyle="width: 150px;" headerKey="" headerValue=""/>
			<@ww.select label="Nível" name="nivel" id="nivel" list="nivels" cssStyle="width: 150px;" headerKey="" headerValue=""/>
			<@ww.select label="Estado" name="uf" id="uf" list="ufs" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidadesCheckList();"/>
			<@frt.checkListBox label="Cidades" id="cidadesCheck" name="cidadesCheck" list="cidadesCheckList" onClick="populaBairros();" filtro="true"/>
			<!-- <@ww.select label="Cidade" name="cidade" id="cidade" list="cidades" cssStyle="width: 250px;" headerKey="" headerValue="" onchange="javascript:populaBairros()" />-->
			<@frt.checkListBox name="bairrosCheck" id="bairrosCheck" label="Bairros" list="bairrosCheckList" filtro="true"/>

			<li style="clear:both;"></li>

			<@ww.select label="Sexo" name="sexo" id="sexo" list="sexos" cssStyle="width: 130px;" liClass="liLeft"/>

			<li>
				<span>Idade Preferencial:</span>
			</li>

			<@ww.textfield name="idadeMin" id="dataPrevIni" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="dataPrevFim" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="anos"/><div style="clear: both"></div>

			<@ww.select label="Deficiência" name="deficiencia" id="deficiencia" list="deficiencias" cssStyle="width: 130px;" liClass="liLeft"/>

			<@ww.select label="Possui Veículo" name="veiculo" id="veiculo" list=r"#{'I':'Indiferente','S':'Sim','N':'Não'}" cssStyle="width: 100px;" />
			<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" list="cargosCheckList" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox label="Áreas de Interesse" name="areasCheck" list="areasCheckList" filtro="true"/>
			<@frt.checkListBox label="Áreas de Formação" name="areasFormacaoCheck" list="areasFormacaoCheckList" filtro="true"/>
			
			<@frt.checkListBox label="Conhecimentos" name="conhecimentosCheck" list="conhecimentosCheckList" filtro="true"/>
			<br />
			<#if solicitacao?exists && solicitacao.id?exists && solicitacao.experiencia?exists>
				<br />
					${solicitacao.experiencia}
				<br />
			</#if>
			<fieldset class="fieldsetClass">
				<legend>Experiência de Trabalho</legend>
				<li>
					<span>Experiência de:<br /></span>
				</li>
				<@ww.textfield name="tempoExperiencia" id="tempoExperiencia" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
				<li>meses <img id="tooltipExperienciaTrabalho" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -2px" /></li>
				<li>&nbsp;</li>
				<@frt.checkListBox label="em" name="experienciasCheck" list="experienciasCheckList" width="475px;" filtro="true" />
			</fieldset>
			
			<fieldset class="fieldsetClass">
				<legend>Palavras Chave</legend>
				<@ww.textfield label="Palavras chave no currículo escaneado" name="palavrasChaveCurriculoEscaneado" id="palavrasChaveCurriculoEscaneado" cssStyle="width: 475px;" />
				<div>
					Palavras chave em outros campos: <img id="tooltipPalavraChave" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -4px" />
					<@ww.textfield name="palavrasChaveOutrosCampos" id="palavrasChaveOutrosCampos" cssStyle="width: 475px;" />
				</div>
				<@ww.select name="formas" id="formas" list=r'#{
			       "2":"Qualquer Palavra",
			       "1":"Todas as Palavras",
			       "3":"Frase Exata"}' />
			</fieldset>
			<br />
			<@ww.select label="Ordenar Por" name="ordenar" id="ordenar" list=r"#{'dataAtualizacao':'Data de Atualização','nome':'Nome'}" cssStyle="width: 170px;" />			
			<@ww.textfield label="Quantidade de registros a serem listados"name="qtdRegistros" id="qtdRegistros" cssStyle="width: 45px; text-align:right;" onkeypress = "return(somenteNumeros(event,''));" maxLength="6" required="true" />
			
			<#if solicitacao?exists && solicitacao.id?exists>			
				<@ww.checkbox label="Trazer apenas candidatos que nunca participaram de processos seletivos" id="somenteCandidatosSemSolicitacao" name="somenteCandidatosSemSolicitacao" labelPosition="left"/>
			</#if>
			
			<@ww.hidden name="filtro" value="true"/>
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>

	<#include "../util/bottomFiltro.ftl" />

	<#if solicitacao?exists && solicitacao.id?exists>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V"></button>
	</#if>

	<#if candidatos?exists >
		<br />
		<div id="legendas" align="right"></div>
		<br />
		<#include "formListCandidatoSolicitacaoBusca.ftl" />

		<#if solicitacao?exists && solicitacao.id?exists>
			<div class="buttonGroup">
				<button onclick="prepareEnviarForm();" class="btnInserirSelecionados" accesskey="I"></button>
				<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V"></button>
			</div>
		</#if>
	</#if>
</body>
</html>