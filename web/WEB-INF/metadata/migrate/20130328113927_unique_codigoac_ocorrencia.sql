update ocorrencia set codigoac = null where codigoac = ''; --.go

ALTER TABLE ocorrencia ADD CONSTRAINT no_blank_codigoac_ocorrencia CHECK(codigoac <> ''); --.go
ALTER TABLE ocorrencia ADD CONSTRAINT unique_codigoac_empresa_ocorrencia UNIQUE(codigoac,empresa_id); --.go