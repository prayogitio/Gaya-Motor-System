CREATE TABLE pembukuan (
	pid SERIAL PRIMARY KEY,
	tanggal DATE,
	keterangan TEXT,
	debit INTEGER,
	kredit INTEGER,
	saldo INTEGER
);


DROP TABLE IF EXISTS daftar_motor;
CREATE TABLE daftar_motor (
  did SERIAL PRIMARY KEY,
  tanggal_beli DATE,
  plat VARCHAR(50),
  merk VARCHAR(50),
  tipe VARCHAR(50),
  tahun INTEGER,
  modal INTEGER,
  status INTEGER
);
