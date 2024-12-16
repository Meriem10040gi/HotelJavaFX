CREATE DATABASE hotelreservation;
--

CREATE TABLE `hotel` (
                         `idHotel` int(11) NOT NULL,
                         `name` varchar(40) NOT NULL,
                         `address` varchar(60) NOT NULL,
                         `description` varchar(200) NOT NULL,
                         `rating` int(11) NOT NULL,
                         `image` varchar(300) NULL,
                         `dateAjout` date NOT NULL,
                         `dateUpdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`idHotel`, `name`, `address`, `description`, `rating`, `image`, `dateAjout`, `dateUpdate`) VALUES
                                                                                                                    (1, 'Grand Palace', '123 Main St, Paris', 'Luxury 5-star hotel in the heart of Paris.', 5, '/Images/hotelimages/1.png', '2024-12-01', '2024-12-01'),
                                                                                                                    (2, 'Ocean View', '456 Beach Blvd, Miami', 'Beachfront hotel with stunning ocean views.', 4, '/Images/hotelimages/2.png', '2024-11-15', '2024-11-15'),
                                                                                                                    (3, 'Mountain Retreat', '789 Alpine Way, Denver', 'Cozy retreat nestled in the Rocky Mountains.', 5, '/Images/hotelimages/6.png', '2024-11-10', '2024-11-10'),
                                                                                                                    (4, 'Urban Haven', '321 Downtown Ave, New York', 'Modern hotel in the heart of the city.', 5, '/Images/hotelimages/4.png', '2024-11-20', '2024-11-20'),
                                                                                                                    (5, 'Desert Oasis', '654 Sahara Rd, Las Vegas', 'Luxury oasis in the desert.', 5, '/Images/hotelimages/5.png', '2024-11-18', '2024-11-18'),
                                                                                                                    (6, 'Rainforest Eco Lodge', '987 Jungle Path, Amazon', 'Sustainable lodge in the rainforest.', 4, '/Images/hotelimages/6.png', '2024-11-12', '2024-11-12'),
                                                                                                                    (7, 'Skyline Suites', '555 Cloud St, Tokyo', 'Exclusive suites with panoramic skyline views.', 5, '/Images/hotelimages/7.png', '2024-11-25', '2024-11-25'),
                                                                                                                    (8, 'Coastal Inn', '432 Harbor Rd, Sydney', 'Charming inn by the harbor.', 4, '/Images/hotelimages/8.png', '2024-11-22', '2024-11-22'),
                                                                                                                    (9, 'Historic Manor', '888 Old Town Rd, London', 'Elegant manor with a rich history.', 5, '/Images/hotelimages/9.png', '2024-11-30', '2024-11-30'),
                                                                                                                    (10, 'Safari Lodge', '777 Savannah Way, Kenya', 'Wildlife safari lodge with modern amenities.', 5, '/Images/hotelimages/1.png', '2024-11-29', '2024-11-29'),
                                                                                                                    (11, 'Northern Lights Hotel', '111 Aurora Blvd, Reykjavik', 'Hotel with a view of the Northern Lights.', 5, '/Images/hotelimages/1.png', '2024-12-02', '2024-12-02'),
                                                                                                                    (12, 'Mediterranean Escape', '222 Seaside Ave, Athens', 'Seaside hotel with Mediterranean charm.', 5, '/Images/hotelimages/2.png', '2024-11-28', '2024-11-28'),
                                                                                                                    (13, 'Art Deco Hotel', '333 Design St, Miami', 'Stylish Art Deco hotel in South Beach.', 4, '/Images/hotelimages/3.png', '2024-11-17', '2024-11-17'),
                                                                                                                    (14, 'Highland Inn', '444 Highland Rd, Edinburgh', 'Charming inn in the Scottish Highlands.', 4, '/Images/hotelimages/4.png', '2024-11-14', '2024-11-14'),
                                                                                                                    (15, 'Cityscape Hotel', '666 Skyline Ave, Chicago', 'Modern hotel with stunning city views.', 5, '/Images/hotelimages/5.png', '2024-11-13', '2024-11-13'),
                                                                                                                    (16, 'Lagoon Resort', '123 Island Way, Maldives', 'Luxury resort surrounded by crystal clear waters.', 5, '/Images/hotelimages/6.png', '2024-11-24', '2024-11-24'),
                                                                                                                    (17, 'Boutique Retreat', '234 Grove Lane, Tuscany', 'Elegant retreat in the heart of Tuscany.', 5, '/Images/hotelimages/7.png', '2024-11-23', '2024-11-23'),
                                                                                                                    (18, 'Royal Castle Hotel', '345 King St, Prague', 'Historic castle hotel fit for royalty.', 5, '/Images/hotelimages/8.png', '2024-11-21', '2024-11-21'),
                                                                                                                    (19, 'Vineyard Villas', '456 Wine Rd, Napa Valley', 'Luxurious villas surrounded by vineyards.', 5, '/Images/hotelimages/9.png', '2024-11-19', '2024-11-19'),
                                                                                                                    (20, 'Cultural Heritage Inn', '567 Heritage Rd, Kyoto', 'Charming inn showcasing local culture.', 4, '/Images/hotelimages/2.png', '2024-11-16', '2024-11-16');

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
                               `idReservation` int(11) NOT NULL,
                               `idUser` int(11) NOT NULL,
                               `dateDebut` date NOT NULL,
                               `dateFin` date NOT NULL,
                               `numberGuests` int(11) NOT NULL,
                               `numberAdults` int(11) NOT NULL,
                               `dateAjout` date NOT NULL,
                               `dateUpdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`idReservation`, `idUser`, `dateDebut`, `dateFin`, `numberGuests`, `numberAdults`, `dateAjout`, `dateUpdate`) VALUES
                                                                                                                                             (1, 101, '2024-12-10', '2024-12-15', 2, 2, '2024-12-02', '2024-12-02'),
                                                                                                                                             (2, 102, '2024-12-12', '2024-12-18', 4, 2, '2024-12-02', '2024-12-02'),
                                                                                                                                             (3, 103, '2024-12-20', '2024-12-25', 1, 1, '2024-12-02', '2024-12-02'),
                                                                                                                                             (4, 104, '2024-12-25', '2024-12-30', 3, 2, '2024-12-02', '2024-12-02'),
                                                                                                                                             (5, 105, '2024-12-15', '2024-12-20', 5, 3, '2024-12-02', '2024-12-02'),
                                                                                                                                             (6, 106, '2024-12-18', '2024-12-22', 2, 1, '2024-12-02', '2024-12-02'),
                                                                                                                                             (7, 107, '2024-12-22', '2024-12-28', 3, 2, '2024-12-02', '2024-12-02'),
                                                                                                                                             (8, 108, '2024-12-05', '2024-12-10', 2, 1, '2024-12-02', '2024-12-02'),
                                                                                                                                             (9, 109, '2024-12-01', '2024-12-04', 1, 1, '2024-12-02', '2024-12-02'),
                                                                                                                                             (10, 110, '2024-12-08', '2024-12-12', 4, 3, '2024-12-02', '2024-12-02');

