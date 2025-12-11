# Projet Android Cloud 2025

Ce projet est une application Android native développée en **Kotlin** et **Jetpack Compose**. Elle met en pratique les concepts d'architecture moderne (**Clean Architecture**, **MVVM**, **Repository Pattern**) pour gérer la consommation d'API, la persistance locale et les services Cloud via Firebase.

## 🎯 Objectifs & Fonctionnalités

L'application répond aux 3 fonctionnalités demandées par le sujet :

### 1\. Homepage 🏠

* Écran d'accueil présentant les membres du groupe.
* Navigation fluide vers les autres fonctionnalités.

### 2\. Liste API & Persistance (Mode Hors-ligne) 📱

* **API :** Consommation d'une API publique (ex: *[Nom de ton API]*).
* **Cache Local (Room) :** Les données récupérées sont stockées en base de données avec un Timestamp.
* **Affichage Avancé :** Utilisation d'une `LazyColumn` avec **Headers** (groupés par date/heure) et Footer.
* **Logique :** Bouton "Ajouter" (Random API call) et "Tout supprimer".

### 3\. Intégration Firebase "Clean" 🔥

* **Remote Config :** Modification dynamique de l'UI sans mise à jour de l'app.
* **Cloud Messaging :** Réception et affichage de notifications push via un Service dédié.
* **Architecture :** Contrairement à une implémentation naïve, les appels Firebase sont isolés dans le **Data Layer** (via un Repository) et non dans l'UI.

-----

## 🏗 Architecture du Projet

Le projet respecte scrupuleusement la séparation des responsabilités (**Separation of Concerns**) via l'architecture **MVVM (Model-View-ViewModel)** couplée au **Repository Pattern**.

### Schéma de flux de données

`UI (Compose)` \<--\> `ViewModel` \<--\> `Repository` \<--\> `Data Sources (API / Room / Firebase)`

### Organisation des dossiers (Package Structure)

L'arborescence suit la logique **UI / Data / Architecture** pour maximiser la lisibilité et la maintenabilité (Principe KISS & DRY).

```text
com.example.androidcloud2025
├── architecture            # Configuration globale (Application, Services)
│   ├── CustomApplication.kt
│   ├── RetrofitBuilder.kt
│   └── MyFirebaseMessagingService.kt
├── data                    # COUCHE DE DONNÉES
│   ├── local               # Base de données Room (Dao, Database)
│   ├── remote              # Appels réseaux (API Endpoints, Firebase DataSource)
│   ├── model               # Modèles de données (Entity, DTO)
│   ├── mapper              # Extension functions (Dto -> Entity -> UI Model)
│   └── repository          # Logique métier et source de vérité unique
├── ui                      # COUCHE DE PRÉSENTATION
│   ├── model               # Modèles spécifiques à la vue (Sealed Interfaces)
│   ├── navigation          # Graphe de navigation Compose
│   ├── screen              # Ecrans (Composables)
│   ├── viewmodel           # Gestion des états (StateFlow)
│   └── theme               # Design System
└── MainActivity.kt
```

-----

## 🛠 Choix Techniques & Justifications

### 1\. Gestion "Clean" de Firebase (Feature 3)

Pour répondre à la contrainte du sujet (ne pas appeler Firebase dans l'UI) :

* Nous avons créé un `FirebaseDataSource` dans le package `data/remote`.
* Un `ConfigRepository` expose les valeurs de configuration via un `Flow`.
* Le `ViewModel` observe ce Flow et met à jour l'état de l'écran.
* **Résultat :** L'écran Compose est totalement agnostique de la technologie utilisée (Firebase).

### 2\. Gestion des Listes (Feature 2)

* Utilisation d'une **Sealed Interface** (`ListUiItem`) pour gérer proprement les différents types de vues dans la `LazyColumn` (Header, Content, Footer).
* Utilisation de `System.currentTimeMillis()` à l'insertion pour le tri et le groupement par date dans les Headers.

### 3\. Stack Technique

* **Langage :** Kotlin
* **UI :** Jetpack Compose (Material 3)
* **Async :** Coroutines & Flow
* **Injection de dépendances :** Manuelle (via `CustomApplication` singleton)
* **Réseau :** Retrofit 2 + OkHttp + Gson
* **Base de données :** Room (SQLite abstraction)
* **Images :** Coil
* **Cloud :** Firebase BoM (Messaging, Remote Config)

-----

## 🚀 Pré-requis et Installation

1.  Cloner le projet.
2.  Ouvrir dans **Android Studio** (Version Koala ou Ladybug recommandée).
3.  Synchroniser le projet avec Gradle.
4.  *(Optionnel si le google-services.json n'est pas inclus)* : Ajouter votre propre fichier `google-services.json` dans le dossier `app/`.
5.  Lancer sur un émulateur (API 35/36) ou un device physique.

-----

## 👥 Auteurs

**Groupe :** MARIN Matthieu & CAUWET Maxime


*Master Cloud Computing & Mobility - 2025*