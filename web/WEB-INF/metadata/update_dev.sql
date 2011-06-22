update parametrosdosistema set appversao = '1.1.48.40';--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (510, 'ROLE_MOV_PALOGRAFICO', 'Exame Palográfico', '/captacao/candidato/prepareExamePalografico.action', 3, true, 359);--.go
alter sequence papel_sequence restart with 511;--.go

update papel set papelmae_id = 463 where id=510;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (511, 'ROLE_REL_COLAB_SEM_REAJUSTE', 'Colaboradores Sem Reajuste', '/cargosalario/historicoColaborador/prepareRelatorioUltimasPromocoes.action', 7, true, 364);--.go
alter sequence papel_sequence restart with 512;--.go

create view SituacaoColaborador as 
select  hc.id as historicoColaboradorId,
	greatest(hc.data,hfs_hc.data, hi_hfs_hc.data, hi_hc.data) as data,
	hc.tiposalario as tipo,
	COALESCE((hfs_hc.quantidade * hi_hfs_hc.valor), hfs_hc.valor, (hc.quantidadeindice * hi_hc.valor), hc.salario) as salario,
	c.id as cargo_id,
	hc.faixasalarial_id,
	hc.estabelecimento_id,
	hc.areaOrganizacional_id,
	hc.colaborador_id,
	hc.motivo
	from historicocolaborador hc
			left join faixasalarial fs_hc on fs_hc.id=hc.faixasalarial_id
			left join faixasalarialhistorico hfs_hc on hfs_hc.faixasalarial_id = fs_hc.id and hc.tiposalario=1
			left join cargo c on c.id = fs_hc.cargo_id
			
			left join indice i_hfs_hc on i_hfs_hc.id = hfs_hc.indice_id and hfs_hc.tipo=2
			left join indicehistorico hi_hfs_hc on i_hfs_hc.id = hi_hfs_hc.indice_id
				
			left join indice i_hc on i_hc.id = hc.indice_id and hc.tiposalario=2
			left join indicehistorico hi_hc on hi_hc.indice_id = i_hc.id

			--join com data dos proximos historicos dos colaboradores
			left join 
			(
				select hc2.data,hc2.colaborador_id as colabId,
				COALESCE((select min(data) from historicocolaborador hc3 where hc3.data > hc2.data and hc3.colaborador_id=hc2.colaborador_id), '01-01-2300') as dataProximo
				from historicocolaborador hc2
				where hc2.status <> 3 -- nao cacelados
				order by colabId, hc2.data 
			) as proximo on proximo.data = hc.data and proximo.colabId = hc.colaborador_id

			--join com data dos proximos historicos das faixas
			left join
			(
				select hfs2.data, hfs2.faixasalarial_id as faixaId,
				COALESCE((select min(data) from faixasalarialhistorico fsh3 where fsh3.data > hfs2.data and fsh3.faixasalarial_id = hfs2.faixasalarial_id), '01-01-2300') as dataProximoHistFaixa
				from faixasalarialhistorico hfs2
				where hfs2.status <> 3 -- nao cacelados 
				order by faixaId, hfs2.data
			) as proximaFaixa on proximaFaixa.data = hfs_hc.data and proximaFaixa.faixaId = hfs_hc.faixasalarial_id

			--join pegando a data atual da faixa pelo historico do colaborador
			left join
			(
				select hc3.id as histColabId,
				COALESCE((select max(data) from faixasalarialhistorico fsh where fsh.data <= hc3.data and fsh.faixasalarial_id = hc3.faixasalarial_id), '01-01-1900') as dataAtualFaixa
				from historicocolaborador as hc3 
				where hc3.tiposalario=1 and hc3.status <> 3 -- nao cacelados
				order by hc3.id
			) as faixaAtual on faixaAtual.histColabId = hc.id

			--join pegando a data atual do indice pelo historico da faixa
			left join
			(
				select hfs3.id as histFaixaId,
				COALESCE((select max(data) from indicehistorico hi where hi.data <= hfs3.data and hi.indice_id = hfs3.indice_id), '01-01-1900') as dataAtualIndiceFaixa
				from faixasalarialhistorico as hfs3
				where hfs3.tipo=2 and hfs3.status <> 3 -- nao cacelados
				order by hfs3.id
			) as indiceAtualFaixa on indiceAtualFaixa.histFaixaId = hfs_hc.id
			
			--join pegando a data atual do indice pelo historico do colaborador
			left join
			(
				select hc4.id as histColabId,
				COALESCE((select max(data) from indicehistorico hi where hi.data <= hc4.data and hi.indice_id = hc4.indice_id), '01-01-1900') as dataAtualIndice
				from historicocolaborador as hc4 
				where hc4.tiposalario=2 and hc4.status <> 3 -- nao cacelados
				order by hc4.id
			) as indiceAtual on indiceAtual.histColabId = hc.id
						
where hc.status <> 3 -- nao cacelados
and (hfs_hc.data < proximo.dataProximo or hfs_hc.data is null)
and (hi_hfs_hc.data < proximo.dataProximo or hi_hfs_hc.data is null)
and (hi_hfs_hc.data < proximaFaixa.dataProximoHistFaixa or hi_hfs_hc.data is null)
and (hfs_hc.data >= faixaAtual.dataAtualFaixa or hfs_hc.data is null)
and (hi_hfs_hc.data >= indiceAtualFaixa.dataAtualIndiceFaixa or hi_hfs_hc.data is null)
and (hi_hc.data < proximo.dataProximo or hi_hc.data is null)
and (hi_hc.data >= indiceAtual.dataAtualIndice or hi_hc.data is null)
order by hc.colaborador_id,hc.data,hfs_hc.data,hi_hfs_hc.data,hi_hc.data;--.go

update papel set papelmae_id=381,ordem=6 where id=70;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (512, 'ROLE_MOV_DISSIDIO', 'Ajuste de Situação (Dissídio)', '/cargosalario/historicoColaborador/prepareAjusteDissidio.action', 4, true, 363); --.go
alter sequence papel_sequence restart with 513;--.go
update parametrosdosistema set modulos = encode(decode(modulos, 'base64') || ',512', 'base64');--.go
insert into perfil_papel(perfil_id, papeis_id) values(1, 512); --.go