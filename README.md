B232270024 О.Дэлгэрмаа

## Орчин
java:
delgermaa@debian:~/lab09$ java -version
openjdk version "17.0.19" 2026-04-21
OpenJDK Runtime Environment (build 17.0.19+10-1-deb12u2-Debian)
OpenJDK 64-Bit Server VM (build 17.0.19+10-1-deb12u2-Debian, mixed mode, sharing)

mv:
delgermaa@debian:~/lab09$ mvn -version
Apache Maven 3.9.9
Maven home: /usr/share/maven
Java version: 17.0.19, vendor: Debian, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.12.111+deb13-amd64", arch: "amd64", family: "unix"



## Хэсэг A: Happy path тестүүд ба эхний хэмжилт

ScholarshipCalculatorTest-д зөвхөн хэвийн урсгалын тестүүдийг бичсэн. Үр дүн (`results/jacoco-A.csv`):

| Хэмжүүр | Хүрээгүй | Хамрагдсан | Хамралт |
|---|---|---|---|
| Instruction | 57 | 95 | 62.5% |
| Branch | 23 | 18 | 43.9% (18/41) |
| Line | 11 | 14 | 56.0% (14/25) |
| Method | 0 | 4 | 100% |

**Ажиглалт.** Хамгийн муу хамрагдсан функц нь `scholarship` Branch 38%, дараа нь `letterGrade` 41%, `gpa` 54% байна. Улаан (огт хүрээгүй) мөрүүд нь голдуу `IllegalArgumentException` шидэх алдааны замууд бөгөөд happy path тест тэдгээрийг огт дайрдаггүй ба шар ромботой салаанууд нь нөхцөл нэг талаараа л шалгагдсан `if`-үүд байна.

## Хэсэг B: CFG зурж branch 100% хүрэх
  ```mermaid
flowchart TD
  S((Start)) --> N1
  N1{"N1: gpa < 0"}
  N2{"N2: gpa > 4"}
  N3{"N3: credits < 0"}
  N4{"N4: hasViolation"}
  N5{"N5: gpa >= 3.8"}
  N6{"N6: credits >= 15"}
  N7{"N7: gpa >= 3.4"}
  N8{"N8: credits >= 12"}
  N9{"N9: gpa >= 3.0"}
  N10["N10: throw exception"]
  N11["N11: return 0"]
  N12["N12: return 100"]
  N13["N13: return 70"]
  N14["N14: return 50"]
  N15["N15: return 0"]
  E((End))
  N1 -- "T: negativeGpaThrows" --> N10
  N1 -- "F: gpaAboveFourThrows" --> N2
  N2 -- "T: gpaAboveFourThrows" --> N10
  N2 -- "F: negativeCreditsThrows" --> N3
  N3 -- "T: negativeCreditsThrows" --> N10
  N3 -- "F: violationReturnsZero" --> N4
  N4 -- "T: violationReturnsZero" --> N11
  N4 -- "F: gpa38Credits15Gives100" --> N5
  N5 -- "T: gpa38Credits15Gives100" --> N6
  N5 -- "F: gpa34Credits12Gives70" --> N7
  N6 -- "T: gpa38Credits15Gives100" --> N12
  N6 -- "F: gpa38LowCreditsFallsTo70" --> N7
  N7 -- "T: gpa34Credits12Gives70" --> N8
  N7 -- "F: gpa30Gives50" --> N9
  N8 -- "T: gpa34Credits12Gives70" --> N13
  N8 -- "F: gpa34LowCreditsFallsTo50" --> N9
  N9 -- "T: gpa30Gives50" --> N14
  N9 -- "F: lowGpaGivesZero" --> N15
  N10 --> E
  N11 --> E
  N12 --> E
  N13 --> E
  N14 --> E
  N15 --> E
```

`scholarship()` функцын CFG-г гараар зурж (`cfg.jpg`), нөхцөл бүрийн True/False
ирмэгт тестийн нэрийг буулгасан: гурван нөхцөлийн exception
(`negativeGpaThrows`, `gpaAboveFourThrows`, `negativeCreditsThrows`),
`violationReturnsZero`, `gpa38Credits15Gives100`, `gpa38LowCreditsFallsTo70`,
`gpa34Credits12Gives70`, `gpa34LowCreditsFallsTo50`, `gpa30Gives50`,
`lowGpaGivesZero`.

Branch ба Line хамралтыг 100% хүргэхэд нийт **25 тест** шаардагдсан
(`results/mvn-verify-B.txt`: `Tests run: 25, Failures: 0`).
`results/jacoco-B.csv`: BRANCH_MISSED = 0 (41/41), LINE_MISSED = 0 (25/25).

Анх A хэсгийн 5 тестээр Branch 18/41 байсан. Дутуу байсан салаанууд нь
`letterGrade()`-ийн C/D/F болон 0-100-аас гадуурх оноо, `scholarship()`-ийн
нөхцөл бүрийн exception ба кредит дутуу тохиолдлууд, `gpa()`-ийн `null`/хоосон
массив, `switch`-ийн C, D, default (F) тохиолдлууд байв.

## C: Давталтын заагийн тестүүд

- 0 удаа: `gpaLoopZero_nullThrows`, `gpaLoopZero_emptyThrows` — null ба хоосон массивт IllegalArgumentException.
- 1 удаа: `gpaLoopOne_singleScoreIsFour` — gpa([95]) == 4.0.
- 2+ удаа: `gpaLoopMany_fourScores` — gpa([95, 85, 75, 65]) == 2.5.

Эдгээр тест хамралтын тоог өсгөөгүй, учир нь Хэсэг B-ийн дараа Line, Branch хамралт аль хэдийн 100% байсан.
Гэхдээ хамралт нь мөр бүр ажилласныг л хэмждэг тул давталт 0, 1, олон удаа ажиллахад
нийлбэр болон `total / scores.length` зөв гарч байгааг (жишээ нь нэг дүнтэй үед хуваагч 1, олон дүнтэй үед дүн бүр нийлбэрт тусах) эдгээр тест л баталгаажуулна.

## D: Хамралт 100% гэхдээ алдаатай

`mvn test` үр дүн (results/mvn-test-D-fail.txt): `boundary_60_isD` унасан,
`expected: <D> but was: <F>`. Алдаа: `letterGrade`-д `score > 60` гэж бичсэн ч
спецификаци 60-ыг D гэсэн тул `score >= 60` байх ёстой. Засвар тусдаа commit-оор хийгдсэн.

**Дүгнэлт.** Алдаа нь Хэсэг B-ийн тестүүдэд баригдаагүй байсан, учир нь `score > 60` ба
`score >= 60` хоёул ижил хоёр салаа (үнэн / худал) үүсгэдэг. Тестүүд 65 (D) ба 30 (F) гэсэн
утгаар хоёр салаагаа хоёуланг нь дайрсан тул JaCoCo 100% гэж харуулсан. Хамралт нь код
ажилласан эсэхийг л хэмждэг, харин тухайн салаа зөв утгаар зөв шийдвэр гаргасан эсэхийг
хэмждэггүй. Зөвхөн заагийн утга 60-ыг шалгасан тест л ялгааг илрүүлсэн. Иймээс хамралт
нь тестийн чанарын шаардлагатай хэмжүүр ч хангалттай нотолгоо биш.