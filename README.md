# yams
Yams en Java
Ce projet est une implémentation en ligne de commande du jeu de Yams (aussi connu sous le nom de Yahtzee) en Java. Il se décline en deux versions : une version solo et une version duo avec adversaire contrôlé par une IA.

Objectifs :
Partie 1 : Yams Solo
Implémenter un jeu de Yams jouable par un seul joueur, avec les règles classiques (lancer de dés, relances, sélection de combinaison, score).
Partie 2 : Yams Duo avec IA
Étendre le jeu pour permettre un affrontement contre un adversaire contrôlé par l’ordinateur, avec différents niveaux d’intelligence.

Fonctionnalités prévues :
- Lancer et relancer les dés jusqu’à 3 fois par tour
- Choix des dés à conserver entre les lancers
- Sélection d’une combinaison à valider dans le tableau de score
- Calcul des points selon les règles du Yams
- Système de tour de jeu et de fin de partie
- IA jouant automatiquement ses tours en duo

Organisation :
Le projet est structuré autour de classes représentant :
- les dés et leur comportement,
- les joueurs (humain ou IA),
- la feuille de score,
- la logique de partie (lancers, tours, fin de jeu).
