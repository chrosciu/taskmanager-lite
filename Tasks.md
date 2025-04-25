# Warsztat - Zadania

## Zadanie 1: Pakiety
Dokonaj reorganizacji pakietów w projekcie, aby (na ile to możliwe) odseparować domenę zespołów od domeny użytkowników.

Zagadnienia do rozważenia:
* na ile da się ograniczyć publiczna widoczność metod w klasach?
* czy zmiana nazwy jakiejś klasy może ułatwić separację domen?

## Zadanie 2: Fasada
Dokonaj refaktoryzacji kodu, aby wprowadzić wzorzec fasady w domenie zespołów, analogicznie do tego jak zostało to zrobione w domenie użytkowników. Pamiętaj o tym iż oprócz samej fasady, na zewnątrz powinny być widoczne tylko klasy DTO i wyjątków (jako parametry wejściowe i wyjściowe metod fasady).

## Zadanie 3: Uproszczona encja
Zastosuj wzorzec uproszczonej encji w domenie zespołów, analogicznie do tego jak zostało to zrobione w domenie użytkowników. Dzięki zastosowaniu tego wzorca będzie można ukryć widoczność niektórych klas w domenie użytkowników (jakich ?) - ukryj je zatem.

## Zadanie 4: CQRS
Zastosuj wzorzec CQRS w domenie użytkowników (poprzez wydzielenie z fasady repozytorium odpowiedzialnego za zapytania niemodyfikujące stanu aplikacji), analogicznie do tego jak zostało to zrobione w domenie zespołów. Rozważ jakie metody powinno zawierać nowo wydzielone repozytorium. Pamiętaj także, aby zwrócone zeń obiekty nie były encjami.

## Zadanie 5: Czysty moduł `domain`
Zrefaktoryzuj kod w module `domain` w domenie użytkowników (analogicznie jak stało się to w domenie zespołów), tak aby nie zawierał on **żadnych** zależności. Skorzystaj tutaj z przeniesienia repozytoriów JPA do warstwy infrastruktury.

## Zadanie 6: Czysty moduł application
Zrefaktoryzuj kod w module `application` w domenie użytkowników (analogicznie jak stało się to w domenie zespołów), tak aby również i on nie zawierał *prawie* *żadnych* zależności - poza modułem `domain`. Wykorzystaj tutaj klasy konfiguracyjne Springa oraz możliwość rozszerzania istniejących interfejsów DTO z warstwy aplikacji w warstwie infrastruktury.

## Zadanie 7: Usuwanie użytkowników z zespołów poprzez eventy
Zaimplementuj usuwanie użytkowników z zespołów poprzez eventy (analogicznie do procesu ich dodawania), tak aby moduł `task-infrastructure` nie musiał już mieć zależności do modułu `user-application`.