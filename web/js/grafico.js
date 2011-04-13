function montaPie(data, clazz, radius, radiusLabel, percentMin, pieLeft, noColumns) {
	$.plot($(clazz), data, {
		series : {
			pie : {
				show : true,
				offset : {
					top : 0,
					left : pieLeft
				},
				stroke : {
					color : '#FFF',
					width : 1
				},
				tilt : 0.8,
				radius : radius,
				combine : {
					color : '#999',
					threshold : 0,
					label : 'Outros'
				},
				label : {
					show : true,
					radius : radiusLabel,
					threshold : percentMin,
					formatter : function(label, series) {
						return '<div class="label">' + series.percent.toFixed(2) + '%</div>';
					}
				}
			}
		},
		grid : {
		//hoverable: true
		//clickable: true
		},
		colors : [ "#edc240", "#afd8f8", "#cb4b4b", "#4da74d", "#9440ed" ],//"#D7ECFC","#DECD99","#226FA5","#8080FF"
		legend : {
			margin : 2,
			noColumns: noColumns,
			labelBoxBorderColor : '#FFF',
			container: '#salarioAreasLegenda',
			labelFormatter : function(label, series) {
				return '<span class="legend">' + label + ' &#x2013; '
						+ series.percent.toFixed(2) + '% ('
						+ series.datapoints.points[1] + ')</span>';
			}
		}
	});

	//$(class).bind("plothover", pieHover);
}