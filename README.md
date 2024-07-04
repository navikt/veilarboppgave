Veilarboppgave
==============

# Henvendelser

Spørsmål knyttet til koden eller prosjektet kan stilles ved å opprette en issue.

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #po-arbeidsoppfølging.

## Kode generert av GitHub Copilot

Dette repoet bruker GitHub Copilot til å generere kode.
## PostgreSQL
Innloggingsinformasjon til databasen: https://vault.adeo.no/

Dev: vault read postgresql/preprod-fss/creds/veilarboppgave-pg15-dev-admin

Ting å passe på ved oppgradering av databasen:
1. Lag PR i repo database-iac, eksempel https://github.com/navikt/database-iac/pull/592 Ny database får gjerne nytt navn, eks veilarboppgave-pg15
I repoet database-iac finner du bla hostnavn og port
2. Lag PR i repo vault.iac, eksempel: https://github.com/navikt/vault-iac/pull/5514 
Ny database må legges inn i terraform/teams/pto/apps/veilarboppgave.yml og i terraform/teams/pto/pto.yml, både under dev og prod
3. I dev: Du kan opprette tabeller i den nye databasen ved å ta ut DDlene fra gammel db, og tilpasse skriptet med nytt navn. Kjør skriptet.
4. I dev: Bruk Import/Export og Copy table to.. (F5) Dobbeltklikk på public i ny database, og kopier. Da kopieres data med riktig user (ikke deg selv)
5. I dev: Vault: Sjekk at url til ny database stemmer med det som er satt i database-iac. Gå inn i Secrets kv/preprod/fss og velg veilarboppgave , q1. 
Hvis ikke VEILARBOPPGAVE_DB_URL er riktig, velg Edit secret og sett riktige verdier.
6. Oppdater koden med riktig databasenavn, se f.eks https://github.com/navikt/veilarboppgave/pull/160/files

