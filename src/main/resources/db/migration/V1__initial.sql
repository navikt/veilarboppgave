CREATE TABLE oppgavehistorikk (
  id BIGSERIAL PRIMARY KEY,
  aktoerid VARCHAR(255) NOT NULL,
  gsak_id VARCHAR(255) NOT NULL,
  tema VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  opprettet_av_ident VARCHAR(20) NOT NULL,
  opprettet_tidspunkt TIMESTAMP NOT NULL
);

CREATE INDEX oppgavehistorikk_aktoerid_idx ON oppgavehistorikk (aktoerid);
