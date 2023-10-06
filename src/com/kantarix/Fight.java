package com.kantarix;

import com.kantarix.model.Creature;
import com.kantarix.model.Monster;
import com.kantarix.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fight {
    private Player player;
    private List<Monster> monsters = new ArrayList<>();
    private Random random = new Random();

    private final int MAX_HEALTH_EXAMPLE = 40;
    private final int MIN_DAMAGE_EXAMPLE = 1;
    private final int MAX_DAMAGE_EXAMPLE = 6;
    private final int MAX_MONSTERS_COUNT_EXAMPLE = 2;

    public Fight() {
        createPlayer();
        createMonsters();
    }

    public void generateFight() {
        while (!monsters.isEmpty()) {
            attack(player, monsters.get(0));
            attack(monsters.get(0), player);
            if (player.getHealth() < player.initialHealth * 0.7f) {
                player.heal();
            }
        }
    }

    private Creature createMonster() {
        int attack = generateAttackOrDefense();
        int defense = generateAttackOrDefense();
        float health = generateHealth();
        return new Monster(attack, defense, health, MIN_DAMAGE_EXAMPLE, MAX_DAMAGE_EXAMPLE);
    }

    private void createPlayer() {
        if (player == null) {
            int attack = generateAttackOrDefense();
            int defense = generateAttackOrDefense();
            float health = generateHealth();
            player = new Player(attack, defense, health, MIN_DAMAGE_EXAMPLE, MAX_DAMAGE_EXAMPLE);
        }
    }

    private void createMonsters() {
        int monsters_count = 1 + random.nextInt(MAX_MONSTERS_COUNT_EXAMPLE);
        for (int i = 0; i < monsters_count; i++)
            monsters.add((Monster) createMonster());
    }

    private void attack(Creature attacking, Creature defending) {
        System.out.print("Атака " + attacking.getClass().getSimpleName() + "(атака " + attacking.getAttack() + ") -> " +
                defending.getClass().getSimpleName() + "(защита " + defending.getDefense() + "): ");
        boolean attackResult = attacking.attack(defending);
        handleAttackResult(defending, attackResult);
    }

    private void handleAttackResult(Creature defending, boolean attackResult) {
        if (attackResult) {
            if (defending.isAlive() && defending instanceof Monster) {
                System.out.println("Атака успешна. Здоровье монстра: " + defending.getHealth());
            } else if (defending.isDead() && defending instanceof Monster) {
                monsters.remove(defending);
                System.out.println("Атака успешна. Монстр повержен.");
                if (monsters.isEmpty()) {
                    System.out.println("Все монстры повержены. Конец игры.");
                    endGame();
                }
            } else if (defending.isAlive() && defending instanceof Player) {
                System.out.printf("Атака успешна. Здоровье игрока: %.2f\n", defending.getHealth());
            } else if (defending.isDead() && defending instanceof Player) {
                System.out.println("Атака успешна. Игрок повержен. Конец игры.");
                endGame();
            }
        } else {
            System.out.println("Атака не увенчалась успехом.");
        }
    }

    private void endGame() {
        System.exit(0);
    }

    private int generateAttackOrDefense() {
        return Creature.MIN_ATTACK_DEFENSE + random.nextInt(Creature.MAX_ATTACK_DEFENSE - Creature.MIN_ATTACK_DEFENSE + 1);
    }

    private float generateHealth() {
        return Creature.MIN_HEALTH + random.nextInt(MAX_HEALTH_EXAMPLE - Creature.MIN_HEALTH + 1);
    }
}
