# UninaDelivery

## Descrizione
UninaDelivery è un sistema avanzato per la gestione logistica delle spedizioni di merci. L'applicazione permette agli operatori di pianificare, monitorare e ottimizzare il processo di spedizione in base agli ordini dei clienti, tenendo conto di variabili come disponibilità dei prodotti, mezzi di trasporto e corrieri disponibili.

Il sistema è progettato con un'interfaccia grafica intuitiva realizzata in JavaFX e sfrutta una base di dati relazionale per gestire gli ordini e le disponibilità.

Il sistema offre diverse funzionalità chiave:
- Login per operatori
- Visualizzazione e filtro degli ordini per utente o intervallo di tempo
- Creazione di nuove spedizioni, assegnando ordini, mezzi di trasporto e corrieri
- Generazione di report statistici mensili
- Gestione delle merci
- Pianificazione di spedizioni ricorrenti (settimanali, mensili o annuali)

## Installazione
### 1. Configurazione del Database
Questo progetto utilizza **PostgreSQL** come database relazionale. Nella repository è disponibile un backup del database per facilitarne la configurazione.

#### Passaggi per ripristinare il database:
1. Installare PostgreSQL, se non già presente.
2. Creare un database con il nome **uninadelivery**.
3. Ripristinare il backup con il comando:
   ```sh
   psql -U postgres -d uninadelivery -f backup.sql
   ```

### 2. Configurazione delle credenziali del Database
Per motivi di sicurezza, le credenziali di accesso al database non sono incluse direttamente nel codice. Devi creare un file di configurazione con i seguenti dati:

#### Passaggi:
1. Nella cartella principale del progetto, crea un file `config.properties`.
2. Inserisci le credenziali di accesso nel file:
   ```properties
   db.url=jdbc:postgresql://localhost:5432/UninaDelivery
   db.username=postgres
   db.password=postgres
   ```
3. Assicurati che il file **`config.properties` non sia caricato su GitHub**, aggiungendolo al `.gitignore`.

### 3. Avvio del Progetto
Il progetto utilizza **JavaFX** per l'interfaccia grafica. Per eseguire l'applicazione:
```sh
mvn clean install
java -jar target/uninaDelivery.jar
```

## Contributi
Se vuoi contribuire al progetto, assicurati di creare un nuovo branch e di inviare una pull request con le modifiche.

## Licenza
Questo progetto è distribuito sotto la licenza MIT.
