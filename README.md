## Progetto Programmazione Avanzata

This project is based on a GitLab [Project Template](https://docs.gitlab.com/ee/gitlab-basics/create-project.html).

## Introduzione

L'obiettivo di questo progetto è sviluppare un'applicazione WEB che gestisca un ***blog***.

L'idea di blog che abbiamo è quella di una ***community*** (ad esempio la community Microsoft) in cui gli utenti, una volta essersi registrati ed autenticati, possono scrivere dei **post** e/o dei **commenti** a post già presenti nel blog.

Quindi tutti gli utenti che si sono registrati ad autenticati possono scrivere post e/o commenti. Ci sarà poi un utente ***admin*** che avrà la funzione di moderatore. 

## Funzioni per tipologia di utenti

### Utenti non autenticati al sito, possono:

1. **Registrarsi**;
2. **Visualizzare le pagine "statiche"** del blog;
3. **Visualizzare** il blog, possono cioè vedere la **lista di tutti i post** presenti nel blog;
4. **Visualizzare** i dettagli di un post, possono cioè vedere le **informazioni del post** (autore, archivio, tag, descrizione estesa...) e **tutti i commenti del post**;
5. **Visualizzare tutti i tag** presenti nel blog;
6. **Visualizzare tutti gli archivi** presenti nel blog;
7. **Visualizzare la lista di tutti i post** appartenenti ad un **particolare archivio**;
8. **Visualizzare la lista di tutti i post** appartenenti ad un **particolare tag**;
9. **Visualizzare la lista di tutti i post** scritti da un **particolare autore** (cliccando sull'autore nei dettagli di un post);
10. **Autenticarsi**;


### Utenti autenticati al sito, possono:

 1. **Effettuare tutte le operazioni dell'utente precedente** (ad eccezione della registrazione e dell'autenticazione);
 2. **Scrivere un** nuovo **post** + **caricamento** di eventuali **allegati** del post;
 3. **Scrivere un commento** in un post specifico;
 4. **Visualizzare** la pagina con le **informazioni del proprio profilo** (nome, cognome, immagine profilo, ...);
 5. **Modificare le informazioni del proprio profilo** (NON si ha la possibilità di modificare l'username);
 6. Visualizzare la **pagina di gestione dei propri post**:
	 1. Possibilità di **modificare/eliminare un post**;
	 2. Possibilità di **aggiungere/modificare/eliminare un allegato** di un post.
7. Visualizzare la **pagina di gestione dei propri commenti**:
	1. Possibilità di **modificare/eliminare un commento**.

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




## ASSUNZIONI:
- **Non si è prevista** la possibilità di **modificare un tag e/o un archivio**, possono soltanto essere eventualmente eliminati se non contengono post (in quanto non è una funzione di interessa per come è stata pensata l'applicazione WEB);
- **Non si è prevista** la possibilità di **inserire un nuovo archivio**, in quanto verrà fatto automaticamente all'inserimento del primo post del mese;
- **L'utente admin non può modificare/eliminare post e/o commenti e/o utenti**, in quanto può nasconderli/disabilitarli. Sarà poi l'utente che l'ha creati a doverli modificare se vuole che vengano riabilitati od a doverli eliminarli;
- Il **campo “username” di User è PK**, in quanto abbiamo assunto che non si ha la possibilità di modificarlo (si possono invece aggiornare le altre informazioni dell'utente);

