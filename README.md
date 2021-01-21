## Progetto Programmazione Avanzata

## Introduzione

L'obiettivo di questo progetto è sviluppare un'applicazione WEB che gestisca un ***blog***.

L'idea di blog che abbiamo è quella di una ***community*** (ad esempio la community Microsoft) in cui gli utenti, una volta essersi registrati ed autenticati, possono scrivere dei **post** e/o dei **commenti** a post già presenti nel blog.

I post presenti nel blog verranno associati ad un **archivio** ed a specifici **tag**.

Gli utenti avranno anche la possibilità di **inserire degli allegati** ai propri post, specificando se potranno essere scaricati o meno dagli altri utenti.

In ogni momento un utente autenticato ha la possibilità di **modificare i dati del proprio profilo** e il **proprio contenuto all'interno del blog** (post, commenti e allegati).

Ci sarà poi un utente ***admin*** che, oltre alle funzioni degli altri utenti, avrà la **funzione di moderatore**. 

## *Installazione*
1. Installare Tomcat, MySQL, Maven e JDK;
2. Creare un nuovo utente in **phpMyAdmin** con le seguenti informazioni, `username: user `e` password: user` (**ALL PRIVILEGES**);
3. Creare un nuovo utente **Tomcat** con le seguenti informazioni, `username: tomcat`,` password: tomcat `e` roles: manager-script`;
4. Avviare i servizi tomcat e mysql;
5. Posizionarsi nella directory di root del progetto;
6. Eseguire il comando `mvn install`;
7. Fine.

**NOTE**: 
- La **prima volta** che viene lanciato il comando `mvn install` è normale che impieghi **dai 3 ai 4 minuti** per terminare (considerando il download delle dipendenze e i vari test). Le volte successive lo stesso comando impiegherà **dai 2 ai 3 minuti**. Impiega questo tempo in quanto ogni unit test è stato effettuato in modo che sia **indipendente** dagli altri, avviene il ***drop* di tutte le tabelle** del database per poi essere create nuovamente;
- Le fasi di building del progetto sono state **automatizzate**, per l'installazione è quindi sufficiente eseguire i passi descritti precedentemente ed il comando `mvn install`;
- Con il comando `mvn install` si ottiene il **redeploy** automatico sull'application server Tomcat e **l'esecuzione** automatica della **classe Java LoadDataTest**, i questo modo il blog risulta in parte già popolato;
- Con il comando `mvn clean` si ottiene, oltre alla pulizia dei file compilati, **l'undeploy** dell'applicazione dal server Tomcat;
- L'installazione precedente è stata testata con le seguenti **versioni**:
	1. Tomcat 9.0.41
	2. MySQL 8.0.18
	3. Maven 3.6.3
	4. JDK 15.0.1

## Accesso al Blog

Per **effettuare l'accesso al blog** si può utilizzare i seguenti utenti predefiniti o se ne possono creare dei nuovi tramite la pagina di registrazione del blog.

- Utente 1: `username: mario98 password: 12345678` (**admin**)
- Utente 2: `username: matteoVerdi password: 12345678`
- Utente 3: `username: giov_bian password: 12345678`
- Utente 4: `username: anto88 password: 12345678`

## Funzioni per Tipologia di Utenti

### Utenti non autenticati al sito, possono:

1. **Registrarsi**;
2. **Visualizzare le pagine "statiche"** del blog;
3. **Visualizzare** il blog, possono cioè vedere la **lista di tutti i post** presenti nel blog;
4. **Visualizzare** i dettagli di un post, possono cioè vedere le **informazioni del post** (autore, archivio, tag, descrizione estesa...) e **tutti i commenti del post**;
5. **Visualizzare tutti i tag** presenti nel blog;
6. **Visualizzare tutti gli archivi** presenti nel blog;
7. **Visualizzare la lista di tutti i post** appartenenti ad un **particolare archivio**;
8. **Visualizzare la lista di tutti i post** appartenenti ad un **particolare tag**;
9. **Visualizzare la lista di tutti i post** scritti da un **particolare autore**;
10. **Autenticarsi**;


