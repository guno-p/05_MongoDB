use tutorial

db.users.insertOne({username: "smith"})
db.users.insertOne({username: "jones"})

db.users.find()
db.users.find({username: "jones"})
db.users.find({
        '$or': [
            {username: "jones"},
            {username: "smith"}
        ]
})

db.users.updateOne({username: "smith"}, {$set: {country: "Canada"}})
db.users.find()

db.users.replaceOne({username: "smith"}, {country: "Canada"})
db.users.updateOne({country: "Canada"}, {$set: {username: "smith", country: "Canada"}})

db.users.updateOne({username: "smith"}, {$unset: {country: 1}})
db.users.find()

show dbs
show collections

db.runCommand({dbStats: 1})
db.runCommand({collStats: "users"})

db.users.deleteOne({username: "smith"})
db.users.deleteMany({})
db.users.drop()

use test
db.product.drop()
for(let i = 0; i < 20000; i++) {
    db.product.insertOne({num: i, name: '스마트폰' + i});
}
db.product.find()

db.product.countDocuments()

db.product.find().sort({'num': -1})

db.product.find().sort({'num': -1}).skip(5*10).limit(10)

db.product.find({
    '$or': [
        {'num': {'$lt': 15}},
        {'num': {'$gt': 19995}}
    ]})

db.product.find({
    '$or': [
        {'name': '스마트폰10'},
        {'name': '스마트폰100'},
        {'name': '스마트폰1000'}
    ]
})

db.product.find(
    { num: { $lt: 5 } },
    { name: 1, _id: 0 }
)
