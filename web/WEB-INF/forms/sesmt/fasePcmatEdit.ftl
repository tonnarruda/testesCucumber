<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<html>
	<head>
		<@ww.head/>
		<#if fasePcmat.id?exists>
			<title>Editar Fase</title>
			<#assign formAction="update.action"/>
			<#assign mesIni = fasePcmat.mesIni/>
			<#assign mesFim = fasePcmat.mesFim/>
		<#else>
			<title>Inserir Fase</title>
			<#assign formAction="insert.action"/>
			<#assign mesIni = ""/>
			<#assign mesFim = ""/>
		</#if>
	
		<#assign validarCampos="return validaFormulario('form', new Array('fase','mesIni','mesFim'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="fasePcmat.id" />
			<@ww.hidden name="fasePcmat.pcmat.id" />
			<@ww.hidden name="ultimoPcmatId" />
			<@ww.token/>

			<@ww.select label="Fase" name="fasePcmat.fase.id" id="fase" list="fases" listKey="id" listValue="descricao" headerValue="" headerKey="-1" required="true" cssStyle="width:500px"/>
			<li id="wwgrp_mesIni" class="wwgrp">	
				<div id="wwlbl_mesIni" class="wwlbl">
					<label for="mesIni" class="desc"> Cronograma:<span class="req">* </span></label>
				</div> 
				<div id="wwctrl_mesIni" class="wwctrl">
					<input id="mesIni" type="text" style="width:30px;text-align:right;" value="${mesIni}" maxlength="3" name="fasePcmat.mesIni" onkeypress="return somenteNumeros(event,',');"/>
					&ordm; ao 
					<input id="mesFim" type="text" style="width:30px;text-align:right;" value="${mesFim}" maxlength="3" name="fasePcmat.mesFim" onkeypress="return somenteNumeros(event,',');"/>
					&ordm; mês
				</div> 
			</li>
			<@ww.textarea label="Descrição" name="fasePcmat.descricao" cssStyle="width:500px"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if ultimoPcmatId == fasePcmat.pcmat.id>
				<button onclick="${validarCampos};" class="btnGravar"></button>
			</#if>
			<button onclick="javascript: executeLink('list.action?pcmat.id=${fasePcmat.pcmat.id}&ultimoPcmatId=${ultimoPcmatId}');" class="btnVoltar"></button>
		</div>
	</body>
</html>
