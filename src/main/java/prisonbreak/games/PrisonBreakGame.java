package prisonbreak.games;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;

import prisonbreak.Exceptions.InventoryEmptyException;
import prisonbreak.Exceptions.InventoryFullException;
import prisonbreak.GameDescription;
import prisonbreak.parser.ParserOutput;
import prisonbreak.type.*;

public class PrisonBreakGame extends GameDescription {

    private void initVerbs() {
        TokenVerb nord = new TokenVerb(VerbType.NORD);
        nord.setAlias(new HashSet<>(Arrays.asList("N", "Nord")));
        getTokenVerbs().add(nord);

        TokenVerb inventory = new TokenVerb(VerbType.INVENTORY);
        inventory.setAlias(new HashSet<>(Arrays.asList("Inv", "I", "Inventario")));
        getTokenVerbs().add(inventory);

        TokenVerb sud = new TokenVerb(VerbType.SOUTH);
        sud.setAlias(new HashSet<>(Arrays.asList("S", "Sud")));
        getTokenVerbs().add(sud);

        TokenVerb est = new TokenVerb(VerbType.EAST);
        est.setAlias(new HashSet<>(Arrays.asList("E", "Est")));
        getTokenVerbs().add(est);

        TokenVerb ovest = new TokenVerb(VerbType.WEST);
        ovest.setAlias(new HashSet<>(Arrays.asList("O", "Ovest")));
        getTokenVerbs().add(ovest);

        TokenVerb end = new TokenVerb(VerbType.END);
        end.setAlias(new HashSet<>(Arrays.asList("End", "Exit", "Fine", "Esci", "Muori", "Ammazzati", "Ucciditi", "Suicidati", "Crepa")));
        getTokenVerbs().add(end);

        TokenVerb look = new TokenVerb(VerbType.LOOK_AT);
        look.setAlias(new HashSet<>(Arrays.asList("Osserva", "Guarda", "Vedi", "Trova", "Cerca", "Descrivi", "Controlla", "Leggi")));
        getTokenVerbs().add(look);

        TokenVerb pickup = new TokenVerb(VerbType.PICK_UP);
        pickup.setAlias(new HashSet<>(Arrays.asList("Prendi", "Afferra", "Ottieni", "Raccogli")));
        getTokenVerbs().add(pickup);

        TokenVerb open = new TokenVerb(VerbType.OPEN);
        open.setAlias(new HashSet<>(Arrays.asList("Sblocca", "Apri")));
        getTokenVerbs().add(open);

        TokenVerb close = new TokenVerb(VerbType.CLOSE);
        close.setAlias(new HashSet<>(Arrays.asList("Chiudi", "Sbarra", "Richiudi")));
        getTokenVerbs().add(close);

        TokenVerb pull = new TokenVerb(VerbType.PULL);
        pull.setAlias(new HashSet<>(Arrays.asList("Trascina", "Tira")));
        getTokenVerbs().add(pull);

        TokenVerb turn_on = new TokenVerb(VerbType.TURN_ON);
        turn_on.setAlias(new HashSet<>(Arrays.asList("Accendi", "On")));
        getTokenVerbs().add(turn_on);

        TokenVerb turn_off = new TokenVerb(VerbType.TURN_OFF);
        turn_off.setAlias(new HashSet<>(Arrays.asList("Spegni", "Off")));
        getTokenVerbs().add(turn_off);

        TokenVerb push = new TokenVerb(VerbType.PUSH);
        push.setAlias(new HashSet<>(Arrays.asList("Premi", "Spingi", "Attiva", "Schiaccia", "Pigia")));
        getTokenVerbs().add(push);

        TokenVerb talk = new TokenVerb(VerbType.TALK_TO);
        talk.setAlias(new HashSet<>(Arrays.asList("Parla", "Chiacchiera", "Comunica", "Dialoga", "Conversa",
                "Affronta", "Ascolta", "Chiedi", "Grida", "Urla", "Mormora", "Sussurra", "Bisbiglia", "Conferisci")));
        getTokenVerbs().add(talk);

        TokenVerb eat = new TokenVerb(VerbType.EAT);
        eat.setAlias(new HashSet<>(Arrays.asList("Mangia", "Pranza", "Cena", "Divora", "Finisci")));
        getTokenVerbs().add(eat);

        TokenVerb play = new TokenVerb(VerbType.PLAY);
        play.setAlias(new HashSet<>(Arrays.asList("Gioca", "Allenati")));
        getTokenVerbs().add(play);

        TokenVerb walk = new TokenVerb(VerbType.WALK);
        walk.setAlias(new HashSet<>(Arrays.asList("Cammina", "Corri", "Vai", "Muoviti", "Striscia", "Avvicinati",
                "Avanza", "Prosegui")));
        getTokenVerbs().add(walk);

        TokenVerb stand_up = new TokenVerb(VerbType.STAND_UP);
        stand_up.setAlias(new HashSet<>(Arrays.asList("Alzati", "Svegliati")));
        getTokenVerbs().add(stand_up);

        TokenVerb sit_down = new TokenVerb(VerbType.SIT_DOWN);
        sit_down.setAlias(new HashSet<>(Arrays.asList("Siediti", "Sdraiati", "Dormi", "Rilassati", "Abbassati")));
        getTokenVerbs().add(sit_down);

        TokenVerb climb = new TokenVerb(VerbType.CLIMB);
        climb.setAlias(new HashSet<>(Arrays.asList("Arrampicati", "Sali", "Scendi", "Buttati", "Scivola", "Scavalca", "Salta")));
        getTokenVerbs().add(climb);

        TokenVerb use = new TokenVerb(VerbType.USE);
        use.setAlias(new HashSet<>(Arrays.asList("Usa", "Testa", "Utilizza", "Adopera", "Usufruisci")));
        getTokenVerbs().add(use);

        //Exit from a room, not from the game
        TokenVerb exit = new TokenVerb(VerbType.EXIT);
        exit.setAlias(new HashSet<>(Arrays.asList("Indietreggia", "Ritorna", "Scappa",
                "Spostati", "Togliti", "Levati", "Fuggi")));
        getTokenVerbs().add(exit);

        TokenVerb enter = new TokenVerb(VerbType.ENTER);
        enter.setAlias(new HashSet<>(Arrays.asList("Entra", "Accedi")));
        getTokenVerbs().add(enter);

        TokenVerb accept = new TokenVerb(VerbType.ACCEPT);
        accept.setAlias(new HashSet<>(Arrays.asList("Accetta", "Sì", "Si", "Ok", "Okay", "Conferma", "D'accordo",
                "Perfetto", "Concedi")));
        getTokenVerbs().add(accept);

        TokenVerb decline = new TokenVerb(VerbType.DECLINE);
        decline.setAlias(new HashSet<>(Arrays.asList("Declina", "Rifiuta", "No", "Respingi", "Evita", "Rinuncia")));
        getTokenVerbs().add(decline);

        TokenVerb put_in = new TokenVerb(VerbType.PUT_IN);
        put_in.setAlias(new HashSet<>(Arrays.asList("Immetti", "Inserisci", "Introduci", "Prova")));
        getTokenVerbs().add(put_in);

        TokenVerb give = new TokenVerb(VerbType.GIVE);
        give.setAlias(new HashSet<>(Arrays.asList("Dai", "Lascia", "Cedi", "Dona", "Regala", "Poni", "Concedi", "Porgi",
                "Consegna", "Offri")));
        getTokenVerbs().add(give);

        TokenVerb make = new TokenVerb(VerbType.MAKE);
        make.setAlias(new HashSet<>(Arrays.asList("Fai", "Crea", "Prepara", "Inventa", "Mischia", "Mescola", "Produci",
                "Costruisci", "Fabbrica", "Realizza", "Genera", "Componi", "Origina")));
        getTokenVerbs().add(make);

        TokenVerb remove = new TokenVerb(VerbType.REMOVE);
        remove.setAlias(new HashSet<>(Arrays.asList("Rimuovi", "Leva", "Elimina")));
        getTokenVerbs().add(remove);

        TokenVerb destroy = new TokenVerb(VerbType.DESTROY);
        destroy.setAlias(new HashSet<>(Arrays.asList("Spezza", "Distruggi", "Fracassa", "Sgretola", "Rompi",
                "Frantuma", "Corrodi")));

        TokenVerb move = new TokenVerb(VerbType.MOVE);
        move.setAlias(new HashSet<>(Arrays.asList("Muovi", "Sposta", "Togli")));
        getTokenVerbs().add(move);

    }

