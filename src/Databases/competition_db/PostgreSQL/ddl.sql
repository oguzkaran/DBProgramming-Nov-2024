
drop table if exists options;
drop table if exists questions;
drop table if exists levels;

create table levels (
    level_id serial primary key,
    description varchar(100) not null
);

insert into levels (description) values ('Easy'), ('Medium'), ('Hard'), ('Expert');

create table questions (
    question_id bigserial primary key,
    description text not null,
    level_id int references levels(level_id) not null
);

create table options (
    option_id bigserial primary key ,
    description text not null,
    question_id bigint references questions(question_id) not null,
    is_answer bool default(false) not null
);

create or replace function get_random_question_id()
    returns bigint
as $$
begin
    return (select question_id from questions order by random() limit 1);
end
$$ language plpgsql;

create or replace function get_random_question_id_by_level_id(int)
    returns bigint
as $$
begin
    return (select question_id from questions where level_id = $1 order by random() limit 1);
end
$$ language plpgsql;

create or replace function get_question_text(bigint)
    returns text
as $$
begin
    return (select description from questions where question_id = $1);
end
$$ language plpgsql;

create or replace function get_options(bigint)
    returns table (description text, is_answer bool)
as $$
begin
    return query (select o.description, o.is_answer from options o where question_id = $1);
end;
$$ language plpgsql;


create or replace function get_answers(bigint)
    returns table (description text)
as $$
begin
    return query (select o.description from options o where question_id = $1 and is_answer = true);
end;
$$ language plpgsql;