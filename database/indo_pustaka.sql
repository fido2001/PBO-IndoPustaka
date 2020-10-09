-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Jun 2020 pada 17.18
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `indo_pustaka`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_akun`
--

CREATE TABLE `data_akun` (
  `idAkun` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `alamat_lengkap` varchar(255) NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `no_ktp` varchar(16) NOT NULL,
  `email` varchar(50) NOT NULL,
  `no_hp` varchar(13) NOT NULL,
  `level` enum('petugas','masyarakat') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_akun`
--

INSERT INTO `data_akun` (`idAkun`, `username`, `password`, `nama_lengkap`, `alamat_lengkap`, `tempat_lahir`, `tanggal_lahir`, `no_ktp`, `email`, `no_hp`, `level`) VALUES
(1, 'fido', 'fb60a2ee571eb726444da7cc80b42377', 'Muhammad Firdaus', 'Genteng, Banyuwangi', 'Banyuwangi', '2015-05-08', '3510091710010003', 'firdauspriyono17@gmail.com', '085335483045', 'petugas'),
(2, 'aditya', 'E05B471AE7EA4EF02E7FFC2F975B5DB8', 'adittt', 'tes alamat', 'bwi', '2020-05-31', 'adit@yahoo.com', '3521304780009', '089765432123', 'masyarakat'),
(5, 'dandy', 'A3135307954F80B0C2661D265EC2DE8B', 'dandy satrio', 'Jember Utara', 'Jember', '2020-05-31', '4520093105160003', 'dandy@gmail.com', '085238885557', 'masyarakat');

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_buku`
--

CREATE TABLE `data_buku` (
  `kodeBuku` varchar(20) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `penulis` varchar(50) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `keterangan` enum('tersedia','kosong') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_buku`
--

INSERT INTO `data_buku` (`kodeBuku`, `judul`, `kategori`, `penulis`, `jumlah`, `keterangan`) VALUES
('PEMRO01', 'Pemrograman Berbasis Website', 'Pemrograman', 'Yudha Alif', 3, 'tersedia'),
('SEJ2019', 'Ken Angrok, Sang Pemimpin', 'Sejarah', 'Suwardono', 5, 'tersedia');

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_donasi`
--

CREATE TABLE `data_donasi` (
  `idDonasi` int(11) NOT NULL,
  `idAkun` int(11) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `penulis` varchar(100) NOT NULL,
  `jumlah` varchar(255) NOT NULL,
  `tanggal_donasi` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_donasi`
--

INSERT INTO `data_donasi` (`idDonasi`, `idAkun`, `judul`, `penulis`, `jumlah`, `tanggal_donasi`) VALUES
(1, 2, 'Pemrograman is Fun', 'Fido Firdaus', '2', '2020-06-16');

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_peminjaman`
--

CREATE TABLE `data_peminjaman` (
  `idPeminjaman` int(11) NOT NULL,
  `idAkun` int(11) NOT NULL,
  `kodeBuku` varchar(20) NOT NULL,
  `alamatPeminjaman` varchar(100) NOT NULL,
  `tanggalPinjam` date NOT NULL DEFAULT current_timestamp(),
  `tanggalKembali` date NOT NULL,
  `reqVerif` enum('diterima','ditolak','menunggu konfirmasi') NOT NULL,
  `caraPengambilan` enum('Ambil Sendiri','Antar Gojek') NOT NULL,
  `jumlah` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_peminjaman`
--

INSERT INTO `data_peminjaman` (`idPeminjaman`, `idAkun`, `kodeBuku`, `alamatPeminjaman`, `tanggalPinjam`, `tanggalKembali`, `reqVerif`, `caraPengambilan`, `jumlah`) VALUES
(2, 2, 'PEMRO01', 'Jember Selatan', '2020-06-17', '2020-06-24', 'menunggu konfirmasi', 'Ambil Sendiri', 1),
(3, 2, 'SEJ2019', 'Jalan Jawa 7 No. 104', '2020-06-18', '2020-06-25', 'diterima', 'Antar Gojek', 1),
(4, 5, 'PEMRO01', 'Jalan Mastrip No. 70', '2020-06-18', '2020-07-02', 'menunggu konfirmasi', 'Antar Gojek', 1);

--
-- Trigger `data_peminjaman`
--
DELIMITER $$
CREATE TRIGGER `stok_min` AFTER INSERT ON `data_peminjaman` FOR EACH ROW BEGIN
	UPDATE data_buku SET jumlah = jumlah - NEW.jumlah
    WHERE kodeBuku = NEW.kodeBuku;
    END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_pengembalian`
--

CREATE TABLE `data_pengembalian` (
  `idPengembalian` int(11) NOT NULL,
  `idPeminjaman` int(11) NOT NULL,
  `kodeBuku` varchar(50) NOT NULL,
  `statusPengembalian` enum('terlambat','tepat waktu','buku hilang') NOT NULL,
  `tanggalPengembalian` date NOT NULL DEFAULT current_timestamp(),
  `caraPengembalian` varchar(50) NOT NULL,
  `denda` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Trigger `data_pengembalian`
--
DELIMITER $$
CREATE TRIGGER `tambah_stok` AFTER INSERT ON `data_pengembalian` FOR EACH ROW BEGIN
	UPDATE data_buku SET jumlah = jumlah + 1
    WHERE kodeBuku = NEW.kodeBuku;
    END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_pengiriman`
