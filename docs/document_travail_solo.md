# Document de Travail – Développement d’un Yams Solo

Thomas NOEL, Rayan ESSAIDI

---

## Tâche a. – Analyse du Code Existant

### 1. Démarche suivie  

Nous avons commencé par lire la classe `Yams`, et comment celle-ci interagit avec les objets alentours. Ensuite, nous avons testé le jeu pour observer son comportement.

---

### 2. Liste des fonctionnalités déjà implémentées

- Affichage des dés et du score
- Relance d’un dé

---

### 3. Manques

Tout le système de combinaison est manquant.

---

## Tâche b. – Proposition de Solution

### 1. Cahier des charges simplifié
 
- Permettre de relancer plusieurs dés à la fois  
- Calcul du score en conséquence de la combinaison des dés lancés simultanément

---

### 2. Choix techniques

Nous allons créer plusieurs Records afin de gérer chaque combinaison possible, toutes implémentant l'interface Combinaison.

---

### 3. Schéma simple de l’organisation du programme

Yams (main)
 ├── Board
 │    └── Dice x5
 ├── ScoreSheet
 └── Combination (interface)
       ├── FullHouse, ThreeOfAKind, DoublePair, LargeStraight, Pair, SmallStraight
       ├── YamsCombinaison
       └── Chance

---

## Tâche c. – Programmation

- Création : Chance, YamsCombinaison, Pair, DoublePair, SmallStraight, LargeStraight (Records)
- Modification : Ajout de la méthode GetFiveDice dans la classe Board
- Tests réalisés : Jeu lancé en manipulant des variables afin d'obtenir certaines combinaisons et ainsi toutes les tester. 

---

## Tâche d. – Livraison

- Code fonctionnel  
- Partie ligne de commande jouable sur 13 tours  
- Combinaisons jouables au choix
- Affichage du score total  
- Ce document rempli  
---

## Commentaires personnels 

Cet exercice fût intéressant notamment en ce qui concerne la création de Records implémentant une interface, cela nous a servi de rappel pour cette manière de structurer un projet.
