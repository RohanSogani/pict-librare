

var mongo = require('mongodb') ;

var Server = mongo.Server,Db = mongo.Db, BSON = mongo.BSONPure ; 


var server = new Server('localhost',27017,{auto_reconnect:true}) ; 

db = new Db('libdb',server) ; 

db.open(function(err,db){
    if(!err){
        console.log("Connected to 'books.db' database");
        db.collection('books',{strict:true},function(err,collection){
            if(err){
                console.log("The 'books' collection doesn't exist"); 
                populateDB();
            }
        }
       );
    }
});


exports.findByTitle = function(req,res){
    var query  = decodeURIComponent(req.query.q) ; 
    console.log('Retrieving title:'+query) ; 
    db.collection('books',function(err,collection){
            collection.find({'title':query}).toArray(function(err,items){
                res.send(items) ; 
            });
        });
    
}

exports.addBook = function(req,res){
    var book = req.body ; 
    console.log('Adding Book:'+JSON.stringify(book));
    db.collection('books',function(err,collection){ 
               collection.insert(book,{safe:true},function(err,result){ 
                        if(err){
                            res.send({'error':'An error has occured'});
                        }
                        else{
                            console.log('Success:'+JSON.stringify(result[0]));
                            res.send(result[0]);
                        }
                   });
        });
}



var populateDB = function(){
    var books = [{
                    title:"C++",
                    author:"Bjarne",
                    publication:"Pearson",
                    categories:["Programming","Academic"],
                         
                 },
                 {
                     title:"java",
                     author:"Swamy",
                     publication:"McGraw Hill",
                     categories:["Programming","Web","GUI","Database"],


                 },
                 {
                       title:"Computer_Networks",
                       author:"Tannenbaum",
                       publication:"Pearson",
                       categories:["Networks","Hardware"], 

                 },
                 {
                        title:"Theory_of_Computation",
                        author:"Sipser",
                        publication:"Oxford",
                        categories:["Compilers","Theory","Mathematics" ],
                             
                 },
                 {
                        title:"Introduction_to_algorithms",
                        author:"CLRS",
                        publication:"PHI",
                        categories:["Algorithms","Mathematics","Theory"],
                 },

                 ];

                 db.collection('books',function(err,collection){
                    collection.insert(books,{safe:true},function(err,result){})
                 });
};
