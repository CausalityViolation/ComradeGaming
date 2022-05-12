# COMRADE GAMING
Probably the most adequate retailer/CRUD-application in the world


# HOW TO SETUP:

 1. run docker-compose up in the application directory via your favorite cmd

 2. run docker-compose up again to restart the main application (it shuts down due to the SQL server taking longer to boot and the main application)

 3. SUCCESS!


==========================================

AUTH:

All requests except those found under "ALL" will require a valid JWT-token. The tokens should be sent with the prefix "Bearer " in postman, or by choosing the Bearer option in Insomnia. Tokens are valid for 10 minutes, and will require manual refresh upon expiration.


===========================================
```
@Get

/login (form with username + password) 
	log in

/products/information/{id}
	product information

/products
	displays all available products

/products/{id}
	displays a specific product

/stats
	displays buyer statistics

/users
	displays all users

/users/{name}
	displays a specified user

/users/forsale/{userID}
	displays all products for sale by specified user

/users/refresh
	refreshes expired JWT-token
	REFRESH TOKEN AS BEARER TOKEN
```
-----------------------------------------------------
```
@Patch

/users/update/{userID}/{password}
	updates specified user's password

/users/buy/{userID}/{productID}
	specified user{userID} purchases a product {productID} 

/users/forsale/{userID}/add
	adds a previously used product to a specified user's "for sale" list
	
	JSON-BODY with the product information
{
	"name": "Hoodie of Nerdness",
	"price": "420",
	"productDescription": "If you wear this, things happen.",
	"imageURL": "www.bilder.com/hoodiepic.png",
	"garment": "Hoodie"
}


/users/forsale/{userID}/update/{productID}
	updates a product in the specified user's "for sale" list
	
	JSON-BODY with the product information
{
	"name": "Hoodie of Nerdness",
	"price": "420",
	"productDescription": "Don't wear this.",
	"imageURL": "www.bilder.com/hoodiepic.png",
	"garment": "Hoodie"
}

/users/admin/create/{id}
	promotes a user to admin status
```
--------------------------------------------------------
```
@Post


/products/add/digitalgame
	Add a digital game to the database
	
	JSON-BODY with the product information
{
	"name": "Baldurs Gate Enhanced Edition",
	"productDescription": "Not better than Final Fantasy",
	"developer": "Black_Isle",
	"publisher": "Beamdog",
	"platform": "PC"
}



/products/add/clothing
	Add a garment to the database
	
	JSON-BODY with the product information
{
	"name": "Hoodie of Nerdness",
	"price": "420",
	"productDescription": "Fun description",
	"imageURL": "www.bilder.com/hoodiepic.png",
	"garment": "Hoodie"
}



/products/add/boardgame
	Add a board game to the database
	
	JSON-BODY with the product information
{
	"name": "Call of Cthulhu: Mansions of Madness",
	"price": 666,
	"productDescription": "Product description goes here!",
	"imageURL": "www.bilder.com/picture.jpg"
}

/products/add/movie
	Add a movie to the database
	
	JSON-BODY with the product information
{
	"name": "The Cement",
	"productDescription": "Watch concrete dry. Now in 8k.",
	"publisher": "MovieFilmAB",
	"price": "129"
}

/users/signup
	Register a new user. Default role is "USER"
	
	JSON-BODY with user information. Password will be encrypted upon submitting the form.
{ 
	"username": "Dolen",
	"password": "Dekk"
}

```
------------------------------------------------------
```
@Delete

/users/forsale/{userID}/sold/{productID}
	Deletes a product from the specified user's "for sale" list. Used when a user sells a product.


/users/delete/{namn}
	Deletes a specified user


/products/delete/{id}
	Deletes a specified product

```
===================================================================
```
Authorization/Roles

@Get
	ALL:
		/users/refresh
	USER and ADMIN:
		/products/information/..., /products, /products/{variabel}, /users/forsale/..., /users/forsale/...
	ADMIN:
		/products/..., /users/...
	

@Post
	ALL:
		/login, /users/signup
	ADMIN:
		/products/...

@Patch
	ADMIN and USER:
		/users/buy/..., /users/forsale/...
	ADMIN:
		/products/..., /users/update/..., /users/admin/...

@Delete
	ADMIN:
		/products/..., /users/...
```


