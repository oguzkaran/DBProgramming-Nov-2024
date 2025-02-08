create database dpn24_chess_tournament_db;

use dpn24_chess_tournament_db;

create table rankings (
    ranking_id int primary key identity(1, 1),
    description nvarchar(250)
);

create table results (
    result_id int primary key identity(1, 1),
    description nvarchar(250)
);

create table contact_informations (
    contact_information_id int primary key identity(1, 1),
    address nvarchar(250),
    phone char(11),
    email nvarchar(100)
);

create table clubs (
    club_id int primary key identity(1, 1),
    name nvarchar(100),
    address nvarchar(250),
    details nvarchar(max)
);

create table sponsors (
    sponsor_id int primary key identity(1, 1),
    name nvarchar(75),
    phone char(11),
    details nvarchar(max)
);

create table organizers (
    organizer_id int primary key identity(1, 1),
    club_id int foreign key references clubs(club_id),
    name nvarchar(100),
    details nvarchar(max)
);

create table tournaments (
    tournament_id int primary key identity(1, 1),
    organizer_id int foreign key references organizers(organizer_id),
    start_date date,
    end_date date,
    name nvarchar(100),
    details nvarchar(max)
);

create table players (
    player_id int primary key identity(1, 1),
    club_id int foreign key references clubs(club_id),
    ranking_id int foreign key references rankings(ranking_id),
    first_name nvarchar(50),
    last_name nvarchar(50),
    contact_information_id int foreign key references contact_informations(contact_information_id) unique,
    details nvarchar(max)
);

create table player_participations (
    player_participation_id int primary key identity(1, 1),
    player_id int foreign key references players(player_id),
    tournament_id int foreign key references tournaments(tournament_id),
    final_result int
);

create table matches (
    match_id int primary key identity(1, 1),
    tournament_id int foreign key references tournaments(tournament_id),
    player1_id int foreign key references players(player_id),
    player2_id int foreign key references players(player_id),
    match_start_datetime datetime,
    result_id int foreign key references results(result_id),
    match_end_datetime datetime
);

create table tournament_sponsors (
    tournament_sponsor_id int primary key identity(1, 1),
    tournament_id int foreign key references tournaments(tournament_id),
    sponsor_id int foreign key references sponsors(sponsor_id)
);
