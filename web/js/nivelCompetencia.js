function toolTipCompetenciaObs(id, obs)
{
	id = "#" + "competencia_" + id + "_obs";
	$(id).qtip({
		content: '<div style="text-align:justify">' + obs.split('$#-').join("\"") + '</div>',
		style: { width: 250 }
	});
}