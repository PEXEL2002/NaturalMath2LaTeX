# 📘 Instrukcja Rozbudowy Projektu: NaturalMath2LaTeX

Ten dokument opisuje proces dodawania nowych funkcjonalności do translatora. Architektura systemu została zaprojektowana tak, aby wspierać naturalny zapis matematyczny i być **pobłażliwą** dla błędów lub skrótów myślowych użytkownika.

---

## 🏗 Architektura Rozwiązania

Projekt opiera się na **wzorcu delegacji** oraz **rekurencji wzajemnej**. Przetwarzanie tekstu na kod LaTeX odbywa się w trzech głównych warstwach:

1.  **Warstwa Tokenizacji (Lexer):** Rozpoznaje surowy tekst i zamienia go na logiczne jednostki (tokeny).
2.  **Warstwa Strukturalna (Parser):** Buduje drzewo składniowe (Parse Tree) i ustala hierarchię działań.
3.  **Warstwa Przetwarzania (Visitor):** Przechodzi po drzewie i zamienia jego węzły na finalne komendy LaTeX zgodne ze standardem Overleaf.

---

## 🚀 Proces Dodawania Nowej Funkcji

### 1. Warstwa Lexera: Naucz system nowych słów
Pierwszym krokiem jest definicja nowych tokenów w pliku gramatyki Lexera.
* **Aliasy i Synonimy:** Aby system był pobłażliwy, definiuj grupy słów przypisane do jednej nazwy tokena (np. dla całek: `calka`, `int`, `integral`).
* **Słowa Pomocnicze:** Dodaj łączniki ułatwiające czytanie, takie jak `od`, `do`, `po`, czy `d`.
* **Separatory:** Zdefiniuj znaki, które mogą oddzielać elementy w strukturach złożonych (np. przecinki, średniki lub pionowe kreski dla wierszy macierzy).

### 2. Warstwa Parsera: Zdefiniuj strukturę i priorytety
W pliku parsera określ, jak tokeny łączą się w wyrażenia (`expression`).
* **Opcjonalność:** Wykorzystuj grupy opcjonalne `(...)?`, aby użytkownik nie musiał podawać wszystkich danych (np. domyślne granice całki lub brak słowa kluczowego przed nawiasem macierzy).
* **Etykiety (Labels):** Każdą regułę zakończ unikalną etykietą po znaku `#`. Dzięki temu ANTLR wygeneruje dedykowane metody w kodzie Java.
* **Hierarchia:** Nowe operacje (całki, macierze, funkcje) umieszczaj wysoko w regule `expression`, aby miały odpowiedni priorytet względem dodawania i mnożenia.

### 3. Kompilacja Projektu
Po każdej edycji plików `.g4` należy przebudować projekt (np. `mvn clean compile`). To kluczowy krok, który generuje "rusztowanie" Javy (klasy Context i BaseVisitor) na podstawie Twojej nowej gramatyki.

### 4. Warstwa Visitora: Implementacja logiki u Specjalisty
Zaimplementuj faktyczne przetwarzanie w odpowiedniej klasie Javy.
* **Podział na klasy:** Rozdzielaj logikę na mniejsze klasy (np. `CalculusVisitor`, `MatrixVisitor`), aby uniknąć gigantycznych plików.
* **Współpraca z Dyrygentem:** Każdy specjalista musi otrzymywać w konstruktorze referencję do głównego `MainVisitor`.
* **Obsługa wartości NULL:** Ponieważ w kroku 2 dopuściliśmy opcjonalność, w Javie zawsze sprawdzaj, czy dany element drzewa istnieje. Jeśli nie – zastosuj wartość domyślną (np. całkuj domyślnie po zmiennej `x`).
* **Formatowanie LaTeX:** Zwracaj Stringi zawierające komendy LaTeX. Pamiętaj, aby parametry (takie jak potęgi czy indeksy dolne) zawsze zamykać w klamrach `{}`.

---

## 💡 Złote Zasady "Pobłażliwego" Projektowania

* **Wielowariantowość:** Dobra reguła parsera powinna obsłużyć zarówno `macierz [1,2]`.
* **Rekurencja (Incepcja):** Dzięki przesyłaniu fragmentów drzewa z powrotem do metody `visit()` głównego Visitora, Twoje funkcje będą mogły zawierać się w sobie nawzajem (np. macierz całek).
* **Ciche Separatory:** Traktuj klamry `{}` jako narzędzie pomocnicze dla użytkownika do grupowania złożonych elementów, które "znikają" po przetworzeniu na LaTeX, nie psując wizualnie wzoru.
* **Testowanie:** Każda nowa funkcja powinna być sprawdzona pod kątem renderowania w Overleafie – upewnij się, że nie brakuje backslashy `\` przed komendami.

---