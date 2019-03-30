Scenario komunikacije izmedju agentnske strane i backend-a:
1. Agent se loguje na sistem i salje agent-login poruku ka backend-u
2. Ukoliko su kredencijali validni backend šalje agent-sync poruku sa svim doticnim agentu relevantnim podacima oko stanja baze
3. Agenstska strana prima poruku i vrši lokalni sync baze

--Nakon uspešnog logovanja --

Ukoliko agent želi da izmeni/doda/obriše smeštaj:
1. Agent vrši izmene smeštaja
2. Agenstska strana šalje poruku agent-accommodation-crud gde je specificirano kakva operacija se vrši i nad kojim smeštajem
3. Backend vraća potvrdu odn. negaciju obavljene operacije zajedno sa pridruženim opisom

Scenario potvrđivanja rezervacije/šalje poruku:
1. Agent potvrđuje rezervaciju/šalje poruku
2. Šalje se agent-reservation-crud/agent-message-crud
3. Backend vraća potvrdu odn. negaciju obavljene operacije zajedno sa pridruženim opisom