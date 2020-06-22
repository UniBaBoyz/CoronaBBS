/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak;

//fixme import prisonbreak.games.FireHouseGame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import prisonbreak.games.PrisonBreakGame;
import prisonbreak.parser.Parser;
import prisonbreak.parser.ParserIta;
import prisonbreak.parser.ParserOutput;
import prisonbreak.type.TokenObject;
import prisonbreak.type.TokenVerb;
import prisonbreak.type.VerbType;

/**
 * ATTENZIONE: l'Engine è molto spartano, in realtà demanda la logica alla
 * classe che implementa GameDescription e si occupa di gestire I/O sul
 * terminale.
 *
 * @author pierpaolo
 */
public class Engine {

    private final GameDescription game;

    private Parser parser;

    public Engine(GameDescription game) {
        this.game = game;
        try {
            this.game.init();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Engine engine = new Engine(new PrisonBreakGame());
        engine.run();
        ParserIta p = new ParserIta(new HashSet<>(Arrays.asList(new TokenVerb(VerbType.USE, new HashSet<>(Arrays.asList("usa", "utilizza"))), new TokenVerb(VerbType.PICK_UP, new HashSet<>(Arrays.asList("prendi", "raccogli"))))),
                new HashSet<>(Arrays.asList(new TokenObject(0, "Computer", new HashSet<>(Arrays.asList("computer", "calcolatore"))), new TokenObject(1, "Mouse", new HashSet<>(Arrays.asList("mouse", "touchpad"))))),
                new HashSet<>(Arrays.asList("Bianco", "Rotto")));

        try {
            List<ParserOutput> pi = p.parse("Inserisci frase qui");
            System.out.println(pi);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void run() {
        parser = new ParserIta(game.getTokenVerbs(), game.getObjects(), game.getAdjectives());
        System.out.println(game.getCurrentRoom().getName());
        System.out.println("================================================");
        System.out.println(game.getCurrentRoom().getDescription());
        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            try {
                ParserOutput p = parser.parse(input);
                if (p.getVerb() != null && p.getVerb().getType().equals(VerbType.END)) {
                    System.out.println("Addio!");
                    break;
                } else {
                    game.nextMove(p, System.out);
                    System.out.println("================================================");
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

}
