<h1 align="center">
  <br>
  <a href="#"><img src="https://github.com/user-attachments/assets/090cfffa-52e7-4c88-a4c4-c064835006a2" alt="UninaDelivery" width="200"></a>
  <br>
  UninaDelivery
  <br>
</h1>

<h4 align="center">Sistema di gestione della logistica per le spedizioni di merci, sviluppato in JavaFX con PostgreSQL.</h4>

<p align="center">
  <img src="https://img.shields.io/github/contributors/Elisa2009Ferrara/UninaDelivery" alt="GitHub contributors">
  <img src="https://img.shields.io/github/license/Elisa2009Ferrara/UninaDelivery" alt="GitHub License">
  <img src="https://img.shields.io/github/commit-activity/t/Elisa2009Ferrara/UninaDelivery/main" alt="GitHub commit activity">
</p>

<p align="center">
  <a href="#descrizione">Descrizione</a> •
  <a href="#installazione">Installazione</a> •
  <a href="#uso">Uso</a> •
  <a href="#contributi">Contributi</a> •
  <a href="#licenza">Licenza</a>
</p>
# UninaDelivery

## Description
UninaDelivery è un sistema avanzato per la gestione logistica delle spedizioni di merci. L'applicazione permette agli operatori di pianificare, monitorare e ottimizzare il processo di spedizione in base agli ordini dei clienti, tenendo conto di variabili come disponibilità dei prodotti, mezzi di trasporto e corrieri disponibili.

Il sistema è progettato con un'interfaccia grafica intuitiva realizzata in JavaFX e sfrutta una base di dati relazionale per gestire gli ordini e le disponibilità.

## Features
Il sistema offre diverse funzionalità chiave:
- Login e registrazione per operatori
- Visualizzazione e filtro degli ordini per utente o intervallo di tempo
- Creazione di nuove spedizioni, assegnando ordini, mezzi di trasporto e corrieri
- Generazione di report statistici mensili
- Gestione delle merci
- Pianificazione di spedizioni ricorrenti (settimanali, mensili o annuali)

## Installation
### 1. Configurazione del Database
Questo progetto utilizza **PostgreSQL** come database relazionale. Nella repository è disponibile un backup del database per facilitarne la configurazione.

#### Passaggi per ripristinare il database:
1. Installare PostgreSQL, se non già presente.
2. Creare un database con il nome **UninaDelivery**.
3. Ripristinare il backup con il comando:
   ```sh
   psql -U postgres -d UninaDelivery -f backup.sql
   ```

### 2. Credenziali del Database
Le credenziali di accesso al database sono state fornite via email.

### 3. Avvio del Progetto
Il progetto utilizza **JavaFX** per l'interfaccia grafica. Per eseguire l'applicazione:
```sh
mvn clean install
java -jar target/uninaDelivery.jar
```

## Licenza
Questo progetto è distribuito sotto la licenza MIT.
