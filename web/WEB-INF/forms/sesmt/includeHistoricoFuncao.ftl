			<@ww.datepicker label="A partir de" name="historicoFuncao.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
			<@ww.textarea label="Descrição das Atividades Executadas pela Função" name="historicoFuncao.descricao" id="descricao" cssClass="inputNome"  required="true"/>
			<@frt.checkListBox label="Exames Obrigatórios (SESMT)" name="examesChecked" id="exame" list="examesCheckList" filtro="true"/>
			<@frt.checkListBox label="EPIs (PPRA)" name="episChecked" id="epi" list="episCheckList" filtro="true"/>
			
			<#assign i = 0/>
			Riscos existentes:<br>
			<@display.table name="riscosFuncoes" id="riscoFuncao" class="dados" style="width:100%;border:none;">
				<@display.column title="<input type='checkbox' id='md'/>" style="width: 30px; text-align: center;">
					<input type="checkbox" id="check${riscoFuncao.risco.id}" value="${riscoFuncao.risco.id}" name="riscoChecks" />
				</@display.column>
				<@display.column property="risco.descricao" title="Risco"/>
				<@display.column property="risco.descricaoGrupoRisco" title="Tipo" style="width: 240px;"/>
				<@display.column title="EPI Eficaz" style="width: 60px;text-align:center;">
					<#if riscoFuncao.risco.epiEficaz == true> 
						Sim
					<#else>
						NA 
					</#if>
				</@display.column>
				<@display.column title="Periodicidade" style="text-align:center;">
					<@ww.select name="riscosFuncoes[${i}].periodicidadeExposicao" id="perExposicao${riscoFuncao.risco.id}" headerKey="" headerValue="Selecione" list=r"#{'C':'Contínua','I':'Intermitente','E':'Eventual'}" disabled="true"/>
				</@display.column>
				<@display.column title="EPC Eficaz" style="width: 60px;text-align:center;">
					<@ww.checkbox id="epcEficaz${riscoFuncao.risco.id}" name="riscosFuncoes[${i}].epcEficaz" disabled="true"/>
					<@ww.hidden name="riscosFuncoes[${i}].risco.id"/>
				</@display.column>
				<@display.column title="Medida de Segurança">
					<@ww.textarea theme="simple" id="medidaDeSeguranca${riscoFuncao.risco.id}" name="riscosFuncoes[${i}].medidaDeSeguranca" cssStyle="width: 260px;height: 50px;" disabled="true"/>
				</@display.column>
		
		
		
				
				<#assign i = i + 1/>
			</@display.table>
