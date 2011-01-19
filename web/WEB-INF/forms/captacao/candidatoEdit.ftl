<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
  <#include "../ftl/mascarasImports.ftl" />

 <style type="text/css">

	<#if moduloExterno>
		@import url('<@ww.url includeParams="none" value="/css/displaytagModuloExterno.css"/>');
	<#else>
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</#if>

</style>

  <link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/candidato.css"/>" media="screen" type="text/css">
  <link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css"/>" media="screen" type="text/css">

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

  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EnderecoDWR.js"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
  <script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

  <script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js"/>"></script>
  <script type="text/javascript" src="<@ww.url includeParams="none" value="/js/formataValores.js"/>"></script>

  <script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.form.js"/>'></script>

  <style type="text/css">
    @import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
  </style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
	
  <#if empresaId?exists>
    <#assign idDaEmpresa=empresaId/>
  <#else>
    <#assign idDaEmpresa><@authz.authentication operation="empresaId"/></#assign>
  </#if>

  <script type='text/javascript'>
    function populaConhecimento(frm, nameCheck)
    {
      DWRUtil.useLoadingMessage('Carregando...');
      var areasIds = getArrayCheckeds(frm, nameCheck);

      CandidatoDWR.getConhecimentos(createListConhecimentos, areasIds, ${idDaEmpresa});
    }

   	function validarCamposCpf()
    {
		<#if moduloExterno?exists && moduloExterno>
			return validaFormularioEPeriodo('form', new Array('cpf' , 'nome','escolaridade','ende','num','uf','cidade','ddd','fone'), new Array('cpf' , 'nascimento', 'emissao', 'vencimento', 'rgDataExpedicao', 'ctpsDataExpedicao'));
	   	<#else> 
	       	if (jQuery("#cpf").val() == "   .   .   -  ")
	   			return validaFormularioEPeriodo('form', new Array('nome','escolaridade','ende','num','uf','cidade','ddd','fone'), new Array('nascimento', 'emissao', 'vencimento', 'rgDataExpedicao', 'ctpsDataExpedicao'));
	   		else
		   		return validaFormularioEPeriodo('form', new Array('cpf' , 'nome','escolaridade','ende','num','uf','cidade','ddd','fone'), new Array('cpf' , 'nascimento', 'emissao', 'vencimento', 'rgDataExpedicao', 'ctpsDataExpedicao'));
		 </#if>
    }

    function createListConhecimentos(data)
    {
      addChecks('conhecimentosCheck',data)
    }

    function setaCampos()
    {
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
		    jAlert("Não é permitido selecionar mais de ${maxCandidataCargo} cargos (Cargo / Função Pretendida)");
		    return false;
		  }
		</#if>

		document.getElementById('candidato.cursos').value = document.getElementById('desCursos').value;
		document.getElementById('candidato.observacao').value = document.getElementById('obs').value;

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
		if(document.getElementById('senha').value == "" )
		{
			jAlert("Senha obrigatória para acesso ao modulo exteno.");
			return false;
		}
	    
		if((document.getElementById('senha').value != document.getElementById('comfirmaSenha').value) )
		{
			jAlert("Senha não confere.");
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
     var pagaPensao = document.getElementById('pagaPensaoId');
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

	jQuery(function($) {
		$("#idioma").load('<@ww.url includeParams="none" value="/captacao/idioma/list.action"/>');
		$("#formacao").load('<@ww.url includeParams="none" value="/captacao/formacao/list.action"/>');
		$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action?empresaId=${idDaEmpresa}"/>');
		
		addBuscaCEP('cep', 'ende', 'bairroNome', 'cidade', 'uf');
	});
	
	
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

	<#assign camposObrigatorio = "true"/>
	<#if candidato.id?exists>
			<#assign camposObrigatorio = "false"/>
	</#if>

</head>

<body>
<@ww.actionerror />

	<#assign qtdAbas = 5/>
	<#if !exibirAbaDocumentos>
		<#assign qtdAbas = 4/>
	</#if>

    <div id="abas">
      <div id="aba1"><a href="javascript: abas(1, '',${edicao},${qtdAbas})">Dados Pessoais</a></div>
      <div id="aba2"><a href="javascript: abas(2, '',${edicao},${qtdAbas})">Formação Escolar</a></div>
      <div id="aba3"><a href="javascript: abas(3, '',${edicao},${qtdAbas})">Perfil Profissional</a></div>
      <div id="aba4"><a href="javascript: abas(4, '',${edicao},${qtdAbas})">Experiências</a></div>
    <#if exibirAbaDocumentos>
      <div id="aba5"><a href="javascript: abas(5, '',${edicao})">Documentos</a></div>
    </#if>
    
    </div>

    <div id="content2" style="display:none; width:98%;">
	
	<@ww.div  id="formacao"/>
	<@ww.div  id="idioma"/>
	Outros Cursos:<br />
	<@ww.textarea id="desCursos" name="desCursos" cssStyle="width:705px;" onblur="${capitalizar}"/>
    </div>

	<div id="content4" style="display: none;">
		<@ww.div id="expProfissional"/>

		Informações Adicionais:<br />
		<@ww.textarea id="obs" name="obs" cssStyle="width: 705px;" onblur="${capitalizar}"/>

		<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO_CANDIDATO">
			<#if !moduloExterno>
				Observações do RH<br />
				<@ww.textarea id="obsrh" name="obsrh" cssStyle="width: 705px;"/>
			</#if>	
		</@authz.authorize>
	</div>


    <@ww.form name="form" action="${formAction}" validate="true" onsubmit="javascript:validarCamposCpf()" method="POST" enctype="multipart/form-data">
		<div id="content1">
			<#if candidato.foto?exists>
				<input type="checkbox" name="exibirFoto" onclick="mostra();" id="exibeFoto"/><label for="exibeFoto">Exibir Foto</label>
				<div id="fotoTbl" <#if candidato?exists && candidato.foto?exists>style="display:none;"</#if>>
					<table style="border:0px;">
						<tr>
							<td>
								<#if candidato.foto?exists>
									<#if moduloExterno?exists && moduloExterno>
										<img src="<@ww.url includeParams="none" value="../captacao/candidato/showFoto.action?candidato.id=${candidato.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
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

			<@ww.textfield label="Nome" name="candidato.nome" id="nome" required="true" liClass="liLeft" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}" onblur="getCandidatosHomonimos();"/>
			<@ww.datepicker label="Nascimento" name="candidato.pessoal.dataNascimento" id="nascimento" liClass="liLeft" cssClass="mascaraData" value="${dataNas}"/>
			<@ww.textfield label="Naturalidade" name="candidato.pessoal.naturalidade" cssStyle="width: 160px;" maxLength="100" liClass="liLeft" onblur="${capitalizar}"/>
			<@ww.select label="Sexo" name="candidato.pessoal.sexo" list="sexos" />
			
			<@ww.div id="homonimos" cssStyle="color:blue;display:none; ">
				<#if moduloExterno?exists && moduloExterno>
					<@ww.hidden id="nomesHomonimos"/>
				<#else>
					Existe(m) candidato(s) homônimo(s):
					<@ww.div id="nomesHomonimos" cssStyle="color:red;">	</@ww.div>
				</#if>
			</@ww.div>
			
																																																							
			<@ww.textfield label="CPF"  name="candidato.pessoal.cpf" id="cpf"  cssClass="mascaraCpf" liClass="liLeft" required="${cpfObrigatorio}" onchange="verificaCpf(this.value);" onblur="verificaCpf(this.value);"/>
			<@ww.select label="Escolaridade" name="candidato.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 300px;"  required="true" headerKey="" headerValue="Selecione..." />
			<@ww.div id="msgCPFDuplicado" cssStyle="color:blue;display:none; "></@ww.div>
			
			<@ww.textfield label="CEP" name="candidato.endereco.cep" id="cep" cssClass="mascaraCep" liClass="liLeft" />
			<@ww.textfield label="Logradouro" name="candidato.endereco.logradouro" id="ende" required="${camposObrigatorio}" cssStyle="width: 300px;" maxLength="40" liClass="liLeft" onblur="${capitalizar}"/>
			<@ww.textfield label="Nº" name="candidato.endereco.numero" id="num" required="${camposObrigatorio}" cssStyle="width:40px;" maxLength="8" liClass="liLeft" onblur="${capitalizar}"/>
			<@ww.textfield label="Complemento" name="candidato.endereco.complemento" cssStyle="width: 250px;" maxLength="20" onblur="${capitalizar}"/>

			<@ww.select label="Estado" name="candidato.endereco.uf.id" id="uf" list="ufs" required="true" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" />
			<@ww.select label="Cidade" name="candidato.endereco.cidade.id" id="cidade" required="true" list="cidades" liClass="liLeft" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" />
			<@ww.textfield label="Bairro" name="candidato.endereco.bairro" id="bairroNome" cssStyle="width: 362px;" maxLength="20" onblur="${capitalizar}"/>
			<div id="bairroContainer"></div>
			

			<@ww.textfield label="E-mail" name="candidato.contato.email" id="email" cssStyle="width: 300px;" maxLength="40" liClass="liLeft"/>
			<@ww.textfield label="DDD" name="candidato.contato.ddd" id="ddd" required="true" onkeypress = "return(somenteNumeros(event,''));" liClass="liLeft" cssStyle="width: 25px;" maxLength="2" />
			<@ww.textfield label="Telefone" name="candidato.contato.foneFixo" id="fone" required="true" onkeypress="return(somenteNumeros(event,''));"  liClass="liLeft" cssStyle="width: 60px;" maxLength="8" />
			<@ww.textfield label="Celular" name="candidato.contato.foneCelular" onkeypress = "return(somenteNumeros(event,''));" cssStyle="width: 60px;" maxLength="8" liClass="liLeft"/>
			<@ww.textfield label="Contato" name="candidato.contato.nomeContato" id="nomeContato" cssStyle="width: 180px;" maxLength="30" />

			<@ww.textfield label="Parentes/Amigos na empresa" name="candidato.pessoal.parentesAmigos" id="parentes" cssStyle="width: 300px;" maxLength="100" liClass="liLeft" onblur="${capitalizar}"/>
			<#if !moduloExterno?exists || !moduloExterno>
				<@ww.textfield label="Indicado Por" name="candidato.pessoal.indicadoPor" id="indicado" cssStyle="width: 300px;" maxLength="100"/>
			</#if>

			<@ww.select label="Estado Civil" name="candidato.pessoal.estadoCivil" list="estadosCivis" cssStyle="width: 210px;" liClass="liLeft" />
			<@ww.textfield label="Qtd. Filhos" onkeypress = "return(somenteNumeros(event,''));" maxLength="2" name="candidato.pessoal.qtdFilhos" id="qtdFilhos" cssStyle="width:25px; text-align:right;" maxLength="2" />

			<@ww.textfield label="Nome do Cônjuge" name="candidato.pessoal.conjuge" cssStyle="width: 300px;" maxLength="40" liClass="liLeft" onblur="${capitalizar}"/>
			<@ww.textfield label="Profissão do Cônjuge" name="candidato.pessoal.profissaoConjuge" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}"/>

			<@ww.textfield label="Nome do Pai" name="candidato.pessoal.pai" cssStyle="width: 300px;" maxLength="60" liClass="liLeft" onblur="${capitalizar}"/>
			<@ww.textfield label="Profissão do Pai" name="candidato.pessoal.profissaoPai" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}"/>

			<@ww.textfield label="Nome da Mãe" name="candidato.pessoal.mae" cssStyle="width: 300px;" maxLength="60" liClass="liLeft" onblur="${capitalizar}"/>
			<@ww.textfield label="Profissão da Mãe" name="candidato.pessoal.profissaoMae" cssStyle="width: 300px;" maxLength="100" onblur="${capitalizar}"/>

			<br />
			<span style="font-family:Verdana; font-weight:normal;">Informações Sócio-Econômicas:</span>
			<br /><br />
			<@ww.select label="Paga Pensão" id="pagaPensaoId" name="candidato.socioEconomica.pagaPensao" list=r"#{true:'Sim',false:'Não'}" onchange="habilitaPagaPensao();" cssStyle="width: 96px;" liClass="liLeft"/>
			<@ww.textfield label="Quantidade" id="quantidadeId" disabled="true"  name="candidato.socioEconomica.quantidade" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{}'));" cssStyle="width:25px; text-align:right;" maxLength="2" />
			<@ww.textfield label="Valor" id="valorId" disabled="true" name="candidato.socioEconomica.valor" liClass="liLeft" maxLength="12" onkeypress = "return(somenteNumeros(event,','));" cssStyle="width:85px; text-align:right;"/>
			<@ww.select label="Possui Veículo" name="candidato.socioEconomica.possuiVeiculo" list=r"#{true:'Sim',false:'Não'}" cssStyle="width: 96px;" liClass="liLeft"/>
			<@ww.select label="Deficiência" name="candidato.pessoal.deficiencia" list="deficiencias"/>
		
			<#if moduloExterno?exists && moduloExterno && candidato.id?exists>
				<@ww.hidden name="candidato.senha" id="senha" value=""/>
				<@ww.hidden name="confirmaSenha" id="comfirmaSenha" />
			<#else>
				<hr style="border:0; border-top:1px solid #CCCCCC;">
				<#if candidato?exists && candidato.id?exists && !moduloExterno>
				<B>(Deixe em branco para manter a senha atual)</B><br><br>
				</#if>
				<@ww.password label="Senha" name="candidato.senha" id="senha" cssStyle="width: 100px;" liClass="liLeft"/>
				<@ww.password label="Confirmar Senha" name="confirmaSenha" id="comfirmaSenha" cssStyle="width: 100px;" />
			</#if>
			
      </div>

      <div id="content3" style="display: none;">
        <@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" list="cargosCheckList" />
        <@frt.checkListBox label="Áreas de Interesse" name="areasCheck" list="areasCheckList" onClick="populaConhecimento(document.forms[0],'areasCheck');"/>
        <@frt.checkListBox label="Conhecimentos" name="conhecimentosCheck" list="conhecimentosCheckList" />
        <@ww.select label="Colocação" name="candidato.colocacao" list="colocacaoList" liClass="liLeft"/>
        <@ww.textfield label="Pretensão Salarial" name="candidato.pretencaoSalarial"  onkeypress = "return(somenteNumeros(event,','));" cssStyle="width:85px; text-align:right;" maxLength="12" />
      </div>

	  <div id="content5" style="display: none;">
		<b><@ww.label label="Identidade" /></b>
    	<@ww.textfield label="Número" name="candidato.pessoal.rg" id="rg" cssStyle="width: 106px;" maxLength="15" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));" />
  	   	<@ww.textfield label="Órgão Emissor" name="candidato.pessoal.rgOrgaoEmissor" cssStyle="width: 73px;" maxLength="10" liClass="liLeft" />
       	<@ww.select label="Estado" name="candidato.pessoal.rgUf.id" id="rgUf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
      	<@ww.datepicker label="Data de Expedição" name="candidato.pessoal.rgDataExpedicao" id="rgDataExpedicao" cssClass="mascaraData" value="${rgDataExpedicao}"/>
        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
       	<b><@ww.label label="Carteira de Habilitação" /></b>
		<@ww.textfield label="Número" name="candidato.habilitacao.numeroHab" cssStyle="width: 80px;" maxLength="11" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
      	<@ww.textfield label="Registro" name="candidato.habilitacao.registro"  cssStyle="width: 120px;" maxLength="15" liClass="liLeft"/>
      	<@ww.datepicker label="Emissão" name="candidato.habilitacao.emissao" id="emissao" liClass="liLeft" cssClass="mascaraData validaDataIni" value="${habEmissao}"/>
      	<@ww.datepicker label="Vencimento" name="candidato.habilitacao.vencimento" id="vencimento" liClass="liLeft" cssClass="mascaraData validaDataFim" value="${dataVenc}"/>
       	<@ww.textfield label="Categoria(s)" name="candidato.habilitacao.categoria" cssStyle="width:25px" maxLength="3" />
        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
		<b><@ww.label label="Título Eleitoral" /></b>
    	<@ww.textfield label="Número" name="candidato.pessoal.tituloEleitoral.titEleitNumero" id="titEleitNumero" cssStyle="width: 95px;" maxLength="13" liClass="liLeft"/>
    	<@ww.textfield label="Zona" name="candidato.pessoal.tituloEleitoral.titEleitZona" id="titEleitZona" cssStyle="width: 95px;" maxLength="13" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
    	<@ww.textfield label="Seção" name="candidato.pessoal.tituloEleitoral.titEleitSecao" id="titEleitSecao" cssStyle="width: 95px;" maxLength="13" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
		<b><@ww.label label="Certificado Militar" /></b>
    	<@ww.textfield label="Número" name="candidato.pessoal.certificadoMilitar.certMilNumero" id="certMilNumero" cssStyle="width: 88px;" maxLength="12" liClass="liLeft" onkeypress = "return(somenteNumeros(event,'{,}'));"/>
    	<@ww.textfield label="Tipo" name="candidato.pessoal.certificadoMilitar.certMilTipo" id="certMilTipo" cssStyle="width: 38px;" maxLength="5" liClass="liLeft"/>
    	<@ww.textfield label="Série" name="candidato.pessoal.certificadoMilitar.certMilSerie" id="certMilSerie" cssStyle="width: 88px;" maxLength="12"/>
        <li><hr style="border-top: 1px solid #CCCCCC; border-bottom:0;"/></li>
		<b><@ww.label label="CTPS - Carteira de Trabalho e Previdência Social" /></b>
    	<@ww.textfield label="Número" name="candidato.pessoal.ctps.ctpsNumero" id="ctpsNumero" cssStyle="width: 58px;" maxLength="8" liClass="liLeft"/>
    	<@ww.textfield label="Série" name="candidato.pessoal.ctps.ctpsSerie" id="ctpsSerie" cssStyle="width: 38px;" maxLength="6" liClass="liLeft"/>
    	<@ww.textfield label="DV" name="candidato.pessoal.ctps.ctpsDv" id="ctpsDv" cssStyle="width: 9px;" maxLength="1" liClass="liLeft"/>
       	<@ww.select label="Estado" name="candidato.pessoal.ctps.ctpsUf.id" id="ctpsUf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
      	<@ww.datepicker label="Data de Expedição" name="candidato.pessoal.ctps.ctpsDataExpedicao" id="ctpsDataExpedicao" cssClass="mascaraData" value="${ctpsDataExpedicao}"/>
      </div>

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

	  <@ww.token/>
    </@ww.form>

    <#-- Campo para controle das abas -->
    <@ww.hidden id="aba" name="aba" value="1"/>

    <br>

    <div class="buttonGroup" style="float:left; width:49%;">
       
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
    <div class="buttonGroup" style="width:50%; float:right; text-align:right;">
        <button class="btnVoltarDesabilitado" id='voltar' disabled="disabled" onclick="abas(-1, 'V',${edicao},${qtdAbas});" accesskey="V">
        </button>
        <button id='avancar' onclick="abas(-1, 'A',${edicao},${qtdAbas});" class="btnAvancar" accesskey="A">
        </button>
    </div>
    <br clear="all">



    <script>
      document.getElementById('desCursos').value = document.getElementById('candidato.cursos').value;
      document.getElementById('obs').value = document.getElementById('candidato.observacao').value;

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

      if(document.getElementById('pagaPensaoId').value == "true")
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
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js"/>'></script>
</body>
</html>