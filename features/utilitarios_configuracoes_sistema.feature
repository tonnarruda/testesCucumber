# language: pt

Funcionalidade: Configurações do Sistema

  Cenário: Alterar Configurações do Sistema
    Dado que eu esteja logado

    Quando eu acesso o menu "Utilitários > Configurações > Sistema"
    Então eu devo ver o título "Editar Configurações do Sistema"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    Então eu devo ver o título "Editar Configurações do Sistema"

    Então eu preencho "URL da Aplicação" com "http://127.0.0.1:8080/fortesrh"
    E eu preencho "Contexto da Aplicação" com "/fortesrh"
    E eu preencho "Atualizador" com "rubens"
    E eu preencho "Configuração do Autenticador" com "v1.14"
    E eu preencho "diasLembretePesquisa" com "1&3&5"
    E eu preencho "diasLembretePeriodoExperiencia" com "30&45&60"
    E eu seleciono "Usuário" de "Perfil Padrão"
    E eu marco "Forçar caixa alta nos campos do módulo externo"
    E eu desmarco "Compartilhar Candidatos entre empresas."
    E eu desmarco "Compartilhar Colaboradores entre empresas."
    E eu preencho "E-mail do Suporte Técnico" com "teste@abc.com.br"
    E eu preencho "Codigo da Empresa" com "123"
    E eu preencho "Codigo do Cliente" com "456"
    E eu preencho "Servidor SMTP" com "smtp.abc.com.br"
    E eu preencho "Porta SMTP" com "25"
    E eu preencho "Usuário" com "teste@abc.com.br"
    E eu preencho "Senha" com "1234"
    E eu preencho o campo (JS) "update_parametrosDoSistema_proximaVersao" com "28/07/2013"
    E eu clico no botão "Gravar"
    Então eu devo ver "Configurações do Sistema atualizadas com sucesso!"