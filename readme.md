# 🧾 🏨 PROJET : Système de gestion de réservations (hôtel / appart / coworking)

---

## 🎯 1. Contexte

Une plateforme permet de :

- réserver des hébergements (chambre, appartement, salle)
- ajouter des services
- gérer les paiements et factures
- appliquer des règles métier complexes (pricing, pénalités, garanties…)

---

## 🧩 2. Concepts métier (à modéliser)

Tu as :

- Client
- Réservation
- Hébergement (chambre / appartement / salle)
- Service (petit-déj, ménage…)
- Facture
- Paiement
- Promotion
- Pénalité

---

## 📏 3. Règles métier (le cœur de l’exercice)

---

## 🔵 A. Réservation

Une réservation doit avoir :

- une date d’arrivée < date de départ
- un hébergement OU une salle (pas les deux)

Une réservation peut être :

- PENDING
- CONFIRMED
- CANCELLED
- COMPLETED

Une réservation CONFIRMED :

- bloque l’hébergement
- ne peut pas être modifiée sur les dates

Une réservation CANCELLED :

- libère l’hébergement
- peut générer une pénalité

---

## 🟢 B. Hébergement

Un hébergement a :

- un prix par nuit
- un statut (DISPONIBLE / OCCUPE)

Il ne peut pas être réservé s’il est OCCUPE

Il devient OCCUPE dès confirmation

Il redevient DISPONIBLE :

- à la fin du séjour
- ou en cas d’annulation

---

## 🟡 C. Pricing (complexe)

Prix de base = prixParNuit × nombreDeNuits

Ajouter :

- services (prix fixes)
- frais supplémentaires

Promotions :

- si > 5 nuits → -10%
- si client fidèle → -5%

Majoration :

- weekend → +20%
- haute saison → +30%

👉 ⚠️ Ces règles peuvent changer souvent

---

## 🟣 D. Paiement

Une réservation doit avoir :

- au moins 30% payé pour être CONFIRMED

Paiement possible en plusieurs fois

Si paiement total atteint :

- statut = PAID

---

## 🔴 E. Facturation

Une facture est créée :

- à la confirmation
- ou au checkout

Une facture contient :

- montant total
- montant payé
- reste à payer

Une facture peut être :

- mise à jour après paiement

---

## ⚫ F. Annulation / pénalités

Si annulation :

- > 7 jours avant → 0 frais
- < 7 jours → 20%
- < 24h → 50%

---

## 🟤 G. Garantie

Certaines chambres demandent une garantie :

- % du prix total

La réservation ne peut pas être confirmée sans garantie