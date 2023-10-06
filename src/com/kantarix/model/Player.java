package com.kantarix.model;

public class Player extends Creature {
    private final int MAX_HEALING_COUNT = 4;
    private final float HEALING_PERCENT = 0.3f;
    private int healingCount = 0;
    private final float healingPower;
    public final float initialHealth;

    public Player(int attack, int defense, float health, int minDamage, int maxDamage) {
        super(attack, defense, health, minDamage, maxDamage);
        this.initialHealth = health;
        this.healingPower = initialHealth * HEALING_PERCENT;
    }

    public void heal() {
        if (healingCount == MAX_HEALING_COUNT) {
            System.out.println("Количество исцелений закончилось.");
            return;
        }

        healingCount++;
        float newHealth = Math.min(getHealth() + healingPower, initialHealth);
        setHealth(newHealth);

        System.out.printf("Здоровье восстановлено до %.2f/%.2f.", getHealth(), initialHealth);
    }

}
