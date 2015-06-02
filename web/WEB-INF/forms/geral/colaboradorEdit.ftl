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
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PessoaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js"/>'></script>
	<script type="text/javascript" src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<#-- <script type="text/javascript" src="jsr_class.js"></script> -->

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/colaborador.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>


	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
	
		#parentesDialog { display: none; }
		#parentesDialog li { margin: 5px 0px; }
		#parentesDialog .divInfoColab, #parentesDialog .divInfoColabDestaque { padding: 5px; margin: 5px 0px; border: 1px solid #BEBEBE; font-size: 10px; }
		#parentesDialog .divInfoColab { background-color: #E9E9E9; }
		#parentesDialog .divInfoColabDestaque { margin-bottom: 10px; }
		#parentesDialog .xz { background-color:#fbfa99; color:red; }
		#parentesDialog table { width: 100%; }
		#parentesDialog td { width: 50%; vertical-align: top; }
	</style>

	<#include "../cargosalario/calculaSalarioInclude.ftl" />

    <#if empresaId?exists>
      <#assign idDaEmpresa=empresaId/>
    <#else>
      <#assign idDaEmpresa><@authz.authentication operation="empresaId"/></#assign>
    </#if>

	<@ww.head />

	<#if colaborador.id?exists>
		<title>Editar Colaborador</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign edicao="true"/>
		<#assign somenteLeitura="true"/>
		<#assign funcaoNome=""/>
		<#if editarHistorico>
			<#assign funcaoDataAdmissao="sugerirDataHistorico();"/>
		<#else>
			<#assign funcaoDataAdmissao=""/>
		</#if>	
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
	<#assign edit="colaborador"/>
	

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
	
	<#if colaborador?exists && colaborador.dataEncerramentoContrato?exists>
		<#assign dataEncerramentoContrato = colaborador.dataEncerramentoContrato?date/>
	<#else>
		<#assign dataEncerramentoContrato = ""/>
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
		
		<#assign totalAbas = 7/>
	<#else>
		<#assign totalAbas = 6/>
	</#if>

	<#assign validarCampos="validaFormularioDinamico();"/>

	<script type="text/javascript">
		var colaboradorId = null;
		<#if colaborador.id?exists>
			colaboradorId = ${colaborador.id};
		</#if>	
		
		$(function() {
			
				
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action"/>');
			
			$('#ambienteTooltipHelp').qtip({
				content: '<strong>Ambiente</strong><br />Necessário para o PPP e PPRA.',
				style: { width: '100px' }
			});
			
			$('#funcaoTooltipHelp').qtip({
				content: '<strong>Função</strong><br />Necessário para o PPP e PPRA.',
				style: {  width: '100px' }
			});
			
			$('#dataAdmissaoTooltipHelp').qtip({ content: 'Não é possível alterar a data de admissão quando integrado com o AC Pessoal.' });
			$('#vinculoTooltipHelp').qtip({ content: 'Não é possível alterar o vínculo quando integrado com o AC Pessoal.' });
			
			addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');

			<#if avaliacoes?exists>
				<#list avaliacoes as avaliacao>
					<#if avaliacao.periodoExperiencia?exists && avaliacao.periodoExperiencia.id?exists>
				    	$('#modeloPeriodo' + ${avaliacao.periodoExperiencia.id}).append("<option value='${avaliacao.id}'>${avaliacao.titulo}</option>");
				    	$('#modeloPeriodoGestor' + ${avaliacao.periodoExperiencia.id}).append("<option value='${avaliacao.id}'>${avaliacao.titulo}</option>");
					</#if>
				</#list>
			</#if>

			<#if colaboradorAvaliacoes?exists>
				<#list colaboradorAvaliacoes as colaboradorAvaliacao>
					<#if colaboradorAvaliacao.periodoExperiencia?exists && colaboradorAvaliacao.periodoExperiencia.id?exists && colaboradorAvaliacao.avaliacao?exists && colaboradorAvaliacao.avaliacao.id?exists>
					    <#if colaboradorAvaliacao.tipo == 'G'>
					    	$('#modeloPeriodoGestor' + ${colaboradorAvaliacao.periodoExperiencia.id}).val(${colaboradorAvaliacao.avaliacao.id});
					    <#else>
					    	$('#modeloPeriodo' + ${colaboradorAvaliacao.periodoExperiencia.id}).val(${colaboradorAvaliacao.avaliacao.id});
						</#if>
					</#if>
				</#list>
			</#if>
			
			<#if edicao == "false">
				$('#nomePai, #nomeMae, #nomeConjuge, #nome').blur(function() {
					if (this.value) verificaParentes(colaboradorId, [this.value]);
				});
				
				<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
					if ($('#estabelecimento').val() && !$('#ambiente').val()) {
						populaAmbiente($('#estabelecimento').val(), null);
					}
				</@authz.authorize>
			</#if>
			
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<#if historicoColaborador?exists &&  historicoColaborador.faixaSalarial?exists && historicoColaborador.faixaSalarial.id?exists 
				&& (!historicoColaborador.funcao?exists || (historicoColaborador.funcao?exists && !historicoColaborador.funcao.id?exists))> 
					populaFuncao(${historicoColaborador.faixaSalarial.id});
				</#if>
			</@authz.authorize>
			
			habilitaDtEncerramentoContrato();
		});
		
		function habilitaDtEncerramentoContrato()
		{
			if ($('#vinculo').val() == 'E')
			{
				$('#dt_encerramentoContrato_button').hide();
				$('#dt_encerramentoContrato').attr('disabled', 'disabled').css('background', '#F6F6F6');
				$('#dt_encerramentoContrato').val('');
			} else {
				$('#dt_encerramentoContrato').removeAttr('disabled').css('background', '#FFFFFF');
				$('#dt_encerramentoContrato_button').show();
				
			}
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
		
		function createListAmbiente(data)
		{
			DWRUtil.removeAllOptions("ambiente");
			DWRUtil.addOptions("ambiente", data);
			orderSelectByNome("ambiente");
		}
		
		function orderSelectByNome(id) {
			var options = $('select#'+id+' option');
			var arr = options.map(function(_, o) { return { t: $(o).text(), v: o.value }; }).get();
			arr.sort(function(o1, o2) { return o1.t > o2.t ? 1 : o1.t < o2.t ? -1 : 0; });
			options.each(function(i, o) {
			  o.value = arr[i].v;
			  $(o).text(arr[i].t);
			});
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
			orderSelectByNome("funcao");
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
				jAlert("Código " + document.getElementById('codCbo').value + " não encontrado.");
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
			document.getElementById('nomeComercial').value = document.getElementById('nome').value.substr(0,30).trim();
		}
		
		function sugerirDataHistorico()
		{
			$('#dt_hist, #dt_hist_hidden').val($('#dt_admissao').val());
		}

		function verificaCpf(data)
	    {
			<#if colaborador.id?exists>
				verificaCpfDuplicado(data, ${idDaEmpresa}, null, ${colaborador.id},  false);
			<#elseif candidato?exists>
				verificaCpfDuplicado(data, ${idDaEmpresa}, null, ${candidato.id}, true);
			<#else>
				verificaCpfDuplicado(data, ${idDaEmpresa}, null, null, false);
			</#if>			
	    }
	    
	    function verificaParentes(colaboradorId, nomes, validaForm)
		{
			$('#parentesDialog').empty();
	    	ColaboradorDWR.findParentesByNome(	colaboradorId, <@authz.authentication operation="empresaId"/>, nomes, function(dados) { listaParentes(dados, '<@authz.authentication operation="empresaNome"/>', validaForm ); });
		}

		var arrayValidacao = new Array('nome','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario');

		function alteraArrayValidacao(tipo)
		{
			if(tipo == ${tipoSalario.getIndice()})
				arrayValidacao = new Array('nome','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario','indice','quantidade');
			else if(tipo == ${tipoSalario.getValor()})
				arrayValidacao = new Array('nome','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario','salarioProposto');
			else
				arrayValidacao = new Array('nome','nascimento','cpf','ende','num','uf','cidade','ddd','fone','escolaridade','nomeComercial','dt_admissao','dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario');
		}
		exibeLabelDosCamposNaoPreenchidos = true;
		function validaFormularioDinamico(noSubmit)
		{
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<#if obrigarAmbienteFuncao && somenteLeitura == "false">
					arrayValidacao.push('ambiente','funcao');
				</#if>
			</@authz.authorize>
			
			return validaFormulario('form', arrayValidacao, new Array('email', 'nascimento', 'cpf', 'cep', 'dt_admissao','dt_encerramentoContrato', 'emissao', 'vencimento','rgDataExpedicao','ctpsDataExpedicao', 'pis' ${validaDataCamposExtras}), (noSubmit == true ? true : false));
		}
		
		function submit()
		{
			if (setaCampos() && validaFormularioDinamico(true))
			{
				verificaParentes(colaboradorId, [$('#nomePai').val(), $('#nomeMae').val(), $('#nomeConjuge').val(), $('#nome').val()], validaFormularioDinamico);
			}
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
		<div id="aba6"><a href="javascript: abas(6, '', ${edicao}, ${totalAbas})">Modelos de Avaliação</a></div>
		
		<#if habilitaCampoExtra>
			<div id="aba7"><a href="javascript: abas(7, '', ${edicao}, ${totalAbas})">Extra</a></div>
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

	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<div id="content1">

			<#assign somenteLeituraIntegraAC="false" />

			<#if integraAc>
				<#if !colaborador.id?exists>
					<@ww.checkbox label="Não enviar este colaborador para o AC Pessoal" id="naoIntegraAc" name="colaborador.naoIntegraAc" liClass="liLeft" labelPosition="left" onchange="$('#wwgrp_obsACPessoal').toggle(!this.checked)"/>
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


			<@ww.textfield label="Nome" name="colaborador.nome" id="nome" required="true" liClass="liLeft" cssStyle="width: 300px;" maxLength="60" onblur="${funcaoNome}"/>

			<@ww.textfield label="Nome Comercial" name="colaborador.nomeComercial" id="nomeComercial" required="true" cssStyle="width: 300px;" maxLength="30"/>

			<@ww.datepicker label="Nascimento" name="colaborador.pessoal.dataNascimento" value="${dataNasc}" id="nascimento" required="true" liClass="liLeft" cssClass="mascaraData"/>

			<@ww.select label="Sexo"   name="colaborador.pessoal.sexo" list="sexos" cssStyle="width: 85px;" liClass="liLeft" />
			<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpf" required="true" cssClass="mascaraCpf"  onchange="verificaCpf(this.value);" onblur="verificaCpf(this.value);" />
			<@ww.div id="msgCPFDuplicado" cssStyle="display:none;"></@ww.div>
			<@ww.textfield label="CEP" name="colaborador.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft" />
			<@ww.textfield label="Logradouro" name="colaborador.endereco.logradouro" id="ende" required="true" cssStyle="width: 300px;" liClass="liLeft" maxLength="40"/>
			<@ww.textfield label="Nº"  name="colaborador.endereco.numero" id="num" required="true" cssStyle="width:40px;" liClass="liLeft" maxLength="10"/>
			<@ww.textfield label="Complemento" name="colaborador.endereco.complemento" id="complemento" cssStyle="width: 205px;" maxLength="20"/>
			<@ww.select label="Estado"     name="colaborador.endereco.uf.id" id="uf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" />
			<@ww.select label="Cidade" name="colaborador.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" required="true" />
			<@ww.textfield label="Bairro" name="colaborador.endereco.bairro" id="bairroNome" cssStyle="width: 325px;" maxLength="85"/>
			<@ww.div id="bairroContainer"/>

			<@ww.textfield label="E-mail"    name="colaborador.contato.email" id="email" cssClass="mascaraEmail" maxLength="200" liClass="liLeft"/>
			<@ww.textfield label="DDD" name="colaborador.contato.ddd" required="true" id="ddd" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" maxLength="2" cssStyle="width:25px;"/>
			<@ww.textfield label="Telefone"  name="colaborador.contato.foneFixo" required="true" id="fone" onkeypress = "return(somenteNumeros(event,''));" maxLength="9" liClass="liLeft" cssStyle="width:80px;"/>
			<@ww.textfield label="Celular"   name="colaborador.contato.foneCelular" onkeypress = "return(somenteNumeros(event,''));" id="celular" maxLength="9" cssStyle="width:80px;"/>
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

			<#if somenteLeituraIntegraAC=="true" && edicao=="true">
				<label for="dt_admissao">Admissão:</label><br />
				<input type="text" theme="simple" disabled="true" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" class="mascaraData"/>
				<img id="dataAdmissaoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />

				<br clear="all"/>

				<label for="colocacao">Colocação:</label><br />
				<@ww.select theme="simple" label="Colocação" disabled="true" name="colaborador.vinculo" list="vinculos" onchange="habilitaDtEncerramentoContrato();" id="vinculo" cssStyle="width: 150px;"/>
				<img id="vinculoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
				
				<br clear="all"/>
				
				<@ww.hidden  name="colaborador.dataAdmissao" value="${dataAdm}" />
				<@ww.hidden  name="colaborador.vinculo" />
			<#else>
			
				<#if editarHistorico>
					<@ww.datepicker label="Admissão" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" cssClass="mascaraData" required="true" onblur="${funcaoDataAdmissao}" onchange="${funcaoDataAdmissao}"/>
				<#else>
					<label for="dt_admissao">Admissão:</label><br />
					<input type="text" theme="simple" disabled="true" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" style="background:#F6F6F6;" class="mascaraData"/>
					<@ww.hidden  name="colaborador.dataAdmissao" value="${dataAdm}" />
					<br clear="all"/>
				</#if>
			
				<@ww.select label="Colocação" name="colaborador.vinculo" list="vinculos" cssStyle="width: 150px;"  id="vinculo" onchange="habilitaDtEncerramentoContrato();" />
			</#if>
			<@ww.datepicker label="Encerramento do Contrato" name="colaborador.dataEncerramentoContrato" value="${dataEncerramentoContrato}" id="dt_encerramentoContrato" cssClass="mascaraData"/>
			
			<@ww.textfield label="Regime de Revezamento (PPP)" name="colaborador.regimeRevezamento" id="regimeRevezamento" cssStyle="width:353px;" maxLength="255"/>

			<li>
				<@ww.div cssClass="divInfo" >
					<ul>

						<#if somenteLeitura == "true">
							<#assign msgHistorico = "última"/>
						<#else>
							<#assign msgHistorico = "1ª"/>
						</#if>

						<h2>Dados da ${msgHistorico} Situação</h2>
						<@ww.textfield label="Data" name="historicoColaborador.data" value="${dataHist}" id="dt_hist" cssClass="mascaraData" cssStyle="background:#F6F6F6;" disabled="true"/>
						<@ww.hidden  id="dt_hist_hidden" name="historicoColaborador.data" value="${dataHist}" />

						<#assign funcaoEstabelecimento="populaAmbiente(this.value,null);"/>
						<@authz.authorize ifNotGranted="ROLE_COMPROU_SESMT">
							<#assign funcaoEstabelecimento=""/>
						</@authz.authorize>

						<@ww.select label="Estabelecimento" name="historicoColaborador.estabelecimento.id" id="estabelecimento" list="estabelecimentos" required="true" listKey="id" listValue="nome" headerKey="" disabled= "${somenteLeitura}" headerValue="Selecione..." cssStyle="width: 355px;" onchange="${funcaoEstabelecimento}"/>
						<@ww.select label="Área Organizacional" name="historicoColaborador.areaOrganizacional.id" id="areaOrganizacional" list="areaOrganizacionals" required="true" listKey="id" listValue="descricao" headerKey="" disabled= "${somenteLeitura}" headerValue="Selecione..." cssStyle="width: 355px;" onchange="verificaMaternidade(this.value, 'areaOrganizacional');"/>
						
						
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
						
							<label for="ambiente">Ambiente:</label><#if obrigarAmbienteFuncao>*</#if> <img id="ambienteTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
							<@ww.select name="historicoColaborador.ambiente.id" theme="simple" id="ambiente" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhum" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
							
							<@ww.select label="Cargo/Faixa" name="historicoColaborador.faixaSalarial.id" id="faixa" list="faixas" listKey="id" listValue="descricao" required="true" headerKey="" headerValue="Selecione..." onchange="populaFuncao(this.value);calculaSalario();" disabled= "${somenteLeitura}" cssStyle="width: 355px;"/>
							
							<label for="funcao">Função:</label><#if obrigarAmbienteFuncao>*</#if> <img id="funcaoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /><br>
							<@ww.select name="historicoColaborador.funcao.id" id="funcao" theme="simple" list="funcoes" listKey="id" listValue="nome" headerKey="" headerValue="Nenhuma" disabled= "${somenteLeitura}" cssStyle="width: 355px;" disabled="${somenteLeitura}"/>
						
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
								<@ww.textfield label="Valor" name="valorCalculado" id="salarioCalculado" cssStyle="width:85px; text-align:right;" disabled= "true" cssClass="currency" onchange="window.jAlert(this)" />
							</ul>
						</div>
						
						<#if integraAc>
							<@ww.textfield label="Observação para o Setor Pessoal" name="obsACPessoal" id="obsACPessoal" cssStyle="width:355px;" disabled="${somenteLeitura}" maxLength="100"/>
						</#if>
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
			<@ww.textfield label="Nº de Registro" name="colaborador.habilitacao.numeroHab" cssStyle="width: 100px;" maxLength="11" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
	      	<@ww.textfield label="Prontuário" name="colaborador.habilitacao.registro" cssStyle="" maxLength="15" liClass="liLeft"/>
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
	    	<@ww.textfield label="DV" name="colaborador.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 11px;" maxLength="1" liClass="liLeft"/>
	       	<@ww.select label="Estado" name="colaborador.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
	      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" cssClass="mascaraData" value="${ctpsDataExpedicao}"/>
	        <li><hr /></li>
			<b><@ww.label label="PIS - Programa de Integração Social"/></b>
			<@ww.textfield label="Número" name="colaborador.pessoal.pis" id="pis" cssClass="mascaraPis" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,'{,}'));" maxLength="11" />
	      </div>

		<div id="content6" style="display: none;">
			<fieldset>
				<legend>Colaborador</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados">
					<@display.column title="Dias" property="dias" style="width:80px;" />
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoes[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" name="colaboradorAvaliacoes[${i}].avaliacao.id" id="modeloPeriodo${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>

			<br />

			<fieldset>
				<legend>Gestor</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados">
					<@display.column title="Dias" property="dias" style="width:80px;" />
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoesGestor[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" name="colaboradorAvaliacoesGestor[${i}].avaliacao.id" id="modeloPeriodoGestor${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>
	    </div>

		<#if habilitaCampoExtra>
			<div id="content7" style="display: none;">
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
		<@ww.hidden name="colaborador.solicitacao.id"/>
		<@ww.hidden name="encerrarSolicitacao" />
		
		<@ww.hidden name="colaborador.pessoal.naturalidade" />
		<@ww.hidden name="colaborador.pessoal.profissaoPai" />
		<@ww.hidden name="colaborador.pessoal.profissaoMae" />
		<@ww.hidden name="colaborador.pessoal.profissaoConjuge" />
		<@ww.hidden name="colaborador.pessoal.conjugeTrabalha" />
		<@ww.hidden name="colaborador.pessoal.parentesAmigos" />
		<@ww.hidden name="colaborador.pessoal.qtdFilhos" />
		<@ww.hidden name="colaborador.candidato.id"/>
		<@ww.hidden name="colaborador.motivoDemissao.id"/>
		<@ww.hidden name="colaborador.observacaoDemissao"/>
		<@ww.hidden name="colaborador.dataSolicitacaoDesligamento"/>
		<@ww.hidden name="colaborador.dataSolicitacaoDesligamentoAc"/>
		<@ww.hidden name="candidatoSolicitacaoId"/>
		
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
		<div style="width: 440px; float: right; text-align: right;">
			<button id='voltar' disabled="disabled" onclick="abas(-1, 'V', ${edicao}, ${totalAbas});" class="btnVoltarDesabilitado" ></button>
			<button id='avancar' onclick="abas(-1, 'A', ${edicao}, ${totalAbas});" class="btnAvancar" ></button>
		</div>

		<div style="width: 440px;">
			<button onclick="submit();" id="gravar" <#if !colaborador.id?exists> </#if> class="btnGravar" accesskey="${accessKey}">
			</button>

			<#if nomeBusca?exists && cpfBusca?exists>
				<button onclick="window.location='list.action?nomeBusca=${nomeBusca}&cpfBusca=${cpfBusca}'" class="btnCancelar" accesskey="C">
			<#else>
				<button onclick="window.location='list.action'" class="btnCancelar" accesskey="C">
			</#if>
			</button>
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
	
	<div id="parentesDialog"></div>
</body>

</html>