flowchart TD
start((👩‍💼 Starter
klagebehandling)) --> formkrav[✏️ Vurderer formkrav]
formkrav -->|Ikke oppfylt ❌ | brev[Send avvisningsbrev ✉️]
brev --> fullført[Fullført ✔︎]
formkrav -->|Oppfylt ✅ | vedtak[Vurder vedtak]
vedtak -->|Vedtaket omgjøres| ferdigstill[Ferdigstill klagen ✔︎]
vedtak -->|Vedtaket opprettholdes| kaBrev[Send brev til bruker om at klagen er sendt til KA ✉️]
kaBrev -.-> kaVurderer[/KA vurderer klagen... ⏳/]
kaVurderer --> opprettSak[Opprettes _VurderKonsekvensForYtelse_-oppgave i TS-sak]
ferdigstill -.-> revurdering[\Saksbehandler må manuelt opprette revurdering i TS-sak\]
