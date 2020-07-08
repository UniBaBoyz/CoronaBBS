package adventure.games.prisonbreak;

import adventure.games.GameDescription;
import adventure.games.VerbsInterface;
import adventure.type.TokenVerb;
import adventure.type.VerbType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Corona-Extra
 */
public class Verbs implements VerbsInterface {

    @Override
    public void initVerbs(GameDescription game) {
        initMovementVerbs(game);
        initBasicVerbs(game);
        initAdvancedVerbs(game);
    }

    private void initMovementVerbs(GameDescription game) {
        TokenVerb nord = new TokenVerb(VerbType.NORD);
        nord.setAlias(new HashSet<>(Collections.singletonList("Nord")));
        game.getTokenVerbs().add(nord);

        TokenVerb sud = new TokenVerb(VerbType.SOUTH);
        sud.setAlias(new HashSet<>(Collections.singletonList("Sud")));
        game.getTokenVerbs().add(sud);

        TokenVerb est = new TokenVerb(VerbType.EAST);
        est.setAlias(new HashSet<>(Collections.singletonList("Est")));
        game.getTokenVerbs().add(est);

        TokenVerb ovest = new TokenVerb(VerbType.WEST);
        ovest.setAlias(new HashSet<>(Collections.singletonList("Ovest")));
        game.getTokenVerbs().add(ovest);

    }

    private void initBasicVerbs(GameDescription game) {
        TokenVerb look = new TokenVerb(VerbType.LOOK_AT);
        look.setAlias(new HashSet<>(Arrays.asList("Osserva", "Guarda", "Vedi", "Trova", "Cerca", "Descrivi",
                "Controlla", "Leggi")));
        game.getTokenVerbs().add(look);

        TokenVerb pickup = new TokenVerb(VerbType.PICK_UP);
        pickup.setAlias(new HashSet<>(Arrays.asList("Prendi", "Afferra", "Ottieni", "Raccogli")));
        game.getTokenVerbs().add(pickup);

        TokenVerb open = new TokenVerb(VerbType.OPEN);
        open.setAlias(new HashSet<>(Arrays.asList("Sblocca", "Apri")));
        game.getTokenVerbs().add(open);

        TokenVerb close = new TokenVerb(VerbType.CLOSE);
        close.setAlias(new HashSet<>(Arrays.asList("Chiudi", "Sbarra", "Richiudi")));
        game.getTokenVerbs().add(close);

        TokenVerb inventory = new TokenVerb(VerbType.INVENTORY);
        inventory.setAlias(new HashSet<>(Arrays.asList("Inv", "I", "Inventario")));
        game.getTokenVerbs().add(inventory);

        TokenVerb remove = new TokenVerb(VerbType.REMOVE);
        remove.setAlias(new HashSet<>(Arrays.asList("Rimuovi", "Leva", "Elimina", "Lascia")));
        game.getTokenVerbs().add(remove);

        TokenVerb pull = new TokenVerb(VerbType.PULL);
        pull.setAlias(new HashSet<>(Arrays.asList("Trascina", "Tira")));
        game.getTokenVerbs().add(pull);

        TokenVerb turnOn = new TokenVerb(VerbType.TURN_ON);
        turnOn.setAlias(new HashSet<>(Arrays.asList("Accendi", "On")));
        game.getTokenVerbs().add(turnOn);

        TokenVerb turnOff = new TokenVerb(VerbType.TURN_OFF);
        turnOff.setAlias(new HashSet<>(Arrays.asList("Spegni", "Off")));
        game.getTokenVerbs().add(turnOff);

        TokenVerb end = new TokenVerb(VerbType.END);
        end.setAlias(new HashSet<>(Arrays.asList("End", "Addio", "Exit", "Fine", "Esci", "Muori", "Ammazzati", "Ucciditi",
                "Suicidati", "Crepa")));
        game.getTokenVerbs().add(end);

        TokenVerb push = new TokenVerb(VerbType.PUSH);
        push.setAlias(new HashSet<>(Arrays.asList("Premi", "Spingi", "Attiva", "Schiaccia",
                "Pigia", "Togli", "Sposta")));
        game.getTokenVerbs().add(push);

        TokenVerb use = new TokenVerb(VerbType.USE);
        use.setAlias(new HashSet<>(Arrays.asList("Usa", "Testa", "Utilizza", "Adopera", "Usufruisci")));
        game.getTokenVerbs().add(use);
    }

