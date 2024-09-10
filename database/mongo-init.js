db = db.getSiblingDB('test_db')
db.createCollection("AbnormalHealthRecord")
db.createCollection("MedicalHistory")
db.MedicalHistory.insertMany(
    [{
        "citizenid":123,
        "fullname": "Chris Wong",
        "age": 25, 
        "sex": "Male",
        "allergy": ["Asthma", "Peanuts"],
        "illness": ["Heart disorder", "High blood pressure"]
    },
    {
        "citizenid":456,
        "fullname": "Ivan Chan",
        "age": 20, 
        "sex": "Male",
        "allergy": ["Asthma", "Peanuts"],
        "illness": ["Heart disorder", "High blood pressure", "Anxiety"]
    }]
)