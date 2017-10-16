
CREATE TABLE fatorDeRisco (
	id bigint NOT NULL,
	codigo character varying(10),
	descricao text
);--.go

ALTER TABLE fatorDeRisco ADD CONSTRAINT fatorDeRisco_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE fatorDeRisco_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

alter table risco add column gruporiscoesocial character varying(5);--.go

alter table risco add column fatorderisco_id bigint;--.go
ALTER TABLE ONLY risco ADD CONSTRAINT risco_fator_de_risco_fk FOREIGN KEY (fatorderisco_id) REFERENCES fatorderisco(id);--.go


update risco set gruporiscoesocial = '01.01' where gruporisco = '01';--.go
update risco set gruporiscoesocial = '02.01' where gruporisco = '02';--.go
update risco set gruporiscoesocial = '03.01' where gruporisco = '03';--.go
update risco set gruporiscoesocial = '05.01' where gruporisco = '05';--.go


INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.001','Infrassom e sons de baixa frequência');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.002','Ruído contínuo ou Intermitente');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.003','Ruído impulsivo ou de Impacto');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.004','Ultrassom');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.005','Campos magnéticos estáticos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.006','Campos magnéticos de sub-radiofrequência (30 kHz e abaixo)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.007','Sub-Radiofrequência (30 kHz e abaixo) e campos eletrostáticos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.008','Radiação de radiofrequência e micro-ondas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.009','Radiação visível e infravermelho próximo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.010','Radiação ultravioleta');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.011','LASERS');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.012','Radiações Ionizantes');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.013','Vibrações Localizadas (Mão-Braço)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.014','Vibração de corpo inteiro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.015','Estresse por frio (Hipotermia)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.016','Estresse e sobrecarga fisiológica por calor');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.017','Pressão Hiperbárica');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.018','Pressão Hipobárica');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.019','Umidade');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'01.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.001','Acetaldeído');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.002','Acetato de benzila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.003','Acetato de n-butila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.004','Acetato de sec-butila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.005','Acetato de terc-butila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.006','Acetato de 2-butoxietila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.007','Acetato de cellosolve');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.008','Acetato de éter monoetílico de etileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.009','Sais de Cianeto');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.010','Acetato de 2-etoxietila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.011','Acetato de sec-hexila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.012','Acetato de isobutila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.013','Acetato de isopropila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.014','Acetato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.015','Acetato de 2-metoxietila (EGMEA)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.016','Acetato de n-propila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.017','Acetato de pentila, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.018','Acetato de vinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.019','Acetileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.020','Acetofenona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.021','Acetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.022','Acetona cianidrina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.023','Acetonitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.024','Ácido acético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.025','Ácido acetilsalicílico (Aspirina)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.026','Ácido acrílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.027','Ácido adípico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.028','Ácido Aristólico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.029','Ácido bromídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.030','ácido carbônico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.031','Ácido cianídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.032','Ácido clorídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.033','Ácido 2-cloropropiônico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.034','Ácido crômico (névoa)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.035','Ácido dicloroacético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.036','Ácido 2,2-dicloropropiônico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.037','Ácido etanóico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.038','Ácido 2-etil hexanoico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.039','Ácido fluorídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.040','Ácido fórmico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.041','Ácido fosfórico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.042','Ácido metacrílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.043','Ácido metanóico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.044','Ácido monocloroacético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.045','Ácido nítrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.046','Ácido oxálico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.047','Ácido peracético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.048','Ácido pícrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.049','Ácido propiônico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.050','Ácido sulfúrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.051','Ácido tereftálico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.052','Ácido tioglicólico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.053','Ácido tricloroacético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.054','Acrilamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.055','Acrilato de n-butila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.056','Acrilato de etila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.057','Acrilato de 2-hidroxipropila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.058','Acrilato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.059','Acrilonitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.060','Acroleína');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.061','Acronitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.062','Adiponitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.063','Aflatoxinas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.064','Aguarrás mineral (Solvente de Stoddard)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.065','Alaclor');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.066','álcalis cáusticos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.067','Alcatrão de hulha, produtos voláteis como aerossóis solúveis em benzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.068','Álcool alílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.069','Álcool n-butílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.070','Álcool sec-butílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.071','Álcool terc-butílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.072','Álcool etílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.073','Álcool furfurílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.074','Álcool isoamílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.075','Álcool isobutílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.076','Álcool isooctílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.077','Álcool isopropílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.078','Álcool propargílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.079','Álcool metil amílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.080','Álcool metílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.081','Álcool n-propílico (n-propanol)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.082','Aldrin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.083','Aldeído acético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.084','Aldeído fórmico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.085','Algodão, bruto, sem tratamento, poeira');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.086','Alumínio metal e compostos insolúveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.087','Amido');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.088','Aminas aromáticas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.089','4 - Aminodifenil (p-xenilamina)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.090','Aminobifenila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.091','aminoderivados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.092','4-Aminodifenil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.093','2-Aminopiridina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.094','Amitrol (3-amina-1,2,4-triazol)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.095','Amônia');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.096','Anidro sulfuroso');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.097','Anidrido acético');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.098','Anidrido ftálico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.099','Anidrido hexahidroftálico todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.100','Anidrido maleico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.101','Anidrido trimelítico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.102','Anilina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.103','o-Anisidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.104','p-Anisidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.105','Antimônio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.106','antraceno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.107','ANTU');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.108','Argônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.109','Arseneto de gálio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.110','Arsênio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.111','Arsina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.112','Asbestos, todas as formas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.113','Asfalto (betume), fumos, como aerossol solúvel em benzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.114','Atrazine (e triazinas simétricas relacionadas)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.115','Auramina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.116','Azatioprina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.117','Azida de sódio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.118','Azinfos metil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.119','Bário e compostos solúveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.120','Benomil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.121','Benzeno e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.122','Benzidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.123','Benzo[a]antraceno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.124','Benzo[b]fluoranteno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.125','Benzopireno (Benzo[a]pireno)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.126','Berílio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.127','betume');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.128','BHC (hexacloreto de benzeno)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.129','Bifenil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.130','Bifenis policlorados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.131','Biscloroetileter');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.132','Bisclorometil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.133','Bissulfito de sódio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.134','Borracha natural, látex como proteínas alergênicas inaláveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.135','Borato, compostos inorgânicos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.136','breu');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.137','Bromacil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.138','Brometo de alila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.139','Brometo de etila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.140','Brometo de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.141','Brometo de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.142','Brometo de vinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.143','Bromo e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.144','Bromoetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.145','Bromofórmio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.146','Bromometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.147','1-Bromopropano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.148','Bussulfano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.149','1,3-Butadieno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.150','Butadieno-estireno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.151','n-Butano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.152','Butano, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.153','1-4 Butanodiol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.154','Butenos, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.155','sec-Butanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.156','Butanona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.157','1-Butanotiol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.158','Butil cellosolve');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.159','n-Butil mercaptana');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.160','n-Butilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.161','o-sec Butilfenol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.162','p-terc-Butiltolueno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.163','2-Butóxi etanol (EGBE)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.164','Cádmio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.165','Canfeno clorado');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.166','Cânfora, sintética');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.167','Caolim');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.168','Caprolactama');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.169','Captafol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.170','Captan');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.171','Carbaril');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.172','Carbeto de silício');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.173','Carbofuran');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.174','Carvão mineral e seus derivados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.175','Catecol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.176','Cellosolve');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.177','Celulose');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.178','Cereais, poeira (aveia, cevada, trigo)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.179','Ceteno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.180','Chumbo e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.181','Chumbo tetraetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.182','Chumbo tetrametila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.183','Cianamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.184','Cianamida de cálcio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.185','Cianeto de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.186','Cianeto de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.187','Cianeto de vinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.188','Cianoacrilato de etila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.189','2-Cianoacrilato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.190','Cianogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.191','Ciclofosfamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.192','Ciclohexano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.193','Ciclohexanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.194','Ciclohexanona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.195','Ciclohexeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.196','Ciclohexilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.197','Ciclonita');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.198','Ciclopentadieno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.199','Ciclopentano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.200','Ciclosporina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.201','Cihexatin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.202','Cimento portland');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.203','Citral');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.204','Clopidol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.205','Clorambucil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.206','Clordane');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.207','Cloreto de alila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.208','Cloreto de amônio - fumos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.209','Cloreto de benzila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.210','Cloreto de benzoíla');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.211','Cloreto de carbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.212','Cloreto de cianogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.213','Cloreto de cloroacetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.214','Cloreto de cromila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.215','Cloreto de dimetil carbamoila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.216','Cloreto de enxofre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.217','Cloreto de etila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.218','Cloreto de fenila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.219','Cloreto de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.220','Cloreto de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.221','Cloreto de metileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.222','Cloreto de polivinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.223','Cloreto de tionila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.224','Cloreto de vinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.225','Cloreto de vinilideno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.226','Cloreto de zinco, fumos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.227','Clornafazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.228','Cloro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.229','Cloroacetaldeído');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.230','2-Cloroacetofenona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.231','Cloroacetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.232','Cloroambucil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.233','Clorobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.234','o-Clorobenzilideno malononitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.235','Clorobromometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.236','Clorodifenil (42% de Cloro)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.237','Clorodifenil (54% de Cloro)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.238','Clorodifluormetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.239','o-Cloroestireno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.240','Cloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.241','Cloroetílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.242','Clorofórmio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.243','1-Cloro-1-nitropropano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.244','1-Cloro-2');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.245','Clorometileter');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.246','Cloropentafluoretano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.247','Cloropicrina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.248','Cloropirifos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.249','Cloroprene');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.250','Cloropreno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.251','ß-Cloropreno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.252','1-Cloro-2-propanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.253','2-Cloro-1-propanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.254','o-Clorotolueno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.255','Cobalto e seus compostos inorgânicos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.256','Cobalto carbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.257','Cobalto hidrocarbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.258','Cobre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.259','Coumafos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.260','Cresol, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.261','Creosoto');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.262','Criseno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.263','Cromato de terc-butila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.264','Cromato de cálcio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.265','Cromato de chumbo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.266','Cromato de estrôncio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.267','Cromatos de zinco');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.268','Cromita - processamento do minério (Cromato)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.269','Cromo e seus compostos inorgânicos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.270','Crotonaldeído');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.271','Crufomate');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.272','Cumeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.273','2,4 D');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.274','DDD (diclorodifenildicloretano)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.275','DDT');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.276','Decaborano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.277','Demeton');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.278','Demeton-S-metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.279','Destilação do alcatrão de hulha');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.280','Diacetil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.281','Diacetona álcool');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.282','Diamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.283','α,α''Diamina m-xileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.284','Dianizidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.285','Diazinon');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.286','Diazometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.287','Diborano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.288','1,2-Dibramoetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.289','Dibrometo de etileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.290','2-N-Dibutilaminoetanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.291','Dibutilftalato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.292','Diciclopentadieno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.293','1,1 Dicloreotileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.294','Dicloreto de etileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.295','Dicloreto de propileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.296','o-Diclorobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.297','p-Diclorobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.298','Diclorobenzidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.299','3,3'' -Diclorobenzidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.300','1,4-Dicloro-2-buteno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.301','Diclorodifluormetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.302','1,3-Dicloro-5,5-dimetil hidantoina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.303','1,1-Dicloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.304','1,2 Dicloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.305','1,2 Dicloroetileno, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.306','Diclorofluormetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.307','Diclorometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.308','1,1-Dicloro-1-nitroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.309','1,2 Dicloropropano (Dicloroacetileno)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.310','1,3-Dicloropropeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.311','Diclorotetrafluoretano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.312','Diclorvos (DDVP)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.313','Dicrotofós');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.314','Dieldrin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.315','Diesel, combustível, como hidrocarbonetos totais');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.316','Dietanolamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.317','Dietilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.318','2-Dietilaminoetanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.319','Dietilcetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.320','Dietil éter');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.321','Dietileno triamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.322','Dietilestil-bestrol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.323','Dietilestilbestrol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.324','Dietilftalato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.325','Dietilsulfato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.326','Difenilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.327','Difluordibromometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.328','Difluoreto de oxigênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.329','Dihidrocloreto de piperazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.330','Diisobutil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.331','Diisocianato de isoforona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.332','2,4 Diisocianato de tolueno (TDI)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.333','Diisopropilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.334','N,N-Dietilhidroxilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.335','Dimetanosulfonato (MILERAN)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.336','N,N-Dimetilacetamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.337','Dimetilacetamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.338','Dimetilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.339','Dimetilanilina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.340','Dimetiletoxisilano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.341','Dimetilformamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.342','Dimetilftalato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.343','1,1-Dimetilhidrazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.344','Dimetilsulfato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.345','Dinitrato de etileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.346','Dinitrato de propileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.347','Dinitrobenzeno, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.348','Dinitro-o-cresol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.349','3,5-Dinitro-o-toluamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.350','Dinitrotolueno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.351','1,4-Dioxano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.352','Dioxation');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.353','Dióxido de carbono');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.354','Dióxido de cloro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.355','Dióxido de enxofre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.356','1,3-Dioxolane');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.357','Dióxido de nitrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.358','Dióxido de titânio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.359','Dióxido de vinilciclohexano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.360','Dipropil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.361','Diquat');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.362','Dissulfeto de alil propila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.363','Dissulfeto de carbono');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.364','Dissulfeto de dimetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.365','Dissulfiram');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.366','Dissulfoton');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.367','Diuron');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.368','Divinil benzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.369','Dodecil mercaptana');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.370','Endosulfan');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.371','Endrin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.372','Enflurano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.373','Epicloridrina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.374','EPN');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.375','Erionita');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.376','Estanho e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.377','Estearatos (J)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.378','Estibina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.379','Estilbenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.380','Estireno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.381','Estriquinina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.382','Etano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.383','Etanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.384','Etanolamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.385','Etanotiol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.386','Éter alil glicidílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.387','Éter n-Butil glicidílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.388','Éter bis-(Clorometílico) ou Bis (cloro metil) éter');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.389','Éter bis (2-dimetilaminoetil)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.390','Éter dicloroetílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.391','Éter diglicidílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.392','Éter etil terc-butílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.393','Éter etílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.394','Éter fenílico, vapor');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.395','Éter fenil glicidílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.396','Éter isopropil glicidílico (IGE)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.397','Éter isopropílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.398','Éter isopropílico de monoetileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.399','Éter metil terc-amílico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.400','Éter metil terc-butílico (MTBE)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.401','Éter metílico de clorometila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.402','Éter metílico de dipropilenoglicol (DPGME)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.403','Éter monobutílico de dietileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.404','Éter monobutílico do etileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.405','Éter monoetílico do etileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.406','Éter monometílico do etileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.407','Etil amil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.408','Etil butil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.409','Etil mercaptana');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.410','n-Etil morfolina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.411','Etilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.412','Etilbenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.413','Etileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.414','Etilenoamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.415','Etilenotiureia');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.416','Etileno cloridrina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.417','Etileno diamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.418','Etileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.419','Etilideno norborneno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.420','Etil isocianato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.421','Etilenoimina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.422','Etilnitrosuréias');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.423','Etion');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.424','Etoposide');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.425','Etoposide em associação com cisplatina e bleomicina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.426','2-Etoxietanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.427','Farinha (poeiras)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.428','Fenacetina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.429','Fenamifos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.430','n-Fenil-ß-naftilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.431','o-Fenileno diamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.432','m-Fenileno diamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.433','p-Fenileno diamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.434','Fenilfosfina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.435','Fenilhidrazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.436','Fenil mercaptana');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.437','Fenol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.438','Fenotiazine');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.439','Fensulfotion');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.440','Fention');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.441','Ferbam');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.442','Ferro, sais solúveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.443','Ferro diciclopentadienila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.444','Ferro, óxido (Fe 2 O 3 )');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.445','Ferro pentacarbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.446','Ferrovanádio, poeira');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.447','Fibras Vítreas Sintéticas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.448','Flúor');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.449','Fluoracetato de sódio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.450','Fluoretos, como F');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.451','Fluoreto de carbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.452','Fluoreto de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.453','Fluoreto de perclorila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.454','Fluoreto de sulfurila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.455','Fluoreto de vinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.456','Fluoreto de vinilideno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.457','Fonofos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.458','Forate');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.459','Formaldeído');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.460','Formamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.461','Formiato de etila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.462','Formiato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.463','Fosfato de dibutila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.464','Fosfato de dibutil fenila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.465','Fosfato de tributila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.466','Fosfato de trifenila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.467','Fosfato de triortocresila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.468','Fosfina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.469','Fosfito de trimetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.470','Fósforo (amarelo)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.471','Fosgênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.472','Fluortriclorometano (freon 11)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.473','Freon 12');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.474','Freon 22');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.475','Freon 113');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.476','Freon 114');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.477','Ftalato de dibutila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.478','Ftalato de di(2-etilhexila)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.479','Ftalato de dietila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.480','m-Ftalodinitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.481','o-Ftalodinitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.482','Furfural');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.483','Gás amoníaco');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.484','Gás carbônico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.485','Gás cianídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.486','Gás clorídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.487','Gás Mostarda');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.488','Gás natural');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.489','Gás sulfídrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.490','Gasolina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.491','Glicerina, névoas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.492','Glicidol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.493','Glioxal');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.494','GLP (gás liquefeito do petróleo)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.495','Glutaraldeído, ativado e não ativado');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.496','Grafite (todas as formas, exceto fibras de grafite)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.497','Grãos, poeira (aveia, trigo, cevada)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.498','Háfnio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.499','halogenados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.500','Halotano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.501','Hélio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.502','Heptacloro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.503','Heptacloro epóxido');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.504','Heptano, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.505','Hexaclorobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.506','Hexaclorobutadieno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.507','Hexaclorociclopentadieno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.508','Hexacloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.509','Hexacloronaftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.510','Hexafluoracetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.511','Hexafluorpropileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.512','Hexafluoreto de enxofre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.513','Hexafluoreto de selênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.514','Hexafluoreto de telúrio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.515','Hexametileno diisocianato (HDI)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.516','Hexametil fosforamida');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.517','n-Hexano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.518','Hexano, outros isômeros que não o n-Hexano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.519','1,6-Hexanodiamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.520','1-Hexeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.521','Hexileno glicol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.522','Hidrazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.523','Hidreto de antimônio (Estibina)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.524','Hidreto de lítio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.525','Hidrocarbonetos alifáticos gasosos Alcanos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.526','Hidrocarbonetos e outros compostos de carbono');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.527','hidrocarbonetos aromáticos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.528','hidrocarbonetos cíclicos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.529','Hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.530','Hidroquinona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.531','Hidróxido de cálcio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.532','Hidróxido de césio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.533','Hidróxido de potássio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.534','Hidróxido de sódio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.535','Hidroxitolueno butilado');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.536','Indeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.537','Iodeto de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.538','Índio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.539','Iodo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.540','Iodetos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.541','Iodofórmio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.542','Isobutanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.543','Isobuteno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.544','isocianato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.545','Isocianato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.546','Isoforona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.547','Isopropilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.548','n-Isopropilanilina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.549','Isopropil benzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.550','2-Isopropoxietanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.551','Ítrio e compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.552','Lactato de n-butila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.553','Lindano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.554','Madeira, poeiras');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.555','Malation');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.556','Manganês e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.557','Manganês ciclopentadienil tricarbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.558','Melfalano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.559','Mercaptanos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.560','Mercúrio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.561','Metabisulfito de sódio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.562','Metacrilato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.563','Metano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.564','Metanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.565','Metil acetileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.566','Metil acetileno-propadieno, mistura (MAPP)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.567','Metilacrilonitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.568','Metilal');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.569','Metilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.570','Metil n-amil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.571','n-Metil anilina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.572','Metil n-butil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.573','Metil cellosolve');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.574','Metilciclohexano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.575','Metilciclohexanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.576','o-Metilciclohexanona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.577','2-Metilciclopentadienil manganês tricarbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.578','Metil clorofórmio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.579','Metil demeton');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.580','Metil etil cetona (MEK)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.581','α-Metil estireno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.582','Metil hidrazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.583','Metil isoamil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.584','Metil isobutil carbinol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.585','Metil isobutil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.586','Metil isopropil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.587','Metil mercaptana');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.588','1-Metil naftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.589','2-Metil naftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.590','Metil paration');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.591','Metil propil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.592','Metil vinil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.593','Metileno-bis-(4-ciclohexilisocianato)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.594','4,4-metileno-bis-(2-cloroanilina) (MOCA®) (MBOCA®)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.595','Metileno bisfenil isocianato (MDI)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.596','4,4''-Metileno dianilina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.597','Metileno-ortocloroanilina (MOCA)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.598','Metomil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.599','Metoxicloro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.600','2-Metoxietanol (EGME)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.601','(2-Metoximetiletoxi) propanol (DPGME)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.602','4-Metoxifenol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.603','1-Metoxi-2-propanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.604','Metoxsalen associado com radiação ultravioleta A');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.605','Monometil hidrazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.606','Metribuzin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.607','Mevinfos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.608','Mica');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.609','Molibdênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.610','Monocrotofós');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.611','Monóxido de carbono');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.612','Morfolina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.613','Naftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.614','ß-Naftilamina (Betanaftilamina)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.615','naftóis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.616','Naled');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.617','Negro de fumo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.618','Neônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.619','Nicotina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.620','Níquel e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.621','Nitrapirin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.622','Nitrato de n-propila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.623','Nitrito de isobutila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.624','p-Nitroanilina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.625','Nitrobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.626','p-Nitroclorobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.627','nitroderivados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.628','4 - Nitrodifenil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.629','4-Nitrodifenila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.630','Nitroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.631','Nitrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.632','Nitroglicerina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.633','Nitrometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.634','Nitronaftilamina 4-Dimetil-aminoazobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.635','1-Nitropropano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.636','2-Nitropropano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.637','Nitrosamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.638','n-Nitrosodimetilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.639','N''-nitrosonornicotina (NNN) e 4-. (metilnitrosamino)-1-(3-piridil)1-butano- na (NNK)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.640','Nitrotolueno, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.641','5-Nitro-o-toluidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.642','Nonano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.643','Octacloronaftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.644','Octano, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.645','Óleo diesel, como hidrocarbonetos totais');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.646','Óleo mineral, excluídos os fluídos de trabalho com metais');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.647','óleo queimado');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.648','Óleos de xisto');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.649','Ortotoluidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.650','p,p''-Oxibis(benzeno sulfonila hidrazida)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.651','Oxicloreto de fósforo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.652','Óxido de boro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.653','Óxido de cálcio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.654','Óxido de difenila o-clorada');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.655','Óxido de etileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.656','Óxido de magnésio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.657','Óxido de mesitila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.658','Óxido de propileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.659','Óxido de zinco');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.660','Óxido nítrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.661','Óxido nitroso');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.662','Oxime-talona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.663','Ozona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.664','Ozônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.665','Parafina, cera (fumos)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.666','Paraquat, como o cátion');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.667','Paration');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.668','Partículados (insolúveis ou de baixa solubilidade) não especificados de outra maneira (PNOS)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.669','Pentaborano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.670','Pentacloreto de fósforo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.671','3, 4, 5, 3´, 4'' -Pentaclorobifenil (PCB - 126)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.672','2 ,3 ,4 ,7 ,8-Pentaclorodibenzofurano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.673','Pentaclorofenol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.674','Pentacloronaftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.675','Pentacloronitrobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.676','Pentaeritritol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.677','Pentafluoreto de bromo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.678','Pentafluoreto de enxofre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.679','n-Pentano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.680','Pentano, todos os isômeros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.681','2,4-Pentanodiona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.682','Pentassulfeto de fósforo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.683','Pentóxido de vanádio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.684','Percloroetileno (Tetracloroetileno)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.685','Perclorometil mercaptana');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.686','Perfluorobutil etileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.687','Perfluorisobutileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.688','Perfluoroctanoato de amônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.689','Peróxido de benzoíla');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.690','Peróxido de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.691','Peróxido de metil etil cetona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.692','Persulfatos, como persulfato');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.693','Petróleo e seus derivados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.694','Picloram');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.695','Pindone');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.696','Pirperazina e sais, como Piperazia');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.697','Piretro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.698','Piridina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.699','Pirofosfato de tetraetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.700','Platina e sais solúveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.701','Plutônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.702','poliisocianetos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.703','poliuretanas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.704','3-Poxipro-pano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.705','Prata e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.706','Procarbazina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.707','Propano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.708','n-propano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.709','Propanona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.710','Propano sultona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.711','Propano sultone');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.712','Propanosultona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.713','n-Propanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.714','iso-Propanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.715','2-Propanol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.716','Propileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.717','Propileno imina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.718','ß-Propiolactona (Beta-propiolactona)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.719','Propionaldeído');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.720','Propoxur');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.721','PVC (poli cloreto de vinila)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.722','Querosene combustível de avião, como vapor de hidrocarbonetos totais');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.723','Quinona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.724','Rádio-224 e seus produtos de decaimento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.725','Rádio-226 e seus produtos de decaimento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.726','Rádio-228 e seus produtos de decaimento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.727','Radônio-222 e seus produtos de decaimento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.728','Resina de vareta (eletrodo arame) de solda, produtos da decomposição térmica (breu)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.729','Resorcinol');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.730','Ródio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.731','Ronel');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.732','Rotenona (comercial)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.733','Sacarose');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.734','Seleneto de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.735','Selênio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.736','Semustina [1-(2 -cloroetil) -3-(4-metilciclohexil)-1-nitrosourea, Metil CC- NU]');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.737','Sesone');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.738','Sílica Cristalina - α-quartzo e cristobalita');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.739','Sílica livre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.740','Sílica cristobalita');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.741','Silicato de cálcio, sintético não fibroso');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.742','Silicato de etila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.743','Silicato de metila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.744','Silicatos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.745','Subtilisins, como enzima cristalina ativa');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.746','Sulfamato de amônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.747','Sulfato de bário');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.748','Sulfato de cálcio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.749','Sulfato de dimetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.750','Sulfato de carbonila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.751','Sulfeto de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.752','Sulfeto de dimetila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.753','sulfeto de níquel');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.754','Sulfometuron metil');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.755','Sulfotep (TEDP)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.756','Sulprofos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.757','Systox');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.758','2,4,5-T');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.759','Talco');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.760','Tálio, e compostos, como TI');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.761','Tamoxifeno (nota: há evidências também conclusivas para seu uso na re- dução do risco de câncer de mama contralateral em pacientes com câncer de mama)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.762','Telureto de bismuto');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.763','Telúrio e compostos (NOS), como Te, excluído telureto de hidrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.764','Temefós');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.765','Terbufos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.766','Terebentina e monoterpenos selecionados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.767','Terfenilas (o,m,p-isômeros)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.768','Terfenilas hidrogenadas (não irradiadas)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.769','1,1,2,2,Tetrabromoetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.770','Tetrabrometo de acetileno (1,1,2,2-Tetrabromoetano)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.771','Tetrabrometo de carbono');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.772','Tetracloreto de carbono');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.773','2,3,7,8-Tetraclorodibenzo-para-dioxina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.774','1,1,1,2-Tetracloro-2,2-difluoretano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.775','1,1,2,2-Tetracloro-1,2-difluoretano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.776','1,1,2,2-Tetracloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.777','Tetracloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.778','Tetracloronaftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.779','Tetracloroetileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.780','Tetrafluoretileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.781','Tetrafluoreto de enxofre');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.782','Tetrahidreto de germânio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.783','Tetrahidreto de silício');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.784','Tetrahidrofurano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.785','Tetraquis (hidroximetil) fosfônio, sais - Cloreto de tetraquis (hidroximetil) fosfônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.786','Tetraquis (hidroximetil) fosfônio, sais - Sulfato de tetraquis (hidroximetil) fosfônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.787','Tetrametil succinonitrila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.788','Tetranitrometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.789','Tetril');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.790','Tetróxido de ósmio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.791','Thiram');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.792','Tiotepa');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.793','Titânio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.794','4,4''-Tiobis (6-terc-butil-m-cresol)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.795','o-Tolidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.796','Tolueno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.797','Tolueno 2,4 ou 2,6 -diisocianato (ou como mistura)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.798','o-Toluidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.799','m-Toluidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.800','p-Toluidina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.801','Tório-232 e seus produtos de decaimento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.802','Tribrometo de boro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.803','Tribromometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.804','Tricloreto de fósforo');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.805','Tricloreto de vinila');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.806','Triclorfon');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.807','Triclorometil benzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.808','1,1,2-Tricloro-1,2,2-trifluoretano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.809','1,2,4-Triclorobenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.810','1,1,1 Tricloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.811','1,1,2-Tricloroetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.812','Tricloroetileno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.813','Triclorometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.814','Triclorofluormetano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.815','Tricloronaftaleno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.816','1,2,3-Tricloropropano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.817','Trietanolamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.818','Trietilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.819','Trifluorbromometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.820','Trifluoreto de boro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.821','Trifluoreto de cloro');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.822','Trifluoreto de nitrogênio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.823','Trifluormonobramometano');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.824','1,3,5-Triglicidil-s-triazinetriona');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.825','Trimetilamina');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.826','Trimetil benzeno (mistura de isômeros)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.827','2,4,6-Trinitrotolueno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.828','trióxido de amônio');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.829','Trióxido de antimônio - Produção');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.830','Tungstênio e seus compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.831','Urânio (natural) Compostos solúveis e insolúveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.832','n-Valeraldeído');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.833','Vinibenzeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.834','4-Vinilciclohexeno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.835','n-Vinil-2-pirrolidone');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.836','Vinil tolueno');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.837','Warfarin');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.838','Xileno (o, m e p isômeros)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.839','Xilidina (mistura de isômeros)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.840','Xisto betuminoso');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.841','Zircônio e compostos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'02.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.001','Trabalho ou operações, em contato permanente com pacientes em isolamento por doenças infecto-contagiosas, bem como objetos de seu uso, não previamente esterilizados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.002','Trabalho ou operações, em contato permanente com carnes, glândulas, vísceras, sangue, ossos, couros, pêlos e dejeções de animais portadores de doenças infecto-contagiosas (carbunculose, brucelose, tuberculose)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.003','Trabalho ou operações, em contato permanente com esgotos (galerias e tanques)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.004','Trabalho ou operações, em contato permanente com lixo urbano (coleta e industrialização)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.005','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em hospitais, serviços de emergência, enfermarias, ambulatórios, postos de vacinação e outros estabelecimentos destinados aos cuidados da saúde humana (aplica se unicamente ao pessoal que tenha contato com os pacientes, bem como aos que manuseiam objetos de uso desses pacientes, não previamente esterilizados).');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.006','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em hospitais, ambulatórios, postos de vacinação e outros estabelecimentos destinados ao atendimento e tratamento de animais (aplica se apenas ao pessoal que tenha contato com tais animais).');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.007','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em contato em laboratórios, com animais destinados ao preparo de soro, vacinas e outros produtos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.008','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em laboratórios de análise clínica e histopatologia (aplica-se tão-só ao pessoal técnico)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.009','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em gabinetes de autópsias, de anatomia e histoanatomopatologia (aplica-se somente ao pessoal técnico)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.010','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em cemitérios (exumação de corpos).');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.011','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em estábulos e cavalariças');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.012','Trabalhos e operações em contato permanente com pacientes, animais ou com material infecto-contagiante, em resíduos de animais deteriorados.');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.013','Trabalho de exumação de corpos e manipulação de resíduos de animais deteriorados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.014','Esvaziamento de biodigestores');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'03.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.001','Exigência de posturas incômodas ou pouco confortáveis por longos períodos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.002','Postura sentada por longos períodos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.003','Postura de pé por longos períodos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.004','Constante deslocamento a pé durante a jornada de trabalho');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.005','Exigência de esforço físico intenso');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.006','Levantamento e transporte manual de cargas ou volumes');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.007','Frequente ação de puxar/empurrar cargas ou volumes');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.008','Frequente execução de movimentos repetitivos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.009','Manuseio de ferramentas e/ou objetos pesados por períodos prolongados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.02.001','Mobiliário sem meios de regulagem de ajuste');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.02.002','Equipamentos e/ou máquinas sem meios de regulagem de ajuste ou sem condições de uso');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.02.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.001','Ausência de pausas para descanso ou não cumprimento destas durante a jornada');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.002','Necessidade de manter ritmos intensos de trabalho');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.003','Trabalho com necessidade de variação de turnos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.004','Monotonia');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.005','Ausência de um plano de capacitação, habilitação, reciclagem e atualização dos empregados');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.006','Cobrança de metas de impossível atingimento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.03.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.04.001','Situações de estresse');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.04.002','Situações de sobrecarga de trabalho mental');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.04.003','Exigência de alto nível de concentração ou atenção');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.04.004','Meios de comunicação ineficientes');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'04.04.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.001','Trabalho em altura');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.002','Iluminação inadequada');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.003','Choque elétrico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.004','Choque mecânico');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.005','Arranjo físico inadequado');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.006','Incêndio e explosão (probabilidade)');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.007','Máquinas e equipamentos sem proteção');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.008','Máquinas e equipamentos com proteção inadequada');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.009','Armazenamento inadequado');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.010','Ferramentas inadequadas ou defeituosas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.011','Soterramento');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.012','Animais peçonhentos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.013','Animais domésticos/Risco a acidentes de ataque');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.014','Animais selvagens/Risco a acidentes de ataque');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.015','Cortes e perfurações');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.016','Queimaduras');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.017','Acidentes de trânsito');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'05.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.001','Explosivos');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.002','Inflamáveis');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.003','Energia elétrica');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.004','Radiações Ionizantes ou substâncias Radioativas');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.005','Profissionais de Segurança Pessoal ou Patrimonial');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.006','As atividades laborais com utilização de motocicleta ou motoneta no deslocamento de trabalhador em vias públicas são consideradas perigosas.');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'06.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'07.01.001','Decisão judicial');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'07.01.002','Acordo - Convenção');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'07.01.003','Liberalidade');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'07.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'08.01.001','Mineração subterrânea cujas atividades sejam exercidas afastadas das frentes de produção');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'08.01.002','Trabalhos em atividades permanentes no subsolo de minerações subterrâneas em frente de produção');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'08.01.999','Outros');--.go
INSERT INTO FATORDERISCO(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('fatorDeRisco_sequence'),'09.01.001','Ausência de Fator de Risco');--.go