    private void initRooms() {
        Room cell = new Room(0, "Cella 17", "Ti trovi nella tua cella 17, al momento sei da solo" +
                " visto che sei l’ultimo arrivato.");
        cell.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo. La stanza e' illuminata da una piccola finestra sbarrata e l’unica via di " +
                "uscita si trova a est, al momento aperta visto che e' l’ora d’aria e tutti i detenuti " +
                "devono raggiungere il giardino!");

        Room corridor = new Room(1, "Corridoio", "Ti trovi nel corridoio del carcere che si" +
                " estende da sud verso nord.");
        corridor.setLook("Si sentono tanti rumori e urla dei detenuti, a ovest la porta della tua cella aperta e" +
                " altre celle in cui non e' possibile entrare poiche' sono chiuse.");

        Room ladders = new Room(2, "Scalinata", "Ti trovi presso una scalinata, l’unica cosa" +
                "che puoi fare e' andare al piano di sotto oppure andare verso sud percorrendo il corridoio.");
        ladders.setLook("Puoi vedere i detenuti che si dirigono verso il giardino.");

        Room lobby = new Room(3, "Atrio", "Ti trovi in un grosso atrio di ingresso, puoi notare" +
                " un enorme porta aperta dove si intravede il giardino.");
        lobby.setLook("Il luogo e' affollato di guardie che controllano la situazione. Vedi a nord un enorme scalinata" +
                " che porta al piano superiore e a ovest le celle degl’altri detenuti. L’atrio si estende ancora " +
                "verso sud.");

        Room garden = new Room(4, "Giardino", "Sei in un ampio giardino verde illuminato " +
                "dal sole, dietro di te la porta che collega al grosso atrio.");
        garden.setLook("Guardando a nord  puoi notare un grosso campo da basket, avanti a te a est un grosso muro " +
                "che separa il giardino dall’esterno con due enormi torri sulle quali ci sono le guardie come vedetta," +
                " a sud invece tre grosse panchine dove puoi sederti e rilassarti.");

