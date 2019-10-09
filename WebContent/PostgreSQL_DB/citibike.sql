CREATE DATABASE IF NOT EXISTS CITIBIKE;

USE CITIBIKE;

CREATE EXTENSION postgis;

CREATE TABLE IF NOT EXISTS BIKE
(
    universalID text primary key,
    id text
);

CREATE TABLE IF NOT EXISTS CLIENT
(
  universalID text primary key,
  type text,
  yearOfBirth text,
  gender text
);

CREATE TABLE IF NOT EXISTS STATION
(
  universalID text primary key,
  id text,
  name text,
  lat double precision,
  long double precision
);

CREATE TABLE IF NOT EXISTS TRIP
(
  universalID text primary key,
  duration integer,
  startTime text,
  stopTime text,
  universalIDstartStation text REFERENCES STATION(universalID),
  universalIDstopStation text REFERENCES STATION(universalID),
  universalIDBike text REFERENCES BIKE(universalID),
  universalIDUser text REFERENCES CLIENT(universalID)
);
