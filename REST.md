#Security

* /api/whoiam:GET
    - returns response with id user, roles and name
    
* /api/j_spring_security_check:POST
    - authenticate user by j_username and j_password fields
    - returns authResponse
    
* /api/j_spring_security_logout
    - logout user
    - returns LogoutResponse

#PermitAll

@Since(0.6)
* /api/feedback:POST
    - send feedback(requires form field email, subject, message)
@Since(0.8)
* /api/catalog/products:GET
    - returns paginated catalog of products

@Since(0.8)
* /api/catalog/companies:GET
    - returns catalog of companies
    
    
@Since(0.16)
* /api/companies/{companyId}/trainers
    - return active published company's trainers
    
@Since(0.16)
* /api/trainers/{trainerId}
    - returns trainer by id


@Since(0.17)
* /api/register-company-self:POST
    register company itself

#Company level

* /api/company:GET
    - getting information for login company
    - return Company object
    
* /api/company/clientcards
    - returns list of card with users who have bought product company or is registered by company
    - list of card with populated users
    
@Deprecated(0.6)
* /api/company/card/{cardUUID}:GET 
@Since(0.6)
* /api/company/cards/{cardUUID}:GET 
    - getting information for card with related user and abonnements buying from login company
    - return Card object with inner populated objects
    
@Deprecated(0.6)
* /card/bynfc/{nfcUUID}
Since(0.6)
* /cards/by-nfc/{nfcUUID}
    - returns card by nfcUUID. It includes only nfcUUID and cardUUID?
 
    
@Deprecated(0.6)
* /api/company/abonnement/markAttended:POST
@Since(0.6)
* /api/company/abonnements/mark-attended:POST
    - based on form request(@deprecated) and json(recommended)
    - adding attendance for user. POST parameters are: userId,abonnementId
    - return updated Abonnement object
    
@Deprecated(0.6)
* /api/company/abonnement/markAttendedByCardId:POST
@Since(0.6)
* /api/company/abonnements/mark-attended-by-card-id
    - adding attendance for user. POST parameters are: cardId,abonnementId
    - return updated Abonnement object
    
@Deprecated(0.6)
* /api/company/abonnement/{id}:GET 
@Since(0.6)
* /api/company/abonnements/{id}:GET 
    - getting detailed info about abonnement
    - return Abonnement object with populated product
    
@Deprecated(0.6)
* /api/company/customer/bindCard:POST
@Since(0.6)
* /api/company/clients/bind-card:POST
    - binding card with customer. POST parameters are: userId, cardUUID
    - return Card object binded with user
    
@Deprecated(0.6)
* /api/company/customer/{userId}:PATCH
@Since(0.6)
@Deprecated(0.7)
* /api/company/clients/{userId}:PATCH
    - updates user by id
    - consumes user
    - returns updated user
@Since(0.7)
* /api/company/clients/update/{cardUUID}
    - updates client by cardUUID
    - updated user
    
* /api/company/products/new:POST
    - creating new product
    - new product with id
    
* /api/company/products/{productId}:PATCH
    - update existed product with new field
    - consumes json
    - returns updated product
    
* /api/company/products:GET
    - getting all products for login company
    - return list of Product objects
    
* /api/company/products/{productId}
    - getting product by product Id
    - return product 
    
@Deprecated(0.6)
* /api/company/productstat/{productId}
@Since(0.6)
* /api/company/productstats/{productId}
    - getting product statistic for product
    - return ProductStat object

@Deprecated(0.5)
* /api/company/card/enrollProduct:POST
    - enroll product to card of user. POST parameters are: cardUUID, productId
    - return created Abonnement object
    
@Deprecated(0.6)
* /api/company/card/enroll-product:POST
@Since(0.6)
* /api/company/cards/enroll-product:POST
    - support as form @deprecated(0.16) and JSON as recommended content type
    - enroll product to card of user. POST parameters are: cardUUID, productId, startDate, endDate
    - return created Abonnement object
    
@Deprecated(0.6)    
* /api/company/clientGroups:GET
@Since(0.6)
* /api/company/clientgroups:GET
    - returns client groups for company. Doesn't include members for now
    
@Deprecated(0.6)
* /api/company/clientGroups/byProduct/{productId}:GET
@Since(0.6)
* /api/company/clientgroups/by-product/{productId}:GET
    - returns client group by product id populated with members

@Since(0.7)
* /api/company/abonnements/change-end-date:POST
    - update endDate for abonnement by company with comment
    - returns updated abonnement

@Since(0.8)
* /api/company/products/archive:GET
    - return only archive products for company
@Since(0.8)
* /api/company/products/archive:POST
    - make product archive
    
@Since(0.8)
* /api/company/products/restore:POST
    - make product active, not archive

@Since(0.9)
* /api/company/cards/next-free-virtual
    - return next free virtual card for register of new client

@Since(0.10)
* /api/company/abonnements/add-comment:POST
    - add comment to abonnement
    
@Since(0.11)
* /api/company/promotions/new:POST
    - creating new promotion
    - new promotion with id
    
@Since(0.11)
* /api/company/promotions/{promotionId}:PATCH
    - update existed promotion with new field
    - consumes json
    - returns updated promotion
    
@Since(0.11)
* /api/company/promotions:GET
    - getting all promotions for login company
    - return list of Product objects
    
@Since(0.11)
* /api/company/promotions/{promotionId}
    - getting promotion by promotion Id 
    - return promotion 
    
@Since(0.11)
* /api/company/promotions/calculate-new-price
    - returns new price for product based on productId and promotionId
    
@Since(0.12)
* /api/company/abonnements/consolidated:GET
    -returns consolidated abonnementId based on userId and productId

@Since(0.12)
* /api/company/accepted-agreement-and-offer:GET
    - returns result about accepting agreement and offer by company
    
@Since(0.12)
* /api/company/accept:POST
    - send response to accept agreement and offer by company    
    
@Since(0.16)
* /api/company/single-attendances/mark-attended:POST
    - marks not-registered user as attended

@Since(0.16)
* /api/company/trainers
    - return company's trainers
    
 @Since(0.16)
* /api/company/trainers/active
    - return company's active trainers   
    
@Since(0.16)
* /api/company/current-statistic
    - return current statistic company
    
#System level

* /api/admin/cards:GET
    - getting information about all cards
    - return list of Cards objects
    
@Deprecated(0.6)    
* /api/admin/deliverToCompany:POST
@Since(0.6)
* /api/admin/cards/deliver-to-company:POST
    - deliver card to company

@Deprecated(0.6)
* /api/admin/addToPool:POST
@Since
* /api/admin/cards/add-to-pool
    - add card to pool

* /api/admin/companies:GET
    - returns all companies


#User level

* /api/user/card:GET
    - getting information about card for login user with populated abonnements
    
@Deprecated(0.6)    
* /api/user/company/{companyId}:GET
@Since(0.6)
* /api/user/companies/{companyId}:GET
    - getting company with active products
    - returns company populated with products
    
* /api/user/profile/update:PATCH
    - updates user profile for current user
    - returns updated user entity
        
* /api/user/change-email-with-agreement
    - sets initial email for client
    - set that user has accepted user agreement
    - returns ChangingEmailResponse
    
@Deprecated(0.6)
* /api/user/abonnement/consolidated/{productId}
@Since(0.6)
* /api/user/abonnements/consolidated/{productId}
    - returns consolidated abonnement for the given product
    
@Since(0.11)
* /api/user/settings/update
    - update userSettings
    
#For all authenticated users

* /api/upload:POST
    - uploads image to server
    - returns media object with information about uploaded image(filename, relativepath,...)



