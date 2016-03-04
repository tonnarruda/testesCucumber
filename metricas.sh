#! /bin/bash 

COUNT_CLASS=0
count_class_by_read=0
qtd_methods_bigs=0
qtd_methods=0
qtd_linhas=0
metodos_grandes=""

dbname="sonar"
username="postgres"

body_email="Algumas métricas aumentaram após o commit $1 <br><br>"

for arq in $( locate src/*.java ) ; do   
    i=1
    init_class=0
    init_line_class=0
    init_method=0
    count_lines_by_method=0
    qtd_methods_class=0
    qtd_methods_bigs_class=0
    lines_with_key=0
    last_line=""
    metodo=""
    
    while read line 
	do 
	   if [[ $line == *" class "* || $line == *" interface "* || $line == *" abstract "* ]]
		then
		  if [[ $line == *"{"* ]]
		  then
		  	init_class=1
		  	count_class_by_read=$(($count_class_by_read+1))
		  else
		  	init_line_class=1
		  fi
		else
			if [[ $init_line_class -eq 1 && $init_class -eq 0 ]]
			then
				init_class=1
		  		count_class_by_read=$(($count_class_by_read+1))
			else
				if [[ $init_class -eq 1 && $init_method -eq 0 && $line == *"{"* && $line != *"}"* ]]
				then
				  init_method=1
				  
				  line_compact=${line//[[:blank:]]/}
				  if [[ ${#line_compact} -ge 3 && $line == *"("* ]]
				  then
				  	metodo=${line%(*}
				  	metodo=${metodo##* }
				  #else
				  #	metodo=${last_line%(*}
				  #	metodo=${metodo##* }
				  fi 
				else
					if [[ $init_class -eq 1 && $init_method -eq 1 && $line == *"{"* ]]
					then
						#if [[ $line != *"}"* ]]
						#then
						#	lines_with_key=$(($lines_with_key+1))
						#else
							
						#fi
						
						keys="${line//[^\{^\}]}"
						keyOpen="${line//[^\{]}"
						keyClose="${line//[^\}]}"
						
						if [[ ${#keyOpen} -ge ${#keyClose} ]]
						then
							lines_with_key=$(($lines_with_key + (${#keyOpen}-${#keyClose}) ))
						else
							lines_with_key=$(($lines_with_key - (${#keyClose}-${#keyOpen}) ))
						fi
						
						if [[ ${#line} -ge 1 && $line != "//"* ]]
						then
							count_lines_by_method=$(($count_lines_by_method+1))
						fi
					else
						if [[ $init_class -eq 1 && $init_method -eq 1 && $lines_with_key -ge 1 && $line == *"}"* ]]
						then

							lines_with_key=$(($lines_with_key-1))
							
							if [[ ${#line} -ge 1 && $line != "//"* ]]
							then
								count_lines_by_method=$(($count_lines_by_method+1))
							fi
						else
							if [[ $init_class -eq 1 && $init_method -eq 1 && $lines_with_key -eq 0 && $line == *"}"* ]]
							then
							  init_method=0
							 
							  qtd_methods_class=$(($qtd_methods_class+1))
							  qtd_methods=$(($qtd_methods+1))
							  if [[ $count_lines_by_method -ge 30 && ${#metodo} -ge 1 ]]
							  then
							  
							    count_para_saber_se_ja_existe=$({
							    psql -qtAU $username $dbname << EOF
							    select count (*) FROM big_methods WHERE method_name = '$metodo' and class = '$arq';
EOF
							    })
								
								if [[ "$count_para_saber_se_ja_existe" -gt 0 ]]
								then
									psql -qtAU $username $dbname << EOF
										UPDATE big_methods SET qtd_linhas = '$count_lines_by_method' WHERE method_name = '$metodo' and class = '$arq';
EOF
								else
									psql -qtAU $username $dbname << EOF
										INSERT INTO big_methods(qtd_linhas, method_name, class) VALUES('$count_lines_by_method', '$metodo', '$arq');
EOF
									
									nome_arquivo=${arq##*/}
							  		metodos_grandes="$metodos_grandes$metodo da classe $nome_arquivo aumentou para $count_lines_by_method<br>"
								fi
							  
							  	qtd_methods_bigs=$(($qtd_methods_bigs+1))
							  	qtd_methods_bigs_class=$(($qtd_methods_bigs_class+1))
							  else
							  	psql -qtAU $username $dbname << EOF
									delete FROM big_methods WHERE method_name = '$metodo' and class = '$arq';
