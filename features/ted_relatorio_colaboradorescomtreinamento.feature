# language: pt

Funcionalidade: Relatório de colaboradores que fizeram um treinamento

  Cenário: Relatório de colaboradores que fizeram um treinamento
    Dado que exista um curso "java"
    Dado que exista uma turma "turma1" para o curso "java"
    Dado que exista a área organizacional "adm"
    Dado que exista o estabelecimento "matriz"

    Dado que eu esteja logado com o usuário "fortes"
    
    Quando eu acesso o menu "T&D > Relatórios > Colaboradores com Treinamentos"
    Então eu devo ver o título "Colaboradores que fizeram o treinamento"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu seleciono "java" de "Curso"
    E eu seleciono "Empresa Padrão" de "Empresa"
    E eu marco "Empresa Padrão - matriz"
    E eu marco "adm"
    E eu seleciono "Sim" de "Aprovado"
    
    E eu clico no botão "Relatorio"
    E eu devo ver "Não existem dados para o filtro informado."