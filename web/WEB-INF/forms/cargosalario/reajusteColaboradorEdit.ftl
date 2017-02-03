<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

<title>Editar Solicitação de Realinhamento</title>
<#assign formAction="update.action"/>
<#assign accessKey="A"/>

<#include "tipoSalarioInclude.ftl" />
<#include "calculaSalarioInclude.ftl" />

<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js?version=${versao}"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>"></script>

<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js?version=${versao}"/>"></script>

<script language='javascript'>
	function populaFuncao(faixaId)
	{
		if(faixaId != "null" && faixaId != "")
		{
			dwr.util.useLoadingMessage('Carregando...');
			FuncaoDWR.getFuncaoByFaixaSalarial(faixaId, createListFuncao);
		}
	}

	function createListFuncao(data)
	{
		dwr.util.removeAllOptions("funcaoProposta");
		dwr.util.addOptions("funcaoProposta", data);
	}

	function populaAmbiente(estabelecimentoId)
	{
		if(estabelecimentoId != "null")
		{
			dwr.util.useLoadingMessage('Carregando...');
			AmbienteDWR.getAmbienteByEstabelecimento(estabelecimentoId, createListAmbiente);
		}
	}
	function createListAmbiente(data)
	{
		dwr.util.removeAllOptions("ambienteProposto");
		dwr.util.addOptions("ambienteProposto", data);
	}

	function enviaForm()
	{
		var camposObrigatorios = new Array('areaProposta', 'tipoSalario');
		
		if (document.getElementById('tipoSalario').value == ${tipoSalario.getIndice()})
		{
			camposObrigatorios.push('indice','quantidade');
		}
		else if (document.getElementById('tipoSalario').value == ${tipoSalario.getValor()})
		{
			camposObrigatorios.push('salarioProposto');
		}
		else
		{
			camposObrigatorios.push('faixa');
		}
		
		<#if obrigarAmbienteFuncao>
			camposObrigatorios.push('ambienteProposto','funcaoProposta');
		</#if>
		
		return validaFormulario('form', camposObrigatorios, 'faixa', null);
	}

</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.fielderror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST" >
		<li>
			<div id="quadroInformacao">
				Colaborador: ${reajusteColaborador.colaborador.nomeComercial}<br/><br/>
			</div>
		</li>

		<li>
			<div id="gogEsq">
				<div id="quadro">
					<h2>Situação atual</h2>
					<ul>
						<div style="height: 50px;"></div> <#-- alinhar estabelecimento atual e proposto -->
						<@ww.label label="Estabelecimento" name="reajusteColaborador.estabelecimentoAtual.nome" />
						<@ww.label label="Área Organizacional" name="reajusteColaborador.areaOrganizacionalAtual.descricao" />
						
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.label label="Ambiente" name="reajusteColaborador.ambienteAtual.nome" id="ambienteAtualNome"/>
						</@authz.authorize>
						
						<@ww.label label="Cargo/Faixa" name="reajusteColaborador.faixaSalarialAtual.descricao" />
						
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.label label="Função" name="reajusteColaborador.funcaoAtual.nome" id="funcaoAtualNome"/>
						</@authz.authorize>
						
						<@ww.label label="Tipo de Salário" name="reajusteColaborador.descricaoTipoSalarioAtual" id="tipoSalarioDescricao" />
						<@ww.label label="Valor" name="reajusteColaborador.salarioAtual" />
					</ul>
				</div>
			</div>

			<div id="gogDir">
				<div id="quadro">
					<h2>Situação proposta</h2>
					<ul>
						<@ww.textfield label="Observação" name="reajusteColaborador.observacao" id="observacao" cssClass="inputNome" maxLength="100" />
						
						<#assign funcaoEstabelecimento="populaAmbiente(this.value);"/>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<#assign funcaoEstabelecimento=""/>
						</@authz.authorize>
						<@ww.select label="Estabelecimento" name="reajusteColaborador.estabelecimentoProposto.id" id="estabelecimentoProposto" list="estabelecimentos" listKey="id" listValue="nome" onchange="${funcaoEstabelecimento}"/>
						
						<@ww.select label="Área Organizacional" name="reajusteColaborador.areaOrganizacionalProposta.id" id="areaProposta" required="true" list="areaOrganizacionals" listKey="id" listValue="descricao" headerValue="Selecione..." headerKey=""  onchange="verificaMaternidade(this.value, 'areaProposta');" cssStyle="width:445px;" />
		
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.select label="Ambiente" name="reajusteColaborador.ambienteProposto.id" id="ambienteProposto" required="${obrigarAmbienteFuncao?string}" list="ambientes" listKey="id" listValue="nome" headerValue="Nenhum" headerKey="-1"/>
							<@ww.select label="Cargo/Faixa" name="reajusteColaborador.faixaSalarialProposta.id" id="faixa" required="true" onchange="calculaSalario();populaFuncao(this.value);" list="faixaSalarials" listKey="id" listValue="descricao"/>
							<@ww.select label="Função" name="reajusteColaborador.funcaoProposta.id" id="funcaoProposta" required="${obrigarAmbienteFuncao?string}" list="funcaos" listKey="id" listValue="nome" headerValue="Nenhum" headerKey="-1" />
						</@authz.authorize>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<@ww.select label="Cargo/Faixa" name="reajusteColaborador.faixaSalarialProposta.id" id="faixa" required="true" onchange="calculaSalario();" list="faixaSalarials" listKey="id" listValue="descricao"/>
						</@authz.authorize>
		

						<@ww.hidden name="reajusteColaborador.id" />
						<@ww.hidden name="reajusteColaborador.estabelecimentoAtual.id" />
						<@ww.hidden name="reajusteColaborador.salarioAtual" />
						<@ww.hidden name="reajusteColaborador.indiceAtual.id" />
						<@ww.hidden name="reajusteColaborador.quantidadeIndiceAtual" />
						<@ww.hidden name="reajusteColaborador.colaborador.id" />
						<@ww.hidden name="reajusteColaborador.tabelaReajusteColaborador.id" id="tabelaReajusteColaborador"/>
						<@ww.hidden name="reajusteColaborador.areaOrganizacionalAtual.id"/>
						<@ww.hidden name="reajusteColaborador.tipoSalarioAtual"/>
						<@ww.hidden name="reajusteColaborador.faixaSalarialAtual.id"/>

						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@ww.hidden name="reajusteColaborador.ambienteAtual.id" />
							<@ww.hidden name="reajusteColaborador.funcaoAtual.id" />
						</@authz.authorize>

						<@ww.hidden name="areaOrganizacionalFiltro.id"/>
						<@ww.hidden name="grupoOcupacionalFiltro.id"/>

						${preparaSalario("reajusteColaborador")}
					</ul>
				</div>
			</div>
		</li>
	</@ww.form>
	<script type='text/javascript'>
		alteraTipoSalario(${reajusteColaborador.tipoSalarioProposto});
	</script>

	<div class="buttonGroup">
		<button onclick="enviaForm();" class="btnGravar" accesskey="${accessKey}">
		</button>

		<button onclick="window.location='../tabelaReajusteColaborador/visualizar.action?tabelaReajusteColaborador.id=${reajusteColaborador.tabelaReajusteColaborador.id}'" class="btnVoltar" accesskey="V">
		</button>
	</div>

</body>
</html>