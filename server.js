var express = require('express')  , 
books = require('./routes/books'); 
//var assert = require('assert');
var app = express(); 

app.configure(function(){
    app.use(express.logger('dev'));
    app.use(express.bodyParser()); 
}) ; 
//app.get('/wines',wines.findAll); 

app.get('/books/search',books.findByTitle);
  
app.post('/books',books.addBook) ; 

app.put('/books/:id',books.updateBook); 

app.delete('/books/:id',books.deleteBook) ; 



app.listen(3000) ; 
console.log('Listening on port 3000') ; 
