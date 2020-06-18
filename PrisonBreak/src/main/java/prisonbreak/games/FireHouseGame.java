/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak.games;

import prisonbreak.GameDescription;
import prisonbreak.parser.ParserOutput;
import prisonbreak.type.*;

import java.io.PrintStream;
import java.util.Iterator;

/**
 * ATTENZIONE: La descrizione del gioco è fatta in modo che qualsiasi gioco
 * debba estendere la classe GameDescription. L'Engine è fatto in modo che posso
 * eseguire qualsiasi gioco che estende GameDescription, in questo modo si
 * possono creare più gioci utilizzando lo stesso Engine.
 * <p>
 * Diverse migliorie possono essere applicati: - la descrizione del gioco
 * potrebbe essere caricate da file o da DBMS in modo da non modificare il
 * codice sorgente - l'utilizzo di file e DBMS non è semplice poiché all'interno
 * del file o del DBMS dovrebbe anche essere codificata la logica del gioco
 * (nextMove) oltre alla descrizione di stanze, oggetti, ecc...
 *
 * @author pierpaolo
 */
public class FireHouseGame extends GameDescription {

    @Override
    public void init() {
        //Commands
        Command nord = new Command(CommandType.NORD, "nord");
        nord.setAlias(new String[]{"n", "N", "Nord", "NORD"});
        getCommands().add(nord);
        Command iventory = new Command(CommandType.INVENTORY, "inventario");
        iventory.setAlias(new String[]{"inv", "i", "I", "INVENTARIO", "Inventario"});
        getCommands().add(iventory);
        Command sud = new Command(CommandType.SOUTH, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command est = new Command(CommandType.EAST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.WEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command end = new Command(CommandType.END, "end");
        end.setAlias(new String[]{"end", "fine", "esci", "muori", "ammazzati", "ucciditi", "suicidati", "exit"});
        getCommands().add(end);
        Command look = new Command(CommandType.LOOK_AT, "osserva");
        look.setAlias(new String[]{"guarda", "vedi", "trova", "cerca", "descrivi", "controlla", "leggi"});
        getCommands().add(look);
        Command pickup = new Command(CommandType.PICK_UP, "raccogli");
        pickup.setAlias(new String[]{"prendi", "afferra", "ottieni"});
        getCommands().add(pickup);
        Command open = new Command(CommandType.OPEN, "apri");
        open.setAlias(new String[]{"sblocca"});
        getCommands().add(open);
        Command close = new Command(CommandType.CLOSE, "chiudi");
        close.setAlias(new String[]{});
        getCommands().add(close);
        Command pull = new Command(CommandType.PULL, "tira");
        pull.setAlias(new String[]{"trascina"});
        getCommands().add(pull);
        Command turn_on = new Command(CommandType.TURN_ON, "accendi");
        turn_on.setAlias(new String[]{"on"});
        getCommands().add(turn_on);
        Command turn_off = new Command(CommandType.TURN_OFF, "spegni");
        turn_off.setAlias(new String[]{"off"});
        getCommands().add(turn_off);
        Command push = new Command(CommandType.PUSH, "premi");
        push.setAlias(new String[]{"spingi", "attiva", "prova", "schiaccia", "pigia"});
        getCommands().add(push);
        Command talk = new Command(CommandType.TALK_TO, "parla");
        talk.setAlias(new String[]{"chiacchiera", "comunica", "dialoga", "conversa",
                "affronta", "ascolta", "chiedi", "grida", "urla", "mormora", "sussurra", "bisbiglia", "conferisci"});
        getCommands().add(talk);
        Command eat = new Command(CommandType.EAT, "mangia");
        eat.setAlias(new String[]{"pranza", "cena", "divora", "finisci"});
        getCommands().add(eat);
        Command play = new Command(CommandType.PLAY, "gioca");
        play.setAlias(new String[]{"allenati"});
        getCommands().add(play);
        Command walk = new Command(CommandType.WALK, "cammina");
        walk.setAlias(new String[]{"corri", "vai", "muoviti", "striscia", "avvicinati", "avanza", "prosegui"});
        getCommands().add(walk);
        Command move = new Command(CommandType.MOVE, "muovi");
        move.setAlias(new String[]{"sposta", "togli", "leva"});
        getCommands().add(move);
        Command stand_up = new Command(CommandType.STAND_UP, "alzati");
        stand_up.setAlias(new String[]{"in piedi", "svegliati"});
        getCommands().add(stand_up);
        Command sit_down = new Command(CommandType.SIT_DOWN, "siediti");
        sit_down.setAlias(new String[]{"sdraiati", "dormi", "rilassati", "abbassati"});
        getCommands().add(sit_down);
        Command climb = new Command(CommandType.CLIMB, "arrampicati");
        climb.setAlias(new String[]{"sali", "scendi", "buttati", "scivola", "scavalca", "salta"});
        getCommands().add(climb);
        Command use = new Command(CommandType.USE, "usa");
        use.setAlias(new String[]{"prova","testa"});
        getCommands().add(use);
        //si intende uscire da una stanza e non uscire dal gioco
        Command exit = new Command(CommandType.EXIT, "esci");
        exit.setAlias(new String[]{"indietreggia", "ritorna", "scappa",
                "spostati", "togliti", "levati", "vai", "vai via", "fuggi"});
        getCommands().add(exit);
        Command esci = new Command(CommandType.ENTER, "entra");
        esci.setAlias(new String[]{"vai", "vai dentro"});
        getCommands().add(esci);
        Command accept = new Command(CommandType.ACCEPT, "accetta");
        accept.setAlias(new String[]{"si", "ok", "okay", "conferma", "d'accordo", "va bene", "perfetto", "concedi"});
        getCommands().add(accept);
        Command decline = new Command(CommandType.DECLINE, "declina");
        decline.setAlias(new String[]{"rifiuta", "no", "non va bene"});
        getCommands().add(decline);
        Command put_in = new Command(CommandType.PUT_IN, "immetti");
        put_in.setAlias(new String[]{"inserisci", "togli", "introduci", "prova"});
        getCommands().add(put_in);
        Command give = new Command(CommandType.GIVE, "dai");
        give.setAlias(new String[]{"lascia", "cedi", "dona", "regala", "poni", "concedi"});
        getCommands().add(give);
        Command make = new Command(CommandType.MAKE, "fai");
        make.setAlias(new String[]{"crea", "prepara", "inventa", "mischia", "mescola", "produci"});
        getCommands().add(make);
        Command remove = new Command(CommandType.REMOVE, "rimuovi");
        remove.setAlias(new String[]{"togli", "corrodi", "elimina", "rompi",
                "spezza", "distruggi", "fracassa", "frantuma", "sgretola"});
        getCommands().add(remove);

        //Descrizione cella
        Room cell = new Room(0, "Cella 17", "Ti trovi nella tua cella 17, al momento sei da solo" +
                " visto che sei l’ultimo arrivato.");
        cell.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo. La stanza e' illuminata da una piccola finestra sbarrata e l’unica via di " +
                "uscita si trova a est, al momento aperta visto che e' l’ora d’aria e tutti i detenuti " +
                "devono raggiungere il giardino!");
        //Descrizione corridoio
        Room corridor = new Room(1, "Corridoio", "Ti trovi nel corridoio del carcere che si" +
                " estende da sud verso nord.");
        corridor.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest la porta della tua cella aperta e" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");
        //Descrizione scale
        Room ladders = new Room(2, "Scalinata", "Ti trovi presso una scalinata, l’unica cosa" +
                "che puoi fare e' andare al piano di sotto oppure andare verso sud percorrendo il corridoio.");
        ladders.setLook("Puoi vedere i detenuti che si dirigono verso il giardino.");
        //Descrizione atrio
        Room lobby = new Room(3, "Atrio", "Ti trovi in un grosso atrio di ingresso, puoi notare" +
                " un enorme porta aperta dove si intravede il giardino.");
        lobby.setLook("Il luogo e' affollato di guardie che controllano la situazione. Vedi a nord un enorme scalinata" +
                " che porta al piano superiore e a ovest le celle degl’altri detenuti. L’atrio si estende ancora " +
                "verso sud.");
        //Descrizione giardino
        Room garden = new Room(4, "Giardino", "Sei in un ampio giardino verde illuminato " +
                "dal sole, dietro di te la porta che collega al grosso atrio.");
        garden.setLook("Guardando a sinistra puoi notare un grosso campo da basket, avanti a te un grosso muro " +
                "che separa il giardino dall’esterno con due enormi torri sulle quali ci sono le guardie come vedetta," +
                " a destra invece tre grosse panchine dove puoi sederti e rilassarti.");
        //Descrizione campo da basket
        Room basket = new Room(5, "Campo da basket", "Ti trovi nel campo di basket" +
                " momentaneamente vuoto.");
        basket.setLook("Puoi notare due canestri e un pallone in mezzo al campo.");
        //Descrizione muro della prigione
        Room wall = new Room(6, "Muro prigione", "Avvicinandoti alle mura le guardie ti danno " +
                "l’ordine di indietreggiare, rappresenti un potenziale pericolo. Non penso sia un’idea geniale " +
                "fuggendo da qui, la zona e' troppo controllata.");
        wall.setLook("Non c'e' nulla di particolare tranne che un grosso muro in mattoni!");
        //Descrizione panchine
        Room bench = new Room(7, "Panchine", "Tutte le panchine sono occupati da un gruppo " +
                "di detenuti che ti guardano con aria sospetta.");
        bench.setLook("Non noti nulla di particolare in loro e nelle panchine, tranne in una dove a terra puoi " +
                "notare un oggetto di metallo simile ad una vite.");
        //Descrizione infermeria
        Room infirmary = new Room(8, "Infermeria", "E' una classica infermeria e ti trovi" +
                " sdraiato sul tuo letto.");
        infirmary.setLook("Aprendo gli occhi noti un tavolo con molti strumenti, un grosso armadio in legno, " +
                "una lavagna, una porta che si collega al corridoio e una finestra sbarrata a nord. Puoi osservare anche " +
                "un quadro di Donald Trump appeso alla parete e in alto un condotto d’aria nuovissimo che sembra " +
                "irraggiungibile.  Sembra non esserci nessuno oltre a te nella stanza, riesci solo ad udire delle " +
                "voci nel corridoio.");
        //Seconda descrizione cella 17
        Room cell1 = new Room(9, "Cella 17", "Caspita gli antidolorifici ti hanno fatto dormire " +
                "molto e ti risvegli nella tua cella privo di qualsiasi dolore! Prima di andare via l’infermiera ti" +
                " ha dato qualche medicinale tra cui un medicinale all’ortica. La porta e' di nuovo aperta," +
                " e' l’ora d’aria, che ne pensi di andare a prendere una bella boccata d’aria?");
        cell1.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo. La stanza è illuminata da una piccola finestra sbarrata e l’unica via di " +
                "uscita si trova a est, al momento aperta!");
        //Descrizione passaggio segreto
        Room passage = new Room(10, "Passaggio segreto", "Sei appena entrato nel passaggio segreto.");
        passage.setLook("Noti delle pareti in roccia un po’ malandate, un’ enorme parete sti blocca la strada" +
                " a nord, puoi solo andare ad sud o a nord o ritornare indietro nella tua cella prima che qualcuno" +
                " ti scopra!!!");
        //Descrizione a sud del passaggio segreto
        Room passage_south = new Room(11, "Passaggio segreto", "Prosegui nel passaggio segreto" +
                " e vedi una scala appoggiata al muro.");
        passage_south.setLook("La scala sembra non portare da nessuna parte, infatti sopra ci sono solo" +
                " delle grate che porta al secondo piano in cui non e' possibile accedere. Il passaggio continua" +
                " ancora a sud oppure puoi sempre ritornare indietro prima che sia troppo tardi!");
        //Descrizione stanza generatore
        Room generator = new Room(12, "Stanza con generatore", "Sembra che il passaggio" +
                " finisca qui, sei in una piccola stanza tutta buia.");
        generator.setLook("Non riesci a vedere nulla tranne che qualche piccola luce lampeggiante di fronte a te!");
        //Descrizione a nord del passaggio segreto
        Room passage_north = new Room(13, "Passaggio segreto", "Sembra che sei arrivato gia' " +
                "in un vicolo cieco, vedi solo un enorme soffitto e una piccola grata posta in alto!");
        passage_north.setLook("Non c'e' nient'altro di particolare.");
        //Descrizione sulla scala
        Room on_ladder = new Room(14, "Sulla scala", "Sei salito sulla scala e noti un condotto" +
                " d’aria un po’ vecchiotto.");
        on_ladder.setLook("Sembra di aver già visto questo tipo di condotto da un’altra parte!");
        //Descrizione condotto d'aria
        Room air_duct = new Room(15, "Condotto d'aria", "Sei riuscito ad entrare nel condotto," +
                " strisci piano cercando di fare meno rumore possibile.");
        air_duct.setLook("Ci sono molte ragnatele e il condotto sembra non utilizzato, cerca di fare veloce in modo" +
                " da non risultare assente all’appello! Il condotto si divide in tre strade diverse, una a nord," +
                " una ad est e l’altra a ovest.");
        //Descrizione a est condotto d'aria
        Room air_duct_east = new Room(16, "Condotto d'aria", "Ti ritrovi in un vicolo ceco, " +
                "sotto di te c’e' un’enorme grata.");
        air_duct_east.setLook("Puoi osservare un’altra cella di un detenuto.");
        //Descrizione cella detenuti
        Room other_cell = new Room(17, "Cella detenuto", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        other_cell.setLook("Meglio non perdere tempo qui!");
        //Descrizione a nord condotto d'aria
        Room air_duct_north = new Room(18, "Condotto d'aria", "Vedi una grossa grata e " +
                "senti delle voci simili a quelle che sentivi quando eri in infermeria.");
        air_duct_north.setLook("C'e' solo una grossa grata");
        //Descrizione a ovest condotto d'aria
        Room air_duct_west = new Room(19, "Condotto d'aria", " Il condotto si fa sempre piu' " +
                "stretto e non riesci piu' a passare! Non sembra sia la strada giusta!");
        air_duct_west.setLook("Scorgi pero' in lontananza un piccolo oggetto!");
        //Descrizione mensa
        Room canteen = new Room(20, "Mensa", " Ti trovi in una grossa mensa e puoi vedere " +
                "i detenuti che stanno mangiando.");
        canteen.setLook("Uno di loro si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto" +
                " che lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                "per averne uno quindi sarei costretto a trattare.");
        //Descrizione palestra
        Room gym = new Room(21, "Palestra", "Ti trovi nella palestra del carcere, " +
                "e' molto grande e non e' tenuta in ottimo stato, alcuni detenuti si stanno allenando.");
        gym.setLook("Intorno a te vedi tanti attrezzi per poterti allenare e aumentare la tua forza.");
        //Descrizione fuori cella isolamento
        Room out_isolation = new Room(22, "Fuori cella isolamento", "Sei di fronte la cella" +
                " di isolamento dove e' collocato tuo fratello. Essendo in un carcere di massima sicurezza, la porta " +
                "e' controllata da un paio di guardie.");
        out_isolation.setLook("Puoi notare da lontano che non si tratta di una semplice porta ma di una porta che " +
                "puo' essere aperta solo tramite un PIN segreto.");
        //Descrizione porta isolamento
        Room door_isolation = new Room(23, "Porta isolamento", "Sei di fronte ad una porta" +
                " blindata che come gia' ti sembrava e' possibile aprire tramite un PIN segreto.");
        door_isolation.setLook("Il pin e' conosciuto solo dalle guardie e quindi ti e' impossibile reperirlo!" +
                " A meno che non vuoi iniziare a sparare numeri a caso devi trovare assolutamente un’altra soluzione" +
                " prima che le luci si accendano e le guardie tornino!");
        //Descrizione dentro isolamento
        Room isolation = new Room(24, "Dentro isolamento", "La porta si apre e ti trovi dentro" +
                " il luogo dove si trovano le celle isolamento. Ci sono tre lunghi corridoi, uno a est, uno a " +
                "sud e l’altro a nord!");
        isolation.setLook("Non noti nient’altro di particolare!");
        //Descrizione corridoio nord isolamento
        Room north_corridor = new Room(25, "Corridoio nord isolamento", "Prosegui nel corridoio" +
                " a nord, ci sono tante celle chiuse di prigionieri in isolamento. Provi a gridare (ma non troppo)," +
                " il nome di tuo fratello ma non risponde nessuno!");
        north_corridor.setLook("Non noti nient’altro di particolare!");
        //Descrizione proseguimento corridoio nord isolamento
        Room north_corridor1 = new Room(26, "Corridoio nord isolamento", "La speranza e' l’ultima" +
                " a morire ma penso proprio che tuo fratello non si trovi in questo corridoio! ");
        north_corridor1.setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");
        //Descrizione fine corridoio nord isolamento
        Room north_corridor2 = new Room(27, "Corridoio nord isolamento", "Il corridoio termina" +
                " con una grossa parete di fronte e te, hai visto tutte le celle ma di tuo fratello nemmeno l’ombra, " +
                "te l’avevo detto io!!!");
        north_corridor2.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");
        //Descrizione corridoio est isolamento
        Room est_corridor = new Room(28, "Corridoio est isolamento", "Prosegui nel corridoio" +
                " a est, ci sono tante celle chiuse di prigionieri in isolamento. Provi a gridare (ma non troppo)," +
                " il nome di tuo fratello ma non risponde nessuno!");
        est_corridor.setLook("Non noti nient’altro di particolare!");
        //Descrizione proseguimento corridoio est isolamento
        Room est_corridor1 = new Room(29, "Corridoio est isolamento", "La speranza e' l’ultima" +
                " a morire ma penso proprio che tuo fratello non si trovi in questo corridoio! ");
        est_corridor1.setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");
        //Descrizione fine corridoio est isolamento
        Room est_corridor2 = new Room(30, "Corridoio est isolamento", "Il corridoio termina" +
                " con una grossa parete di fronte e te, hai visto tutte le celle ma di tuo fratello nemmeno l’ombra, " +
                "te l’avevo detto io!!!");
        est_corridor2.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");
        //Descrizione corridoio sud isolamento
        Room south_corridor = new Room(31, "Corridoio sud isolamento", "Prosegui nel corridoio " +
                "a sud, ci sono tante celle chiuse di prigionieri in isolamento. Provi a gridare (ma non troppo)!");
        south_corridor.setLook("Senti un mormorio in lontananza!");
        //Descrizione proseguimento corridoio sud isolamento
        Room south_corridor1 = new Room(32, "Corridoio sud isolamento", "Il mormorio si fa sempre" +
                " piu' forte ma non hai ancora trovato la cella di tuo fratello.");
        south_corridor1.setLook("Puoi osservare le celle degli altri prigionieri in cui non e' possibile entrare!");
        //Descrizione fine corridoio sud isolamento
        Room south_corridor2 = new Room(33, "Corridoio sud isolamento", "Il corridoio termina con " +
                "una grossa parete di fronte a te, hai visto tutte le celle tranne l’ultima!");
        south_corridor2.setLook("Avvicinandoti a questa senti la voce di tuo fratello, hai trovato la sua cella!!!");
        //Descrizione cella del fratello
        Room brother_cell = new Room(34, "Cella fratello", "Sei nella cella di tuo fratello, " +
                "l’aspetto della cella e' ripugnante.");
        brother_cell.setLook("Vedi solo un piccolo lavandino e un water e un letto in legno che sembra scomodissimo." +
                " Di fronte a te, tuo fratello con un’aria contenta di vederti ma allo stesso tempo sorpresa!");
        //Descrizione2 fuori cella isolamento
        Room out_isolation1 = new Room(35, "Fuori cella isolamento", "Appena dato il farmaco " +
                "decidi di fuggire fuori la cella isolamento prima che le luci si accendano e le guardie ti scoprano!!!" +
                " Sono le 20:55 hai esattamente 5 minuti per tornare alla tua cella e completare il tuo piano! " +
                "Speri che abbiamo portato tuo fratello in infermeria!");
        out_isolation1.setLook("Sei di fronte la cella di isolamento dove e' collocato tuo fratello. Essendo in un " +
                "carcere di massima sicurezza, la porta e' controllata da un paio di guardie. Puoi notare da lontano" +
                " che non si tratta di una semplice porta ma di una porta che puo' essere aperta solo tramite un" +
                " PIN segreto.");
        //Descrizione 3 cella
        Room cell2 = new Room(36, "Cella 17", "Sei arrivato alla tua cella , ad aspettarti " +
                "puntuale c’e' il tuo amichetto Genny. E' ora di attuare il piano!");
        cell2.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo!");
        //Descrizione 2 infermeria
        Room infirmary1 = new Room(37, "Infermeria", "Sei in infermeria, vedi tuo fratello " +
                "sdraiato sul letto, la porta dell’infermeria e' chiusa a chiave.");
        infirmary1.setLook("Puoi notare il letto di tuo fratello, il vecchio armadio, il tavolo con gli strumenti, " +
                "una finestra a nord e una lavagna appesa al muro. Non c’e' nessun altro siccome e' notte!");
        //Descrizione finale
        Room out_window = new Room(38, "Fine", "Decidi di fuggire dalla finestra. Tu e tutta" +
                " la tua squadra usate il lungo cavo che collega la finestra al grande muro della prigione. Arrivati" +
                " sul muro riuscite a scavalcare con molta attenzione il filo spinato presente. Fate tutti un grande" +
                " salto fuori dalla prigione e scappate per 100 metri verso nord. Li trovare ad aspettarvi un " +
                "elicottero guidato da vostro padre, che sapeva tutto del vostro piano segreto e l’ora esatta in cui " +
                "dovevate attuarlo! Salite tutti sull’elicottero e' fuggite tutti insieme verso il Messico cosicche'" +
                " nessuno potra' piu' ritrovarvi!\n\n" +
                "COMPLIMENTI HAI VINTO!");
        out_window.setLook("");
        //Descrizione finestra infermeria
        Room window = new Room(39, "Finestra infermeria", "La finestra e' sbarrata e non" +
                " sembra possibile aprirla! Puoi notare un lungo cavo che porta fino al muro della prigione!");
        window.setLook("Non noti nient'altro di particolare!");
        //Descrizione di fronte alle panchine
        Room front_bench = new Room(40, "Di fronte alle panchine", "Ti avvicini alla panchina" +
                " per controllare meglio l’oggetto ma vieni subito fermato da un gruppo di neri che con aria " +
                "minacciosa ti chiedono di allontanarti percha' la panchina e' la loro. Cosa scegli di fare?");
        front_bench.setLook("Vedi il gruppo di neri che ti fissa aspettando una tua mossa, non credo sia l’idea " +
                "migliore restare lì impalato.");
        //Descrizione rissa
        Room brawl = new Room(41, "Rissa", "Sai benissimo che in un carcere non si possono" +
                " comprare panchine e ti avvicini nuovamente con l’intendo di prendere l’oggetto. Il gruppetto " +
                "ti blocca e il piu' grosso di loro ti tira un pugno contro il viso... \n Perdendo i sensi" +
                " non ti ricordi piu' nulla e ti svegli in infermeria.");
        brawl.setLook("");
        //Descrizione rissa con bisturi
        Room brawl1 = new Room(42, "Rissa", "Riesci subito a tirare fuori il bisturi dalla " +
                "tasca, il gruppetto lo vede e capito il pericolo decide di lasciare stare (Mettere a rischio la " +
                "vita per una panchina sarebbe veramente stupido) e vanno via con un’aria di vendetta. Ora sei solo " +
                "vicino alla panchina.");
        brawl1.setLook("E' una grossa panchina in legno un po’ malandata, ci sei solo tu nelle vicinanze.");
        //Descrizione sud atrio
        Room south_lobby = new Room(43, "Atrio", "Ti trovi a sud del grosso atrio di ingresso." +
                " Puoi notare che l'atrio prosegue sia a nord che a sud!");
        south_lobby.setLook("Puoi notare a ovest le celle chiuse dei detenuti e a est la palestra.");
        //Descrizione fine atrio
        Room end_lobby = new Room(44, "Atrio", "Ti trovi alla fine del grosso atrio, a sud vedi " +
                " tante guardie, sembra una zona particolarmente protetta!!! Noti a est la sala per la mensa e a " +
                " ovest le celle chiuse dei detenuti!");
        end_lobby.setLook("Non c'è nulla di particolare! Puoi ritornare indietro verso nord!");


        //maps
        cell.setEast(corridor);
        cell.setWest(passage);
        cell1.setEast(corridor);
        cell1.setWest(passage);
        cell2.setEast(corridor);
        cell2.setWest(passage);
        corridor.setWest(cell);
        corridor.setWest(cell1);
        corridor.setWest(cell2);
        corridor.setNorth(ladders);
        ladders.setSouth(corridor);
        ladders.setWest(other_cell);
        ladders.setEast(lobby);
        lobby.setWest(ladders);
        lobby.setEast(garden);
        lobby.setSouth(south_lobby);
        south_lobby.setEast(gym);
        south_lobby.setNorth(lobby);
        south_lobby.setWest(other_cell);
        south_lobby.setSouth(end_lobby);
        garden.setEast(wall);
        garden.setWest(lobby);
        garden.setNorth(basket);
        garden.setSouth(bench);
        basket.setSouth(garden);
        wall.setWest(garden);
        bench.setNorth(garden);
        bench.setSouth(front_bench);
        front_bench.setNorth(bench);
        front_bench.setSouth(brawl);
        front_bench.setSouth(brawl1);
        brawl.setNorth(front_bench);
        brawl1.setNorth(front_bench);
        gym.setWest(south_lobby);
        end_lobby.setEast(canteen);
        end_lobby.setWest(other_cell);
        end_lobby.setNorth(south_lobby);
        end_lobby.setSouth(out_isolation);
        end_lobby.setSouth(out_isolation1);
        canteen.setWest(end_lobby);
        out_isolation.setNorth(end_lobby);
        out_isolation.setEast(door_isolation);
        out_isolation.setWest(other_cell);
        out_isolation1.setWest(other_cell);
        out_isolation1.setNorth(end_lobby);
        out_isolation1.setEast(door_isolation);
        door_isolation.setWest(out_isolation);
        door_isolation.setWest(out_isolation1);
        door_isolation.setEast(isolation);
        isolation.setWest(door_isolation);
        isolation.setEast(est_corridor);
        isolation.setNorth(north_corridor);
        isolation.setSouth(south_corridor);
        est_corridor.setEast(est_corridor1);
        est_corridor.setWest(isolation);
        est_corridor1.setEast(est_corridor2);
        est_corridor1.setWest(est_corridor);
        est_corridor2.setWest(est_corridor1);
        north_corridor.setNorth(north_corridor1);
        north_corridor.setSouth(isolation);
        north_corridor1.setNorth(north_corridor2);
        north_corridor1.setSouth(north_corridor);
        north_corridor2.setSouth(north_corridor1);
        south_corridor.setNorth(isolation);
        south_corridor.setSouth(south_corridor1);
        south_corridor1.setNorth(south_corridor);
        south_corridor1.setSouth(south_corridor2);
        south_corridor2.setNorth(south_corridor1);
        south_corridor2.setEast(brother_cell);
        brother_cell.setWest(south_corridor2);
        passage.setEast(cell);
        passage.setEast(cell1);
        passage.setEast(cell2);
        passage.setNorth(passage_north);
        passage.setSouth(passage_south);
        passage_south.setNorth(passage);
        passage_south.setSouth(generator);
        generator.setNorth(passage_south);
        passage_north.setSouth(passage);
        passage_north.setNorth(on_ladder);
        on_ladder.setSouth(passage_north);
        on_ladder.setNorth(air_duct);
        air_duct.setSouth(on_ladder);
        air_duct.setEast(air_duct_east);
        air_duct.setNorth(air_duct_north);
        air_duct.setWest(air_duct_west);
        air_duct_east.setSouth(other_cell);
        air_duct_east.setWest(air_duct);
        air_duct_west.setEast(air_duct);
        air_duct_north.setSouth(air_duct);
        air_duct_north.setNorth(infirmary1);
        infirmary1.setNorth(window);
        infirmary.setNorth(window);
        window.setSouth(infirmary);
        window.setSouth(infirmary1);
        window.setNorth(out_window);

        getRooms().add(cell);
        getRooms().add(cell1);
        getRooms().add(cell2);
        getRooms().add(corridor);
        getRooms().add(ladders);
        getRooms().add(lobby);
        getRooms().add(garden);
        getRooms().add(basket);
        getRooms().add(wall);
        getRooms().add(bench);
        getRooms().add(front_bench);
        getRooms().add(brawl);
        getRooms().add(brawl1);
        getRooms().add(south_lobby);
        getRooms().add(other_cell);
        getRooms().add(gym);
        getRooms().add(canteen);
        getRooms().add(end_lobby);
        getRooms().add(out_isolation);
        getRooms().add(door_isolation);
        getRooms().add(isolation);
        getRooms().add(north_corridor);
        getRooms().add(south_corridor);
        getRooms().add(est_corridor);
        getRooms().add(north_corridor1);
        getRooms().add(south_corridor1);
        getRooms().add(est_corridor1);
        getRooms().add(north_corridor2);
        getRooms().add(south_corridor2);
        getRooms().add(est_corridor2);
        getRooms().add(brother_cell);
        getRooms().add(passage);
        getRooms().add(passage_south);
        getRooms().add(generator);
        getRooms().add(passage_north);
        getRooms().add(on_ladder);
        getRooms().add(air_duct);
        getRooms().add(air_duct_east);
        getRooms().add(air_duct_west);
        getRooms().add(air_duct_north);
        getRooms().add(infirmary);
        getRooms().add(infirmary1);
        getRooms().add(out_window);
        getRooms().add(window);
        getRooms().add(out_isolation1);

        AdvObject screw= new AdvObject(1, "vite", "E' una semplice vite con inciso il numero di" +
                " serie: 11121147");
        screw.setAlias(new String[]{"viti", "cacciavite", "cacciaviti","chiodo"});
        brawl1.getObjects().add(screw);
        AdvObject scotch= new AdvObject(2, "scotch", "E' un semplice scotch, dimenticato li" +
                " forse da qualche operaio!");
        scotch.setAlias(new String[]{"nastro", "nastro adesivo"});
        air_duct_west.getObjects().add(scotch);
        AdvObject tools= new AdvObject(3, "attrezzi", "Sono degli attrezzi da palestra, ottimi" +
                " per allenarsi e aumentare la forza!");
        tools.setAlias(new String[]{"manubri", "pesi"});
        tools.setPickupable(false);
        tools.setUsable(true);
        gym.getObjects().add(tools);
        AdvObject food = new AdvObject(4, "cibo", "C'è solo il tuo pranzo, puoi mangiarlo anche" +
                " se non servirà a nulla");
        food.setAlias(new String[]{"pranzo", "cena","piatto","tavolo"});
        tools.setEatable(true);
        cell.getObjects().add(food);
        cell1.getObjects().add(food);
        cell2.getObjects().add(food);
        AdvObject ball = new AdvObject(5, "palla da basket", "E' un semplice pallone da basket");
        ball.setAlias(new String[]{"palla", "pallone","pallone da basket"});
        ball.setUsable(true);
        basket.getObjects().add(ball);
        AdvObject ladder = new AdvObject(6, "scala", "E' solo una scala in legno, " +
                "sembra molto leggera e facile da spostare");
        ladder.setAlias(new String[]{"scaletta", "scala in legno"});
        passage_south.getObjects().add(ball);

        AdvObject scalpel = new AdvObject(7, "bisturi", "Sono solo tanti bisturi tutti uguali!");
        scalpel.setAlias(new String[]{});
        scalpel.setUsable(true);
        infirmary.getObjects().add(scalpel);

        AdvObject hacksaw = new AdvObject(8, "seghetto", "E’ un seghetto molto affilato," +
                " potresti riuscire a rompere qualcosa");
        hacksaw.setAlias(new String[]{"sega","taglierino"});
        scalpel.setUsable(true);
        //TO DO assegnare oggetto a Jonny Bello

        AdvObject substances = new AdvObject(9, "sostanze", "Sul tavolo puoi vedere alcuni " +
                "strumenti di lavoro e alcune sostanze come: Cloruro di sodio, acido solforico e altre sostanze" +
                " di cui non riesco nemmeno a leggere il nome!");
        substances.setAlias(new String[]{"tavolo con strumenti","ingredienti","acido","sostanze chimiche"});
        substances.setUsable(true);
        substances.setMixable(true);
        infirmary1.getObjects().add(substances);

        AdvObject medicine = new AdvObject(10, "farmaco alle ortiche", "E' un medicinale per" +
                " alleviare i dolori.");
        medicine.setAlias(new String[]{"farmaco","medicina","compresse alle ortiche","compresse","sciroppo",
        "sciroppo alle ortiche"});
        medicine.setGive(true);
        //TO DO mettere nell'inventario
        
        //set starting room
        setCurrentRoom(cell);
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        if (p.getCommand() == null) {
            out.println("Non ho capito cosa devo fare! Prova con un altro comando.");
        } else {
            //move
            boolean noroom = false;
            boolean move = false;
            if (p.getCommand().getType() == CommandType.NORD) {
                if (getCurrentRoom().getNorth() != null) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.SOUTH) {
                if (getCurrentRoom().getSouth() != null) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.EAST) {
                if (getCurrentRoom().getEast() != null) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.WEST) {
                if (getCurrentRoom().getWest() != null) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.INVENTORY) {
                out.println("Nel tuo inventario ci sono:");
                for (AdvObject o : getInventory()) {
                    out.println(o.getName() + ": " + o.getDescription());
                }
            } else if (p.getCommand().getType() == CommandType.LOOK_AT) {
                if (getCurrentRoom().getLook() != null) {
                    out.println(getCurrentRoom().getLook());
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }
            } else if (p.getCommand().getType() == CommandType.PICK_UP) {
                if (p.getObject() != null) {
                    if (p.getObject().isPickupable()) {
                        getInventory().add(p.getObject());
                        getCurrentRoom().getObjects().remove(p.getObject());
                        out.println("Hai raccolto: " + p.getObject().getDescription());
                    } else {
                        out.println("Non puoi raccogliere questo oggetto.");
                    }
                } else {
                    out.println("Non c'è niente da raccogliere qui.");
                }
            } else if (p.getCommand().getType() == CommandType.OPEN) {
                /*ATTENZIONE: quando un oggetto contenitore viene aperto, tutti gli oggetti contenuti
                 * vengongo inseriti nella stanza o nell'inventario a seconda di dove si trova l'oggetto contenitore.
                 * Questa soluzione NON va bene poiché quando un oggetto contenitore viene richiuso è complicato
                 * non rendere più disponibili gli oggetti contenuti rimuovendoli dalla stanza o dall'invetario.
                 * Trovare altra soluzione.
                 */
                if (p.getObject() == null && p.getInvObject() == null) {
                    out.println("Non c'è niente da aprire qui.");
                } else {
                    if (p.getObject() != null) {
                        if (p.getObject().isOpenable() && p.getObject().isOpen() == false) {
                            if (p.getObject() instanceof AdvObjectContainer) {
                                out.println("Hai aperto: " + p.getObject().getName());
                                AdvObjectContainer c = (AdvObjectContainer) p.getObject();
                                if (!c.getList().isEmpty()) {
                                    out.print(c.getName() + " contiene:");
                                    Iterator<AdvObject> it = c.getList().iterator();
                                    while (it.hasNext()) {
                                        AdvObject next = it.next();
                                        getCurrentRoom().getObjects().add(next);
                                        out.print(" " + next.getName());
                                        it.remove();
                                    }
                                    out.println();
                                }
                            } else {
                                out.println("Hai aperto: " + p.getObject().getName());
                                p.getObject().setOpen(true);
                            }
                        } else {
                            out.println("Non puoi aprire questo oggetto.");
                        }
                    }
                    if (p.getInvObject() != null) {
                        if (p.getInvObject().isOpenable() && p.getInvObject().isOpen() == false) {
                            if (p.getInvObject() instanceof AdvObjectContainer) {
                                AdvObjectContainer c = (AdvObjectContainer) p.getInvObject();
                                if (!c.getList().isEmpty()) {
                                    out.print(c.getName() + " contiene:");
                                    Iterator<AdvObject> it = c.getList().iterator();
                                    while (it.hasNext()) {
                                        AdvObject next = it.next();
                                        getInventory().add(next);
                                        out.print(" " + next.getName());
                                        it.remove();
                                    }
                                    out.println();
                                }
                            } else {
                                p.getInvObject().setOpen(true);
                            }
                            out.println("Hai aperto nel tuo inventario: " + p.getInvObject().getName());
                        } else {
                            out.println("Non puoi aprire questo oggetto.");
                        }
                    }
                }
            } else if (p.getCommand().getType() == CommandType.PUSH) {
                //ricerca oggetti pushabili
                if (p.getObject() != null && p.getObject().isPushable()) {
                    out.println("Hai premuto: " + p.getObject().getName());
                    if (p.getObject().getId() == 3) {
                        end(out);
                    }
                } else if (p.getInvObject() != null && p.getInvObject().isPushable()) {
                    out.println("Hai premuto: " + p.getInvObject().getName());
                    if (p.getInvObject().getId() == 3) {
                        end(out);
                    }
                } else {
                    out.println("Non ci sono oggetti che puoi premere qui.");
                }
            }
            if (noroom) {
                out.println("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri per oltrepassare i muri...");
            } else if (move) {
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                out.println(getCurrentRoom().getDescription());
            }
        }
    }

    private void end(PrintStream out) {
        out.println("Premi il pulsante del giocattolo e in seguito ad una forte esplosione la tua casa prende fuoco...tu e tuoi famigliari cercate invano di salvarvi e venite avvolti dalle fiamme...è stata una morte CALOROSA...addio!");
        System.exit(0);
    }
}
