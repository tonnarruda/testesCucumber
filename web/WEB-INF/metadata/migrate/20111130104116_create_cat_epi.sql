CREATE TABLE cat_epi (
    cat_id bigint NOT NULL,
    epi_id bigint NOT NULL
);--.go
ALTER TABLE cat_epi ADD CONSTRAINT cat_epi_cat_fk FOREIGN KEY (cat_id) REFERENCES cat(id);--.go
ALTER TABLE cat_epi ADD CONSTRAINT cat_epi_epi_fk FOREIGN KEY (epi_id) REFERENCES epi(id);--.go