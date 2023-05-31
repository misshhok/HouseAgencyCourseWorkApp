create table if not exists public.user
(
    id                bigserial   not null
        primary key,
    login              varchar(50) not null
        constraint ux_user_login
            unique,
    password_hash      varchar(60) not null,
    first_name         varchar(50),
    last_name          varchar(50),
    email              varchar(191)
        constraint ux_user_email
            unique,
    image_url          varchar(256),
    activated          boolean     not null,
    lang_key           varchar(10),
    activation_key     varchar(20),
    reset_key          varchar(20),
    created_by         varchar(50),
    created_date       timestamp,
    reset_date         timestamp,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

create table if not exists public.authority
(
    name varchar(50) not null
        primary key
);

create table if not exists public.user_authority
(
    user_id        bigserial   not null
        constraint fk_user_id
            references public.user(id),
    authority_name varchar(50) not null
        constraint fk_authority_name
            references public.authority(name),
    primary key (user_id, authority_name)
);

create table if not exists public.persistent_token
(
    series      varchar(20) not null
        primary key,
    user_id     bigint
        constraint fk_user_persistent_token
            references public.user(id),
    token_value varchar(20) not null,
    token_date  date,
    ip_address  varchar(39),
    user_agent  varchar(255)
);

create table if not exists public.cities
(
    id    bigserial not null
        primary key,
    title varchar(255)
);

create table if not exists public.streets_of_cities
(
    id      bigserial not null
        primary key,
    title   varchar(255),
    city_id bigint
        constraint fk_streets_of_cities__city_id
            references public.cities(id)
);

create table if not exists public.addresses
(
    id                bigserial not null
        primary key,
    house_number      integer,
    street_of_city_id bigint
        constraint fk_addresses__street_of_city_id
            references public.streets_of_cities(id)
);

create table if not exists public.types_of_residental_estate
(
    id    bigserial not null
        primary key,
    title varchar(255)
);

create table if not exists public.statuses_of_residental_estate
(
    id    bigserial not null
        primary key,
    title varchar(255)
);

create table if not exists public.purposes_of_non_residental_estate
(
    id    bigserial not null
        primary key,
    title varchar(255)
);

create table if not exists public.building_type_of_non_residental_estate
(
    id    bigserial not null
        primary key,
    title varchar(255)
);

create table if not exists public.types_of_commercial_events
(
    id          bigserial not null
        primary key,
    title       varchar(255),
    description varchar(255),
    estate_type varchar(255)
);

create table if not exists public.clients
(
    id           bigserial not null
        primary key,
    name         varchar(255),
    surename     varchar(255),
    patronymic   varchar(255),
    phone_number varchar(255)
);

create table if not exists public.residental_estates
(
    id                             bigserial not null
        primary key,
    living_space                   double precision,
    cadastral_number               bigint,
    commissioning_date             date,
    number_of_rooms                integer,
    furnish_type                   varchar(255),
    has_balcony                    boolean,
    bathroom_type                  varchar(255),
    ceiling_height                 double precision,
    price                          numeric(21, 2),
    address_id                     bigint
        constraint fk_residental_estates__address_id
            references public.addresses(id),
    type_of_residental_estate_id   bigint
        constraint fk_residental_estates__type_of_residental_estate_id
            references public.types_of_residental_estate(id),
    status_of_residental_estate_id bigint
        constraint fk_residental_estates__status_of_residental_estate_id
            references public.statuses_of_residental_estate(id)
);

create table if not exists public.non_residental_estates
(
    id                                        bigserial not null
        primary key,
    price                                     numeric(21, 2),
    commissioning_date                        date,
    cadastral_number                          bigint,
    total_space                               double precision,
    purpose_of_non_residental_estate_id       bigint
        constraint fk_non_residental_estates__purpose_of_non_residental_id
            references public.purposes_of_non_residental_estate(id),
    building_type_of_non_residental_estate_id bigint
        constraint fk_non_residental_estates__building_type_of_non_residental_id
            references public.building_type_of_non_residental_estate(id),
    address_id                                bigint
        constraint fk_non_residental_estates__address_id
            references public.addresses(id)
);

create table if not exists public.comercial_events_of_non_residental_estate
(
    id                          bigserial not null
        primary key,
    agent_notes                 varchar(255),
    date_of_event               date,
    type_of_commercial_event_id bigint
        constraint fk_comercial_events_of_non_resi__type_of_commercial_event_id
            references public.types_of_commercial_events(id),
    non_residental_estate_id    bigint
        constraint fk_comercial_events_of_non_resi__non_residental_estate_id
            references public.non_residental_estates(id),
    client_id                   bigint
        constraint fk_comercial_events_of_non_residental_estate__client_id
            references public.clients(id)
);

create table if not exists public.comercial_events_of_residental_estate
(
    id                          bigserial not null
        primary key,
    agent_notes                 varchar(255),
    date_of_event               date,
    type_of_commercial_event_id bigint
        constraint fk_comercial_events_of_resident__type_of_commercial_event_id
            references public.types_of_commercial_events(id),
    client_id                   bigint
        constraint fk_comercial_events_of_residental_estate__client_id
            references public.clients(id),
    residental_estate_id        bigint
        constraint fk_comercial_events_of_resident__residental_estate_id
            references public.residental_estates(id)
);

