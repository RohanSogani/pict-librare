var express = require('express')  , 
books = require('./routes/books'); 

var app = express(); 

app.configure(function(){
    app.use(express.logger('dev'));
    app.use(express.bodyParser()); 
}) ; 
//app.get('/wines',wines.findAll); 

app.get('/books/search',books.findByTitle);
  
app.post('/books',books.addBook) ; 

//app.put('/wines/:id',wines.updateWine); 

//app.delete('/wines/:id',wines.deleteWine) ; 



app.listen(3000) ; 
console.log('Listening on port 3000') ; 
