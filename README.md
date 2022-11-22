# Logique
1. appel Structure de donnee
2. Lire query.txt
3. 

## Structure de donnée
``` 
1. Créer WorldMap


Boucle(pour tous les fichiers){

3. Lire un fichier
   Boucle(pour tous les mots){
   
   
   
   if (mot w =  lettre majuscule)
      then minuscule
   if (mot w = ponctuation)
      then space
   if (mot w = plusierus spaces)
      then 1 space
   if (voit 1 space )
      then 
         if !(is key w dans WorldMap)
            then insert le mot w comme key de WorldMap
      
               if !(is key nomFichier dans FileMap)
                  insert nomFichier
                  ArrayList.add(i)
            
               else
                  ArrayList.add(i) 
      else
         créer fileMap
         addKey (nomFichier)
         addValeur = i
         
   incrémenterNbrMot(i=0,i++)
   }
   incrémenterNbrFichier(j=0,j++)
   nombreTotalmot += map (key:nomFichier, valeur:nbrTotalMot = i)
 
}
nombreTotalFichier = j
```
      1. 
WorldMap
- key: mot w
- valeur: FileMap

FileMap
- key: nom du fichier
- valeur: position du mot dans le fichier

{(stunning, {00026, 1}), () }

hashcode() -> trouver quel fileMap pour le mot w

### Taille de map
``` java
tailleWordMap = 1000

if (i/1000 == 0.75){
   tailleWordMap(tailleWordMap * 2) + 1
   copier contenu de ancien WordMap à nouveau WordMap
}


```

## TFIDF (fréquence de terme - fréquence de document inverse)
1. appel WordMao 
``` 
Boucle {
   1. TF (w) = count (w) /totalW
      1. nbr total du mot w / le nombre de mot total dans le document
      
      Count(w)
            findKey (w) 
            if (exist)
               finValeurDeKey(w) -> FileMap
               findValeurDuFileMap -> arrayList
               count = arrayList.lengh 
      totalW
            nombreTotalMot.findKey(fichier)
            nombreTotalMot.findValeur() -> nombre total mot pour le fichier

   2. IDF (w) = ln(totalID/count (d,w))
      1. ln(le nombre total de doc. / le nbr de document qui contient le mot w)
            
       totalID
               nombreTotalFichier
      count(d,w)
               dans WorldMap, findKey(w)
               avec FileMap associer au key, trouver nbr de Key dans fileMap
   
   3. TFIDF (w) = TF(w) * IDF(w)
```
      
2. Comparer le TFIDF: chercher max
3. On out le nom de fichier qui la valeur TFIDF le plus élevé

## Bi-grammes
1. voir Structure de donnee


def TFIDF(x):
    f(w)

w m
really like
really hate

[[phot, phil], [phil, bor]]
