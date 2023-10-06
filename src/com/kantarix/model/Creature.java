package com.kantarix.model;

import java.util.Random;

public abstract class Creature {
    Random random = new Random();
    private final int DICE_SIDES = 6;
    public static final int MIN_HEALTH = 1;
    public static final int MIN_ATTACK_DEFENSE = 1;
    public static final int MAX_ATTACK_DEFENSE = 30;

    private final int attack;
    private final int defense;
    private float health;
    private final int minDamage;
    private final int maxDamage;

    protected Creature(int attack, int defense, float health, int minDamage, int maxDamage) {
        checkParametersValid(attack, defense, health, minDamage, maxDamage);
        this.attack = attack;
        this.defense = defense;
        this.health = health;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public boolean attack(Creature creature) {
        int attackModifier = this.attack - creature.defense + 1;
        // доп шанс при невозможности ударить врага с очень большой защитой
        if (attackModifier <= 0) {
            System.out.print("уровень атаки слишком мал, активация дополнительного шанса удара! ");
            attackModifier = 1;
        }

        boolean isSuccess = isAttackSuccess(attackModifier);
        if (isSuccess) {
            int damage = minDamage + random.nextInt(maxDamage - minDamage + 1);
            creature.setDamage(damage);
        }
        return isSuccess;
    }

    public void setDamage(int damage) {
        this.health -= damage;
        if (health < 0) health = 0;
    }

    private boolean isAttackSuccess(int attackModifier) {
        while (attackModifier > 0) {
            int diceResult = random.nextInt(DICE_SIDES + 1);
            if (diceResult > 4)
                return true;
            attackModifier--;
        }

        return false;
    }

    private void checkParametersValid(int attack, int defense, float health, int minDamage, int maxDamage) {
        isAttackValid(attack);
        isDefenseValid(defense);
        isHealthValid(health);
        isDamageValid(minDamage, maxDamage);
    }

    private void isAttackValid(int attack) throws IllegalArgumentException {
        if (attack < MIN_ATTACK_DEFENSE || attack > MAX_ATTACK_DEFENSE)
            throw new IllegalArgumentException("Параметр атаки не соответствуют требованиям.");
    }

    private void isDefenseValid(int defense) throws IllegalArgumentException {
        if (defense < MIN_ATTACK_DEFENSE || defense > MAX_ATTACK_DEFENSE)
            throw new IllegalArgumentException("Параметр защиты не соответствуют требованиям.");
    }

    private void isHealthValid(float health) throws IllegalArgumentException {
        if (health < MIN_HEALTH)
            throw new IllegalArgumentException("Параметр здоровья не соответствуют требованиям.");
    }

    private void isDamageValid(int minDamage, int maxDamage) throws IllegalArgumentException {
        if (minDamage <= 0 || maxDamage <= 0 || minDamage > maxDamage)
            throw new IllegalArgumentException("Параметры урона не соответствуют требованиям.");
    }

    public boolean isAlive() {
        return health >= MIN_HEALTH;
    }

    public boolean isDead() {
        return !isAlive();
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
