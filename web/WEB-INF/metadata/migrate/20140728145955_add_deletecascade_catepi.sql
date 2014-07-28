ALTER TABLE cat_epi DROP CONSTRAINT cat_epi_cat_fk;--.go 
ALTER TABLE cat_epi ADD CONSTRAINT cat_epi_cat_fk FOREIGN KEY (cat_id) REFERENCES cat(id) ON DELETE CASCADE;--.go