update parametrosdosistema set appcontext='/fortesrh', appurl = 'http://127.0.0.1:8080/fortesrh', proximaversao='2020-01-01';
update grupoac set acurlsoap = 'http://localhost:1024/soap/IAcPessoal',acurlwsdl = 'http://localhost:1024/wsdl/IAcPessoal';
update parametrosdosistema set emailsmtp='mailtrap.io', emailport='465', emailuser='284631bca48fa0a62', emailpass='6979a34aab4468', autenticacao = true, tls = false where emailsmtp is not null and emailsmtp != '';
update usuario set senha='MTIzNA==';
update historicocolaborador set salario=5555.00;
update gastoempresaitem set valor=25.00;
update historicobeneficio set valor=55.00;
update empresa set acintegra=false;