<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/displaytag.css"/>" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/colaborador.css"/>" media="screen" type="text/css">

	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />

	<!--[if IE]>
	<style type="text/css">
		#abas
		{
			margin-bottom: 4px;
		}
		#wwlbl_desCursos label,
		#wwlbl_obs label
		{
			margin-left: -15px !important;
		}
	</style>
	<![endif]-->

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/colaborador.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>


	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<#include "../cargosalario/calculaSalarioInclude.ftl" />

	<script type="text/javascript">
	
		
		function populaAmbiente(estabelecimentoId, ambienteId)
		{
			if(estabelecimentoId != "null")
			{
				DWRUtil.useLoadingMessage('Carregando...');
				AmbienteDWR.getAmbienteByEstabelecimento(function(data){createListAmbiente(data, ambienteId);
															}, estabelecimentoId, ambienteId);
			}
		}
		
		function createListAmbiente(data)
		{
			DWRUtil.removeAllOptions("ambiente");
			DWRUtil.addOptions("ambiente", data);
		}

		function populaFuncao(faixaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			FuncaoDWR.getFuncaoByFaixaSalarial(createListFuncao, faixaId);
		}
		function createListFuncao(data)
		{
			DWRUtil.removeAllOptions("funcao");
			DWRUtil.addOptions("funcao", data);
		}

		function setaCampos()
		{
			document.getElementById('colaborador.cursos').value = document.getElementById('desCursos').value;
			document.getElementById('colaborador.observacao').value = document.getElementById('obs').value;
			return true;
		}

		function setDescricao(data)
		{
			if(data == null)
			{
				alert("Código " + document.getElementById('codCbo').value + " não encontrado.");
				document.getElementById('descricaoCargo').value = "";
				document.getElementById('codCbo').value = "";
				document.getElementById('funcaoId').value = "";
				document.getElementById('codCbo').focus();
			}
			else
			{
				for (var prop in data)
				{
					DWRUtil.setValue("descricaoCargo",data[prop]);
					DWRUtil.setValue("funcaoId",prop);
				}
			}
		}

		function sugerirNomeComercial()
		{
			document.getElementById('nomeComercial').value = document.getElementById('nomeColab').value.substr(0,30).trim();
		}
		function sugerirDataHistorico()
		{
			if(document.getElementById('dt_hist').value == "  /  /    ")
				document.getElementById('dt_hist').value = document.getElementById('dt_admissao').value;
		}

		jQuery(function($) {
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action"/>');
			
			$('#ambienteTooltipHelp').qtip({
				content: '<strong>Ambiente</strong><br />Necessário para o PPP e PPRA.'
				,
				style: {
		        	 width: '100px'
		        }
			});
			
			$('#funcaoTooltipHelp').qtip({
				content: '<strong>Função</strong><br />Necessário para o PPP e PPRA.'
				,
				style: {
		        	 width: '100px'
		        }
			});
			
			//addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');			
		});
	</script>

	<@ww.head />
	<#if colaborador.id?exists>
		<title>Editar Colaborador</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign edicao="true"/>
		<#assign somenteLeitura="true"/>
		<#assign funcaoNome=""/>
		<#assign funcaoDataAdmissao=""/>
	<#else>
		<title>Inserir Colaborador</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="G"/>
		<#assign edicao="false"/>
		<#assign somenteLeitura="false"/>
		<#assign funcaoNome="sugerirNomeComercial();"/>
		<#assign funcaoDataAdmissao="sugerirDataHistorico();"/>
	</#if>

	<#if editarHistorico>
		<#assign somenteLeitura="false"/>
	</#if>

	<#if historicoColaborador?exists && historicoColaborador.data?exists>
		<#assign dataHist = historicoColaborador.data?date/>
	<#else>
		<#assign dataHist = ""/>
	</#if>

	<#if colaborador?exists && colaborador.dataAdmissao?exists>
		<#assign dataAdm = colaborador.dataAdmissao?date/>
	<#else>
		<#assign dataAdm = ""/>
	</#if>
	<#if colaborador?exists && colaborador.pessoal?exists && colaborador.pessoal.rgDataExpedicao?exists>
		<#assign rgDataExpedicao = colaborador.pessoal.rgDataExpedicao?date/>
	<#else>
		<#assign rgDataExpedicao = ""/>
	</#if>
	<#if colaborador?exists && colaborador.habilitacao?exists && colaborador.habilitacao.emissao?exists>
		<#assign habEmissao = colaborador.habilitacao.emissao?date/>
	<#else>
		<#assign habEmissao = ""/>
	</#if>
	<#if colaborador?exists && colaborador.habilitacao?exists && colaborador.habilitacao.vencimento?exists>
		<#assign dataVenc = colaborador.habilitacao.vencimento?date/>
	<#else>
		<#assign dataVenc = ""/>
	</#if>
	<#if colaborador?exists && colaborador.pessoal?exists && colaborador.pessoal.ctps?exists && colaborador.pessoal.ctps.ctpsDataExpedicao?exists>
		<#assign ctpsDataExpedicao = colaborador.pessoal.ctps.ctpsDataExpedicao?date/>
	<#else>
		<#assign ctpsDataExpedicao = ""/>
	</#if>
	<#if colaborador?exists && colaborador.pessoal.dataNascimento?exists>
		<#assign dataNasc = colaborador.pessoal.dataNascimento?date/>
	<#else>
		<#assign dataNasc = ""/>
	</#if>

	<#assign validaDataCamposExtras = ""/>
	<#if habilitaCampoExtra>
		<#list configuracaoCampoExtras as configuracaoCampoExtra>		
			
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data1">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data1'"/>
			</#if>
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data2">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data2'"/>
			</#if>
			<#if configuracaoCampoExtra.nome?exists && configuracaoCampoExtra.nome == "data3">
				<#assign validaDataCamposExtras = validaDataCamposExtras + ", 'data3'"/>
			</#if>
		</#list>
		
		<#assign totalAbas = 6/>
	<#else>
		<#assign totalAbas = 5/>
	</#if>


	<#assign validarCampos="validaFormularioDinamico();"/>
	<script type="text/javascript">
		var arrayValidacao = new Array('nomeColab','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario');

		function alteraArrayValidacao(tipo)
		{
			if(tipo == ${tipoSalario.getIndice()})
				arrayValidacao = new Array('nomeColab','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario','indice','quantidade');
			else if(tipo == ${tipoSalario.getValor()})
				arrayValidacao = new Array('nomeColab','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario','salarioProposto');
			else
				arrayValidacao = new Array('nomeColab','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario');
		}

		function validaFormularioDinamico()
		{
			return validaFormulario('form', arrayValidacao, new Array('email', 'nascimento', 'cpf', 'cep', 'dt_admissao', 'emissao', 'vencimento','rgDataExpedicao','ctpsDataExpedicao', 'pis' ${validaDataCamposExtras}));
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<div id="abas">
		<div id="aba1"><a href="javascript: abas(1, '', ${edicao}, ${totalAbas})">Dados Pessoais</a></div>
		<div id="aba2"><a href="javascript: abas(2, '', ${edicao}, ${totalAbas})">Dados Funcionais</a></div>
		<div id="aba3"><a href="javascript: abas(3, '', ${edicao}, ${totalAbas})">Formação Escolar</a></div>
		<div id="aba4"><a href="javascript: abas(4, '', ${edicao}, ${totalAbas})">Experiências</a></div>
		<div id="aba5"><a href="javascript: abas(5, '', ${edicao}, ${totalAbas})">Documentos</a></div>
		
		<#if habilitaCampoExtra>
			<div id="aba6"><a href="javascript: abas(6, '', ${edicao}, ${totalAbas})">Extra</a></div>
		</#if>
	</div>

	<#-- Campos fora do formulário por causa do ajax.
	Antes de enviar o form os cursos e a observação são setados em campos hidden dentro do form. -->
	<#-- Acima do form para corrigir problema de layout no IE -->
	<div id="content3" style="display: none;">
		<@ww.div id="formacao"/>
		<@ww.div  id="idioma"/>
		<@ww.textarea label="Cursos" id="desCursos" name="desCursos" cssStyle="width: 705px;"/>
	</div>
	<div id="content4" style="display: none;">
		<@ww.div id="expProfissional" />
		<@ww.textarea label="Informações Adicionais" id="obs" name="obs" cssStyle="width: 705px;"/>
	</div>

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
		<div id="content1">

			<#assign somenteLeituraIntegraAC="false" />

			<#if integraAc>
				<#if !colaborador.id?exists>
					<@ww.checkbox label="Não enviar este colaborador para o AC Pessoal" id="naoIntegraAc" name="colaborador.naoIntegraAc" liClass="liLeft" labelPosition="left"/>
				<#else>
					<@ww.hidden id="naoIntegraAc" name="colaborador.naoIntegraAc"/>
					<#if colaborador.naoIntegraAc>
						Este colaborador não está integrado com o AC Pessoal
					<#else>
						Este colaborador está integrado com o AC Pessoal
					</#if>
				</#if>

				<#assign somenteLeituraIntegraAC="true" />
			</#if>


			<#if colaborador.foto?exists || (candidato?exists && candidato.id?exists && candidato.foto?exists)>
				<div id="fotoTbl">
					<table style="border:0px;">
						<tr>
							<td>
								<#if colaborador.id?exists>
									<img src="<@ww.url includeParams="none" value="showFoto.action?colaborador.id=${colaborador.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
								<#else>
									<#-- Veio do prepareContrata, era um candidato -->
									<#if candidato?exists && candidato.id?exists && candidato.foto?exists>
										<img src="<@ww.url includeParams="none" value="/captacao/candidato/showFoto.action?candidato.id=${candidato.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
									</#if>
								</#if>
							</td>
							<td>
								<@ww.checkbox label="Manter foto atual" name="manterFoto" onclick="mostraFoto()" value="true" checked="checked" labelPosition="left"/>
								<div id="fotoUpLoad" style="display:none;">
									<@ww.file label="Nova Foto" name="colaborador.foto" id="foto"/>
								</div>
							</td>
						</tr>
					</table>
					<hr>
				</div>
	        <#else>
				<@ww.file label="Foto" name="colaborador.foto" id="foto"/>
	        </#if>


			<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColab" required="true" liClass="liLeft" cssStyle="width: 300px;" maxLength="60" onblur="${funcaoNome}"/>

			<@ww.textfield label="Nome Comercial" name="colaborador.nomeComercial" id="nomeComercial" required="true" cssStyle="width: 300px;" maxLength="30"/>

			<@ww.datepicker label="Nascimento" name="colaborador.pessoal.dataNascimento" value="${dataNasc}" id="nascimento" required="true" liClass="liLeft" cssClass="mascaraData"/>

			<@ww.select label="Sexo"   name="colaborador.pessoal.sexo" list="sexos" cssStyle="width: 85px;" liClass="liLeft" />
			<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpf" required="true" cssClass="mascaraCpf" />
			<@ww.textfield label="CEP" name="colaborador.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft" />
			<@ww.textfield label="Logradouro" name="colaborador.endereco.logradouro" id="ende" required="true" cssStyle="width: 300px;" liClass="liLeft" maxLength="40"/>
			<@ww.textfield label="Nº"  name="colaborador.endereco.numero" id="num" required="true" cssStyle="width:40px;" liClass="liLeft" maxLength="10"/>
			<@ww.textfield label="Complemento" name="colaborador.endereco.complemento" cssStyle="width: 205px;" maxLength="20"/>
			<@ww.select label="Estado"     name="colaborador.endereco.uf.id" id="uf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" />
			<@ww.select label="Cidade" name="colaborador.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" required="true" />
				<@ww.textfield label="Bairro" name="colaborador.endereco.bairro" id="bairroNome" cssStyle="width: 325px;" maxLength="20"/>
				<@ww.div id="bairroContainer"/>
			<@ww.textfield label="E-mail"    name="colaborador.contato.email" id="email" cssClass="mascaraEmail" maxLength="40" liClass="liLeft"/>
			<@ww.textfield label="DDD" name="colaborador.contato.ddd" required="true" id="ddd" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" maxLength="2" cssStyle="width:25px;"/>
			<@ww.textfield label="Telefone"  name="colaborador.contato.foneFixo" required="true" id="fone" onkeypress = "return(somenteNumeros(event,''));" maxLength="8" liClass="liLeft" cssStyle="width:60px;"/>
			<@ww.textfield label="Celular"   name="colaborador.contato.foneCelular" onkeypress = "return(somenteNumeros(event,''));" maxLength="8" cssStyle="width:60px;"/>
			<@ww.select label="Escolaridade" name="colaborador.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 303px;" liClass="liLeft" required="true" headerKey="" headerValue="Selecione..." />
			<@ww.select label="Estado Civil" name="colaborador.pessoal.estadoCivil" list="estadosCivis" cssStyle="width: 210px;" liClass="liLeft"/>
			<@ww.select label="Deficiência" name="colaborador.pessoal.deficiencia" list="deficiencias" cssStyle="width: 160px;"/>

			<@ww.textfield label="Nome do Pai" name="colaborador.pessoal.pai" id="nomePai" liClass="liLeft" cssStyle="width: 300px;" maxLength="60"/>
			<@ww.textfield label="Nome da Mãe" name="colaborador.pessoal.mae" id="nomeMae" cssStyle="width: 300px;" maxLength="60"/>
			<@ww.textfield label="Nome do Cônjuge" name="colaborador.pessoal.conjuge" id="nomeConjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft"/>
			<@ww.textfield label="Qtd. Filhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="colaborador.pessoal.qtdFilhos" id="qtdFilhos" cssStyle="width:25px; text-align:right;" maxLength="2" />
	
	
	
	
	
		</div>

		<div id="content2" style="display: none;">
			<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matricula" disabled= "${somenteLeituraIntegraAC}" cssStyle="width:150px;" liClass="liLeft" maxLength="20"/>

			<@ww.datepicker label="Admissão" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" cssClass="mascaraData" required="true" onblur="${funcaoDataAdmissao}" onchange="${funcaoDataAdmissao}"/>

			<@ww.select label="Colocação" name="colaborador.vinculo" list="vinculos" cssStyle="width: 150px;"/>
			<@ww.textfield label="Regime de Revezamento (PPP)" name="colaborador.regimeRevezamento" id="regimeRevezamento" cssStyle="width:353px;" maxLength="50"/>

			<li>
				<@ww.div cssClass="divInfo" >
					<ul>

						<#if somenteLeitura == "true">
							<#assign msgHistorico = "última"/>
						<#else>
							<#assign msgHistorico = "1ª"/>
						</#if>

						<h2>Dados da ${msgHistorico} Situação</h2>
						<#if somenteLeitura == "true">
							<@ww.textfield label="Data" name="historicoColaborador.data" value="${dataHist}" id="dt_hist" cssStyle="width:71px;" required="true" disabled= "${somenteLeitura}"/>
						<#else>
							<@ww.datepicker label="Data" name="historicoColaborador.data" value="${dataHist}" id="dt_hist" cssClass="mascaraData" required="true" disabled= "${somenteLeitura}"/>
						</#if>


						<#assign funcaoEstabelecimento="populaAmbiente(this.value,null);"/>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<#assign funcaoEstabelecimento=""/>
						</@authz.authorize>

						<@ww.select label="Estabelecimento" name="historicoColaborador.estabelecimento.id" id="estabelecimento" list="estabelecimentos" required="true" listKey="id" listValue="nome" headerKey="" disabled= "${somenteLeitura}" headerValue="Selecione..." cssStyle="width: 355px;" onchange="${funcaoEstabelecimento}"/>
						<@ww.select label="Área Organizacional" name="historicoColaborador.areaOrganizacional.id" id="areaOrganizacional" list="areaOrganizacionals" required="true" listKey="id" listValue="descricao" headerKey="" disabled= "${somenteLeitura}" headerValue="Selecione..." cssStyle="width: 355px;" onchange="verificaMaternidade(this.value, 'areaOrganizacional');"/>
						
						
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
						
							Ambiente: <img id="ambienteTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
							<@ww.select name="historicoColaborador.ambiente.id" theme="simple" id="ambiente" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhum" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
							
							<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixas" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="populaFuncao(this.value);calculaSalario();" disabled= "${somenteLeitura}" cssStyle="width: 355px;"/>
							Função: <img id="funcaoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
							<@ww.select name="historicoColaborador.funcao.id" id="funcao" list="funcoes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhuma" disabled= "${somenteLeitura}" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
						
						</@authz.authorize>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixas" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="calculaSalario();" disabled= "${somenteLeitura}" cssStyle="width: 355px;"/>
						</@authz.authorize>
						
						
						<@ww.select label="Exposição a Agentes Nocivos (Código GFIP)" name="historicoColaborador.gfip" id="gfip" list="codigosGFIP" headerKey="" headerValue="Selecione..." disabled= "${somenteLeitura}" cssStyle="width: 355px;"/>

						<@ww.select label="Salário Proposto" name="salarioPropostoPor" id="tipoSalario" list="tiposSalarios"  headerValue="Selecione..." headerKey="" disabled="${somenteLeitura}" required="true" onchange="alteraTipoSalario(this.value);calculaSalario();alteraArrayValidacao(this.value);"/>
						<div id="valorDiv" style="display:none">
							<ul>
								<@ww.textfield label="Valor" name="salarioColaborador" required="true" id="salarioProposto" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" disabled="${somenteLeitura}"/>
							</ul>
						</div>
						<div id="indiceDiv" style="display:none">
							<ul>
								<@ww.select label="Índice" name="indice.id" id="indice" required="true" list="indices" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" liClass="liLeft" onchange="calculaSalario();" disabled="${somenteLeitura}"/>
								<@ww.textfield label="Qtd." name="quantidadeIndice" id="quantidade" required="true" onchange="calculaSalario();" onkeypress="return(somenteNumeros(event,'{,}'));" cssStyle="width: 30px;text-align:right;" maxLength="6" disabled="${somenteLeitura}"/>
							</ul>
						</div>
						<div id="valorCalculadoDiv">
							<ul>
								<@ww.textfield label="Valor" name="valorCalculado" id="salarioCalculado" cssStyle="width:85px; text-align:right;" disabled= "true" cssClass="currency" onchange="window.alert(this)" />
							</ul>
						</div>
					</ul>
				</@ww.div>
			</li>

			<#if somenteLeituraIntegraAC== "true">
				<@ww.hidden name="colaborador.matricula" />
			</#if>

			<#if somenteLeitura == "true">
				<@ww.hidden name="salarioColaborador" />
				<@ww.hidden name="historicoColaborador.funcao.id" />
				<@ww.hidden name="historicoColaborador.areaOrganizacional.id" />
				<@ww.hidden name="historicoColaborador.estabelecimento.id" />
				<@ww.hidden name="historicoColaborador.data" />
				<@ww.hidden name="historicoColaborador.faixaSalarial.id" />
			</#if>
		</div>

		  <div id="content5" style="display: none;">
			<b><@ww.label label="Identidade" /></b>
	    	<@ww.textfield label="Número" name="colaborador.pessoal.rg" id="rg" cssStyle="width: 106px;" maxLength="15" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));" />
	  	   	<@ww.textfield label="Órgão Emissor" name="colaborador.pessoal.rgOrgaoEmissor" cssStyle="width: 73px;" maxLength="10" liClass="liLeft" />
	       	<@ww.select label="Estado" name="colaborador.pessoal.rgUf.id" id="rgUf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
	      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.rgDataExpedicao" id="rgDataExpedicao" cssClass="mascaraData" value="${rgDataExpedicao}"/>
	        <li><hr /></li>
	       	<b><@ww.label label="Carteira de Habilitação" /></b>
			<@ww.textfield label="Número" name="colaborador.habilitacao.numeroHab" cssStyle="width: 80px;" maxLength="11" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
	      	<@ww.textfield label="Registro" name="colaborador.habilitacao.registro" cssStyle="" maxLength="15" liClass="liLeft"/>
	      	<@ww.datepicker label="Emissão" name="colaborador.habilitacao.emissao" id="emissao" liClass="liLeft" cssClass="mascaraData" value="${habEmissao}"/>
	      	<@ww.datepicker label="Vencimento" name="colaborador.habilitacao.vencimento" id="vencimento" liClass="liLeft" cssClass="mascaraData" value="${dataVenc}"/>
	       	<@ww.textfield label="Categoria(s)" name="colaborador.habilitacao.categoria" cssStyle="width:25px" maxLength="3" />
	        <li><hr /></li>
			<b><@ww.label label="Título Eleitoral" /></b>
	    	<@ww.textfield label="Número" name="colaborador.pessoal.tituloEleitoral.titEleitNumero" id="titEleitNumero" cssStyle="width: 95px;" maxLength="13" liClass="liLeft"/>
	    	<@ww.textfield label="Zona" name="colaborador.pessoal.tituloEleitoral.titEleitZona" id="titEleitZona" cssStyle="width: 95px;" maxLength="3" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
	    	<@ww.textfield label="Seção" name="colaborador.pessoal.tituloEleitoral.titEleitSecao" id="titEleitSecao" cssStyle="width: 95px;" maxLength="4" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
	        <li><hr /></li>
			<b><@ww.label label="Certificado Militar" /></b>
	    	<@ww.textfield label="Número" name="colaborador.pessoal.certificadoMilitar.certMilNumero" id="certMilNumero" cssStyle="width: 88px;" maxLength="12" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
	    	<@ww.textfield label="Tipo" name="colaborador.pessoal.certificadoMilitar.certMilTipo" id="certMilTipo" cssStyle="width: 38px;" maxLength="5" liClass="liLeft"/>
	    	<@ww.textfield label="Série" name="colaborador.pessoal.certificadoMilitar.certMilSerie" id="certMilSerie" cssStyle="width: 88px;" maxLength="12"/>
	        <li><hr /></li>
			<b><@ww.label label="CTPS - Carteira de Trabalho e Previdência Social" /></b>
	    	<@ww.textfield label="Número" name="colaborador.pessoal.ctps.ctpsNumero" id="ctpsNumero" cssStyle="width: 58px;" maxLength="8" liClass="liLeft"/>
	    	<@ww.textfield label="Série" name="colaborador.pessoal.ctps.ctpsSerie" id="ctpsSerie" cssStyle="width: 38px;" maxLength="6" liClass="liLeft"/>
	    	<@ww.textfield label="DV" name="colaborador.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 9px;" maxLength="1" liClass="liLeft"/>
	       	<@ww.select label="Estado" name="colaborador.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
	      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" cssClass="mascaraData" value="${ctpsDataExpedicao}"/>
	        <li><hr /></li>
			<b><@ww.label label="PIS - Programa de Integração Social"/></b>
			<@ww.textfield label="Número" name="colaborador.pessoal.pis" id="pis" cssClass="mascaraPis" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,'{,}'));" maxLength="11" />
	      </div>

		<#if habilitaCampoExtra>
			<div id="content6" style="display: none;">
				<#include "camposExtras.ftl" />
		    </div>
		</#if>
		<@ww.hidden name="colaborador.camposExtras.id" />			
	    
		<@ww.hidden name="colaborador.cursos" id="colaborador.cursos" />
		<@ww.hidden name="colaborador.observacao" id="colaborador.observacao" />
		<@ww.hidden name="colaborador.usuario.id" />
		<@ww.hidden name="colaborador.codigoAC" />
		<@ww.hidden name="colaborador.empCodigoAC" />
		<@ww.hidden name="colaborador.desligado" />
		<@ww.hidden name="colaborador.dataDesligamento" />
		<@ww.hidden name="colaborador.id" />
		<@ww.hidden name="nomeBusca" />
		<@ww.hidden name="cpfBusca" />
		<@ww.hidden name="colaborador.empresa.id" />
		<@ww.hidden name="colaborador.estabelecimento.id" />
		<@ww.hidden name="solicitacao.id" />

		<@ww.hidden name="colaborador.pessoal.naturalidade" />
		<@ww.hidden name="colaborador.pessoal.profissaoPai" />
		<@ww.hidden name="colaborador.pessoal.profissaoMae" />
		<@ww.hidden name="colaborador.pessoal.profissaoConjuge" />
		<@ww.hidden name="colaborador.pessoal.conjugeTrabalha" />
		<@ww.hidden name="colaborador.pessoal.parentesAmigos" />
		<@ww.hidden name="colaborador.pessoal.qtdFilhos" />
		<@ww.hidden name="colaborador.candidato.id"/>

		<@ww.hidden name="page" />
		<#if candidato?exists>
			<@ww.hidden name="idCandidato" value="${candidato.id}"/>
		</#if>
		<@ww.token/>
	</@ww.form>

	<#-- Campo para controle das abas -->
	<@ww.hidden id="aba" name="aba" value="1"/>

	<script>
		document.getElementById('desCursos').value = document.getElementById('colaborador.cursos').value;
		document.getElementById('obs').value = document.getElementById('colaborador.observacao').value;
	</script>

	<br>
	<div class="buttonGroup">
		<div style="float: left;width: 50%;">

			<button onclick="if (setaCampos()) ${validarCampos};" id="gravar" <#if !colaborador.id?exists> </#if> class="btnGravar" accesskey="${accessKey}">
			</button>

			<#if nomeBusca?exists && cpfBusca?exists>
				<button onclick="window.location='list.action?nomeBusca=${nomeBusca}&cpfBusca=${cpfBusca}'" class="btnCancelar" accesskey="C">
			<#else>
				<button onclick="window.location='list.action'" class="btnCancelar" accesskey="C">
			</#if>
			</button>
		</div>
		<div style="text-align: right;">
			<button id='voltar' disabled="disabled" onclick="abas(-1, 'V', ${edicao}, ${totalAbas});" class="btnVoltarDesabilitado" ></button>
			<button id='avancar' onclick="abas(-1, 'A', ${edicao}, ${totalAbas});" class="btnAvancar" ></button>
		</div>
	</div>

	<script type="text/javascript">
		function mostraFoto()
		{
			mostrar(document.getElementById('fotoUpLoad'));
		}

		alteraTipoSalario(document.getElementById("tipoSalario").value);
		alteraArrayValidacao(document.getElementById("tipoSalario").value);
	</script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js"/>'></script>
</body>

</html>