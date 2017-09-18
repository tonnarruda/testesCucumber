<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/colaborador.css?version=${versao}"/>" media="screen" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/font-awesome.min.css?version=${versao}"/>" type="text/css">
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/moment.min.2.18.1.js?version=${versao}"/>'></script>

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
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PessoaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ReajusteDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FuncaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AmbienteDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<#-- <script type="text/javascript" src="jsr_class.js"></script> -->

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/colaborador.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>


	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

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
		#wwgrp_dt_encerramentoContrato { margin-top: 5px; }
		input[disabled], select[disabled] { background: #EFEFEF; }
		input[readonly] { background: #EFEFEF; }
		#divDecideAlteracaoOrRetificacao { display: none; }
		#divInformeDataAlteracao { display: none; }
		.calendar{ z-index: 1010 !important;}
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
	
	<#if colaborador?exists && colaborador.dataDesligamento?exists>
		<#assign dataDesligamento = colaborador.dataDesligamento?date/>
	<#else>
		<#assign dataDesligamento = ""/>
	</#if>
	
	<#if colaborador?exists && colaborador.nome?exists>
		<#assign nomeColaborador = colaborador.nome/>
	<#else>
		<#assign nomeColaborador = ""/>
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
	<#assign dadosIntegradosAtualizados = "${dadosIntegradosAtualizados?string}"/>
	
	<script type="text/javascript">
		var colaboradorId = null;
		<#if colaborador.id?exists>
			colaboradorId = ${colaborador.id};
		</#if>	
		
		var camposColaboradorVisivel = "${parametrosDoSistema.camposColaboradorVisivel}";
		var camposColaboradorObrigatorio = "${camposColaboradorObrigatorio}";

		var abasVisiveis = "${parametrosDoSistema.camposColaboradorTabs}";
		var arrayAbasVisiveis  = abasVisiveis.split(',');
		qtdAbas = arrayAbasVisiveis.length;
		var arrayObrigatorios = new Array();
		var colaboradorMap = new Object();
		
		$(function() {
			$(".campo").each(function(){
				var campos = camposColaboradorVisivel.split(',');
				var id = this.id.replace('wwgrp_', '');
				var idNaoEncontrado = ($.inArray(id, campos) == -1);
			    if (idNaoEncontrado)
					$(this).hide();
			});	
			
			<#if colaborador.id?exists>
				$(".campoAdd").each(function(){
					var id = this.id.replace('wwgrp_', '');
					if(id == 'cpf' || id =='cep'){
						colaboradorMap[id] = replaceAll($('#' + id).val().replace('-',''),'.','');
					}else{
						if($('#' + id).val() == "  /  /    ")
							colaboradorMap[id] = "";
						else
							colaboradorMap[id] = $('#' + id).val();
					}
				});		
			</#if>	

			$.each(camposColaboradorObrigatorio.split(','), function (index, idCampo) {
			    var lblAntigo = $('label[for='+idCampo+']');
			    lblAntigo.text(lblAntigo.text().replace(/\s$/, '') + "*");
			});
			
			if(camposColaboradorObrigatorio != "")
				arrayObrigatorios = camposColaboradorObrigatorio.split(',');
				
			$('#abas div').each(function(){
					var abaNaoEncontrada = ($.inArray($(this).attr('class'), arrayAbasVisiveis) == -1);
			        if (abaNaoEncontrada)
			            $(this).hide();
			});
			
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
			
			$('#vinculoSocioTooltipHelp').qtip({ content: 'Colaboradores com a colocação Sócio, não serão integrados com o Fortes Pessoal.' });
			$('#dataAdmissaoTooltipHelp').qtip({ content: 'Não é possível alterar a data de admissão quando integrado com o Fortes Pessoal.' });
			$('#vinculoTooltipHelp').qtip({ content: 'Não é possível alterar o vínculo quando integrado com o Fortes Pessoal.' });
			$('#pisTooltipHelp').qtip({ content: 'O PIS é obrigatório para as seguintes colocações: Empregado, Aprendiz, Temporário e Sócio.<br>É opcional para a colocação de Estagiário.' });
			
			<#if colaborador.id?exists && integraAc && !colaborador.naoIntegraAc>
				$('.naturalidadeAndNacionalidadeHelp').qtip({
					content: '<div style="text-align:justify">Informações do sistema Fortes Pessoal.</div>',
					style: { width: 180 }
				});
			</#if>
			
			addBuscaCEP('cep', 'endereco', 'bairroNome', 'cidade', 'uf');

			<#if avaliacoes?exists>
				<#list avaliacoes as avaliacao>
					<#if avaliacao.periodoExperiencia?exists && avaliacao.periodoExperiencia.id?exists>
				    	$('#modeloPeriodo' + ${avaliacao.periodoExperiencia.id}).append("<option value='${avaliacao.id}'>${avaliacao.tituloComDescricaoInativo} </option>");
				    	$('#modeloPeriodoGestor' + ${avaliacao.periodoExperiencia.id}).append("<option value='${avaliacao.id}'>${avaliacao.tituloComDescricaoInativo} </option>");
		    			<#if !avaliacao.periodoExperiencia.ativo>
		    				$('#modeloPeriodo' + ${avaliacao.periodoExperiencia.id}).attr('disabled', 'disabled');
		    				$('#modeloPeriodoGestor' + ${avaliacao.periodoExperiencia.id}).attr('disabled', 'disabled');
				    	</#if>
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
					if (this.value) verificaParentes(colaboradorId, [this.value], false);
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
			
			$("#naoIntegraAc").change(function(){
				$('#matricula').toggleDisabled();
				$('#wwgrp_obsACPessoal').toggle(!this.checked);
				$('#matricula').val("");
			})
			
			habilitaDtEncerramentoContrato();
			checkNaoIntegraAC();
			configurarObrigatoriedadeDoPis();
			configuraEdicaoCamposIntegrados();
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

		function checkNaoIntegraAC(){
			if ($('#integradaEAderiuAoESocial').val() == "false" && $('#isEdicao').val() == "false"){
				 var isVinculoSocio = $('#vinculo').val() == 'O';
				 $("#naoIntegraACSocio").parent().parent().parent().toggle(isVinculoSocio);
				 $("#naoIntegraAc").parent().parent().parent().toggle(!isVinculoSocio);
				 $("#naoIntegraACSocio").attr('checked', 'checked');
			}
			else
				$("#naoIntegraACSocio").parent().parent().parent().hide();
		}
		
		function configurarObrigatoriedadeDoPis()
		{
			var lblAntigo = $('label[for="pis"]');
			lblAntigo.text(lblAntigo.text().replace('*', ''));
			if($("#vinculo").val() != "S"){
				lblAntigo.text(lblAntigo.text().replace(/\s$/, '') + "*");
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
	    	ColaboradorDWR.findParentesByNome(colaboradorId, <@authz.authentication operation="empresaId"/>, nomes, function(dados) { 
	    		if(validaForm){
	    			if(listaParentesInsertColaborador(dados, '<@authz.authentication operation="empresaNome"/>'))
	    				validaFormularioDinamico(); 
	    		}else{
	    			listaParentes(dados, '<@authz.authentication operation="empresaNome"/>');
	    		}
	    	});
		}

		var arrayValidacao = arrayObrigatorios;

		function alteraArrayValidacao(tipo)
		{
			if(tipo == ${tipoSalario.getIndice()})
				arrayValidacao = arrayValidacao.concat(new Array('dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario','indice','quantidade'));
			else if(tipo == ${tipoSalario.getValor()})
				arrayValidacao = arrayValidacao.concat(new Array('dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario','salarioProposto'));
			else
				arrayValidacao = arrayValidacao.concat(new Array('dt_hist', 'estabelecimento','areaOrganizacional','faixa','tipoSalario'));
		}
		
		function validaFormularioDinamico()
		{
			marcaAbas = true;
			exibeLabelDosCamposNaoPreenchidos = true;
			desmarcarAbas();
		
			$('#formacao, #idioma, #expProfissional').css('backgroundColor','inherit');
		
			var msg = "Os seguintes campos são obrigatórios: <br />";
			var idiomaInvalido = $.inArray('idioma', arrayObrigatorios) > -1 && $('#idiomaTable tbody tr').size() < 1;
			var expInvalido = $.inArray('expProfissional', arrayObrigatorios) > -1 && $('#exp tbody tr').size() < 1;
			var formacaoInvalido = $.inArray('formacao', arrayObrigatorios) > -1 && $('#formacao tbody tr').size() < 1;

			if (formacaoInvalido){
    		   	marcarAbas('#formacao');
				$('#formacao').css('backgroundColor','#ffeec2');
    			msg += "Formação Escolar<br />";
			}

			if (idiomaInvalido){
	    		marcarAbas('#idioma');
				$('#idioma').css('backgroundColor','#ffeec2');
	    		msg += "Idiomas<br />";
			}

			if (expInvalido){
	    		marcarAbas('#expProfissional');
				$('#expProfissional').css('backgroundColor','#ffeec2');
	    		msg += "Experiência Profissional<br />";
			}
			
			if (formacaoInvalido || idiomaInvalido || expInvalido) {
	    		jAlert(msg);
	    		return false;
	    	}
			
			// valida os multicheckboxes
			arrayObrigatorios = $.map(arrayObrigatorios, function(item) {
				return item;
			});
			
			// valida os itens que constituem subformularios
			arrayObrigatorios = $.grep(arrayObrigatorios, function(value) {
				return value != 'formacao' && value != 'idioma' && value != 'expProfissional';
			});
			
			arrayValidacao = arrayObrigatorios;
			
			alteraArrayValidacao(document.getElementById("tipoSalario").value);
			
			<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
				<#if obrigarAmbienteFuncao && somenteLeitura == "false">
					arrayValidacao.push('ambiente','funcao');
				</#if>
			</@authz.authorize>
			
			if($("#vinculo").val() != "S")
				arrayValidacao.push('pis');
			
			$('.campo-integrado-select').removeAttr('disabled');
			
			var validaCampos = false;
			validaCampos = validaFormulario('form', arrayValidacao, new Array('email', 'nascimento', 'cpf', 'cep', 'dt_admissao','dt_encerramentoContrato', 'emissao', 'vencimento','rgDataExpedicao','ctpsDataExpedicao', 'pis' ${validaDataCamposExtras}), true);
			
			if(validaCampos){
				<#if colaborador.id?exists && colaborador.empresa.acIntegra && !colaborador.naoIntegraAc>
					<#if !desabilitarEdicaoCamposIntegrados && colaborador.codigoAC?exists &&  colaborador.codigoAC != "">
						dialogIntegraAc();
					<#else>
						$('#form').submit();
					</#if>
				<#else>
					$('#form').submit();
				</#if>
			}
			else
				configuraEdicaoCamposIntegrados();
		}
		
		function prepareSubmit() {
			var selectsAvalPeriodoExperiencia = $(".periodoExperiencia");
			var selectsAvalPeriodoExperienciaSelecionados = $(".periodoExperiencia[value!='']");
			if ( $(".abaModelosAvaliacao").is(":visible") && selectsAvalPeriodoExperiencia.length > 0 && selectsAvalPeriodoExperiencia.length > selectsAvalPeriodoExperienciaSelecionados.length ) {
				var corpo = "Existem modelos do acompanhamento do período de experiência não configurados: <br>";
				var modelosColaborador = [];
				var modelosGestor = [];
				
				$(selectsAvalPeriodoExperiencia).each(function(){
					if ( $.inArray(this, selectsAvalPeriodoExperienciaSelecionados) == -1 ) {
						if ($(this).parents("fieldset").find("legend").text() == "Colaborador")
							modelosColaborador.push(this);
						else
							modelosGestor.push(this);
					}
				});
				
				if(modelosColaborador.length > 0) {
					corpo += "<br> COLABORADOR: <br>";
					$(modelosColaborador).each(function(){
						corpo += " - "+$(this).parents("tr").find("td:eq(0)").text() + " dias <br>" ;
					});
				}
				
				if(modelosColaborador.length > 0) {
					corpo += "<br> GESTOR: <br>";
					$(modelosGestor).each(function(){
						corpo += " - "+$(this).parents("tr").find("td:eq(0)").text() + " dias <br>" ;
					});
				}
				
				corpo += "<br><br>Gravar mesmo assim?";
				
				$("<div>"+corpo+"</div>").dialog({ 	modal: true, 
													title: 'Modelos de avaliação não configurados',
													width: 400,
													buttons: 
													[
													    {
													        text: "Sim",
													        click: function() { $(this).dialog("close"); submiter(); }
													    },
													    {
													        text: "Não",
													        click: function() {
													        	abas(6, '', true, 6);
													        	$(this).dialog("close");
													        }
													    }
													]
												});
			} else {
				submiter();
			}
		}
		
		function submiter()
		{
			setaCampos();
			<#if edicao == "false">
				verificaParentes(colaboradorId, [$('#nomePai').val(), $('#nomeMae').val(), $('#nomeConjuge').val(), $('#nome').val()], true);
			<#else>
				validaFormularioDinamico();
			</#if>
		}
		
		function dialogIntegraAc(){
			if(!houveAlteracaoDosCampos()){
				$('#dataAlteracao').val(null);
				$('#form').submit();
			}else {
				$('#divDecideAlteracaoOrRetificacao').dialog({ 	
													modal: true, 
													title: 'Informações Pessoais',
													width: 480,
													height: 260,
													buttons: 
													[
													    {
													        text: "Gravar",
												        	click: function() { 
												        		if($('#podeEfetuarRetificacao').val() == "false" || $('input[name="tipoAlteracao"]:checked').length > 0){
													        		$('#dadosIntegradosAtualizados').val(true);
													        		validaDataAlteracao();
													        	}else{
														    		jAlert("Marque pelo menos uma das opções.");
													        	}
												        	}
													    },
													    {
													        text: "Cancelar",
												        	click: function() {
												        		$(this).dialog("close");
												        	}
													    }
													]
												});
			}
		}
		
		
		function exibeOuOcultaDataDeAlteracao(){
			if($('input[name="tipoAlteracao"]:checked').val() == 'A'){
				$("#divDecideAlteracaoOrRetificacao").dialog("option", "height",350);
				$('#divInformeDataAlteracao').show();
			}
			else if($('input[name="tipoAlteracao"]:checked').val() == 'R'){
				$("#divDecideAlteracaoOrRetificacao").dialog("option", "height",260);
				$('#divInformeDataAlteracao').hide();
			}
		}
			
		function validaDataAlteracao(){
			$('.dataValida').remove();
			
			var dataDesligamento1 = "${dataDesligamento?string}";
			
			
			if($('input[name="tipoAlteracao"]:checked').val() == 'A' || $('#podeEfetuarRetificacao').val() == "false"){
				if(moment($("#dataAlt").val(),'DD/MM/YYYY',true).locale('pt-BR').isValid()){
					var dataNovoHistorico = moment($("#dataAlt").val(),'DD-MM-YYYY').locale('pt-BR');
					var dataDesligamento = moment("${dataDesligamento?string}","DD-MM-YYYY").locale("pt-BR");
					if(!dataNovoHistorico.isAfter(dataDesligamento)){
						$('#dataAlteracao').val($('#dataAlt').val());
						$('#form').submit();
					}else{
						jAlert("Colaborador <strong>${nomeColaborador}</strong> está desligado. Só é possível inserir um novo histórico com data menor ou igual a data do desligamento.<br><br>Data do desligamento: <strong>${dataDesligamento?string}</strong>.</span>");
					}
				}
				else
					$('#dataAlt').parent().append("<span class='dataValida' style='color: #9F6000;padding: 3px 10px;padding-top: 2px;margin-left:2px;font-size: 11px;background-color: #FEEFB3;'>Informe uma data válida.</span>")
			}
			else{
				$('#dataAlteracao').val(null);
				$('#form').submit();
			}
		}
		
		function houveAlteracaoDosCampos(){
			var retorno = false;
			$(".campoAdd").each(function(){
				var id = this.id.replace('wwgrp_', '');
				if(colaboradorMap[id] != $('#' + id).val()){
					retorno = true;	
				}
			});
			
			return retorno;	
		}
		
		function configuraEdicaoCamposIntegrados(){
			<#if desabilitarEdicaoCamposIntegrados>
				$('.campo-integrado').attr('readonly', 'readonly');
				$('.campo-integrado-select').attr('disabled', 'disabled');
			</#if>
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<div id="abas">
		<div id="aba1" class="abaDadosPessoais"><a href="javascript: abas(1, '', ${edicao}, ${totalAbas})">Dados Pessoais</a></div>
		<div id="aba2" class="abaDadosFuncionais"><a href="javascript: abas(2, '', ${edicao}, ${totalAbas})">Dados Funcionais</a></div>
		<div id="aba3" class="abaFormacaoEscolar"><a href="javascript: abas(3, '', ${edicao}, ${totalAbas})">Formação Escolar</a></div>
		<div id="aba4" class="abaExperiencias"><a href="javascript: abas(4, '', ${edicao}, ${totalAbas})">Experiências</a></div>
		<div id="aba5" class="abaDocumentos"><a href="javascript: abas(5, '', ${edicao}, ${totalAbas})">Documentos</a></div>
		<div id="aba6" class="abaModelosAvaliacao"><a href="javascript: abas(6, '', ${edicao}, ${totalAbas})">Modelos de Avaliação</a></div>
		
		<#if habilitaCampoExtra>
			<div id="aba7" class="abaExtra"><a href="javascript: abas(7, '', ${edicao}, ${totalAbas})">Extra</a></div>
		</#if>
	</div>

	<#-- Campos fora do formulário por causa do ajax.
	Antes de enviar o form os cursos e a observação são setados em campos hidden dentro do form. -->
	<#-- Acima do form para corrigir problema de layout no IE -->
	<div id="content3" style="display: none;">
		<@ww.div id="formacao" cssClass="campo"/>
		<@ww.div  id="idioma" cssClass="campo"/>
		<@ww.textarea label="Cursos" id="desCursos" name="desCursos" cssStyle="width: 705px;" liClass="campo"/>
		<div style="clear: both;"></div>
	</div>
	<div id="content4" style="display: none;">
		<@ww.div id="expProfissional" />
		<@ww.textarea label="Informações Adicionais" id="obs" name="obs" cssStyle="width: 705px;" liClass="campo"/>
		<div style="clear: both;"></div>
	</div>

	<@ww.form id="form" name="form" action="${formAction}" method="POST" enctype="multipart/form-data">
		<div id="content1">

			<@ww.hidden id="respondeuEntrevista" name="colaborador.respondeuEntrevista"/>
			<#assign somenteLeituraIntegraAC="false" />

			<#if integraAc>
				<#if !colaborador.id?exists>
					<@ww.checkbox label="Não enviar este colaborador para o Fortes Pessoal" id="naoIntegraAc" name="colaborador.naoIntegraAc" liClass="liLeft campo" labelPosition="left" onchange="" />
					<@ww.checkbox label="Este colaborador não será enviado para o Fortes Pessoal" id="naoIntegraACSocio" name="naoIntegra" liClass="liLeft campo" disabled="true" labelPosition="left"/>				
				<#else>
					<@ww.hidden id="naoIntegraAc" name="colaborador.naoIntegraAc"/>
					<#if colaborador.naoIntegraAc>
						<div>Este colaborador não está integrado com o Fortes Pessoal</div>
					<#else>
						<div>Este colaborador está integrado com o Fortes Pessoal</div>
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
								<#if !desabilitarEdicaoCamposIntegrados>
									<@ww.checkbox label="Manter foto atual" name="manterFoto" onclick="mostraFoto()" value="true" checked="checked" labelPosition="left"/>
									<div id="fotoUpLoad" style="display:none;">
										<@ww.file label="Nova Foto" name="colaborador.foto" id="foto"/>
									</div>
								<#else>
									<@ww.hidden id="manterFoto" name="manterFoto" value="true"/>
								</#if>
								
							</td>
						</tr>
					</table>
					<hr>
				</div>
	        <#elseif !desabilitarEdicaoCamposIntegrados>
				<@ww.file label="Foto" name="colaborador.foto" id="foto"/>
	        </#if>

			<@ww.textfield label="Nome" name="colaborador.nome" id="nome" liClass="liLeft campo campoAdd" cssClass="campo-integrado" cssStyle="width: 300px;" maxLength="60" onblur="${funcaoNome};"/>
			<@ww.textfield label="Nome Comercial" name="colaborador.nomeComercial"  liClass="campo" id="nomeComercial" cssStyle="width: 300px;" maxLength="30" cssClass="campo-integrado"/>
			<#if desabilitarEdicaoCamposIntegrados>
				<@ww.textfield label="Nascimento" name="colaborador.pessoal.dataNascimento"  value="${dataNasc}" id="nascimento" liClass="liLeft campo campoAdd" cssClass="mascaraData campo-integrado"/>
			<#else>
				<@ww.datepicker label="Nascimento" name="colaborador.pessoal.dataNascimento" value="${dataNasc}" id="nascimento" liClass="liLeft campo campoAdd" cssClass="mascaraData"/>
			</#if>
			<@ww.select label="Sexo" id="sexo" name="colaborador.pessoal.sexo" list="sexos" cssStyle="width: 85px;" liClass="liLeft campo campoAdd" cssClass="campo-integrado-select"/>
			<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpf" liClass="campo campoAdd" cssClass="mascaraCpf campo-integrado" onchange="verificaCpf(this.value);" onblur="verificaCpf(this.value);"/>
			<@ww.div id="msgCPFDuplicado" cssStyle="display:none;"></@ww.div>
			
			<#if colaborador.id?exists && integraAc && !colaborador.naoIntegraAc>
				<@ww.textfield label="Naturalidade" name="colaborador.naturalidade" id="naturalidade" disabled="true" cssStyle="width: 300px;" liClass="liLeft"/>
				<img src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -212px" class="liLeft naturalidadeAndNacionalidadeHelp"/>
				<@ww.textfield label="Nacionalidade" name="colaborador.nacionalidade" id="nacionalidade" disabled="true" cssStyle="width: 280px;" liClass="liLeft"/>
				<img src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -185px" class="naturalidadeAndNacionalidadeHelp"/>
				<br clear="all"/>
			</#if>			
			
			<@ww.div id="wwgrp_endereco" cssClass="campo">
				<@ww.textfield label="CEP" name="colaborador.endereco.cep" id="cep" cssClass="mascaraCep campo-integrado" liClass="liLeft campoAdd"/>
				<@ww.textfield label="Logradouro" name="colaborador.endereco.logradouro" id="ende" cssStyle="width: 300px;" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="40"/>
				<@ww.textfield label="Nº"  name="colaborador.endereco.numero" id="num" cssStyle="width:40px;" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="10"/>
				<@ww.textfield label="Complemento" name="colaborador.endereco.complemento" id="complemento" licClass="campoAdd" cssClass="campo-integrado" cssStyle="width: 205px;" maxLength="20"/>
				<@ww.select label="Estado"     name="colaborador.endereco.uf.id" id="uf" list="estados" liClass="liLeft campoAdd" cssClass="campo-integrado-select" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
				<@ww.select label="Cidade" name="colaborador.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft campoAdd" cssClass="campo-integrado-select" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue=""/>
				<@ww.textfield label="Bairro" name="colaborador.endereco.bairro" id="bairroNome" liClass="campoAdd" cssClass="campo-integrado" cssStyle="width: 325px;" maxLength="85"/>
				<@ww.div id="bairroContainer"/>
			</@ww.div>

			<@ww.textfield label="E-mail" name="colaborador.contato.email" id="email" cssClass="mascaraEmail campo-integrado" maxLength="200" liClass="liLeft campo campoAdd"/>
			<@ww.div id="wwgrp_fone"  cssClass="campo">
				<@ww.textfield label="DDD" name="colaborador.contato.ddd" id="ddd" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="2" cssStyle="width:25px;"/>
				<@ww.textfield label="Telefone"  name="colaborador.contato.foneFixo" id="fone" onkeypress = "return(somenteNumeros(event,''));" maxLength="9" liClass="liLeft campoAdd" cssClass="campo-integrado" cssStyle="width:80px;"/>
			</@ww.div>
			<@ww.textfield label="DDD" name="colaborador.contato.dddCelular" id="dddCelular" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="2" cssStyle="width:25px;"/>
			<@ww.textfield label="Celular"   name="colaborador.contato.foneCelular"  liClass="campo campoAdd" cssClass="campo-integrado" onkeypress = "return(somenteNumeros(event,''));" id="celular" maxLength="9" cssStyle="width:80px;"/>
			<@ww.select label="Escolaridade" name="colaborador.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 303px;" liClass="liLeft campo campoAdd" cssClass="campo-integrado-select" headerKey="" headerValue="Selecione..."/>
			<@ww.select label="Estado Civil" name="colaborador.pessoal.estadoCivil" id="estadoCivil" list="estadosCivis" cssStyle="width: 210px;" liClass="liLeft campo campoAdd" cssClass="campo-integrado-select"/>
			<@ww.select label="Deficiência" name="colaborador.pessoal.deficiencia" id="deficiencia" list="deficiencias" cssStyle="width: 160px;" liClass="campo campoAdd" cssClass="campo-integrado-select"/>

			<@ww.textfield label="Nome do Pai" name="colaborador.pessoal.pai" id="nomePai" liClass="liLeft campo campoAdd" cssClass="campo-integrado" cssStyle="width: 300px;" maxLength="60"/>
			<@ww.textfield label="Nome da Mãe" name="colaborador.pessoal.mae" id="nomeMae" cssStyle="width: 300px;" maxLength="60"  liClass="campo campoAdd" cssClass="campo-integrado"/>
			<@ww.textfield label="Nome do Cônjuge" name="colaborador.pessoal.conjuge" id="nomeConjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft campo campoAdd" cssClass="campo-integrado"/>
			<@ww.textfield label="Qtd. Filhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="colaborador.pessoal.qtdFilhos" id="qtdFilhos" liClass="campo campoAdd" cssStyle="width:25px; text-align:right;" maxLength="2" />
			<div style="clear: both;"></div>
		</div>

		<div id="content2" style="display: none;">
			<#if integraAc && !colaborador.naoIntegraAc>
				<@ww.textfield label="Código no Fortes Pessoal" name="colaborador.codigoAC" id="codigoAC" disabled="true" cssStyle="width: 80px;"/>
			</#if>
			<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matricula" disabled= "${(integraAc && !colaborador.naoIntegraAc)?string}" cssStyle="width:150px;" liClass="liLeft campo campoAdd" maxLength="20" />

			<#if somenteLeituraIntegraAC=="true" && edicao=="true">
				<label for="dt_admissao">Admissão:</label><br />
				<input type="text" theme="simple" disabled="true" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" class="mascaraData"/>
				<img id="dataAdmissaoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
				<br clear="all"/>
				<@ww.hidden  name="colaborador.dataAdmissao" value="${dataAdm}" />
			<#else>
				<#if editarHistorico>
					<@ww.datepicker label="Admissão" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" liClass="campoAdd" cssClass="mascaraData" onblur="${funcaoDataAdmissao}" onchange="${funcaoDataAdmissao}"/>
				<#else>
					<label for="dt_admissao">Admissão:</label><br />
					<input type="text" theme="simple" disabled="true" name="colaborador.dataAdmissao" value="${dataAdm}" id="dt_admissao" style="background:#F6F6F6;" class="mascaraData"/>
					<@ww.hidden  name="colaborador.dataAdmissao" value="${dataAdm}" />
					<br clear="all"/>
				</#if>
			</#if>
			
			<#if somenteLeituraIntegraAC=="true" && edicao=="true" && !colaborador.naoIntegraAc>
				<@ww.div id="wwgrp_vinculo"  cssClass="campo">
					<label for="vinculo">Colocação:</label><br />
					<@ww.select theme="simple" label="Colocação" disabled="true" name="colaborador.vinculo" cssClass="campo" list="vinculos" onchange="habilitaDtEncerramentoContrato();configurarObrigatoriedadeDoPis();" id="vinculo" cssStyle="width: 150px;"/>
					<img id="vinculoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
					<br clear="all"/>
				</@ww.div> 
				<@ww.hidden  name="colaborador.vinculo" />
			<#else>
				<@ww.div id="wwgrp_vinculo"  cssClass="campo">
					<label for="vinculo">Colocação:</label><br />
					<@ww.select theme="simple" label="Colocação" name="colaborador.vinculo" list="vinculos" cssClass="campo campoAdd" cssStyle="width: 150px;" id="vinculo" onchange="habilitaDtEncerramentoContrato(),checkNaoIntegraAC();configurarObrigatoriedadeDoPis();" />
					<#if integraAc && !colaborador.naoIntegraAc && !empresaEstaIntegradaEAderiuAoESocial>
						<img id="vinculoSocioTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
					</#if>
				</@ww.div>	
			</#if>
			
			<@ww.datepicker label="Encerramento do Contrato" name="colaborador.dataEncerramentoContrato" value="${dataEncerramentoContrato}" id="dt_encerramentoContrato" cssClass="mascaraData" liClass="campo"/>
			
			<@ww.textfield label="Regime de Revezamento (PPP)" liClass="campo" name="colaborador.regimeRevezamento" id="regimeRevezamento" cssStyle="width:353px;" maxLength="255"/>

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
						<@ww.select label="Área Organizacional" name="historicoColaborador.areaOrganizacional.id" id="areaOrganizacional" list="areaOrganizacionals" required="true" listKey="id" listValue="descricaoComCodigoAC" headerKey="" disabled= "${somenteLeitura}" headerValue="Selecione..." cssStyle="width: 355px;" onchange="verificaMaternidade(this.value, 'areaOrganizacional');"/>
						
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
								<@ww.textfield label="Valor" name="salarioColaborador" required="true" id="salarioProposto" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12" disabled="${somenteLeitura}" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
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

			<#if integraAc && !colaborador.naoIntegraAc>
				<@ww.hidden name="colaborador.matricula" />
			</#if>

			<#if somenteLeitura == "true">
				<@ww.hidden name="salarioColaborador" />
				<@ww.hidden name="historicoColaborador.funcao.id" />
				<@ww.hidden name="historicoColaborador.areaOrganizacional.id" />
				<@ww.hidden name="historicoColaborador.estabelecimento.id" />
				<@ww.hidden name="historicoColaborador.data" />
				<@ww.hidden name="historicoColaborador.faixaSalarial.id" />
				<@ww.hidden name="salarioPropostoPor" value="${historicoColaborador.tipoSalario}" />
				<@ww.hidden name="obsACPessoal" value="${historicoColaborador.obsACPessoal}" />
			</#if>
			
			<div style="clear: both;"></div>
		</div>
		<div id="content5" style="display: none;">
			<li>
				<@ww.div id="wwgrp_identidade" cssClass="campo">
					<ul>
						<b><@ww.label label="Identidade" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.rg" id="identidade" cssStyle="width: 106px;" maxLength="15" liClass="liLeft campoAdd" cssClass="campo-integrado" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				  	   	<@ww.textfield label="Órgão Emissor" id="rgOrgaoEmissor" name="colaborador.pessoal.rgOrgaoEmissor" cssStyle="width: 73px;" maxLength="10" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				       	<@ww.select label="Estado" name="colaborador.pessoal.rgUf.id" id="rgUf" list="estados" liClass="liLeft campoAdd" cssClass="campo-integrado-select" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
				      	<#if desabilitarEdicaoCamposIntegrados>
							<@ww.textfield label="Data de Expedição" name="colaborador.pessoal.rgDataExpedicao" id="rgDataExpedicao" liClass="campoAdd" cssClass="mascaraData campo-integrado" value="${rgDataExpedicao}"/>
						<#else>
				      		<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.rgDataExpedicao" id="rgDataExpedicao" liClass="campoAdd" cssClass="mascaraData campo-integrado" value="${rgDataExpedicao}"/>
						</#if>
				      	
				      	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
			      	</ul>
				</@ww.div>
			</li>	
	       	<li>
				<@ww.div id="wwgrp_carteiraHabilitacao" cssClass="campo">
					<ul>
				       	<b><@ww.label label="Carteira de Habilitação" /></b>
						<@ww.textfield label="Nº de Registro" id="carteiraHabilitacao" name="colaborador.habilitacao.numeroHab" cssStyle="width: 100px;" maxLength="11" liClass="liLeft campoAdd" cssClass="campo-integrado" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				      	<@ww.textfield label="Prontuário" id="prontuario" name="colaborador.habilitacao.registro" cssStyle="" maxLength="15" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				      	<#if desabilitarEdicaoCamposIntegrados>
							<@ww.textfield label="Emissão" name="colaborador.habilitacao.emissao" id="emissao" liClass="liLeft campoAdd" cssClass="mascaraData campo-integrado" value="${habEmissao}"/>
						<#else>
					      	<@ww.datepicker label="Emissão" name="colaborador.habilitacao.emissao" id="emissao" liClass="liLeft campoAdd" cssClass="mascaraData campo-integrado" value="${habEmissao}"/>
						</#if>
						
						<#if desabilitarEdicaoCamposIntegrados>
							<@ww.textfield label="Vencimento" name="colaborador.habilitacao.vencimento" id="vencimento" liClass="liLeft campoAdd" cssClass="mascaraData campo-integrado" value="${dataVenc}"/>
						<#else>
					      	<@ww.datepicker label="Vencimento" name="colaborador.habilitacao.vencimento" id="vencimento" liClass="liLeft campoAdd" cssClass="mascaraData campo-integrado" value="${dataVenc}"/>
						</#if>
						
				       	<@ww.textfield label="Categoria(s)" name="colaborador.habilitacao.categoria" id="chCategoria" liClass="campoAdd" cssClass="campo-integrado" cssStyle="width:25px" maxLength="3"/>
				       	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
		       		</ul>
				</@ww.div>
			</li>	
	  		<li>
				<@ww.div id="wwgrp_tituloEleitoral" cssClass="campo">
					<ul>
						<b><@ww.label label="Título Eleitoral" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.tituloEleitoral.titEleitNumero" id="tituloEleitoral" cssStyle="width: 95px;" maxLength="13" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				    	<@ww.textfield label="Zona" name="colaborador.pessoal.tituloEleitoral.titEleitZona" id="titEleitZona" cssStyle="width: 95px;" maxLength="3" liClass="liLeft campoAdd" cssClass="campo-integrado" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				    	<@ww.textfield label="Seção" name="colaborador.pessoal.tituloEleitoral.titEleitSecao" id="titEleitSecao" cssStyle="width: 95px;" maxLength="4" liClass="campoAdd" cssClass="campo-integrado" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				    	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	       			</ul>
				</@ww.div>
			</li>	
		  	<li>
				<@ww.div id="wwgrp_certificadoMilitar" cssClass="campo">
					<ul>
						<b><@ww.label label="Certificado Militar" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.certificadoMilitar.certMilNumero" id="certificadoMilitar" cssStyle="width: 88px;" maxLength="12" liClass="liLeft campoAdd" cssClass="campo-integrado" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				    	<@ww.textfield label="Tipo" name="colaborador.pessoal.certificadoMilitar.certMilTipo" id="certMilTipo" cssStyle="width: 38px;" maxLength="5" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				    	<@ww.textfield label="Série" name="colaborador.pessoal.certificadoMilitar.certMilSerie" id="certMilSerie" cssStyle="width: 88px;" maxLength="12" liClass="campoAdd" cssClass="campo-integrado"/>
				    	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	      			</ul>
				</@ww.div>
			</li>	
	  		<li id="wwgrp_ctps" class="campo">
				<@ww.div >
					<ul>
						<b><@ww.label label="CTPS - Carteira de Trabalho e Previdência Social" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.ctps.ctpsNumero" id="ctps" cssStyle="width: 58px;" maxLength="8" liClass="liLeft" cssClass="campo-integrado"/>
				    	<@ww.textfield label="Série" name="colaborador.pessoal.ctps.ctpsSerie" id="ctpsSerie" cssStyle="width: 38px;" maxLength="6" liClass="liLeft" cssClass="campo-integrado"/>
				    	<@ww.textfield label="DV" name="colaborador.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 11px;" maxLength="1" liClass="liLeft" cssClass="campo-integrado"/>
				       	<@ww.select label="Estado" name="colaborador.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="estados" liClass="liLeft campoAdd" cssClass="campo-integrado-select" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
				      	<#if desabilitarEdicaoCamposIntegrados>
							<@ww.textfield label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" liClass="campoAdd" cssClass="mascaraData campo-integrado" value="${ctpsDataExpedicao}"/>
						<#else>
					      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" liClass="campoAdd" cssClass="mascaraData campo-integrado" value="${ctpsDataExpedicao}"/>
						</#if>
				      	
				      	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
					</ul>
				</@ww.div>
			</li>	
	  		<li id="wwgrp_pis" class="campo">
				<@ww.div >
					<ul>
						<b><@ww.label label="PIS - Programa de Integração Social"/></b>
						<@ww.textfield label="Número" name="colaborador.pessoal.pis" id="pis" liClass="campoAdd" cssClass="mascaraPis campo-integrado" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,'{,}'));" maxLength="11"/>
				    	<img id="pisTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="float: left; margin-left: 83px;margin-top: -18px;"/>
				    	<div style="clear: both;"></div>
				   	</ul>
				</@ww.div>
			</li>
	    </div>

		<div id="content6" style="display: none;">
			<fieldset>
				<legend>Colaborador</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados">
					<#if periodoExperiencia?exists && !periodoExperiencia.ativo>
						<#assign style="color: #e36f6f;"/>
					<#else>
						<#assign style=""/>
					</#if>
					
					<@display.column title="Dias" style="width:45px;${style}" >
					${periodoExperiencia.dias}  
					<#if periodoExperiencia?exists && !periodoExperiencia.ativo>
						<i class="fa fa-ban" aria-hidden="true" title="Dia inativo" style="color: #e36f6f;"></i>
					</#if>
					</@display.column>
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoes[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" name="colaboradorAvaliacoes[${i}].avaliacao.id" cssClass="periodoExperiencia" id="modeloPeriodo${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>

			<br />

			<fieldset>
				<legend>Gestor</legend>
				<#assign i = 0 />
				<#assign style=""/>
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados">
					<#if periodoExperiencia?exists && !periodoExperiencia.ativo>
						<#assign style="color: #e36f6f;"/>
					<#else>
						<#assign style=""/>
					</#if>
					<@display.column title="Dias" style="width:45px;${style}" >
					${periodoExperiencia.dias}  
					<#if periodoExperiencia?exists && !periodoExperiencia.ativo>
						<i class="fa fa-ban" aria-hidden="true" title="Dia inativo" style="color: #e36f6f;"></i>
					</#if>
					</@display.column>
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoesGestor[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<#if colaboradorSistemaId?exists && colaborador?exists && colaborador.id?exists && colaboradorSistemaId == colaborador.id>
							<@authz.authorize ifAllGranted="ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA">
								<@ww.select theme="simple" name="colaboradorAvaliacoesGestor[${i}].avaliacao.id" cssClass="periodoExperiencia" id="modeloPeriodoGestor${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
							</@authz.authorize>
							<@authz.authorize ifNotGranted="ROLE_AV_GESTOR_RECEBER_NOTIFICACAO_PROPRIA_AVALIACAO_ACOMP_DE_EXPERIENCIA">
								<@ww.select theme="simple" name="colaboradorAvaliacoesGestor[${i}].avaliacao.id" cssClass="periodoExperiencia" id="modeloPeriodoGestor${periodoExperiencia.id}" disabled="true" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
							</@authz.authorize>
						<#else>
							<@ww.select theme="simple" name="colaboradorAvaliacoesGestor[${i}].avaliacao.id" cssClass="periodoExperiencia" id="modeloPeriodoGestor${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 750px;"/>
						</#if>
					</@display.column>
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>
			<div style="clear: both;"></div>
	    </div>

		<#if habilitaCampoExtra>
			<div id="content7" style="display: none;">
				<#include "camposExtras.ftl" />
				<div style="clear: both;"></div>
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
		<@ww.hidden name="dadosIntegradosAtualizados" id="dadosIntegradosAtualizados" value="${dadosIntegradosAtualizados}"/>
		<@ww.hidden name="dataAlteracao" id="dataAlteracao"/>
		
		
		<@ww.hidden id="integradaEAderiuAoESocial" value="${empresaEstaIntegradaEAderiuAoESocial?string}" />
		<@ww.hidden id ="isEdicao" value="${edicao?string}"/>
		
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
			<button onclick="prepareSubmit();" id="gravar" <#if !colaborador.id?exists> </#if> class="btnGravar" accesskey="${accessKey}">
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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
	
	<@ww.hidden id ="podeEfetuarRetificacao" value="${podeRetificar?string}"/>
	
	<#if colaborador?exists && !colaborador.dataDesligamento?exists>
		<#assign dataNovoHistorico = dataAlteracao?date />
	<#else>
		<#assign dataNovoHistorico = "" />
	</#if>
	
	<#if podeRetificar>
		<div id="divDecideAlteracaoOrRetificacao">
		<#if colaborador?exists && colaborador.dataDesligamento?exists>
			Este colaborador está desligado. Só é possível inserir um novo histórico com data menor ou igual a data do desligamento.<br>Data do desligamento: <strong>${dataDesligamento?string}</strong>.
			</br></br>
		</#if>
			Para as informações modificadas, Você deseja que no Fortes Pessoal seja criado um novo histórico ou que sejam retificadas?
			</br></br>
			<@ww.div id="divTipoAlteracao" cssClass="radio">
				<input id="tipoAlteracao" name="tipoAlteracao" type="radio" value="A" onchange="exibeOuOcultaDataDeAlteracao();"/><label>Novo Histórico</label>
				<input id="tipoAlteracao" name="tipoAlteracao" type="radio" value="R" onchange="exibeOuOcultaDataDeAlteracao();"/><label>Retificar</label>
			</@ww.div>
			</br>
			<@ww.div id="divInformeDataAlteracao">
				<@ww.datepicker label="Informe a data a partir de quando ocorreu a atualização" value="${dataNovoHistorico}" id="dataAlt" liClass="liLeft" cssClass="mascaraData"/>
				</br></br>
				<h5>Essa informação é obrigatória em virtude de exigência do eSocial.</h5>
			</@ww.div>
		</div>
	<#else>
		<div id="divDecideAlteracaoOrRetificacao">
			<#if colaborador?exists && colaborador.dataDesligamento?exists>
			Este colaborador está desligado. Só é possível inserir um novo histórico com data menor ou igual a data do desligamento.<br>Data do desligamento: <strong>${dataDesligamento?string}</strong>.
			</br></br>
			</#if>
			Será criado no Fortes Pessoal um novo histórico cadastral para o colaborador.
			</br></br>
			<@ww.datepicker label="Informe a data a partir de quando ocorreu a atualização" value="${dataNovoHistorico}" id="dataAlt" liClass="liLeft" cssClass="mascaraData"/>
			</br></br>
			<h5>Essa informação é obrigatória em virtude de exigência do eSocial.</h5>
		</div>
	</#if>
</body>

</html>