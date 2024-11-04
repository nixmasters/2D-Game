# 2D JavaFX Game - Developed in IntelliJ IDEA

This game is built using JavaFX and consists of two separate windows: a selection screen for game settings and a dynamic gameplay window.

---

## **Game Overview**

### **Window 1: Game Settings (GUI)**
The first window provides a simple graphical user interface (GUI) for configuring the game. Players can select:
- **Difficulty Level**
- **Cannon Size**
- **Field Shape**

### **Window 2: Gameplay**

- **Player Character:**  
  - Represented by a hexagon that spawns in the center of the field.
  - The player's gun direction follows the mouse cursor, allowing precise aiming.

- **Enemies:**  
  - Spawned around the outer perimeter of the field in a circular pattern.
  - Enemies randomly shoot at the player, reducing player health points if they hit.

---

## **Player Abilities**

- **Shooting:**
  - **Normal Bullets:** Fired by pressing the left mouse button, each shot reduces an enemy’s HP by 1.
  - **Strong Bullets:** Fired by pressing the right mouse button, each shot reduces an enemy’s HP by 2.  
  - Strong bullets are limited to a maximum of 4. When depleted, the player cannot fire strong bullets until more ammo is collected.

- **Collectibles:**
  - **Shield (Blue Hexagon):** Grants a protective barrier that blocks enemy bullets for 5 seconds.
  - **Health (Heart):** Restores 1 health point.
  - **Strong Bullet Ammo (Coin):** Restores 1 strong bullet.

---

## **Enemy Mechanics**

- **Attacks:**  
  - Enemies periodically shoot at the player. Each successful hit reduces the player's health by 1.
  - If the player's health drops to 0, the game ends.

- **Defensive Mechanism:**  
  - Occasionally, a temporary wall appears in front of each enemy, blocking player bullets for a few random seconds.

---

## **Game Objectives**

- **Win Condition:** Eliminate all enemies to complete the game.
- **Lose Condition:** The game ends if the player's health reaches 0.

--- 
