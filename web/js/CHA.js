function createEventClickAddCriterio(competencia) {
	$(".inputCriterioDescricao").unbind();
	$(".inputCriterioDescricao").keypress(function(event){
		if ( event.which == 13 ) {
			addCriterio(competencia);
		}
	});
}

function reorganizeListaDeCriterios(competencia, addNew) {
	$("#criterios li").each(function(i){
		if( addNew && $(this).find(".inputCriterioDescricao").attr("name") == "" )
			$(this).find(".inputCriterioDescricao").focus();
			
		$(this).find(".inputCriterioId").attr("name", competencia + ".criteriosAvaliacaoCompetencia["+i+"].id");
		$(this).find(".inputCriterioDescricao").attr("name", competencia + ".criteriosAvaliacaoCompetencia["+i+"].descricao");
	});
	
}

function delCriterio(competencia, item)
{
	$(item).parent().parent().remove();
	reorganizeListaDeCriterios(competencia);
}

function validaAddCriterio(empresaId, competencia)
{
	if($('.inputCriterioDescricao').size() == 0)
	{
		NivelCompetenciaDWR.existeNivelCompetenciaSemPercentual(empresaId, function(data){
			if(data)			
				jAlert("Não é possível adicionar critérios para essa competência, pois existem níveis de competência sem percentual mínimo configurado.");
			else
				addCriterio(competencia);
		});	
	}else{
		addCriterio(competencia);
	}
}	
	
function addCriterio(competencia, criterioDescricao, criterioId)
{	
	criterioId = criterioId != undefined ? criterioId : "";
	criterioDescricao = criterioDescricao != undefined ? criterioDescricao : "";
	
	var criterio = '<li style="margin: 2px 0;"><span>';
	criterio += '<img title="Remover critério" onclick="delCriterio(\''+ competencia +'\', this)" src="/fortesrh/imgs/remove.png" border="0" align="absMiddle" style="cursor:pointer;" />&nbsp;';
	criterio += '<input type="hidden" value="' + criterioId + '" class="inputCriterioId" >';
	criterio += '<input type="text" maxlength="100" value="' + criterioDescricao + '" class="inputCriterioDescricao" style="width:468px;">';
	criterio += '</span></li>';

	$('#criterios').append(criterio);
	
	reorganizeListaDeCriterios(competencia, !criterioId);
	createEventClickAddCriterio(competencia);
}