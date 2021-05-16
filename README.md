# Auctions Petru application

Install notes
===

```
$ git clone https://github.com/petrul/petru-spideo.git
$ cd petru-spideo
```

The projects is composed of two apps, a server-side java named petru-auctions/ and a ionic angular named petru-auctions-ionic/

You'll need java java version 13.0.2, gradle 7.0 or better for the backend.
Make sure you have node v14, Angular: 6.1.3, ionic 6.14.1 or better for the frontend.

Build the java backend:

```
$ cd petru-auctions
$ gradle clean bootrun
```

The java back server should launch on port 8080.

Open a new terminal and navigate to the ionic frontend:

```
$ cd petru-spideo/petru-auctions-ionic
```

Run:

```
$ npm i
$ ionic serve
```

The app should be served on port 8100. 

Documentation
===

This application is backed by Java Spring Boot. 
The frontend is powered by Angular Ionic.

## API

A list of REST links is available at:

- http://localhost:8080/api/
- http://localhost:8080/api/users{?page,size,sort}
- http://localhost:8080/api/auctionHouses{?page,size,sort}
- http://localhost:8080/api/biddings{?page,size,sort}
- http://localhost:8080/api/auctions{?page,size,sort}


For example:

- to retrieve a list of AuctionHouses:
    curl  http://localhost:8080/api/auctionHouses

- retrieve Auctions for AuctionHouse #3:
    curl http://localhost:8080/api/auctionHouses/1010/auctions/

- retrieve Biddings of auction #1110
    curl http://localhost:8080/api/auctions/1110/biddings

- To create a new AuctionHouse:
    curl -X POST http://localhost:8080/api/auctionHouses -d '{  "name": "auction house #1" }' -H 'Content-Type:application/json'
  

Rest API's are exposed through the built-in Spring Data Rest repositories corresponding to entities: User, AuctionHouse, Auction and Bidding.

There is also a separate RestController, namely FacadeController, which is used for aggreggating data and presenting them into a form which removes redundant calls in some use cases (for example get an Auction with its Biddings in one call, without redundant information). To that end, data transfer objects (DTO's) corresponding to each Entity are created.


