package adventure.games.firehouse;

import adventure.games.GameDescription;
import adventure.games.VerbsInterface;
import adventure.type.TokenVerb;
import adventure.type.VerbType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author pierpaolo
 */
public class FireHouseVerbs implements VerbsInterface {

    @Override
    public void initVerbs(GameDescription game) {
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

        TokenVerb end = new TokenVerb(VerbType.END);
        end.setAlias(new HashSet<>(Arrays.asList("End", "Addio", "Exit", "Fine", "Esci", "Muori", "Ammazzati", "Ucciditi",
                "Suicidati", "Crepa")));
        game.getTokenVerbs().add(end);

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

        TokenVerb push = new TokenVerb(VerbType.PUSH);
        push.setAlias(new HashSet<>(Arrays.asList("Premi", "Spingi", "Attiva", "Schiaccia",
                "Pigia", "Togli", "Sposta")));
        game.getTokenVerbs().add(push);
    }
}
