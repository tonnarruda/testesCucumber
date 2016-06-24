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
	</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<#include "../cargosalario/calculaSalarioInclude.ftl" />

	<#assign totalAbas = 3/>
	
	<script type="text/javascript">
	
		var camposColaboradorVisivel = "${parametrosDoSistema.camposColaboradorVisivel}";
		var camposColaboradorObrigatorio = "${parametrosDoSistema.camposColaboradorObrigatorio}";

		var abasVisiveis = "${parametrosDoSistema.camposColaboradorTabs}";
		var arrayAbasVisiveis  = abasVisiveis.split(',');
		qtdAbas = arrayAbasVisiveis.length;
		var arrayObrigatorios = new Array();

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
			
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action?internalToken=${internalToken}"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action?internalToken=${internalToken}"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action?internalToken=${internalToken}"/>');			
		});
		
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
			
			arrayObrigatorios = $.grep(arrayObrigatorios, function(value) {
				return value != 'formacao' && value != 'idioma' && value != 'expProfissional' && 
				value != 'nome' && value != 'nomeComercial' && value != 'nascimento' && value != 'sexo' &&
				value != 'cpf' && value != 'deficiencia' && value != 'dt_admissao';
			});
	
			arrayValidacao = arrayObrigatorios;
			
			return validaFormulario('form', arrayValidacao, new Array('ende','num','uf','cidade','ddd','fone','escolaridade','cep'))
		}
		
		
	</script>
	
	<#assign validarCampos="validaFormularioDinamico();"/>
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

		<@ww.form name="form" action="updateInfoPessoais.action" onsubmit="${validarCampos}" validate="true" method="POST">
			<div id="content1">
				<@ww.div id="wwgrp_endereco" cssClass="campo">
					<@ww.textfield label="CEP" name="colaborador.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft"/>
					<@ww.textfield label="Logradouro" name="colaborador.endereco.logradouro" id="ende" cssStyle="width: 300px;" liClass="liLeft" maxLength="40"/>
					<@ww.textfield label="Nº"  name="colaborador.endereco.numero" id="num" cssStyle="width:40px;" liClass="liLeft" maxLength="10"/>
					<@ww.textfield label="Complemento" name="colaborador.endereco.complemento" id="complemento" cssStyle="width: 232px;" maxLength="20"/>
					<@ww.select label="Estado"     name="colaborador.endereco.uf.id" id="uf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" />
					<@ww.select label="Cidade" name="colaborador.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 237px;" headerKey="" headerValue="" />
						
					<@ww.textfield label="Bairro" name="colaborador.endereco.bairro" id="bairroNome" cssStyle="width: 350px;"  maxLength="85"/>
					<@ww.div id="bairroContainer"/>
				</@ww.div>	
				<@ww.textfield label="E-mail"    name="colaborador.contato.email" id="email" cssStyle="width: 300px;" maxLength="40" liClass="liLeft campo"/>
				<@ww.div id="wwgrp_fone"  cssClass="campo">
					<@ww.textfield label="DDD" name="colaborador.contato.ddd" id="ddd" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" maxLength="2" cssStyle="width:25px;"/>
					<@ww.textfield label="Telefone"  name="colaborador.contato.foneFixo" id="fone" onkeypress = "return(somenteNumeros(event,''));" maxLength="8" liClass="liLeft" cssStyle="width:60px;"/>
				</@ww.div>
				
				<@ww.textfield label="Celular" name="colaborador.contato.foneCelular" id="celular" onkeypress = "return(somenteNumeros(event,''));" maxLength="9" cssStyle="width:80px;" liClass="campo"/>
				<@ww.select label="Escolaridade" name="colaborador.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 303px;" liClass="liLeft campo" headerKey="" headerValue="Selecione..."/>
				
				<@ww.select label="Estado Civil" name="colaborador.pessoal.estadoCivil" id="estadoCivil" list="estadosCivis" cssStyle="width: 210px;" liClass="campo"/>
				
				<@ww.textfield label="Nome do Pai" name="colaborador.pessoal.pai" id="nomePai" liClass="liLeft campo" cssStyle="width: 300px;" maxLength="60"/>
				<@ww.textfield label="Nome da Mãe" name="colaborador.pessoal.mae" id="nomeMae" liClass="campo" cssStyle="width: 300px;" maxLength="60"/>
				<@ww.textfield label="Nome do Cônjuge" name="colaborador.pessoal.conjuge" id="nomeConjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft campo" />
				<@ww.textfield label="Qtd. Filhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="colaborador.pessoal.qtdFilhos" id="qtdFilhos" liClass="campo" cssStyle="width:25px; text-align:right;" maxLength="2" />
				<div style="clear: both;"></div>
			</div>
			
			<@ww.hidden name="colaborador.cursos" id="colaborador.cursos" />
			<@ww.hidden name="colaborador.observacao" id="colaborador.observacao" />
			<@ww.hidden name="colaborador.candidato.id"/>
			<@ww.hidden name="colaborador.id"/>
			<@ww.hidden name="colaborador.codigoAC"/>
			
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
				<button onclick="if (setaCampos()) ${validarCampos};" id="gravar" class="btnGravar">
				</button>
			</div>
		</div>
	</#if>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
</body>

</html>