--

CREATE TABLE `data_pengiriman` (
  `idPengiriman` int(11) NOT NULL,
  `idPeminjaman` int(11) NOT NULL,
  `statusPengiriman` enum('belum','sudah') NOT NULL,
  `tanggalKirim` date NOT NULL DEFAULT current_timestamp(),
  `tanggalTerima` date DEFAULT NULL,
  `konfirmasiPenerimaan` enum('belum','sudah') NOT NULL,
  `ongkir` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_pengiriman`
--

INSERT INTO `data_pengiriman` (`idPengiriman`, `idPeminjaman`, `statusPengiriman`, `tanggalKirim`, `tanggalTerima`, `konfirmasiPenerimaan`, `ongkir`) VALUES
(1, 3, 'sudah', '2020-06-18', '2020-06-18', 'sudah', '12000');

--
-- Trigger `data_pengiriman`
--
DELIMITER $$
CREATE TRIGGER `ganti_status` AFTER INSERT ON `data_pengiriman` FOR EACH ROW BEGIN
	UPDATE data_peminjaman SET reqVerif = 'diterima'
    WHERE idPeminjaman = NEW.idPeminjaman;
    END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `data_akun`
--
ALTER TABLE `data_akun`
  ADD PRIMARY KEY (`idAkun`);

--
-- Indeks untuk tabel `data_buku`
--
ALTER TABLE `data_buku`
  ADD PRIMARY KEY (`kodeBuku`);

--
-- Indeks untuk tabel `data_donasi`
--
ALTER TABLE `data_donasi`
  ADD PRIMARY KEY (`idDonasi`),
  ADD KEY `fk_akun2` (`idAkun`);

--
-- Indeks untuk tabel `data_peminjaman`
--
ALTER TABLE `data_peminjaman`
  ADD PRIMARY KEY (`idPeminjaman`),
  ADD KEY `fk_akun` (`idAkun`),
  ADD KEY `fk_kode_pinjam` (`kodeBuku`);

--
-- Indeks untuk tabel `data_pengembalian`
--
ALTER TABLE `data_pengembalian`
  ADD PRIMARY KEY (`idPengembalian`),
  ADD KEY `fk_peminjaman` (`idPeminjaman`),
  ADD KEY `fk_kodebuku` (`kodeBuku`);

--
-- Indeks untuk tabel `data_pengiriman`
--
ALTER TABLE `data_pengiriman`
  ADD PRIMARY KEY (`idPengiriman`),
  ADD KEY `fk_peminjaman2` (`idPeminjaman`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `data_akun`
--
ALTER TABLE `data_akun`
  MODIFY `idAkun` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `data_donasi`
--
ALTER TABLE `data_donasi`
  MODIFY `idDonasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `data_peminjaman`
--
ALTER TABLE `data_peminjaman`
  MODIFY `idPeminjaman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `data_pengembalian`
--
ALTER TABLE `data_pengembalian`
  MODIFY `idPengembalian` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `data_pengiriman`
--
ALTER TABLE `data_pengiriman`
  MODIFY `idPengiriman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `data_donasi`
--
ALTER TABLE `data_donasi`
  ADD CONSTRAINT `fk_akun2` FOREIGN KEY (`idAkun`) REFERENCES `data_akun` (`idAkun`);

--
-- Ketidakleluasaan untuk tabel `data_peminjaman`
--
ALTER TABLE `data_peminjaman`
  ADD CONSTRAINT `fk_akun` FOREIGN KEY (`idAkun`) REFERENCES `data_akun` (`idAkun`),
  ADD CONSTRAINT `fk_kode_pinjam` FOREIGN KEY (`kodeBuku`) REFERENCES `data_buku` (`kodeBuku`);

--
-- Ketidakleluasaan untuk tabel `data_pengembalian`
--
ALTER TABLE `data_pengembalian`
  ADD CONSTRAINT `fk_kodebuku` FOREIGN KEY (`kodeBuku`) REFERENCES `data_buku` (`kodeBuku`),
  ADD CONSTRAINT `fk_peminjaman` FOREIGN KEY (`idPeminjaman`) REFERENCES `data_peminjaman` (`idPeminjaman`);

--
-- Ketidakleluasaan untuk tabel `data_pengiriman`
--
ALTER TABLE `data_pengiriman`
  ADD CONSTRAINT `fk_peminjaman2` FOREIGN KEY (`idPeminjaman`) REFERENCES `data_peminjaman` (`idPeminjaman`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
