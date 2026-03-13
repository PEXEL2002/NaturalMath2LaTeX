# NaturalMath2LaTeX

## Opis projektu
**NaturalMath2LaTeX** to inteligentny edytor i transpilator, który ułatwia pisanie skomplikowanych równań matematycznych w systemie LaTeX (np. dla środowiska Overleaf). Narzędzie pozwala na używanie intuicyjnych skrótów oraz fraz języka naturalnego, które są automatycznie konwertowane na profesjonalną składnię matematyczną.

## Kluczowe funkcjonalności
* **Intuicyjne ułamki:** Zamiana zapisu `a/b` lub `(x+1)/(y-1)` na strukturę `\frac{}{}`.
* **Interpreter analizy matematycznej:** Obsługa fraz typu `calka x^2`, `granica n do nieskonczonosci` czy `suma i=0 do 10`.
* **Automatyczne symbole:** Konwersja nazw greckich liter (np. `delta`, `pi`) na ich odpowiedniki LaTeXowe.
* **Obsługa zagnieżdżeń:** Poprawne przetwarzanie złożonych struktur, np. pierwiastków wewnątrz ułamków.

## Technologia
* **Parser Generator:** ANTLR v4 (do analizy struktury wyrażeń matematycznych).
* **Język implementacji:** Java.
* **Format wyjściowy:** Tekst zgodny ze standardem LaTeX (AmsMath).

## Przykład działania
| Wejście (Skrót) | Wyjście (LaTeX) | Render |
| :--- | :--- | :--- |
| `a/b` | `\frac{a}{b}` | $$\frac{a}{b}$$ |
| `calka sin(x)` | `\int \sin(x) \,dx` | $$\int \sin(x) \,dx$$ |
| `suma n=1 do 10 n^2` | `\sum_{n=1}^{10} n^2` | $$\sum_{n=1}^{10} n^2$$ |