    private void initAdvancedVerbs(GameDescription game) {
        TokenVerb talk = new TokenVerb(VerbType.TALK_TO);
        talk.setAlias(new HashSet<>(Arrays.asList("Parla", "Chiacchiera", "Comunica", "Dialoga", "Conversa",
                "Ascolta", "Grida", "Urla", "Mormora", "Sussurra", "Bisbiglia", "Conferisci")));
        game.getTokenVerbs().add(talk);

        TokenVerb faceUp = new TokenVerb(VerbType.FACE_UP);
        faceUp.setAlias(new HashSet<>(Arrays.asList("Affronta", "Affrontali", "Attacca", "Mena", "Azzuffati", "Litiga",
                "Scontrati", "Lotta", "Combatti", "Attaccali", "Menali", "Picchia", "Picchiali")));
        game.getTokenVerbs().add(faceUp);

        TokenVerb ask = new TokenVerb(VerbType.ASK);
        ask.setAlias(new HashSet<>(Arrays.asList("Chiedi", "Domanda", "Desidero")));
        game.getTokenVerbs().add(ask);

        TokenVerb eat = new TokenVerb(VerbType.EAT);
        eat.setAlias(new HashSet<>(Arrays.asList("Mangia", "Pranza", "Cena", "Divora")));
        game.getTokenVerbs().add(eat);

        TokenVerb play = new TokenVerb(VerbType.PLAY);
        play.setAlias(new HashSet<>(Arrays.asList("Gioca", "Divertiti", "Giocherella")));
        game.getTokenVerbs().add(play);

        TokenVerb workOut = new TokenVerb(VerbType.WORK_OUT);
        workOut.setAlias(new HashSet<>(Arrays.asList("Allenati", "Allena")));
        game.getTokenVerbs().add(workOut);

        TokenVerb standUp = new TokenVerb(VerbType.STAND_UP);
        standUp.setAlias(new HashSet<>(Arrays.asList("Alzati", "Svegliati")));
        game.getTokenVerbs().add(standUp);

        TokenVerb sitDown = new TokenVerb(VerbType.SIT_DOWN);
        sitDown.setAlias(new HashSet<>(Arrays.asList("Siediti", "Sdraiati", "Dormi", "Rilassati", "Abbassati")));
        game.getTokenVerbs().add(sitDown);

        TokenVerb climb = new TokenVerb(VerbType.CLIMB);
        climb.setAlias(new HashSet<>(Arrays.asList("Arrampicati", "Sali", "Su")));
        game.getTokenVerbs().add(climb);

        TokenVerb getOff = new TokenVerb(VerbType.GET_OFF);
        getOff.setAlias(new HashSet<>(Arrays.asList("Scendi", "Scivola", "Giu", "Giù")));
        game.getTokenVerbs().add(getOff);

        //Exit from a room, not from the game
        TokenVerb exit = new TokenVerb(VerbType.EXIT);
        exit.setAlias(new HashSet<>(Arrays.asList("Indietreggia", "Ritorna", "Scappa",
                "Spostati", "Togliti", "Levati", "Fuggi")));
        game.getTokenVerbs().add(exit);

        TokenVerb enter = new TokenVerb(VerbType.ENTER);
        enter.setAlias(new HashSet<>(Arrays.asList("Entra", "Accedi")));
        game.getTokenVerbs().add(enter);

        TokenVerb accept = new TokenVerb(VerbType.ACCEPT);
        accept.setAlias(new HashSet<>(Arrays.asList("Accetta", "Sì", "Si", "Ok", "Okay", "Conferma", "D'accordo",
                "Perfetto", "Concedi")));
        game.getTokenVerbs().add(accept);

        TokenVerb decline = new TokenVerb(VerbType.DECLINE);
        decline.setAlias(new HashSet<>(Arrays.asList("Declina", "Rifiuta", "No", "Respingi", "Evita", "Rinuncia")));
        game.getTokenVerbs().add(decline);

        TokenVerb put_in = new TokenVerb(VerbType.PUT_IN);
        put_in.setAlias(new HashSet<>(Arrays.asList("Immetti", "Inserisci", "Introduci", "Prova")));
        game.getTokenVerbs().add(put_in);

        TokenVerb give = new TokenVerb(VerbType.GIVE);
        give.setAlias(new HashSet<>(Arrays.asList("Dai", "Cedi", "Dona", "Regala", "Poni", "Concedi", "Porgi",
                "Consegna", "Offri")));
        game.getTokenVerbs().add(give);

        TokenVerb make = new TokenVerb(VerbType.MAKE);
        make.setAlias(new HashSet<>(Arrays.asList("Fai", "Crea", "Prepara", "Inventa",
                "Mischia", "Mescola", "Produci", "Realizza", "Genera", "Componi", "Origina")));
        game.getTokenVerbs().add(make);

        TokenVerb remove = new TokenVerb(VerbType.REMOVE);
        remove.setAlias(new HashSet<>(Arrays.asList("Rimuovi", "Leva", "Elimina", "Lascia")));
        game.getTokenVerbs().add(remove);

        TokenVerb destroy = new TokenVerb(VerbType.DESTROY);
        destroy.setAlias(new HashSet<>(Arrays.asList("Spezza", "Distruggi", "Fracassa", "Sgretola", "Rompi",
                "Frantuma", "Corrodi")));
        game.getTokenVerbs().add(destroy);
    }
}
