# NaturalMath2LaTeX

## Opis projektu
**NaturalMath2LaTeX** to inteligentny transpilator zamieniający język naturalny oraz intuicyjne skróty matematyczne na profesjonalny kod **LaTeX**. Narzędzie zostało zaprojektowane, aby uprościć proces pisania skomplikowanych równań w edytorach takich jak Overleaf, eliminując konieczność ręcznego wpisywania żmudnych komend systemowych.

## Kluczowe funkcjonalności
* **Analiza Matematyczna:** Zaawansowana obsługa całek, granic, sum oraz iloczynów wielokrotnych.
* **Logika i Zbiory:** Przetwarzanie naturalnych spójników (i, lub, nie) na symbole logiczne oraz obsługę teorii zbiorów.
<!--
* **Obsługa zagnieżdżeń:** Dzięki wykorzystaniu drzewa składniowego (AST), narzędzie pozwala na dowolne zagnieżdżanie struktur (np. całka w liczniku ułamka).
* **Operator `//` (Smart Fraction):** Intuicyjne tworzenie ułamków pionowych przy zachowaniu standardowego dzielenia liniowego `/`.
-->
## Przykłady użycia

### 1. Analiza Matematyczna i Iteratory
| Wejście skrótowe | Wyjście LaTeX | Przykładowy render |
| :--- | :--- | :--- |
| `calka sin(x)` | `\int \sin(x) dx` | $$\int \sin(x) dx$$ |
| `calka od 0 do pi sin(x) po x` | `\int_{0}^{\pi} \sin(x) dx` | $$\int_{0}^{\pi} \sin(x) dx$$ |
| `granica n -> inf (1/n)` | `\lim_{n \to \infty} (\frac{1}{n})` | $$\lim_{n \to \infty} (\frac{1}{n})$$ |
| `suma n=1 do inf (1//n^2)` | `\sum_{n=1}^{\infty} \frac{1}{n^2}` | $$\sum_{n=1}^{\infty} \frac{1}{n^2}$$ |
| `iloczyn k=1 do n (x+k)` | `\prod_{k=1}^{n} (x+k)` | $$\prod_{k=1}^{n} (x+k)$$ |

### 2. Arytmetyka, Logika i Zbiory
| Wejście skrótowe | Wyjście LaTeX | Przykładowy render |
| :--- | :--- | :--- |
| `p i (q lub nie r)` | `p \land (q \lor \neg r)` | $$p \land (q \lor \neg r)$$ |
| `A sumaZ B nalezy do C` | `A \cup B \in C` | $$A \cup B \in C$$ |
| `x nieNalezy do (A suma_zb B)` | `x \notin (A \cup B)` | $$x \notin (A \cup B)$$ |
<!--| `(a+b) // (c+d)` | `\frac{a+b}{c+d}` | $$\frac{a+b}{c+d}$$ |-->

### 3. Alfabet grecki, Nieskończoność i Znaki
| Wejście skrótowe | Wyjście LaTeX | Przykładowy render |
| :--- | :--- | :--- |
| `alfa + beta` | `\alpha + \beta` | $$\alpha + \beta$$ |
| `-oo` | `-\infty` | $$-\infty$$ |
| `Gamma // fi` | `\frac{\Gamma}{\phi}` | $$\frac{\Gamma}{\phi}$$ |
| `-varepsilon + 5` | `-\varepsilon + 5` | $$-\varepsilon + 5$$ |

## Tryby uruchamiania z `.env`

Aplikacja czyta konfigurację z pliku `.env` i uruchamia się w jednym z dwóch trybów:

- `MODE=CONSOLE` - tryb terminalowy (domyślny)
- `MODE=API` - uruchamia serwer HTTP (Javalin)
- `PORT=7070` - port dla API (opcjonalny)

Przykładowy `.env`:

```env
MODE=API
PORT=7070
```

Uruchomienie:

```bash
mvn compile
mvn exec:java
```

## API

Endpoint:

- `POST /api/convert`

Przykładowe wywołanie:

```bash
curl -X POST http://localhost:7070/api/convert \
  -H "Content-Type: application/json" \
  -d '{"expression":"f(x) = x dla x >=0; -x dla x <0"}'
```

Przykładowa odpowiedź:

```json
{
  "expression": "f(x) = x dla x >=0; -x dla x <0",
  "latex": "...wynik wygenerowany przez parser..."
}
```

