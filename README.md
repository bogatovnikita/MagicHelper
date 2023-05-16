# AR154


1. [Figma](https://www.figma.com/file/AeNdMYk97iEtm4kIcNmPj4/AR154-new-magic-cleaner-%40mixsolo?node-id=2-6463&t=pXKmAE3h9ZXxyk97-0)
2. Trello:
   1. [Daily](https://trello.com/c/9WT06Sky/94-ar154-new-magic-cleaner-mixsolo)
   2. [Development](https://trello.com/c/hReX1wQU/193-ar154-new-magic-cleaner-mixsolo)
3. Главная ветка - main
4. [ИДС](https://docs.google.com/spreadsheets/d/1AHlBq4dA7J7cFEF2MS-sQe1qSKzSffZV/edit#gid=243144553)

## Конвенции

### Когда сливаться?
Чем чаще, тем лучше, но не менее, чем ращ в день.

### Куда кидать ресурсы

В модуль res, он является общим хранилищем для ресурсов

### Что кидать в модуль res?

1. - [ ]  Стринги все
2. - [x]  Цвета все 
3. - [x]  Шрифты все
4. - [ ]  Стили, которые используются более, чем в одном модуле
5. - [ ]  Иконки, которые используются более, чем в одном модуле
6. - [ ]  Анимации, которые используются более, чем в одном модуле



### Именование

1. Строки - для каждой нужен префикс, соответствующию модулю, и суффикс, соответствующий
   параметру, если он есть:

```xml
<string name="storage_title_S">Some stroge title %s</string>
<string name="storage_title">Some stroge title</string>

<string name="cache_title_S">Some stroge title %s</string>
<string name="cache_title">Some stroge title</string>
```

2. Цвета - в соответствии с фигмой. Если нет в фигме, то запросить у дизайнера.

### Что делаем со строками

1. По возможности переименовываем в соответствии с конвенцией
2. Если строка используется более, чем в 1-ом модуле, ставим префикс `general_`

### Как именовать коммиты?

#### Обязательно
[имя модуля]: описание коммита
[имя модуля]:[имя модлуя]: описание коммита

#### Рекомендуемо
[имя модуля].[имя пакета] описание коммита

### Как именовать пакеты?

[никнейм].[имя фичи].[data/domain/presentation]. ...