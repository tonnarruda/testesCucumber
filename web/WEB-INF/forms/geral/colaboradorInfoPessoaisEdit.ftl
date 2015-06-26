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
	<script type="text/javascript" src="jsr_class.js"></script>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js?version=${versao}"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<#include "../cargosalario/calculaSalarioInclude.ftl" />

	<script type="text/javascript">

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
			addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');
			
			$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
			$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
			$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action"/>');			
		});
		
	</script>
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
		
		<#assign totalAbas = 4/>
	<#else>
		<#assign totalAbas = 3/>
	</#if>
	
	
	
	
	<#assign validarCampos="return validaFormulario('form', new Array('ende','num','uf','cidade','ddd','fone','escolaridade'), new Array('cep' ${validaDataCamposExtras}))"/>
	<@ww.head />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if colaborador.id?exists>
		<div id="abas">
			<div id="aba1"><a href="javascript: abas(1, '', true, ${totalAbas})">Dados Pessoais</a></div>
			<div id="aba2"><a href="javascript: abas(2, '', true, ${totalAbas})">Formação Escolar</a></div>
			<div id="aba3"><a href="javascript: abas(3, '', true, ${totalAbas})">Experiências</a></div>
			
			<#if habilitaCampoExtra>
				<div id="aba4"><a href="javascript: abas(4, '', true, ${totalAbas})">Extra</a></div>
			</#if>
		</div>
	
		<#-- Campos fora do formulário por causa do ajax.
		Antes de enviar o form os cursos e a observação são setados em campos hidden dentro do form. -->
		<#-- Acima do form para corrigir problema de layout no IE -->
		<div id="content2" style="display: none;">
			<@ww.div id="formacao" />
			<@ww.div id="idioma" />
			<@ww.textarea label="Cursos" id="desCursos" name="desCursos" cssStyle="width: 705px;"/>
		</div>
		<div id="content3" style="display: none;">
			<@ww.div id="expProfissional" />
			<@ww.textarea label="Informações Adicionais" id="obs" name="obs" cssStyle="width: 705px;"/>
		</div>
	
		<@ww.form name="form" action="updateInfoPessoais.action" onsubmit="${validarCampos}" validate="true" method="POST">
			<div id="content1">
				<@ww.textfield label="CEP" name="colaborador.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft"/>
				<@ww.textfield label="Logradouro" name="colaborador.endereco.logradouro" id="ende" required="true" cssStyle="width: 300px;" liClass="liLeft" maxLength="40"/>
				<@ww.textfield label="Nº"  name="colaborador.endereco.numero" id="num" required="true" cssStyle="width:40px;" liClass="liLeft" maxLength="10"/>
				<@ww.textfield label="Complemento" name="colaborador.endereco.complemento" cssStyle="width: 232px;" maxLength="20"/>
				<@ww.select label="Estado"     name="colaborador.endereco.uf.id" id="uf" list="estados" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""  required="true"/>
				<@ww.select label="Cidade" name="colaborador.endereco.cidade.id" id="cidade" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 237px;" headerKey="" headerValue="" required="true" />
					
				<@ww.textfield label="Bairro" name="colaborador.endereco.bairro" id="bairroNome" cssStyle="width: 350px;"  maxLength="85"/>
				<@ww.div id="bairroContainer"/>
					
				<@ww.textfield label="E-mail"    name="colaborador.contato.email" id="email" cssStyle="width: 300px;" maxLength="40" liClass="liLeft"/>
				<@ww.textfield label="DDD" name="colaborador.contato.ddd" required="true" id="ddd" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" maxLength="2" cssStyle="width:25px;"/>
				<@ww.textfield label="Telefone"  name="colaborador.contato.foneFixo" required="true" id="fone" onkeypress = "return(somenteNumeros(event,''));" maxLength="8" liClass="liLeft" cssStyle="width:60px;"/>
				<@ww.textfield label="Celular"   name="colaborador.contato.foneCelular" onkeypress = "return(somenteNumeros(event,''));" maxLength="8" cssStyle="width:60px;"/>
				<@ww.select label="Escolaridade" name="colaborador.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 303px;" liClass="liLeft" required="true" headerKey="" headerValue="Selecione..."/>
				<@ww.select label="Estado Civil" name="colaborador.pessoal.estadoCivil" list="estadosCivis" cssStyle="width: 210px;"/>
	
				<@ww.textfield label="Nome do Pai" name="colaborador.pessoal.pai" id="nomePai" liClass="liLeft" cssStyle="width: 300px;" maxLength="60"/>
				<@ww.textfield label="Nome da Mãe" name="colaborador.pessoal.mae" id="nomeMae" cssStyle="width: 300px;" maxLength="60"/>
				<@ww.textfield label="Nome do Cônjuge" name="colaborador.pessoal.conjuge" id="nomeConjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft" />
				<@ww.textfield label="Qtd. Filhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="colaborador.pessoal.qtdFilhos" id="qtdFilhos" cssStyle="width:25px; text-align:right;" maxLength="2" />
			</div>
			
			<#if habilitaCampoExtra>
				<div id="content4" style="display: none;">
					<#include "camposExtras.ftl" />
			    </div>
			</#if>
			
			<@ww.hidden name="colaborador.camposExtras.id" />		
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