        Room basket = new Room(5, "Campo da basket", "Ti trovi nel campo di basket" +
                " momentaneamente vuoto.");
        basket.setLook("Puoi notare due canestri e un pallone in mezzo al campo.");

        Room wall = new Room(6, "Muro prigione", "Avvicinandoti alle mura le guardie ti danno " +
                "l’ordine di indietreggiare, rappresenti un potenziale pericolo. Non penso sia un’idea geniale " +
                "fuggendo da qui, la zona e' troppo controllata.");
        wall.setLook("Non c'e' nulla di particolare tranne che un grosso muro in mattoni!");

        Room bench = new Room(7, "Panchine", "Tutte le panchine sono occupati da un gruppo " +
                "di detenuti che ti guardano con aria sospetta.");
        bench.setLook("Non noti nulla di particolare in loro e nelle panchine, tranne in una dove a terra puoi " +
                "notare un oggetto di metallo simile ad una vite.");

        Room infirmary = new Room(8, "Infermeria", "E' una classica infermeria e ti trovi" +
                " sdraiato sul tuo letto.");
        infirmary.setLook("Aprendo gli occhi noti un tavolo con molti strumenti, un grosso armadio in legno, " +
                "una lavagna, una porta che si collega al corridoio e una finestra sbarrata a nord. Puoi osservare anche " +
                "un quadro di Donald Trump appeso alla parete e in alto un condotto d’aria nuovissimo che sembra " +
                "irraggiungibile.  Sembra non esserci nessuno oltre a te nella stanza, riesci solo ad udire delle " +
                "voci nel corridoio.");

