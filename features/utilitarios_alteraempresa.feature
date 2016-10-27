# language: pt

Funcionalidade: Importar Cadastros

  Cenário: Importar Cadastros
    Dado que exista uma empresa "Empresa xModelo"
    Dado que eu esteja logado com o usuário "SOS"
    Então eu acesso o menu "Sair"
    Dado que eu esteja logado com o usuário "SOS"
    Então eu devo ver "(Empresa Padrão)" dentro de ".nomeEmpresa"
    Quando eu acesso o menu "Utilitários > Alterar Empresa > Empresa xModelo"
    Então eu devo ver "(Empresa xModelo)" dentro de ".nomeEmpresa"
    Quando eu acesso o menu "Utilitários > Alterar Empresa > Empresa Padrão"
    Então eu devo ver "(Empresa Padrão)" dentro de ".nomeEmpresa"
