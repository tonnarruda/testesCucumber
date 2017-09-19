<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
  <#include "../ftl/mascarasImports.ftl" />

	 <style type="text/css">
		<#if moduloExterno>
			@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css?version=${versao}"/>');
		<#else>
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		</#if>
 		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/candidato.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>');
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
    #wwlbl_obs label,
    #wwlbl_obsrh label
    {
    	margin-left: -15px !important;
    }
  </style>
  <![endif]-->

  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PessoaDWR.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

  <script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js?version=${versao}"/>"></script>
  <script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>"></script>

  <script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js?version=${versao}"/>'></script>

	<#if moduloExterno?exists && moduloExterno>
			<#assign edicao="false"/>
	<#else>
		<#if candidato.id?exists>
			<#assign edicao="false"/>
		<#else>
			<#assign edicao="true"/>
		</#if>
	</#if>
	
	<#if empresaId?exists>
		<#assign idDaEmpresa=empresaId/>
	<#else>
		<#assign idDaEmpresa><@authz.authentication operation="empresaId"/></#assign>
	</#if>

	<script type='text/javascript'>
		<#if !moduloExterno>
			var camposCandidatoVisivel = "${parametrosDoSistema.camposCandidatoVisivel}";
			var camposCandidatoObrigatorio = "${parametrosDoSistema.camposCandidatoObrigatorio}";
	
			var abasVisiveis = "${parametrosDoSistema.camposCandidatoTabs}";
		<#else>
			var camposCandidatoVisivel = "${parametrosDoSistema.camposCandidatoExternoVisivel}";
			var camposCandidatoObrigatorio = "${parametrosDoSistema.camposCandidatoExternoObrigatorio}";
	
			var abasVisiveis = "${parametrosDoSistema.camposCandidatoExternoTabs}";
		</#if>			
		var arrayAbasVisiveis  = abasVisiveis.split(',');
		qtdAbas = arrayAbasVisiveis.length;
		var arrayObrigatorios = new Array();
		
		$(function() {
			addBuscaCEP('cep', 'endereco', 'bairroNome', 'cidade', 'uf');
			
			$(".campo").each(function(){
				var campos = camposCandidatoVisivel.split(',');
				var id = this.id.replace('wwgrp_', '');
				var idNaoEncontrado = ($.inArray(id, campos) == -1);
			    if (idNaoEncontrado)
					$(this).hide();
			});

			$('#wwgrp_comoFicouSabendoVagaQual').toggle($('#comoFicouSabendoVaga').val()==1);			

			$.each(camposCandidatoObrigatorio.split(','), function (index, idCampo) {
			    var lblAntigo = $('label[for='+idCampo+']');
			    lblAntigo.text(lblAntigo.text().replace(/\s$/, '') + "*");
			});
			
			if(camposCandidatoObrigatorio != "")
				arrayObrigatorios = camposCandidatoObrigatorio.split(',');
				
			$('#abas div').each(function(){
					var abaNaoEncontrada = ($.inArray($(this).attr('class'), arrayAbasVisiveis) == -1);
			        if (abaNaoEncontrada)
			            $(this).hide();
			});
			
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action?empresaId=${idDaEmpresa}"/>');
			
			if(qtdAbas == 1)
				ajustaBotoes(1, 1);
				
			<#if edicao == "true">
				$('#nomePai, #nomeMae, #nomeConjuge, #nome').blur(function() {
					if (this.value) verificaParentes([this.value]);
				});
			</#if>
		});
		
		function verificaParentes(nomes)
		{
			$('#parentesDialog').empty();
	    	ColaboradorDWR.findParentesByNome(null, <@authz.authentication operation="empresaId"/>, nomes, function(dados) { 
	    		listaParentes(dados, '<@authz.authentication operation="empresaNome"/>'); 
	    	});
		}

		function populaConhecimento(frm, nameCheck)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(frm, nameCheck);
		
			CandidatoDWR.getConhecimentos(createListConhecimentos, areasIds, ${idDaEmpresa});
		}
		
		function validarCamposCpf()
		{
			exibeLabelDosCamposNaoPreenchidos = true;
			desmarcarAbas();
		
			$('#formacao, #idioma, #expProfissional, #cpf, #comoFicouSabendoVagaQual').css('backgroundColor','inherit');
		
			var msg = "Os seguintes campos são obrigatórios: <br />";
			var formacaoInvalido = $.inArray('formacao', arrayObrigatorios) > -1 && $('#formacao tbody tr').size() < 1;
			var idiomaInvalido = $.inArray('idioma', arrayObrigatorios) > -1 && $('#idiomaTable tbody tr').size() < 1;
			var expInvalido = $.inArray('expProfissional', arrayObrigatorios) > -1 && $('#exp tbody tr').size() < 1;
			var ficouSabendoVaga = $.inArray('comoFicouSabendoVaga', arrayObrigatorios) > -1 && $('#comoFicouSabendoVaga').val() == 1 && ($('#comoFicouSabendoVagaQual').val() == null || $('#comoFicouSabendoVagaQual').val() == '');
			
			if (formacaoInvalido)
			{
	    		marcarAbas('#formacao');
				$('#formacao').css('backgroundColor','#ffeec2');
	    		msg += "Formação Escolar<br />";
			}

			if (idiomaInvalido)
			{
	    		marcarAbas('#idioma');
				$('#idioma').css('backgroundColor','#ffeec2');
	    		msg += "Idiomas<br />";
			}

			if (expInvalido)
			{
	    		marcarAbas('#expProfissional');
				$('#expProfissional').css('backgroundColor','#ffeec2');
	    		msg += "Experiência Profissional<br />";
			}
	    		
	    	if(ficouSabendoVaga )
	    	{
	    		marcarAbas('#comoFicouSabendoVagaQual');
	    		$('#comoFicouSabendoVagaQual').css('backgroundColor','#ffeec2');
	    		msg += "Qual?<br />";
	    	}
	    			    	
	    	if (ficouSabendoVaga  || formacaoInvalido || idiomaInvalido || expInvalido) {
	    		jAlert(msg);
	    		return false;
	    	}
			
			<#if candidato.id?exists>
				arrayObrigatorios = $.grep(arrayObrigatorios, function(value) {
					return value != 'senha' && value != 'comfirmaSenha';
				});
			</#if>
			
			// valida os multicheckboxes
			arrayObrigatorios = $.map(arrayObrigatorios, function(item) {
				return (item == 'areasCheck' || item ==  'cargosCheck' || item == 'conhecimentosCheck') ? '@' + item : item;
			});
			
			// valida os itens que constituem subformularios
			arrayObrigatorios = $.grep(arrayObrigatorios, function(value) {
				return value != 'formacao' && value != 'idioma' && value != 'expProfissional';
			});

			return validaFormulario('form', arrayObrigatorios, new Array('email', 'cpf', 'nascimento', 'cep', 'emissao', 'vencimento', 'rgDataExpedicao', 'ctpsDataExpedicao', 'pis', 'data1', 'data2', 'data3'));
		}
		
		function createListConhecimentos(data)
		{
			addChecks('conhecimentosCheck',data);
		}
		
		function setaCampos()
		{
			desmarcarAbas();
		
			<#if moduloExterno?exists && moduloExterno && !candidato.id?exists>
				if( !validarSenhaExterno())
			  		return false;
			<#else>
				if( !validarSenhaInterno())
			  		return false;
			</#if>
			
			
			<#if maxCandidataCargo?exists && 0 < maxCandidataCargo>
				if(qtdeChecksSelected(document.getElementsByName('form')[0],'cargosCheck') > ${maxCandidataCargo})
				{
					jAlert("Não é permitido selecionar mais do que ${maxCandidataCargo} cargos (Cargo / Função Pretendida)");
					return false;
				}
			</#if>
		
			document.getElementById('candidato.cursos').value = document.getElementById('desCursos').value;
			document.getElementById('candidato.observacao').value = document.getElementById('infoAdicionais').value;
		
			if(document.getElementById('obsrh'))
				document.getElementById('candidato.observacaoRH').value = document.getElementById('obsrh').value;
		
			return true;
		}
		
		function validarSenhaInterno()
		{ 
			var senhaValue = document.getElementById('senha').value;
			if(senhaValue != "" && senhaValue != document.getElementById('comfirmaSenha').value )
			{
				jAlert("Senha não confere");
				return false;
			}
		
			return true;
		}
		
		function validarSenhaExterno()
		{	
			$('#senha, #comfirmaSenha').css('backgroundColor','#fff');
		
			if( $('#senha').val() == "" )
			{
				marcarAbas('#senha');
				$('#senha').css('backgroundColor','#ffeec2');
				jAlert("Senha obrigatória para acesso ao modulo exteno.");
				return false;
			}
		    
			if( $('#senha').val() != $('#comfirmaSenha').val() )
			{
				marcarAbas('#comfirmaSenha');
				$('#senha, #comfirmaSenha').css('backgroundColor','#ffeec2');
				jAlert("Confirmação de senha não confere com a senha inserida.");
				return false;
			}
		
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
		
		function habilitaPagaPensao()
		{
			 var pagaPensao = document.getElementById('pensao');
			 var quantidade = document.getElementById('quantidadeId');
			 var valor      = document.getElementById('valorId');
			
			 if(pagaPensao.value == "true")
			 {
				quantidade.disabled=false;
				valor.disabled=false;
			 }
			 else
			 {
				quantidade.disabled=true;
				valor.disabled=true;
			 }
		}
		
		function verificaCpf(data)
		{
			 <#if moduloExterno?exists && moduloExterno>
				<#if candidato.id?exists>
					<#if idDaEmpresa?exists>
						verificaCpfDuplicado(data, ${idDaEmpresa}, true, ${candidato.id}, true);
					<#else>
						verificaCpfDuplicado(data, null, true, ${candidato.id}, true);
					</#if>
				<#else>
					<#if idDaEmpresa?exists>
						verificaCpfDuplicado(data, ${idDaEmpresa}, true, null, true);
					<#else>
						verificaCpfDuplicado(data, null, true, null, true);
					</#if>
				</#if>
			<#else>
				<#if candidato.id?exists>
					verificaCpfDuplicado(data, ${idDaEmpresa}, null, ${candidato.id}, true);
				<#else>
					verificaCpfDuplicado(data, ${idDaEmpresa}, null, null, true);
				</#if>			
			</#if>
		}
		
		function comoFicouSabendoVagaChange(select)
		{
			if ($(select).val()==1)
				$('#wwgrp_comoFicouSabendoVagaQual').show();
			else
			{
				$('#comoFicouSabendoVagaQual').val('');
				$('#wwgrp_comoFicouSabendoVagaQual').hide();
			}
		}
		
		function candidatosHomonimos()
		{
			var url = "<@ww.url value="/captacao/candidato/infoCandidato.action"/>"
			
			<#if candidato?exists && candidato.id?exists>
				url =url.replace("?candidato.id=" + ${candidato.id} , "");
			</#if>
			
			getCandidatosHomonimos(url);
		}
</script>

  <@ww.head />
  
  	<#if moduloExterno?exists && moduloExterno>
		<#if upperCase?exists && upperCase>
			<#assign capitalizar = "this.value = this.value.toUpperCase();" />
		<#else>
			<#assign capitalizar = "" />
		</#if>
		
		<#if candidato.id?exists>
			<#assign actionCancelar="../externo/prepareListAnuncio.action?empresaId=${empresaId}"/>
			<#assign formAction="../externo/update.action?moduloExterno=true"/>
		<#else>
			<#assign actionCancelar="../externo/prepareLogin.action?empresaId=${empresaId}"/>
			<#assign formAction="../externo/insert.action?moduloExterno=true"/>
		</#if>
	<#else>
		<#assign capitalizar = "" />
		<#assign actionCancelar="list.action"/>
		
		<#if candidato.id?exists>
			<#assign formAction="update.action"/>
		<#else>
			<#assign formAction="insert.action"/>
			<#assign edicao="true"/>
		</#if>
	</#if>
	
	<#assign edit="true"/>

	<#if candidato.id?exists>
		<title>Editar Candidato</title>
		<#assign accessKey="A"/>
		<#assign edicao="true"/>
	<#else>
		<title>Inserir Candidato</title>
		<#assign accessKey="G"/>
		<#assign edicao="false"/>
	</#if>

  <#if candidato?exists && candidato.id?exists>
    <#if candidato.pessoal?exists && candidato.pessoal.dataNascimento?exists>
      <#assign dataNas = candidato.pessoal.dataNascimento?date/>
    <#else>
      <#assign dataNas = ""/>
    </#if>

    <#if candidato.pessoal?exists && candidato.pessoal.rgDataExpedicao?exists>
      <#assign rgDataExpedicao = candidato.pessoal.rgDataExpedicao?date/>
    <#else>
      <#assign rgDataExpedicao = ""/>
    </#if>

    <#if candidato.pessoal?exists && candidato.pessoal.ctps?exists && candidato.pessoal.ctps.ctpsDataExpedicao?exists>
      <#assign ctpsDataExpedicao = candidato.pessoal.ctps.ctpsDataExpedicao?date/>
    <#else>
      <#assign ctpsDataExpedicao = ""/>
    </#if>

    <#if candidato.habilitacao?exists && candidato.habilitacao.emissao?exists>
      <#assign habEmissao= candidato.habilitacao.emissao?date/>
    <#else>
      <#assign habEmissao = ""/>
    </#if>

    <#if candidato.habilitacao?exists && candidato.habilitacao.vencimento?exists>
      <#assign dataVenc= candidato.habilitacao.vencimento?date/>
    <#else>
      <#assign dataVenc = ""/>
    </#if>
  <#else>
      <#assign dataNas = ""/>
      <#assign habEmissao = ""/>
      <#assign dataVenc = ""/>
      <#assign rgDataExpedicao = ""/>
      <#assign ctpsDataExpedicao = ""/>
  </#if>

	<#assign cpfObrigatorio = "false"/>
	<#if moduloExterno?exists && moduloExterno>
		<#assign cpfObrigatorio = "true"/>
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
	</#if>
</head>

<body>
<@ww.actionmessage />
<@ww.actionerror />
	<div id="abas">
    	<div id="aba1" class="abaDadosPessoais"><a href="javascript: abas(1, '',${edicao}, qtdAbas)">Dados Pessoais</a></div>
		<div id="aba2" class="abaFormacaoEscolar"><a href="javascript: abas(2, '',${edicao}, qtdAbas)">Formação Escolar</a></div>
		<div id="aba3" class="abaPerfilProfissional"><a href="javascript: abas(3, '',${edicao}, qtdAbas)">Perfil Profissional</a></div>
		<div id="aba4" class="abaExperiencias"><a href="javascript: abas(4, '',${edicao}, qtdAbas)">Experiências</a></div>
		<div id="aba5" class="abaDocumentos"><a href="javascript: abas(5, '',${edicao}, qtdAbas)">Documentos</a></div>
		<div id="aba6" class="abaCurriculo"><a href="javascript: abas(6, '',${edicao}, qtdAbas)">Currículo</a></div>
		<#if habilitaCampoExtra>
			<div id="aba7" class="abaExtra"><a href="javascript: abas(7, '', ${edicao}, qtdAbas)">Extra</a></div>
		</#if>
    </div>

	<div id="content4" class="4" style="display: none;">
		<@ww.div id="expProfissional" />

		<li id="wwgrp_infoAdicionais" class="campo">
			<@ww.div >
				<ul>
					<label for="infoAdicionais">Informações Adicionais:</label><br />
					<@ww.textarea id="infoAdicionais" name="obs" cssStyle="width: 705px;" onblur="${capitalizar}"/>
				</ul>
			</@ww.div>
		</li>			

		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_CANDIDATO">
			<#if !moduloExterno>
				Observações do RH<br />
				<@ww.textarea id="obsrh" name="obsrh" cssStyle="width: 705px;"/>
			</#if>	
		</@authz.authorize>
	</div>
	
	<div id="content2" class="2" style="display:none; width:98%;">
		<@ww.div  id="formacao" cssClass="campo"/>
		<@ww.div  id="idioma" cssClass="campo"/>
		<@ww.textarea label="Outros Cursos" id="desCursos" name="desCursos" cssStyle="width:783px;" onblur="${capitalizar}" liClass="campo"/>
    </div>

    <@ww.form name="form" action="${formAction}" validate="true" onsubmit="javascript:validarCamposCpf();" method="POST" enctype="multipart/form-data">
		<div id="content1" class="1">
			<#if candidato.foto?exists>
				<input type="checkbox" name="exibirFoto" onclick="mostra();" id="exibeFoto"/><label for="exibeFoto">Exibir Foto</label>
				<div id="fotoTbl" <#if candidato?exists && candidato.foto?exists>style="display:none;"</#if>>
					<table style="border:0px;">
						<tr>
							<td>
								<#if candidato.foto?exists>
									<#if moduloExterno?exists && moduloExterno>
										<#if candidato.id?exists>
											<img src="<@ww.url includeParams="none" value="../captacao/candidato/showFoto.action?candidato.id=${candidato.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
										<#else>
											<@ww.checkbox label="Manter foto atual" name="manterFoto" onclick="mostraFoto()" value="true" checked="checked" labelPosition="left"/>
											<div id="fotoUpLoad" style="display:none;">
												<@ww.file label="Nova Foto" name="candidato.foto" id="foto"/>
											</div>
										</#if>
									<#else>
										<img src="<@ww.url includeParams="none" value="showFoto.action?candidato.id=${candidato.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
									</#if>
								</#if>
							</td>
							<td>
								<@ww.checkbox label="Manter foto atual" name="manterFoto" onclick="mostraFoto()" value="true" checked="checked" labelPosition="left"/>
								<div id="fotoUpLoad" style="display:none;">
									<@ww.file label="Nova Foto" name="candidato.foto" id="foto"/>
								</div>
							</td>
						</tr>
					</table>
				</div>
			<#else>
				<@ww.file label="Foto" name="candidato.foto" id="foto"/>
			</#if>

			<hr style="border:0; border-top:1px solid #CCCCCC;">
						
			<@ww.div id="homonimos" cssStyle="color:blue;display:none; ">
				<#if moduloExterno?exists && moduloExterno>
					<@ww.hidden id="nomesHomonimos"/>
				<#else>
					Existe(m) candidato(s) homônimo(s):
					<@ww.div id="nomesHomonimos" cssStyle="color:red;">	</@ww.div>
				</#if>
			</@ww.div>
			
			<@ww.div id="msgCPFDuplicado" cssStyle="display:none;"></@ww.div>			
			
			<@ww.textfield label="Nome" name="candidato.nome" id="nome" liClass="liLeft" cssStyle="width: 300px;" maxLength="60" onblur="candidatosHomonimos();${capitalizar}"/>
			
			<@ww.datepicker label="Nascimento" name="candidato.pessoal.dataNascimento" id="nascimento" liClass="liLeft campo" cssClass="mascaraData" value="${dataNas}"/>
			<@ww.textfield label="Naturalidade" id="naturalidade" name="candidato.pessoal.naturalidade" cssStyle="width: 160px;" maxLength="100" liClass="liLeft , campo" onblur="${capitalizar}"/>
			<@ww.select label="Sexo" id="sexo" name="candidato.pessoal.sexo" list="sexos" liClass="liLeft , campo" />
																																																							
			<@ww.textfield label="CPF"  name="candidato.pessoal.cpf" id="cpf"  cssClass="mascaraCpf" liClass="liLeft" onchange="verificaCpf(this.value);" onblur="verificaCpf(this.value);"/>
			<@ww.select label="Escolaridade" name="candidato.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 300px;"  headerKey="" headerValue="Selecione..." liClass="liLeft , campo" />
			
			<li>
				<@ww.div id="wwgrp_endereco" cssClass="campo">
					<ul>
						<@ww.textfield label="CEP" name="candidato.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft" />
						<@ww.textfield label="Logradouro" name="candidato.endereco.logradouro" id="ende" cssStyle="width: 300px;" maxLength="40" liClass="liLeft" onblur="${capitalizar}"/>
						<@ww.textfield label="Nº" name="candidato.endereco.numero" id="num" cssStyle="width:40px;" maxLength="8" liClass="liLeft" onblur="${capitalizar}"/>
						<@ww.textfield label="Complemento" name="candidato.endereco.complemento" id="complemento" cssStyle="width: 250px;" maxLength="20" onblur="${capitalizar}" liClass="liLeft" />
			
						<@ww.select label="Estado" name="candidato.endereco.uf.id" id="uf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" />
						<@ww.select label="Cidade" name="candidato.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" />
						<@ww.textfield label="Bairro" name="candidato.endereco.bairro" id="bairroNome" cssStyle="width: 300px;" maxLength="85" onblur="${capitalizar}" liClass="liLeft" />
						<div id="bairroContainer"></div>
					</ul>
				</@ww.div>
			</li>			

			<@ww.textfield label="E-mail" name="candidato.contato.email" id="email" cssStyle="width: 270px;" maxLength="200" liClass="liLeft , campo" cssClass="mascaraEmail"/>
			
			<li>
				<@ww.div id="wwgrp_fone"  cssClass="campo">
					<ul>
						<@ww.textfield label="DDD" name="candidato.contato.ddd" id="ddd" onkeypress="return(somenteNumeros(event,''));" cssStyle="width: 25px;" maxLength="2"  liClass="liLeft"/>
						<@ww.textfield label="Telefone" name="candidato.contato.foneFixo" id="fone" onkeypress="return(somenteNumeros(event,''));"  cssStyle="width: 80px;" maxLength="9"  liClass="liLeft" />
					</ul>
				</@ww.div>
			</li>			
			
			<@ww.textfield label="Celular" id="celular" name="candidato.contato.foneCelular" onkeypress = "return(somenteNumeros(event,''));" cssStyle="width: 80px;" maxLength="9" liClass="liLeft , campo"/>
			<@ww.textfield label="Contato" name="candidato.contato.nomeContato" id="nomeContato" cssStyle="width: 180px;" maxLength="30" liClass="liLeft , campo" onblur="${capitalizar}"/>

			<@ww.textfield label="Parentes/Amigos na empresa" name="candidato.pessoal.parentesAmigos" id="parentes" cssStyle="width: 300px;" maxLength="100" liClass="liLeft , campo" onblur="${capitalizar}"/>
			<@ww.textfield label="Indicado Por" name="candidato.pessoal.indicadoPor" id="indicado" cssStyle="width: 300px;" maxLength="100" liClass="liLeft , campo" onblur="${capitalizar}" />

			<@ww.select label="Estado Civil" id="estadoCivil" name="candidato.pessoal.estadoCivil" list="estadosCivis" cssStyle="width: 210px;" liClass="liLeft , campo" />
			<@ww.textfield label="Qtd. Filhos" id="qtdFilhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="candidato.pessoal.qtdFilhos" cssStyle="width:72px; text-align:right;" liClass="liLeft , campo"/>

			<@ww.textfield label="Nome do Cônjuge" id="nomeConjuge" name="candidato.pessoal.conjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft , campo" onblur="${capitalizar}" />
			<@ww.textfield label="Profissão do Cônjuge" id="profConjuge" name="candidato.pessoal.profissaoConjuge" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}"  liClass="liLeft , campo"/>

			<@ww.textfield label="Nome do Pai" id="nomePai" name="candidato.pessoal.pai" cssStyle="width: 300px;" maxLength="60" liClass="liLeft , campo" onblur="${capitalizar}"/>
			<@ww.textfield label="Profissão do Pai" id="profPai" name="candidato.pessoal.profissaoPai" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}"  liClass="liLeft , campo"/>

			<@ww.textfield label="Nome da Mãe" id="nomeMae" name="candidato.pessoal.mae" cssStyle="width: 300px;" maxLength="60" liClass="liLeft , campo" onblur="${capitalizar}"/>
			<@ww.textfield label="Profissão da Mãe" id="profMae" name="candidato.pessoal.profissaoMae" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}" liClass="liLeft , campo"/>
			<li>
				<@ww.div id="wwgrp_comoFicouSabendoVaga" cssClass="campo">
					<ul>
						<@ww.select label="Como Ficou Sabendo da Vaga" id="comoFicouSabendoVaga" name="candidato.comoFicouSabendoVaga.id" list="comoFicouSabendoVagas" listKey="id" listValue="nome" onchange="comoFicouSabendoVagaChange(this)" headerKey="" headerValue="Selecione..." cssStyle="width: 200px;" liClass="liLeft"/>
						<@ww.textfield label="Qual?" id="comoFicouSabendoVagaQual" name="candidato.comoFicouSabendoVagaQual" cssStyle="width: 300px;" maxLength="100" liClass="liLeft"/>
					</ul>
				</@ww.div>
			</li>

			<li>
				<@ww.div id="wwgrp_pensao" cssClass="campo">
					<ul>
						<@ww.select label="Paga Pensão" id="pensao" name="candidato.socioEconomica.pagaPensao" list=r"#{true:'Sim',false:'Não'}" onchange="habilitaPagaPensao();" cssStyle="width: 110px;" liClass="liLeft"/>
						<@ww.textfield label="Qtd." id="quantidadeId" disabled="true"  name="candidato.socioEconomica.quantidade" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{}'));" cssStyle="width:25px; text-align:right;" maxLength="2" />
						<@ww.textfield label="Valor" id="valorId" disabled="true" name="candidato.socioEconomica.valor" liClass="liLeft" maxLength="12" onkeypress = "return(somenteNumeros(event,','));" cssStyle="width:85px; text-align:right;"/>
					</ul>
				</@ww.div>
			</li>
			
			<@ww.select label="Possui Veículo" id="possuiVeiculo" name="candidato.socioEconomica.possuiVeiculo" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 96px;" liClass="liLeft , campo"/>
			<@ww.select label="Deficiência" id="deficiencia" name="candidato.pessoal.deficiencia" list="deficiencias" liClass="liLeft , campo"/>
		
			<div style="clear: both;width:700px;"></div>

			<#if moduloExterno?exists && moduloExterno && candidato.id?exists>
				<@ww.hidden name="candidato.senha" id="senha" value="" />
				<@ww.hidden name="confirmaSenha" id="comfirmaSenha" />
			<#else>
				<hr style="border:0; border-top:1px solid #CCCCCC;">
				<#if candidato?exists && candidato.id?exists && !moduloExterno>
					<B>(Deixe em branco para manter a senha atual)</B><br><br>
				</#if>
				
				<#if moduloExterno>
					<div style="font-weight: bold;">Prezado Candidato, esta senha deverá ser utilizada por você para acessos futuros ao seu currículo e a visualização de vagas.</div>
				</#if>
				<@ww.password label="Senha" name="candidato.senha" id="senha" cssStyle="width: 100px;" liClass="liLeft"/>
				<@ww.password label="Confirmar Senha" name="confirmaSenha" id="comfirmaSenha" cssStyle="width: 100px;"/>
				
			</#if>
      </div>



      <div id="content3" class="3" style="display: none;">
		<li>
			<@ww.div id="wwgrp_cargosCheck" cssClass="campo">
			<ul>
				<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" id="cargosCheck" list="cargosCheckList" filtro="true" />
			</ul>
			</@ww.div>
		</li>	
		<li>
			<@ww.div id="wwgrp_areasCheck" cssClass="campo">
			<ul>
        		<@frt.checkListBox label="Áreas de Interesse" name="areasCheck" list="areasCheckList" id="areasCheck" onClick="populaConhecimento(document.forms[0],'areasCheck');" filtro="true" />
			</ul>
			</@ww.div>
		</li>	
		<li>
			<@ww.div id="wwgrp_conhecimentosCheck" cssClass="campo">
				<ul>        
					<@frt.checkListBox label="Conhecimentos" name="conhecimentosCheck" list="conhecimentosCheckList" id="conhecimentosCheck" filtro="true" />
				</ul>
			</@ww.div>
		</li>	
		<li>
			<@ww.div id="wwgrp_colocacao" cssClass="campo">
				<ul>
			        <@ww.select label="Colocação" name="candidato.colocacao" list="colocacaoList" id ="colocacao" liClass="liLeft"/>
			        <@ww.textfield label="Pretensão Salarial" name="candidato.pretencaoSalarial"  onkeypress = "return(somenteNumeros(event,','));" cssStyle="width:85px; text-align:right;" maxLength="12" />
				</ul>
			</@ww.div>
		</li>	
      </div>

	  <div id="content5" class="5" style="display: none;">
	  	<li>
			<@ww.div id="wwgrp_identidade" cssClass="campo">
				<ul>
					<b><@ww.label label="Identidade" /></b>
			    	<@ww.textfield label="RG" name="candidato.pessoal.rg" id="identidade" cssStyle="width: 106px;" maxLength="15" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));" />
			  	   	<@ww.textfield label="Órgão Emissor" id="rgOrgaoEmissor" name="candidato.pessoal.rgOrgaoEmissor" cssStyle="width: 73px;" maxLength="10" liClass="liLeft" />
			       	<@ww.select label="Estado" name="candidato.pessoal.rgUf.id" id="rgUf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
			      	<@ww.datepicker label="Data de Expedição" name="candidato.pessoal.rgDataExpedicao" id="rgDataExpedicao" cssClass="mascaraData" value="${rgDataExpedicao}"/>
		        	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
      			</ul>
			</@ww.div>
		</li>	
      	
	  	<li>
			<@ww.div id="wwgrp_carteiraHabilitacao" cssClass="campo">
				<ul>
			       	<b><@ww.label label="Carteira de Habilitação" /></b>
					<@ww.textfield id="carteiraHabilitacao" label="Nº de Registro" name="candidato.habilitacao.numeroHab" cssStyle="width: 100px;" maxLength="11" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
			      	<@ww.textfield label="Prontuário" name="candidato.habilitacao.registro"  cssStyle="width: 120px;" maxLength="15" liClass="liLeft"/>
			      	<@ww.datepicker label="Emissão" name="candidato.habilitacao.emissao" id="emissao" liClass="liLeft" cssClass="mascaraData validaDataIni" value="${habEmissao}"/>
			      	<@ww.datepicker label="Vencimento" name="candidato.habilitacao.vencimento" id="vencimento"  liClass="liLeft" cssClass="mascaraData validaDataFim" value="${dataVenc}"/>
			       	<@ww.textfield label="Categoria(s)" name="candidato.habilitacao.categoria" id="chCategoria" cssStyle="width:25px" maxLength="3" />
			        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
      			</ul>
			</@ww.div>
		</li>	
		
	  	<li>
			<@ww.div id="wwgrp_tituloEleitoral" cssClass="campo">
				<ul>
					<b><@ww.label label="Título Eleitoral" /></b>
			    	<@ww.textfield label="Número" name="candidato.pessoal.tituloEleitoral.titEleitNumero" id="tituloEleitoral" cssStyle="width: 95px;" maxLength="13" liClass="liLeft"/>
			    	<@ww.textfield label="Zona" name="candidato.pessoal.tituloEleitoral.titEleitZona" id="titEleitZona" cssStyle="width: 95px;" maxLength="13" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
			    	<@ww.textfield label="Seção" name="candidato.pessoal.tituloEleitoral.titEleitSecao" id="titEleitSecao" cssStyle="width: 95px;" maxLength="13" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
			        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
      			</ul>
			</@ww.div>
		</li>	
		
	  	
	  	<li>
			<@ww.div id="wwgrp_certificadoMilitar" cssClass="campo">
				<ul>
					<b><@ww.label label="Certificado Militar" /></b>
			    	<@ww.textfield label="Número" name="candidato.pessoal.certificadoMilitar.certMilNumero" id="certificadoMilitar" cssStyle="width: 88px;" maxLength="12" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
			    	<@ww.textfield label="Tipo" name="candidato.pessoal.certificadoMilitar.certMilTipo" id="certMilTipo" cssStyle="width: 38px;" maxLength="5" liClass="liLeft"/>
			    	<@ww.textfield label="Série" name="candidato.pessoal.certificadoMilitar.certMilSerie" id="certMilSerie" cssStyle="width: 88px;" maxLength="12"/>
			        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
      			</ul>
			</@ww.div>
		</li>	
		
	  	<li id="wwgrp_ctps" class="campo">
			<@ww.div >
				<ul>
					<b><@ww.label label="CTPS - Carteira de Trabalho e Previdência Social" /></b>
			    	<@ww.textfield label="Número" name="candidato.pessoal.ctps.ctpsNumero" id="ctps" cssStyle="width: 58px;" maxLength="8" liClass="liLeft"/>
			    	<@ww.textfield label="Série" name="candidato.pessoal.ctps.ctpsSerie" id="ctpsSerie" cssStyle="width: 38px;" maxLength="6" liClass="liLeft"/>
			    	<@ww.textfield label="DV" name="candidato.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 9px;" maxLength="1" liClass="liLeft"/>
			       	<@ww.select label="Estado" name="candidato.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
			      	<@ww.datepicker label="Data de Expedição" name="candidato.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" cssClass="mascaraData" value="${ctpsDataExpedicao}"/>
			        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	      		</ul>
			</@ww.div>
		</li>	
		
	  	<li id="wwgrp_pis" class="campo">
			<@ww.div >
				<ul>
					<b><@ww.label label="PIS - Programa de Integração Social" /></b>
					<@ww.textfield label="Número" name="candidato.pessoal.pis" id="pis" cssClass="mascaraPis" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,'{,}'));" maxLength="11" />
	      		</ul>
			</@ww.div>
		</li>	
      </div>
      
      <div id="content6" class="6" style="display:none;">
		<label for="ocrTexto">Descrição do Currículo:</label>
		<@ww.textarea name="candidato.ocrTexto" id="ocrTexto" cssStyle="width: 777px;height: 500px"/>
	  </div>
      
		<#if habilitaCampoExtra>
	      <div id="content7" class="7" style="display:none;">
			<#include "../geral/camposExtras.ftl" />
		  </div>
		</#if>
      
      <@ww.hidden name="candidato.dataCadastro" />
      <@ww.hidden name="candidato.empresa.id" />
      <@ww.hidden name="candidato.blackList" />
      <@ww.hidden name="candidato.observacaoBlackList" />
      <@ww.hidden name="candidato.contratado" />
      <@ww.hidden name="candidato.disponivel" />
      <@ww.hidden name="candidato.origem" />
      <@ww.hidden name="empresaId" />

      <@ww.hidden name="candidato.cursos" id="candidato.cursos" />
      <@ww.hidden name="candidato.observacao" id="candidato.observacao" />
      <@ww.hidden name="candidato.observacaoRH" id="candidato.observacaoRH" />
      <@ww.hidden name="candidato.id" />
      
      <@ww.hidden name="solicitacao.id" />

	  <@ww.token/>
    </@ww.form>

    <#-- Campo para controle das abas -->
    <@ww.hidden id="aba" name="aba" value="1"/>

    <br />
    <div class="buttonGroup" style="width: 440px; float:right; text-align: right;">
        <button class="btnVoltarDesabilitado" id='voltar' disabled="disabled" onclick="abas(-1, 'V',${edicao}, qtdAbas);" accesskey="V">
        </button>
        <button id='avancar' onclick="abas(-1, 'A',${edicao}, qtdAbas);" class="btnAvancar" accesskey="A">
        </button>
    </div>

    <div class="buttonGroup" style="width: 440px;">
		<#if moduloExterno>
       		<button class="btnGravarDesabilitado"  disabled="disabled" onclick="if(setaCampos())javascript:validarCamposCpf();" id="gravarModuloExterno" >
        	</button>
        <#else>
       		<button class="btnGravar" onclick="if(setaCampos())javascript:validarCamposCpf();" id="gravar" >
        	</button>
        </#if>
		
        <button class="btnCancelar" onclick="newConfirm('Tem certeza que deseja cancelar?', function(){window.location='${actionCancelar}'});" class="btnCancelar" accesskey="C">
        </button>
    </div>
    
    <script>
      document.getElementById('desCursos').value = document.getElementById('candidato.cursos').value;
      document.getElementById('infoAdicionais').value = document.getElementById('candidato.observacao').value;

      if(document.getElementById('obsrh'))
        document.getElementById('obsrh').value = document.getElementById('candidato.observacaoRH').value;

      function mostra()
      {
        mostrar(document.getElementById('fotoTbl'));
        mostrar(document.getElementById('fotoImg'));
      }
      function mostraFoto()
      {
        mostrar(document.getElementById('fotoUpLoad'));
      }

      if(document.getElementById('pensao').value == "true")
      {
        document.getElementById('quantidadeId').disabled = false;
        document.getElementById('valorId').disabled = false;
      }
      
      function verificaCpfDuplicadoDecisao(valorCpf)
	  {
   		<#if !candidato.id?exists>
   			verificaCpfDuplicado(valorCpf, ${idDaEmpresa}, ${moduloExterno?string});
		<#else>
   			verificaCpfDuplicado(valorCpf, ${idDaEmpresa}, ${moduloExterno?string}, ${candidato.id});
		</#if>
	  }	
      
    </script>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
	<div id="parentesDialog"></div>
</body>
</html>