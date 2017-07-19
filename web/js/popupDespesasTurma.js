function limpaDespesas()
{
	 $('#totalCustos').text('0,00');
	 $('.despesa').val('');
}

function abrirPopupDespesas() 
{
	var camposOcultos = '';
	$('#formDialog').dialog({ 	modal: true, 
								width: 500,
								height: 360,
								buttons: 
								[
								    {
								        text: "Gravar",
								        click: function() { 
								        	var despesas = new Array();

											$('.despesa').each(function(){
											    if (this.value && moeda2float(this.value) > 0)
											        despesas.push({tipoDespesaId:this.name, despesa:moeda2float(this.value)});
											});
											
											var despesasJSON = JSON.stringify(despesas);
											var despesasTotal = somaDespesas();
											var turmaId = $('#turmaId').val();
											
											if (turmaId != '')
												TurmaDWR.saveDespesas(despesasJSON, turmaId, despesasTotal);
											
											$('#custos').val(despesasJSON);
											$('#custo').val(float2moeda(despesasTotal));
											
								        	$(this).dialog("close"); 
								        }
								    },
								    {
								        text: "Limpar",
								        click: function() { limpaDespesas(); }
								    }
								] ,
								open: function(event, ui) 
								{ 
									var turmaId = $('#turmaId').val();
									if (turmaId != '')
									{
										TurmaDWR.getDespesas(turmaId, function(turmaTipoDespesas) 
										{
											$(turmaTipoDespesas).each(function(i, turmaTipoDespesa) {
												$("#tipoDespesa :input[name='" + turmaTipoDespesa.tipoDespesa.id + "']").val( float2moeda(turmaTipoDespesa.despesa) );
											});
											
											$('#totalCustos').text(float2moeda(somaDespesas()));
										});
									
									} else 
									{
										var custos = $('#custos').val();
										
										if (custos != '') 
										{
											custos = JSON.parse(custos);

											$(custos).each(function() {
												$("#tipoDespesa :input[name='" + this.tipoDespesaId + "']").val( float2moeda(this.despesa) );
											});
											
											$('#totalCustos').text(float2moeda(somaDespesas()));
										}
									}
								},
								close: function(event, ui)
								{
									limpaDespesas();
								}
							});
}

function somaDespesas() 
{
	var total = 0;

	$('.despesa').each(function (i, item) {
	    var valor = $(item).val();
	    if (valor && valor != '')
	        total += moeda2float(valor);
	});
	
	return total;
}
