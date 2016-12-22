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

	<#assign totalAbas = 4/>
	
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
			
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action"/>');			
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
			
			return validaFormulario('form', arrayValidacao, new Array('ende','num','uf','cidade','ddd','fone','escolaridade','cep','emissao','vencimento','rgDataExpedicao','ctpsDataExpedicao', 'pis'));
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
			<div id="aba4" class="abaDocumentos"><a href="javascript: abas(4, '', true, ${totalAbas})">Documentos</a></div>
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
			
		<div id="content4" style="display: none;">
			<li>
				<@ww.div id="wwgrp_identidade" cssClass="campo">
					<ul>
					<b><@ww.label label="Identidade" /></b>
			    	<@ww.textfield label="Número" name="colaborador.pessoal.rg" id="rg" cssStyle="width: 106px;" maxLength="15" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));" />
			  	   	<@ww.textfield label="Órgão Emissor" name="colaborador.pessoal.rgOrgaoEmissor" cssStyle="width: 73px;" maxLength="10" liClass="liLeft" />
			       	<@ww.select label="Estado" name="colaborador.pessoal.rgUf.id" id="rgUf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
			      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.rgDataExpedicao" id="rgDataExpedicao" cssClass="mascaraData" value="${rgDataExpedicao}"/>
			      	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	         		</ul>
				</@ww.div>
			</li>	
	       	<li>
				<@ww.div id="wwgrp_carteiraHabilitacao" cssClass="campo">
					<ul>
				       	<b><@ww.label label="Carteira de Habilitação" /></b>
						<@ww.textfield label="Nº de Registro" id="carteiraHabilitacao" name="colaborador.habilitacao.numeroHab" cssStyle="width: 100px;" maxLength="11" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				      	<@ww.textfield label="Prontuário" name="colaborador.habilitacao.registro" cssStyle="" maxLength="15" liClass="liLeft"/>
				      	<@ww.datepicker label="Emissão" name="colaborador.habilitacao.emissao" id="emissao" liClass="liLeft" cssClass="mascaraData" value="${habEmissao}"/>
				      	<@ww.datepicker label="Vencimento" name="colaborador.habilitacao.vencimento" id="vencimento" liClass="liLeft" cssClass="mascaraData" value="${dataVenc}"/>
				       	<@ww.textfield label="Categoria(s)" name="colaborador.habilitacao.categoria" cssStyle="width:25px" maxLength="3" />
				       	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	        		</ul>
				</@ww.div>
			</li>	
	  		<li>
				<@ww.div id="wwgrp_tituloEleitoral" cssClass="campo">
					<ul>
						<b><@ww.label label="Título Eleitoral" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.tituloEleitoral.titEleitNumero" id="titEleitNumero" cssStyle="width: 95px;" maxLength="13" liClass="liLeft"/>
				    	<@ww.textfield label="Zona" name="colaborador.pessoal.tituloEleitoral.titEleitZona" id="titEleitZona" cssStyle="width: 95px;" maxLength="3" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				    	<@ww.textfield label="Seção" name="colaborador.pessoal.tituloEleitoral.titEleitSecao" id="titEleitSecao" cssStyle="width: 95px;" maxLength="4" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				    	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	        		</ul>
				</@ww.div>
			</li>	
			
		  	<li>
				<@ww.div id="wwgrp_certificadoMilitar" cssClass="campo">
					<ul>
						<b><@ww.label label="Certificado Militar" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.certificadoMilitar.certMilNumero" id="certMilNumero" cssStyle="width: 88px;" maxLength="12" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
				    	<@ww.textfield label="Tipo" name="colaborador.pessoal.certificadoMilitar.certMilTipo" id="certMilTipo" cssStyle="width: 38px;" maxLength="5" liClass="liLeft"/>
				    	<@ww.textfield label="Série" name="colaborador.pessoal.certificadoMilitar.certMilSerie" id="certMilSerie" cssStyle="width: 88px;" maxLength="12"/>
				    	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	        </ul>
				</@ww.div>
			</li>	
	  		<li id="wwgrp_ctps" class="campo">
				<@ww.div >
					<ul>
						<b><@ww.label label="CTPS - Carteira de Trabalho e Previdência Social" /></b>
				    	<@ww.textfield label="Número" name="colaborador.pessoal.ctps.ctpsNumero" id="ctpsNumero" cssStyle="width: 58px;" maxLength="8" liClass="liLeft"/>
				    	<@ww.textfield label="Série" name="colaborador.pessoal.ctps.ctpsSerie" id="ctpsSerie" cssStyle="width: 38px;" maxLength="6" liClass="liLeft"/>
				    	<@ww.textfield label="DV" name="colaborador.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 11px;" maxLength="1" liClass="liLeft"/>
				       	<@ww.select label="Estado" name="colaborador.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
				      	<@ww.datepicker label="Data de Expedição" name="colaborador.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" cssClass="mascaraData" value="${ctpsDataExpedicao}"/>
				      	<li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
	        		</ul>
				</@ww.div>
			</li>	
	  		<li id="wwgrp_pis" class="campo">
				<@ww.div >
					<ul>
						<b><@ww.label label="PIS - Programa de Integração Social"/></b>
						<@ww.textfield label="Número" name="colaborador.pessoal.pis" id="pis" cssClass="mascaraPis" cssStyle="width: 79px;" onkeypress = "return(somenteNumeros(event,'{,}'));" maxLength="11" />
				    	<div style="clear: both;"></div>
				 	</ul>
				</@ww.div>
			</li>
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