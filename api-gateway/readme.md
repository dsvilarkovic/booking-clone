Za podesavanje vaseg mikroservisa u eureka server i zuul proxy potrebno je: <br><br>
<code>
	1. Navesti u application.properties vaseg servisa :
		<ul>
			<li>a)eureka.client.service-url.default-zone=http://localhost:8761/eureka/  ; ovo je url mikroservisa service-registry koji treba pokrenuti kao spring boot aplikaciju pre svega</li>
			<li>b)server.port dodati broj porta na kojem zelite da vam se pokrece aplikacija, nikako ostavljati na 80/8080</li>
			<li>c)dodati odgovarajuce ime npr: spring.application.name = mock-service </li>
		</ul>
		
	2. (Za zuul proxy, nije obavezno ako ne zelite spolja da pristupati servisima, tj ako nije preko Feign Client ili Rest Template, vec preko klasicne Angular frontend aplikacije) Navesti u api-gateway mikroservisu : 
		<ul>
			<li>a) dodatak urlu zuul-proxyja preko koga ce se pristupati mikroservisu (npr, nas zuul proxy radi na localhost:8762/api/, ako namestite ovu putanju za mock-service, onda ce se operacijama tog mikroservisa pristupati preko localhost:8762/api/mock-service/**):  zuul.routes.mock-service.path=/mock-service/** </li>
			<li>b) id servisa kome cete pristupati, cisto informativno ubaciti, ne secam se poente: zuul.routes.mock-service.service-id=mock-service </li>
			<li>==> zuul.routes.<<ime servisa u application.properties vaseg mikroservisa, tj ovo iz 1.c)>>.</li>
		</ul>
	
	*. ako zelite da privremeno ugasite registrovanje na eureka server ili zuul proxy, dodati u application.properties vaseg mikroservisa sledece: eureka.client.enabled = false <br>
	**. Zuul Proxy registracija (preko api-gateway) je obavezna samo ako zelite spolja da prisupate, ako vam je dovoljno da mikroservisu pristupa samo neki od drugih mikroservisa iz eureka server registra, onda preskociti 2. korak
	***. Redosled pokretanja aplikacija (preko Run Spring Boot Application) je backend -> api-gateway -> vas mikroservis 
</code>