# 1 užduotis: Java objektas ↔ XML

Šis failas skirtas žmogui, kuris moka PHP, bet dar nėra dirbęs su Java. Projektas įgyvendina abi užduoties dalis. Pavyzdyje yra vienas studentas ir **10 jam priskirtų disciplinų**.

## Kaip paleisti „Mac“ kompiuteryje

1. Atidaryk programą **Terminal**. Ją gali rasti per „Spotlight“ paiešką (`⌘` + tarpas, įrašyk `Terminal`).
2. Nukopijuok ir įvykdyk šias komandas po vieną:

   ```bash
   cd /Users/vaidotas/Projects/ws1
   java -version
   ./gradlew run
   ```

3. `java -version` turi parodyti Java 21. Šiame kompiuteryje Java 21 jau įdiegta. Pirmą kartą `./gradlew run` gali užtrukti, nes „Gradle Wrapper“ parsisiųs Gradle ir JAXB bibliotekas. Tam reikia interneto.
4. Kai pamatysi `BUILD SUCCESSFUL`, virš jo bus išvestas studentas, 10 disciplinų ir iš Java objekto naujai sugeneruotas XML. Serveris ir klientas paleidžiami automatiškai vienos komandos metu. Antras „Terminal“ langas nereikalingas.

Jei projektą perkelsi į „Windows“, tame aplanke komandą `./gradlew run` pakeisk į `gradlew.bat run`. Tame kompiuteryje taip pat turi būti įdiegta Java 21.

## Testai, schemų patikra ir JavaDoc

Projekto aplanke paleisk:

```bash
./gradlew test javadoc
```

Sėkmę rodo `BUILD SUCCESSFUL`. Yra 10 automatinių testų: tikrinami visi pagrindiniai Java klasių darbai, perdavimas per tinklą, abu JAXB virsmai ir XML atitikimas DTD bei XSD. Testų ataskaita yra `build/reports/tests/test/index.html`. Sugeneruota JavaDoc dokumentacija yra `docs/javadoc/index.html`; ji taip pat įkelta į „GitHub“. Abu HTML failus gali atidaryti naršyklėje per „Finder“.

Jei nori atskirai patikrinti XML su kompiuteryje esančiu `xmllint`, projekto aplanke paleisk:

```bash
xmllint --noout --valid src/main/resources/student.xml
xmllint --noout --schema src/main/resources/student.xsd src/main/resources/student.xml
```

Pirmoji komanda tikrina pagal DTD, antroji pagal XSD. Šių komandų paleisti **nebūtina**, nes `./gradlew test` tai patikrina automatiškai.

## Kas yra kiekviename faile

| Failas | Paskirtis |
| --- | --- |
| `src/main/resources/student.xml` | Pradinis XML dokumentas su studentu ir 10 disciplinų. |
| `src/main/resources/student.dtd` | DTD: nurodo, kokie elementai ir kokia jų tvarka leidžiami. |
| `src/main/resources/student.xsd` | XSD: papildomai tikrina tipus (`int`, `float`, `boolean`) ir reikalauja bent 10 disciplinų. |
| `src/main/java/lt/example/xmltask/Student.java` | Pagrindinis POJO su `String`, `int`, `float`, `boolean`, `char` ir `List<Subject>` laukais. |
| `src/main/java/lt/example/xmltask/Subject.java` | Vienos disciplinos POJO. |
| `src/main/java/lt/example/xmltask/XmlServer.java` | Per `java.net.ServerSocket` išsiunčia pradinį XML. |
| `src/main/java/lt/example/xmltask/XmlClient.java` | Per `java.net.Socket` priima XML baitus. |
| `src/main/java/lt/example/xmltask/XmlTransformer.java` | Vienintelė klasė, atliekanti JAXB virsmus ir XSD patikrą. |
| `src/main/java/lt/example/xmltask/DemoApplication.java` | Sujungia visus veiksmus į vieną paleidžiamą demonstraciją. |
| `src/test/java/lt/example/xmltask/` | JUnit testai. |
| `build.gradle` | Bibliotekos ir paleidimo komandos. |
| `gradlew`, `gradlew.bat`, `gradle/wrapper/` | „Gradle Wrapper“, todėl nereikia atskirai diegti Gradle. |

