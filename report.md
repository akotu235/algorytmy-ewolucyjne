### Sztuczna inteligencja i systemy ekspertowe
# Algorytmy Ewolucyjne
## *Zasada działania, obszary zastosowań, biblioteki programistyczne, zastosowanie do wybranego problemu optymalizacyjnego oraz eksperymenty*
### **Andrzej Kotulski**
**WSZiB**, 2024

---


## 1. Zasada działania i zastosowania
**Algorytm ewolucyjny** (ang. evolutionary algorithm) to metoda optymalizacji inspirowana mechanizmami ewolucji biologicznej, takimi jak dziedziczenie, mutacja, krzyżowanie oraz selekcja naturalna. Algorytm działa na populacji potencjalnych rozwiązań problemu, które są kodowane w formie genotypów (zbiorów cech). Każde rozwiązanie jest oceniane przy użyciu funkcji celu (fitness), która mierzy jego "przystosowanie" w kontekście optymalizacji. Proces ewolucji składa się z kilku kluczowych etapów:

1.	**Inicjalizacja**: Generowanie początkowej populacji rozwiązań.

2. **Ocena**: Każdy osobnik w populacji jest oceniany przy użyciu funkcji fitness, co pozwala ocenić jakość każdego rozwiązania.

3. **Selekcja**: Wybór najlepiej przystosowanych osobników do reprodukcji na podstawie wyników fitness.

4. **Krzyżowanie i mutacja**: Procesy inspirowane biologiczną ewolucją, które tworzą nowe rozwiązania (potomków) poprzez kombinację cech rodziców i losowe zmiany (mutacje).

5. **Zastępowanie**: Nowe rozwiązania zastępują część populacji, a proces jest powtarzany iteracyjnie aż do osiągnięcia zadanych kryteriów zakończenia, np. liczby iteracji lub ustabilizowania się wartości fitness.

Algorytmy ewolucyjne znajdują zastosowanie w wielu dziedzinach. Jednym z ich głównych zastosowań jest sztuczna inteligencja, gdzie algorytmy ewolucyjne są wykorzystywane do trenowania modeli neuronowych (algorytmy neuroewolucji). Dzięki adaptacyjnemu charakterowi mogą one automatycznie optymalizować architekturę sieci neuronowych oraz ich parametry [1].

Kolejnym obszarem jest optymalizacja wielokryterialna, gdzie rozwiązania muszą spełniać wiele, często sprzecznych wymagań. Przykładami takich problemów są optymalizacja zasobów w systemach inżynieryjnych oraz projektowanie efektywnych sieci telekomunikacyjnych [2].

Zastosowanie algorytmów ewolucyjnych w logistyce obejmuje m.in. problemy trasowania pojazdów, optymalizację transportu oraz zarządzanie magazynem. Złożoność tych problemów sprawia, że tradycyjne metody optymalizacyjne są często niewystarczające, a algorytmy inspirowane ewolucją oferują efektywne podejście do ich rozwiązywania [2, 3].


## 2. Biblioteki programistyczne

### 2.1. DEAP (Distributed Evolutionary Algorithms in Python)

**Instalacja**:

`DEAP` jest dostępna poprzez menedżer pakietów Pythona, co umożliwia jej łatwą instalację i integrację z istniejącymi projektami.

**Możliwości**:

`DEAP` oferuje elastyczną platformę do tworzenia algorytmów ewolucyjnych, takich jak:

* Algorytmy genetyczne (`GA`)
* Programowanie genetyczne (`GP`)
* Strategie ewolucyjne (`ES`)
* Algorytmy ewolucji różnicowej (`DE`)

Obsługuje niestandardowe genotypy, funkcje fitness oraz operatory, a także umożliwia wizualizację wyników.

**Zalety**:

* Duża elastyczność w tworzeniu algorytmów.
* Wsparcie dla wielu typów algorytmów.
* Możliwości optymalizacji wielokryterialnej.

**Wady**:

* Krzywa uczenia się dla początkujących.
* Potencjalnie wolniejsza wydajność przy dużych populacjach.

## 2.2. Jenetics (Java)

**Instalacja**:

`Jenetics` można zainstalować, dodając odpowiednią zależność do pliku konfiguracyjnego projektu opartego na Mavenie lub Gradle, co ułatwia zarządzanie biblioteką i jej wersjami w środowisku Java.

**Możliwości**:

`Jenetics` umożliwia implementację algorytmów genetycznych z prostym API, oferując wsparcie dla:

* Algorytmów genetycznych (`GA`)
* Algorytmów ewolucji różnicowej (`DE`)
* Algorytmów wielokryterialnych (`NSGA-II`, `SPEA2`)

Obsługuje paralelizację, co zwiększa wydajność.

**Zalety**:

* Wydajność i skalowalność.
* Proste i intuicyjne API.
* Wsparcie dla równoległego przetwarzania.

**Wady**:

* Skupiona głównie na algorytmach genetycznych.


## 3. Zaimplementowany algorytm rozwiązujący wybrany problem optymalizacyjny

*[opis wybranego problemu optymalizacyjnego (np. optymalizacja funkcji, komiwojażer, generowanie optymalnych konstrukcji (np. <http://www.boxcar2d.com/>), optymalizacja wielokryterialna, wielokryterialna optymalizacja trasy drona/statku z uwzględnieniem przeszkód/pogody) oraz szczegółowy opis algorytmu ewolucyjnego użytego do jego rozwiązania: pseudokod wraz z opisem słownym, sposób reprezentacji rozwiązania w genotypie osobnika, wartości parametrów (liczebność populacji, prawdopodobieństwa mutacji i rekombinacji oraz inne w zależności od użytego algorytmu) wraz z komentarzami]*


## 4. Wyniki eksperymentów

*[wartości parametrów algorytmu ustawiane dla poszczególnych eksperymentów, jeżeli różnią się od tych podanych w sekcji 3, skomentowane wykresy pokazujące średnie wyniki z odchyleniami standardowymi z co najmniej 10 uruchomień algorytmu (wykresy powinny pokazywać: najlepszy, najgorszy i średni fitness w populacji w trakcie trwania eksperymentu), omówienie najlepszego znalezionego rozwiązania.]*


## 5. Podsumowanie

*[podsumowanie rezultatów projektu oraz uzyskanych wyników eksperymentów]*

## Bibliografia

1. **K. O. Stanley i R. Miikkulainen, Evolving Neural Networks through Augmenting Topologies**, MIT Press, 2002. 

2. **J. H. Holland, Adaptation in Natural and Artificial Systems: An Introductory Analysis with Applications to Biology, Control, and Artificial Intelligence**, MIT Press, 1992. 

3. **D. E. Goldberg, Genetic Algorithms in Search, Optimization, and Machine Learning**, Addison-Wesley, 1989. 

## Załączniki

1. **Repozytorium kodu źródłowego** – Pełny kod projektu. Dostępne online: <https://github.com/akotu235/algorytmy-ewolucyjne>

2. **Wersja online sprawozdania** – Bieżąca wersja dokumentu. Dostępne online: <https://github.com/akotu235/algorytmy-ewolucyjne/blob/master/report.md>