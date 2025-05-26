use test

db.users.insertOne({'username': "smith"})
db.users.find()

db.users.updateOne({username: "smith"}, {
    $set: {
        favorites: {
            cities: ["Chicago", "Cheyenne"],
            movies: ["Casablanca", "For a Few Dollars More", "The Sting"]
        }
    }
})
db.users.find()


db.users.insertOne({'username': "jones"})
db.users.find()

db.users.updateOne({username: "jones"}, {
    $set: {
        favorites: {
            movies: ["Casablanca", "Rocky"]
        }
    }
})
db.users.find()

db.users.find({"favorites.movies": "Casablanca"})

db.users.updateMany(
    {"favorites.movies": "Casablanca"},
    {$addToSet: {"favorites.movies": "The Maltese Falcon"}},
    {upsert: false}
)

for(let i = 0; i < 20000; i++) {
    db.numbers.insertOne({num: i});
}
db.numbers.countDocuments()
db.numbers.find()

db.numbers.find({
        'num': {'$gte': 20, '$lte': 25}
    }
).explain("executionStats")


db.numbers.createIndex({num: 1})
db.numbers.getIndexes()

db.numbers.find({
        'num': {'$gte': 20, '$lte': 25}
    }
).explain("executionStats")

/*
*     "executionStats": {
*     "executionSuccess": true,
*     "nReturned": 6,
*     "executionTimeMillis": 0,
*/