-- --------------------------------------------------------

--
-- Table structure for table `reservationchambre`
--

CREATE TABLE `reservationchambre` (
                                      `idReservationChambre` int(11) NOT NULL,
                                      `idChambre` int(11) NOT NULL,
                                      `idReservation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservationchambre`
--

INSERT INTO `reservationchambre` (`idReservationChambre`, `idChambre`, `idReservation`) VALUES
                                                                                            (1, 5, 1),
                                                                                            (2, 6, 1),
                                                                                            (3, 3, 2),
                                                                                            (4, 4, 2),
                                                                                            (5, 5, 3),
                                                                                            (6, 3, 3),
                                                                                            (7, 6, 4),
                                                                                            (8, 4, 4),
                                                                                            (9, 5, 5),
                                                                                            (10, 6, 5),
                                                                                            (11, 3, 6),
                                                                                            (12, 4, 6),
                                                                                            (13, 5, 7),
                                                                                            (14, 6, 7),
                                                                                            (15, 3, 8),
                                                                                            (16, 4, 8),
                                                                                            (17, 5, 9),
                                                                                            (18, 6, 9),
                                                                                            (19, 3, 10),
                                                                                            (20, 4, 10);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
                        `idRoom` int(11) NOT NULL,
                        `idHotel` int(11) NOT NULL,
                        `numero` int(11) NOT NULL,
                        `typeRoom` varchar(40) NOT NULL,
                        `description` varchar(200) NOT NULL,
                        `prix` double NOT NULL,
                        `disponibilite` tinyint(1) NOT NULL,
                        `image` varchar(300) NOT NULL,
                        `dateAjout` date NOT NULL,
                        `dateUpdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`idRoom`, `idHotel`, `typeRoom`, `description`, `prix`, `disponibilite`, `image`, `dateAjout`, `dateUpdate`,`numero`) VALUES
                                                                                                                                              (1, 1, 'DOUBLE', 'Spacious double room with a comfortable bed.', 120, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',1),
                                                                                                                                              (2, 1, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 200, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',2),
                                                                                                                                              (3, 1, 'FAMILLE', 'Family room with multiple beds for a group stay.', 180, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',3),
                                                                                                                                              (4, 1, 'SINGLE', 'Cozy single room ideal for solo travelers.', 100, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',4),
                                                                                                                                              (5, 2, 'DOUBLE', 'Spacious double room with a comfortable bed.', 130, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (6, 2, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 210, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',6),
                                                                                                                                              (7, 2, 'FAMILLE', 'Family room with multiple beds for a group stay.', 190, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',7),
                                                                                                                                              (8, 2, 'SINGLE', 'Cozy single room ideal for solo travelers.', 110, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',8),
                                                                                                                                              (9, 1, 'DOUBLE', 'Spacious double room with a comfortable bed.', 120, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',9),
                                                                                                                                              (10, 1, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 200, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',10),
                                                                                                                                              (11, 1, 'FAMILLE', 'Family room with multiple beds for a group stay.', 180, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (12, 1, 'SINGLE', 'Cozy single room ideal for solo travelers.', 100, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (13, 2, 'DOUBLE', 'Spacious double room with a comfortable bed.', 130, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (14, 2, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 210, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (15, 2, 'FAMILLE', 'Family room with multiple beds for a group stay.', 190, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (16, 2, 'SINGLE', 'Cozy single room ideal for solo travelers.', 110, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (17, 1, 'DOUBLE', 'Spacious double room with a comfortable bed.', 120, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (18, 1, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 200, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (19, 1, 'FAMILLE', 'Family room with multiple beds for a group stay.', 180, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (20, 1, 'SINGLE', 'Cozy single room ideal for solo travelers.', 100, 1, '/Images/Rooms/1.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (21, 2, 'DOUBLE', 'Spacious double room with a comfortable bed.', 130, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (22, 2, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 210, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (23, 2, 'FAMILLE', 'Family room with multiple beds for a group stay.', 190, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (24, 2, 'SINGLE', 'Cozy single room ideal for solo travelers.', 110, 1, '/Images/Rooms/2.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (25, 3, 'DOUBLE', 'Spacious double room with a comfortable bed.', 125, 1, '/Images/Rooms/3.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (26, 3, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 205, 1, '/Images/Rooms/3.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (27, 3, 'FAMILLE', 'Family room with multiple beds for a group stay.', 185, 1, '/Images/Rooms/3.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (28, 3, 'SINGLE', 'Cozy single room ideal for solo travelers.', 105, 1, '/Images/Rooms/3.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (29, 4, 'DOUBLE', 'Spacious double room with a comfortable bed.', 135, 1, '/Images/Rooms/4.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (30, 4, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 215, 1, '/Images/Rooms/4.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (31, 4, 'FAMILLE', 'Family room with multiple beds for a group stay.', 195, 1, '/Images/Rooms/4.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (32, 4, 'SINGLE', 'Cozy single room ideal for solo travelers.', 115, 1, '/Images/Rooms/4.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (33, 5, 'DOUBLE', 'Spacious double room with a comfortable bed.', 140, 1, '/Images/Rooms/5.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (34, 5, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 220, 1, '/Images/Rooms/5.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (35, 5, 'FAMILLE', 'Family room with multiple beds for a group stay.', 200, 1, '/Images/Rooms/5.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (36, 5, 'SINGLE', 'Cozy single room ideal for solo travelers.', 120, 1, '/Images/Rooms/5.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (37, 6, 'DOUBLE', 'Spacious double room with a comfortable bed.', 145, 1, '/Images/Rooms/6.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (38, 6, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 225, 1, '/Images/Rooms/6.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (39, 6, 'FAMILLE', 'Family room with multiple beds for a group stay.', 205, 1, '/Images/Rooms/6.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (40, 6, 'SINGLE', 'Cozy single room ideal for solo travelers.', 125, 1, '/Images/Rooms/6.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (41, 7, 'DOUBLE', 'Spacious double room with a comfortable bed.', 150, 1, '/Images/Rooms/7.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (42, 7, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 230, 1, '/Images/Rooms/7.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (43, 7, 'FAMILLE', 'Family room with multiple beds for a group stay.', 210, 1, '/Images/Rooms/7.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (44, 7, 'SINGLE', 'Cozy single room ideal for solo travelers.', 130, 1, '/Images/Rooms/7.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (45, 8, 'DOUBLE', 'Spacious double room with a comfortable bed.', 155, 1, '/Images/Rooms/8.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (46, 8, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 235, 1, '/Images/Rooms/8.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (47, 8, 'FAMILLE', 'Family room with multiple beds for a group stay.', 215, 1, '/Images/Rooms/8.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (48, 8, 'SINGLE', 'Cozy single room ideal for solo travelers.', 135, 1, '/Images/Rooms/8.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (49, 9, 'DOUBLE', 'Spacious double room with a comfortable bed.', 160, 1, '/Images/Rooms/9.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (50, 9, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 240, 1, '/Images/Rooms/9.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (51, 9, 'FAMILLE', 'Family room with multiple beds for a group stay.', 220, 1, '/Images/Rooms/9.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (52, 9, 'SINGLE', 'Cozy single room ideal for solo travelers.', 140, 1, '/Images/Rooms/9.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (53, 10, 'DOUBLE', 'Spacious double room with a comfortable bed.', 165, 1, '/Images/Rooms/10.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (54, 10, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 245, 1, '/Images/Rooms/10.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (55, 10, 'FAMILLE', 'Family room with multiple beds for a group stay.', 225, 1, '/Images/Rooms/10.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (56, 10, 'SINGLE', 'Cozy single room ideal for solo travelers.', 145, 1, '/Images/Rooms/10.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (57, 11, 'DOUBLE', 'Spacious double room with a comfortable bed.', 170, 1, '/Images/Rooms/11.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (58, 11, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 250, 1, '/Images/Rooms/11.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (59, 11, 'FAMILLE', 'Family room with multiple beds for a group stay.', 230, 1, '/Images/Rooms/11.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (60, 11, 'SINGLE', 'Cozy single room ideal for solo travelers.', 150, 1, '/Images/Rooms/11.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (61, 12, 'DOUBLE', 'Spacious double room with a comfortable bed.', 175, 1, '/Images/Rooms/12.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (62, 12, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 255, 1, '/Images/Rooms/12.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (63, 12, 'FAMILLE', 'Family room with multiple beds for a group stay.', 235, 1, '/Images/Rooms/12.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (64, 12, 'SINGLE', 'Cozy single room ideal for solo travelers.', 155, 1, '/Images/Rooms/12.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (65, 13, 'DOUBLE', 'Spacious double room with a comfortable bed.', 180, 1, '/Images/Rooms/13.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (66, 13, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 260, 1, '/Images/Rooms/13.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (67, 13, 'FAMILLE', 'Family room with multiple beds for a group stay.', 240, 1, '/Images/Rooms/13.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (68, 13, 'SINGLE', 'Cozy single room ideal for solo travelers.', 160, 1, '/Images/Rooms/13.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (69, 14, 'DOUBLE', 'Spacious double room with a comfortable bed.', 185, 1, '/Images/Rooms/14.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (70, 14, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 265, 1, '/Images/Rooms/14.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (71, 14, 'FAMILLE', 'Family room with multiple beds for a group stay.', 245, 1, '/Images/Rooms/14.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (72, 14, 'SINGLE', 'Cozy single room ideal for solo travelers.', 165, 1, '/Images/Rooms/14.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (73, 15, 'DOUBLE', 'Spacious double room with a comfortable bed.', 190, 1, '/Images/Rooms/15.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (74, 15, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 270, 1, '/Images/Rooms/15.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (75, 15, 'FAMILLE', 'Family room with multiple beds for a group stay.', 250, 1, '/Images/Rooms/15.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (76, 15, 'SINGLE', 'Cozy single room ideal for solo travelers.', 170, 1, '/Images/Rooms/15.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (77, 16, 'DOUBLE', 'Spacious double room with a comfortable bed.', 195, 1, '/Images/Rooms/16.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (78, 16, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 275, 1, '/Images/Rooms/16.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (79, 16, 'FAMILLE', 'Family room with multiple beds for a group stay.', 255, 1, '/Images/Rooms/16.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (80, 16, 'SINGLE', 'Cozy single room ideal for solo travelers.', 175, 1, '/Images/Rooms/16.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (81, 17, 'DOUBLE', 'Spacious double room with a comfortable bed.', 200, 1, '/Images/Rooms/17.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (82, 17, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 280, 1, '/Images/Rooms/17.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (83, 17, 'FAMILLE', 'Family room with multiple beds for a group stay.', 260, 1, '/Images/Rooms/17.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (84, 17, 'SINGLE', 'Cozy single room ideal for solo travelers.', 180, 1, '/Images/Rooms/17.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (85, 18, 'DOUBLE', 'Spacious double room with a comfortable bed.', 205, 1, '/Images/Rooms/18.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (86, 18, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 285, 1, '/Images/Rooms/18.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (87, 18, 'FAMILLE', 'Family room with multiple beds for a group stay.', 265, 1, '/Images/Rooms/18.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (88, 18, 'SINGLE', 'Cozy single room ideal for solo travelers.', 185, 1, '/Images/Rooms/18.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (89, 19, 'DOUBLE', 'Spacious double room with a comfortable bed.', 210, 1, '/Images/Rooms/19.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (90, 19, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 290, 1, '/Images/Rooms/19.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (91, 19, 'FAMILLE', 'Family room with multiple beds for a group stay.', 270, 1, '/Images/Rooms/19.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (92, 19, 'SINGLE', 'Cozy single room ideal for solo travelers.', 190, 1, '/Images/Rooms/19.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (93, 20, 'DOUBLE', 'Spacious double room with a comfortable bed.', 215, 1, '/Images/Rooms/20.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (94, 20, 'DE_LUXE', 'Luxurious deluxe room with modern amenities.', 295, 1, '/Images/Rooms/20.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (95, 20, 'FAMILLE', 'Family room with multiple beds for a group stay.', 275, 1, '/Images/Rooms/20.png', '2024-12-02', '2024-12-02',5),
                                                                                                                                              (96, 20, 'SINGLE', 'Cozy single room ideal for solo travelers.', 200, 1, '/Images/Rooms/20.png', '2024-12-02', '2024-12-02',6);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
                        `idUser` int(11) NOT NULL,
                        `nom` varchar(100) DEFAULT NULL,
                        `prenom` varchar(100) DEFAULT NULL,
                        `address` varchar(255) DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `phone` varchar(20) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `role` varchar(20) DEFAULT NULL,
                        `dateAjout` date NOT NULL,
                        `dateUpdate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `nom`, `prenom`, `address`, `email`, `phone`, `password`, `role`, `dateAjout`, `dateUpdate`) VALUES
                                                                                                                               (101, 'Dupont', 'Pierre', '10 rue des Fleurs, Paris', 'pierre.dupont@example.com', '0123456789', 'password123', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (102, 'Lemoine', 'Sophie', '22 avenue de la République, Lyon', 'sophie.lemoine@example.com', '0234567890', 'password456', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (103, 'Martin', 'Claire', '15 rue des Lilas, Marseille', 'claire.martin@example.com', '0345678901', 'password789', 'ADMIN', '2024-12-01', '2024-12-01'),
                                                                                                                               (104, 'Benoit', 'Julien', '3 rue de la Paix, Bordeaux', 'julien.benoit@example.com', '0456789012', 'password101', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (105, 'Fournier', 'Lucie', '8 boulevard de Strasbourg, Lille', 'lucie.fournier@example.com', '0567890123', 'password112', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (106, 'Durand', 'Nicolas', '13 rue du Parc, Nantes', 'nicolas.durand@example.com', '0678901234', 'password113', 'ADMIN', '2024-12-01', '2024-12-01'),
                                                                                                                               (107, 'Blanc', 'Alice', '25 rue de la Liberté, Toulouse', 'alice.blanc@example.com', '0789012345', 'password114', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (108, 'Lemoine', 'Marc', '30 avenue de la Gare, Nice', 'marc.lemoine@example.com', '0890123456', 'password115', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (109, 'Roux', 'Emilie', '17 rue des Champs, Montpellier', 'emilie.roux@example.com', '0912345678', 'password116', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (110, 'Dubois', 'Pierre', '40 rue des Cèdres, Paris', 'pierre.dubois@example.com', '0123456789', 'password117', 'ADMIN', '2024-12-01', '2024-12-01'),
                                                                                                                               (111, 'Pires', 'Marc', '12 rue de la République, Lyon', 'marc.pires@example.com', '0234567890', 'password118', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (112, 'Giraud', 'Isabelle', '5 rue des Écoles, Marseille', 'isabelle.giraud@example.com', '0345678901', 'password119', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (113, 'Leclerc', 'Jean', '21 rue de la Liberté, Bordeaux', 'jean.leclerc@example.com', '0456789012', 'password120', 'ADMIN', '2024-12-01', '2024-12-01'),
                                                                                                                               (114, 'Lemoine', 'Sophie', '18 avenue des Champs, Lille', 'sophie.lemoine2@example.com', '0567890123', 'password121', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (115, 'Blanc', 'Paul', '6 boulevard Saint-Germain, Nantes', 'paul.blanc@example.com', '0678901234', 'password122', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (116, 'Gomez', 'Lucia', '14 rue des Jardins, Toulouse', 'lucia.gomez@example.com', '0789012345', 'password123', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (117, 'Leclerc', 'Julien', '9 rue des Cèdres, Paris', 'julien.leclerc@example.com', '0890123456', 'password124', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (118, 'Roux', 'Marc', '3 rue du Parc, Montpellier', 'marc.roux@example.com', '0912345678', 'password125', 'CLIENT', '2024-12-01', '2024-12-01'),
                                                                                                                               (119, 'Dupuis', 'Christine', '20 rue des Vignes, Nice', 'christine.dupuis@example.com', '0123456789', 'password126', 'ADMIN', '2024-12-01', '2024-12-01'),
                                                                                                                               (120, 'Moreau', 'Philippe', '7 avenue de la Gare, Lyon', 'philippe.moreau@example.com', '0234567890', 'password127', 'CLIENT', '2024-12-01', '2024-12-01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hotel`
--
ALTER TABLE `hotel`
    ADD PRIMARY KEY (`idHotel`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
    ADD PRIMARY KEY (`idReservation`),
  ADD KEY `fk_user` (`idUser`);

--
-- Indexes for table `reservationchambre`
--
ALTER TABLE `reservationchambre`
    ADD PRIMARY KEY (`idReservationChambre`),
  ADD KEY `fk_chambre` (`idChambre`),
  ADD KEY `fk_reservation` (`idReservation`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
    ADD PRIMARY KEY (`idRoom`),
  ADD KEY `fk_hotel` (`idHotel`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
    ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hotel`
--
ALTER TABLE `hotel`
    MODIFY `idHotel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
    MODIFY `idReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `reservationchambre`
--
ALTER TABLE `reservationchambre`
    MODIFY `idReservationChambre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
    MODIFY `idRoom` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
    MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=121;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
    ADD CONSTRAINT `fk_user` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `reservationchambre`
--
ALTER TABLE `reservationchambre`
    ADD CONSTRAINT `fk_chambre` FOREIGN KEY (`idChambre`) REFERENCES `room` (`idRoom`),
  ADD CONSTRAINT `fk_reservation` FOREIGN KEY (`idReservation`) REFERENCES `reservation` (`idReservation`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
    ADD CONSTRAINT `fk_hotel` FOREIGN KEY (`idHotel`) REFERENCES `hotel` (`idHotel`);
COMMIT;


