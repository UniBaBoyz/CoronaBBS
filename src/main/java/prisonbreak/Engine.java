/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prisonbreak;

import prisonbreak.games.FireHouseGame;
import prisonbreak.parser.ParserOutput;
import prisonbreak.parser.ParserTemp;
import prisonbreak.type.VerbType;

import java.util.Scanner;

/**
 * ATTENZIONE: l'Engine è molto spartano, in realtà demanda la logica alla
 * classe che implementa GameDescription e si occupa di gestire I/O sul
 * terminale.
 *
 * @author pierpaolo
 */
public class Engine {

    private final GameDescription game;

    private final ParserTemp parser;

    public Engine(GameDescription game) {
        this.game = game;
        try {
            this.game.init();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        parser = new ParserTemp();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Engine engine = new Engine(new FireHouseGame());
        //engine.run();
        /*ParserIta p =  new ParserIta();

        try {
            if(p.parse("utilizza il computer bianco dopodichè prendi il bianco mouse")) {
                System.out.println("Tutto ok");
            } else {
                System.out.println("Sbagliato");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }*/
    }

    public void run() {
        System.out.println(game.getCurrentRoom().getName());
        System.out.println("================================================");
        System.out.println(game.getCurrentRoom().getDescription());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getTokenVerbs(), game.getCurrentRoom().getObjects(), game.getInventory());
            if (p.getVerb() != null && p.getVerb().getType() == VerbType.END) {
                System.out.println("Addio!");
                break;
            } else {
                game.nextMove(p, System.out);
                System.out.println("================================================");
            }
        }
    }

}
