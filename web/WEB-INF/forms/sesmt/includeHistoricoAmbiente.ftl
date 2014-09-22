		<@ww.datepicker label="A partir de" name="historicoAmbiente.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.textarea label="Descrição do Ambiente" name="historicoAmbiente.descricao" id="descricao" required="true"/>
		
		<@ww.textfield label="Tempo de Exposição" name="historicoAmbiente.tempoExposicao" id="tempoExposicao" maxLength="40" style="width:100px;"/>
	
		<@frt.checkListBox label="EPCs existentes no Ambiente" name="epcCheck" list="epcCheckList" filtro="true"/>
		
		<#assign i = 0/>
		<#if empresaControlaRiscoPor == 'A'> 
			Riscos existentes:<br>
			<@display.table name="riscosAmbientes" id="riscoAmbiente" class="dados" style="width:500px;border:none;">
				<@display.column title="<input type='checkbox' id='md'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" id="check${riscoAmbiente.risco.id}" value="${riscoAmbiente.risco.id}" name="riscoChecks" />
				</@display.column>
				<@display.column property="risco.descricao" title="Risco" style="width: 240px;"/>
				<@display.column property="risco.descricaoGrupoRisco" title="Tipo" style="width: 240px;"/>
				<@display.column title="EPI Eficaz" style="width: 140px;text-align:center;">
					<#if riscoAmbiente.risco.epiEficaz == true> 
						Sim
					<#else>
						NA 
					</#if>
				</@display.column>
				<@display.column title="Periodicidade" style="text-align:center;">
					<@ww.select name="riscosAmbientes[${i}].periodicidadeExposicao" id="perExposicao${riscoAmbiente.risco.id}" headerKey="" headerValue="Selecione" list=r"#{'C':'Contínua','I':'Intermitente','E':'Eventual'}" disabled="true"/>
				</@display.column>
				<@display.column title="EPC Eficaz" style="width: 140px;text-align:center;">
					<@ww.checkbox id="epcEficaz${riscoAmbiente.risco.id}" name="riscosAmbientes[${i}].epcEficaz" disabled="true"/>
					<@ww.hidden name="riscosAmbientes[${i}].risco.id"/>
				</@display.column>
				<@display.column title="Medida de Segurança">
					<@ww.textarea theme="simple" id="medidaDeSeguranca${riscoAmbiente.risco.id}" name="riscosAmbientes[${i}].medidaDeSeguranca" cssStyle="width: 260px;height: 50px;" disabled="true"/>
				</@display.column>
				
				<#assign i = i + 1/>
			</@display.table>
		<#else>
			<#list riscosAmbientes as riscoAmbiente>
				<@ww.hidden name="riscosAmbientes[${i}].periodicidadeExposicao" />
				<@ww.hidden name="riscosAmbientes[${i}].epcEficaz" />
				<@ww.hidden name="riscosAmbientes[${i}].medidaDeSeguranca" />
				<@ww.hidden name="riscosAmbientes[${i}].risco.id" />
				<#assign i = i + 1/>
			</#list> 
		</#if>
