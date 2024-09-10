const { MongoClient } = require('mongodb');
const { Kafka } = require('kafkajs');
const kafka = new Kafka({
    brokers: ['localhost:29092'],
});
  
const consumer = kafka.consumer({ groupId: 'my-group' });
const mongoURL = 'mongodb://localhost:27017';

async function insertRecordToMongodb(report_json) {    
    try {
        // Connect to MongoDB
        const client = await MongoClient.connect(mongoURL, {
          useNewUrlParser: true,
          useUnifiedTopology: true,
          authSource: "admin",
          auth: {
            username: "user",
            password: 'pass',
          },
        });
        console.log('Connected to MongoDB');
    
        const db = client.db("test_db");
        const collection = db.collection("AbnormalHealthRecord");
        
        const createResult = await collection.insertOne(report_json);
        console.log(`Document created with ID: ${createResult.insertedId}`);
    
      } catch (err) {
        console.error('Error connecting to MongoDB:', err);
      } 
}


// helper functions
function ecg_array_preprocess(ecg_arr){
    for (let i = 0; i < ecg_arr.length; i++){
        if (ecg_arr[i] === null){
            ecg_arr[i] = 0
        }
        ecg_arr[i] = parseFloat(ecg_arr[i]);
    }
    return ecg_arr
}

function healthmetric_check(kafka_message_json){
    let checked_result = kafka_message_json;
    checked_result["abnormal"] = false
    

    // for testing
    // if (kafka_message_json.HEARTRATEAVERAGE > 70){
    //     checked_result.abnormal = true;
    //     checked_result["comment"] = `HEART_RATE_AVERAGE: ${kafka_message_json.HEARTRATEAVERAGE}`; 
    // }


    if (kafka_message_json.HEART_RATE_AVERAGE < 70 || kafka_message_json.HEART_RATE_AVERAGE > 150 ){
        checked_result.abnormal = true;
        checked_result["comment"] = `HEART_RATE_AVERAGE: ${kafka_message_json.HEART_RATE_AVERAGE}`; 
    }
    else if (kafka_message_json.OXYGENSATURATIONAVERAGE < 90){
        checked_result.abnormal = true;
        checked_result["comment"] = `HEART_RATE_AVERAGE: ${kafka_message_json.HEART_RATE_AVERAGE}`; 
    }
    else if (kafka_message_json.BLOOD_PRESSURE_SYSTOLIC_AVERAGE < 80 || kafka_message_json.BLOOD_PRESSURE_DIASTOLIC_AVERAGE > 200){
        checked_result.abnormal = true;
        checked_result["comment"] = `BLOOD_PRESSURE_AVERAGE: ${kafka_message_json.BLOOD_PRESSURE_DIASTOLIC_AVERAGE} | ${kafka_message_json.BLOOD_PRESSURE_SYSTOLIC_AVERAGE}`; 
    }

    return checked_result
}

const run = async () => {
  await consumer.connect();
  await consumer.subscribe({ topic: 'HEALTHRECORDAVERAGE_5S' });
  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
        
        let message_json = JSON.parse(message.value.toString());

        let abnormalReport = healthmetric_check(message_json);
        
        if (abnormalReport.abnormal == true){          
            abnormalReport["ecgaggregation"] = ecg_array_preprocess(abnormalReport.ECGAGGREGATION);            
            abnormalReport["citizenid"] = abnormalReport.ID;
            abnormalReport["timestamp"] = String(new Date().getTime());
            abnormalReport["heartrateaverage"] = parseFloat(abnormalReport.HEARTRATEAVERAGE)
            abnormalReport["oxygensaturationaverage"] = parseFloat(abnormalReport.OXYGENSATURATIONAVERAGE)
            abnormalReport["bloodpressuresystolicaverage"] = parseFloat(abnormalReport.BLOODPRESSURESYSTOLICAVERAGE)
            abnormalReport["bloodpressurediastolicaverage"] = parseFloat(abnormalReport.BLOODPRESSUREDIASTOLICAVERAGE)
            console.log(abnormalReport)
            insertRecordToMongodb(abnormalReport);        
        }
    },
  });

};


run().catch(console.error);

