<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Solicitação de Realinhamento de Cargos & Salários</title>
	<#assign formAction="insertSolicitacaoReajuste.action"/>
	<#assign accessKey="I"/>

	<#include "tipoSalarioInclude.ftl" />
	<#include "calculaSalarioInclude.ftl" />

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js"/>"></script>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js"/>"></script>

	<script type="text/javascript">
		function verficaColaborador(tabelaId, colaboradorId)
		{
			DWREngine.setErrorHandler(errorVerificaColaborador);
			ReajusteDWR.verificaColaborador(verificaColaborador, tabelaId, colaboradorId, <@authz.authentication operation="empresaAcIntegra"/>);
		}

		function verificaColaborador(data)
		{
			populaDados(data);
		}		
		
		function errorVerificaColaborador(msg) 
		{
			document.getElementById('colaborador').value = "-1";
			jAlert(msg);
			limpaDados();
		}

		function populaDados(colaboradorId)
		{
			if(colaboradorId != "-1")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				ReajusteDWR.getColaboradorSolicitacaoReajuste(exibeDados, colaboradorId);
			}
			else
			{
				limpaDados();
			}
		}

		function exibeDados(data)
		{
   			var salarioAtualMascara = formatarMascaraMonetaria(data.salarioAtual);
			document.getElementById('areaOrganizacionalAtual').value = data.areaOrganizacionalAtualId;
			document.getElementById('areaOrganizacionalAtualNome').innerHTML = "<b>" + data.areaOrganizacionalAtualNome + "</b>";
			document.getElementById('estabelecimentoAtualId').value = data.estabelecimentoAtualId;
			document.getElementById('estabelecimentoAtualNome').innerHTML = "<b>" + data.estabelecimentoAtualNome + "</b>";
			document.getElementById('cargoAtualNome').innerHTML = "<b>" + data.cargoAtualNome + "</b>";
			document.getElementById('salarioAtualValor').innerHTML = "<b>" + salarioAtualMascara + "</b>";
			
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				if(data.funcaoAtualNome!=null)
					document.getElementById('funcaoAtualNome').innerHTML = "<b>" + data.funcaoAtualNome + "</b>";
				else
					document.getElementById('funcaoAtualNome').innerHTML = "<b>Não possui</b>";
				
				if(data.ambienteAtualNome!=null)
					document.getElementById('ambienteAtualNome').innerHTML = "<b>" + data.ambienteAtualNome + "</b>";
				else
					document.getElementById('ambienteAtualNome').innerHTML = "<b>Não possui</b>";
			</@authz.authorize>

			document.getElementById('tipoSalarioDescricao').innerHTML = "<b>" + data.tipoSalarioDescricao + "<b>";

			document.getElementById('salarioAtualValor').value = replaceAll(data.salarioAtual, ".", ",");
			document.getElementById('salarioAtual').value = replaceAll(data.salarioAtual, ".", ",");

			document.getElementById('tipoSalarioAtual').value = data.tipoSalarioAtual;

			document.getElementById('tipoSalario').value = data.tipoSalarioAtual;

			document.getElementById('indiceAtualId').value = data.indiceAtualId;
			document.getElementById('indice').value = data.indiceAtualId;

			document.getElementById('quantidadeIndiceAtual').value = data.quantidadeIndiceAtual;
			document.getElementById('quantidade').value = data.quantidadeIndiceAtual;

			document.getElementById('faixaSalarialAtual').value = data.faixaSalarialAtual;
			
			document.getElementById('faixa').value = data.faixaSalarialAtual;

			document.getElementById('estabelecimentoProposto').value = data.estabelecimentoAtualId;

			document.getElementById('areaOrganizacionalAtual').value = data.areaOrganizacionalAtualId;

			document.getElementById('areaOrganizacionalProposta').value = data.areaOrganizacionalAtualId;

			document.getElementById('salarioProposto').value = salarioAtualMascara;

			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				if(data.funcaoAtualId != undefined)
					document.getElementById('funcaoAtual').value = data.funcaoAtualId;
			
				if(data.ambienteAtualId != undefined)
					document.getElementById('ambienteAtual').value = data.ambienteAtualId;
			
				if (data.estabelecimentoAtualId != "" && data.estabelecimentoAtualId != null && data.estabelecimentoAtualId != "null")
					populaAmbiente(data.estabelecimentoAtualId, data.ambienteAtualId);	
					
				populaFuncao(document.getElementById('faixa').value, data.funcaoAtualId);
			</@authz.authorize>
			
			alteraTipoSalario(data.tipoSalarioAtual);
			calculaSalario();
		}

		function populaColaborador(areaId)
		{
			limpaDados();
			if(areaId != "" && areaId != null && areaId != "null")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				ReajusteDWR.getColaboradoresByAreaOrganizacional(exibeColaboradores, areaId);
			}
			else
			{
				DWRUtil.removeAllOptions("colaborador");
			}
		}

		function exibeColaboradores(data)
		{
			DWRUtil.removeAllOptions("colaborador");
			DWRUtil.addOptions("colaborador", data);
		}

		function formatarMascaraMonetaria(src)
		{
  			var quebra = src.split(".");
  			var centavos = quebra[1];
  			var valor = quebra[0];

			if (centavos.length == 1)
			{
				centavos = centavos + "0";
			}

  			valorTam = valor.length;
  			if (valorTam > 3)
  			{
  				valorCentena = valor.substring( valorTam - 3, valorTam );
  				valorMilhar = valor.substring( 0, valorTam - 3 );
  				valorFinal = valorMilhar + "." + valorCentena + "," + centavos;
  			}
  			else
  			{
				valorFinal = valor +","+centavos;
			}

			return valorFinal;
		}

		function limpaDados()
		{
			document.getElementById("estabelecimentoAtualNome").innerHTML	=	"<b>&nbsp</b>";
			document.getElementById("areaOrganizacionalAtualNome").innerHTML=	"<b>&nbsp</b>";
			document.getElementById("cargoAtualNome").innerHTML				=	"<b>&nbsp</b>";
			document.getElementById("tipoSalarioDescricao").innerHTML		=	"<b>&nbsp</b>";
			document.getElementById("salarioAtualValor").innerHTML			=	"<b>&nbsp</b>";
			
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				document.getElementById("funcaoAtualNome").innerHTML			=	"<b>&nbsp</b>";
				document.getElementById("ambienteAtualNome").innerHTML			=	"<b>&nbsp</b>";
			</@authz.authorize>

			document.getElementById('estabelecimentoProposto').value = "";
			document.getElementById('areaOrganizacionalProposta').value = "";
			document.getElementById('faixa').value = "";
			
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				document.getElementById('ambienteProposto').value = "-1";
				document.getElementById('funcaoProposta').value = "-1";
			</@authz.authorize>
			
			document.getElementById('tipoSalario').value = "";
			document.getElementById('observacao').value = "";
			escondeTudo();
		}

		function set(name, valor){
			document.getElementById(name).innerHTML = valor;
		}

		function populaFuncao(faixaId, funcaoId)
		{
			funcId = funcaoId;
			if(faixaId != "null" && faixaId != "")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				FuncaoDWR.getFuncaoByFaixaSalarial(function(data){createListFuncao(data, funcaoId);
											}, faixaId, funcaoId);
			}
		}

		function createListFuncao(data, funcaoId)
		{
			DWRUtil.removeAllOptions("funcaoProposta");
			DWRUtil.addOptions("funcaoProposta", data);
			
			if(funcaoId != null)
				document.getElementById('funcaoProposta').value = funcaoId;		
		}

		function populaAmbiente(estabelecimentoId, ambienteId)
		{
			if(estabelecimentoId != "null")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AmbienteDWR.getAmbienteByEstabelecimento(function(data){createListAmbiente(data, ambienteId);
															}, estabelecimentoId, ambienteId);
			}
		}

		function createListAmbiente(data, ambId)
		{
			DWRUtil.removeAllOptions("ambienteProposto");
			DWRUtil.addOptions("ambienteProposto", data);

			if(ambId != null)
				document.getElementById('ambienteProposto').value = ambId;
		}

		function enviaForm()
		{
			var camposObrigatorios = new Array('tabelaReajuste', 'areaOrganizacional', 'colaborador','estabelecimentoProposto', 'areaOrganizacionalProposta', 'faixa');
			
			<#if exibeSalario>
				camposObrigatorios.push('tipoSalario');
				
				if (document.getElementById('tipoSalario').value == ${tipoSalario.getIndice()})
				{
					camposObrigatorios.push('salarioProposto','indice','quantidade');
				}
				else if (document.getElementById('tipoSalario').value == ${tipoSalario.getValor()})
				{
					camposObrigatorios.push('salarioProposto');
				}
			<#else>
				if (document.getElementById('tipoSalario').value == ${tipoSalario.getIndice()})
				{
					camposObrigatorios.push('tipoSalario', 'quantidade');
				}
				else if (document.getElementById('tipoSalario').value == ${tipoSalario.getValor()})
				{
					camposObrigatorios.push('tipoSalario');
				}
			</#if>
			
			<#if obrigarAmbienteFuncaoColaborador>
				camposObrigatorios.push('ambienteProposto','funcaoProposta');
			</#if>
			
			return validaFormulario('form', camposObrigatorios, null);
		}

