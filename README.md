# CISQ1: Lingo Trainer
[![Java CI](https://github.com/kaasbroodju/cisq1-lingo/actions/workflows/build.yml/badge.svg)](https://github.com/kaasbroodju/cisq1-lingo/actions/workflows/build.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=kaasbroodju_cisq1-lingo&metric=coverage)](https://sonarcloud.io/dashboard?id=kaasbroodju_cisq1-lingo)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=kaasbroodju_cisq1-lingo&metric=ncloc)](https://sonarcloud.io/dashboard?id=kaasbroodju_cisq1-lingo)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=kaasbroodju_cisq1-lingo&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=kaasbroodju_cisq1-lingo)

# Vulnerability Analysis
## A3:2017 Sensitive Data Exposure
### Description
In de praktijk word vaak gewerkt met gevoelige data. Als deze data niet geincrypt is of word zomaar verstuurt, kan de schade snel uit de hand lopen.
### Risk
Zeer laag risico, In deze Lingo applicatie werken we niet met NAW of persoons-identificerend gegevens.
### Counter-measures
Geen.

## A9:2017 Using Components with known issues
### Description
Components kunnen niet meer bijgewerkt worden of zijn kompleet vergeten. Als er een exploit is gevonden komt de patch laat of niet. Components draaien ook met deze privileges als je applicatie.
### Risk
In de dependency check is naar voren gekomen dat tomcat een exploid heeft
### Counter-measures
Geen.

## A10:2017 Insufficient Logging
### Description
Soms wordt en niet in de gaten gehouden hoe een app draait. Als je dat niet bijhoudt kunnen hack poging niet gedectecteerd worden. Bij een systeem wat logd bij belangrijke events kan er sneller en beter een breach gedetecteerd worden.
### Risk
Er word weining geleogd en als er iets word gelogd is het in de terminal.
### Counter-measures
Mislukte responses van controllers worden nu gelogd.
