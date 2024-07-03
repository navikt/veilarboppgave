grant connect on database "veilarboppgave-pg15-dev" to "veilarboppgave-pg15-dev-admin";

grant connect on database "veilarboppgave-pg15-dev" to "veilarboppgave-pg15-dev-user";

grant connect on database "veilarboppgave-pg15-dev" to "veilarboppgave-pg15-dev-readonly";

create table public.flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table public.flyway_schema_history
    owner to "veilarboppgave-pg15-dev-admin";

create index flyway_schema_history_s_idx
    on public.flyway_schema_history (success);

grant delete, insert, select, truncate, update on public.flyway_schema_history to "veilarboppgave-pg15-dev-user";

grant select on public.flyway_schema_history to "veilarboppgave-pg15-dev-readonly";

create table public.oppgavehistorikk
(
    id                  bigserial
        primary key,
    aktoerid            varchar(255) not null,
    gsak_id             varchar(255) not null,
    tema                varchar(255) not null,
    type                varchar(255) not null,
    opprettet_av_ident  varchar(20)  not null,
    opprettet_tidspunkt timestamp    not null
);

alter table public.oppgavehistorikk
    owner to "veilarboppgave-pg15-dev-admin";

create sequence public.oppgavehistorikk_id_seq;

alter sequence public.oppgavehistorikk_id_seq owner to "veilarboppgave-pg15-dev-admin";

alter sequence public.oppgavehistorikk_id_seq owned by public.oppgavehistorikk.id;

grant select, usage on sequence public.oppgavehistorikk_id_seq to "veilarboppgave-pg15-dev-readonly";


create index oppgavehistorikk_aktoerid_idx
    on public.oppgavehistorikk (aktoerid);

grant delete, insert, select, truncate, update on public.oppgavehistorikk to "veilarboppgave-pg15-dev-user";

grant select on public.oppgavehistorikk to "veilarboppgave-pg15-dev-readonly";
