# Document de Travail – Développement d’un Yams Duo

Thomas NOEL, Rayan ESSAIDI

---

## Tâche a. – Analyse du Code Produit pour l’Objectif 1

### 1. Démarche suivie  

Nous avons relancé le jeu et nous avons revu comment la relance des dés et l'attribution du score était gérés.

---

### 2. Liste des éléments réutilisables

- Classe `Dice`
- Classe `Board`
- Classe `ScoreSheet`
- Interface `Combinaison`
- Les Records concernant les combinaisons

---

### 3. Manques par rapport à un Yams duo

Gestion de deux joueurs notamment en ce qui concerne le déroulement des tours et la gestion des scores pour les deux joueurs pour la fin de partie.

---

## Tâche b. – Proposition de Solution

### 1. Cahier des charges simplifié

- Permettre à deux joueurs de jouer à tour de rôle
- Permettre aux joueurs de s'identifier entre eux (numéros et noms)
- Enregistrer et comparer le score des deux joueurs à l'issue de la partie
- Créer un robot contre lequel s'exercer.

---

### 2. Choix techniques

- Nouvelle classe pour la gestion du robot
- Modifications dans la classe Yams, notamment aux niveau de certaines variables et méthodes pour implémenter un deuxième joueur.

---

### 3. Schéma simple de l’organisation du programme

Yams (main)
 ├── Board
 │    └── Dice x5
 ├── ScoreSheet
 ├── YamsBot
 └── Combination (interface)
       ├── FullHouse, ThreeOfAKind, DoublePair, LargeStraight, Pair, SmallStraight
       ├── YamsCombinaison
       └── Chance

---

## Tâche c. – Programmation

- Ajouts : Classe YamsBot, méthode playTurn dans la classe Yams  
- Modifications : Dans la classe Yams, méthodes init, askReroll, et main modifiés pour pourvoir lire et afficher les informations pour un deuxième joueur  
- Tests réalisés : Jeu lancé et observation de la gestion de l'issue de la partie, tests de parties contre le robot

---

## Tâche d. – Livraison

- Partie jouable en ligne de commande à deux joueurs
- Robot fonctionnel
- Ce document rempli

---

## Commentaire personnel 

Implémenter un deuxième joueur était finalement plutôt simple. Cependant, la création du robot s'est révélé compliqué, particulièrement dans la prise de décision en fonction de la situation ; il est difficile de faire un programme parfaitement résponsif.
