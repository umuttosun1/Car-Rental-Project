<#
.SYNOPSIS
    Creates the SwiftWheels PostgreSQL role + database (if missing) and loads schema and demo data.

.DESCRIPTION
    Nothing is dropped. Running it again only re-applies functions/procedures (CREATE OR REPLACE)
    and skips the demo data when the database already contains rows.

.EXAMPLE
    powershell -ExecutionPolicy Bypass -File scripts\setup-database.ps1
    powershell -ExecutionPolicy Bypass -File scripts\setup-database.ps1 -SuperUser postgres -NoSeed
#>
param(
    [string]$PgBin = "",
    [string]$DbHost = "localhost",
    [int]$Port = 5432,
    [string]$SuperUser = "postgres",
    [string]$Database = "swiftwheels",
    [string]$AppUser = "swiftwheels",
    [string]$AppPassword = "swiftwheels",
    [switch]$NoSeed
)

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
if (-not (Test-Path (Join-Path $root "database\schema.sql"))) { $root = $PSScriptRoot }  # zip layout

if (-not $PgBin) {
    $candidate = Get-ChildItem "C:\Program Files\PostgreSQL" -Directory -ErrorAction SilentlyContinue |
        Sort-Object { [int]($_.Name -replace '\D', '') } -Descending | Select-Object -First 1
    if ($candidate) { $PgBin = Join-Path $candidate.FullName "bin" }
}
$psql = if ($PgBin) { Join-Path $PgBin "psql.exe" } else { "psql" }
$pgIsReady = if ($PgBin) { Join-Path $PgBin "pg_isready.exe" } else { "pg_isready" }

& $pgIsReady -h $DbHost -p $Port | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "PostgreSQL $DbHost`:$Port cevap vermiyor." -ForegroundColor Red
    Write-Host "Servis kuruluysa: Start-Service postgresql-x64-<surum>"
    Write-Host "Servis yoksa:     & `"$PgBin\pg_ctl.exe`" -D `"<data klasoru>`" -l `"<data klasoru>\logfile`" start"
    exit 1
}

$env:PGCLIENTENCODING = "UTF8"
Write-Host "1/3 Rol ve veritabani kontrol ediliyor ($SuperUser olarak)..."
$escapedPassword = $AppPassword.Replace("'", "''")
@"
DO `$`$ BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '$AppUser') THEN
    CREATE ROLE "$AppUser" LOGIN PASSWORD '$escapedPassword';
  END IF;
END `$`$;
SELECT 'CREATE DATABASE "$Database" OWNER "$AppUser" ENCODING ''UTF8'''
WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = '$Database') \gexec
"@ | & $psql -h $DbHost -p $Port -U $SuperUser -d postgres -v ON_ERROR_STOP=1 -q
if ($LASTEXITCODE -ne 0) { throw "Rol/veritabani olusturulamadi." }

$env:PGPASSWORD = $AppPassword
Write-Host "2/3 Sema yukleniyor..."
& $psql -h $DbHost -p $Port -U $AppUser -d $Database -v ON_ERROR_STOP=1 -q -f (Join-Path $root "database\schema.sql")
if ($LASTEXITCODE -ne 0) { throw "schema.sql yuklenemedi." }

if (-not $NoSeed) {
    Write-Host "3/3 Ornek veriler yukleniyor..."
    & $psql -h $DbHost -p $Port -U $AppUser -d $Database -v ON_ERROR_STOP=1 -q -f (Join-Path $root "database\seed.sql")
    if ($LASTEXITCODE -ne 0) { throw "seed.sql yuklenemedi." }
}
Remove-Item Env:PGPASSWORD

Write-Host ""
Write-Host "Hazir! jdbc:postgresql://$DbHost`:$Port/$Database  (kullanici: $AppUser)" -ForegroundColor Green
if (-not $NoSeed) {
    Write-Host "Demo hesaplar: admin@swiftwheels.com / Admin123!   demo@swiftwheels.com / Demo123!"
}
