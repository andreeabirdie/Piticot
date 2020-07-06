# Piticot
Proiectați și implementați o aplicație client-server pentru următoarea problemă.
Un joc cu 3 jucători numit Piticot. Trei utilizatori autentificați pot juca acest joc. Serverul va trimite fiecărui jucător
12 poziții reprezentând traseul care trebuie parcurs de jucători, la început toți jucătorii fiind poziționați înaintea
primei poziții. Jucătorul care ajunge cel mai aproape de ultima poziție (conform cerinței 2) după 3 runde, câștigă
jocul. Fiecare jucător poate să facă următoarele:
1. Login. După autentificarea cu succes se deschide o nouă fereastră în care este afișat un buton “Start joc”.
Doar după ce trei jucători se autentifică în aplicație, butonul respectiv poate fi apăsat de ultimul jucător
autentificat. Serverul va trimite tuturor jucătorilor numele participanților la joc (username) și o configurație
de start cu 12 poziții selectată aleator din baza de date (ex. configurație start _ _ _ W _ _ _ W _ _ _ _ ).
2. Schimbă poziția. Fiecare jucător, cand îi vine rândul, generează local și aleator un număr n între 1 și 4 (1, 2,
3 sau 4), prin apăsarea unui buton. Dupa ce numărul este generat, acesta este trimis la server și jucătorul va
înainte cu n poziții în configurație, astfel:
• Dacă noua poziție nu este ocupată, jucătorul rămâne pe poziția respectivă.
• Dacă noua poziție este ocupată de alt jucător, acel jucător se întoarce pe prima poziție liberă dintre
pozițiile anterioare sau 0 (dacă era pe poziția 1).
• Daca noua poziție este W, jucătorul se întoarce pe prima poziție liberă dintre pozițiile anterioare sau 0.
Toți jucătorii văd numărul n generat, jucătorul care l-a generat și pozițiile ocupate de ei în urma acestei mutări.
Dupa ce un jucător a generat un număr, el nu mai poate genera alt număr până când nu generează și ceilalalți
jucători câte un număr. Cerința 2 se repetă de încă 2 ori.
La finalul celor 3 runde, serverul va trimite tuturor numele câștigătorului și toți jucătorii îl vor vedea pe
interfața grafică.
3. Un serviciu REST care permite vizualizarea configurației de start (cele 12 poziții) și numele jucătorilor
participanți la un joc.
4. Un serviciu REST care pentru un anumit joc si un anumit jucător permite vizualizarea numerelor generate la
fiecare rundă și configurația jocului dupa mutarea respectivă.
Exemplu:
Având jucătorii A, B și C și configurația _ C _ W B _A_ _ _ _ _ , dacă jucătorul C generează numărul 2 atunci
configurația după mutare va fi _ _ C W B _A_ _ _ _ _
Observație:
Configurațiile de start, numerele generate la fiecare rundă și configurația după fiecare mutare se păstrează în
baza de date.
Cerințe:
• Aplicația poate fi dezvoltată în orice limbaj de programare (Java, C#, etc).
• Datele vor fi preluate/salvate dintr-o bază de date relațională.
• Pentru o entitate (exceptând jucator) se va folosi un instrument ORM pentru stocarea datelor.