### Utenti autenticati al sito, possono:

 1. **Effettuare tutte le operazioni dell'utente precedente** (ad eccezione della registrazione e dell'autenticazione);
 2. **Scrivere un** nuovo **post**;
 3. **Scrivere un nuovo commento** in un post specifico;
 4. **Visualizzare** la pagina con le **informazioni del proprio profilo** (nome, cognome, immagine profilo, ...);
 5. **Modificare le informazioni del proprio profilo** (NON si ha la possibilità di modificare l'username);
 6. **Eliminare il proprio profilo**;
 7. Visualizzare la **pagina di gestione dei propri post**:
	 1. Possibilità di **modificare un post**;
	 2. Possibilità di **eliminare un post**.
8. Visualizzare la **pagina di gestione degli allegati** dei propri post:
	1. Possibilità di **aggiungere un allegato** di un post;
	2. Possibilità di **modificare un allegato** di un post;
	3. Possibilità di **eliminare un allegato** di un post.
9. Visualizzare la **pagina di gestione dei propri commenti**:
	1. Possibilità di **modificare un commento**;
	2. Possibilità di **eliminare un commento**.

### Utente admin, possono:

 1. **Effettuare tutte le operazioni dell'utente precedente** (ad eccezione della registrazione e dell'autenticazione);
 2. Visualizzare la **pagina di gestione dei tag**:
	1. Possibilità di **aggiungere un nuovo tag**;
	2. Possibilità di **eliminare un tag** (SOLO SE non ci sono post ad esso associati). 
3. Visualizzare la **pagina di gestione degli archivi**:
	1. Possibilità di **eliminare un archivio** (SOLO SE non ci sono post ad esso associati).
4. Visualizzare la **pagina di gestione di tutti gli utenti**:
	1. Possibilità di **abilitare/disabilitare un utente**.
5. Visualizzare la **pagina di gestione di tutti i post**:
	1. Possibilità di **mostrare/nascondere un post**.
6. Visualizzare la **pagina di gestione di tutti gli allegati**:
	1. Possibilità di **mostrare/nascondere un allegato**.
7. Visualizzare la **pagina di gestione di tutti i commenti**:
	1. Possibilità di **mostrare/nascondere un commento**.


## Mappa Pagine Blog per Tipologia di Utenti

La **mappa delle pagine del blog** divisa per tipologia di utenti può essere trovata a [questa pagina](https://gitlab.com/FedeMiscia/advprogproject/-/wikis/Mappa-Pagine-Blog) della Wiki di questa Repository.

## Link Blog per Tipologia di Utenti

La **lista delle URL del blog** divisa per tipologia di utenti può essere trovata a [questa pagina](https://gitlab.com/FedeMiscia/advprogproject/-/wikis/Link-Blog) della Wiki di questa Repository.

## Javadoc

La **Javadoc** realizzata può essere visualizzata aprendo dal browser la pagina  [index.html](https://gitlab.com/FedeMiscia/advprogproject/-/blob/master/doc/index.html) nella cartella [doc](https://gitlab.com/FedeMiscia/advprogproject/-/tree/master/doc) che si trova in questa repository.

## ASSUNZIONI:
- **Non si è prevista** la possibilità di **modificare un tag e/o un archivio**, possono soltanto essere eventualmente eliminati se non contengono post (in quanto non è una funzione di interessa per come è stato implementato il nostro blog);
- **Non si è prevista** la possibilità di **inserire un nuovo archivio**, in quanto verrà fatto automaticamente all'inserimento del primo post di ogni mese;
- **L'utente admin non può modificare/eliminare post e/o commenti e/o utenti**, in quanto può nasconderli/disabilitarli. Sarà poi l'utente che l'ha creati a doverli modificare se vuole che vengano riabilitati od a doverli eliminarli;
- Il **campo “username” di User è PK**, in quanto abbiamo assunto che non si ha la possibilità di modificarlo (si possono invece aggiornare le altre informazioni dell'utente);



This project is based on a GitLab [Project Template](https://docs.gitlab.com/ee/gitlab-basics/create-project.html).
