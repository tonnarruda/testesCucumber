ALTER TABLE parametrosdosistema RENAME  camposcandidatovisivel TO camposcandidatoexternovisivel; --.go
ALTER TABLE parametrosdosistema RENAME  camposcandidatoobrigatorio TO camposcandidatoexternoobrigatorio; --.go
ALTER TABLE parametrosdosistema RENAME  camposcandidatotabs TO camposcandidatoexternotabs; --.go

ALTER TABLE parametrosdosistema add camposcandidatovisivel text; --.go
ALTER TABLE parametrosdosistema add camposcandidatoobrigatorio text; --.go
ALTER TABLE parametrosdosistema add camposcandidatotabs text; --.go

ALTER TABLE parametrosdosistema add camposcolaboradorvisivel text; --.go
ALTER TABLE parametrosdosistema add camposcolaboradorobrigatorio text; --.go
ALTER TABLE parametrosdosistema add camposcolaboradortabs text; --.go

update parametrosdosistema set camposcolaboradorvisivel = 'nome,nomeComercial,nascimento,sexo,cpf,escolaridade,endereco,email,fone,celular,estadoCivil,qtdFilhos,nomeConjuge,nomePai,nomeMae,deficiencia,matricula,dt_admissao,vinculo,dt_encerramentoContrato,regimeRevezamento,formacao,idioma,desCursos,expProfissional,infoAdicionais,identidade,carteiraHabilitacao,tituloEleitoral,certificadoMilitar,ctps,pis,modelosAvaliacao'; --.go
update parametrosdosistema set camposcolaboradorobrigatorio = 'nome,nomeComercial,nascimento,cpf,escolaridade,ende,num,cidade,email,fone,dt_admissao'; --.go
update parametrosdosistema set camposcolaboradortabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais,abaModelosAvaliacao'; --.go

update parametrosdosistema set camposcandidatoobrigatorio = 'nome,escolaridade,ende,num,cidade,fone'; --.go
update parametrosdosistema set camposcandidatotabs = 'abaDocumentos,abaExperiencias,abaPerfilProfissional,abaFormacaoEscolar,abaDadosPessoais'; --.go
update parametrosdosistema set camposcandidatovisivel = 'nome,nascimento,naturalidade,sexo,cpf,escolaridade,endereco,email,fone,celular,nomeContato,parentes,estadoCivil,qtdFilhos,nomeConjuge,profConjuge,nomePai,profPai,nomeMae,profMae,pensao,possuiVeiculo,deficiencia,comoFicouSabendoVaga,comfirmaSenha,senha,formacao,idioma,desCursos,cargosCheck,areasCheck,conhecimentosCheck,colocacao,expProfissional,infoAdicionais,identidade,carteiraHabilitacao,tituloEleitoral,certificadoMilitar,ctps,pis'; --.go

update parametrosdosistema set camposcolaboradorvisivel = ''; --.go
update parametrosdosistema set camposcolaboradorobrigatorio = ''; --.go
update parametrosdosistema set camposcolaboradortabs = ''; --.go

insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (674, 'ROLE_CONFIG_CAMPOS_COLABORADOR', 
'Cadastro de Colaborador', '#', 1, false, 503);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (675, 'ROLE_CONFIG_CAMPOS_CANDIDATO', 
'Cadastro de Candidato', '#', 2, false, 503);--.go
insert into papel (id, codigo, nome, url, ordem, menu, papelmae_id) values (676, 'ROLE_CONFIG_CAMPOS_CANDIDATO_EXT', 
'Cadastro de Candidato (Externo)', '#', 3, false, 503);--.go

alter sequence papel_sequence restart with 677;--.go

update papel set codigo = 'ROLE_CONFIG_CAMPOS', nome = 'Configurar Cadastro de Colaborador e Candidato', url = '/geral/parametrosDoSistema/listCampos.action' where id = 503;

insert into perfil_papel(perfil_id, papeis_id) select perfil_id, 676 from perfil_papel where papeis_id in (503);