</script>
</head>
<body>
<@ww.actionerror />
<@ww.fielderror />
<@ww.actionmessage />
	<#if areaOrganizacionals?exists && 0 < areaOrganizacionals?size >
		<@ww.form name="form" id="form" action="${formAction}" method="POST">
			<li>
			<div id="quadroInformacao">
				<ul>
					<@ww.select label="Planejamento de Realinhamento" name="tabelaReajusteColaborador.id" id ="tabelaReajuste"  required="true" list="tabelaReajusteColaboradors" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1" />
					<@ww.select label="Áreas Organizacionais" name="areaOrganizacional.id" id="areaOrganizacional" required="true" list="areaOrganizacionals" listKey="id" listValue="descricao" headerValue="Selecione..." headerKey="" onchange="populaColaborador(this.value);" cssStyle="width:445px;" />
					<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" required="true" list="colaboradores" listKey="id" listValue="nomeComercial" headerValue="[Selecione Uma Área]" headerKey="-1" onchange="verficaColaborador(document.getElementById('tabelaReajuste').value, this.value);" />
				</ul>
			</div>
			</li>
			<li>
			<div id="gogEsq">
				<div id="quadro">
					<h2>Situação atual</h2>
					<ul>
						<div style="height: 50px;"></div> <#-- alinhar estabelecimento atual e proposto -->
						<@ww.label label="Estabelecimento" name="estabelecimentoAtual.nome" id="estabelecimentoAtualNome" cssStyle="height:40px;"/>
						<@ww.label label="Área Organizacional" name="areaOrganizacionalAtual.nome" id="areaOrganizacionalAtualNome"/>

						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.label label="Ambiente" name="ambienteAtualNome" id="ambienteAtualNome"/>
						</@authz.authorize>

						<@ww.label label="Cargo/Faixa" name="faixaSalarialAtual.nome" id="cargoAtualNome"/>

						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.label label="Função" name="funcaoAtualNome" id="funcaoAtualNome"/>
						</@authz.authorize>

						<@ww.label label="Tipo de Salário" name="tipoSalarioDescricao" id="tipoSalarioDescricao"/>
						<@ww.label label="Valor" name="salarioAtualValor" id="salarioAtualValor"/>
					</ul>
				</div>
			</div>

			<div id="gogDir">
				<div id="quadro">
					<h2>Situação proposta</h2>
					<ul>
						<#-- campo observação ficava no fim e foi movido pra ca devido a bug no IE7 -->
						<@ww.textarea label="Observação" name="reajusteColaborador.observacao" id="observacao" cssStyle="width:550px;height:60px" /><br><br><br>
												
						<#assign funcaoEstabelecimento="populaAmbiente(this.value,null);"/>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<#assign funcaoEstabelecimento=""/>
						</@authz.authorize>
						<@ww.select label="Estabelecimento" name="reajusteColaborador.estabelecimentoProposto.id" id="estabelecimentoProposto" required="true" headerValue="Selecione..." headerKey="" list="estabelecimentos" listKey="id" listValue="nome" onchange="${funcaoEstabelecimento}"/>
						
						<@ww.select label="Área Organizacional" name="reajusteColaborador.areaOrganizacionalProposta.id" id="areaOrganizacionalProposta" required="true"  headerValue="Selecione..." headerKey="" list="areaOrganizacionalsPropostas" listKey="id" listValue="descricao" onchange="verificaMaternidade(this.value, 'areaOrganizacionalProposta');" cssStyle="width:445px;"/>
						
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.select label="Ambiente" name="reajusteColaborador.ambienteProposto.id" id="ambienteProposto" required="${obrigarAmbienteFuncaoColaborador?string}" list="ambientes" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="-1"/>
							<@ww.select label="Cargo/Faixa" name="reajusteColaborador.faixaSalarialProposta.id" required="true" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" headerValue="Selecione..." headerKey="" onchange="calculaSalario();populaFuncao(this.value);"/>
							<@ww.select label="Função" name="reajusteColaborador.funcaoProposta.id" id="funcaoProposta" required="${obrigarAmbienteFuncaoColaborador?string}" headerValue="Selecione..." headerKey="-1" list="funcoes" listKey="id" listValue="nome"/>
						</@authz.authorize>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<@ww.select label="Cargo/Faixa" name="reajusteColaborador.faixaSalarialProposta.id" required="true" id="faixa" list="faixaSalarials" listKey="id" listValue="descricao" headerValue="Selecione..." headerKey="" onchange="calculaSalario();"/>
						</@authz.authorize>

						<@ww.hidden name="reajusteColaborador.areaOrganizacionalAtual.id" id="areaOrganizacionalAtual"/>
						<@ww.hidden name="reajusteColaborador.faixaSalarialAtual.id" id="faixaSalarialAtual"/>
						<@ww.hidden name="reajusteColaborador.tipoSalarioAtual" id="tipoSalarioAtual" />
						<@ww.hidden name="reajusteColaborador.indiceAtual.id" id="indiceAtualId" />
						<@ww.hidden name="reajusteColaborador.quantidadeIndiceAtual" id="quantidadeIndiceAtual" />
						<@ww.hidden name="reajusteColaborador.salarioAtual" id="salarioAtual" />
						<@ww.hidden name="reajusteColaborador.estabelecimentoAtual.id" id="estabelecimentoAtualId" />
		
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.hidden name="reajusteColaborador.funcaoAtual.id" id="funcaoAtual" />
							<@ww.hidden name="reajusteColaborador.ambienteAtual.id" id="ambienteAtual" />
						</@authz.authorize>


						<#-- Função que usa o include tipoSalarioInclude.ftl -->
						${preparaSalario("reajusteColaborador")}
					</ul>
				</div>
			</div>
			</li>

		</@ww.form>

		<div class="buttonGroup">
			<button onclick="enviaForm();" class="btnGravar" accesskey="${accessKey}">
			</button>
		</div>
	<#else>
		<b style="color:red;text-align:center;">Você não é responsável por nenhuma Área Organizacional.</b>
	</#if>
	<script type="text/javascript">
		limpaDados();
	</script>
</body>
</html>