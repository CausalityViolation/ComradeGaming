# COMRADE GAMING
Probably the most adequate retailer/CRUD-application in the world


# HOW TO SETUP:

 1. Docker Compose

 2. Docker Compose igen (För att main-applikationen startar innan SQL-servern och krashar, så den behöver startas om manuellt igen)

 3. SUCCESS!


==========================================

AUTH:

Alla requests utom de som står under "ALLA" kräver en giltig JWT-token. Dessa ska skickas som header med prefixet "Bearer ". 
JWT-tokens är giltiga i 10 minuter från skapandet.


===========================================
```
@Get

/login (form med username + password) 
	logga in

/products/information/{id}
	få info om en produkt

/products
	returnerar alla produkter

/products/{id}
	returnerar specifierad produkt

/stats
	returnerar köpstatistik

/users
	returnerar alla users

/users/{namn}
	returnerar en user med det specifierade namnet

/users/forsale/{userID}
	retunerar alla produkter som relevant användare säljer

/users/refresh
	refreshar din utgångna token
	REFRESH TOKEN SOM BEARER TOKEN
```
-----------------------------------------------------
```
@Patch

/users/update/{userID}/{password}
	updaterar den specifierade användarens lösenord

/users/buy/{userID}/{productID}
	användaren köper den relevanta produkten

/users/forsale/{userID}/add
	lägger till en begagnad produkt som användaren vill sälja till användaren
	JSON-BODY MED PRODUKTEN
{
	"name": "Hoodie of Nerdness",
	"price": "420",
	"productDescription": "If you wear this. No intercourse you will have",
	"imageURL": "www.bilder.com/hoodiepic.png",
	"garment": "Hoodie"
}


/users/forsale/{userID}/update/{productID}
	uppdaterar en produkt som den relevanta användaren har till salu
	JSON-BODY MED PRODUKTEN
{
	"name": "Hoodie of Nerdness",
	"price": "420",
	"productDescription": "If you wear this. No intercourse you will have",
	"imageURL": "www.bilder.com/hoodiepic.png",
	"garment": "Hoodie"
}

/users/admin/create/{id}
	uppgraderar en användare till admin
```
--------------------------------------------------------
```
@Post


/products/add/digitalgame
	lägger till ett datta-spejl i databasen
	JSON-BODY
{
	"name": "Baldurs Gate Enhanced Edition",
	"productDescription": "Better than Final Fantasy",
	"developer": "Black_Isle",
	"publisher": "Beamdog",
	"platform": "PC"
}



/products/add/clothing
	lägger till ett klädesplagg i databasen
	JSON-BODY
{
	"name": "Hoodie of Nerdness",
	"price": "420",
	"productDescription": "If you wear this. No intercourse you will have",
	"imageURL": "www.bilder.com/hoodiepic.png",
	"garment": "Hoodie"
}



/products/add/boardgame
	lägger till ett brädspel i databsen
	JSON-BODY
{
	"name": "Call of Cthulhu: Mansions of Madness",
	"price": 666,
	"productDescription": "",
	"imageURL": "www.bilder.com/cocmom.jpg"
}

/products/add/movie
	lägger till en film i databsen
	JSON-BODY
{
	"name": "The Cement",
	"productDescription": "Watch concrete dry!!!",
	"publisher": "MovieFilmAB",
	"price": "129"
}

/users/signup
	registrera en ny användare. Default roll är "USER".
	JSON-BODY
{ 
	"username": "David",
	"password": "Dromedar"
}

```
------------------------------------------------------
```
@Delete

/users/forsale/{userID}/sold/{productID}
	Användaren "säljer" en av sina produkter som hen har till salu


/users/delete/{namn}
	raderar den specifierade användaren


/products/delete/{id}
	raderar den specifierade produkten

```
===================================================================
```
Authorization/Roller

@Get
	ALLA:
		/users/refresh
	USER och ADMIN:
		/products/information/..., /products, /products/{variabel}, /users/forsale/..., /users/forsale/...
	ADMIN:
		/products/..., /users/...
	

@Post
	ALLA:
		/login, /users/signup
	ADMIN:
		/products/...

@Patch
	ADMIN och USER:
		/users/buy/..., /users/forsale/...
	ADMIN:
		/products/..., /users/update/..., /users/admin/...

@Delete
	ADMIN:
		/products/..., /users/...
```


