<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<#if BDS?exists && !BDS>
		<title>Inserir Candidatos na Solicitação</title>
	<#else>
		<title>Exportar Candidatos para BDS</title>
	</#if>

	<#if BDS?exists && BDS>
		<#assign actionInserir="prepareEnviarFormBDS();"/>
	<#else>
		<#assign actionInserir="prepareEnviarForm();"/>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ConhecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	</style>
	<style type="text/css">#menuBusca a.ativaAvancada{color: #FFCB03;}</style>

	<script type="text/javascript">
	
		$(function(){
			enviaEmpresa($('#empresaSelect').val());
		});
	
		function enviaEmpresa(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			<!-- Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado -->
			ConhecimentoDWR.getByEmpresa(createListConhecimentos, empresaId);
		}
		
		function createListConhecimentos(data)
		{
			addChecks('conhecimentosCheck',data)
		}
		
		function populaBairros()
		{
			DWRUtil.useLoadingMessage('Carregando...');
			BairroDWR.getBairrosMap(createListBairros, document.getElementById("cidade").value);
		}

		function createListBairros(data)
		{
			addChecks("bairrosCheck",data)
		}

		function prepareEnviarFormBDS()
		{
			if(verificaCandidatos())
			{
				document.formCand.action = "selecionaDestinatariosBDS.action";
				document.formCand.submit();
			}
			else
			{
				jAlert("Nenhum Candidato selecionado!");
			}
		}


		function limparFiltro()
		{
			var valueCpf = "   .   .   -  ";
			var valueData = "  /  /    ";
			var campos = new Array('indicadoPor', 'nomeBusca', 'dataPrevIni', 'dataPrevFim', 'tempoExperiencia', 'palavrasChave', 'escolaridade', 'idioma', 'nivel', 'uf', 'cidade');
			$('#qtdRegistros').val(100);
			$('#ordenar').val('dataAtualizacao');
			
			for(var contador = 0; contador < campos.length; contador++)
			{
				document.getElementById(campos[contador]).value = "";
			}

			populaCidades();
			populaBairros();
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
	<#if !BDS>
		<#include "buscaCandidatoSolicitacaoLinks.ftl" />
	</#if>

	<#include "../util/topFiltro.ftl" />
		<button onclick="limparFiltro();" class="btnLimparFiltro grayBGE"></button>

		<@ww.form name="formBusca" id="formBusca" action="busca.action" onsubmit="${validarCampos}" method="POST">

			<#if BDS?exists && !BDS>
				<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" onchange="enviaEmpresa(this.value)" liClass="liLeft" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			</#if>

			<@ww.hidden name="BDS"/>
			<#if BDS?exists && !BDS>
				<@ww.hidden name="solicitacao.id"/>
			</#if>

			<@ww.textfield label="Indicado Por" id="indicadoPor" name="indicadoPorBusca" cssStyle="width: 300px;"/>

			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 280px;" liClass="liLeft"/>
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" liClass="liLeft" cssClass="mascaraCpf"/>

			<li>
				<span>
				Cadastrado/Atualizado entre:
				</span>
			</li>
			<@ww.datepicker name="dataCadIni" id="dataCadIni" value="${dataIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
			<@ww.label value="e" liClass="liLeft" />
			<@ww.datepicker name="dataCadFim" id="dataCadFim" value="${dataFim}"  cssClass="mascaraData validaDataFim"/>

			<@ww.select label="Escolaridade mínima" name="escolaridade" id="escolaridade" list="escolaridades" liClass="liLeft" cssStyle="width: 220px;" headerKey="" headerValue=""/>
			<@ww.select label="Idioma" name="idioma" id="idioma" list="idiomas" listKey="id" listValue="nome" liClass="liLeft" cssStyle="width: 150px;" headerKey="" headerValue=""/>
			<@ww.select label="Nível" name="nivel" id="nivel" list="nivels" cssStyle="width: 150px;" headerKey="" headerValue=""/>
			<@ww.select label="Estado" name="uf" id="uf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
			<@ww.select label="Cidade" name="cidade" id="cidade" list="cidades" cssStyle="width: 250px;" headerKey="" headerValue="" onchange="javascript:populaBairros()" />
			<@frt.checkListBox name="bairrosCheck" id="bairrosCheck" label="Bairros" list="bairrosCheckList" />

			<li style="clear:both;"></li>

			<@ww.select label="Sexo" name="sexo" id="sexo" list="sexos" cssStyle="width: 130px;" liClass="liLeft"/>

			<li>
				<span>
				Idade Preferencial:
				</span>
			</li>

			<@ww.textfield name="idadeMin" id="dataPrevIni" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="dataPrevFim" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="anos"/><div style="clear: both"></div>

			<@ww.select label="Deficiência" name="deficiencia" id="deficiencia" list="deficiencias" cssStyle="width: 130px;" liClass="liLeft"/>

			<@ww.select label="Possui Veículo" name="veiculo" id="veiculo" list=r"#{'I':'Indiferente','S':'Sim','N':'Não'}" cssStyle="width: 100px;" />
			<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" list="cargosCheckList" />
			<@frt.checkListBox label="Áreas de Interesse" name="areasCheck" list="areasCheckList"/>
			<@frt.checkListBox label="Conhecimentos" name="conhecimentosCheck" list="conhecimentosCheckList" />
			<br>
			<#if solicitacao?exists && solicitacao.experiencia?exists>
			<br>
				${solicitacao.experiencia}
			<br>
			</#if>
			<li>
				<span>
				Experiência de:
				<br>
				</span>
			</li>
			<@ww.textfield name="tempoExperiencia" id="tempoExperiencia" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<li>meses</li>
			<li>&nbsp;</li>
			<@frt.checkListBox label="em" name="experienciasCheck" list="experienciasCheckList" />
			<@ww.textfield label="Palavras-chave no currículo" name="palavrasChave" id="palavrasChave" cssStyle="width: 500px;" />
			<@ww.select name="formas" id="formas" list=r'#{
		       "2":"Qualquer Palavra",
		       "1":"Todas as Palavras",
		       "3":"Frase Exata"}' />
			<br>
			<@ww.select label="Ordenar Por" name="ordenar" id="ordenar" list=r"#{'dataAtualizacao':'Data de Atualização','nome':'Nome'}" cssStyle="width: 170px;" />			
			<@ww.textfield label="Quantidade de registros a serem listados"name="qtdRegistros" id="qtdRegistros" cssStyle="width: 45px; text-align:right;" onkeypress = "return(somenteNumeros(event,''));" maxLength="6" />
			
			<@ww.checkbox label="Trazer apenas candidatos que nunca participaram de processos seletivos" id="somenteCandidatosSemSolicitacao" name="somenteCandidatosSemSolicitacao" labelPosition="left"/>
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>

	<#include "../util/bottomFiltro.ftl" />

	<#if BDS?exists && !BDS>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V">
		</button>
	</#if>

	<#if candidatos?exists >
		<br>

		<#include "formListCandidatoSolicitacaoBusca.ftl" />

		<div class="buttonGroup">
			<button onclick="${actionInserir}" class="btnInserirSelecionados" accesskey="I">
			</button>
			<#if BDS?exists && !BDS>
				<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V">
				</button>
			</#if>
		</div>
	</#if>
</body>
</html>