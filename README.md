<h1 align="center">
  <br>
  <a href="#"><img src="https://github.com/Elisa2009Ferrara/UninaDelivery/blob/main/src/main/resources/images/UninaIconaNuova.png" width="250"></a>
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
  <a href="#features">Features</a> •
  <a href="#built-with">Built with</a> •
  <a href="#installazione">Installazione</a> •
  <a href="#contributi">Contributi</a> •
  <a href="#licenza">Licenza</a>
</p>

## Descrizione
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

## Built with
Per questo progetto sono stati realizzati il database e l'applicativo Java con GUI in JavaFX.

* [![PostgreSQL][PostgreSQL-logo]][PostgreSQL-url]
* [![IntelliJ IDEA][IntelliJ-logo]][IntelliJ-url]
* [![Java][Java-logo]][Java-url]
* [![JavaFX][JavaFX-logo]][JavaFX-url]
* [![CSS][CSS-logo]][CSS-url]

[PostgreSQL-logo]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/

[IntelliJ-logo]: https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white
[IntelliJ-url]: https://www.jetbrains.com/idea/

[Java-logo]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/

[JavaFX-logo]: https://img.shields.io/badge/JavaFX-43853D?style=for-the-badge&logo=java&logoColor=white
[JavaFX-url]: https://openjfx.io/

[CSS-logo]: https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white
[CSS-url]: https://developer.mozilla.org/en-US/docs/Web/CSS

## Installazione
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
## Contributi
<p align="center">
  <table>
    <tr>
      <td align="center">
        <img src="https://avatars.githubusercontent.com/u/147719961?v=4" width="100px;" alt="Federica Fortino"/><br />
        <sub><b>Federica Fortino</b></sub>
      </td>
      <td align="center">
        <img src="https://avatars.githubusercontent.com/u/147701593?v=4" width="100px;" alt="Francesco Guzzetta"/><br />
        <sub><b>Francesco Guzzetta</b></sub>
      </td>
      <td align="center">
        <img src="https://avatars.githubusercontent.com/u/148976556?v=4" width="100px;" alt="Elisa Ferrara"/><br />
        <sub><b>Elisa Ferrara</b></sub>
      </td>
    </tr>
  </table>
</p>

## Licenza
Questo progetto è distribuito sotto la licenza MIT.