EOF
							  fi
							  
							  count_lines_by_method=0
							  metodo=""
							else
								if [[ $init_class -eq 1 && $init_method -eq 1 && $line != *"{"* && $line != *"}"*  ]]
								then
									if [[ ${#line} -ge 2 && $line != "//"* ]]
									then

										count_lines_by_method=$(($count_lines_by_method+1))
									fi
								else
									if [[ $init_class -eq 1 && $init_method -eq 0 ]]
									then
									
									  if [[ $line == *"}"* && $line != "@"*  ]]
									  then
										  if [[ ${#line} -ge 1 && $line != "//"* ]]
										  then
											count_lines_by_method=$(($count_lines_by_method+1))
										  fi
										  init_class=0
									  else
									      last_line=$line

									      if [[ $line == *"("* ]]
										  then
										  	metodo=${line%(*}
										  	metodo=${metodo##* }
										  fi 
									  fi
									  
									fi
								fi
							fi
						fi
					fi
				fi
			fi
		fi

	   i=$(($i+1))
	done < $arq
	
	qtd_linhas=$(($qtd_linhas+$i))
	COUNT_CLASS=$(($COUNT_CLASS+1))
done

ultima_porcentagem_cobertura=$({
psql -qtAU $username $dbname << EOF
	SELECT val FROM ultimas_metricas WHERE 'metrica' = 'coverage' and data = (select max(data) from ultimas_metricas WHERE 'metrica' = 'coverage');
EOF
})

atual_porcentagem_cobertura=$({
psql -qtAU $username $dbname << EOF
	select val from (
		select distinct proj.name NAME_OF_PROJ, metric.name metric_name, metric.description Description, projdesc.value val,
		projdesc.variation_value_1, projdesc.variation_value_2, projdesc.variation_value_3, projdesc.variation_value_4, projdesc.variation_value_5, snap.created_at CREATED_DATE
		from projects proj
		inner join snapshots snap on snap.project_id=proj.id
		inner join (select max(snap2.id) as id from snapshots snap2 GROUP BY snap2.project_id ) as Lookup on Lookup.id=snap.id
		inner join project_measures projdesc on projdesc.snapshot_id=snap.id
		inner join metrics metric on  projdesc.metric_id =metric.id
		where 
		--metric.id in ( 1, 2, 3, 4 , 5, 6, 7 ,8,9,10,22 ) and
		metric.name = 'coverage' and
		proj.name = 'RH' and
		proj.scope = 'PRJ' ORDER BY proj.name
	) as m;
EOF
})

ultima_porcentagem_cod_dupicado=$({
psql -qtAU $username $dbname << EOF
	SELECT val FROM ultimas_metricas WHERE 'metrica' = 'cod_duplicado' and data = (select max(data) from ultimas_metricas WHERE 'metrica' = 'cod_duplicado');
EOF
})

atual_porcentagem_cod_dupicado=$({
psql -qtAU $username $dbname << EOF	
	select val from (
		select distinct proj.name NAME_OF_PROJ, metric.name metric_name, metric.description Description, projdesc.value val,
		projdesc.variation_value_1, projdesc.variation_value_2, projdesc.variation_value_3, projdesc.variation_value_4, projdesc.variation_value_5, snap.created_at CREATED_DATE
		from projects proj
		inner join snapshots snap on snap.project_id=proj.id
		inner join (select max(snap2.id) as id from snapshots snap2 GROUP BY snap2.project_id ) as Lookup on Lookup.id=snap.id
		inner join project_measures projdesc on projdesc.snapshot_id=snap.id
		inner join metrics metric on  projdesc.metric_id =metric.id
		where 
		--metric.id in ( 1, 2, 3, 4 , 5, 6, 7 ,8,9,10,22 ) and
		metric.name = 'duplicated_lines_density' and
		proj.name = 'RH' and
		proj.scope = 'PRJ' ORDER BY proj.name
	) as m;
EOF
})

#echo "Total de métodos grandes: $qtd_methods_bigs"
echo "$bodyemail $metodos_grandes"
#echo "Ultima porcentagem de cobertura: $ultima_porcentagem_cobertura"
#echo "Porcentagem atual de cobertura: $atual_porcentagem_cobertura"
#echo "Ultima porcentagem de cod. duplicado: $ultima_porcentagem_cod_duplicado"
#echo "Porcentagem atual de cod. duplicado: $atual_porcentagem_cod_duplicado"

now=$(date +"%T")
#echo "Current time : $now"

#echo $metodos_grandes >> metodos.csv
#echo "Total de arquivos: $COUNT_CLASS"
#echo "Total de classes: $count_class_by_read"
