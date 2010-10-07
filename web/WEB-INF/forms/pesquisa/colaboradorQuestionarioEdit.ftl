<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Adicionar Colaboradores à ${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/questionario.css"/>');
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/GrupoOcupacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">

		function populaEstabelecimentosAreasGrupos(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			
			EstabelecimentoDWR.getByEmpresa(createListEstabelecimentos, empresaId);
			AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
			GrupoOcupacionalDWR.getByEmpresa(createListGrupos, empresaId);
			CargoDWR.getByEmpresa(createListCargos, empresaId);
		}

		function createListEstabelecimentos(data)
		{
			addChecks('estabelecimentosCheck',data)
		}
		function createListAreas(data)
		{
			addChecks('areasCheck',data)
		}
		function createListGrupos(data)
		{
			addChecks('gruposCheck',data)
		}
		
		function populaCargosByGrupo(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var gruposIds = getArrayCheckeds(frm, nameCheck);
			CargoDWR.getCargoByGrupo(createListCargos, gruposIds);
		}

		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
		}

		function filtrarOpt(value)
		{
			if(value == "1")
			{
				document.getElementById('divAreas').style.display = "";
				document.getElementById('divGrupos').style.display = "none";
			}
			else if(value == "2")
			{
				document.getElementById('divAreas').style.display = "none";
				document.getElementById('divGrupos').style.display = "";
			}
		}

		function exibirPercentual(checkbox)
		{
			if(checkbox.checked)
			{
				document.getElementById('divPercentual').style.display = "";
			}
			else
			{
				document.getElementById('divPercentual').style.display = "none";
			}
		}

		function exibirQtd(value)
		{
			if(value == "1")
			{
				document.getElementById('divPerc').style.display = "";
				document.getElementById('divQtd').style.display = "none";
			}
			else if(value == "2")
			{
				document.getElementById('divPerc').style.display = "none";
				document.getElementById('divQtd').style.display = "";
			}
		}

		function validaForm()
		{
			var filtrarPorValue = document.getElementById('filtrarPor').value;
			var qtdPercentualValue = document.getElementById('qtdPercentual').value;
			var calculaPercentualChecked = document.getElementById('calcularPercentual').checked;

			var validaAreaCargo = '@areasCheck';
			var validaPercentualQuantidade = 'percentual';

			if(filtrarPorValue == "2")
			{
				validaAreaCargo = '@cargosCheck';
			}

			if(qtdPercentualValue == "2")
			{
				validaPercentualQuantidade = 'quantidade';
			}

			if(calculaPercentualChecked)
			{
				return validaFormulario('form', new Array('filtrarPor', '@estabelecimentosCheck',validaAreaCargo,validaPercentualQuantidade), null);
			}
			else
			{
				return validaFormulario('form', new Array('filtrarPor', '@estabelecimentosCheck',validaAreaCargo), null);
			}
		}

		function marcarDesmarcar(frm)
		{
			var vMarcar;

			if (document.getElementById('md').checked)
			{
				vMarcar = true;
			}
			else
			{
				vMarcar = false;
			}

			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'colaboradoresId' && elements[i].type == 'checkbox')
					{
						elements[i].checked = vMarcar;
					}
				}
			}
		}

		function verificaSelecao(frm)
		{
			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'colaboradoresId')
					{
						if(elements[i].type == 'checkbox' && elements[i].checked)
						{
							document.formColab.action = "insert.action";
							document.formColab.submit();
							return true;
						}
					}
				}
			}

			alert('Selecione algum Colaborador!');
			return false;
		}
	</script>


	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<#if questionario.dataInicio?exists && questionario.dataFim?exists>
		<@ww.div cssClass="divInformacao">
			<span>${tipoQuestionario.getDescricaoMaisc(questionario.tipo)}: <b>${questionario.titulo}</b></span>

			<#if questionario.dataInicio?exists && questionario.dataFim?exists>
				Período: <b>${questionario.dataInicio} a ${questionario.dataFim}</b>
			</#if>
		</@ww.div>
		<br>
	</#if>
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="listFiltro.action" onsubmit="validaForm();" method="POST">
		
			<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" cssStyle="width: 147px;" listKey="id" listValue="nome" required="true" onchange="populaEstabelecimentosAreasGrupos(this.value)" />
		
			<@ww.select id="filtrarPor" label="Filtrar Colaboradores por" name="filtrarPor" list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt(this.value);"/>

			<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
			<li>
				<@ww.div id="divAreas">
					<ul>
						<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList"/>
					</ul>
				</@ww.div>
			</li>
			<li>
				<@ww.div id="divGrupos" cssStyle="display:none;">
					<ul>
						<@frt.checkListBox name="gruposCheck" id="gruposCheck" label="Grupos Ocupacionais" list="gruposCheckList" onClick="populaCargosByGrupo(document.forms[0],'gruposCheck');"/>
						<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
					</ul>
				</@ww.div>
			</li>

			<@ww.checkbox label="Sortear uma amostra aleatória de colaboradores" id="calcularPercentual" name="calcularPercentual" labelPosition="left" onclick="exibirPercentual(this);"/>

			<li>
				<@ww.div id="divPercentual" cssStyle="display:none;">
					<ul>
						<li>
							<@ww.div cssClass="divInfo" cssStyle="padding-left:10px;">
								<ul>
									<@ww.select label="" id="qtdPercentual" name="qtdPercentual" list=r"#{'1':'Sortear um percentual de Colaboradores', '2':'Sortear uma quantidade fixa de Colaboradores'}" onchange="exibirQtd(this.value);"/>
									<br>
									<li>
										<@ww.div id="divPerc">
											<ul>
												<@ww.textfield label="Percentual de colaboradores a serem sorteados" id="percentual" name="percentual" onkeypress = "return(somenteNumeros(event,'{.}'));" maxLength="4" cssStyle="width:28px;text-align: right;" after="%"/>
											</ul>
										</@ww.div>
									</li>
									<li>
										<@ww.div id="divQtd" cssStyle="display:none;">
											<ul>
												<@ww.textfield label="Quantidade de colaboradores a serem sorteados" id="quantidade" name="quantidade" onkeypress = "return(somenteNumeros(event,''));" maxLength="3" cssStyle="width:25px;text-align: right;"/>
											</ul>
										</@ww.div>
									</li>
									<@ww.checkbox label="Aplicar individualmente a cada Área/Grupo" name="aplicarPorParte" labelPosition="left" />
								</ul>
							</@ww.div>
						</li>
					</ul>
				</@ww.div>
			</li>

			<@ww.hidden name="questionario.id"/>
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnPesquisar grayBGE" onclick="validaForm();" accesskey="I">
			</button>

			<#if colaboradores?exists && colaboradores?size = 0 >
				<button class="btnVoltar grayBGE" onclick="window.location='list.action?questionario.id=${questionario.id}'" accesskey="V">
				</button>
			</#if>
		</div>
	<#include "../util/bottomFiltro.ftl" />

	<#if colaboradores?exists && 0 < colaboradores?size>
		<br>
		<@ww.form name="formColab" action="insert.action" method="POST">
			<@display.table name="colaboradores" id="colaborador" class="dados">
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formColab);' checked />" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${colaborador.id}" name="colaboradoresId" checked />
				</@display.column>
				<@display.column property="nomeComercial" title="Colaborador"/>
				<@display.column property="estabelecimento.nome" title="Estabelecimento"/>
				<@display.column property="areaOrganizacional.descricao" title="Área"/>
			</@display.table>

			<@ww.hidden name="questionario.id"/>
		</@ww.form>
	</#if>

	<#if colaboradores?exists && 0 < colaboradores?size>
		<div class="buttonGroup">
				<button onclick="verificaSelecao(document.formColab);" class="btnInserirSelecionados" accesskey="I">
				</button>

				<button class="btnVoltar" onclick="window.location='list.action?questionario.id=${questionario.id}'" accesskey="V">
				</button>
		</div>
	</#if>

	<script type="text/javascript">
		exibirPercentual(document.getElementById('calcularPercentual'));
		filtrarOpt(document.getElementById('filtrarPor').value);
		exibirQtd(document.getElementById('qtdPercentual').value);
	</script>
</body>
</html>