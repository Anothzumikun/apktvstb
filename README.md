# TV Player - APK untuk STB Pribadi

App pemutar TV berbasis playlist M3U/IPTV. Kamu isi sendiri URL playlist-nya (dari sumber yang legal buat kamu pakai), app ini cuma jadi "pemutar".

## Fitur
- Baca playlist format M3U/M3U8 standar (#EXTINF dengan tvg-logo, group-title, nama channel)
- List channel dengan logo & kategori
- Player pakai ExoPlayer (Media3) — support HLS streaming
- Ganti URL playlist kapan saja lewat menu Pengaturan (tombol ikon di kanan atas)
- UI gelap, remote-friendly (bisa dinavigasi pakai D-pad STB)

## Cara Build APK dari HP (tanpa PC, tanpa Android Studio)

Karena kamu kerja dari HP, cara paling gampang adalah pakai **GitHub Actions** — build APK-nya jalan otomatis di server GitHub, kamu tinggal download hasilnya.

### Langkah-langkah:

1. **Buat repository baru di GitHub** (lewat app GitHub atau browser HP)
   - Buka github.com > New repository > kasih nama, misal `tv-player`

2. **Upload semua file project ini** ke repo tersebut
   - Bisa lewat GitHub Codespaces (seperti biasa kamu pakai), atau
   - Upload manual via web GitHub (drag & drop folder — browser HP kadang bisa, kalau tidak bisa pakai Codespaces)
   - Pastikan struktur foldernya tetap sama persis (termasuk folder `.github/workflows/`)

3. **Push/commit ke branch `main`**
   - Begitu ke-push, GitHub Actions otomatis jalan build APK

4. **Cek hasil build**
   - Buka tab **Actions** di repo GitHub kamu
   - Klik run yang lagi jalan/selesai (nama "Build APK")
   - Scroll ke bawah, ada bagian **Artifacts** > `tv-player-debug-apk`
   - Download file itu (isinya `app-debug.apk`), lalu install di STB kamu

5. **Install APK di STB**
   - Pindahkan APK ke STB (lewat USB, file manager, atau share link Google Drive)
   - Aktifkan "Install dari sumber tidak dikenal" kalau diminta
   - Install seperti biasa

### Update APK selanjutnya
Setiap kali kamu edit kode dan push ulang ke GitHub, Actions otomatis build ulang. Tinggal download APK terbaru dari tab Actions.

## Cara Pakai App
1. Buka app, pertama kali akan kosong
2. Tap ikon **Pengaturan** (kanan atas)
3. Masukkan URL playlist M3U kamu (contoh format: `https://contohsitus.com/playlist.m3u`)
4. Tap **Simpan**
5. Balik ke halaman utama, channel akan otomatis dimuat
6. Pilih channel untuk mulai nonton

## Catatan Penting
- App ini murni **pemutar (player)**, tidak menyediakan channel/link streaming apapun secara default.
- Legalitas konten yang diputar sepenuhnya tergantung dari sumber playlist M3U yang kamu masukkan sendiri — gunakan sumber yang memang berhak/legal kamu akses (misal langganan IPTV resmi, server streaming pribadi, dsb).
