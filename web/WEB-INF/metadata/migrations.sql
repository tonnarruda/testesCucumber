update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'cidade','cidade,uf');--.go
update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'tituloEleitoral','tituloEleitoral,titEleitZona,titEleitSecao');--.go
update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'certificadoMilitar','certificadoMilitar,certMilTipo,certMilSerie');--.go

update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'cidade','cidade,uf');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'tituloEleitoral','tituloEleitoral,titEleitZona,titEleitSecao');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'certificadoMilitar','certificadoMilitar,certMilTipo,certMilSerie');--.go

update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'cidade','cidade,uf');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'tituloEleitoral','tituloEleitoral,titEleitZona,titEleitSecao');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'certificadoMilitar','certificadoMilitar,certMilTipo,certMilSerie');--.go

update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'ctps','ctps,ctpsSerie,ctpsUf');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'ctps','ctps,ctpsSerie,ctpsUf');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'ctps','ctps,ctpsSerie,ctpsUf');--.go

update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'identidade','identidade,rgOrgaoEmissor');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'identidade','identidade,rgOrgaoEmissor');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'identidade','identidade,rgOrgaoEmissor');--.go

update parametrosdosistema set camposcolaboradorobrigatorio= replace(camposcolaboradorobrigatorio,'carteiraHabilitacao','carteiraHabilitacao,vencimento,chCategoria');--.go
update parametrosdosistema set camposcandidatoobrigatorio= replace(camposcandidatoobrigatorio,'carteiraHabilitacao','carteiraHabilitacao,vencimento,chCategoria');--.go
update parametrosdosistema set camposcandidatoexternoobrigatorio= replace(camposcandidatoexternoobrigatorio,'carteiraHabilitacao','carteiraHabilitacao,vencimento,chCategoria');--.go