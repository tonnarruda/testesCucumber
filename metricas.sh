#! /bin/bash 

COUNT_CLASS=0
count_class_by_read=0
qtd_methods_bigs=0
qtd_methods=0
qtd_linhas=0
metodos_grandes_src=''
metodos_grandes_test=''

dbname="sonar"
username="postgres"

comandos=""

body_email="Algumas métricas aumentaram após o commit $1 <br><br>"

for arq in $( locate $(pwd)/src/*.java ) ; do   
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
    methods_class=()
    
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
							  
							  qtd_this_method_in_class=0
							  for word in ${methods_class[*]}; do
								if [[ $word = $metodo ]]; then
									(( qtd_this_method_in_class++ ))
								fi
							  done 
							  
							  methods_class+=($metodo)
							 
							  qtd_methods_class=$(($qtd_methods_class+1))
							  qtd_methods=$(($qtd_methods+1))
							  if [[ $count_lines_by_method -ge 30 && ${#metodo} -ge 1 ]]
							  then
							  
							    count_para_saber_se_ja_existe=$({
							    psql -qtAU $username $dbname << EOF
							    select count (*) FROM big_methods WHERE method_name = '$metodo' and class = '$arq' and project = 'RH' and position_class=($qtd_this_method_in_class+1);
EOF
							    })
								
								if [[ "$count_para_saber_se_ja_existe" -gt 0 ]]
								then
									comandos="$comandos UPDATE big_methods SET qtd_linhas = '$count_lines_by_method' WHERE method_name = '$metodo' and class = '$arq' and project = 'RH' and position_class=($qtd_this_method_in_class+1);"
								else
									comandos="$comandos INSERT INTO big_methods(qtd_linhas, method_name, class, project, position_class) VALUES('$count_lines_by_method', '$metodo', '$arq', 'RH',  ($qtd_this_method_in_class+1));"
									
									nome_arquivo=${arq##*/}
							  		metodos_grandes_src="$metodos_grandes_src"'  Método '"$metodo"' da classe "'$nome_arquivo'" está com '$count_lines_by_method$' linhas.\n'
								fi
							  
							  	qtd_methods_bigs=$(($qtd_methods_bigs+1))
							  	qtd_methods_bigs_class=$(($qtd_methods_bigs_class+1))
							  else
							  	if  [[ ${#metodo} -ge 1 ]]
								then
							  		comandos="$comandos delete FROM big_methods WHERE method_name = '$metodo' and class = '$arq' and project = 'RH' and position_class=($qtd_this_method_in_class+1);"
							  	fi
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



for arq in $( locate $(pwd)/test/*.java ) ; do   
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
    methods_class=()
    
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
							  
							  qtd_this_method_in_class=0
							  for word in ${methods_class[*]}; do
								if [[ $word = $metodo ]]; then
									(( qtd_this_method_in_class++ ))
								fi
							  done 
							  
							  methods_class+=($metodo)
							 
							  qtd_methods_class=$(($qtd_methods_class+1))
							  qtd_methods=$(($qtd_methods+1))
							  if [[ $count_lines_by_method -ge 30 && ${#metodo} -ge 1 ]]
							  then
							  
							    count_para_saber_se_ja_existe=$({
							    psql -qtAU $username $dbname << EOF
							    select count (*) FROM big_methods WHERE method_name = '$metodo' and class = '$arq' and project = 'RHTest' and position_class=($qtd_this_method_in_class+1);
EOF
							    })
								
								if [[ "$count_para_saber_se_ja_existe" -gt 0 ]]
								then
									comandos="$comandos UPDATE big_methods SET qtd_linhas = '$count_lines_by_method' WHERE method_name = '$metodo' and class = '$arq' and project = 'RHTest' and position_class=($qtd_this_method_in_class+1);"
								else
									comandos="$comandos INSERT INTO big_methods(qtd_linhas, method_name, class, project, position_class) VALUES('$count_lines_by_method', '$metodo', '$arq', 'RHTest', ($qtd_this_method_in_class+1));"
									
									nome_arquivo=${arq##*/}
							  		metodos_grandes_test="$metodos_grandes_test"'  Método '"$metodo"' da classe "'$nome_arquivo'" está com '$count_lines_by_method$' linhas.\n'
								fi
							  
							  	qtd_methods_bigs=$(($qtd_methods_bigs+1))
							  	qtd_methods_bigs_class=$(($qtd_methods_bigs_class+1))
							  else
							  	comandos="$comandos delete FROM big_methods WHERE method_name = '$metodo' and class = '$arq' and project = 'RHTest' and position_class=($qtd_this_method_in_class+1);"
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

#echo "$comandos"

#echo "Executando comandos..."

psql -qtAU $username $dbname << EOF
	$comandos
EOF

ultima_porcentagem_cobertura_src=$({
psql -qtAU $username $dbname << EOF
	SELECT val FROM ultimas_metricas WHERE 'metrica' = 'coverage' and data = (select max(data) from ultimas_metricas WHERE 'metrica' = 'coverage' and project = 'RH') and project = 'RH' limit 1;
EOF
})

atual_porcentagem_cobertura_src=$({
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

ultima_porcentagem_cobertura_test=$({
psql -qtAU $username $dbname << EOF
	SELECT val FROM ultimas_metricas WHERE 'metrica' = 'coverage' and data = (select max(data) from ultimas_metricas WHERE 'metrica' = 'coverage' and project = 'RHTest') and project = 'RHTest' limit 1;
EOF
})

atual_porcentagem_cobertura_test=$({
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
		proj.name = 'RHTest' and
		proj.scope = 'PRJ' ORDER BY proj.name
	) as m;
EOF
})

ultima_porcentagem_cod_dupicado_src=$({
psql -qtAU $username $dbname << EOF
	SELECT val FROM ultimas_metricas WHERE metrica = 'cod_duplicado' and data = (select max(data) from ultimas_metricas WHERE metrica = 'cod_duplicado' and project = 'RH') and project = 'RH' limit 1;
EOF
})

atual_porcentagem_cod_duplicado_src=$({
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

ultima_porcentagem_cod_dupicado_test=$({
psql -qtAU $username $dbname << EOF
	SELECT val FROM ultimas_metricas WHERE metrica = 'cod_duplicado' and data = (select max(data) from ultimas_metricas WHERE metrica = 'cod_duplicado' and project = 'RHTest') and project = 'RHTest' limit 1;
EOF
})

atual_porcentagem_cod_duplicado_test=$({
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
		proj.name = 'RHTest' and
		proj.scope = 'PRJ' ORDER BY proj.name
	) as m;
EOF
})



if [[ ${#metodos_grandes_src} -ge 1 ]] 
then
	echo $'\n - Surgiram novos métodos grandes no projeto principal: \n'"$metodos_grandes_src"$'\n'
fi

if [[ ${#metodos_grandes_test} -ge 1 ]] 
then
	echo $'\n - Surgiram novos métodos grandes no projeto de testes: \n'"$metodos_grandes_test"$'\n'
fi

atual_porcentagem_cod_duplicado_src=$(awk 'BEGIN{print '$atual_porcentagem_cod_duplicado_src'}')
compare_cod_duplicado_src=$(awk 'BEGIN{ print '$ultima_porcentagem_cod_dupicado_src' >= '$atual_porcentagem_cod_duplicado_src' }')
if [[ "$compare_cod_duplicado_src" -ne 1 ]]
then
	echo $'\n\n - Porcentagem de cód. duplicado aumentou de '$ultima_porcentagem_cod_dupicado_src' para '$atual_porcentagem_cod_duplicado_src' no projeto principal.'
fi

psql -qtAU $username $dbname << EOF
	INSERT INTO ultimas_metricas (data, metrica, val, project) VALUES (current_timestamp, 'cod_duplicado', '$atual_porcentagem_cod_duplicado_src', 'RH');
EOF

atual_porcentagem_cod_duplicado_test=$(awk 'BEGIN{print '$atual_porcentagem_cod_duplicado_test'}')
compare_cod_duplicado_test=$(awk 'BEGIN{ print '$ultima_porcentagem_cod_dupicado_test' >= '$atual_porcentagem_cod_duplicado_test' }')
if [[ "$compare_cod_duplicado_test" -ne 1  ]]
then
	echo $'\n\n - Porcentagem de cód. duplicado aumentou de '$ultima_porcentagem_cod_dupicado_test' para '$atual_porcentagem_cod_duplicado_test' no projeto de testes.'
fi

psql -qtAU $username $dbname << EOF
	INSERT INTO ultimas_metricas (data, metrica, val, project) VALUES (current_timestamp, 'cod_duplicado', '$atual_porcentagem_cod_duplicado_test', 'RHTest');
EOF

#######

atual_porcentagem_cobertura_src=$(awk 'BEGIN{print '$atual_porcentagem_cobertura_src'}')
compare_cod_duplicado_src=$(awk 'BEGIN{ print "'$ultima_porcentagem_cobertura_src'"<="'$atual_porcentagem_cobertura_src'" }')
if [[ "$compare_cod_duplicado_src" -ne 1 ]]
then
	echo $'\n\n - Porcentagem de cód. duplicado aumentou de '$ultima_porcentagem_cobertura_src' para '$atual_porcentagem_cobertura_src' no projeto principal.'
fi

psql -qtAU $username $dbname << EOF
	INSERT INTO ultimas_metricas (data, metrica, val, project) VALUES (current_timestamp, 'coverage', '$atual_porcentagem_cobertura_src', 'RH');
EOF

atual_porcentagem_cobertura_test=$(awk 'BEGIN{print '$atual_porcentagem_cobertura_test'}')
compare_cod_duplicado_test=$(awk 'BEGIN{ print "'$ultima_porcentagem_cobertura_test'"<="'$atual_porcentagem_cobertura_test'" }')
if [[ "$compare_cod_duplicado_test" -ne 1  ]]
then
	echo $'\n\n - Porcentagem da cobertura diminuiu de '$ultima_porcentagem_cobertura_test' para '$atual_porcentagem_cobertura_test' no projeto de testes.'
fi

psql -qtAU $username $dbname << EOF
	INSERT INTO ultimas_metricas (data, metrica, val, project) VALUES (current_timestamp, 'coverage', '$atual_porcentagem_cobertura_test', 'RHTest');
EOF
