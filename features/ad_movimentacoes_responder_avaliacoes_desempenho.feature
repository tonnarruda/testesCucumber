# language: pt

Funcionalidade: Cadastrar Avaliações de Desempenho

  Cenário: Cadastro de Avaliações de Desempenho
    Dado que exista uma avaliacao "modelo"
    Dado que exista uma avaliacao desempenho "Av de Desempenho" com o avaliador "joao"




    E eu clico "Exibir Filtro"
    E eu seleciono "Av de Desempenho" de "Avaliação de Desempenho"
    E eu seleciono "joao" de "Avaliador"





    Dado que eu esteja logado
