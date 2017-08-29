<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Editar Informações Pessoais</title>
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/colaborador.css?version=${versao}"/>" media="screen" type="text/css">

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

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/areaOrganizacional.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/colaborador.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		#divDecideAlteracaoOrRetificacao { display: none; }
		#divInformeDataAlteracao { display: none; }
		.calendar{ z-index: 1010 !important;}
		input[disabled], select[disabled] { background: #EFEFEF; }
		input[readonly] { background: #EFEFEF; }
	</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<#include "../cargosalario/calculaSalarioInclude.ftl" />
	
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
		
		<#assign totalAbas = 5/>
	<#else>
		<#assign totalAbas = 4/>
	</#if>
	
	<#assign dadosIntegradosAtualizados = "${dadosIntegradosAtualizados?string}"/>
	<#assign disabledCamposIntegrados = "${desabilitarEdicaoCamposIntegrados?string}"/> 
	
	<script type="text/javascript">
		var camposColaboradorVisivel = "${parametrosDoSistema.camposColaboradorVisivel}";
		var camposColaboradorObrigatorio = "${camposColaboradorObrigatorio}";
		
		if(${pisObrigatorio?string})
			camposColaboradorObrigatorio = camposColaboradorObrigatorio + ',pis';
		
		var abasVisiveis = "${parametrosDoSistema.camposColaboradorTabs}";
		var arrayAbasVisiveis  = abasVisiveis.split(',');
		qtdAbas = arrayAbasVisiveis.length;
		var arrayObrigatorios = new Array();
		var colaboradorMap = new Object();

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
		
		$(function() {
			$(".campo").each(function(){
				var campos = camposColaboradorVisivel.split(',');
				var id = this.id.replace('wwgrp_', '');
				var idNaoEncontrado = ($.inArray(id, campos) == -1);
			    if (idNaoEncontrado)
					$(this).hide();
			});	

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
			
			addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');
			
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action"/>');
			
			<#if colaborador?exists && colaborador.id?exists>
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
			
			configuraEdicaoCamposIntegrados();
		});
		
		function validaFormularioDinamico()
		{
			$('.campo-integrado-select').removeAttr('disabled');
			
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
			
			arrayObrigatorios = $.grep(arrayObrigatorios, function(value) {
				return value != 'formacao' && value != 'idioma' && value != 'expProfissional' && 
				value != 'nome' && value != 'nomeComercial' && value != 'nascimento' && value != 'sexo' &&
				value != 'cpf' && value != 'deficiencia' && value != 'dt_admissao';
			});
	
			arrayValidacao = arrayObrigatorios;
			if($("#vinculo").val() != "S")
				arrayValidacao.push('pis');
			
			var validaCampos = validaFormulario('form', arrayValidacao, new Array('ende','num','uf','cidade','ddd','fone','escolaridade','cep','emissao','vencimento','rgDataExpedicao','ctpsDataExpedicao', 'pis' ${validaDataCamposExtras}), true);
		
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
			}else
				configuraEdicaoCamposIntegrados();	
		}
		
		function dialogIntegraAc(){
			if(!houveAlteracaoDosCampos()){
				$('#dataAlteracao').val(null);
				$('#form').submit();
			}else {
				$('#divDecideAlteracaoOrRetificacao').dialog({ 	
													modal: true, 
													title: 'Informações Pessoais',
													height: 200,
													width: 450,
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
				$("#divDecideAlteracaoOrRetificacao").dialog("option", "height",280);
				$('#divInformeDataAlteracao').show();
			}
			else if($('input[name="tipoAlteracao"]:checked').val() == 'R'){
				$("#divDecideAlteracaoOrRetificacao").dialog("option", "height",200);
				$('#divInformeDataAlteracao').hide();
			}
		}
			
		function validaDataAlteracao(){
			$('.dataValida').remove();
			if($('input[name="tipoAlteracao"]:checked').val() == 'A' || $('#podeEfetuarRetificacao').val() == "false"){
				if(validaDate($('#dataAlt')[0]) && $('#dataAlt').val() != "  /  /    " &&  $('#dataAlt').val() != ""){
					$('#dataAlteracao').val($('#dataAlt').val());
					$('#form').submit();
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
	<@ww.head />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if colaborador.id?exists>
		<div id="abas">
			<div id="aba1" class="abaDadosPessoais"><a href="javascript: abas(1, '', true, ${totalAbas})">Dados Pessoais</a></div>
			<div id="aba2" class="abaFormacaoEscolar"><a href="javascript: abas(2, '', true, ${totalAbas})">Formação Escolar</a></div>
			<div id="aba3" class="abaExperiencias"><a href="javascript: abas(3, '', true, ${totalAbas})">Experiências</a></div>
			<div id="aba4" class="abaDocumentos"><a href="javascript: abas(4, '', true, ${totalAbas})">Documentos</a></div>
			
			<#if habilitaCampoExtra>
				<div id="aba5" class="abaExtra"><a href="javascript: abas(5, '', true, ${totalAbas})">Extra</a></div>
			</#if>
		</div>
	
		<#-- Campos fora do formulário por causa do ajax.
		Antes de enviar o form os cursos e a observação são setados em campos hidden dentro do form. -->
		<#-- Acima do form para corrigir problema de layout no IE -->
		<div id="content2" style="display: none;">
			<@ww.div id="formacao" cssClass="campo" />
			<@ww.div id="idioma"  cssClass="campo" />
			<@ww.textarea label="Cursos" id="desCursos" name="desCursos" cssStyle="width: 705px;" liClass="campo" />
		</div>
		<div id="content3" style="display: none;">
			<@ww.div id="expProfissional" cssClass="campo" />
			<@ww.textarea label="Informações Adicionais" id="obs" name="obs" cssStyle="width: 705px;" liClass="campo"/>
		</div>

		<@ww.form id="form" name="form" action="updateInfoPessoais.action" method="POST">
			<div id="content1">
				<@ww.div id="wwgrp_endereco" cssClass="campo">
					<@ww.textfield label="CEP" name="colaborador.endereco.cep" id="cep" cssClass="mascaraCep campo-integrado" liClass="liLeft campoAdd"/>
					<@ww.textfield label="Logradouro" name="colaborador.endereco.logradouro" id="endereco" cssStyle="width: 300px;" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="40"/>
					<@ww.textfield label="Nº"  name="colaborador.endereco.numero" id="num" cssStyle="width:40px;" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="10"/>
					<@ww.textfield label="Complemento" name="colaborador.endereco.complemento" id="complemento campoAdd" cssClass="campo-integrado" cssStyle="width: 232px;" maxLength="20"/>
					<@ww.select label="Estado"     name="colaborador.endereco.uf.id" id="uf" list="estados" liClass="liLeft campoAdd" cssClass="campo-integrado-select" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
					<@ww.select label="Cidade" name="colaborador.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft campoAdd" cssClass="campo-integrado-select" listKey="id" listValue="nome" cssStyle="width: 237px;" headerKey="" headerValue=""/>
					<@ww.textfield label="Bairro" name="colaborador.endereco.bairro" id="bairroNome" cssStyle="width: 350px;"  maxLength="85" liClass="campoAdd" cssClass="campo-integrado"/>
					<@ww.div id="bairroContainer"/>
				</@ww.div>	
				<@ww.textfield label="E-mail" name="colaborador.contato.email" id="email" cssStyle="width: 300px;" maxLength="40" liClass="liLeft campo campoAdd" cssClass="campo-integrado"/>
				<@ww.div id="wwgrp_fone"  cssClass="campo">
					<@ww.textfield label="DDD" name="colaborador.contato.ddd" id="ddd" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft campoAdd" cssClass="campo-integrado" maxLength="2" cssStyle="width:25px;"/>
					<@ww.textfield label="Telefone"  name="colaborador.contato.foneFixo" id="fone" onkeypress = "return(somenteNumeros(event,''));" maxLength="8" liClass="liLeft campoAdd" cssClass="campo-integrado" cssStyle="width:60px;"/>
				</@ww.div>
				
				<@ww.textfield label="Celular" name="colaborador.contato.foneCelular" id="celular" onkeypress = "return(somenteNumeros(event,''));" maxLength="9" cssStyle="width:80px;" liClass="campo campoAdd" cssClass="campo-integrado"/>
				<@ww.select label="Escolaridade" name="colaborador.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 303px;" liClass="liLeft campo campoAdd" headerKey="" headerValue="Selecione..." cssClass="campo-integrado-select"/>
				
				<@ww.select label="Estado Civil" name="colaborador.pessoal.estadoCivil" id="estadoCivil" list="estadosCivis" cssStyle="width: 210px;" liClass="campo campoAdd" cssClass="campo-integrado-select"/>
				
				<@ww.textfield label="Nome do Pai" name="colaborador.pessoal.pai" id="nomePai" liClass="liLeft campo campoAdd" cssStyle="width: 300px;" maxLength="60" cssClass="campo-integrado"/>
				<@ww.textfield label="Nome da Mãe" name="colaborador.pessoal.mae" id="nomeMae" liClass="campo campoAdd" cssStyle="width: 300px;" maxLength="60" cssClass="campo-integrado"/>
				<@ww.textfield label="Nome do Cônjuge" name="colaborador.pessoal.conjuge" id="nomeConjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft campo campoAdd" cssClass="campo-integrado"/>
				<@ww.textfield label="Qtd. Filhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="colaborador.pessoal.qtdFilhos" id="qtdFilhos" liClass="campo campoAdd" cssStyle="width:25px; text-align:right;" maxLength="2"/>
				<div style="clear: both;"></div>
			</div>
			
		<div id="content4" style="display: none;">
			<li>
				<@ww.div id="wwgrp_identidade" cssClass="campo">
					<ul>
					<b><@ww.label label="Identidade" /></b>
			    	<@ww.textfield label="Número" name="colaborador.pessoal.rg" id="identidade" cssStyle="width: 106px;" maxLength="15" liClass="liLeft campoAdd" onkeypress = "return(somenteNumeros(event,'{,}'));" cssClass="campo-integrado"/>
			  	   	<@ww.textfield label="Órgão Emissor" id="rgOrgaoEmissor" name="colaborador.pessoal.rgOrgaoEmissor" cssStyle="width: 73px;" maxLength="10" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
			       	<@ww.select label="Estado" name="colaborador.pessoal.rgUf.id" id="rgUf" list="estados" liClass="liLeft campoAdd" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" cssClass="campo-integrado-select"/>
					<#if desabilitarEdicaoCamposIntegrados>
				      	<@ww.textfield label="Data de Expedição" name="colaborador.pessoal.rgDataExpedicao" id="rgDataExpedicao" liClass="campoAdd" cssClass="mascaraData campo-integrado" value="${rgDataExpedicao}"/>
					<#else>
				      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.rgDataExpedicao" id="rgDataExpedicao" liClass="campoAdd" cssClass="mascaraData" value="${rgDataExpedicao}"/>
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
				      		<@ww.datepicker label="Emissão" name="colaborador.habilitacao.emissao" id="emissao" liClass="liLeft campoAdd" cssClass="mascaraData" value="${habEmissao}"/>
				      	</#if>
				      	
				      	<#if desabilitarEdicaoCamposIntegrados>
				      		<@ww.textfield label="Vencimento" name="colaborador.habilitacao.vencimento" id="vencimento" liClass="liLeft campoAdd" cssClass="mascaraData campo-integrado" value="${dataVenc}"/>
				      	<#else>
				      		<@ww.datepicker label="Vencimento" name="colaborador.habilitacao.vencimento" id="vencimento" liClass="liLeft campoAdd" cssClass="mascaraData" value="${dataVenc}"/>
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
				    	<@ww.textfield label="Número" name="colaborador.pessoal.ctps.ctpsNumero" id="ctps" cssStyle="width: 58px;" maxLength="8" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				    	<@ww.textfield label="Série" name="colaborador.pessoal.ctps.ctpsSerie" id="ctpsSerie" cssStyle="width: 38px;" maxLength="6" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				    	<@ww.textfield label="DV" name="colaborador.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 11px;" maxLength="1" liClass="liLeft campoAdd" cssClass="campo-integrado"/>
				       	<@ww.select label="Estado" name="colaborador.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="estados" liClass="liLeft campoAdd" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" cssClass="campo-integrado-select"/>
				      	<#if desabilitarEdicaoCamposIntegrados>
				      		<@ww.textfield label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" liClass="campoAdd" cssClass="mascaraData" value="${ctpsDataExpedicao}" cssClass="campo-integrado"/>
				      	<#else>
				      		<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" liClass="campoAdd" cssClass="mascaraData" value="${ctpsDataExpedicao}"/>
				      	</#if>
				      	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	        		</ul>
				</@ww.div>
			</li>	
	  		<li id="wwgrp_pis" class="campo">
				<@ww.div >
					<ul>
						<b><@ww.label label="PIS - Programa de Integração Social"/></b>
						<@ww.textfield label="Número" name="colaborador.pessoal.pis" id="pis" liClass="campoAdd" cssClass="mascaraPis" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,'{,}'));" maxLength="11" cssClass="campo-integrado" />
				    	<div style="clear: both;"></div>
				 	</ul>
				</@ww.div>
			</li>
	    </div>
	    
		<#if habilitaCampoExtra>
			<div id="content5" style="display: none;">
				<#include "camposExtras.ftl" />
				<div style="clear: both;"></div>
		    </div>
		</#if>
		
		<@ww.hidden name="colaborador.camposExtras.id" />
		<@ww.hidden name="colaborador.cursos" id="colaborador.cursos" />
		<@ww.hidden name="colaborador.observacao" id="colaborador.observacao" />
		<@ww.hidden name="colaborador.candidato.id"/>
		<@ww.hidden name="colaborador.id"/>
		<@ww.hidden name="colaborador.codigoAC"/>
		<@ww.hidden name="dadosIntegradosAtualizados" id="dadosIntegradosAtualizados" value="${dadosIntegradosAtualizados}"/>
		<@ww.hidden name="dataAlteracao" id="dataAlteracao"/>
		
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
			<div style="float: right; width: 400px; text-align: right;">
				<button id='voltar' disabled="disabled" onclick="abas(-1, 'V', true, ${totalAbas});" class="btnVoltarDesabilitado" accesskey="V">
				</button>
				<button id='avancar' onclick="abas(-1, 'A', true, ${totalAbas});" class="btnAvancar" accesskey="A">
				</button>
			</div>
			<div style="width: 400px;">
				<button onclick="if (setaCampos()) validaFormularioDinamico();" id="gravar" class="btnGravar">
				</button>
			</div>
		</div>
	</#if>
	
	<@ww.hidden id ="podeEfetuarRetificacao" value="${podeRetificar?string}"/>
	
	<#if podeRetificar>
		<div id="divDecideAlteracaoOrRetificacao">Para as informações modificadas, Você deseja que no Fortes Pessoal seja criado um novo histórico ou que sejam retificadas?
			</br></br>
			<@ww.div id="divTipoAlteracao" cssClass="radio">
				<input id="tipoAlteracao" name="tipoAlteracao" type="radio" value="A" onchange="exibeOuOcultaDataDeAlteracao();"/><label>Novo Histórico</label>
				<input id="tipoAlteracao" name="tipoAlteracao" type="radio" value="R" onchange="exibeOuOcultaDataDeAlteracao();"/><label>Retificar</label>
			</@ww.div>
			</br>
			<@ww.div id="divInformeDataAlteracao">
				<@ww.datepicker label="Informe a data a partir de quando ocorreu a atualização" value="${dataAlteracao?date}" id="dataAlt" liClass="liLeft" cssClass="mascaraData"/>
				</br></br>
				<h5>Essa informação é obrigatória em virtude de exigência do eSocial.</h5>
			</@ww.div>
		</div>
	<#else>
		<div id="divDecideAlteracaoOrRetificacao">Será criado no Fortes Pessoal um novo histórico cadastral para o colaborador.
			</br></br>
			<@ww.datepicker label="Informe a data a partir de quando ocorreu a atualização" value="${dataAlteracao?date}" id="dataAlt" liClass="liLeft" cssClass="mascaraData"/>
			</br></br>
			<h5>Essa informação é obrigatória em virtude de exigência do eSocial.</h5>
		</div>
	</#if>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
</body>

</html>