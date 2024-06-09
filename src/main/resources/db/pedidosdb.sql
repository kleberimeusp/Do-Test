CREATE DATABASE `pedidosdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
-- pedidosdb.pedido definition

CREATE TABLE `pedido` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo_cliente` bigint(20) NOT NULL,
  `data_cadastro` datetime(6) DEFAULT NULL,
  `nome_produto` varchar(255) NOT NULL,
  `numero_controle` varchar(255) NOT NULL,
  `quantidade` int(11) DEFAULT NULL,
  `valor_total` decimal(38,2) NOT NULL,
  `valor_unitario` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_numero_controle` (`numero_controle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET time_zone = 'America/Sao_Paulo'; -- Substitua 'America/Sao_Paulo' pelo fuso horário correto