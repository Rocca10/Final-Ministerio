-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-07-2024 a las 06:17:15
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ministerio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comandas`
--

CREATE TABLE `comandas` (
  `id` bigint(20) NOT NULL,
  `mesa` int(11) NOT NULL,
  `vigilante_codigo` varchar(30) NOT NULL,
  `lista` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `comandas`
--

INSERT INTO `comandas` (`id`, `mesa`, `vigilante_codigo`, `lista`) VALUES
(9, 12, 'asd', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `items`
--

CREATE TABLE `items` (
  `tipo` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `precio` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `items`
--

INSERT INTO `items` (`tipo`, `id`, `descripcion`, `precio`) VALUES
('Bebida', 11, 'Vodka', 1234),
('Bebida', 12, 'Mila con papas', 124553),
('Preparacion', 13, 'Coca', 5312);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `items_comanda`
--

CREATE TABLE `items_comanda` (
  `comanda_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `items_comanda`
--

INSERT INTO `items_comanda` (`comanda_id`, `item_id`) VALUES
(9, 11),
(9, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `rol` varchar(31) NOT NULL,
  `codigo` varchar(30) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`rol`, `codigo`, `nombre`, `password`) VALUES
('Admin', 'admin', 'NewAdmin', 'admin'),
('Vigilante', 'asd', 'asd', 'asd'),
('Vigilante', 'fas', 'FDSA', 'fas'),
('Investigador', 'qwe', 'qwe', 'qwe');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comandas`
--
ALTER TABLE `comandas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9eg3nuemx0rdgwpu69k46t3r4` (`vigilante_codigo`);

--
-- Indices de la tabla `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_dvinxd4vwacy0f4ggrhh0ncv` (`descripcion`);

--
-- Indices de la tabla `items_comanda`
--
ALTER TABLE `items_comanda`
  ADD KEY `FKi8l0ig9vn9egmc2327gxexo16` (`item_id`),
  ADD KEY `FK3akpg3anfgfg4tjtlswmcnugc` (`comanda_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comandas`
--
ALTER TABLE `comandas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `items`
--
ALTER TABLE `items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comandas`
--
ALTER TABLE `comandas`
  ADD CONSTRAINT `FK9eg3nuemx0rdgwpu69k46t3r4` FOREIGN KEY (`vigilante_codigo`) REFERENCES `usuarios` (`codigo`);

--
-- Filtros para la tabla `items_comanda`
--
ALTER TABLE `items_comanda`
  ADD CONSTRAINT `FK3akpg3anfgfg4tjtlswmcnugc` FOREIGN KEY (`comanda_id`) REFERENCES `comandas` (`id`),
  ADD CONSTRAINT `FKi8l0ig9vn9egmc2327gxexo16` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
