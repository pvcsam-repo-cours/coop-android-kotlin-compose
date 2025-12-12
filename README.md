# Projet Android Cloud 2025

Ce projet est une application Android native développée en **Kotlin** et **Jetpack Compose**. Elle met en pratique les concepts d'architecture moderne (**Clean Architecture**, **MVVM**, **Repository Pattern**) pour gérer la consommation d'API, la persistance locale et les services Cloud via Firebase.

## 🎯 Objectifs & Fonctionnalités

L'application répond aux 3 fonctionnalités demandées par le sujet :

### 1\. Homepage 🏠

* Écran d'accueil présentant les membres du groupe.
* Navigation fluide vers les autres fonctionnalités.

### 2\. Liste API & Persistance (Mode Hors-ligne) 📱

* **API :** Consommation de l'API publique [TheMealDB](https://www.themealdb.com/) pour récupérer des recettes de repas aléatoires.
* **Cache Local (Room) :** Les données récupérées sont stockées en base de données avec un Timestamp.
* **Affichage Avancé :** Utilisation d'une `LazyColumn` avec **Headers** (groupés par date/pays/catégorie) et Footer.
* **Tri dynamique :** Possibilité de trier les repas par date d'ajout, pays ou catégorie.
* **Logique :** Bouton "Ajouter" (appel API random) et "Tout supprimer".
* **Navigation :** Clic sur un repas → écran de détail avec toutes les informations.

### 3\. Intégration Firebase "Clean" 🔥

* **Remote Config :** Modification dynamique de l'UI sans mise à jour de l'app.
* **Cloud Messaging :** Réception et affichage de notifications push via un Service dédié.
* **Architecture :** Contrairement à une implémentation naïve, les appels Firebase sont isolés dans le **Data Layer** (via un Repository) et non dans l'UI.

-----

## 🏗 Architecture du Projet

Le projet respecte scrupuleusement la séparation des responsabilités (**Separation of Concerns**) via l'architecture **Clean Architecture** avec **MVVM (Model-View-ViewModel)** couplée au **Repository Pattern**.

L'architecture est organisée en 3 couches principales :
- **UI Layer** : Présentation (Compose, ViewModels, UI Models)
- **Domain Layer** : Logique métier (Interfaces Repository, Modèles de domaine)
- **Data Layer** : Sources de données (API, Room, Firebase)

### Schéma de flux de données

```
UI (Compose) ←→ ViewModel ←→ Repository (Interface) ←→ RepositoryImpl ←→ Data Sources
                                                           ↓
                                              ┌───────────┼───────────┐
                                              ↓           ↓           ↓
                                          API (Retrofit) Room    Firebase
```

### Organisation des dossiers (Package Structure)

L'arborescence suit la logique **UI / Data / Architecture** pour maximiser la lisibilité et la maintenabilité (Principe KISS & DRY).

```text
fr.upjv.projet_coop
├── architecture            # Configuration globale (Application, Services)
│   ├── CustomApplication.kt    # Singleton pour injection de dépendances
│   ├── RetrofitBuilder.kt      # Configuration Retrofit pour API TheMealDB
│   ├── AppDatabase.kt          # Base de données Room
│   ├── Extensions.kt           # Fonctions d'extension (getCustomApplication)
│   └── MyFirebaseMessagingService.kt  # Service Firebase Cloud Messaging
├── domain                    # COUCHE DOMAINE (Interfaces & Modèles)
│   ├── model                  # Modèles de domaine (AppConfig)
│   └── repository             # Interfaces Repository (ConfigRepository)
├── data                      # COUCHE DE DONNÉES
│   ├── dao                    # Data Access Objects (MealDao)
│   ├── mapper                 # Extension functions (Dto -> Entity -> Data)
│   ├── model                  # Modèles de données (Entity, DTO, Data)
│   ├── remote                 # Appels réseaux
│   │   ├── MealEndpoint.kt    # Interface Retrofit pour TheMealDB
│   │   └── FirebaseDataSource.kt  # Source de données Firebase
│   └── repository             # Implémentations Repository
│       ├── MealRepository.kt  # Gestion des repas (API + Room)
│       └── ConfigRepositoryImpl.kt  # Gestion config Firebase
├── ui                        # COUCHE DE PRÉSENTATION
│   ├── model                  # Modèles UI (MealUiState, SortOption)
│   ├── navigation             # Navigation Compose
│   │   ├── AppDestinations.kt # Définitions des routes (sealed interface)
│   │   └── AppNavigation.kt   # Graphe de navigation
│   ├── screen                 # Ecrans (Composables)
│   │   ├── HomeScreen.kt      # Écran d'accueil
│   │   ├── MealListScreen.kt  # Liste des repas (Feature 2)
│   │   ├── MealDetailScreen.kt # Détail d'un repas
│   │   ├── Feature3Screen.kt  # Écran Firebase (Feature 3)
│   │   └── FirebaseConfigScreen.kt
│   ├── viewmodel              # ViewModels (MealViewModel, Feature3ViewModel)
│   └── theme                  # Design System (Material 3)
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

* **API TheMealDB :** Utilisation de l'endpoint `/random.php` pour récupérer des repas aléatoires.
* **Sealed Interface :** Utilisation de `MealUiItem` (sealed interface) pour gérer proprement les différents types de vues dans la `LazyColumn` (Header, MealItem, Footer).
* **Tri dynamique :** Trois options de tri disponibles via `SortOption` :
  - Par date d'ajout (plus récentes en premier)
  - Par pays (ordre alphabétique)
  - Par catégorie (ordre alphabétique)
* **Groupement :** Les repas sont groupés selon l'option de tri sélectionnée avec des Headers et Footers pour chaque groupe.
* **Timestamp :** Utilisation de `System.currentTimeMillis()` à l'insertion pour le tri et le groupement par date.
* **Architecture Clean :** Respect de la séparation des couches (UI, Domain, Data) avec mapping des objets via des fonctions d'extension.
* **Navigation type-safe :** Navigation Compose avec destinations sérialisables (`AppDestinations.MealDetail(mealId)`).

### 3\. Stack Technique

* **Langage :** Kotlin
* **UI :** Jetpack Compose (Material 3)
* **Async :** Coroutines & Flow
* **Injection de dépendances :** Manuelle (via `CustomApplication` singleton + `Extensions.kt`)
* **Réseau :** Retrofit 2 + Gson Converter
* **API :** TheMealDB (https://www.themealdb.com/api/json/v1/1/)
* **Base de données :** Room (SQLite abstraction)
* **Images :** Coil (pour le chargement d'images)
* **Cloud :** Firebase BoM (Messaging, Remote Config)
* **Navigation :** Navigation Compose avec type-safe routing (kotlinx.serialization)

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