## Kas vyksta paleidus programą

1. `DemoApplication` sukuria serverį vietiniame kompiuteryje. Portas `0` reiškia, kad laisvą numerį parenka pati operacinė sistema.
2. `XmlServer` perskaito `student.xml` ir perduoda jį klientui per TCP ryšį.
3. `XmlClient` priima tą patį XML kaip baitus.
4. `XmlTransformer.transformToPOJO()` patikrina gautą XML pagal XSD ir JAXB pagalba paverčia jį `Student` objektu su `Subject` sąrašu.
5. Programa išveda visus objekto laukus į konsolę.
6. `XmlTransformer.transformToXML()` tą objektą vėl paverčia XML, patikrina rezultatą pagal XSD ir išveda į konsolę.

Pradinis XML turi nuorodą į DTD (`<!DOCTYPE ...>`), kad jį būtų galima tikrinti DTD. Iš tinklo gautam XML JAXB nenaudoja išorinio DTD failo; vietoje to taikoma XSD patikra. Taip klientas nepriklauso nuo atskiro DTD failo šalia tinklo duomenų.

## Palyginimas su PHP

- **POJO** yra paprasta Java klasė su laukais ir metodais. Ji panaši į PHP klasės objektą, kuriame laikai duomenis.
- `List<Subject>` panašus į PHP masyvą, kuriame yra keli `Subject` objektai.
- **JAXB** atlieka darbą, panašų į PHP XML duomenų nuskaitymą į objektą ir objekto serializavimą atgal į XML. Nuo Java 11 JAXB nebėra įtraukta į JDK, todėl biblioteka pridėta per `build.gradle`.
- **Gradle** valdo bibliotekas ir komandas panašiai kaip „Composer“ PHP projektuose.
- **JUnit** testai atlieka panašų vaidmenį kaip „PHPUnit“.

`char` yra vienas Java simbolis. JAXB pagal numatytąjį veikimą jį pateikia kaip simbolio skaitinį kodą, todėl `Student` klasėje yra du trumpi XML tarpiniai metodai, kurie įrašo ir perskaito tikrą raidę `J`. Taip Java laukas lieka `char`, o XML atitinka XSD vieno simbolio taisyklę.

## Kodėl kodas sudėtas taip

Kiekviena klasė turi aiškią užduotį: POJO klasės laiko duomenis, serveris siunčia, klientas priima, transformavimo klasė verčia ir tikrina XML, o pagrindinė klasė koordinuoja veiksmus. Taip laikomasi S.O.L.I.D. vienos atsakomybės principo, o paprastas pavyzdys neapkrautas nereikalingomis abstrakcijomis. Transformavimo logika sutelkta tik `XmlTransformer` klasėje; jos du vieši virsmo metodai vadinasi tiksliai `transformToPOJO()` ir `transformToXML()`.

Kodui naudojami įprasti Java pavadinimai: klasių vardai prasideda didžiąja raide, metodų ir laukų vardai – mažąja, o paketo vardas (`lt.example.xmltask`) rašomas mažosiomis raidėmis. Klasės ir vieši metodai turi JavaDoc komentarus.

## Jei nori pakeisti duomenis

Redaguok `src/main/resources/student.xml`. Disciplinos yra tarp `<subjects>` ir `</subjects>`; turi likti **bent 10** `<subject>` įrašų. Jei pakeisi laukų tvarką ar įrašysi netinkamą tipą, XSD patikra sustabdys programą, o testai parodys klaidą. Po pakeitimo vėl paleisk `./gradlew test run`.

## Naudotos oficialios nuorodos

- [JAXB projekto dokumentacija](https://eclipse-ee4j.github.io/jaxb-ri/) – XML ir Java objektų susiejimas.
- [Gradle Wrapper dokumentacija](https://docs.gradle.org/current/userguide/gradle_wrapper.html) – `gradlew` paleidimas.
- [JUnit 5 naudotojo vadovas](https://docs.junit.org/5.13.1/user-guide/index.html) – testai.
- [Java paketų vardų taisyklės](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html) – klasių organizavimas.
