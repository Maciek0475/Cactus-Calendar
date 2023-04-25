-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 25 Kwi 2023, 19:07
-- Wersja serwera: 10.4.22-MariaDB
-- Wersja PHP: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `web-app`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `cities`
--

CREATE TABLE `cities` (
  `id` int(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `cities`
--

INSERT INTO `cities` (`id`, `name`, `lat`, `lon`) VALUES
(1, 'Białystok', 53.139, 23.159),
(2, 'Bydgoszcz', 53.12, 18.01),
(3, 'Gdańsk', 54.36, 18.639),
(4, 'Gorzów Wielkopolski', 52.74, 15.23),
(5, 'Katowice', 50.259, 19.02),
(6, 'Kielce', 50.889, 20.649),
(7, 'Kraków', 50.06, 19.959),
(8, 'Lublin', 51.24, 22.57),
(9, 'Łódź', 51.77, 19.459),
(10, 'Olsztyn', 53.779, 20.489),
(11, 'Opole', 50.679, 17.94),
(12, 'Poznań', 52.399, 16.9),
(13, 'Rzeszów', 50.049, 21.999),
(14, 'Szczecin', 53.43, 14.529),
(15, 'Toruń', 53.02, 18.609),
(16, 'Warszawa', 52.259, 21.02),
(17, 'Wrocław', 51.11, 17.03),
(18, 'Zielona Góra', 51.94, 15.489);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `plans`
--

CREATE TABLE `plans` (
  `id` int(10) NOT NULL,
  `date` date NOT NULL,
  `note` varchar(256) NOT NULL,
  `success_propability` double NOT NULL,
  `user_id` int(10) NOT NULL,
  `city_id` int(10) NOT NULL,
  `is_done` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(10) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(45) NOT NULL,
  `city_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `plans`
--
ALTER TABLE `plans`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user-id` (`user_id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `cities`
--
ALTER TABLE `cities`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT dla tabeli `plans`
--
ALTER TABLE `plans`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
