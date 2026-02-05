# ⚔️ GameOfKnight

`GameOfKnight` is a 2D action-platformer developed in **Java** using the **Processing** library. It features a knight protagonist with fluid animations, gravity-based physics, and a chasing AI enemy.



## 🎮 Game Overview
In this game, you play as a knight who must navigate a platform to reach a hidden treasure while being pursued by a relentless ghost. The game demonstrates core programming concepts such as **state management**, **collision detection**, and **sprite sheet manipulation**.

### 🎯 Objective
* **Win:** Reach the treasure chest (**Cofre**).
* **Lose:** Get touched by the ghost (**Fantasma**) without attacking.

### 🕹️ Controls
| Action | Key |
| :--- | :--- |
| **Move Left** | `A` or `Left Arrow` |
| **Move Right** | `D` or `Right Arrow` |
| **Jump** | `W`, `Up Arrow` (Only when on ground) |
| **Attack** | `Spacebar` |
| **Restart** | `R` (After Game Over or Victory) |

---

## 🚀 Key Technical Features

### 1. Sprite Sheet Animation
The game utilizes custom sprite sheets (`knight.png` and `knight3.png`). The code programmatically slices these sheets to create distinct animations for:
* **Idle:** Static poses based on the last direction faced.
* **Running:** A 4-frame loop that triggers when moving.
* **Attacking:** A high-speed 4-frame combat animation.

### 2. Physics & Mechanics
* **Gravity Engine:** Implements a `gravedad` variable and `velocidadY` to simulate realistic falling and jumping.
* **Collision Detection:** Uses distance-based logic (`dist()`) to determine interactions between the player, the enemy, and the treasure.

### 3. Enemy AI
* **Targeting:** The enemy uses a basic chase algorithm, constantly adjusting its coordinates to follow the player's position.

---

## 🛠️ Technologies Used
* **Java**: Core logic and object-oriented structure.
* **Processing (PApplet)**: Rendering engine and input handling.
* **NetBeans/IntelliJ**: Recommended IDEs for execution.

---

## ▶️ Setup and Execution
1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/BMarina4/GameOfKnight.git](https://github.com/BMarina4/GameOfKnight.git)
    ```
2.  **Project Structure:**
    Ensure your directory looks like this so the code can find the images:
    ```text
    ├── src/
    │   └── juego/GameOfKnight.java
    └── imagenes/
        ├── knight.png
        ├── knight3.png
        ├── fondo3.jpg
        ├── fantasma.png
        └── cofre.png
    ```
3.  **Run:** Open the project in your Java IDE and run the `main` method in `GameOfKnight.java`.

---

## 📬 Contact
**Marina Benach Vilasís** 
* **Role:** Student of Web Application Development (DAW) at Monlau.
* **Email:** [marinabenvil@monlau.com](mailto:marinabenvil@monlau.com).
* **GitHub:** [BMarina4](https://github.com/BMarina4).