        Room cell1 = new Room(9, "Cella 17", "Caspita gli antidolorifici ti hanno fatto dormire " +
                "molto e ti risvegli nella tua cella privo di qualsiasi dolore! Prima di andare via l’infermiera ti" +
                " ha dato qualche medicinale tra cui un medicinale all’ortica. La porta e' di nuovo aperta," +
                " e' l’ora d’aria, che ne pensi di andare a prendere una bella boccata d’aria?");
        cell1.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo. La stanza è illuminata da una piccola finestra sbarrata e l’unica via di " +
                "uscita si trova a est, al momento aperta!");

        Room passage = new Room(10, "Passaggio segreto", "Sei appena entrato nel passaggio segreto.");
        passage.setLook("Noti delle pareti in roccia un po’ malandate, un’ enorme parete sti blocca la strada" +
                " a nord, puoi solo andare ad sud o a nord o ritornare indietro nella tua cella prima che qualcuno" +
                " ti scopra!!!");
        passage.setLocked(true);

        Room passage_south = new Room(11, "Passaggio segreto", "Prosegui nel passaggio segreto" +
                " e vedi una scala appoggiata al muro.");
        passage_south.setLook("La scala sembra non portare da nessuna parte, infatti sopra ci sono solo" +
                " delle grate che porta al secondo piano in cui non e' possibile accedere. Il passaggio continua" +
                " ancora a sud oppure puoi sempre ritornare indietro prima che sia troppo tardi!");

        Room generator = new Room(12, "Stanza con generatore", "Sembra che il passaggio" +
                " finisca qui, sei in una piccola stanza tutta buia.");
        generator.setLook("Non riesci a vedere nulla tranne che qualche piccola luce lampeggiante di fronte a te!");

        Room passage_north = new Room(13, "Passaggio segreto", "Sembra che sei arrivato gia' " +
                "in un vicolo cieco, vedi solo un enorme soffitto e una piccola grata posta in alto!");
        passage_north.setLook("Non c'e' nient'altro di particolare.");

        Room on_ladder = new Room(14, "Sulla scala", "Sei salito sulla scala e noti un condotto" +
                " d’aria un po’ vecchiotto.");
        on_ladder.setLook("Sembra di aver già visto questo tipo di condotto da un’altra parte!");
        on_ladder.setLocked(true);

        Room air_duct = new Room(15, "Condotto d'aria", "Sei riuscito ad entrare nel condotto," +
                " strisci piano cercando di fare meno rumore possibile.");
        air_duct.setLook("Ci sono molte ragnatele e il condotto sembra non utilizzato, cerca di fare veloce in modo" +
                " da non risultare assente all’appello! Il condotto si divide in tre strade diverse, una a nord," +
                " una ad est e l’altra a ovest.");

        Room air_duct_east = new Room(16, "Condotto d'aria", "Ti ritrovi in un vicolo ceco, " +
                "sotto di te c’e' un’enorme grata.");
        air_duct_east.setLook("Puoi osservare un’altra cella di un detenuto.");

        Room other_cell = new Room(17, "Cella detenuto", "La cella e' controllata da un " +
                "poliziotto e poi non mi sembra il caso di intrufolarsi in una cella di un detenuto. Rischieresti " +
                "di mandare a rotoli il piano!!!");
        other_cell.setLook("Meglio non perdere tempo qui!");
        other_cell.setLocked(true);

        Room air_duct_north = new Room(18, "Condotto d'aria", "Vedi una grossa grata e " +
                "senti delle voci simili a quelle che sentivi quando eri in infermeria.");
        air_duct_north.setLook("C'e' solo una grossa grata");
        air_duct_north.setLocked(true);

        Room air_duct_west = new Room(19, "Condotto d'aria", " Il condotto si fa sempre piu' " +
                "stretto e non riesci piu' a passare! Non sembra sia la strada giusta!");
        air_duct_west.setLook("Scorgi pero' in lontananza un piccolo oggetto!");

        Room canteen = new Room(20, "Mensa", " Ti trovi in una grossa mensa e puoi vedere " +
                "i detenuti che stanno mangiando.");
        canteen.setLook("Uno di loro si avvicina a te e sussurrando ti chiede se sei interessato a qualche oggetto" +
                " che lui possiede. Ovviamente ogni oggetto ha un costo ma tu non possiedi alcun soldo, " +
                "per averne uno quindi sarei costretto a trattare.");

        Room gym = new Room(21, "Palestra", "Ti trovi nella palestra del carcere, " +
                "e' molto grande e non e' tenuta in ottimo stato, alcuni detenuti si stanno allenando.");
        gym.setLook("Intorno a te vedi tanti attrezzi per poterti allenare e aumentare la tua forza.");

        Room out_isolation = new Room(22, "Fuori cella isolamento", "Sei di fronte la cella" +
                " di isolamento dove e' collocato tuo fratello. Essendo in un carcere di massima sicurezza, la porta " +
                "e' controllata da un paio di guardie.");
        out_isolation.setLook("Puoi notare da lontano che non si tratta di una semplice porta ma di una porta che " +
                "puo' essere aperta solo tramite un PIN segreto.");

        Room door_isolation = new Room(23, "Porta isolamento", "Sei di fronte ad una porta" +
                " blindata che come gia' ti sembrava e' possibile aprire tramite un PIN segreto.");
        door_isolation.setLook("Il pin e' conosciuto solo dalle guardie e quindi ti e' impossibile reperirlo!" +
                " A meno che non vuoi iniziare a sparare numeri a caso devi trovare assolutamente un’altra soluzione" +
                " prima che le luci si accendano e le guardie tornino!");
        door_isolation.setLocked(true);

        Room isolation = new Room(24, "Dentro isolamento", "La porta si apre e ti trovi dentro" +
                " il luogo dove si trovano le celle isolamento. Ci sono tre lunghi corridoi, uno a est, uno a " +
                "sud e l’altro a nord!");
        isolation.setLook("Non noti nient’altro di particolare!");
        isolation.setLocked(true);

        Room north_corridor = new Room(25, "Corridoio nord isolamento", "Prosegui nel corridoio" +
                " a nord, ci sono tante celle chiuse di prigionieri in isolamento. Provi a gridare (ma non troppo)," +
                " il nome di tuo fratello ma non risponde nessuno!");
        north_corridor.setLook("Non noti nient’altro di particolare!");

        Room north_corridor1 = new Room(26, "Corridoio nord isolamento", "La speranza e' l’ultima" +
                " a morire ma penso proprio che tuo fratello non si trovi in questo corridoio! ");
        north_corridor1.setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");

        Room north_corridor2 = new Room(27, "Corridoio nord isolamento", "Il corridoio termina" +
                " con una grossa parete di fronte e te, hai visto tutte le celle ma di tuo fratello nemmeno l’ombra, " +
                "te l’avevo detto io!!!");
        north_corridor2.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");

        Room est_corridor = new Room(28, "Corridoio est isolamento", "Prosegui nel corridoio" +
                " a est, ci sono tante celle chiuse di prigionieri in isolamento. Provi a gridare (ma non troppo)," +
                " il nome di tuo fratello ma non risponde nessuno!");
        est_corridor.setLook("Non noti nient’altro di particolare!");

        Room est_corridor1 = new Room(29, "Corridoio est isolamento", "La speranza e' l’ultima" +
                " a morire ma penso proprio che tuo fratello non si trovi in questo corridoio! ");
        est_corridor1.setLook("Puoi solo osservare altre celle di detenuti in cui non e' possibile entrare.");

        Room est_corridor2 = new Room(30, "Corridoio est isolamento", "Il corridoio termina" +
                " con una grossa parete di fronte e te, hai visto tutte le celle ma di tuo fratello nemmeno l’ombra, " +
                "te l’avevo detto io!!!");
        est_corridor2.setLook("Non c'e' nulla, puoi solo ritornare indietro!!!");

        Room south_corridor = new Room(31, "Corridoio sud isolamento", "Prosegui nel corridoio " +
                "a sud, ci sono tante celle chiuse di prigionieri in isolamento. Provi a gridare (ma non troppo)!");
        south_corridor.setLook("Senti un mormorio in lontananza!");

        Room south_corridor1 = new Room(32, "Corridoio sud isolamento", "Il mormorio si fa sempre" +
                " piu' forte ma non hai ancora trovato la cella di tuo fratello.");
        south_corridor1.setLook("Puoi osservare le celle degli altri prigionieri in cui non e' possibile entrare!");

        Room south_corridor2 = new Room(33, "Corridoio sud isolamento", "Il corridoio termina con " +
                "una grossa parete di fronte a te, hai visto tutte le celle tranne l’ultima!");
        south_corridor2.setLook("Avvicinandoti a questa senti la voce di tuo fratello, hai trovato la sua cella!!!");

        Room brother_cell = new Room(34, "Cella fratello", "Sei nella cella di tuo fratello, " +
                "l’aspetto della cella e' ripugnante.");
        brother_cell.setLook("Vedi solo un piccolo lavandino e un water e un letto in legno che sembra scomodissimo." +
                " Di fronte a te, tuo fratello con un’aria contenta di vederti ma allo stesso tempo sorpresa!");

        Room out_isolation1 = new Room(35, "Fuori cella isolamento", "Appena dato il farmaco " +
                "decidi di fuggire fuori la cella isolamento prima che le luci si accendano e le guardie ti scoprano!!!" +
                " Sono le 20:55 hai esattamente 5 minuti per tornare alla tua cella e completare il tuo piano! " +
                "Speri che abbiamo portato tuo fratello in infermeria!");
        out_isolation1.setLook("Sei di fronte la cella di isolamento dove e' collocato tuo fratello. Essendo in un " +
                "carcere di massima sicurezza, la porta e' controllata da un paio di guardie. Puoi notare da lontano" +
                " che non si tratta di una semplice porta ma di una porta che puo' essere aperta solo tramite un" +
                " PIN segreto.");

        Room cell2 = new Room(36, "Cella 17", "Sei arrivato alla tua cella , ad aspettarti " +
                "puntuale c’e' il tuo amichetto Genny. E' ora di attuare il piano!");
        cell2.setLook("La cella e' poco accogliente: e' presente un letto a castello molto scomodo e pieno di polvere, " +
                "un lavandino fissato al muro con delle viti e il water. Sul tavolo c’e' il tuo pranzo che emana " +
                "un odore non buonissimo!");

        Room infirmary1 = new Room(37, "Infermeria", "Sei in infermeria, vedi tuo fratello " +
                "sdraiato sul letto, la porta dell’infermeria e' chiusa a chiave.");
        infirmary1.setLook("Puoi notare il letto di tuo fratello, il vecchio armadio, il tavolo con gli strumenti, " +
                "una finestra a nord e una lavagna appesa al muro. Non c’e' nessun altro siccome e' notte!");

        Room out_window = new Room(38, "Fine", "Decidi di fuggire dalla finestra. Tu e tutta" +
                " la tua squadra usate il lungo cavo che collega la finestra al grande muro della prigione. Arrivati" +
                " sul muro riuscite a scavalcare con molta attenzione il filo spinato presente. Fate tutti un grande" +
                " salto fuori dalla prigione e scappate per 100 metri verso nord. Li trovare ad aspettarvi un " +
                "elicottero guidato da vostro padre, che sapeva tutto del vostro piano segreto e l’ora esatta in cui " +
                "dovevate attuarlo! Salite tutti sull’elicottero e' fuggite tutti insieme verso il Messico cosicché" +
                " nessuno potrà piu' ritrovarvi!\n\n" +
                "COMPLIMENTI HAI VINTO!");
        out_window.setLocked(true);
        out_window.setLook("");

        Room window = new Room(39, "Finestra infermeria", "La finestra e' sbarrata e non" +
                " sembra possibile aprirla! Puoi notare un lungo cavo che porta fino al muro della prigione!");
        window.setLook("Non noti nient'altro di particolare!");

        Room front_bench = new Room(40, "Di fronte alle panchine", "Ti avvicini alla panchina" +
                " per controllare meglio l’oggetto ma vieni subito fermato da un gruppo di neri che con aria " +
                "minacciosa ti chiedono di allontanarti percha' la panchina e' la loro. Cosa scegli di fare?");
        front_bench.setLook("Vedi il gruppo di neri che ti fissa aspettando una tua mossa, non credo sia l’idea " +
                "migliore restare lì impalato.");

        Room brawl = new Room(41, "Rissa", "Sai benissimo che in un carcere non si possono" +
                " comprare panchine e ti avvicini nuovamente con l’intendo di prendere l’oggetto. Il gruppetto " +
                "ti blocca e il piu' grosso di loro ti tira un pugno contro il viso... \n Perdendo i sensi" +
                " non ti ricordi piu' nulla e ti svegli in infermeria.");
        brawl.setLook("");

        Room brawl1 = new Room(42, "Rissa", "Riesci subito a tirare fuori il bisturi dalla " +
                "tasca, il gruppetto lo vede e capito il pericolo decide di lasciare stare (Mettere a rischio la " +
                "vita per una panchina sarebbe veramente stupido) e vanno via con un’aria di vendetta. Ora sei solo " +
                "vicino alla panchina.");
        brawl1.setLook("E' una grossa panchina in legno un po’ malandata, ci sei solo tu nelle vicinanze.");
        brawl1.setLocked(true);

        Room south_lobby = new Room(43, "Atrio", "Ti trovi a sud del grosso atrio di ingresso." +
                " Puoi notare che l'atrio prosegue sia a nord che a sud!");
        south_lobby.setLook("Puoi notare a ovest le celle chiuse dei detenuti e a est la palestra.");

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
        infirmary1.setSouth(air_duct_north);
        infirmary.setSouth(air_duct_north);
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

        TokenObject screw = new TokenObject(1, new HashSet<>(Arrays.asList("Vite", "Chiodo")),
                "E' una semplice vite con inciso il numero di serie: 11121147.");
        brawl1.getObjects().add(screw);

        TokenObject scotch = new TokenObject(2, new HashSet<>(Arrays.asList("Scotch", "Nastro")),
                "E' un semplice scotch, dimenticato li forse da qualche operaio!");
        air_duct_west.getObjects().add(scotch);

        TokenObject tools = new TokenObject(3, new HashSet<>(Arrays.asList("Attrezzi", "Manubri", "Pesi")),
                "Sono degli attrezzi da palestra, ottimi per allenarsi e aumentare la forza!");
        tools.setPickupable(false);
        tools.setUsable(true);
        gym.getObjects().add(tools);

        TokenObject food = new TokenObject(4, new HashSet<>(Arrays.asList("Cibo", "Pranzo", "Cena", "Piatto",
                "Tavolo")),
                "C'è solo il tuo pranzo, puoi mangiarlo anche se non servirà a nulla.");
        tools.setEatable(true);
        cell.getObjects().add(food);
        cell1.getObjects().add(food);
        cell2.getObjects().add(food);

        TokenObject ball = new TokenObject(5, new HashSet<>(Arrays.asList("Palla", "Pallone", "Basketball")),
                "E' un semplice pallone da basket.");
        ball.setUsable(true);
        basket.getObjects().add(ball);

        TokenObject ladder = new TokenObject(6, new HashSet<>(Arrays.asList("Scala", "Scaletta")),
                "E' solo una scala in legno, sembra molto leggera e facile da spostare.");
        ladder.setPushable(true);
        passage_south.getObjects().add(ladder);

        TokenObject scalpel = new TokenObject(7, new HashSet<>(Arrays.asList("Bisturi", "Lama")),
                "Sono solo tanti bisturi tutti uguali!");
        scalpel.setUsable(true);
        infirmary.getObjects().add(scalpel);

        //TODO assegnare oggetto a Jonny Bello
        TokenObject hacksaw = new TokenObject(8, new HashSet<>(Arrays.asList("Seghetto", "Sega", "Taglierino")),
                "E’ un seghetto molto affilato, potresti riuscire a rompere qualcosa.",
                new HashSet<>(Arrays.asList(new TokenAdjective(new HashSet<>(Arrays.asList("Rotto", "Distrutto",
                        "Devastato", "Spaccato"))), new TokenAdjective(new HashSet<>(Arrays.asList("Aggiustato",
                        "Sistemato", "Riparato"))))));
        scalpel.setUsable(true);
        cell.getObjects().add(hacksaw);

        TokenObject substances = new TokenObject(9, new HashSet<>(Arrays.asList("Sostanze", "Ingredienti", "Acido",
                "Oggetti")),
                "Sul tavolo puoi vedere alcuni strumenti di lavoro e alcune sostanze come: Cloruro " +
                        "di sodio, acido solforico e altre sostanze di cui non riesco nemmeno a leggere il nome!");
        substances.setUsable(true);
        substances.setMixable(true);
        infirmary1.getObjects().add(substances);

        //TODO da mettere nell'inventario nella prima fase del gioco
        TokenObject medicine = new TokenObject(10, new HashSet<>(Arrays.asList("farmaco", "medicina", "compresse",
                "sciroppo")),
                "E' un medicinale per alleviare i dolori.");
        medicine.setGive(true);

        TokenObject sink = new TokenObject(11, new HashSet<>(Arrays.asList("Lavandino", "Lavello", "Lavabo")),
                "E' un piccolo lavandino fissato al muro con delle viti arruginite... Ha un aspetto " +
                        "malandato!");
        sink.setPickupable(false);
        sink.setUsable(true);
        cell.getObjects().add(sink);
        cell1.getObjects().add(sink);
        cell2.getObjects().add(sink);
        brother_cell.getObjects().add(sink);

        TokenObject bed = new TokenObject(12, new HashSet<>(Arrays.asList("Letto", "Lettino", "Brandina",
                "Lettuccio")),
                "E' un letto in legno molto vecchio e sembra anche molto scomodo!");
        bed.setPickupable(false);
        cell.getObjects().add(bed);
        cell1.getObjects().add(bed);
        cell2.getObjects().add(bed);
        brother_cell.getObjects().add(bed);

        TokenObject table = new TokenObject(14, new HashSet<>(Arrays.asList("Tavolo", "Tavolino", "Scrivania")),
                "E' un semplice tavolo in legno, molto piccolo e molto sporco!");
        table.setPickupable(false);
        cell.getObjects().add(table);
        cell1.getObjects().add(table);
        cell2.getObjects().add(table);

        TokenObject windowCell = new TokenObject(15, new HashSet<>(Arrays.asList("Finestra")),
                "E' una piccola finestra dalla quale puoi osservare il cortile della prigione! " +
                        "Bel panorama!!!");
        windowCell.setPickupable(false);
        cell.getObjects().add(windowCell);
        cell1.getObjects().add(windowCell);
        cell2.getObjects().add(windowCell);

        TokenObject water = new TokenObject(16, new HashSet<>(Arrays.asList("Water", "Cesso", "Gabinetto",
                "Tazza", "Wc")),
                "Un water qualunque, senza nessun particolare, non penso sia bello osservarlo");
        water.setPickupable(false);
        cell.getObjects().add(water);
        cell1.getObjects().add(water);
        cell2.getObjects().add(water);
        brother_cell.getObjects().add(water);

        TokenObject railing = new TokenObject(16, new HashSet<>(Arrays.asList("Ringhiera", "Scorrimano")),
                "Sei troppo giovane per camminare appoggiato ad una ringhiera e troppo giovane " +
                        "per buttarti, ti prego non farlo!!!");
        railing.setPickupable(false);
        corridor.getObjects().add(railing);

        TokenObject door = new TokenObject(17, new HashSet<>(Arrays.asList("Porta", "Portone", "Ingresso")),
                "E' una grande porta che separa giardino e atrio, e' sempre aperta e non puoi chiuderla!");
        door.setPickupable(false);
        garden.getObjects().add(door);
        lobby.getObjects().add(door);

        TokenObject basketObject = new TokenObject(18, new HashSet<>(Arrays.asList("Canestro", "Basket")),
                "Sono due canestri, ottimi per giocare a basket e perdere tempo!!!");
        basketObject.setPickupable(false);
        basket.getObjects().add(basketObject);

        TokenObject blackboard = new TokenObject(19, new HashSet<>(Arrays.asList("Lavagna", "Lavagnetta")),
                "Vedi scritto tante ricette tra cui quella per creare l’acido cloridico! ");
        blackboard.setPickupable(false);
        infirmary.getObjects().add(blackboard);
        infirmary1.getObjects().add(blackboard);

        TokenObject windowsInfirmary = new TokenObject(20, new HashSet<>(Arrays.asList("Finestra")),
                "La finestra è sbarrata non sembra possibile aprirla! Puoi notare un lungo cavo che porta" +
                        " fino al muro della prigione!");
        windowsInfirmary.setPickupable(false);
        infirmary.getObjects().add(windowsInfirmary);
        infirmary1.getObjects().add(windowsInfirmary);

        TokenObject tableInfirmary = new TokenObject(21, new HashSet<>(Arrays.asList("Tavolo", "Tavolino")),
                "Noti vari oggetti, alcuni non sai nemmeno a cosa possano servire, in particolare in un" +
                        " cassetto ci sono una decina di bisturi. Non penso che qualcuno se ne accorga se ne prendi " +
                        "uno solo!");
        tableInfirmary.setPickupable(false);
        infirmary.getObjects().add(tableInfirmary);

        TokenObject picture = new TokenObject(22, new HashSet<>(Arrays.asList("Quadro", "Tramp", "Dipinto",
                "Ritratto", "Foto", "Fotografia")),
                " Il presidente ha un sorriso smagliante e uno sguardo felice, perché proprio quel quadro " +
                        "li?");
        picture.setPickupable(false);
        picture.setPushable(true);
        infirmary.getObjects().add(picture);
        infirmary1.getObjects().add(picture);

        TokenObject doorInfirmary = new TokenObject(23, new HashSet<>(Arrays.asList("Porta")),
                " la porta è chiusa, su un foglietto puoi leggere che potrai uscire solo quando" +
                        " l’infermiere verrà a dirtelo. Mi dispiace devi attendere, puoi continuare a controllare " +
                        "la stanza. ");
        doorInfirmary.setPickupable(false);
        infirmary.getObjects().add(doorInfirmary);
        infirmary1.getObjects().add(doorInfirmary);

        TokenObjectContainer wardrobe = new TokenObjectContainer(24, new HashSet<>(Arrays.asList("Armadio",
                "Guardaroba", "Armadietto")),
                "E' un semplice armadio in legno molto antico pieno di camici per infermieri, " +
                        "non noti nulla di particolare.");
        wardrobe.setOpenable(true);
        wardrobe.setPickupable(false);
        wardrobe.setOpen(false);
        infirmary.getObjects().add(wardrobe);
        infirmary1.getObjects().add(wardrobe);

        TokenObject gowns = new TokenObject(25, new HashSet<>(Arrays.asList("Camici", "Camice", "Vestito",
                "Vestiti")),
                "Combinazione non esiste uno della tua misura, che peccato!!! È inutile prenderne un altro");
        gowns.setPickupable(false);
        wardrobe.add(gowns);

        TokenObject newAirDuct = new TokenObject(26, new HashSet<>(Arrays.asList("Condotto", "Passaggio")),
                "Non sei un campione di arrampicata o salto in alto, perché perdere tempo qui!");
        newAirDuct.setPickupable(false);
        infirmary.getObjects().add(newAirDuct);
        infirmary1.getObjects().add(newAirDuct);

        TokenObject grate = new TokenObject(27, new HashSet<>(Arrays.asList("Grata", "Grate")),
                "Puoi notare solo il piano superiore, ma non puoi fare nient' altro");
        grate.setPickupable(false);
        passage_south.getObjects().add(grate);

        TokenObject generatorObject = new TokenObject(28, new HashSet<>(Arrays.asList("Generatore")),
                "C'è un enorme pulsante rosso con una scritta che vieta di premerlo!!!");
        generatorObject.setPickupable(false);
        generatorObject.setUsable(true);
        generator.getObjects().add(generatorObject);

        TokenObject button = new TokenObject(29, new HashSet<>(Arrays.asList("Pulsante", "Bottone",
                "Interruttore")),
                "C'è un enorme pulsante rosso con una scritta che vieta di premerlo!!!");
        button.setPickupable(false);
        button.setPushable(true);
        generator.getObjects().add(button);

        TokenObject gratePassage = new TokenObject(30, new HashSet<>(Arrays.asList("Grata")),
                "La cella è controllata da un poliziotto e poi non mi sembra il caso di intrufolarsi" +
                        " in una cella di un detenuto. Rischieresti di mandare a rotoli il piano!!!");
        gratePassage.setPickupable(false);
        air_duct_east.getObjects().add(gratePassage);

        TokenObject drug = new TokenObject(31, new HashSet<>(Arrays.asList("Droga", "Stupefacenti")),
                "Meglio continuare il piano di fuga da lucidi e fortunatamente non hai soldi con te per" +
                        " acquistarla! Ti ricordo che il tuo piano è fuggire di prigione e non rimanerci qualche " +
                        "anno di più!");
        drug.setPickupable(false);
        //TODO ASSEGNARE DROGA A GENNY

        TokenObject videogame = new TokenObject(32, new HashSet<>(Arrays.asList("VideoGame", "Gioco",
                "Videogioco")),
                "Sarebbe molto bello se solo avessi 8 anni! Quando uscirai di prigione avrai molto tempo " +
                        "per giocare anche a videogiochi migliori!");
        videogame.setPickupable(false);
        //TODO ASSEGNARE DROGA A GENNY

        TokenObject acid = new TokenObject(33, new HashSet<>(Arrays.asList("Acido")),
                "Leggendo la ricetta alla lavagna capisci come creare l’acido, mischi le sostanze " +
                        "tutte insieme utilizzando le giuste dosi in modo da non sbagliare! Sei riuscito a" +
                        " creare l’acido!");
        acid.setUsable(true);
        //TODO NON E' ASSEGNATO A NULLA
    }

    @Override
    public void init() {
        initVerbs();
        initRooms();

        //Set starting room
        setCurrentRoom(getRooms().stream()
                .filter(room -> room.getId() == 0)
                .findFirst()
                .orElse(null));

        //Set Inventory
        setInventory(new Inventory(5));
    }

    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        int minScore = getIncreaseScore();
        boolean move = false;
        boolean noroom = false;

        try {

            if (p.getVerb().getVerbType().equals(VerbType.NORD)) {
                if (getCurrentRoom().getNorth() != null && !getCurrentRoom().getNorth().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.SOUTH)) {
                if (getCurrentRoom().getSouth() != null && !getCurrentRoom().getSouth().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.EAST)) {
                if (getCurrentRoom().getEast() != null && !getCurrentRoom().getEast().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.WEST)) {
                if (getCurrentRoom().getWest() != null && !getCurrentRoom().getWest().isLocked()) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.INVENTORY)) {
                if (!getInventory().isEmpty()) {
                    out.println("Nel tuo inventario ci sono:");
                }
                for (TokenObject o : getInventory().getObjects()) {
                    out.println(o.getAlias().iterator().next() + ": " + o.getDescription());
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.LOOK_AT)) {
                if (getCurrentRoom().getLook() != null) {
                    out.println(getCurrentRoom().getLook());
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }
            } else if (p.getVerb().getVerbType().equals(VerbType.PICK_UP)) {
                if (p.getObject() != null && p.getObject().isPickupable()
                        && getCurrentRoom().getObjects().contains(p.getObject())) {
                    getInventory().add(p.getObject());
                } else if (p.getObject() == null) {
                    out.println("Cosa vorresti prendere di preciso?");
                } else if (!p.getObject().isPickupable()) {
                    out.println("Non e' certo un oggetto che si può prendere imbecille!");
                } else if (!getCurrentRoom().getObjects().contains(p.getObject())) {
                    out.println("Questo oggetto lo vedi solo nei tuoi sogni!");
                } else if (getInventory().contains(p.getObject())) {
                    out.println("Guarda bene nella tua borsa, cretino!");
                }
            }

            // while (getScore() < minScore) {
            //TODO PRENDERE IL BISTURI PER AUMENTARE IL PUNTEGGIO

            /* //TODO per passare allo step successivo eseguire queste istruzioni
            increaseScore();
            minScore += getIncreaseScore();
             */
            //}

            //while (getScore() < getIncreaseScore() * 2) {
            //TODO PRENDERE LA VITE
            //}

            //while (getScore() < getIncreaseScore() * 3) {

            //}
            if (noroom) {
                out.println("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri per oltrepassare i muri...");
            } else if (move) {
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                out.println(getCurrentRoom().getDescription());
            }
        } catch (InventoryEmptyException iee) {
            out.println("L'inventario è vuoto!");

        } catch (InventoryFullException ife) {
            out.println("Non puoi mettere più elementi nel tuo inventario!");
            out.println("!!!!Non hai mica la borsa di Mary Poppins!!!!!");
        }
    }
}

